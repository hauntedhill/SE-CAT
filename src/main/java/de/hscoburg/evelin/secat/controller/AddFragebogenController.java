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

import org.controlsfx.dialog.Dialogs;
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
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

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
	private ListView<Frage> fragenList;
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

		fragenList.setCellFactory(new Callback<ListView<Frage>, ListCell<Frage>>() {

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
		List<Fragebogen> fList = fragebogenModel.getFragebogenFor(null, null, null, null, null, null, null);
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
							Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.input.error.title"))
									.masthead(SeCatResourceBundle.getInstance().getString("scene.input.error.txt")).showError();

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

		saveFragebogen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private List<Item> currentItems = new ArrayList<>();

			private List<Frage> currentFragen = new ArrayList<>();

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) throws Exception {
				// TODO:
				/**
				 * Hinzugefuegt da Exception bei Zugriff aus einem No FX Thread, ich frage mich blos warum ....
				 */
				currentItems.addAll(itemList.getItems());
				currentFragen.addAll(fragenList.getItems());
			}

			@Override
			public void handleAction(ActionEvent event) throws Exception {

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

						// Fragebogen f = new Fragebogen();
						// fragebogenModel.persistFragebogen(f);

						fragebogenModel.addFragebogen(name.getText(), currentItems, perspektive.getValue(), eigenschaft.getValue(), skala.getValue(),
								lehrveranstaltung.getValue(), currentFragen, positionFrage.getValue());

						/*
						 * for (Frage frage : fragenList.getItems()) {
						 * 
						 * Frage_Fragebogen frageFragebogen = new Frage_Fragebogen(); frageFragebogen.setPosition(positionFrage.getValue());
						 * frageFragebogen.setFrage(frage); f.addFrage_Fragebogen(frageFragebogen); fragenModel.persist(frageFragebogen); }
						 * fragebogenModel.mergeFragebogen(f);
						 * 
						 * for (Item item : itemList.getItems()) { item.addFragebogen(f); handlungsfeldModel.mergeItem(item); }
						 */
					} else {

						fragebogenModel.editFragebogen(editFragebogen, name.getText(), currentItems, perspektive.getValue(), eigenschaft.getValue(),
								skala.getValue(), lehrveranstaltung.getValue(), currentFragen, fragenToRemove, itemsToRemove, positionFrage.getValue());
					}
				}
				/*
				 * editFragebogen.setName(name.getText()); editFragebogen.setItems(itemList.getItems());
				 * editFragebogen.setPerspektive(perspektive.getValue()); editFragebogen.setEigenschaft(eigenschaft.getValue());
				 * editFragebogen.setSkala(skala.getValue()); editFragebogen.setLehrveranstaltung(lehrveranstaltung.getValue());
				 * editFragebogen.setExportiert(false); editFragebogen.setErstellungsDatum(new Date());
				 * 
				 * ArrayList<Frage> fragenExist = new ArrayList<Frage>(); for (Frage_Fragebogen frage_fragebogen :
				 * editFragebogen.getCustomFragen()) { fragenExist.add(frage_fragebogen.getFrage()); }
				 * 
				 * for (Frage frage : fragenToRemove) { for (Frage_Fragebogen frage_fragebogen : editFragebogen.getCustomFragen()) { if
				 * (frage_fragebogen.getFrage().equals(frage)) { fragenExist.remove(frage); frage_fragebogen.setFragebogen(null);
				 * fragenModel.merge(frage_fragebogen); } } }
				 * 
				 * for (Frage frage : fragenList.getItems()) {
				 * 
				 * if (!fragenExist.contains(frage) || fragenExist.isEmpty()) { Frage_Fragebogen frageFragebogen = new Frage_Fragebogen();
				 * frageFragebogen.setPosition(positionFrage.getValue()); frageFragebogen.setFrage(frage);
				 * editFragebogen.addFrage_Fragebogen(frageFragebogen); fragenModel.persist(frageFragebogen);
				 * fragebogenModel.mergeFragebogen(editFragebogen); } else { for (Frage_Fragebogen frageFragebogen :
				 * editFragebogen.getCustomFragen()) { if (frageFragebogen.getFrage().equals(frage)) {
				 * frageFragebogen.setPosition(positionFrage.getValue()); fragenModel.merge(frageFragebogen); }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * } ArrayList<Fragebogen> itemFb = new ArrayList<Fragebogen>(); itemFb.add(editFragebogen);
				 * 
				 * for (Item item : itemList.getItems()) { if (!itemsToRemove.contains(item)) { item.setFrageboegen(itemFb);
				 * handlungsfeldModel.mergeItem(item); } }
				 * 
				 * for (Item item : itemsToRemove) { ArrayList<Fragebogen> fbs = new ArrayList<Fragebogen>(); for (Fragebogen f :
				 * item.getFrageboegen()) { if (!editFragebogen.equals(f)) { fbs.add(f); } item.setFrageboegen(fbs);
				 * handlungsfeldModel.mergeItem(item); }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * }
				 */catch (IllegalArgumentException iae) {
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
				ObservableList<Item> items = FXCollections.observableArrayList();
				ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
				// itemList.setItems(items);
				// frageList.setItems(fragenOl);
				// frageText.clear();
				fach.getSelectionModel().clearSelection();
				perspektive.getSelectionModel().clearSelection();
				eigenschaft.getSelectionModel().clearSelection();
				skala.getSelectionModel().clearSelection();
				lehrveranstaltung.getSelectionModel().clearSelection();
				name.setText("");

			}

		});

		vorlage.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				Fragebogen x = vorlage.getSelectionModel().getSelectedItem();
				ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
				ObservableList<Item> items = FXCollections.observableArrayList();

				if (x.getId() != -1) {

					fragenOl.clear();

					for (Item item : x.getItems()) {
						items.add(item);
					}

					for (Frage_Fragebogen frage : x.getFrageFragebogen()) {
						fragenOl.add(frage.getFrage());
						positionFrage.setValue(frage.getPosition());
					}

					perspektive.getSelectionModel().select(x.getPerspektive());
					eigenschaft.getSelectionModel().select(x.getEigenschaft());
					skala.getSelectionModel().select(x.getSkala());
					lehrveranstaltung.getSelectionModel().select(x.getLehrveranstaltung());
					name.setText(x.getName());
					itemList.setItems(items);
					fragenList.setItems(fragenOl);
				} else {
					itemList.setItems(items);
					fragenList.setItems(fragenOl);
					fach.getSelectionModel().clearSelection();
					perspektive.getSelectionModel().clearSelection();
					eigenschaft.getSelectionModel().clearSelection();
					skala.getSelectionModel().clearSelection();
					lehrveranstaltung.getSelectionModel().clearSelection();
					name.setText("");

				}
			}
		});
		;

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
				fragenOl = fragenList.getItems();
				for (Frage frage : tableViewFragen.getSelectionModel().getSelectedItems()) {
					if (!fragenOl.contains(frage)) {
						fragenOl.add(frage);
					}
					if (fragenToRemove.contains(frage)) {
						fragenToRemove.remove(frage);
					}
				}

				fragenList.setItems(fragenOl);

			}
		}, addFrage);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
				fragenOl = fragenList.getItems();
				fragenToRemove.add(fragenList.getSelectionModel().getSelectedItem());
				fragenOl.remove(fragenList.getSelectionModel().getSelectedItem());

				fragenList.setItems(fragenOl);

			}
		}, removeFrage);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addFragebogen.lable.title";
	}

	public void filterTreeTable() {

		treeTableController.buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(true, true), true, true, selectedPerspektive, selectedEigenschaft,
				null, null, null);
	}

	public void setFragebogenToEdit(Fragebogen f) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		ObservableList<Frage> fragenOl = FXCollections.observableArrayList();
		editMode = true;
		editFragebogen = f;
		for (Item item : f.getItems()) {
			items.add(item);
		}

		for (Frage_Fragebogen frage : f.getFrageFragebogen()) {
			fragenOl.add(frage.getFrage());
			positionFrage.setValue(frage.getPosition());

		}
		fragenList.setItems(fragenOl);
		perspektive.getSelectionModel().select(f.getPerspektive());
		eigenschaft.getSelectionModel().select(f.getEigenschaft());
		skala.getSelectionModel().select(f.getSkala());
		lehrveranstaltung.getSelectionModel().select(f.getLehrveranstaltung());
		name.setText(f.getName());
		itemList.setItems(items);
	}

}
