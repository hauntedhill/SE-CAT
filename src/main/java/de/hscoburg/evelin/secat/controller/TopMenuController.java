package de.hscoburg.evelin.secat.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class TopMenuController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuItemLoad;

	@FXML
	private MenuItem menuItemSave;

	@FXML
	private MenuItem menuItemClose;

	@Override
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
	}

}
