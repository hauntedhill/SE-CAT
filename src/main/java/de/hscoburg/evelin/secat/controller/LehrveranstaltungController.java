package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class LehrveranstaltungController extends BaseController {

	@FXML
	private ListView<Lehrveranstaltung> listLehrveranstaltung;

	@FXML
	private Button buttonAdd;

	@FXML
	private ComboBox<Integer> boxJahr;
	@FXML
	private ComboBox<SemesterType> boxSemester;
	@FXML
	private ComboBox<FachContainer> boxFach;

	@FXML
	private TextField textDozent;

	@Autowired
	private FachModel fachModel;

	@Autowired
	private LehrveranstaltungModel lehrveranstaltungsModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		ObservableList<Integer> years = FXCollections.observableArrayList();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = year + 5; i > year - 5; i--) {
			years.add(i);
		}
		boxJahr.setItems(years);

		boxJahr.getSelectionModel().select(year);

		boxJahr.setValue(year);

		ObservableList<SemesterType> semester = FXCollections.observableArrayList();

		for (SemesterType t : SemesterType.values()) {
			semester.add(t);
		}
		boxSemester.setItems(semester);

		boxSemester.getSelectionModel().select(SemesterType.values()[0]);

		boxSemester.setValue(SemesterType.values()[0]);

		ObservableList<FachContainer> faecher = FXCollections.observableArrayList();

		for (Fach f : fachModel.getFaecher() != null ? fachModel.getFaecher() : new ArrayList<Fach>()) {
			faecher.add(new FachContainer(f));
		}

		boxFach.setItems(faecher);
		if (faecher.size() > 0) {
			boxFach.getSelectionModel().select(faecher.get(0));
			boxFach.setValue(faecher.get(0));
		}

		boxFach.setVisibleRowCount(10);

		loadList();
		boxJahr.requestFocus();
		listLehrveranstaltung.setCellFactory(new Callback<ListView<Lehrveranstaltung>, ListCell<Lehrveranstaltung>>() {

			@Override
			public ListCell<Lehrveranstaltung> call(ListView<Lehrveranstaltung> p) {

				ListCell<Lehrveranstaltung> cell = new ListCell<Lehrveranstaltung>() {

					@Override
					protected void updateItem(Lehrveranstaltung t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " " + t.getDozent());
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

					lehrveranstaltungsModel.saveLehrveranstaltung(textDozent.getText(), boxFach.getValue().getFach(), boxJahr.getValue(),
							boxSemester.getValue());
				} catch (IllegalArgumentException iae) {
					javafx.application.Platform.runLater(new Runnable() {

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
		ObservableList<Lehrveranstaltung> myObservableList = FXCollections.observableList(lehrveranstaltungsModel.getLehrveranstaltung());
		listLehrveranstaltung.setItems(myObservableList);
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.fach.title.lable";
	}

	private class FachContainer {
		private Fach fach;

		public FachContainer(Fach l) {
			fach = l;
		}

		public Fach getFach() {
			return fach;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return fach.getName();
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return fach.equals(obj);
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return fach.hashCode();
		}
	}

}
