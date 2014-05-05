package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;






import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class AddItemController implements Initializable {

	@FXML
	private Button save;

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
	public void initialize(URL location, ResourceBundle resources) {
	/*	ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		eigenschaft.setItems(options);

		eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);*/

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    
	            Perspektive pers = new Perspektive();
	            pers.setId(( new Random()).nextInt() );
	            pers.setName( name.getText() );
	            ArrayList<Perspektive> persList = new ArrayList<Perspektive>();
	            persList.add( pers );
			    
	            
			    
				Item i = new Item();
				i.setId((new Random()).nextInt());
				i.setAktiv( true );
				i.setName(name.getText());
				i.setNotiz( notiz.getText() );
						
				
				
				hauptfeldController.addItemToCurrentSelection(i);
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
