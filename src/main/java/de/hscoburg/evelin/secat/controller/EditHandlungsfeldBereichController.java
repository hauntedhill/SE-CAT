package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class EditHandlungsfeldBereichController extends BaseController {

	@FXML
	private Button save;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private TitledPane titledPane;

	@Autowired
	private HandlungsfeldController handlungsfeldfeldController;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private TreeTableController treeTableController;

	private TreeItem<TreeItemWrapper> selected;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));
		selected = treeTableController.getSelectedTreeItem();
		name.setText(selected.getValue().getName());

		if (selected.getValue().isHandlungsfeld()) {
			titledPane.setText(SeCatResourceBundle.getInstance().getString("scene.edithandlungsfeldBereich.lable.title") + " "
					+ SeCatResourceBundle.getInstance().getString("scene.all.handlungsfeld"));
		} else {
			titledPane.setText(SeCatResourceBundle.getInstance().getString("scene.edithandlungsfeldBereich.lable.title") + " "
					+ SeCatResourceBundle.getInstance().getString("scene.all.subcriterion"));
		}

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					if (name.getText() == null || name.getText().equals("")) {
						throw new IllegalArgumentException();
					}
					Bereich bereich = new Bereich();
					Handlungsfeld hf = new Handlungsfeld();

					if (selected.getValue().isHandlungsfeld()) {
						hf = selected.getValue().getHandlungsfeld();
						hf.setName(name.getText());
						handlungsfeldModel.mergeHandlugsfeld(hf);

					} else
						bereich = selected.getValue().getBereich();
					bereich.setName(name.getText());
					handlungsfeldModel.mergeBereich(bereich);

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
				if (selected.getValue().isHandlungsfeld()) {
					treeTableController.updateHandlungsfeld(selected.getParent().getChildren().indexOf(selected), -1);
				} else {
					treeTableController.updateHandlungsfeld(selected.getParent().getParent().getChildren().indexOf(selected.getParent()), selected.getParent()
							.getChildren().indexOf(selected));
				}

				Stage stage = (Stage) save.getScene().getWindow();
				stage.close();
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

	@Override
	public void setTitle() {

		if (selected.getValue().isBereich()) {
			setTitle(" " + SeCatResourceBundle.getInstance().getString("scene.all.subcriterion") + " " + selected.getValue().getName());
		} else {
			setTitle(" " + SeCatResourceBundle.getInstance().getString("scene.all.handlungsfeld") + " " + selected.getValue().getName());
		}

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.edithandlungsfeldBereich.lable.title";

	}
}
