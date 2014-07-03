package de.hscoburg.evelin.secat.util.javafx;

import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class EditableStringTableCell<S, T> extends TableCell<S, T> {
	private TextField textField;

	private ValueListTypeHandler<S> updateHandler;

	private boolean firstEnter = true;

	public EditableStringTableCell(ValueListTypeHandler<S> handler) {

		setEditable(true);
		updateHandler = handler;
	}

	@Override
	public void startEdit() {

		if (updateHandler.isLocked(getTableView().getSelectionModel().getSelectedItem())) {
			return;
		}
		firstEnter = false;
		super.startEdit();

		if (textField == null) {
			createTextField();
		}
		textField.clear();
		setGraphic(textField);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		if (getItem() != null) {
			textField.setText(getItem().toString());
		}
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
			setText(item.toString());
		}
	}

	private void createTextField() {

		textField = new TextField();

		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnKeyReleased(new SeCatEventHandle<KeyEvent>() {

			private final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER);

			private final KeyCombination kb2 = new KeyCodeCombination(KeyCode.ESCAPE);

			@Override
			public void handleAction(KeyEvent t) throws Exception {

				if (kb.match(t) && !firstEnter) {
					if (getTableView().getSelectionModel().getSelectedItem() != null) {

						try {
							final S val = updateHandler.merge(getTableView().getSelectionModel().getSelectedItem(), textField.getText());

							boolean success = updateHandler.update(val);
							if (success) {
								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										commitEdit((T) textField.getText());
										cancelEdit();

									}
								});

							}
						} catch (IllegalArgumentException iae) {
							// Nothing
						}

					} else if (kb2.match(t)) {

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								cancelEdit();

							}
						});

					}

				}
				firstEnter = false;

			}
		});
	}
}