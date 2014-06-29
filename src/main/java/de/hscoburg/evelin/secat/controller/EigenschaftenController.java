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
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

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
		listEigenschaften.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {

			@Override
			public ListCell<Eigenschaft> call(ListView<Eigenschaft> p) {

				ListCell<Eigenschaft> cell = new ListCell<Eigenschaft>() {

					@Override
					protected void updateItem(Eigenschaft t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

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
		}, buttonAdd);

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
