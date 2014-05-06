package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.ResourceBundle;














import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class MoveItemController implements Initializable {

	@FXML
	private Button move;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private ChoiceBox<String> handlungsfeld;

	@Autowired
	private HandlungsfeldController hauptfeldController;
	
	@Autowired
	private HandlungsfeldModel hauptfeldModel;
	
	@Autowired
	private HandlungsfeldDAO service;



	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    
	    ObservableList<String> options = FXCollections.observableArrayList();
	    TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
	    
	    
	    TreeItem<TreeItemWrapper> neu;
	   
	    TreeItem<TreeItemWrapper> old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex());
	    String selected = old.getValue().getName();
	    final List<Handlungsfeld> hf = hauptfeldModel.getHandlungsfelderBy(true, true);
	    final ListIterator<Handlungsfeld> it = hf.listIterator();
	    final List<Item> oldItem = old.getValue().getHandlungsfeld().getItems();
	    String tmp = "";
	    
	    while (it.hasNext()) {
	        tmp = it.next().getName();
	        if(!tmp.equals( selected )){
	        options.add( tmp );
	        }
	    }
	                    
		
		handlungsfeld.setItems(options);

		//eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		move.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
			    TreeItemWrapper old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue();
			    Handlungsfeld tmp = new Handlungsfeld();
			    ListIterator<Handlungsfeld> it = hf.listIterator();
			     while (it.hasNext()) {
			            tmp = it.next();
			            if(tmp.getName().equals( handlungsfeld.getValue() ))
			                break;    
			        }
			    
				List<Item> items = old.getHandlungsfeld().getItems();
				ListIterator<Item> iter = items.listIterator();
				Item tmpItem = new Item();
				    while(iter.hasNext()){
				        
				        tmpItem = iter.next();
				        tmpItem.setHandlungsfeld( tmp );
				        hauptfeldModel.mergeItem( tmpItem );
				    }
				
				  
				    
				
				hauptfeldController.refreshHandlungsfeld( handlungsfeld.getValue(), oldItem );
				
			    
				Stage stage = (Stage) move.getScene().getWindow();
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
