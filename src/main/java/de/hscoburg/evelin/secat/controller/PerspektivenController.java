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
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.EditableCell;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.ValueListTypeHandler;

/**
 * Controller zur Anzeige der Perspektiven
 * 
 * @author zuch1000
 * 
 */
@Controller
public class PerspektivenController extends BaseController {

	@FXML
	private TitledPane addPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private ListView<Perspektive> listPerspektiven;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNamePerspektiven;

	@Autowired
	private PerspektivenModel perspektivenModel;

	/**
	 * Initialisiert die View
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		loadList();
		textNamePerspektiven.requestFocus();

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					perspektivenModel.savePerspektive(textNamePerspektiven.getText());
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

		listPerspektiven.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {

			@Override
			public ListCell<Perspektive> call(ListView<Perspektive> p) {

				return new EditableCell<Perspektive>(new ValueListTypeHandler<Perspektive>() {

					@Override
					public Perspektive merge(Perspektive value, String newValue) {
						value.setName(newValue);
						return value;
					}

					@Override
					public String getText(Perspektive value) {

						if (value != null) {
							return value.getName();
						} else {
							return "";
						}
					}

					@Override
					public boolean update(Perspektive value) {
						try {

							perspektivenModel.updatePerspektive(value);
							return true;

						} catch (IllegalArgumentException iae) {

						}
						return false;
					}

					@Override
					public boolean isLocked(Perspektive value) {

						return perspektivenModel.isLocked(value);
					}
				});
			}
		});

		ActionHelper.setEditFor(listPerspektiven);

	}

	/**
	 * Loadt die Perspektiven und setzt diese in der View
	 */
	private void loadList() {

		listPerspektiven.setItems(FXCollections.observableList(perspektivenModel.getPerspektiven()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.perspective.lable.title";
	}

}
