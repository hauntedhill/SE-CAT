package de.hscoburg.evelin.secat.controller;

import java.net.URL;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class AddFragebogenController extends BaseController {

	
	private static Logger logger = LoggerFactory.getLogger(AddFragebogenController.class);

	@FXML
	private Menu menuFilter;

	@FXML
	private MenuItem menuItemFilterHandlungsfeld;

	@FXML
	private MenuItem menuItemFilterItem;

	@FXML
	private MenuItem menuItemFilterOff;

	@FXML
	private Button addItem;

	@FXML
	private Button removeItem;

	@Autowired
	private TreeTableController treeTableController;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@FXML
	private ListView<Item> itemList;
	@FXML
	private ComboBox<Perspektive> perspektive;
	@FXML
	private ComboBox<Eigenschaft> eigenschaft;
	@FXML
	private ComboBox<Skala> skala;
	@FXML
	private ComboBox<Fach> fach;
	@FXML
	private ComboBox<Lehrveranstaltung> lehrveranstaltung;

	private static ObservableList<Item> items = FXCollections.observableArrayList();

	private static Perspektive selectedPerspektive = null;

	private static Eigenschaft selectedEigenschaft = null;


	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		treeTableController.setSelectionMode(SelectionMode.MULTIPLE);

		menuItemFilterItem.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		menuItemFilterOff.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));

		addItem.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		removeItem.setGraphic(new ImageView(new Image("/image/icons/edit_remove.png", 16, 16, true, true)));

		itemList.setItems(items);
		// itemList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		perspektive.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.perspective"));
		eigenschaft.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.property"));
		fach.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.fachlable"));
		skala.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.all.scale"));
		lehrveranstaltung.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.lehrveranstaltung.lable"));

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

		ObservableList<Fach> fachOl = FXCollections.observableArrayList();
		List<Fach> fList = handlungsfeldModel.getFaecher();
		for (Fach f : fList) {
			fachOl.add(f);
		}
		fach.setItems(fachOl);

		lehrveranstaltung.setConverter(new StringConverter<Lehrveranstaltung>() {
			@Override
			public String toString(Lehrveranstaltung object) {
				if (object == null) {
					return "";
				}
				return object.toString();
			}

			@Override
			public Lehrveranstaltung fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});



		fach.setItems(fachOl);

		menuItemFilterItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/filterAllItems.fxml");

				stage.show();

				stage.setOnHidden(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						logger.debug("Closing dialog stage.");

					}
				});

			}

		});

		menuItemFilterOff.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				// inaktiv = false;
				treeTableController.buildTreeTable();
			}

		});

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

			}

			@Override
			public void updateUI() {


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

}
