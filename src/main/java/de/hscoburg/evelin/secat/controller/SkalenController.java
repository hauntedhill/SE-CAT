package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ColumnHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.EditableStringTableCell;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.TableCellAction;
import de.hscoburg.evelin.secat.util.javafx.ValueListTypeHandler;

/**
 * Controller zur Anzeige der Skalen
 * 
 * @author zuch1000
 * 
 */
@Controller
public class SkalenController extends BaseController {

	@FXML
	private TitledPane addPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private TableView<Skala> tableSkalen;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameSkalen;

	@FXML
	private RadioButton discretQuestion;

	@FXML
	private RadioButton freeQuestion;

	@FXML
	private RadioButton multipleQuestion;

	@Autowired
	private SkalenModel skalenModel;

	@FXML
	private TextField textZeilen;

	@FXML
	private TextField textSchritte;
	@FXML
	private TextField textSchrittweite;
	@FXML
	private TextField textMinimal;
	@FXML
	private TextField textMaximal;
	@FXML
	private TextField textOptimum;

	@FXML
	private GridPane diskretePanel;

	@FXML
	private GridPane freePanel;

	@FXML
	private GridPane multiplePanel;

	@FXML
	private ListView<String> listKeys;
	@FXML
	private Button up;
	@FXML
	private Button down;
	@FXML
	private Button add;
	@FXML
	private Button del;

	@FXML
	private TextField textKey;

	@FXML
	private TextField textStandardAntwort;

	@FXML
	private TextField textSchrittweiteMC;

	@FXML
	private TextField textRefuse;

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		ToggleGroup group = new ToggleGroup();

		discretQuestion.setToggleGroup(group);
		freeQuestion.setToggleGroup(group);
		multipleQuestion.setToggleGroup(group);

		freeQuestion.setSelected(true);

		loadList();
		textNameSkalen.requestFocus();

		multipleQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				freePanel.setVisible(false);
				diskretePanel.setVisible(false);
				multiplePanel.setVisible(true);

			}
		});

		multipleQuestion.setOnKeyPressed(new SeCatEventHandle<Event>() {

			private boolean newSelection = false;

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
					newSelection = true;
				} else {
					newSelection = false;
				}

			}

			@Override
			public void updateUI() {
				if (newSelection) {
					multipleQuestion.setSelected(true);
					freePanel.setVisible(false);
					diskretePanel.setVisible(false);
					multiplePanel.setVisible(true);
				}
			}

		});

		discretQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				freePanel.setVisible(false);
				diskretePanel.setVisible(true);
				multiplePanel.setVisible(false);

			}
		});

		discretQuestion.setOnKeyPressed(new SeCatEventHandle<Event>() {

			private boolean newSelection = false;

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
					newSelection = true;
				} else {
					newSelection = false;
				}

			}

			@Override
			public void updateUI() {
				if (newSelection) {
					discretQuestion.setSelected(true);
					freePanel.setVisible(false);
					diskretePanel.setVisible(true);
					multiplePanel.setVisible(false);
				}
			}

		});

		freeQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				freePanel.setVisible(true);
				diskretePanel.setVisible(false);
				multiplePanel.setVisible(false);

			}
		});

		freeQuestion.setOnKeyPressed(new SeCatEventHandle<Event>() {

			private boolean newSelection = false;

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
					newSelection = true;
				} else {
					newSelection = false;
				}

			}

			@Override
			public void updateUI() {
				if (newSelection) {
					freeQuestion.setSelected(true);
					freePanel.setVisible(true);
					diskretePanel.setVisible(false);
					multiplePanel.setVisible(false);

				}
			}

		});

		tableSkalen.setEditable(true);

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(0), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getName() != null) {
					return new ReadOnlyObjectWrapper<String>(p.getValue().getName());
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(0)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setName(newValue);
						return value;
					}

					@Override
					public String getText(Skala value) {

						return value.getName();
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(1), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getType() != null) {
					return new ReadOnlyObjectWrapper<String>(p.getValue().getType().name());
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});
		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(2), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getZeilen() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getZeilen()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(2)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setZeilen(Integer.parseInt(newValue));
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getZeilen() != null) {
							return String.valueOf(value.getZeilen());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.FREE || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(3), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getSchritte() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getSchritte()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(3)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setSchritte(Integer.parseInt(newValue));
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getSchritte() != null) {
							return String.valueOf(value.getSchritte());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.DISCRET || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(4), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getSchrittWeite() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getSchrittWeite()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(4)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setSchrittWeite(Integer.parseInt(newValue));
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getSchrittWeite() != null) {
							return String.valueOf(value.getSchrittWeite());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return !(value.getType() == SkalaType.DISCRET || value.getType() == SkalaType.MC) || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(5), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getMinText() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getMinText()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(5)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setMinText(newValue);
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getMinText() != null) {
							return String.valueOf(value.getMinText());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.DISCRET || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(6), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getMaxText() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getMaxText()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(6)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setMaxText(newValue);
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getMaxText() != null) {
							return String.valueOf(value.getMaxText());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.DISCRET || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(7), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getOptimum() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getOptimum()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(7)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setOptimum(Integer.parseInt(newValue));
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getOptimum() != null) {
							return String.valueOf(value.getOptimum());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.DISCRET || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(8), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
				if (p.getValue().getVerweigerungsAntwort() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getVerweigerungsAntwort()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(8)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setVerweigerungsAntwort(newValue);
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getVerweigerungsAntwort() != null) {
							return String.valueOf(value.getVerweigerungsAntwort());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.MC || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(9), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

				if (p.getValue().getAndereAntwort() != null) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getAndereAntwort()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(9)).setCellFactory(new Callback<TableColumn<Skala, String>, TableCell<Skala, String>>() {

			@Override
			public TableCell<Skala, String> call(TableColumn<Skala, String> param) {
				// TODO Auto-generated method stub^

				return new EditableStringTableCell<Skala, String>(new ValueListTypeHandler<Skala>() {

					@Override
					public Skala merge(Skala value, String newValue) {
						value.setAndereAntwort(newValue);
						return value;
					}

					@Override
					public String getText(Skala value) {

						if (value.getAndereAntwort() != null) {
							return String.valueOf(value.getAndereAntwort());
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Skala value) {
						try {
							skalenModel.updateSkala(value);

							return true;
						} catch (IllegalArgumentException iae) {
							return false;
						}
					}

					@Override
					public boolean isLocked(Skala value) {
						// TODO Auto-generated method stub
						return value.getType() != SkalaType.MC || skalenModel.isLocked(value);
					}

				});
			}

		});

		ColumnHelper.setTableColumnCellFactory(tableSkalen.getColumns().get(10), new TableCellAction<Skala, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

				if (p.getValue().getAuswahl() != null && p.getValue().getAuswahl().size() > 0) {
					return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getAuswahl()));
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					skalenModel.saveSkala(freeQuestion.isSelected() ? SkalaType.FREE : discretQuestion.isSelected() ? SkalaType.DISCRET : SkalaType.MC,
							textNameSkalen.getText(), textZeilen.getText(), textSchritte.getText(), textSchrittweite.getText(), textMinimal.getText(),
							textMaximal.getText(), textOptimum.getText(), listKeys.getItems(), textSchrittweiteMC.getText(), textStandardAntwort.getText(),
							textRefuse.getText());
				} catch (NumberFormatException nfe) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							DialogHelper.showInputErrorDialog();
						}
					});
				}
			}

			@Override
			public void updateUI() {
				loadList();
			}
		}, buttonAdd, true);

		// buttonAdd.setOnAction(new SeCatEventHandle<ActionEvent>() {
		//
		// @Override
		// public void handleAction(ActionEvent event) throws Exception {
		//
		// try {
		//
		// skalenModel.saveSkala(freeQuestion.isSelected() ? SkalaType.FREE : discretQuestion.isSelected() ? SkalaType.DISCRET :
		// SkalaType.MC,
		// textNameSkalen.getText(), textZeilen.getText(), textSchritte.getText(), textSchrittweite.getText(), textMinimal.getText(),
		// textMaximal.getText(), textOptimum.getText(), listKeys.getItems(), textSchrittweiteMC.getText(), textStandardAntwort.getText(),
		// textRefuse.getText());
		// } catch (NumberFormatException nfe) {
		// Platform.runLater(new Runnable() {
		//
		// @Override
		// public void run() {
		// DialogHelper.showInputErrorDialog();
		// }
		// });
		// }
		// }
		//
		// @Override
		// public void updateUI() {
		// loadList();
		// }
		// });
		//
		// buttonAdd.setOnKeyPressed(new SeCatEventHandle<Event>() {
		//
		// @Override
		// public void handleAction(Event event) {
		// if (((KeyEvent) event).getCode() == KeyCode.ENTER)
		//
		// {
		// buttonAdd.fire();
		// }
		//
		// }
		//
		// @Override
		// public void updateUI() {
		// loadList();
		// }
		// });

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				if (!"".equals(textKey.getText()) && textKey.getText() != null) {
					listKeys.getItems().add(textKey.getText());
				}
			}
		}, add);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {

				String tmp = listKeys.getSelectionModel().getSelectedItem();
				int index = listKeys.getSelectionModel().getSelectedIndex() - 1;
				if (index >= 0) {
					listKeys.getItems().remove(tmp);
					listKeys.getItems().add(index, tmp);
					listKeys.getSelectionModel().select(tmp);

				}
			}
		}, up);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {

				String tmp = listKeys.getSelectionModel().getSelectedItem();
				int index = listKeys.getSelectionModel().getSelectedIndex();
				if (index >= 0 && index + 1 < listKeys.getItems().size()) {
					listKeys.getItems().remove(tmp);
					listKeys.getItems().add(index + 1, tmp);
					listKeys.getSelectionModel().select(tmp);

				}
			}
		}, down);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				listKeys.getItems().remove(listKeys.getSelectionModel().getSelectedItem());
			}
		}, del);
		loadList();

	}

	/**
	 * Laedt die Skalen aus der DB und setzt diese in der View
	 */
	private void loadList() {

		tableSkalen.setItems(FXCollections.observableList(skalenModel.getSkalen()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.skala.lable.title";
	}

}
