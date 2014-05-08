package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class FilterItemController extends BaseController {

	@FXML
	private Button filter;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private TextField rolle;
	@FXML
	private TextArea notiz;
	@FXML
	private ListView<String> eigenschaft;

	@Autowired
	private HandlungsfeldController hauptfeldController;

	@Autowired
	private HandlungsfeldModel hauptfeldModel;

	@Override
<<<<<<< HEAD
	public void initializeController(URL location, ResourceBundle resources) {
		/*
		 * ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		 * eigenschaft.setItems(options);
		 * 
		 * eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		 */
=======
	public void initialize(URL location, ResourceBundle resources) {
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e

	    
	    
	    
	    
		filter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				hauptfeldController.filterItem(notiz.getText());
				Stage stage = (Stage) filter.getScene().getWindow();
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
		// TODO Auto-generated method stub
		return "Items filtern";
	}

}
