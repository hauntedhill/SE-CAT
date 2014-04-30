package de.hscoburg.evelin.secat.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
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

import de.hscoburg.evelin.secat.dao.TestService;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class HandlungsfeldController implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(HandlungsfeldController.class);

	@FXML
	private TreeTableView<Handlungsfeld> treeTable;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuItemLoad;

	@FXML
	private MenuItem menuItemSave;

	@FXML
	private MenuItem menuItemClose;

	@Autowired
	private TestService service;

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
					service.saveDBToFile(file);

				}
			}
		});

		menuItemClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				service.save();
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
					service.loadDBFromFile(file);
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

		// treeTable.getColumns().get(0).setCellValueFactory(new TreeItemPropertyValueFactory<Hauptfeld, String>("name"));

		((TreeTableColumn<Handlungsfeld, String>) treeTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Handlungsfeld, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Handlungsfeld, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getName());

						// return null;

					}
				});

		treeTable.setRowFactory(new Callback<TreeTableView<Handlungsfeld>, TreeTableRow<Handlungsfeld>>() {

			public TreeTableRow<Handlungsfeld> call(TreeTableView<Handlungsfeld> treeTableView) {

				final TreeTableRow<Handlungsfeld> row = new TreeTableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				MenuItem removeItem = new MenuItem("Remove");

				removeItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						// data.remove(row.getItem());
						// treeTblViewFiles.getSelectionModel().clearSelection();

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
				rowMenu.getItems().add(removeItem);
				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(rowMenu)
								.otherwise((ContextMenu) null));
				return row;
			}

		});

		// ObservableList<Hauptfeld> rows = FXCollections.observableArrayList();

		// rows.addAll(orderService.findOrders(orderSearchCriteria));

		// treeTable.set

		Handlungsfeld h = new Handlungsfeld();
		h.setId(1);
		h.setName("Hauptfelder");

		// em.persist(h);
		// em.flush();
		// em.close();

		Handlungsfeld h1 = new Handlungsfeld();
		h1.setId(2);
		h1.setName("test123");

		Handlungsfeld h2 = new Handlungsfeld();
		h2.setId(3);
		h2.setName("test456");

		TreeItem<Handlungsfeld> root = new TreeItem<Handlungsfeld>(h);

		root.getChildren().add(new TreeItem<Handlungsfeld>(h1));
		root.getChildren().add(new TreeItem<Handlungsfeld>(h2));

		// treeTable.setShowRoot(true);

		treeTable.setRoot(root);

		// service.save();

	}

	public void addHauptfeldToCurrentSelection(Handlungsfeld h) {
		treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(new TreeItem<Handlungsfeld>(h));

	}

	public TreeTableView<Handlungsfeld> getTreeTable() {
		return treeTable;
	}

	public void setTreeTable(TreeTableView<Handlungsfeld> treeTable) {
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

	// public TreeTableView<Hauptfeld> getTreeTable() {
	// return treeTable;
	// }
	//
	// public void setTreeTable(TreeTableView<Hauptfeld> treeTable) {
	// this.treeTable = treeTable;
	// }

}
