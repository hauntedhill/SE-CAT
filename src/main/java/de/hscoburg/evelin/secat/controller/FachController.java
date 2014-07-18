package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.EditableCell;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.ValueListTypeHandler;

/**
 * Controller zur Anzeige der Faecher
 * 
 * @author zuch1000
 * 
 */
@Controller
public class FachController extends BaseController {

	@FXML
	private TitledPane addPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private ListView<Fach> listFach;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameFach;

	@Autowired
	private FachModel fachModel;

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		loadList();
		textNameFach.requestFocus();

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					fachModel.saveFach(textNameFach.getText());
				} catch (IllegalArgumentException iae) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							DialogHelper.showInputErrorDialog();

						}
					});

				}

			}

			@Override
			public void updateUI() {
				loadList();
			}
		}, buttonAdd, true);

		listFach.setCellFactory(new Callback<ListView<Fach>, ListCell<Fach>>() {

			@Override
			public ListCell<Fach> call(ListView<Fach> p) {

				return new EditableCell<Fach>(new ValueListTypeHandler<Fach>() {

					@Override
					public Fach merge(Fach value, String newValue) {
						value.setName(newValue);
						return value;
					}

					@Override
					public String getText(Fach value) {

						if (value != null) {
							return value.getName();
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Fach value) {
						try {

							fachModel.updateFach(value);
							return true;

						} catch (IllegalArgumentException iae) {

						}
						return false;
					}

					@Override
					public boolean isLocked(Fach value) {

						return fachModel.isLocked(value);
					}
				});
			}
		});

		ActionHelper.setEditFor(listFach);

	}

	/**
	 * Laed und setzt die vorhandenen Faecher
	 */
	private void loadList() {

		listFach.setItems(FXCollections.observableList(fachModel.getFaecher()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.fach.title.lable";
	}

}
