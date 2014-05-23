package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class HandlungsfeldController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HandlungsfeldController.class);

	// @FXML
	// private TreeTableView<TreeItemWrapper> treeTable;

	@FXML
	private MenuBar menuBar;

	/*
	 * @FXML private Menu menuFilter;
	 * 
	 * @FXML private MenuItem menuItemFilterHandlungsfeld;
	 * 
	 * @FXML private MenuItem menuItemFilterItem;
	 * 
	 * @FXML private MenuItem menuItemFilterOff;
	 */

	@Autowired
	private HandlungsfeldDAO service;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private TreeTableController treeTableController;

	// private static boolean inaktiv = false;

	public void initializeController(URL location, ResourceBundle resources) {

		/*
		 * menuItemFilterHandlungsfeld.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		 * menuItemFilterItem.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		 * menuItemFilterOff.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		 */

		/*
		 * ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(0)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) { return new
		 * ReadOnlyObjectWrapper<String>(p.getValue().getValue().getName());
		 * 
		 * } });
		 * 
		 * ((TreeTableColumn<TreeItemWrapper, Node>) treeTable.getColumns().get(1)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, Node>, ObservableValue<Node>>() {
		 * 
		 * public ObservableValue<Node> call(CellDataFeatures<TreeItemWrapper, Node> p) { if (p.getValue().getValue().isAktive()) { return
		 * new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true))); } else { return new
		 * ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true, true))); }
		 * 
		 * } });
		 * 
		 * ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(2)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) { return new
		 * ReadOnlyObjectWrapper<String>(p.getValue().getValue().getNotiz());
		 * 
		 * } });
		 * 
		 * /* ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(3)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() { public ObservableValue<String>
		 * call(CellDataFeatures<TreeItemWrapper, String> p) { return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getSkala());
		 * } });
		 */

		/*
		 * ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(3)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
		 * 
		 * StringBuilder b = new StringBuilder(); for (Eigenschaft e : p.getValue().getValue().getEigenschaften()) { if (b.length() != 0) {
		 * b.append(", "); } b.append(e.getName()); }
		 * 
		 * return new ReadOnlyObjectWrapper<String>(b.toString());
		 * 
		 * } });
		 * 
		 * ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(4)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
		 * 
		 * StringBuilder b = new StringBuilder(); for (Perspektive e : p.getValue().getValue().getPerspektiven()) { if (b.length() != 0) {
		 * b.append(", "); } b.append(e.getName()); }
		 * 
		 * return new ReadOnlyObjectWrapper<String>(b.toString());
		 * 
		 * } });
		 */

		treeTableController.setRowFactory(new Callback<TreeTableView<TreeItemWrapper>, TreeTableRow<TreeItemWrapper>>() {

			public TreeTableRow<TreeItemWrapper> call(TreeTableView<TreeItemWrapper> treeTableView) {

				final TreeTableRow<TreeItemWrapper> row = new TreeTableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				final ContextMenu rowMenuHf = new ContextMenu();

				MenuItem addHfItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.addHfItem"), new ImageView(
						new Image("/image/icons/add_hand.png", 16, 16, true, true)));
				MenuItem addBereichItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.addBereichItem"),
						new ImageView(new Image("/image/icons/add_hand.png", 16, 16, true, true)));
				MenuItem activateHfItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.activateHfItem"),
						new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true)));
				MenuItem activateItItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.activateItItem"),
						new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true)));
				MenuItem editItItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.editItItem"), new ImageView(
						new Image("/image/icons/edit.png", 16, 16, true, true)));
				MenuItem deactivateHfItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.deactivateHfItem"),
						new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true, true)));
				MenuItem deactivateItItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.deactivateItItem"),
						new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true, true)));
				MenuItem addItItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.addItItem"), new ImageView(
						new Image("/image/icons/add_item.png", 16, 16, true, true)));
				MenuItem moveItems = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.moveItems"), new ImageView(
						new Image("/image/icons/up.png", 16, 16, true, true)));
				MenuItem filterItItem = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.handlungsfeld.ctxmenue.filterItItem"), new ImageView(
						new Image("/image/icons/viewmag.png", 16, 16, true, true)));

				addHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (treeTableController.getSelectedTreeItem().getValue().isHandlungsfeld()) {

							Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addHandlungsfeld.fxml");

							stage.show();

							stage.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									logger.debug("Closing dialog stage.");

								}
							});
						}
					}

				});

				addItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (selectedTreeItem.getValue().isBereich()) {
							Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addItem.fxml");

							stage.show();

							stage.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									logger.debug("Closing dialog stage.");

								}
							});
						}
					}
				});

				editItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/editItem.fxml");

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
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (selectedTreeItem.getValue().isHandlungsfeld() && selectedTreeItem.getValue().getHandlungsfeld().getId() != -1) {
							Handlungsfeld h = selectedTreeItem.getValue().getHandlungsfeld();
							h.setAktiv(false);
							handlungsfeldModel.mergeHandlugsfeld(h);
							int index = selectedTreeItem.getParent().getChildren().indexOf(selectedTreeItem);
							selectedTreeItem.getParent().getChildren().set(index, treeTableController.createNode(new TreeItemWrapper(h)));

						}
					}
				});

				activateHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (selectedTreeItem.getValue().isHandlungsfeld() && selectedTreeItem.getValue().getHandlungsfeld().getId() != -1) {
							Handlungsfeld h = selectedTreeItem.getValue().getHandlungsfeld();
							h.setAktiv(true);
							handlungsfeldModel.mergeHandlugsfeld(h);
							int index = selectedTreeItem.getParent().getChildren().indexOf(selectedTreeItem);
							selectedTreeItem.getParent().getChildren().set(index, treeTableController.createNode(new TreeItemWrapper(h)));
						}
					}
				});

				deactivateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						Item i = selectedTreeItem.getValue().getItem();
						i.setAktiv(false);
						handlungsfeldModel.mergeItem(i);
						int index = selectedTreeItem.getParent().getChildren().indexOf(selectedTreeItem);
						selectedTreeItem.getParent().getChildren().set(index, treeTableController.createNode(new TreeItemWrapper(i)));
					}

				});

				activateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						Item i = selectedTreeItem.getValue().getItem();
						i.setAktiv(true);
						handlungsfeldModel.mergeItem(i);
						int index = selectedTreeItem.getParent().getChildren().indexOf(selectedTreeItem);
						selectedTreeItem.getParent().getChildren().set(index, treeTableController.createNode(new TreeItemWrapper(i)));

					}

				});
				filterItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (!treeTableController.getTreeTable().getSelectionModel().getSelectedItem().getValue().getName().equals("Handlungsfelder")) {

							Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/filterItem.fxml");

							stage.show();

							stage.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									logger.debug("Closing dialog stage.");

								}
							});
						}
					}

				});

				addBereichItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (selectedTreeItem.getValue().isHandlungsfeld() && selectedTreeItem.getValue().getHandlungsfeld().getId() != -1) {

							Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addBereich.fxml");

							stage.show();

							stage.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									logger.debug("Closing dialog stage.");

								}
							});

						}
					}
				});

				moveItems.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						TreeItem<TreeItemWrapper> selectedTreeItem = treeTableController.getSelectedTreeItem();
						if (selectedTreeItem.getValue().isHandlungsfeld() && selectedTreeItem.getValue().getHandlungsfeld().getId() != -1) {

							Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/moveItems.fxml");

							stage.show();

							stage.setOnHidden(new EventHandler<WindowEvent>() {
								public void handle(WindowEvent we) {
									logger.debug("Closing dialog stage.");

								}
							});
						}
					}

				});

				/*
				 * menuItemFilterItem.setOnAction(new EventHandler<ActionEvent>() {
				 * 
				 * @Override public void handle(ActionEvent t) {
				 * 
				 * Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/filterAllItems.fxml");
				 * 
				 * stage.show();
				 * 
				 * stage.setOnHidden(new EventHandler<WindowEvent>() { public void handle(WindowEvent we) {
				 * logger.debug("Closing dialog stage.");
				 * 
				 * } });
				 * 
				 * }
				 * 
				 * });
				 * 
				 * menuItemFilterHandlungsfeld.setOnAction(new EventHandler<ActionEvent>() {
				 * 
				 * @Override public void handle(ActionEvent t) { if (inaktiv == false) { inaktiv = true;
				 * treeTableController.buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(false, false), false, false); } else {
				 * 
				 * inaktiv = false; treeTableController.buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(true, true), true,
				 * true);
				 * 
				 * }
				 * 
				 * }
				 * 
				 * });
				 * 
				 * menuItemFilterOff.setOnAction(new EventHandler<ActionEvent>() {
				 * 
				 * @Override public void handle(ActionEvent t) { inaktiv = false; treeTableController.buildTreeTable(); }
				 * 
				 * });
				 */

				rowMenu.getItems().add(activateItItem);
				rowMenu.getItems().add(deactivateItItem);
				rowMenu.getItems().add(editItItem);

				rowMenuHf.getItems().add(addHfItem);
				rowMenuHf.getItems().add(activateHfItem);
				rowMenuHf.getItems().add(deactivateHfItem);
				rowMenuHf.getItems().add(addBereichItem);
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

						return (row.itemProperty() != null ? (row.itemProperty().getValue() != null ? (!row.itemProperty().getValue().isHandlungsfeld() ? (!row
								.itemProperty().getValue().isBereich() ? row.itemProperty().getValue() : null) : null) : null) : null);
					}

					@Override
					public TreeItemWrapper get() {
						// TODO Auto-generated method stub
						return (row.itemProperty().get() != null ? (row.itemProperty().get() != null ? (!row.itemProperty().get().isHandlungsfeld() ? (!row
								.itemProperty().getValue().isBereich() ? row.itemProperty().get() : null) : null) : null) : null);
					}

				};

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(rowMenuObserver)).then(rowMenu)
								.otherwise((ContextMenu) rowMenuHf));

				return row;

			}

		});

		// buildTreeTable();

	}

	/*
	 * public void addHandlungsfeldToCurrentSelection(Handlungsfeld h) {
	 * 
	 * if
	 * (treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().equals(treeTable.getRoot()
	 * .getValue())) {
	 * treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(createNode(new
	 * TreeItemWrapper(h))); } else
	 * treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
	 * .add(createNode(new TreeItemWrapper(h)));
	 * 
	 * }
	 */
	/*
	 * public void filterItem(String notiz) {
	 * 
	 * Handlungsfeld ha =
	 * treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getHandlungsfeld();
	 * TreeItem<TreeItemWrapper> parent = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex());
	 * 
	 * treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren()
	 * .removeAll(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren()); //
	 * AUSKOMMENTIERT WEGEN NEUEN ENTITIES // List<Item> item = ha.getItems(); List<Item> item = new ArrayList<Item>(); ListIterator<Item>
	 * iter = item.listIterator(); while (iter.hasNext()) { TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next()); if (notiz != null
	 * && !notiz.equals("")) { if (itwrapped.getNotiz().equals(notiz)) { parent.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
	 * } } else { parent.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped)); }
	 * 
	 * }
	 * 
	 * }
	 */
	/*
	 * public TreeTableView<TreeItemWrapper> getTreeTable() { return treeTable; }
	 * 
	 * public void setTreeTable(TreeTableView<TreeItemWrapper> treeTable) { this.treeTable = treeTable; }
	 * 
	 * public void buildTreeTable() { Handlungsfeld h = new Handlungsfeld(); h.setId(-1); h.setName("Handlungsfelder"); h.setAktiv(true);
	 * List<Handlungsfeld> hf = handlungsfeldModel.getHandlungsfelderBy(true, null); TreeItemWrapper t = new TreeItemWrapper(h);
	 * TreeItem<TreeItemWrapper> root = createNode(t); ListIterator<Handlungsfeld> it = hf.listIterator(); while (it.hasNext()) {
	 * 
	 * root.getChildren().add(createNode(new TreeItemWrapper(it.next()))); }
	 * 
	 * root.setExpanded(true); treeTable.setRoot(root);
	 * 
	 * }
	 * 
	 * public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv) {
	 * buildFilteredTreeTable(hfList, handlungsfeldAktiv, itemAktiv, null, null, null, null, null); }
	 * 
	 * public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv, Perspektive p,
	 * Eigenschaft e,
	 * 
	 * String notizHandlungsfeld, String notizItem, Fach f) {
	 * 
	 * Handlungsfeld h = new Handlungsfeld(); h.setId(-1); h.setName("Handlungsfelder"); h.setAktiv(true); TreeItemWrapper t = new
	 * TreeItemWrapper(h); TreeItem<TreeItemWrapper> root = new TreeItem<TreeItemWrapper>(t);
	 * 
	 * for (Handlungsfeld hf : hfList) { List<Bereich> bereiche = hf.getBereiche(); for (Bereich bereich : bereiche) {
	 * bereich.setItems(handlungsfeldModel.getItemBy(bereich, itemAktiv, p, e, notizHandlungsfeld, notizItem, f)); // ONLY FOR TESTING
	 * List<Item> items = handlungsfeldModel.getItemBy(bereich, itemAktiv, p, e, notizHandlungsfeld, notizItem, f); for (Item i : items) {
	 * System.out.println(i.getName()); }
	 * 
	 * } hf.setBereiche(bereiche); root.getChildren().add(createNode(new TreeItemWrapper(hf))); }
	 * 
	 * root.setExpanded(true); treeTable.setRoot(root);
	 * 
	 * }
	 */
	/*
	 * public TreeItem<TreeItemWrapper> getSelectedTreeItem() {
	 * 
	 * return treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()); }
	 * 
	 * public TreeItem<TreeItemWrapper> createNode(final TreeItemWrapper t) {
	 * 
	 * if (t.isItem()) { return new TreeItem<TreeItemWrapper>(t, new ImageView(new Image("/image/icons/filenew.png", 16, 16, true, true)));
	 * } return new TreeItem<TreeItemWrapper>(t, new ImageView(new Image("/image/icons/share.png", 16, 16, true, true))) {
	 * 
	 * private boolean isLeaf; private boolean isFirstTimeChildren = true; private boolean isFirstTimeLeaf = true;
	 * 
	 * @Override public ObservableList<TreeItem<TreeItemWrapper>> getChildren() {
	 * 
	 * if (isFirstTimeChildren) { isFirstTimeChildren = false;
	 * 
	 * super.getChildren().setAll(buildChildren(this)); }
	 * 
	 * return super.getChildren(); }
	 * 
	 * @Override public boolean isLeaf() {
	 * 
	 * if (isFirstTimeLeaf) { isFirstTimeLeaf = false; TreeItemWrapper t = (TreeItemWrapper) getValue(); if (t.isHandlungsfeld()) isLeaf =
	 * !t.isHandlungsfeld(); if (t.isBereich()) isLeaf = !t.isBereich(); } return isLeaf; }
	 * 
	 * private ObservableList<TreeItem<TreeItemWrapper>> buildChildren(TreeItem<TreeItemWrapper> TreeItem) {
	 * 
	 * TreeItemWrapper t = TreeItem.getValue();
	 * 
	 * if (t != null && t.isHandlungsfeld()) { List<Bereich> bereiche = t.getHandlungsfeld().getBereiche();
	 * 
	 * if (bereiche != null) { ObservableList<TreeItem<TreeItemWrapper>> children = FXCollections.observableArrayList();
	 * 
	 * for (Bereich child : bereiche) { TreeItem<TreeItemWrapper> bereich = createNode(new TreeItemWrapper(child));
	 * 
	 * List<Item> items = child.getItems(); if (items != null && !items.isEmpty()) { ObservableList<TreeItem<TreeItemWrapper>> itemsOl =
	 * FXCollections.observableArrayList(); for (Item item : items) { itemsOl.add(createNode(new TreeItemWrapper(item))); }
	 * bereich.getChildren().addAll(itemsOl); // System.out.println(bereich.getChildren().get(0).getValue().getName()); }
	 * 
	 * children.add(bereich); }
	 * 
	 * return children; } }
	 * 
	 * if (t != null && t.isBereich() && isFirstTimeChildren) { List<Item> items = t.getBereich().getItems();
	 * 
	 * if (items != null) { ObservableList<TreeItem<TreeItemWrapper>> children = FXCollections.observableArrayList();
	 * 
	 * for (Item child : items) { TreeItem<TreeItemWrapper> item = createNode(new TreeItemWrapper(child)); children.add(item); }
	 * 
	 * return children; } }
	 * 
	 * return FXCollections.emptyObservableList(); } }; }
	 */

	@Override
	public String getKeyForSceneName() {

		return "scene.handlungsfeld.lable.title";
	}
}
