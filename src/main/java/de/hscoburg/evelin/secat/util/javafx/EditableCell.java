package de.hscoburg.evelin.secat.util.javafx;

import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Klasse fuer die Steuerung editierbarer ListCells
 * 
 * @author zuch1000
 * 
 */
public class EditableCell<T> extends ListCell<T> {

	private TextField textField;

	private ValueListTypeHandler<T> updateHandler;

	private boolean firstEnter = true;

	public EditableCell(ValueListTypeHandler<T> handler) {

		setEditable(true);
		updateHandler = handler;
	}

	@Override
	public void startEdit() {

		if (updateHandler.isLocked(getItem())) {
			return;
		}
		firstEnter = true;
		super.startEdit();

		if (textField == null) {
			createTextField();
		}
		textField.clear();
		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		textField.setText(updateHandler.getText(getItem()));
		textField.requestFocus();
	}

	@Override
	public void cancelEdit() {

		super.cancelEdit();
		setContentDisplay(ContentDisplay.TEXT_ONLY);
		textField = null;
	}

	@Override
	public void commitEdit(T s) {
		super.commitEdit(s);
	}

	@Override
	public void updateItem(final T item, boolean empty) {
		super.updateItem(item, empty);

		if (item != null) {
			setText(updateHandler.getText(item));
		}
	}

	/**
	 * Erzeugt das textfeld und setzt die entsprechenden Listener um den "Commit" zu verarbeiten.
	 */
	private void createTextField() {

		textField = new TextField();
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnKeyReleased(new SeCatEventHandle<KeyEvent>() {

			private final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER);

			private final KeyCombination kb2 = new KeyCodeCombination(KeyCode.ESCAPE);

			@Override
			public void handleAction(KeyEvent t) throws Exception {

				if (kb.match(t) && !firstEnter) {

					final T val = updateHandler.merge(getItem(), textField.getText());

					boolean success = updateHandler.update(val);
					if (success) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								commitEdit(val);

							}
						});

					}

				} else if (kb2.match(t)) {

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							cancelEdit();

						}
					});

				}
				firstEnter = false;

			}
		});
	}
}