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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;

@Controller
public class FilterAllItemsController implements Initializable {

	@FXML
	private Button filter;

	@FXML
	private Button cancle;
	/*@FXML
    private TextField name;
    @FXML
    private TextField rolle;*/
    @FXML
    private TextArea notiz;
    @FXML
    private ListView<Eigenschaft> eigenschaftList;
    
    @FXML
    private ListView<Perspektive> perspektiveList;

    @FXML
    private ComboBox<Skala> skalaBox;
    
    @Autowired
    private HandlungsfeldController hauptfeldController;

    @Autowired
    private HandlungsfeldModel handlungsfeldModel;

    @Autowired
    private PerspektivenModel perspektivenModel;
    
    @Autowired
    private SkalenModel skalenModel;
    
    @Autowired
    private EigenschaftenModel eigenschaftModel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	  
	    skalaBox.setCellFactory(new Callback<ListView<Skala>, ListCell<Skala>>() {

	           @Override
	           public ListCell<Skala> call(ListView<Skala> s) {

	               ListCell<Skala> cell = new ListCell<Skala>() {

	                   @Override
	                   protected void updateItem(Skala t, boolean bln) {
	                       super.updateItem(t, bln);
	                       
	                       if (t != null) {
	                           setText(t.getName());
	                       }
	                   }

	               };

	               return cell;
	           }
	       });
	       
	       
	       
	       ObservableList<Skala> skalenOl = FXCollections.observableArrayList();
	       List<Skala> skalenList = skalenModel.getSkalen();
	       ListIterator<Skala> itskala = skalenList.listIterator();
	       
	       while (itskala.hasNext()) {

	           skalenOl.add( itskala.next() );;
	           }
	       

	       
	       skalaBox.setItems( skalenOl );
	       
	        
	       perspektiveList.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {

	           @Override
	           public ListCell<Perspektive> call(ListView<Perspektive> s) {

	               ListCell<Perspektive> cell = new ListCell<Perspektive>() {

	                   @Override
	                   protected void updateItem(Perspektive t, boolean bln) {
	                       super.updateItem(t, bln);
	                       if (t != null) {
	                           setText(t.getName());
	                       }
	                   }

	               };

	               return cell;
	           }
	       }); 
	        
	        
	        ObservableList<Perspektive> perspektivenOl = FXCollections.observableArrayList();
	        List<Perspektive> persList = perspektivenModel.getPerspektiven();
	        ListIterator<Perspektive> itpers = persList.listIterator();
	        
	        while (itpers.hasNext()) {

	            perspektivenOl.add( itpers.next() );
	            }
	        
	        
	        perspektiveList.setItems( perspektivenOl );
	      
	        

	        
	        
	        eigenschaftList.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {

	            @Override
	            public ListCell<Eigenschaft> call(ListView<Eigenschaft> s) {

	                ListCell<Eigenschaft> cell = new ListCell<Eigenschaft>() {

	                    @Override
	                    protected void updateItem(Eigenschaft t, boolean bln) {
	                        super.updateItem(t, bln);
	                        if (t != null) {
	                            setText(t.getName());
	                        }
	                    }

	                };

	                return cell;
	            }
	        }); 
	         
	         
	         ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();
	         List<Eigenschaft> eigenList = eigenschaftModel.getEigenschaften();
	         ListIterator<Eigenschaft> iteigenschaft = eigenList.listIterator();
	         
	         while (iteigenschaft.hasNext()) {

	             eigenschaftOl.add( iteigenschaft.next() );
	             }
	         
	         
	         eigenschaftList.setItems( eigenschaftOl );

	    
	    
	    
	    
		filter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {;
			    			
		
				Stage stage = (Stage) filter.getScene().getWindow();
				List<Handlungsfeld> result = handlungsfeldModel.getHandlungsfelderBy( true, 
				                                                                      true, 
				                                                                      perspektiveList.getSelectionModel().getSelectedItem() , 
				                                                                      eigenschaftList.getSelectionModel().getSelectedItem(), 
				                                                                      null, 
				                                                                      notiz.getText(), 
				                                                                      null );
				hauptfeldController.buildFilteredTreeTable( result );
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
