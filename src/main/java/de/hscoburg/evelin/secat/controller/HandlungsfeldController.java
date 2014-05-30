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

	@FXML
	private MenuBar menuBar;


	@Autowired
	private HandlungsfeldDAO service;

	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private TreeTableController treeTableController;


	public void initializeController(URL location, ResourceBundle resources) {


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


	}


	@Override
	public String getKeyForSceneName() {

		return "scene.handlungsfeld.lable.title";
	}
}
