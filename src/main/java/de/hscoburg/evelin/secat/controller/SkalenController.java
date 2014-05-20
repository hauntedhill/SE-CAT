package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class SkalenController extends BaseController {

	@FXML
	private ListView<Skala> listSkalen;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameSkalen;

	@FXML
	private RadioButton discretQuestion;

	@FXML
	private RadioButton freeQuestion;

	@Autowired
	private SkalenModel skalenModel;

	@FXML
	private TextField textZeilen;

	@FXML
	private TextField textSchritte;
	@FXML
	private TextField textSchrittweite;
	@FXML
	private TextField textMinimal;
	@FXML
	private TextField textMaximal;
	@FXML
	private TextField textOptimum;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		ToggleGroup group = new ToggleGroup();

		discretQuestion.setToggleGroup(group);
		freeQuestion.setToggleGroup(group);

		freeQuestion.setSelected(true);

		loadList();
		textNameSkalen.requestFocus();
		listSkalen.setCellFactory(new Callback<ListView<Skala>, ListCell<Skala>>() {

			@Override
			public ListCell<Skala> call(ListView<Skala> p) {

				ListCell<Skala> cell = new ListCell<Skala>() {

					@Override
					protected void updateItem(Skala t, boolean bln) {
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

		buttonAdd.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					skalenModel.saveSkala(freeQuestion.isSelected() ? SkalaType.FREE : SkalaType.DISCRET, textNameSkalen.getText(), textZeilen.getText(),
							textSchritte.getText(), textSchrittweite.getText(), textMinimal.getText(), textMaximal.getText(), textOptimum.getText());
				} catch (NumberFormatException nfe) {
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
		});

		buttonAdd.setOnKeyPressed(new SeCatEventHandle<Event>() {

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER)

				{
					buttonAdd.fire();
				}

			}

			@Override
			public void updateUI() {
				loadList();
			}
		});

	}

	private void loadList() {
		ObservableList<Skala> myObservableList = FXCollections.observableList(skalenModel.getSkalen());
		listSkalen.setItems(myObservableList);
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.skala.lable.title";
	}

}
