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
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.EditableCell;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.ValueListTypeHandler;

/**
 * Controller fuer die Anzeige der Eigenschaften
 * 
 * @author zuch1000
 * 
 */
@Controller
public class EigenschaftenController extends BaseController {

	@FXML
	private TitledPane addPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private ListView<Eigenschaft> listEigenschaften;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameEigenschaften;

	@Autowired
	private EigenschaftenModel eigenschaftenModel;

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		loadList();
		textNameEigenschaften.requestFocus();

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {

					eigenschaftenModel.saveEigenschaft(textNameEigenschaften.getText());

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

		listEigenschaften.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {

			@Override
			public ListCell<Eigenschaft> call(ListView<Eigenschaft> p) {

				return new EditableCell<Eigenschaft>(new ValueListTypeHandler<Eigenschaft>() {

					@Override
					public Eigenschaft merge(Eigenschaft value, String newValue) {
						value.setName(newValue);
						return value;
					}

					@Override
					public String getText(Eigenschaft value) {

						if (value != null) {
							return value.getName();
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Eigenschaft value) {
						try {

							eigenschaftenModel.updateEigenschaft(value);
							return true;

						} catch (IllegalArgumentException iae) {

						}
						return false;
					}

					@Override
					public boolean isLocked(Eigenschaft value) {

						return eigenschaftenModel.isLocked(value);
					}
				});
			}
		});

		ActionHelper.setEditFor(listEigenschaften);

	}

	/**
	 * Laed die Liste mit den Eigenschaften und setzt diese in der View
	 */
	private void loadList() {
		listEigenschaften.setItems(FXCollections.observableList(eigenschaftenModel.getEigenschaften()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.property.lable.title";
	}

}
