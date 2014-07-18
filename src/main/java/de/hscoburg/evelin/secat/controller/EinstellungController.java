package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;
import de.hscoburg.evelin.secat.model.EinstellungModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

/**
 * Controller fuer die Anzeige der Einstellungen
 * 
 * @author zuch1000
 * 
 */
@Controller
public class EinstellungController extends BaseController {

	@FXML
	private TextField textStandort;

	@FXML
	private Button buttonSetzen;

	@Autowired
	private EinstellungModel einstellungModel;

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		Einstellung e = einstellungModel.getWertForStandort();
		if (e != null) {
			textStandort.setText(einstellungModel.getWertForStandort().getWert());
		}
		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					einstellungModel.saveEinstellung(EinstellungenType.STANDORT, textStandort.getText());
				} catch (IllegalArgumentException iae) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							DialogHelper.showInputErrorDialog();

						}
					});

				}

			}

		}, buttonSetzen, true);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.einstellungen.lable.titel";
	}

}
