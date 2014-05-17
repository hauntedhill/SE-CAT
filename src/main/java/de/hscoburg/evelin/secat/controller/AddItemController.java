package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class AddItemController extends BaseController {

	@FXML
	private Button save;
	@FXML
	private Button cancle;
	@FXML
	private Button chooseTemplate;
	@FXML
	private Button undo;
	@FXML
	private TextField name;
	@FXML
	private TextField rolle;
	@FXML
	private TextArea notiz;
	@FXML
	private ListView<Eigenschaft> eigenschaftList;
	@FXML
	private ListView<Perspektive> perspektiveList;
	@FXML
	private ComboBox<Item> templateBox;

	@Autowired
	private HandlungsfeldController handlungsfeldController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;
	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private EigenschaftenModel eigenschaftModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
		chooseTemplate.setGraphic(new ImageView(new Image("/image/icons/editcopy.png", 16, 16, true, true)));
		undo.setGraphic(new ImageView(new Image("/image/icons/editdelete.png", 16, 16, true, true)));
		Handlungsfeld chosenHandlungsfeld = handlungsfeldController.getTreeTable().getSelectionModel()
				.getModelItem(handlungsfeldController.getTreeTable().getSelectionModel().getSelectedIndex()).getValue().getHandlungsfeld();

		templateBox.setConverter(new StringConverter<Item>() {
			@Override
			public String toString(Item object) {

				if (object == null) {
					System.out.println("null");
					return "";
				}
				return object.getName();
			}

			@Override
			public Item fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});
		templateBox.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.addItem.templatebox.prompttextproperty"));

		ObservableList<Item> itemOl = FXCollections.observableArrayList();

		// AUSKOMMENTIERT WEGEN NEUEN ENTITIES
		// List<Item> itemList = handlungsfeldModel.getItemBy(chosenHandlungsfeld, true, null, null, null, null, null);
		List<Item> itemList = new ArrayList<>();

		ListIterator<Item> itItem = itemList.listIterator();

		while (itItem.hasNext()) {

			itemOl.add(itItem.next());
			;
		}

		templateBox.setItems(itemOl);

		perspektiveList.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {

			@Override
			public ListCell<Perspektive> call(ListView<Perspektive> s) {

				ListCell<Perspektive> cell = new ListCell<Perspektive>() {

					@Override
					protected void updateItem(Perspektive t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

		ObservableList<Perspektive> perspektivenOl = FXCollections.observableArrayList();
		List<Perspektive> persList = perspektivenModel.getPerspektiven();
		ListIterator<Perspektive> itpers = persList.listIterator();

		while (itpers.hasNext()) {

			perspektivenOl.add(itpers.next());
		}

		perspektiveList.setItems(perspektivenOl);
		perspektiveList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		eigenschaftList.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {

			@Override
			public ListCell<Eigenschaft> call(ListView<Eigenschaft> s) {

				ListCell<Eigenschaft> cell = new ListCell<Eigenschaft>() {

					@Override
					protected void updateItem(Eigenschaft t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

		ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();
		List<Eigenschaft> eigenList = eigenschaftModel.getEigenschaften();
		ListIterator<Eigenschaft> iteigenschaft = eigenList.listIterator();

		while (iteigenschaft.hasNext()) {

			eigenschaftOl.add(iteigenschaft.next());
		}

		eigenschaftList.setItems(eigenschaftOl);
		eigenschaftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (name.getText() != null && !name.getText().equals("")) {
					TreeItem<TreeItemWrapper> selected = handlungsfeldController.getSelectedTreeItem();
					TreeItem<TreeItemWrapper> parent = selected.getParent();
					Item i = new Item();
					i.setAktiv(true);
					i.setName(name.getText());
					i.setNotiz(notiz.getText());

					if (perspektiveList.getSelectionModel().getSelectedItems() != null) {
						i.setPerspektiven(perspektiveList.getSelectionModel().getSelectedItems());
					}

					if (eigenschaftList.getSelectionModel().getSelectedItems() != null) {
						i.setEigenschaften(eigenschaftList.getSelectionModel().getSelectedItems());
					}

					ArrayList<Item> list = new ArrayList<Item>();
					list.add(i);
					i.setBereich(selected.getValue().getBereich());

					handlungsfeldModel.persistItem(i);

					Bereich reNew = selected.getValue().getBereich();
					reNew.addItem(i);

					int index = selected.getParent().getChildren().indexOf(selected);
					System.out.println(index);
					System.out.println(selected.getValue().getName());
					parent.getChildren().remove(selected);
					parent.getChildren().add(index, handlungsfeldController.createNode(new TreeItemWrapper(reNew)));
					parent.getChildren().get(index).setExpanded(true);

				} else {

					Dialogs.create().title("Warnung").masthead("Item konnte nich angelegt werden!").message("Kein Name vergeben!").showWarning();
				}

				Stage stage = (Stage) save.getScene().getWindow();
				stage.close();

			}
		});

		chooseTemplate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				perspektiveList.getSelectionModel().clearSelection();
				eigenschaftList.getSelectionModel().clearSelection();
				notiz.setText(templateBox.getValue().getNotiz());
				List<Perspektive> templatePerspektive = templateBox.getValue().getPerspektiven();
				for (Perspektive set : templatePerspektive) {
					perspektiveList.getSelectionModel().select(perspektiveList.getItems().indexOf(set));
				}
				List<Eigenschaft> templateEigenschaft = templateBox.getValue().getEigenschaften();
				for (Eigenschaft set : templateEigenschaft) {
					eigenschaftList.getSelectionModel().select(eigenschaftList.getItems().indexOf(set));
				}
			}
		});

		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				perspektiveList.getSelectionModel().clearSelection();
				eigenschaftList.getSelectionModel().clearSelection();
				notiz.setText("");
			}
		});

		cancle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Stage stage = (Stage) cancle.getScene().getWindow();
				// do what you have to do
				stage.close();

			}
		});
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addItem.lable.title";
	}

}
