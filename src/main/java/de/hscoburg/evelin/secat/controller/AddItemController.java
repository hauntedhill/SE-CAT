package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.helper.TreeItemWrapper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

/**
 * Controller zur Erstellung von Items
 * 
 * @author moro1000
 * 
 */
@Controller
public class AddItemController extends BaseController {

	@FXML
	private Button save;
	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private TextArea frage;
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
	private TreeTableController treeTableController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;
	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private EigenschaftenModel eigenschaftModel;

	private static TreeItem<TreeItemWrapper> selected;
	private static Bereich bereich;
	private static int indexBereich;
	private static int indexHandlungsfeld;
	private static Item editItem;
	private static boolean editMode = false;

	/**
	 * Initialisierierung
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		frage.wrapTextProperty().set(true);
		selected = treeTableController.getSelectedTreeItem();
		bereich = selected.getValue().getBereich();
		indexBereich = selected.getParent().getChildren().indexOf(selected);
		indexHandlungsfeld = selected.getParent().getParent().getChildren().indexOf(selected.getParent());

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		templateBox.setConverter(ConverterHelper.getConverterForItem());
		templateBox.promptTextProperty().set(SeCatResourceBundle.getInstance().getString("scene.addItem.templatebox.prompttextproperty"));

		perspektiveList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

		eigenschaftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

		reloadGUI();
		templateBox.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				if (templateBox.getValue() != null) {
					if (templateBox.getValue().getId() == -1) {

						perspektiveList.getSelectionModel().clearSelection();
						eigenschaftList.getSelectionModel().clearSelection();
						notiz.clear();
						frage.clear();

					} else if (templateBox.getValue() != null) {
						perspektiveList.getSelectionModel().clearSelection();
						eigenschaftList.getSelectionModel().clearSelection();
						notiz.setText(templateBox.getValue().getNotiz());
						frage.setText(templateBox.getValue().getFrage());
						List<Perspektive> templatePerspektive = templateBox.getValue().getPerspektiven();
						List<Eigenschaft> templateEigenschaft = templateBox.getValue().getEigenschaften();
						for (Perspektive set : templatePerspektive) {
							perspektiveList.getSelectionModel().select(perspektiveList.getItems().indexOf(set));
						}

						for (Eigenschaft set : templateEigenschaft) {
							eigenschaftList.getSelectionModel().select(eigenschaftList.getItems().indexOf(set));
						}
					}

				}
			}

		});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					if (name.getText() == null || name.getText().equals("")) {
						throw new IllegalArgumentException();
					}
					if (editMode == false) {
						handlungsfeldModel.persistItem(createItem());
					} else {

						editItem.setName(name.getText());
						editItem.setNotiz(notiz.getText());
						editItem.setFrage(frage.getText());

						if (perspektiveList.getSelectionModel().getSelectedItems() != null) {
							editItem.setPerspektiven(perspektiveList.getSelectionModel().getSelectedItems());
						}

						if (eigenschaftList.getSelectionModel().getSelectedItems() != null) {
							editItem.setEigenschaften(eigenschaftList.getSelectionModel().getSelectedItems());
						}
						handlungsfeldModel.mergeItem(editItem);
						editMode = false;

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
				reloadGUI();
				treeTableController.updateHandlungsfeld(indexHandlungsfeld, indexBereich);

			}

		}, save, true);

		cancle.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {
				Stage stage = (Stage) cancle.getScene().getWindow();
				stage.close();
			}

		});
	}

	private Item createItem() {

		Item i = new Item();
		i.setAktiv(true);
		i.setName(name.getText());
		i.setNotiz(notiz.getText());
		i.setFrage(frage.getText());

		if (perspektiveList.getSelectionModel().getSelectedItems() != null) {
			i.setPerspektiven(perspektiveList.getSelectionModel().getSelectedItems());
		}

		if (eigenschaftList.getSelectionModel().getSelectedItems() != null) {
			i.setEigenschaften(eigenschaftList.getSelectionModel().getSelectedItems());
		}

		i.setBereich(selected.getValue().getBereich());
		return i;
	}

	/**
	 * Methode l�dt die Maske nach der Erstellung neu
	 */
	private void reloadGUI() {
		ObservableList<Item> itemOl = FXCollections.observableArrayList();
		ObservableList<Perspektive> perspektivenOl = FXCollections.observableArrayList();
		ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();

		List<Item> itemList = handlungsfeldModel.getItemBy(bereich, true, null, null, null, null, null);
		List<Perspektive> persList = perspektivenModel.getPerspektiven();
		List<Eigenschaft> eigenList = eigenschaftModel.getEigenschaften();
		Item keineVorlage = new Item();
		keineVorlage.setId(-1);
		keineVorlage.setName((SeCatResourceBundle.getInstance().getString("scene.all.notemplate")));
		itemOl.add(keineVorlage);
		for (Item item : itemList) {
			itemOl.add(item);
		}
		for (Perspektive perspektive : persList) {

			perspektivenOl.add(perspektive);
		}
		for (Eigenschaft eigenschaft : eigenList) {

			eigenschaftOl.add(eigenschaft);
		}
		templateBox.setItems(itemOl);
		perspektiveList.setItems(perspektivenOl);
		eigenschaftList.setItems(eigenschaftOl);
		name.clear();
		frage.clear();
		perspektiveList.getSelectionModel().clearSelection();
		eigenschaftList.getSelectionModel().clearSelection();
		notiz.clear();
	}

	/**
	 * Methode setzt das Items das editiert werden soll und initialisiert die Listen.
	 * 
	 * @param i
	 *            {@link Item} - das zu editierende Item
	 */
	public void setItemToEdit(Item i) {

		indexBereich = selected.getParent().getParent().getChildren().indexOf(selected.getParent());
		indexHandlungsfeld = selected.getParent().getParent().getParent().getChildren().indexOf(selected.getParent().getParent());

		editItem = i;
		editMode = true;
		name.setText(i.getName());
		frage.setText(i.getFrage());
		notiz.setText(i.getNotiz());
		frage.setText(i.getFrage());
		List<Perspektive> templatePerspektive = i.getPerspektiven();
		List<Eigenschaft> templateEigenschaft = i.getEigenschaften();
		for (Perspektive set : templatePerspektive) {
			perspektiveList.getSelectionModel().select(perspektiveList.getItems().indexOf(set));
		}

		for (Eigenschaft set : templateEigenschaft) {
			eigenschaftList.getSelectionModel().select(eigenschaftList.getItems().indexOf(set));
		}
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addItem.lable.title";
	}

	@Override
	public void setTitle() {

		setTitle("( " + selected.getValue().getBereich().getHandlungsfeld().getName() + " - " + selected.getValue().getBereich().getName() + " )");
	}
}
