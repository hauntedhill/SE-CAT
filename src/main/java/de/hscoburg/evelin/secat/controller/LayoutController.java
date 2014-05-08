package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class LayoutController implements Initializable {

	private static final String GUI_PATH = "/gui/";

	private static final String STAMMDATEN_PATH = GUI_PATH + "stammdaten/";

	public static final String HANDLUNGSFELD_FXML = STAMMDATEN_PATH + "handlungsfeld.fxml";

	public static final String EIGENSCHAFTEN_FXML = STAMMDATEN_PATH + "Eigenschaften.fxml";

	public static final String PERSPEKTIVEN_FXML = STAMMDATEN_PATH + "Perspektiven.fxml";

	public static final String SKALEN_FXML = STAMMDATEN_PATH + "Skalen.fxml";

	public static final String TOPMENU_FXML = GUI_PATH + "Topmenu.fxml";

	public static final String SPLASHSCREEN_FXML = GUI_PATH + "splashScreen.fxml";

	public static final String LAYOUT_FXML = GUI_PATH + "Layout.fxml";

	@FXML
	private BorderPane layout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		layout.setTop((Node) SpringFXMLLoader.getInstance().load(TOPMENU_FXML));

		setCenterNode(HANDLUNGSFELD_FXML);

	}

	public void setCenterNode(String url) {
		layout.setCenter((Node) SpringFXMLLoader.getInstance().load(url));
	}

}
