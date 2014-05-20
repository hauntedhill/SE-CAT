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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.SeCat;
import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.base.LayoutController;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class SpracheController extends BaseController {

	@FXML
	private Button buttonWaehlen;
	@FXML
	private ComboBox<LocaleContainer> boxSprachen;

	@Autowired
	private LayoutController layout;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		Locale list[] = SimpleDateFormat.getAvailableLocales();

		List<LocaleContainer> items = new LinkedList<LocaleContainer>();
		for (Locale l : list) {
			items.add(new LocaleContainer(l));
		}

		items.sort(new Comparator<LocaleContainer>() {

			@Override
			public int compare(LocaleContainer o1, LocaleContainer o2) {
				return o1.getLocale().getDisplayName().compareTo(o2.getLocale().getDisplayName());
			}
		});

		ObservableList<LocaleContainer> myObservableList = FXCollections.observableList(items);

		boxSprachen.setVisibleRowCount(10);

		buttonWaehlen.setGraphic(new ImageView(new Image("/image/icons/apply.png", 16, 16, true, true)));

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			private Node guiNode;

			private Node guiMenuNode;

			@Override
			public void handleAction(ActionEvent event) {

				Locale.setDefault(boxSprachen.getValue().getLocale());

				SeCatResourceBundle.getInstance().refreseh();
				guiNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.SPRACHE_FXML);
				guiMenuNode = (Node) SpringFXMLLoader.getInstance().load(LayoutController.TOPMENU_FXML, SeCat.PRIMARY_STAGE);
			}

			@Override
			public void updateUI() {
				layout.setCenterNode(guiNode);
				layout.setTop(guiMenuNode);
			}
		}, buttonWaehlen);

		boxSprachen.setItems(myObservableList);

		boxSprachen.getSelectionModel().select(new LocaleContainer(Locale.getDefault()));

		boxSprachen.setValue(new LocaleContainer(Locale.getDefault()));
		// boxSprachen.getSelectionModel().select(1);

	}

	private class LocaleContainer {
		private Locale locale;

		public LocaleContainer(Locale l) {
			locale = l;
		}

		public Locale getLocale() {
			return locale;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return locale.getDisplayName() + " (" + locale.getLanguage() + ("".equals(locale.getCountry().trim()) ? "" : "_") + locale.getCountry() + ")";
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return locale.equals(obj);
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return locale.hashCode();
		}
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.sprache.lable.title";
	}

}
