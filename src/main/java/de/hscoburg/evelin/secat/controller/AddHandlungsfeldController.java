package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

@Controller
public class AddHandlungsfeldController extends BaseController {

	@FXML
	private Button save;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	/*
	 * @FXML private TextField rolle;
	 * 
	 * @FXML private ListView<String> eigenschaft;
	 */

	@Autowired
	private HandlungsfeldController hauptfeldController;

	@Autowired
	private HandlungsfeldModel hauptfeldModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		save.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		cancle.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Handlungsfeld h = new Handlungsfeld();

				h.setAktiv(true);
				h.setName(name.getText());

				hauptfeldController.addHauptfeldToCurrentSelection(h);
				Stage stage = (Stage) save.getScene().getWindow();

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

		return "Handlungsfeld hinzufügen";
	}

}
