package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.helper.TreeItemWrapper;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.FragenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

/**
 * Controller zur Erstellung von Fragebögen
 * 
 * @author moro1000
 * 
 */
@Controller
public class AddFragebogenController extends BaseController {

	@FXML
	private Button addItem;
	@FXML
	private Button removeItem;
	@FXML
	private Button addFrage;
	@FXML
	private Button removeFrage;
	@FXML
	private Button saveFragebogen;
	@FXML
	private TextField name;
	@FXML
	private ListView<Item> itemList;
	@FXML
	private ListView<Frage> fragenListTop;
	@FXML
	private ListView<Frage> fragenListBottom;
	@FXML
	private ComboBox<Fragebogen> vorlage;
	@FXML
	private ComboBox<Perspektive> perspektive;
	@FXML
	// private ComboBox<Skala> skalaFrage;
	// @FXML
	private ComboBox<FragePosition> positionFrage;
	@FXML
	private ComboBox<Eigenschaft> eigenschaft;
	@FXML
	private ComboBox<Skala> skala;
	@FXML
	private ComboBox<Fach> fach;
	@FXML
	private ComboBox<Lehrveranstaltung> lehrveranstaltung;
	@FXML
	private TableView<Frage> tableViewFragen;

	@Autowired
	private TreeTableController treeTableController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;
	@Autowired
	private LehrveranstaltungModel lehrveranstaltungModel;
	@Autowired
	private FachModel fachModel;
	@Autowired
	private FragebogenModel fragebogenModel;
	@Autowired
	private FragenModel fragenModel;
	@Autowired
	private EigenschaftenModel eigenschaftenModel;
	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private SkalenModel skalenModel;

	private static boolean editMode = false;
	private static Fragebogen editFragebogen;
	private static Perspektive selectedPerspektive = null;
	private static Eigenschaft selectedEigenschaft = null;
	private static ArrayList<Item> itemsToRemove = new ArrayList<Item>();
	private static ArrayList<Frage> fragenToRemove = new ArrayList<Frage>();

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		treeTableController.setSelectionMode(SelectionMode.MULTIPLE);

		addItem.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		removeItem.setGraphic(new ImageView(new Image("/image/icons/edit_remove.png", 16, 16, true, true)));
		addFrage.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		removeFrage.setGraphic(new ImageView(new Image("/image/icons/edit_remove.png", 16, 16, true, true)));

		perspektive.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.perspective"));
		eigenschaft.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.property"));
		fach.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.fachlable"));
		skala.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.scale"));
		// skalaFrage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.scale"));
		lehrveranstaltung.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.lable"));
		// positionFrage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.frageboegen.frage.label.position"));
		vorlage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.addItem.templatebox.prompttextproperty"));

		((TableColumn<Frage, String>) tableViewFragen.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());

					}
				});

		((TableColumn<Frage, String>) tableViewFragen.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

					}
				});

		((TableColumn<Frage, String>) tableViewFragen.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getText());

					}
				});

		ObservableList<Frage> tableFragenOl = FXCollections.observableArrayList();
		List<Frage> frList = fragenModel.getFragen();
		for (Frage f : frList) {
			tableFragenOl.add(f);
		}

		tableViewFragen.setItems(tableFragenOl);
		tableViewFragen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		perspektive.setConverter(ConverterHelper.getConverterForPerspektive());

		ObservableList<Perspektive> perspektiveOl = FXCollections.observableArrayList();
		List<Perspektive> pList = perspektivenModel.getPerspektiven();
		for (Perspektive p : pList) {
			perspektiveOl.add(p);
		}
		perspektive.setItems(perspektiveOl);

		eigenschaft.setConverter(ConverterHelper.getConverterForEigenschaft());

		ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();
		List<Eigenschaft> eList = eigenschaftenModel.getEigenschaften();
		for (Eigenschaft e : eList) {
			eigenschaftOl.add(e);
		}
		eigenschaft.setItems(eigenschaftOl);

		itemList.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

			@Override
			public ListCell<Item> call(ListView<Item> i) {

				ListCell<Item> cell = new ListCell<Item>() {

					@Override
					protected void updateItem(Item i, boolean bln) {
						super.updateItem(i, bln);
						if (i != null) {
							setText(i.getName());
						}
						if (i == null) {
							setText("");
						}

					}

				};

				return cell;
			}
		});

		fragenListTop.setCellFactory(new Callback<ListView<Frage>, ListCell<Frage>>() {

			@Override
			public ListCell<Frage> call(ListView<Frage> f) {

				ListCell<Frage> cell = new ListCell<Frage>() {

					@Override
					protected void updateItem(Frage f, boolean bln) {
						super.updateItem(f, bln);
						if (f != null) {
							setText(f.getName());
						}
						if (f == null) {
							setText("");
						}

					}

				};

				return cell;
			}
		});

		fragenListBottom.setCellFactory(new Callback<ListView<Frage>, ListCell<Frage>>() {

			@Override
			public ListCell<Frage> call(ListView<Frage> f) {

				ListCell<Frage> cell = new ListCell<Frage>() {

					@Override
					protected void updateItem(Frage f, boolean bln) {
						super.updateItem(f, bln);
						if (f != null) {
							setText(f.getName());
						}
						if (f == null) {
							setText("");
						}

					}

				};

				return cell;
			}
		});

		skala.setConverter(ConverterHelper.getConverterForSkala());

		ObservableList<Skala> skalaOl = FXCollections.observableArrayList();
		List<Skala> sList = skalenModel.getSkalen();
		for (Skala s : sList) {
			skalaOl.add(s);
		}
		skala.setItems(skalaOl);

		fach.setConverter(new StringConverter<Fach>() {
			@Override
			public String toString(Fach object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Fach fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});
		ObservableList<Fach> fachOl = FXCollections.observableArrayList();
		List<Fach> faecher = fachModel.getFaecher();
		for (Fach f : faecher) {
			fachOl.add(f);
		}
		fach.setItems(fachOl);

		lehrveranstaltung.setConverter(ConverterHelper.getConverterForLehrveranstaltung());

		vorlage.setConverter(ConverterHelper.getConverterForFragebogen());

		ObservableList<Fragebogen> vorlageOl = FXCollections.observableArrayList();
		Fragebogen keinFragebogen = new Fragebogen();
		keinFragebogen.setName("Keine Vorlage");
		keinFragebogen.setId(-1);
		vorlageOl.add(keinFragebogen);
		List<Fragebogen> fList = fragebogenModel.getFragebogenFor(null, null, null, null, null, null, null, false);
		for (Fragebogen f : fList) {
			vorlageOl.add(f);
		}
		vorlage.setItems(vorlageOl);

		ObservableList<FragePosition> fragePosOl = FXCollections.observableArrayList();
		fragePosOl.add(FragePosition.TOP);
		fragePosOl.add(FragePosition.BOTTOM);
		positionFrage.setItems(fragePosOl);
		positionFrage.getSelectionModel().select(0);

		addItem.setOnAction(new SeCatEventHandle<ActionEvent>() {
			boolean addItemConflict = false;

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					ObservableList<TreeItem<TreeItemWrapper>> treeItems = treeTableController.getSelectedTreeItemList();
					for (TreeItem<TreeItemWrapper> child : treeItems) {
						if (!child.getValue().getPerspektiven().contains(selectedPerspektive)) {
							addItemConflict = true;
							throw new IllegalArgumentException();
						}

					}
				} catch (IllegalArgumentException iae) {
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
				if (addItemConflict == false) {
					ObservableList<Item> items = itemList.getItems();
					ObservableList<TreeItem<TreeItemWrapper>> treeItems = treeTableController.getSelectedTreeItemList();

					for (TreeItem<TreeItemWrapper> child : treeItems) {
						if (child.getValue().isItem() && !itemList.getItems().contains(child.getValue().getItem())) {
							items.add(child.getValue().getItem());
							if (itemsToRemove.contains(child.getValue().getItem())) {
								itemsToRemove.remove(child.getValue().getItem());
							}
						}
					}
					itemList.setItems(items);
				} else {
					addItemConflict = false;
				}
			}
		});

		removeItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				itemsToRemove.clear();
				itemsToRemove.add(itemList.getSelectionModel().getSelectedItem());
			}

			@Override
			public void updateUI() {
				ObservableList<Item> items = itemList.getItems();
				items.removeAll(itemsToRemove);
				itemList.setItems(items);

			}
		});

		perspektive.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				selectedPerspektive = perspektive.getValue();

			}

			@Override
			public void updateUI() {
				filterTreeTable();
			}

		});

		eigenschaft.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				selectedEigenschaft = eigenschaft.getValue();

			}

			@Override
			public void updateUI() {
				filterTreeTable();
			}
		});

		fach.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {

				if (fach.getValue() != null) {
					ObservableList<Lehrveranstaltung> lvOl = FXCollections.observableArrayList();
					lvOl.setAll(fach.getValue().getLehrveranstaltungen());
					lehrveranstaltung.setItems(lvOl);
				}
			}
		});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			private List<Item> currentItems = new ArrayList<>();

			private List<Frage> currentFragenTop = new ArrayList<>();

			private List<Frage> currentFragenBottom = new ArrayList<>();

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
				// TODO:
				/**
				 * Hinzugefuegt da Exception bei Zugriff aus einem No FX Thread, ich frage mich blos warum ....
				 */
				currentItems.addAll(itemList.getItems());
				currentFragenTop.addAll(fragenListTop.getItems());
				currentFragenBottom.addAll(fragenListBottom.getItems());

			}

			@Override
			public void handleAction(ActionEvent event) throws IllegalArgumentException {

				try {
					if (name.getText().equals("") || selectedPerspektive == null || skala.getValue() == null || lehrveranstaltung.getValue() == null
							|| currentItems.isEmpty() || positionFrage == null)
						throw new IllegalArgumentException();
					for (Item item : currentItems) {
						if (!item.getPerspektiven().contains(selectedPerspektive)) {
							throw new IllegalArgumentException();
						}
					}

					if (editMode == false) {

						fragebogenModel.addFragebogen(name.getText(), currentItems, perspektive.getValue(), eigenschaft.getValue(), skala.getValue(),
								lehrveranstaltung.getValue(), currentFragenTop, currentFragenBottom);

					} else {

						fragebogenModel.editFragebogen(editFragebogen, name.getText(), currentItems, perspektive.getValue(), eigenschaft.getValue(),
								skala.getValue(), lehrveranstaltung.getValue(), currentFragenTop, currentFragenBottom, fragenToRemove, itemsToRemove);
					}
				} catch (IllegalArgumentException iae) {
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
				ObservableList<Item> items = FXCollections.observableArrayList();
				ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
				itemList.getItems().clear();
				fragenListTop.getItems().clear();
				fragenListBottom.getItems().clear();
				fach.getSelectionModel().clearSelection();
				perspektive.getSelectionModel().clearSelection();
				eigenschaft.getSelectionModel().clearSelection();
				skala.getSelectionModel().clearSelection();
				lehrveranstaltung.getSelectionModel().clearSelection();
				name.setText("");

			}

		}, saveFragebogen, true);

		vorlage.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				Fragebogen vorlageFragebogen = vorlage.getSelectionModel().getSelectedItem();
				ObservableList<Frage> fragenOlTop = FXCollections.observableArrayList();
				ObservableList<Frage> fragenOlBottom = FXCollections.observableArrayList();
				ObservableList<Item> items = FXCollections.observableArrayList();

				if (vorlageFragebogen.getId() != -1) {

					fragenOlTop.clear();
					fragenOlBottom.clear();

					for (Item item : vorlageFragebogen.getItems()) {
						items.add(item);
					}

					for (Frage_Fragebogen frage : vorlageFragebogen.getFrageFragebogen()) {
						if (frage.getPosition().equals(FragePosition.TOP)) {
							fragenOlTop.add(frage.getFrage());
						} else {
							fragenOlBottom.add(frage.getFrage());
						}

					}

					perspektive.getSelectionModel().select(vorlageFragebogen.getPerspektive());
					eigenschaft.getSelectionModel().select(vorlageFragebogen.getEigenschaft());
					skala.getSelectionModel().select(vorlageFragebogen.getSkala());
					lehrveranstaltung.getSelectionModel().select(vorlageFragebogen.getLehrveranstaltung());
					name.setText(vorlageFragebogen.getName());
					itemList.setItems(items);
					fragenListTop.setItems(fragenOlTop);
					fragenListBottom.setItems(fragenOlBottom);
				} else {
					itemList.setItems(items);
					fragenListTop.setItems(fragenOlTop);
					fragenListBottom.setItems(fragenOlBottom);
					fach.getSelectionModel().clearSelection();
					perspektive.getSelectionModel().clearSelection();
					eigenschaft.getSelectionModel().clearSelection();
					skala.getSelectionModel().clearSelection();
					lehrveranstaltung.getSelectionModel().clearSelection();
					name.setText("");

				}
			}
		});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				ObservableList<Frage> fragenOlTop = FXCollections.observableArrayList();
				ObservableList<Frage> fragenOlBottom = FXCollections.observableArrayList();
				fragenOlTop = fragenListTop.getItems();
				fragenOlBottom = fragenListBottom.getItems();
				for (Frage frage : tableViewFragen.getSelectionModel().getSelectedItems()) {
					if (!fragenOlTop.contains(frage) && !fragenOlBottom.contains(frage)) {
						if (positionFrage.getValue().equals(FragePosition.TOP)) {
							fragenOlTop.add(frage);
						} else {
							fragenOlBottom.add(frage);
						}
					}

				}

				fragenListTop.setItems(fragenOlTop);
				fragenListBottom.setItems(fragenOlBottom);

			}
		}, addFrage);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				ObservableList<Frage> fragenOlTop = FXCollections.observableArrayList();
				ObservableList<Frage> fragenOlBottom = FXCollections.observableArrayList();
				fragenOlTop = fragenListTop.getItems();
				fragenOlBottom = fragenListBottom.getItems();

				if (fragenListTop.getSelectionModel().getSelectedItem() != null) {
					fragenOlTop.remove(fragenListTop.getSelectionModel().getSelectedItem());
					fragenToRemove.add(fragenListTop.getSelectionModel().getSelectedItem());
					fragenListTop.getSelectionModel().clearSelection();
				} else if (fragenListBottom.getSelectionModel().getSelectedItem() != null) {
					fragenOlBottom.remove(fragenListBottom.getSelectionModel().getSelectedItem());
					fragenToRemove.add(fragenListBottom.getSelectionModel().getSelectedItem());
					fragenListBottom.getSelectionModel().clearSelection();
				}

				fragenListTop.setItems(fragenOlTop);
				fragenListBottom.setItems(fragenOlBottom);

			}
		}, removeFrage);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addFragebogen.lable.title";
	}

	/**
	 * Filtert die Daten in der Treetableview
	 */
	public void filterTreeTable() {

		treeTableController.buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(true, true), true, true, selectedPerspektive, selectedEigenschaft,
				null, null, null);
	}

	/**
	 * Methode setzt den Fragebogen der editiert werden soll und initialisiert die Listen.
	 * 
	 * @param f
	 *            {@link Fragebogen} - der zu editierende Fragebogen
	 */
	public void setFragebogenToEdit(Fragebogen f) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		ObservableList<Frage> fragenOlTop = FXCollections.observableArrayList();
		ObservableList<Frage> fragenOlBottom = FXCollections.observableArrayList();
		editMode = true;
		editFragebogen = f;
		for (Item item : f.getItems()) {
			items.add(item);
		}

		for (Frage_Fragebogen frage : f.getFrageFragebogen()) {
			if (frage.getPosition().equals(FragePosition.TOP)) {
				fragenOlTop.add(frage.getFrage());
			} else {
				fragenOlBottom.add(frage.getFrage());
			}

		}
		fragenListTop.setItems(fragenOlTop);
		fragenListBottom.setItems(fragenOlBottom);
		perspektive.getSelectionModel().select(f.getPerspektive());
		eigenschaft.getSelectionModel().select(f.getEigenschaft());
		skala.getSelectionModel().select(f.getSkala());
		lehrveranstaltung.getSelectionModel().select(f.getLehrveranstaltung());
		name.setText(f.getName());
		itemList.setItems(items);
	}

}
