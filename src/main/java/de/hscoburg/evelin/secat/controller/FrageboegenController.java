package de.hscoburg.evelin.secat.controller;

import java.io.File;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.base.LayoutController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.BewertungModel;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ColumnHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.javafx.TableCellAction;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zum anzeigen und filtern der Frageboegen
 * 
 * @author zuch1000
 * 
 */
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

	@Autowired
	private BewertungModel bewertungsModel;

	/**
	 * Initlisiert die View mit Standardwerten
	 * 
	 * @param location
	 * @param resources
	 */
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

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(0), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getName());
			}
		});
		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(1), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				if (p.getValue().getEigenschaft() != null) {
					return new ReadOnlyObjectWrapper<String>(p.getValue().getEigenschaft().getName());
				} else {
					return new ReadOnlyObjectWrapper<String>("");
				}
			}
		});
		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(2), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getPerspektive().getName());
			}
		});
		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(3), new TableCellAction<Fragebogen, Node>() {

			@Override
			public ObservableValue<Node> call(CellDataFeatures<Fragebogen, Node> p) {
				if (p.getValue().getExportiert()) {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/apply.png", 16, 16, true, true)));
				} else {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
				}
			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(4), new TableCellAction<Fragebogen, String>() {

			@SuppressWarnings("deprecation")
			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				Lehrveranstaltung t = p.getValue().getLehrveranstaltung();

				return new ReadOnlyObjectWrapper<String>(t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " " + t.getDozent());

			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(5), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {

				return new ReadOnlyObjectWrapper<String>(SimpleDateFormat.getDateInstance().format(p.getValue().getErstellungsDatum()));

			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(6), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

			}
		});

		frageboegen.setRowFactory(new Callback<TableView<Fragebogen>, TableRow<Fragebogen>>() {

			public TableRow<Fragebogen> call(TableView<Fragebogen> treeTableView) {

				final TableRow<Fragebogen> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				final MenuItem editItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.edit"), new ImageView(new Image(
						"/image/icons/edit.png", 16, 16, true, true)));

				final MenuItem importItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.import"), new ImageView(
						new Image("/image/icons/up.png", 16, 16, true, true)));

				MenuItem exportItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.export"), new ImageView(new Image(
						"/image/icons/run.png", 16, 16, true, true)));

				final MenuItem exportCoreItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.exportCore"),
						new ImageView(new Image("/image/icons/run.png", 16, 16, true, true)));

				row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {
						if (frageboegen.getSelectionModel().getSelectedItem() != null) {
							editItem.setDisable(frageboegen.getSelectionModel().getSelectedItem().getExportiert());
							importItem.setDisable(!frageboegen.getSelectionModel().getSelectedItem().getExportiert());

							exportCoreItem.setDisable(frageboegen.getSelectionModel().getSelectedItem().getBewertungen() == null
									|| frageboegen.getSelectionModel().getSelectedItem().getBewertungen().size() == 0);

						}

					}
				});

				editItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private Stage stage;

					@Override
					public void handleAction(ActionEvent t) {
						stage = SpringFXMLLoader.getInstance().loadInNewScene(LayoutController.EDIT_FRAGEBOGEN_FXML);

					}

					@Override
					public void updateUI() {

						stage.show();
					}

				});

				importItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private File file;

					private int anzCVSRows = -1;

					@Override
					public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
						FileChooser fileChooser = new FileChooser();

						// Set extension filter
						FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(SeCatResourceBundle.getInstance().getString(
								"scene.filechooser.csvname"), "*.csv");
						fileChooser.getExtensionFilters().add(extFilter);

						fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

						// Show open file dialog
						file = fileChooser.showOpenDialog(getCurrentStage());

					}

					@Override
					public void handleAction(ActionEvent t) throws Exception {
						// TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						// if (treeTableController.getSelectedTreeItem().getValue().isHandlungsfeld()) {
						//

						anzCVSRows = bewertungsModel.importBewertungen(file);
						//

						//

					}

					@Override
					public void updateUI() {
						if (anzCVSRows != -1) {
							Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.import.title"))
									.masthead(SeCatResourceBundle.getInstance().getString("scene.import.text") + " " + anzCVSRows).showInformation();
						}
					}

				});

				exportItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private File file;

					private ObservableList<Fragebogen> tableData;

					@Override
					public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
						FileChooser fileChooser = new FileChooser();

						// Set extension filter
						FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(SeCatResourceBundle.getInstance().getString(
								"scene.filechooser.xmlname"), "*.xml");
						fileChooser.getExtensionFilters().add(extFilter);

						fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

						fileChooser.setInitialFileName(frageboegen.getSelectionModel().getSelectedItem().getName());

						// Show save file dialog
						file = fileChooser.showSaveDialog(getCurrentStage());
					}

					@Override
					public void handleAction(ActionEvent t) throws Exception {

						if (file != null) {
							fragebogenModel.exportFragebogenToQuestorPro(frageboegen.getSelectionModel().getSelectedItem(), file);

						}
						tableData = getFrageboegen();

					}

					@Override
					public void updateUI() {
						updateTable(tableData);
					}
				});

				exportCoreItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private File file;

					@Override
					public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
						FileChooser fileChooser = new FileChooser();

						// Set extension filter
						FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(SeCatResourceBundle.getInstance().getString(
								"scene.filechooser.xmlname"), "*.xml");
						fileChooser.getExtensionFilters().add(extFilter);

						fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

						fileChooser.setInitialFileName(frageboegen.getSelectionModel().getSelectedItem().getName());

						// Show save file dialog
						file = fileChooser.showSaveDialog(getCurrentStage());
					}

					@Override
					public void handleAction(ActionEvent t) throws Exception {

						fragebogenModel.exportQuestionarieToCore(frageboegen.getSelectionModel().getSelectedItem(), file);

						// exportController.exportFragebogen(frageboegen.getSelectionModel().getSelectedItem(), file);

						// tableData = getFrageboegen();

					}

					@Override
					public void updateUI() {
						// updateTable(tableData);
					}
				});

				rowMenu.getItems().add(editItem);
				rowMenu.getItems().add(exportItem);
				rowMenu.getItems().add(importItem);
				rowMenu.getItems().add(exportCoreItem);

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(rowMenu)
								.otherwise((ContextMenu) null));
				return row;

			}

		});

		// ActionHelper.setAutoResizeToggleListenerForTitledPanel(searchPanel, tablePanel, frageboegen);

		updateTable(getFrageboegen());

	}

	/**
	 * Gibt alle Frageboegen die den Selektionskriterien entsprechen zuerueck
	 * 
	 * @return {@link ObservableList} mit {@link Fragebogen}
	 */
	private ObservableList<Fragebogen> getFrageboegen() {
		return FXCollections.observableArrayList(fragebogenModel.getFragebogenFor(searchEigenschaft.getValue(), searchPerspektive.getValue(),
				searchLehrveransteltung.getValue(), searchName.getText(), searchFromDate.getValue(), searchToDate.getValue(), searchSkala.getValue()));

	}

	/**
	 * Setzt alle Frageboegen in der Tabelle neu
	 * 
	 * @param boegen
	 *            {@link ObservableList} mit den zu setzenden {@link Fragebogen}
	 */
	private void updateTable(ObservableList<Fragebogen> boegen) {

		if (frageboegen.getItems() != null) {
			frageboegen.getItems().clear();
		}
		frageboegen.setItems(boegen);
	}

	/**
	 * Resettet die FilterKriterien
	 */
	private void resetSearchBox() {

		searchName.setText("");
		searchEigenschaft.getSelectionModel().clearSelection();
		searchPerspektive.getSelectionModel().clearSelection();
		searchLehrveransteltung.getSelectionModel().clearSelection();
		searchSkala.getSelectionModel().clearSelection();

		searchFromDate.setValue(null);
		searchToDate.setValue(null);
	}

	/**
	 * Gibt den Key des Names der Scene zurueck
	 * 
	 * @return {@link String}
	 */
	@Override
	public String getKeyForSceneName() {
		return "scene.frageboegen.lable.title";
	}

	/**
	 * Gibt den aktuell selektierten Fragebogen zurueck.
	 * 
	 * @return {@link Fragebogen}
	 */
	public Fragebogen getSelectedFragebogen() {
		return frageboegen.getSelectionModel().getSelectedItem();

	}

}
