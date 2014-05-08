package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

<<<<<<< HEAD
=======















>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
<<<<<<< HEAD
=======
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
<<<<<<< HEAD
=======
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;

@Controller
public class MoveItemController extends BaseController {

	@FXML
	private Button move;

	@FXML
	private Button cancle;
	@FXML
	private TextField name;
	@FXML
	private ComboBox<Handlungsfeld> handlungsfeld;

	@Autowired
	private HandlungsfeldController hauptfeldController;

	@Autowired
<<<<<<< HEAD
	private HandlungsfeldModel hauptfeldModel;

=======
	private HandlungsfeldModel handlungsfeldModel;
	
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e
	@Autowired
	private HandlungsfeldDAO service;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

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
			if (!tmp.equals(selected)) {
				options.add(tmp);
			}
		}

<<<<<<< HEAD
		handlungsfeld.setItems(options);

		// eigenschaft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
=======
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    
	   
	    
	      handlungsfeld.setCellFactory(new Callback<ListView<Handlungsfeld>, ListCell<Handlungsfeld>>() {

	           @Override
	           public ListCell<Handlungsfeld> call(ListView<Handlungsfeld> s) {

	               ListCell<Handlungsfeld> cell = new ListCell<Handlungsfeld>() {

	                   @Override
	                   protected void updateItem(Handlungsfeld t, boolean bln) {
	                       super.updateItem(t, bln);
	                       if (t != null) {
	                           setText(t.getName());
	                       }
	                   }

	               };

	               return cell;
	           }
	       });
	      
	      
	      TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
	      TreeItem<TreeItemWrapper> old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex());
	      
	      
	      
	       ObservableList<Handlungsfeld> handlungsfeldOl = FXCollections.observableArrayList();
	       List<Handlungsfeld> handlungsfeldList = handlungsfeldModel.getHandlungsfelderBy( true, true );
	       ListIterator<Handlungsfeld> ithandlungsfeld = handlungsfeldList.listIterator();
	       Handlungsfeld tmp;
	       while (ithandlungsfeld.hasNext()) {
	           tmp = ithandlungsfeld.next();
	           if(!tmp.equals( old ))
	           handlungsfeldOl.add( tmp );
	           }
	       

	       
	       handlungsfeld.setItems( handlungsfeldOl );

>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e

		move.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
<<<<<<< HEAD
				TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
				TreeItemWrapper old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue();
				Handlungsfeld tmp = new Handlungsfeld();
				ListIterator<Handlungsfeld> it = hf.listIterator();
				while (it.hasNext()) {
					tmp = it.next();
					if (tmp.getName().equals(handlungsfeld.getValue()))
						break;
				}
=======
			    

			    TreeTableView<TreeItemWrapper> treeTable = hauptfeldController.getTreeTable();
			    TreeItemWrapper old = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue();
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e

				List<Item> items = old.getHandlungsfeld().getItems();
				handlungsfeld.getValue().setItems( items );
				
				ListIterator<Item> iter = items.listIterator();
				Item tmpItem = new Item();
<<<<<<< HEAD
				while (iter.hasNext()) {

					tmpItem = iter.next();
					tmpItem.setHandlungsfeld(tmp);
					hauptfeldModel.mergeItem(tmpItem);
				}

				hauptfeldController.refreshHandlungsfeld(handlungsfeld.getValue(), oldItem);

=======
				    while(iter.hasNext()){
				        
				        tmpItem = iter.next();
				        tmpItem.setHandlungsfeld( handlungsfeld.getValue() );
				        handlungsfeldModel.mergeItem( tmpItem );
				    }
				
				  
				    
				
				hauptfeldController.buildTreeTable();
				
			    
>>>>>>> 8b0008830fab2af2d43c211856d678877505b58e
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

	@Override
	public String getSceneName() {

		return "Items verschieben";
	}

}
