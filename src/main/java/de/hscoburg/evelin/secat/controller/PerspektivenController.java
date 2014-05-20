package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class PerspektivenController extends BaseController {

	@FXML
	private ListView<Perspektive> listPerspektiven;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNamePerspektiven;

	@Autowired
	private PerspektivenModel perspektivenModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		loadList();
		textNamePerspektiven.requestFocus();
		listPerspektiven.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {

			@Override
			public ListCell<Perspektive> call(ListView<Perspektive> p) {

				ListCell<Perspektive> cell = new ListCell<Perspektive>() {

					@Override
					protected void updateItem(Perspektive t, boolean bln) {
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

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					perspektivenModel.savePerspektive(textNamePerspektiven.getText());
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
		ObservableList<Perspektive> myObservableList = FXCollections.observableList(perspektivenModel.getPerspektiven());

		listPerspektiven.setItems(myObservableList);
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.perspective.lable.title";
	}

}
