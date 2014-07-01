package de.hscoburg.evelin.secat.util.javafx;

import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditableCell<T> extends ListCell<T> {

	private TextField textField;

	private ValueTypeHandler<T> updateHandler;

	public EditableCell(ValueTypeHandler<T> handler) {

		setEditable(true);
		updateHandler = handler;
	}

	@Override
	public void startEdit() {

		if (updateHandler.isLocked(getItem())) {
			return;
		}

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

	private void createTextField() {
		textField = new TextField();
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnKeyReleased(new SeCatEventHandle<KeyEvent>() {

			@Override
			public void handleAction(KeyEvent t) throws Exception {

				if (t.getCode() == KeyCode.ENTER) {

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

				} else if (t.getCode() == KeyCode.ESCAPE) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							cancelEdit();

						}
					});

				}

			}
		});
	}
}