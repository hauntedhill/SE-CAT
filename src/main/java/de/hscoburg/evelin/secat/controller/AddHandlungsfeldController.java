package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
		/*
		 * ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		 * eigenschaft.setItems(options);
		 * 
		 * eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		 */

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Handlungsfeld h = new Handlungsfeld();
				// h.setId((new Random()).nextInt());
				h.setAktiv(true);
				h.setName(name.getText());

				hauptfeldController.addHauptfeldToCurrentSelection(h);
				Stage stage = (Stage) save.getScene().getWindow();
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

		return "Handlungsfeld hinzufügen";
	}

}
