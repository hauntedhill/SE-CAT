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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

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

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		loadList();
		textNameFach.requestFocus();
		listFach.setCellFactory(new Callback<ListView<Fach>, ListCell<Fach>>() {

			@Override
			public ListCell<Fach> call(ListView<Fach> p) {

				ListCell<Fach> cell = new ListCell<Fach>() {

					@Override
					protected void updateItem(Fach t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

		buttonAdd.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));
		ActionHelper.setAutoResizeToggleListenerForTitledPanel(addPanel, tablePanel, listFach);
		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				try {
					fachModel.saveFach(textNameFach.getText());
				} catch (IllegalArgumentException iae) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.input.error.title"))
									.masthead(SeCatResourceBundle.getInstance().getString("scene.input.error.txt")).showError();

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

	private void loadList() {

		listFach.setItems(FXCollections.observableList(fachModel.getFaecher()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.fach.title.lable";
	}

}
