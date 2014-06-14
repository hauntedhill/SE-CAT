package de.hscoburg.evelin.secat.controller.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.SeCat;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zur Steuerung des Layoutes
 * 
 * @author zuch1000
 * 
 */
@Controller
public class LayoutController implements Initializable {

	/**
	 * Adressen der einzelnen Scenen
	 */
	private static final String GUI_PATH = "/gui/";

	private static final String STAMMDATEN_PATH = GUI_PATH + "stammdaten/";

	private static final String EINSTELLUNGEN_PATH = GUI_PATH + "einstellungen/";

	private static final String FRAGEBOGEN_PATH = GUI_PATH + "fragebogen/";

	public static final String HANDLUNGSFELD_FXML = STAMMDATEN_PATH + "handlungsfeld.fxml";

	public static final String EIGENSCHAFTEN_FXML = STAMMDATEN_PATH + "Eigenschaften.fxml";

	public static final String PERSPEKTIVEN_FXML = STAMMDATEN_PATH + "Perspektiven.fxml";

	public static final String FAECHER_FXML = STAMMDATEN_PATH + "Fach.fxml";

	public static final String LEHRVERANSTALTUNGEN_FXML = STAMMDATEN_PATH + "Lehrveranstaltung.fxml";

	public static final String SKALEN_FXML = STAMMDATEN_PATH + "Skalen.fxml";

	public static final String TOPMENU_FXML = GUI_PATH + "Topmenu.fxml";

	public static final String SPLASHSCREEN_FXML = GUI_PATH + "Splash.fxml";

	public static final String LAYOUT_FXML = GUI_PATH + "Layout.fxml";

	public static final String SPRACHE_FXML = EINSTELLUNGEN_PATH + "Sprache.fxml";

	public static final String FRAGEBOGEN_FXML = FRAGEBOGEN_PATH + "addFragebogen.fxml";

	public static final String SHOW_FRAGEBOGEN_FXML = FRAGEBOGEN_PATH + "Frageboegen.fxml";

	public static final String FRAGEN_FXML = FRAGEBOGEN_PATH + "Fragen.fxml";

	public static final String BEWERTUNG_FXML = FRAGEBOGEN_PATH + "Bewertung.fxml";

	public static final String SHOW_BEWERTUNG_FXML = FRAGEBOGEN_PATH + "BewertungAnzeigen.fxml";

	public static final String EDIT_FRAGEBOGEN_FXML = FRAGEBOGEN_PATH + "editFragebogen.fxml";

	@FXML
	private BorderPane layout;

	/**
	 * Methode zum initialisieren des Layoutes
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		layout.setTop((Node) SpringFXMLLoader.getInstance().load(TOPMENU_FXML, SeCat.PRIMARY_STAGE));
		layout.setCenter((Node) SpringFXMLLoader.getInstance().load(HANDLUNGSFELD_FXML, SeCat.PRIMARY_STAGE));

	}

	/**
	 * Setzt die Center Node
	 * 
	 * @param url
	 */
	public void setCenterNode(Node url) {
		layout.setCenter(url);
	}

	/**
	 * Setzt die Top Node
	 * 
	 * @param url
	 */
	public void setTop(Node url) {
		layout.setTop(url);
	}

}
