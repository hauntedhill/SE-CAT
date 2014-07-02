package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class AddHandlungsfeldController extends BaseController {

	@FXML
	private Button save;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;

	@Autowired
	private HandlungsfeldController handlungsfeldfeldController;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private TreeTableController treeTableController;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					if (name.getText() == null || name.getText().equals("")) {
						throw new IllegalArgumentException();
					}
					Handlungsfeld h = new Handlungsfeld();
					h.setAktiv(true);
					h.setName(name.getText());
					handlungsfeldModel.persistHandlungsfeld(h);
					treeTableController.addHandlungsfeldToCurrentSelection(h);

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
	public String getKeyForSceneName() {

		return "scene.addhandlungsfeld.lable.title";
	}

}
