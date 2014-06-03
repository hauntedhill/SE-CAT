package de.hscoburg.evelin.secat.controller.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class TopMenuController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem eigenschaften;

	@FXML
	private MenuItem perspektive;

	@FXML
	private MenuItem skalen;

	@FXML
	private MenuItem handlungsfeld;

	@FXML
	private MenuItem sprache;

	@FXML
	private MenuItem faecher;

	@FXML
	private MenuItem lehrveranstaltungen;

	@FXML
	private MenuItem addFragebogen;

	@FXML
	private MenuItem showFragebogen;

	@Autowired
	private LayoutController layout;

	@FXML
	private MenuItem fragen;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		eigenschaften.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.EIGENSCHAFTEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		perspektive.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.PERSPEKTIVEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		handlungsfeld.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.HANDLUNGSFELD_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});
		skalen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.SKALEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});
		sprache.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.SPRACHE_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});
		faecher.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.FAECHER_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		lehrveranstaltungen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.LEHRVERANSTALTUNGEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		addFragebogen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.FRAGEBOGEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		showFragebogen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.SHOW_FRAGEBOGEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

		fragen.setOnAction(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			@Override
			public void handleAction(ActionEvent event) {
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.FRAGEN_FXML);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
			}
		});

	}

}
