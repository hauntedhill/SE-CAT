package de.hscoburg.evelin.secat.util.javafx;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class EditableComboBoxTableCell<S, T, E> extends TableCell<S, T> {
	private ComboBox<E> comboBox;

	private ValueTableTypeHandler<S, E> updateHandler;

	private boolean firstEnter = true;

	private ObservableList<E> data;

	private StringConverter<E> converter;

	public EditableComboBoxTableCell(ValueTableTypeHandler<S, E> handler, ObservableList<E> data, StringConverter<E> converter) {

		this(handler, data);
		this.converter = converter;
	}

	public EditableComboBoxTableCell(ValueTableTypeHandler<S, E> handler, ObservableList<E> data) {

		setEditable(true);
		updateHandler = handler;
		this.data = data;
	}

	@Override
	public void startEdit() {

		if (updateHandler.isLocked(getTableView().getSelectionModel().getSelectedItem())) {
			return;
		}
		firstEnter = false;
		super.startEdit();

		if (comboBox == null) {
			createComboBox();
		}

		comboBox.getSelectionModel().select((E) updateHandler.getValue(getTableView().getSelectionModel().getSelectedItem()));

		comboBox.setValue((E) updateHandler.getValue(getTableView().getSelectionModel().getSelectedItem()));

		setGraphic(comboBox);

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		// if (getItem() != null) {
		// textField.setText(getItem().toString());
		// }
		comboBox.requestFocus();

	}

	@Override
	public void cancelEdit() {

		super.cancelEdit();
		setContentDisplay(ContentDisplay.TEXT_ONLY);
		comboBox = null;
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

	private void createComboBox() {

		comboBox = new ComboBox<>();
		comboBox.setItems(data);
		if (converter != null) {
			comboBox.setConverter(converter);
		}
		comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

		comboBox.setOnKeyReleased(new SeCatEventHandle<KeyEvent>() {

			@Override
			public void handleAction(KeyEvent t) throws Exception {

				if (t.getCode() == KeyCode.ENTER && !firstEnter) {
					if (getTableView().getSelectionModel().getSelectedItem() != null) {
						final S val = updateHandler.merge(getTableView().getSelectionModel().getSelectedItem(), comboBox.getValue());

						boolean success = updateHandler.update(val);
						if (success) {
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									commitEdit((T) updateHandler.getText(comboBox.getValue()));
									cancelEdit();
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
				firstEnter = false;

			}
		});

	}
}