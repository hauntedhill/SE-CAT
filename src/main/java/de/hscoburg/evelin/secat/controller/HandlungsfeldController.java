package de.hscoburg.evelin.secat.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class HandlungsfeldController implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(HandlungsfeldController.class);

	@FXML
	private TreeTableView<TreeItemWrapper> treeTable;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuItemLoad;

	@FXML
	private MenuItem menuItemSave;

	@FXML
	private MenuItem menuItemClose;

	@Autowired
	private HandlungsfeldDAO service;

	@Autowired
	private HandlungsfeldModel hauptfeldModel;

	public void initialize(URL location, ResourceBundle resources) {

		menuItemSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FLX files (*.flx)", "*.flx");
				fileChooser.getExtensionFilters().add(extFilter);

				Stage stage = (Stage) menuBar.getScene().getWindow();

				// Show save file dialog
				File file = fileChooser.showSaveDialog(stage);

				System.out.println(file);

				// java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
				if (file != null) {
					if (file.exists()) {
						file.delete();
					}
					// service.saveDBToFile(file);

				}
			}
		});

		menuItemClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		menuItemLoad.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private File file;

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FLX files (*.flx)", "*.flx");
				fileChooser.getExtensionFilters().add(extFilter);
				Stage stage = (Stage) menuBar.getScene().getWindow();

				file = fileChooser.showOpenDialog(stage);
			}

			@Override
			public void handleAction(ActionEvent event) {

				if (file != null) {
					// service.loadDBFromFile(file);
					// try {
					// Thread.sleep(1000);
					// throw new NullPointerException();
					// } catch (InterruptedException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			}
		});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getName());

					}
				});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getNotiz());

					}
				});

		treeTable.setRowFactory(new Callback<TreeTableView<TreeItemWrapper>, TreeTableRow<TreeItemWrapper>>() {

			public TreeTableRow<TreeItemWrapper> call(TreeTableView<TreeItemWrapper> treeTableView) {

				final TreeTableRow<TreeItemWrapper> row = new TreeTableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				final ContextMenu rowMenuHf = new ContextMenu();
				MenuItem addHfItem = new MenuItem("add Handlungsfeld");
				MenuItem activateHfItem = new MenuItem("activate Handlungsfeld");
				MenuItem activateItItem = new MenuItem("activate Item");
				MenuItem deactivateHfItem = new MenuItem("deactivate Handlungsfeld");
				MenuItem deactivateItItem = new MenuItem("deactivate Item");
				MenuItem addItItem = new MenuItem("add Item");
				MenuItem moveItems = new MenuItem("move Items");
				MenuItem filterItItem = new MenuItem("filter Item");

				addHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Stage stage = new Stage();

						Parent p = ((Parent) SpringFXMLLoader.getInstance().load("/gui/stammdaten/addHandlungsfeld.fxml"));

						Scene scene = new Scene(p);

						stage.setScene(scene);
						stage.show();

						stage.setOnHidden(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								logger.debug("Closing dialog stage.");

							}
						});

					}

				});

				addItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Stage stage = new Stage();

						Parent p = ((Parent) SpringFXMLLoader.getInstance().load("/gui/stammdaten/addItem.fxml"));

						Scene scene = new Scene(p);

						stage.setScene(scene);
						stage.show();

						stage.setOnHidden(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								logger.debug("Closing dialog stage.");

							}
						});

					}

				});

				deactivateHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Handlungsfeld h = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue()
								.getHandlungsfeld();
						h.setAktiv(false);
						hauptfeldModel.mergeHandlugsfeld(h);
					}

				});

				activateHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Handlungsfeld h = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue()
								.getHandlungsfeld();
						h.setAktiv(true);
						hauptfeldModel.mergeHandlugsfeld(h);
					}

				});

				deactivateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
			
						Item i = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getItem();
						i.setAktiv(false);
						hauptfeldModel.mergeItem(i);

					}

				});

				activateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
			
						Item i = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getItem();
						i.setAktiv(true);
						hauptfeldModel.mergeItem(i);
					}

				});
				
                filterItItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {

                        Stage stage = new Stage();

                        Parent p = ((Parent) SpringFXMLLoader.getInstance().load("/gui/stammdaten/filterItem.fxml"));

                        Scene scene = new Scene(p);

                        stage.setScene(scene);
                        stage.show();

                        stage.setOnHidden(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent we) {
                                logger.debug("Closing dialog stage.");

                            }
                        });

                    }

                });
                
                moveItems.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {

                        Stage stage = new Stage();

                        Parent p = ((Parent) SpringFXMLLoader.getInstance().load("/gui/stammdaten/moveItems.fxml"));

                        Scene scene = new Scene(p);

                        stage.setScene(scene);
                        stage.show();

                        stage.setOnHidden(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent we) {
                                logger.debug("Closing dialog stage.");

                            }
                        });

                    }

                });
                
                
                
				
				rowMenu.getItems().add(activateItItem);
				rowMenu.getItems().add(deactivateItItem);
				rowMenu.getItems().add(filterItItem);

				rowMenuHf.getItems().add(addHfItem);
				rowMenuHf.getItems().add(activateHfItem);
				rowMenuHf.getItems().add(deactivateHfItem);
				rowMenuHf.getItems().add(addItItem);
				rowMenuHf.getItems().add(moveItems);

			
				ObservableObjectValue<TreeItemWrapper> rowMenuObserver = new ObservableObjectValue<TreeItemWrapper>() {

					@Override
					public void addListener(InvalidationListener listener) {
						row.itemProperty().addListener(listener);

					}

					@Override
					public void removeListener(InvalidationListener listener) {
						row.itemProperty().removeListener(listener);

					}

					@Override
					public void addListener(ChangeListener<? super TreeItemWrapper> listener) {
						row.itemProperty().addListener(listener);

					}

					@Override
					public void removeListener(ChangeListener<? super TreeItemWrapper> listener) {
						row.itemProperty().removeListener(listener);

					}

					@Override
					public TreeItemWrapper getValue() {

						return (row.itemProperty() != null ? (row.itemProperty().getValue() != null ? (!row.itemProperty().getValue().isHandlungsfeld() ? row
								.itemProperty().getValue() : null) : null) : null);
					}

					@Override
					public TreeItemWrapper get() {
						// TODO Auto-generated method stub
						return (row.itemProperty().get() != null ? (row.itemProperty().get() != null ? (!row.itemProperty().get().isHandlungsfeld() ? row
								.itemProperty().get() : null) : null) : null);
					}
				};

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(rowMenuObserver)).then(rowMenu)
								.otherwise((ContextMenu) rowMenuHf));

				// row.contextMenuProperty().bindBidirectional(BidirectionalBinding.bind(javafx.beans.binding.Bindings.isNotNull(rowMenuObserver)).then(rowMenu)
				// .otherwise((ContextMenu) null),null);

				// row.contextMenuProperty().bind(
				// javafx.beans.binding.Bindings
				// .when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty() != null ? (row.itemProperty().getValue() != null ? (row
				// .itemProperty().getValue().isHandlungsfeld() ? new ReadOnlyObjectWrapper<String>("")
				// : new ReadOnlyObjectWrapper<String>(null)) : new ReadOnlyObjectWrapper<String>(null))
				// : new ReadOnlyObjectWrapper<String>(null))).then(rowMenuHf).otherwise((ContextMenu) null));
				// //
				// row.contextMenuProperty()
				// .bind(javafx.beans.binding.Bindings
				// .when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty().getValue() != null ? (!row.itemProperty().getValue()
				// .isHandlungsfeld() ? new ReadOnlyObjectWrapper<String>("") : null) : null)).then(rowMenu).otherwise((ContextMenu) null));

				return row;

			}

		});



		Handlungsfeld h = new Handlungsfeld();
		h.setId(1);
		h.setName("Handlungsfelder");

		List<Handlungsfeld> hf = hauptfeldModel.getHandlungsfelderBy(true, true);

		TreeItemWrapper t = new TreeItemWrapper(h);
		TreeItem<TreeItemWrapper> root = new TreeItem<TreeItemWrapper>(t);
		ListIterator<Handlungsfeld> it = hf.listIterator();
		while (it.hasNext()) {

			Handlungsfeld ha = it.next();
			List<Item> item = ha.getItems();
			TreeItemWrapper hawrapped = new TreeItemWrapper(ha);
			TreeItem<TreeItemWrapper> node = new TreeItem<TreeItemWrapper>(hawrapped);

			ListIterator<Item> iter = item.listIterator();
			while (iter.hasNext()) {
				TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next());
				node.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
			}

			root.getChildren().add(node);

		}

		root.setExpanded(true);
		treeTable.setRoot(root);

	}

	public void addHauptfeldToCurrentSelection(Handlungsfeld h) {
	    
	    hauptfeldModel.persistHandlungsfeld( h );
	    TreeItemWrapper t = new TreeItemWrapper(h);
	    System.out.println( treeTable.getRoot().getValue().getName());
	    if(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().equals( treeTable.getRoot().getValue() )){
	        treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(new TreeItem<TreeItemWrapper>(t));
	    }
	    else
	        treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren().add(new TreeItem<TreeItemWrapper>(t));

	}
	
	
	public void addItemToCurrentSelection(Item i) {
	    ArrayList<Item> list = new ArrayList<Item>();
	    list.add( i );
		i.setHandlungsfeld(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getHandlungsfeld());
		hauptfeldModel.persistItem(i);
		TreeItemWrapper t = new TreeItemWrapper(i);
		treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getHandlungsfeld().setItems( list );
		treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(new TreeItem<TreeItemWrapper>(t));

	}
	
	public void refreshHandlungsfeld(String name , List<Item> oldItem){
	    treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().remove( treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren() );
	    treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren().remove( treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()) );

	    ListIterator<TreeItem<TreeItemWrapper>> it =  treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren().listIterator();
	    while(it.hasNext()){
	        TreeItem<TreeItemWrapper> tmp = it.next();
	        System.out.println( tmp.getValue().getName() );
	        if(tmp.getValue().getHandlungsfeld().getName().equals( name )){
	            tmp.getChildren().remove( tmp.getChildren() );
	            
	            
	            List<Item> item = oldItem;
	            ListIterator<Item> iter = item.listIterator();
	            while (iter.hasNext()) {
	                TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next());
	                tmp.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
	            }
	            
	        }
	        
	            
	    }
	    
	  
	}
	
	public void filterItem( String notiz){
	    
	  
	    Handlungsfeld ha = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getValue().getHandlungsfeld();
	    TreeItem<TreeItemWrapper> parent = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent();
	    
	    
	    treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren().removeAll( 
	           treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren() );
	    
	    List<Item> item = ha.getItems();
        ListIterator<Item> iter = item.listIterator();
        while (iter.hasNext()) {
            TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next());
            System.out.println( itwrapped.getNotiz() );
            if(notiz != null && !notiz.equals( "" )){
                if(itwrapped.getNotiz().equals( notiz )){
                    parent.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
                    }
            }
            else{
                parent.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
            }
            
            
        }
	    
	  
	}
	
	
	public TreeTableView<TreeItemWrapper> getTreeTable() {
		return treeTable;
	}

	public void setTreeTable(TreeTableView<TreeItemWrapper> treeTable) {
		this.treeTable = treeTable;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public MenuItem getMenuItemLoad() {
		return menuItemLoad;
	}

	public void setMenuItemLoad(MenuItem menuItemLoad) {
		this.menuItemLoad = menuItemLoad;
	}

	public MenuItem getMenuItemSave() {
		return menuItemSave;
	}

	public void setMenuItemSave(MenuItem menuItemSave) {
		this.menuItemSave = menuItemSave;
	}
	
	public void buildTreeTable(){
	        Handlungsfeld h = new Handlungsfeld();
	        h.setId(1);
	        h.setName("Handlungsfelder");

	        List<Handlungsfeld> hf = hauptfeldModel.getHandlungsfelderBy(true, true);

	        TreeItemWrapper t = new TreeItemWrapper(h);
	        TreeItem<TreeItemWrapper> root = new TreeItem<TreeItemWrapper>(t);
	        ListIterator<Handlungsfeld> it = hf.listIterator();
	        while (it.hasNext()) {

	            Handlungsfeld ha = it.next();
	            List<Item> item = ha.getItems();
	            TreeItemWrapper hawrapped = new TreeItemWrapper(ha);
	            TreeItem<TreeItemWrapper> node = new TreeItem<TreeItemWrapper>(hawrapped);

	            ListIterator<Item> iter = item.listIterator();
	            while (iter.hasNext()) {
	                TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next());
	                node.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
	            }

	            root.getChildren().add(node);

	        }

	        root.setExpanded(true);
	        treeTable.setRoot(root);
	    
	}


}
