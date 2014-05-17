package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class AddBereichController extends BaseController {

	@FXML
	private Button save;
	@FXML
	private Button cancel;
	@FXML
	private TextField name;

	@Autowired
	private HandlungsfeldController handlungsfeldController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancel.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!name.getText().equals("")) {
					Handlungsfeld h = handlungsfeldController.getSelectedTreeItem().getValue().getHandlungsfeld();
					Bereich b = new Bereich();
					b.setName(name.getText());
					b.setHandlungsfeld(h);
					handlungsfeldModel.persistBereich(b);

					TreeItem<TreeItemWrapper> selected = handlungsfeldController.getSelectedTreeItem();
					Handlungsfeld reNew = selected.getValue().getHandlungsfeld();
					reNew.addBereich(b);
					int index = selected.getParent().getChildren().indexOf(selected);
					selected.getParent().getChildren().remove(selected);
					handlungsfeldController.getTreeTable().getRoot().getChildren().add(index, handlungsfeldController.createNode(new TreeItemWrapper(reNew)));
					handlungsfeldController.getTreeTable().getRoot().getChildren().get(index).setExpanded(true);
				} else {

					Dialogs.create().title("Warnung").masthead("Handlungsfeld konnte nich angelegt werden!").message("Kein Name vergeben!").showWarning();
				}
				Stage stage = (Stage) save.getScene().getWindow();
				stage.close();

			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Stage stage = (Stage) cancel.getScene().getWindow();
				// do what you have to do
				stage.close();

			}
		});
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addbereich.lable.title";
	}

}
