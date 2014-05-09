package de.hscoburg.evelin.secat.controller;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class MoveItemController extends BaseController {

	@FXML
	private Button move;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private ComboBox<Handlungsfeld> handlungsfeld;

	@Autowired
	private HandlungsfeldController hauptfeldController;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private HandlungsfeldDAO service;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		move.setGraphic(new ImageView(new Image("/image/icons/up.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
		handlungsfeld.setConverter(new StringConverter<Handlungsfeld>() {
			@Override
			public String toString(Handlungsfeld h) {

				if (h == null) {
					System.out.println("null");
					return "";
				}
				return h.getName();
			}

			@Override
			public Handlungsfeld fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		});

		handlungsfeld.promptTextProperty().set("Handlungsfeld w�hlen");

		TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
		TreeItem<TreeItemWrapper> old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex());

		ObservableList<Handlungsfeld> handlungsfeldOl = FXCollections.observableArrayList();
		List<Handlungsfeld> handlungsfeldList = handlungsfeldModel.getHandlungsfelderBy(true, true);
		ListIterator<Handlungsfeld> ithandlungsfeld = handlungsfeldList.listIterator();
		Handlungsfeld tmp;
		while (ithandlungsfeld.hasNext()) {
			tmp = ithandlungsfeld.next();
			if (!tmp.equals(old))
				handlungsfeldOl.add(tmp);
		}

		handlungsfeld.setItems(handlungsfeldOl);

		move.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
				TreeItemWrapper old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue();

				List<Item> items = old.getHandlungsfeld().getItems();
				handlungsfeld.getValue().setItems(items);

				ListIterator<Item> iter = items.listIterator();
				Item tmpItem = new Item();
				while (iter.hasNext()) {

					tmpItem = iter.next();
					tmpItem.setHandlungsfeld(handlungsfeld.getValue());
					handlungsfeldModel.mergeItem(tmpItem);
				}

				hauptfeldController.buildTreeTable();

				Stage stage = (Stage) move.getScene().getWindow();
				// do what you have to do
				stage.close();

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
	public String getSceneName() {

		return "Items verschieben";
	}

}
