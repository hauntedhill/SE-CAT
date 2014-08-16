package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
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
import de.hscoburg.evelin.secat.controller.helper.TreeItemWrapper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zur Anzeige des Treetableviews
 * 
 * @author moro1000
 * 
 */
@Controller
public class TreeTableController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HandlungsfeldController.class);

	@FXML
	private TreeTableView<TreeItemWrapper> treeTable;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@FXML
	private Menu menuFilter;

	@FXML
	private MenuItem menuItemFilterHandlungsfeld;

	@FXML
	private MenuItem menuItemFilterItem;

	@FXML
	private MenuItem menuItemFilterOff;

	private static boolean inaktiv = false;

	/**
	 * Initialisierierung
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		menuItemFilterHandlungsfeld.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		menuItemFilterItem.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		menuItemFilterOff.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));

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

		/*
		 * ((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(3)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<TreeItemWrapper, String>, ObservableValue<String>>() { public ObservableValue<String>
		 * call(CellDataFeatures<TreeItemWrapper, String> p) { return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getSkala());
		 * } });
		 */

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(3))
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

		((TreeTableColumn<TreeItemWrapper, String>) treeTable.getColumns().get(4))
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

		buildTreeTable();

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
					buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(false, false), false, false);
				} else {

					inaktiv = false;
					buildFilteredTreeTable(handlungsfeldModel.getHandlungsfelderBy(true, true), true, true);

				}

			}

		});

		menuItemFilterOff.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				inaktiv = false;
				buildTreeTable();
			}

		});

	}

	/**
	 * Methode fügt neues Handlungsfeld hinzu.
	 * 
	 * @param h
	 *            {@link Handlungsfeld} - das einzufügende Handlungsfeld
	 */
	public void addHandlungsfeldToCurrentSelection(Handlungsfeld h) {

		if (getSelectedTreeItem().getValue().equals(treeTable.getRoot().getValue())) {
			getSelectedTreeItem().getChildren().add(createNode(new TreeItemWrapper(h)));
		} else
			getSelectedTreeItem().getParent().getChildren().add(createNode(new TreeItemWrapper(h)));

	}

	/**
	 * Methode erstellt und initialiert die Treetableview
	 * 
	 */
	public void buildTreeTable() {
		Handlungsfeld h = new Handlungsfeld();
		h.setId(-1);
		h.setName("Handlungsfelder");
		h.setAktiv(true);
		List<Handlungsfeld> hf = handlungsfeldModel.getHandlungsfelderBy(true, null);
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

	/**
	 * Methode initialisiert und erzeugt die Treetableview
	 * 
	 * 
	 * @param hfList
	 *            {@link List} mit {@link Handlungsfeld}er
	 * @param handlungsfeldAktiv
	 *            {@link boolean}
	 * @param itemAktiv
	 *            {@link boolean}
	 */
	public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv) {
		buildFilteredTreeTable(hfList, handlungsfeldAktiv, itemAktiv, null, null, null, null, null);
	}

	/**
	 * Methode initialisiert und erzeugt die gefilterte Treetableview
	 * 
	 * 
	 * @param hfList
	 *            {@link List} mit {@link Handlungsfeld}er
	 * @param handlungsfeldAktiv
	 *            {@link boolean}
	 * @param itemAktiv
	 *            {@link boolean}
	 * @param p
	 *            {@link Perspektive}
	 * @param e
	 *            {@link Eigenschaft}
	 * @param notizHandlungsfeld
	 *            {@link String}
	 * @param notizItem
	 *            {@link String}
	 * @param f
	 *            {@link Fach}
	 */
	public void buildFilteredTreeTable(List<Handlungsfeld> hfList, boolean handlungsfeldAktiv, boolean itemAktiv, Perspektive p, Eigenschaft e,

	String notizHandlungsfeld, String notizItem, Fach f) {

		Handlungsfeld h = new Handlungsfeld();
		h.setId(-1);
		h.setName("Handlungsfelder");
		h.setAktiv(true);
		TreeItemWrapper t = new TreeItemWrapper(h);
		TreeItem<TreeItemWrapper> root = new TreeItem<TreeItemWrapper>(t);

		for (Handlungsfeld hf : hfList) {
			List<Bereich> bereiche = hf.getBereiche();
			for (Bereich bereich : bereiche) {
				bereich.setItems(handlungsfeldModel.getItemBy(bereich, itemAktiv, p, e, notizHandlungsfeld, notizItem, f));

			}
			hf.setBereiche(bereiche);
			root.getChildren().add(createNode(new TreeItemWrapper(hf)));
		}
		root.setExpanded(true);
		treeTable.setRoot(root);

	}

	/**
	 * Methode gibt die TreeTableView zurück.
	 * 
	 * @return {@link TreeTableView}
	 */
	public TreeTableView<TreeItemWrapper> getTreeTable() {
		return treeTable;
	}

	/**
	 * Methode setzt die Treetableview.
	 * 
	 * @param treeTable
	 *            {@link TreeTableView}
	 */
	public void setTreeTable(TreeTableView<TreeItemWrapper> treeTable) {
		this.treeTable = treeTable;
	}

	/**
	 * Methode gibt das ausgewählte TreeItem zurück.
	 * 
	 * @return {@link TreeItem}
	 */
	public TreeItem<TreeItemWrapper> getSelectedTreeItem() {

		return treeTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Methode erzeugt ein TreeItem der Treetableview.
	 * 
	 * @param t
	 *            {@link TreeItemWrapper}
	 * 
	 * @return {@link TreeItem}
	 */
	public TreeItem<TreeItemWrapper> createNode(final TreeItemWrapper t) {

		if (t.isItem()) {
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

					super.getChildren().setAll(buildChildren(this));
				}

				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {

				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					TreeItemWrapper t = (TreeItemWrapper) getValue();
					if (t.isHandlungsfeld())
						isLeaf = !t.isHandlungsfeld();
					if (t.isBereich())
						isLeaf = !t.isBereich();
				}
				return isLeaf;
			}

			/**
			 * Methode erzeugt Kinder des TreeItems, wenn vorhanden.
			 * 
			 * @param TreeItem
			 *            {@link TreeItem}
			 * 
			 * 
			 * @return {@link ObservableList} mit {@link TreeItem}s
			 */
			private ObservableList<TreeItem<TreeItemWrapper>> buildChildren(TreeItem<TreeItemWrapper> TreeItem) {

				TreeItemWrapper t = TreeItem.getValue();

				if (t != null && t.isHandlungsfeld()) {
					List<Bereich> bereiche = t.getHandlungsfeld().getBereiche();

					if (bereiche != null) {
						ObservableList<TreeItem<TreeItemWrapper>> children = FXCollections.observableArrayList();

						for (Bereich child : bereiche) {
							TreeItem<TreeItemWrapper> bereich = createNode(new TreeItemWrapper(child));

							/*
							 * List<Item> items = child.getItems();
							 * 
							 * if (items != null && !items.isEmpty()) { ObservableList<TreeItem<TreeItemWrapper>> itemsOl =
							 * FXCollections.observableArrayList(); for (Item item : items) { itemsOl.add(createNode(new
							 * TreeItemWrapper(item))); } bereich.getChildren().addAll(itemsOl); //
							 * System.out.println(bereich.getChildren().get(0).getValue().getName()); }
							 */

							children.add(bereich);
						}

						return children;
					}
				}

				if (t != null && t.isBereich() && !isFirstTimeChildren) {
					List<Item> items = t.getBereich().getItems();
					if (items != null) {
						ObservableList<TreeItem<TreeItemWrapper>> children = FXCollections.observableArrayList();

						for (Item child : items) {
							TreeItem<TreeItemWrapper> item = createNode(new TreeItemWrapper(child));
							children.add(item);
						}

						return children;
					}
				}

				return FXCollections.emptyObservableList();
			}
		};
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addFragebogen.lable.title";
	}

	/**
	 * Methode gibts ausgewählte TreeItems der Treetableview zurück
	 * 
	 * @return {@link ObservableList} mit {@link TreeItem}s
	 */
	public ObservableList<TreeItem<TreeItemWrapper>> getSelectedTreeItemList() {

		return treeTable.getSelectionModel().getSelectedItems();
	}

	/**
	 * Methode setzt den SelectionMode der Treetableview
	 * 
	 * @param arg0
	 *            {@link SelectionMode}
	 */
	public void setSelectionMode(SelectionMode arg0) {

		treeTable.getSelectionModel().setSelectionMode(arg0);
	}

	/**
	 * Methode setzt Rowfactory der Treetableview
	 * 
	 * @param arg0
	 *            Callback {@link TreeTableView},{@link TreeItemWrapper}, {@link TreeTableRow},{@link TreeItemWrapper}
	 */
	public void setRowFactory(Callback<TreeTableView<TreeItemWrapper>, TreeTableRow<TreeItemWrapper>> arg0) {

		treeTable.setRowFactory(arg0);
	}

	/**
	 * Methode aktualisiert das Handlungsfeldund Bereich
	 * 
	 * @param hf
	 *            {@link int} Index der Handlungsfeld
	 * @param b
	 *            {@link int} Index der Bereich
	 */
	public void updateHandlungsfeld(int hf, int b) {
		buildTreeTable();
		treeTable.getRoot().getChildren().get(hf).setExpanded(true);
		if (b != -1) {
			treeTable.getRoot().getChildren().get(hf).getChildren().get(b).setExpanded(true);
		}
	}

}