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
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;

@Controller
public class EditItemController extends BaseController {

	@FXML
	private Button edit;
	@FXML
	private Button cancel;
	@FXML
	private TextField name;
	@FXML
	private TextArea notiz;
	@FXML
	private ListView<Eigenschaft> eigenschaftList;
	@FXML
	private ListView<Perspektive> perspektiveList;
	@Autowired
	private HandlungsfeldController handlungsfeldController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;
	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private EigenschaftenModel eigenschaftModel;
	@Autowired
	private TreeTableController treeTableController;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		edit.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancel.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
		TreeItem<TreeItemWrapper> oldTreeItem = treeTableController.getSelectedTreeItem();
		final Item editItem = oldTreeItem.getValue().getItem();

		name.setText(editItem.getName());
		notiz.setText(editItem.getNotiz());

		perspektiveList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		perspektiveList.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {
			@Override
			public ListCell<Perspektive> call(ListView<Perspektive> p) {
				ListCell<Perspektive> cell = new ListCell<Perspektive>() {
					@Override
					protected void updateItem(Perspektive p, boolean bool) {
						super.updateItem(p, bool);
						if (p != null) {
							setText(p.getName());
						}
					}
				};

				return cell;
			}
		});

		ObservableList<Perspektive> perspektivenOl = FXCollections.observableArrayList();
		List<Perspektive> persList = perspektivenModel.getPerspektiven();

		for (Perspektive p : persList) {
			perspektivenOl.add(p);
		}
		perspektiveList.setItems(perspektivenOl);
		for (Perspektive p : perspektiveList.getItems()) {
			if (editItem.getPerspektiven().contains(p)) {
				perspektiveList.getSelectionModel().select(perspektiveList.getItems().indexOf(p));
			}
		}

		eigenschaftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		eigenschaftList.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {
			@Override
			public ListCell<Eigenschaft> call(ListView<Eigenschaft> e) {
				ListCell<Eigenschaft> cell = new ListCell<Eigenschaft>() {
					@Override
					protected void updateItem(Eigenschaft e, boolean bool) {
						super.updateItem(e, bool);
						if (e != null) {
							setText(e.getName());
						}
					}
				};

				return cell;
			}
		});

		ObservableList<Eigenschaft> eigenschaftenOl = FXCollections.observableArrayList();
		List<Eigenschaft> eigenschaftenList = eigenschaftModel.getEigenschaften();

		for (Eigenschaft e : eigenschaftenList) {
			eigenschaftenOl.add(e);
		}
		eigenschaftList.setItems(eigenschaftenOl);
		for (Eigenschaft e : eigenschaftList.getItems()) {
			if (editItem.getEigenschaften().contains(e)) {
				eigenschaftList.getSelectionModel().select(eigenschaftList.getItems().indexOf(e));
			}
		}

		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Item newItem = editItem;
				newItem.setName(name.getText());
				newItem.setNotiz(notiz.getText());
				newItem.setPerspektiven(perspektiveList.getSelectionModel().getSelectedItems());
				newItem.setEigenschaften(eigenschaftList.getSelectionModel().getSelectedItems());
				handlungsfeldModel.mergeItem(newItem);
				TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
				int index = selectedTreeItem.getParent().getChildren().indexOf(selectedTreeItem);
				selectedTreeItem.getParent().getChildren().add(index, treeTableController.createNode(new TreeItemWrapper(newItem)));
				selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);
				Stage stage = (Stage) edit.getScene().getWindow();
				stage.close();

			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();

			}
		});

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addItem.lable.title";
	}
}
