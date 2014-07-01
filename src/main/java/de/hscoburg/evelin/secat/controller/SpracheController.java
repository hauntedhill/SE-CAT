package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.SeCat;
import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.base.LayoutController;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zur Anzeige der Sprachen
 * 
 * @author zuch1000
 * 
 */
@Controller
public class SpracheController extends BaseController {

	@FXML
	private Button buttonWaehlen;
	@FXML
	private ComboBox<Locale> boxSprachen;

	@Autowired
	private LayoutController layout;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		Locale list[] = SimpleDateFormat.getAvailableLocales();

		List<Locale> items = new LinkedList<Locale>();
		for (Locale l : list) {
			if ((l.getLanguage().toLowerCase().equals("en") || l.getLanguage().toLowerCase().trim().equals("de")) && l.getCountry().trim().equals("")) {
				items.add(l);
			}
		}

		items.sort(new Comparator<Locale>() {

			@Override
			public int compare(Locale o1, Locale o2) {
				return o1.getDisplayName().compareTo(o2.getDisplayName());
			}
		});

		ObservableList<Locale> myObservableList = FXCollections.observableList(items);

		boxSprachen.setVisibleRowCount(10);

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			private Node guiMenuNode;

			@Override
			public void handleAction(ActionEvent event) {

				Locale.setDefault(boxSprachen.getValue());

				SeCatResourceBundle.getInstance().refreseh();
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.SPRACHE_FXML);
				guiMenuNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.TOPMENU_FXML, SeCat.PRIMARY_STAGE);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
				layout.setTop(guiMenuNode);
			}
		}, buttonWaehlen, true);

		boxSprachen.setConverter(ConverterHelper.getConverterForLocale());

		boxSprachen.setItems(myObservableList);

		boxSprachen.getSelectionModel().select(Locale.getDefault());

		boxSprachen.setValue(Locale.getDefault());
		// boxSprachen.getSelectionModel().select(1);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.sprache.lable.title";
	}

}
