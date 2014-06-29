package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.helper.TreeItemWrapper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

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
	private TreeTableController treeTableController;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancel.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		save.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					if (name.getText().equals("")) {
						throw new IllegalArgumentException();
					}
					Handlungsfeld h = treeTableController.getSelectedTreeItem().getValue().getHandlungsfeld();
					Bereich b = new Bereich();
					b.setName(name.getText());
					b.setHandlungsfeld(h);
					handlungsfeldModel.persistBereich(b);

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
				TreeItem<TreeItemWrapper> selected = treeTableController.getSelectedTreeItem();
				treeTableController.updateHandlungsfeld(selected.getParent().getChildren().indexOf(selected), -1);
				Stage stage = (Stage) save.getScene().getWindow();
				stage.close();
			}

		});

		cancel.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

			}

			@Override
			public void updateUI() {

				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
		});
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addbereich.lable.title";
	}

}
