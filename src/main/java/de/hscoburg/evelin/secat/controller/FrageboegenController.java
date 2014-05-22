package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class FrageboegenController extends BaseController {

	@FXML
	private TitledPane searchPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private TextField searchName;
	@FXML
	private TableView<Fragebogen> frageboegen;

	@FXML
	private ComboBox<Perspektive> searchPerspektive;

	@FXML
	private ComboBox<Eigenschaft> searchEigenschaft;

	@FXML
	private ComboBox<Lehrveranstaltung> searchLehrveransteltung;

	@FXML
	private ComboBox<Skala> searchSkala;

	@FXML
	private Button search;

	@FXML
	private Button reset;
	@FXML
	private DatePicker searchFromDate;
	@FXML
	private DatePicker searchToDate;

	@Autowired
	private EigenschaftenModel eigenschaftenModel;

	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private LehrveranstaltungModel lehrveranstaltungModel;
	@Autowired
	private SkalenModel skalenModel;

	@Autowired
	private FragebogenModel fragebogenModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {
		searchName.requestFocus();
		searchEigenschaft.setConverter(ConverterHelper.getConverterForEigenschaft());
		searchPerspektive.setConverter(ConverterHelper.getConverterForPerspektive());
		searchLehrveransteltung.setConverter(ConverterHelper.getConverterForLehrveranstaltung());
		searchSkala.setConverter(ConverterHelper.getConverterForSkala());

		searchEigenschaft.setItems(FXCollections.observableArrayList(eigenschaftenModel.getEigenschaften()));
		searchEigenschaft.getItems().add(0, null);
		searchPerspektive.setItems(FXCollections.observableArrayList(perspektivenModel.getPerspektiven()));
		searchPerspektive.getItems().add(0, null);
		searchLehrveransteltung.setItems(FXCollections.observableArrayList(lehrveranstaltungModel.getLehrveranstaltung()));
		searchLehrveransteltung.getItems().add(0, null);
		searchSkala.setItems(FXCollections.observableArrayList(skalenModel.getSkalen()));
		searchSkala.getItems().add(0, null);

		search.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		reset.setGraphic(new ImageView(new Image("/image/icons/reload.png", 16, 16, true, true)));

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
				resetSearchBox();
			}

			ObservableList<Fragebogen> result;

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				result = getFrageboegen();
			}

			@Override
			public void updateUI() {
				updateTable(result);
			}
		}, reset);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			ObservableList<Fragebogen> result;

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				result = getFrageboegen();
			}

			@Override
			public void updateUI() {
				updateTable(result);
			}
		}, search);

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());

					}
				});

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getEigenschaft().getName());

					}
				});

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getPerspektive().getName());

					}
				});

		((TableColumn<Fragebogen, Node>) frageboegen.getColumns().get(3))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, Node>, ObservableValue<Node>>() {

					public ObservableValue<Node> call(CellDataFeatures<Fragebogen, Node> p) {
						if (p.getValue().getExportiert()) {
							return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/apply.png", 16, 16, true, true)));
						} else {
							return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
						}

					}
				});

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(4))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {

						Lehrveranstaltung t = p.getValue().getLehrveranstaltung();

						return new ReadOnlyObjectWrapper<String>(t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " "
								+ t.getDozent());

					}
				});

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(5))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(SimpleDateFormat.getDateInstance().format(p.getValue().getErstellungsDatum()));

					}
				});

		((TableColumn<Fragebogen, String>) frageboegen.getColumns().get(6))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

					}
				});

		frageboegen.setRowFactory(new Callback<TableView<Fragebogen>, TableRow<Fragebogen>>() {

			public TableRow<Fragebogen> call(TableView<Fragebogen> treeTableView) {

				final TableRow<Fragebogen> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				MenuItem editItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.edit"), new ImageView(new Image(
						"/image/icons/edit.png", 16, 16, true, true)));
				MenuItem exportItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.export"), new ImageView(new Image(
						"/image/icons/run.png", 16, 16, true, true)));

				editItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						// TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						// if (treeTableController.getSelectedTreeItem().getValue().isHandlungsfeld()) {
						//
						// Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addHandlungsfeld.fxml");
						//
						// stage.show();
						//
						// stage.setOnHidden(new EventHandler<WindowEvent>() {
						// public void handle(WindowEvent we) {
						// logger.debug("Closing dialog stage.");
						//
						// }
						// });
						// }
					}

				});

				exportItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						// TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						// if (selectedTreeItem.getValue().isHandlungsfeld() && selectedTreeItem.getValue().getHandlungsfeld().getId() !=
						// -1) {
						//
						// Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addBereich.fxml");
						//
						// stage.show();
						//
						// stage.setOnHidden(new EventHandler<WindowEvent>() {
						// public void handle(WindowEvent we) {
						// logger.debug("Closing dialog stage.");
						//
						// }
						// });

						// }
					}
				});

				rowMenu.getItems().add(editItem);
				rowMenu.getItems().add(exportItem);

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(rowMenu)
								.otherwise((ContextMenu) null));
				return row;

			}

		});

		ActionHelper.setToggleListenerForTitledPanelResize(searchPanel, tablePanel, frageboegen, 176);

		// searchPanel.expandedProperty().addListener(new ChangeListener<Boolean>() {
		//
		// private int HEIGHT_DIFF = 176;
		//
		// @Override
		// public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		// if (newValue) {
		// tablePanel.setLayoutY(235);
		// // tablePanel.setPrefHeight(205);
		// tablePanel.setPrefHeight(tablePanel.getPrefHeight() - HEIGHT_DIFF);
		// // frageboegen.setPrefHeight(181);
		// frageboegen.setPrefHeight(frageboegen.getPrefHeight() - HEIGHT_DIFF);
		// } else {
		// tablePanel.setLayoutY(60);
		// // tablePanel.setPrefHeight(380);
		// tablePanel.setPrefHeight(tablePanel.getPrefHeight() + HEIGHT_DIFF);
		// // frageboegen.setPrefHeight(356);
		// frageboegen.setPrefHeight(frageboegen.getPrefHeight() + HEIGHT_DIFF);
		// }
		//
		// }
		//
		// });

		updateTable(getFrageboegen());

	}

	private ObservableList<Fragebogen> getFrageboegen() {
		return FXCollections.observableArrayList(fragebogenModel.getFragebogenFor(searchEigenschaft.getValue(), searchPerspektive.getValue(),
				searchLehrveransteltung.getValue(), searchName.getText(), searchFromDate.getValue(), searchToDate.getValue(), searchSkala.getValue()));

	}

	private void updateTable(ObservableList<Fragebogen> boegen) {

		frageboegen.setItems(boegen);
	}

	private void resetSearchBox() {

		searchName.setText("");
		searchEigenschaft.getSelectionModel().clearSelection();
		searchPerspektive.getSelectionModel().clearSelection();
		searchLehrveransteltung.getSelectionModel().clearSelection();
		searchSkala.getSelectionModel().clearSelection();

		searchFromDate.setValue(null);
		searchToDate.setValue(null);
	}

	@Override
	public String getKeyForSceneName() {
		// TODO Auto-generated method stub
		return "scene.frageboegen.lable.title";
	}

}
