package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class SkalenController implements Initializable {

	@FXML
	private ListView<Skala> listSkalen;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameSkalen;

	@Autowired
	private SkalenModel skalenModel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadList();
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
				Skala e = new Skala();
				e.setName(textNameSkalen.getText());
				skalenModel.persist(e);

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

}
