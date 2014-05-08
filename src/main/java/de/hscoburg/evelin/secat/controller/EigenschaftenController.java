package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class EigenschaftenController extends BaseController {

	@FXML
	private ListView<Eigenschaft> listEigenschaften;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameEigenschaften;

	@Autowired
	private EigenschaftenModel eigenschaftenModel;

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

		buttonAdd.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));

		buttonAdd.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				Eigenschaft e = new Eigenschaft();
				e.setName(textNameEigenschaften.getText());
				eigenschaftenModel.persist(e);

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
					Eigenschaft e = new Eigenschaft();
					e.setName(textNameEigenschaften.getText());
					eigenschaftenModel.persist(e);
				}

			}

			@Override
			public void updateUI() {
				loadList();
			}
		});

	}

	private void loadList() {
		ObservableList<Eigenschaft> myObservableList = FXCollections.observableList(eigenschaftenModel.getEigenschaften());
		listEigenschaften.setItems(myObservableList);
	}

	@Override
	public String getSceneName() {

		return "Eigenschaften pflegen";
	}

}
