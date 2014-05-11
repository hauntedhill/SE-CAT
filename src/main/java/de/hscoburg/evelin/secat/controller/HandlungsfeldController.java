package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
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
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.TreeItemWrapper;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class HandlungsfeldController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HandlungsfeldController.class);

	@FXML
	private TreeTableView<TreeItemWrapper> treeTable;

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu menuFilter;

	@FXML
	private MenuItem menuItemFilterHandlungsfeld;

	@FXML
	private MenuItem menuItemFilterItem;

	@Autowired
	private HandlungsfeldDAO service;

	@Autowired
	private HandlungsfeldModel hauptfeldModel;

	private static boolean inaktiv = false;

	public void initializeController(URL location, ResourceBundle resources) {

		menuItemFilterHandlungsfeld.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		menuItemFilterItem.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getName());

					}
				});

		((TreeTableColumn<TreeItemWrapper, Node>) treeTable.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, Node>, ObservableValue<Node>>() {

					public ObservableValue<Node> call(CellDataFeatures<TreeItemWrapper, Node> p) {
						if (p.getValue().getValue().isAktive()) {
							return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true)));
						} else {
							return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true, true)));
						}

					}
				});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getNotiz());

					}
				});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(3))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getSkala());

					}
				});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(4))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {

						StringBuilder b = new StringBuilder();
						for (Eigenschaft e : p.getValue().getValue().getEigenschaften()) {
							if (b.length() != 0) {
								b.append(", ");
							}
							b.append(e.getName());
						}

						return new ReadOnlyObjectWrapper<String>(b.toString());

					}
				});

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(5))
				.setCellValueFactory(new Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<TreeItemWrapper, String> p) {

						StringBuilder b = new StringBuilder();
						for (Perspektive e : p.getValue().getValue().getPerspektiven()) {
							if (b.length() != 0) {
								b.append(", ");
							}
							b.append(e.getName());
						}

						return new ReadOnlyObjectWrapper<String>(b.toString());

					}
				});

		treeTable.setRowFactory(new Callback<TreeTableView<TreeItemWrapper>, TreeTableRow<TreeItemWrapper>>() {

			public TreeTableRow<TreeItemWrapper> call(TreeTableView<TreeItemWrapper> treeTableView) {

				final TreeTableRow<TreeItemWrapper> row = new TreeTableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				final ContextMenu rowMenuHf = new ContextMenu();
				MenuItem addHfItem = new MenuItem("add Handlungsfeld", new ImageView(new Image("/image/icons/add_hand.png", 16, 16, true, true)));
				MenuItem activateHfItem = new MenuItem("activate Handlungsfeld", new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true)));
				MenuItem activateItItem = new MenuItem("activate Item", new ImageView(new Image("/image/icons/bookmark.png", 16, 16, true, true)));
				MenuItem deactivateHfItem = new MenuItem("deactivate Handlungsfeld", new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true,
						true)));
				MenuItem deactivateItItem = new MenuItem("deactivate Item", new ImageView(new Image("/image/icons/bookmark_Silver.png", 16, 16, true, true)));
				MenuItem addItItem = new MenuItem("add Item", new ImageView(new Image("/image/icons/add_item.png", 16, 16, true, true)));
				MenuItem moveItems = new MenuItem("move Items", new ImageView(new Image("/image/icons/up.png", 16, 16, true, true)));
				MenuItem filterItItem = new MenuItem("filter Item", new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));

				addHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/addHandlungsfeld.fxml");

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
						if (treeTable.getSelectionModel().getSelectedItem().getValue().getHandlungsfeld().getId() != -1) {

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

				deactivateHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (treeTable.getSelectionModel().getSelectedItem().getValue().getHandlungsfeld().getId() != -1) {
							Handlungsfeld h = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue()
									.getHandlungsfeld();
							h.setAktiv(false);
							hauptfeldModel.mergeHandlugsfeld(h);
							int index = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
									.indexOf(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()));

							treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
									.set(index, createNode(new TreeItemWrapper(h)));

						}
					}
				});

				activateHfItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (treeTable.getSelectionModel().getSelectedItem().getValue().getHandlungsfeld().getId() != -1) {
							Handlungsfeld h = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue()
									.getHandlungsfeld();
							h.setAktiv(true);
							hauptfeldModel.mergeHandlugsfeld(h);
							int index = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
									.indexOf(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()));
							treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
									.set(index, createNode(new TreeItemWrapper(h)));

						}
					}
				});

				deactivateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Item i = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getItem();
						i.setAktiv(false);
						hauptfeldModel.mergeItem(i);

						int index = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
								.indexOf(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()));

						treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
								.set(index, createNode(new TreeItemWrapper(i)));
					}

				});

				activateItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Item i = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getItem();
						i.setAktiv(true);
						hauptfeldModel.mergeItem(i);
						int index = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
								.indexOf(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()));

						treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
								.set(index, createNode(new TreeItemWrapper(i)));

					}

				});
				filterItItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (!treeTable.getSelectionModel().getSelectedItem().getValue().getName().equals("Handlungsfelder")) {

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

				moveItems.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (treeTable.getSelectionModel().getSelectedItem().getValue().getHandlungsfeld().getId() != -1) {

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

				menuItemFilterItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/stammdaten/filterAllItems.fxml");

						stage.show();

						stage.setOnHidden(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								logger.debug("Closing dialog stage.");

							}
						});

					}

				});

				menuItemFilterHandlungsfeld.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						if (inaktiv == false) {
							inaktiv = true;
							buildFilteredTreeTable(hauptfeldModel.getHandlungsfelderBy(false, false), false, false);
						} else {

							inaktiv = false;
							buildFilteredTreeTable(hauptfeldModel.getHandlungsfelderBy(true, true), true, true);

						}

					}

				});

				rowMenu.getItems().add(activateItItem);
				rowMenu.getItems().add(deactivateItItem);

				rowMenuHf.getItems().add(addHfItem);
				rowMenuHf.getItems().add(activateHfItem);
				rowMenuHf.getItems().add(deactivateHfItem);
				rowMenuHf.getItems().add(addItItem);
				rowMenuHf.getItems().add(moveItems);
				// rowMenuHf.getItems().add(filterItItem);

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
				//
				// row.contextMenuProperty()
				// .bind(javafx.beans.binding.Bindings
				// .when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty().getValue() != null ? (!row.itemProperty().getValue()
				// .isHandlungsfeld() ? new ReadOnlyObjectWrapper<String>("") : null) : null)).then(rowMenu).otherwise((ContextMenu) null));

				return row;

			}

		});

		this.buildTreeTable();

	}

	public void addHauptfeldToCurrentSelection(Handlungsfeld h) {

		hauptfeldModel.persistHandlungsfeld(h);

		if (treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().equals(treeTable.getRoot().getValue())) {
			treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(createNode(new TreeItemWrapper(h)));
		} else
			treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getParent().getChildren()
					.add(createNode(new TreeItemWrapper(h)));

	}

	public void filterItem(String notiz) {

		Handlungsfeld ha = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getValue().getHandlungsfeld();
		TreeItem<TreeItemWrapper> parent = treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex());

		treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren()
				.removeAll(treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren());

		List<Item> item = ha.getItems();
		ListIterator<Item> iter = item.listIterator();
		while (iter.hasNext()) {
			TreeItemWrapper itwrapped = new TreeItemWrapper(iter.next());
			if (notiz != null && !notiz.equals("")) {
				if (itwrapped.getNotiz().equals(notiz)) {
					parent.getChildren().add(new TreeItem<TreeItemWrapper>(itwrapped));
				}
			} else {
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

	// public MenuBar getMenuBar() {
	// return menuBar;
	// }
	//
	// public void setMenuBar(MenuBar menuBar) {
	// this.menuBar = menuBar;
	// }
	//
	// public MenuItem getMenuItemLoad() {
	// return menuItemLoad;
	// }
	//
	// public void setMenuItemLoad(MenuItem menuItemLoad) {
	// this.menuItemLoad = menuItemLoad;
	// }
	//
	// public MenuItem getMenuItemSave() {
	// return menuItemSave;
	// }
	//
	// public void setMenuItemSave(MenuItem menuItemSave) {
	// this.menuItemSave = menuItemSave;
	// }
	public void buildTreeTable() {
		Handlungsfeld h = new Handlungsfeld();
		h.setId(-1);
		h.setName("Handlungsfelder");
		h.setAktiv(true);
		List<Handlungsfeld> hf = hauptfeldModel.getHandlungsfelderBy(true, null);
		/*
		 * if (inaktiv == false) { hf = hauptfeldModel.getHandlungsfelderBy(true, true); } else {
		 * 
		 * hf = hauptfeldModel.getHandlungsfelderBy(false, false); }
		 */
		TreeItemWrapper t = new TreeItemWrapper(h);
		TreeItem<TreeItemWrapper> root = createNode(t);
		ListIterator<Handlungsfeld> it = hf.listIterator();
		while (it.hasNext()) {

			root.getChildren().add(createNode(new TreeItemWrapper(it.next())));
		}

		root.setExpanded(true);
		treeTable.setRoot(root);

	}

	public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv) {
		buildFilteredTreeTable(hfList, handlungsfeldAktiv, itemAktiv, null, null, null, null, null);
	}

	public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv, Perspektive p, Eigenschaft e,

	String notizHandlungsfeld, String notizItem, Fach f) {

		Handlungsfeld h = new Handlungsfeld();
		h.setId(-1);
		h.setName("Handlungsfelder");
		h.setAktiv(true);
		TreeItemWrapper t = new TreeItemWrapper(h);
		TreeItem<TreeItemWrapper> root = new TreeItem<TreeItemWrapper>(t);

		ListIterator<Handlungsfeld> it = hfList.listIterator();
		while (it.hasNext()) {

			Handlungsfeld ha = it.next();
			List<Item> item = hauptfeldModel.getItemBy(ha, itemAktiv, p, e, notizHandlungsfeld, notizItem, f);
			ha.setItems(item);

			root.getChildren().add(createNode(new TreeItemWrapper(ha)));

		}

		root.setExpanded(true);
		treeTable.setRoot(root);

	}

	@Override
	public String getSceneName() {

		return "Handlungsfelder pflegen";
	}

	public TreeItem<TreeItemWrapper> createNode(final TreeItemWrapper t) {

		if (!t.isHandlungsfeld()) {
			return new TreeItem<TreeItemWrapper>(t, new ImageView(new Image("/image/icons/filenew.png", 16, 16, true, true)));
		}
		return new TreeItem<TreeItemWrapper>(t, new ImageView(new Image("/image/icons/share.png", 16, 16, true, true))) {

			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override
			public ObservableList<TreeItem<TreeItemWrapper>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;

					// First getChildren() call, so we actually go off and
					// determine the children of the File contained in this TreeItem.
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					TreeItemWrapper t = (TreeItemWrapper) getValue();
					isLeaf = !t.isHandlungsfeld();
				}

				return isLeaf;
			}

			private ObservableList<TreeItem<TreeItemWrapper>> buildChildren(TreeItem<TreeItemWrapper> TreeItem) {

				TreeItemWrapper t = TreeItem.getValue();

				if (t != null && t.isHandlungsfeld()) {
					List<Item> items = t.getHandlungsfeld().getItems();
					if (items != null) {
						ObservableList<TreeItem<TreeItemWrapper>> children = FXCollections.observableArrayList();

						for (Item child : items) {

							children.add(createNode(new TreeItemWrapper(child)));
						}

						return children;
					}
				}

				return FXCollections.emptyObservableList();
			}
		};
	}

}
