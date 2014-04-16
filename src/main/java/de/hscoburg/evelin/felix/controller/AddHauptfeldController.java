package de.hscoburg.evelin.felix.controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.felix.model.Hauptfeld;

@Controller
public class AddHauptfeldController implements Initializable {

	@FXML
	private Button save;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private TextField rolle;
	@FXML
	private ListView<String> eigenschaft;

	@Autowired
	private HauptfelderControllerImpl hauptfeldController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		eigenschaft.setItems(options);

		eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Hauptfeld h = new Hauptfeld();
				h.setId((new Random()).nextInt());
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

}
