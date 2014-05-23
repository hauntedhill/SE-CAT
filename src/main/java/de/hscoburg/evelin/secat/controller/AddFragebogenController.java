package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class AddFragebogenController extends BaseController {

	
	private static Logger logger = LoggerFactory.getLogger(AddFragebogenController.class);

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

	@FXML
	private TextField name;
	@FXML
	private TextArea frageText;
	@FXML
	private ListView<Item> itemList;
	@FXML
	private ListView<Frage> frageList;
	@FXML
	private ComboBox<Fragebogen> vorlage;
	@FXML
	private ComboBox<Perspektive> perspektive;
	@FXML
	private ComboBox<Skala> skalaFrage;
	@FXML
	private ComboBox<String> positionFrage;
	@FXML
	private ComboBox<Eigenschaft> eigenschaft;
	@FXML
	private ComboBox<Skala> skala;
	@FXML
	private ComboBox<Fach> fach;
	@FXML
	private ComboBox<Lehrveranstaltung> lehrveranstaltung;

	private static boolean editMode = false;

	private static Fragebogen editFragebogen;

	private static ObservableList<Fach> fachOl = FXCollections.observableArrayList();

	private static ObservableList<Item> items = FXCollections.observableArrayList();

	private static ObservableList<Lehrveranstaltung> lvOl = FXCollections.observableArrayList();

	private static ObservableList<Frage> fragenOl = FXCollections.observableArrayList();

	private static Perspektive selectedPerspektive = null;

	private static Eigenschaft selectedEigenschaft = null;

	private static ArrayList<Item> itemsToRemove = new ArrayList<Item>();

	private static ArrayList<Frage> fragenToRemove = new ArrayList<Frage>();

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		treeTableController.setSelectionMode(SelectionMode.MULTIPLE);

		// menuItemFilterItem.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		// menuItemFilterOff.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));

		addItem.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		removeItem.setGraphic(new ImageView(new Image("/image/icons/edit_remove.png", 16, 16, true, true)));

		itemList.setItems(items);
		frageList.setItems(fragenOl);

		perspektive.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.perspective"));
		eigenschaft.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.property"));
		fach.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.fachlable"));
		skala.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.scale"));
		skalaFrage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.scale"));
		lehrveranstaltung.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.lable"));
		positionFrage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.frageboegen.frage.label.position"));
		vorlage.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.addItem.templatebox.prompttextproperty"));


		perspektive.setConverter(new StringConverter<Perspektive>() {
			@Override
			public String toString(Perspektive object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Perspektive fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		ObservableList<Perspektive> perspektiveOl = FXCollections.observableArrayList();
		List<Perspektive> pList = handlungsfeldModel.getPerspektiven();
		for (Perspektive p : pList) {
			perspektiveOl.add(p);
		}
		perspektive.setItems(perspektiveOl);



		eigenschaft.setConverter(new StringConverter<Eigenschaft>() {
			@Override
			public String toString(Eigenschaft object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Eigenschaft fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();
		List<Eigenschaft> eList = handlungsfeldModel.getEigenschaften();
			for(Eigenschaft e:eList){
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

		frageList.setCellFactory(new Callback<ListView<Frage>, ListCell<Frage>>() {

			@Override
			public ListCell<Frage> call(ListView<Frage> f) {

				ListCell<Frage> cell = new ListCell<Frage>() {

					@Override
					protected void updateItem(Frage f, boolean bln) {
						super.updateItem(f, bln);
						if (f != null) {
							setText(f.getText());
						}
						if (f == null) {
							setText("");
						}

					}

				};

				return cell;
			}
		});

		skala.setConverter(new StringConverter<Skala>() {
			@Override
			public String toString(Skala object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Skala fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		ObservableList<Skala> skalaOl = FXCollections.observableArrayList();
		List<Skala> sList = handlungsfeldModel.getSkalen();
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


		List<Fach> faecher = handlungsfeldModel.getFaecher();

		for (Fach f : faecher) {
			fachOl.add(f);
		}
		fach.setItems(fachOl);


		lehrveranstaltung.setConverter(new StringConverter<Lehrveranstaltung>() {
			@Override
			public String toString(Lehrveranstaltung l) {
				if (l == null) {
					return "";
				}
				return l.getFach().getName() + " " + l.getSemester().name() + l.getJahr().getYear() + " " + l.getDozent();
			}

			@Override
			public Lehrveranstaltung fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		lehrveranstaltung.setItems(lvOl);

		vorlage.setConverter(new StringConverter<Fragebogen>() {
			@Override
			public String toString(Fragebogen object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Fragebogen fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		ObservableList<Fragebogen> vorlageOl = FXCollections.observableArrayList();
		List<Fragebogen> fList = fragebogenModel.getFragebogenFor(null, null, null, null, null, null, null);
		for (Fragebogen f : fList) {
			vorlageOl.add(f);
		}
		vorlage.setItems(vorlageOl);

		skalaFrage.setConverter(new StringConverter<Skala>() {
			@Override
			public String toString(Skala object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Skala fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		ObservableList<Skala> skalaFrageOl = FXCollections.observableArrayList();

		for (Skala s : sList) {
			skalaFrageOl.add(s);
		}

		skalaFrage.setItems(skalaFrageOl);

		ObservableList<String> fragePosOl = FXCollections.observableArrayList();
		fragePosOl.add("Top");
		fragePosOl.add("Bottom");
		positionFrage.setItems(fragePosOl);
		/*
		 * menuItemFilterItem.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent t) {
		 * 
		 * Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/filterAllItems.fxml");
		 * 
		 * stage.show();
		 * 
		 * stage.setOnHidden(new EventHandler<WindowEvent>() { public void handle(WindowEvent we) { logger.debug("Closing dialog stage.");
		 * 
		 * } });
		 * 
		 * }
		 * 
		 * });
		 * 
		 * menuItemFilterOff.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent t) { // inaktiv = false; treeTableController.buildTreeTable(); }
		 * 
		 * });
		 */

		addItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				ObservableList<TreeItem<TreeItemWrapper>> treeItems = treeTableController.getSelectedTreeItemList();

				for (TreeItem<TreeItemWrapper> child : treeItems) {
					if (child.getValue().isItem() && !itemList.getItems().contains(child.getValue().getItem())) {
						items.add(child.getValue().getItem());
					}
				}

			}

		});

		removeItem.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				itemsToRemove.add(itemList.getSelectionModel().getSelectedItem());
			}

			@Override
			public void updateUI() {
				items.remove(itemList.getSelectionModel().getSelectedItem());

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

				lvOl.setAll(fach.getValue().getLehrveranstaltungen());				
			}

			@Override
			public void updateUI() {


			}
		});

		saveFragebogen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				

				if(editMode == false){
					Fragebogen f = new Fragebogen();
					f.setName(name.getText());
					f.setItems(itemList.getItems());
					f.setPerspektive(perspektive.getValue());
					f.setEigenschaft(eigenschaft.getValue());
					f.setSkala(skala.getValue());
					f.setLehrveranstaltung(lehrveranstaltung.getValue());
					f.setExportiert(false);
					f.setErstellungsDatum(new Date());
					f.setFragen(frageList.getItems());
					fragebogenModel.persistFragebogen(f);

					for (Frage frage : frageList.getItems()) {
						frage.setFragebogen(f);
						fragebogenModel.persistFrage(frage);
					}


					ArrayList<Fragebogen> itemFb = new ArrayList<Fragebogen>();
					itemFb.add(f);

					for (Item item : itemList.getItems()) {
						item.setFrageboegen(itemFb);
						handlungsfeldModel.mergeItem(item);
					}
				
				}
				else{
					
					editFragebogen.setName(name.getText());
					editFragebogen.setItems(itemList.getItems());
					editFragebogen.setPerspektive(perspektive.getValue());
					editFragebogen.setEigenschaft(eigenschaft.getValue());
					editFragebogen.setSkala(skala.getValue());
					editFragebogen.setLehrveranstaltung(lehrveranstaltung.getValue());
					editFragebogen.setExportiert(false);
					editFragebogen.setErstellungsDatum(new Date());
					editFragebogen.setFragen(frageList.getItems());
					fragebogenModel.mergeFragebogen(editFragebogen);

					for (Frage frage : frageList.getItems()) {
						if (!fragenToRemove.contains(frage)) {
						frage.setFragebogen(editFragebogen);
						fragebogenModel.mergeFrage(frage);
						}
					}
					ArrayList<Fragebogen> itemFb = new ArrayList<Fragebogen>();
					itemFb.add(editFragebogen);

					for (Item item : itemList.getItems()) {
						if (!itemsToRemove.contains(item)) {
						item.setFrageboegen(itemFb);
						handlungsfeldModel.mergeItem(item);
						}
					}
					
					for (Item item : itemsToRemove) {
						ArrayList<Fragebogen> fbs = new ArrayList<Fragebogen>();
						for (Fragebogen f : item.getFrageboegen()) {
							if (!editFragebogen.equals(f)) {
								fbs.add(f);
							}
							item.setFrageboegen(fbs);
							handlungsfeldModel.mergeItem(item);
						}

					}
					for (Frage frage : fragenToRemove) {

						frage.setFragebogen(null);
						fragebogenModel.mergeFrage(frage);

					}
				}
				

			}

		});

		vorlage.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				Fragebogen x = vorlage.getSelectionModel().getSelectedItem();

				for (Item item : x.getItems()) {
					items.add(item);
				}

				for (Frage frage : x.getFragen()) {
					fragenOl.add(frage);
				}

			}

			@Override
			public void updateUI() {
				Fragebogen x = vorlage.getSelectionModel().getSelectedItem();



				perspektive.getSelectionModel().select(x.getPerspektive());
				eigenschaft.getSelectionModel().select(x.getEigenschaft());
				skala.getSelectionModel().select(x.getSkala());
				lehrveranstaltung.getSelectionModel().select(x.getLehrveranstaltung());
				name.setText(x.getName());


			}
		});
		
		addFrage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Frage f = new Frage();
				f.setText(frageText.getText());
				f.setSkala(skalaFrage.getValue());
				if (positionFrage.equals("Bottom")) {
					f.setPosition(FragePosition.BOTTOM);
				} else {
					f.setPosition(FragePosition.TOP);
				}

				fragenOl.add(f);



			}

		});

		removeFrage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fragenToRemove.add(frageList.getSelectionModel().getSelectedItem());
				fragenOl.remove(frageList.getSelectionModel().getSelectedItem());
				
			}

		});
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

		editMode = true;
		editFragebogen = f;
		for (Item item : f.getItems()) {
			items.add(item);
		}

		for (Frage frage : f.getFragen()) {
			fragenOl.add(frage);
		}

		perspektive.getSelectionModel().select(f.getPerspektive());
		eigenschaft.getSelectionModel().select(f.getEigenschaft());
		skala.getSelectionModel().select(f.getSkala());
		lehrveranstaltung.getSelectionModel().select(f.getLehrveranstaltung());
		name.setText(f.getName());
	}

}
