package de.hscoburg.evelin.secat.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
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

	@FXML
	private CheckBox searchArchiviert;
	//
	// @FXML
	// private ContextMenu ctxMenue;

	// @FXML
	// private MenuItem ctxMenueEdit;
	// @FXML
	// private MenuItem ctxMenueImport;
	// @FXML
	// private MenuItem ctxMenueExportQuestorPro;
	// @FXML
	// private MenuItem ctxMenueExportCore;
	//
	// @FXML
	// private MenuItem ctxMenueDeleteBewertung;
	//
	// @FXML
	// private MenuItem ctxMenueDelete;
	//
	// @FXML
	// private MenuItem ctxMenueArchive;

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
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
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

		searchArchiviert.setOnKeyReleased(new EventHandler<KeyEvent>() {

			private final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER);

			@Override
			public void handle(KeyEvent event) {
				if (kb.match(event)) {
					searchArchiviert.setSelected(!searchArchiviert.isSelected());
					event.consume();

				}

			}

		});

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
		}, search, true);

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
				if (p.getValue() != null && p.getValue().getExportiertQuestorPro() != null && p.getValue().getExportiertQuestorPro()) {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/apply.png", 16, 16, true, true)));
				} else {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
				}
			}
		});
		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(4), new TableCellAction<Fragebogen, Node>() {

			@Override
			public ObservableValue<Node> call(CellDataFeatures<Fragebogen, Node> p) {
				if (p.getValue() != null && p.getValue().getExportiertCore() != null && p.getValue().getExportiertCore()) {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/apply.png", 16, 16, true, true)));
				} else {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
				}
			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(5), new TableCellAction<Fragebogen, String>() {

			@SuppressWarnings("deprecation")
			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				Lehrveranstaltung t = p.getValue().getLehrveranstaltung();

				return new ReadOnlyObjectWrapper<String>(t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " " + t.getDozent());

			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(6), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {

				return new ReadOnlyObjectWrapper<String>(SimpleDateFormat.getDateInstance().format(p.getValue().getErstellungsDatum()));

			}
		});

		ColumnHelper.setTableColumnCellFactory(frageboegen.getColumns().get(7), new TableCellAction<Fragebogen, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

			}
		});

		frageboegen.setRowFactory(new Callback<TableView<Fragebogen>, TableRow<Fragebogen>>() {

			public TableRow<Fragebogen> call(TableView<Fragebogen> treeTableView) {

				final TableRow<Fragebogen> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				final MenuItem ctxMenueEdit = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.edit"), new ImageView(
						new Image("/image/icons/edit.png", 16, 16, true, true)));

				final MenuItem ctxMenueImport = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.import"), new ImageView(
						new Image("/image/icons/up.png", 16, 16, true, true)));

				MenuItem ctxMenueExportQuestorPro = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.export"),
						new ImageView(new Image("/image/icons/run.png", 16, 16, true, true)));

				final MenuItem ctxMenueExportCore = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.exportCore"),
						new ImageView(new Image("/image/icons/run.png", 16, 16, true, true)));

				final MenuItem ctxMenueDelete = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.delete"), new ImageView(
						new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

				final MenuItem ctxMenueDeleteBewertung = new MenuItem(
						SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.deleteBewertung"), new ImageView(new Image(
								"/image/icons/button_cancel.png", 16, 16, true, true)));

				final MenuItem ctxMenueArchive = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.frageboegen.ctxmenue.archive"), new ImageView(
						new Image("/image/icons/reload.png", 16, 16, true, true)));

				row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {
						if (frageboegen.getSelectionModel().getSelectedItem() != null) {

							if (frageboegen.getSelectionModel().getSelectedItem().getExportiertQuestorPro() != null) {
								ctxMenueEdit.setDisable(frageboegen.getSelectionModel().getSelectedItem().getExportiertQuestorPro());
								ctxMenueDelete.setDisable(frageboegen.getSelectionModel().getSelectedItem().getExportiertQuestorPro());
								ctxMenueImport.setDisable(!frageboegen.getSelectionModel().getSelectedItem().getExportiertQuestorPro());
							}

							ctxMenueExportCore.setDisable(frageboegen.getSelectionModel().getSelectedItem().getBewertungen() == null
									|| frageboegen.getSelectionModel().getSelectedItem().getBewertungen().size() == 0);

							ctxMenueDeleteBewertung.setDisable(frageboegen.getSelectionModel().getSelectedItem().getBewertungen() == null
									|| frageboegen.getSelectionModel().getSelectedItem().getBewertungen().size() == 0);

						}

					}
				});

				ctxMenueArchive.setOnAction(new SeCatEventHandle<ActionEvent>() {
					private ObservableList<Fragebogen> tableData;

					@Override
					public void handleAction(ActionEvent t) {

						fragebogenModel.toggleArchiviert(getSelectedFragebogen());
						tableData = getFrageboegen();

					}

					@Override
					public void updateUI() {
						updateTable(tableData);
					}

				});

				ctxMenueDeleteBewertung.setOnAction(new SeCatEventHandle<ActionEvent>() {
					private ObservableList<Fragebogen> tableData;

					@Override
					public void handleAction(ActionEvent t) {

						bewertungsModel.deleteBewertung(getSelectedFragebogen());
						tableData = getFrageboegen();

					}

					@Override
					public void updateUI() {
						updateTable(tableData);
					}

				});

				ctxMenueDelete.setOnAction(new SeCatEventHandle<ActionEvent>() {
					private ObservableList<Fragebogen> tableData;

					@Override
					public void handleAction(ActionEvent t) {

						fragebogenModel.deleteFragebogen(getSelectedFragebogen());
						tableData = getFrageboegen();
					}

					@Override
					public void updateUI() {
						updateTable(tableData);
					}

				});

				ctxMenueEdit.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private Stage stage;

					@Override
					public void handleAction(ActionEvent t) {

					}

					@Override
					public void updateUI() {
						stage = SpringFXMLLoader.getInstance().loadInNewScene(LayoutController.EDIT_FRAGEBOGEN_FXML);
						stage.show();
					}

				});

				ctxMenueImport.setOnAction(new SeCatEventHandle<ActionEvent>() {
					private ObservableList<Fragebogen> tableData;
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
						if (file != null) {
							anzCVSRows = bewertungsModel.importBewertungen(file);
						}
						tableData = getFrageboegen();
						//

						//

					}

					@Override
					public void updateUI() {
						if (anzCVSRows != -1) {

							DialogHelper.showInformationDialog(SeCatResourceBundle.getInstance().getString("scene.import.title"), SeCatResourceBundle
									.getInstance().getString("scene.import.text") + " " + anzCVSRows);

						}
						updateTable(tableData);
					}

				});

				ctxMenueExportQuestorPro.setOnAction(new SeCatEventHandle<ActionEvent>() {

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

				ctxMenueExportCore.setOnAction(new SeCatEventHandle<ActionEvent>() {

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

						try {
							if (file != null) {
								fragebogenModel.exportQuestionarieToCore(frageboegen.getSelectionModel().getSelectedItem(), file);
							}
						} catch (IllegalArgumentException iae) {
							Platform.runLater(new Runnable() {

								@Override
								public void run() {

									DialogHelper.showErrorDialog("scene.exportCore.error.title", "scene.exportCore.error.text", null);

								}
							});

						}
						// exportController.exportFragebogen(frageboegen.getSelectionModel().getSelectedItem(), file);

						tableData = getFrageboegen();

					}

					@Override
					public void updateUI() {
						updateTable(tableData);
					}
				});

				rowMenu.getItems().add(ctxMenueEdit);
				rowMenu.getItems().add(ctxMenueDelete);
				rowMenu.getItems().add(ctxMenueDeleteBewertung);
				rowMenu.getItems().add(ctxMenueArchive);
				rowMenu.getItems().add(ctxMenueExportQuestorPro);
				rowMenu.getItems().add(ctxMenueExportCore);
				rowMenu.getItems().add(ctxMenueImport);

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
				searchLehrveransteltung.getValue(), searchName.getText(), searchFromDate.getValue(), searchToDate.getValue(), searchSkala.getValue(),
				searchArchiviert.isSelected()));

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
		searchArchiviert.setSelected(false);
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
