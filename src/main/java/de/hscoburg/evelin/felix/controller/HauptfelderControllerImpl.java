package de.hscoburg.evelin.felix.controller;

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

import de.hscoburg.evelin.felix.FelixEventHandle;
import de.hscoburg.evelin.felix.model.Hauptfeld;
import de.hscoburg.evelin.felix.services.TestService;
import de.hscoburg.evelin.felix.util.spring.SpringFXMLLoader;

@Controller
public class HauptfelderControllerImpl implements Initializable {

	private static Logger logger = LoggerFactory.getLogger(HauptfelderControllerImpl.class);

	@FXML
	private TreeTableView<Hauptfeld> treeTable;

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

		menuItemLoad.setOnAction(new FelixEventHandle<ActionEvent>() {

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
					try {
						Thread.sleep(1000);
						throw new NullPointerException();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// treeTable.getColumns().get(0).setCellValueFactory(new TreeItemPropertyValueFactory<Hauptfeld, String>("name"));

		((TreeTableColumn<Hauptfeld, String>) treeTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Hauptfeld, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Hauptfeld, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().getName());

						// return null;

					}
				});

		treeTable.setRowFactory(new Callback<TreeTableView<Hauptfeld>, TreeTableRow<Hauptfeld>>() {

			public TreeTableRow<Hauptfeld> call(TreeTableView<Hauptfeld> treeTableView) {

				final TreeTableRow<Hauptfeld> row = new TreeTableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				MenuItem removeItem = new MenuItem("Remove");

				removeItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						// data.remove(row.getItem());
						// treeTblViewFiles.getSelectionModel().clearSelection();

						Stage stage = new Stage();

						Parent p = ((Parent) SpringFXMLLoader.getInstance().load("/addHauptfeld.fxml"));

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

		Hauptfeld h = new Hauptfeld();
		h.setId(1);
		h.setName("Hauptfelder");

		// em.persist(h);
		// em.flush();
		// em.close();

		Hauptfeld h1 = new Hauptfeld();
		h1.setId(2);
		h1.setName("test123");

		Hauptfeld h2 = new Hauptfeld();
		h2.setId(3);
		h2.setName("test456");

		TreeItem<Hauptfeld> root = new TreeItem<Hauptfeld>(h);

		root.getChildren().add(new TreeItem<Hauptfeld>(h1));
		root.getChildren().add(new TreeItem<Hauptfeld>(h2));

		// treeTable.setShowRoot(true);

		treeTable.setRoot(root);

		service.save();

	}

	public void addHauptfeldToCurrentSelection(Hauptfeld h) {
		treeTable.getSelectionModel().getModelItem(treeTable.getSelectionModel().getSelectedIndex()).getChildren().add(new TreeItem<Hauptfeld>(h));

	}

	public TreeTableView<Hauptfeld> getTreeTable() {
		return treeTable;
	}

	public void setTreeTable(TreeTableView<Hauptfeld> treeTable) {
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
