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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

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

		// listSkalen.setCellFactory(new Callback<ListView<Skala>, ListCell<Skala>>() {
		//
		// @Override
		// public ListCell<Skala> call(ListView<Skala> p) {
		//
		// ListCell<Skala> cell = new ListCell<Skala>() {
		//
		// @Override
		// protected void updateItem(Skala t, boolean bln) {
		// super.updateItem(t, bln);
		// if (t != null) {
		// setText(t.getName());
		// }
		// }
		//
		// };
		//
		// return cell;
		// }
		// });

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {
						if (p.getValue().getName() != null) {
							return new ReadOnlyObjectWrapper<String>(p.getValue().getName());
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getType() != null) {
							return new ReadOnlyObjectWrapper<String>(p.getValue().getType().name());
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getRows() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getRows()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(3))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getSteps() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getSteps()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(4))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getWeight() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getWeight()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(5))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getMinText() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getMinText()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(6))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getMaxText() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getMaxText()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(7))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getOptimum() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getOptimum()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(8))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getRefuseAnswer() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getRefuseAnswer()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(9))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getOtherAnswer() != null) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getOtherAnswer()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		((TableColumn<Skala, String>) tableSkalen.getColumns().get(10))
				.setCellValueFactory(new Callback<CellDataFeatures<Skala, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Skala, String> p) {

						if (p.getValue().getChoices() != null && p.getValue().getChoices().size() > 0) {
							return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getChoices()));
						} else {
							return new ReadOnlyObjectWrapper<String>("");
						}

					}
				});

		buttonAdd.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));

		buttonAdd.setOnAction(new SeCatEventHandle<ActionEvent>() {

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
							Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.input.error.title"))
									.masthead(SeCatResourceBundle.getInstance().getString("scene.input.error.txt")).showError();

						}
					});
				}
			}

			@Override
			public void updateUI() {
				loadList();
			}
		});

		buttonAdd.setOnKeyPressed(new SeCatEventHandle<Event>() {

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER)

				{
					buttonAdd.fire();
				}

			}

			@Override
			public void updateUI() {
				loadList();
			}
		});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				listKeys.getItems().add(textKey.getText());
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
		buttonAdd.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		add.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		del.setGraphic(new ImageView(new Image("/image/icons/edit_remove.png", 16, 16, true, true)));
		up.setGraphic(new ImageView(new Image("/image/icons/1uparrow.png", 16, 16, true, true)));
		down.setGraphic(new ImageView(new Image("/image/icons/1downarrow.png", 16, 16, true, true)));
		loadList();

	}

	private void loadList() {

		tableSkalen.setItems(FXCollections.observableList(skalenModel.getSkalen()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.skala.lable.title";
	}

}
