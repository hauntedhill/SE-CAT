package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class TopMenuController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem eigenschaften;

	@FXML
	private MenuItem perspektive;

	@FXML
	private MenuItem handlungsfeld;

	@Autowired
	private LayoutController layout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		eigenschaften.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) {
				layout.setCenterNode(LayoutController.EIGENSCHAFTEN_FXML);
			}

			@Override
			public void handleAction(ActionEvent event) {

			}
		});

		perspektive.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) {
				layout.setCenterNode(LayoutController.PERSPEKTIVEN_FXML);
			}

			@Override
			public void handleAction(ActionEvent event) {

			}
		});

		handlungsfeld.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void performBeforeEventsBlocked(ActionEvent event) {
				layout.setCenterNode(LayoutController.HANDLUNGSFELD_FXML);
			}

			@Override
			public void handleAction(ActionEvent event) {

			}
		});
	}

}
