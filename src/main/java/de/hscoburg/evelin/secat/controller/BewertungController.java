package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zur Auswahl einer Bewertung
 * 
 * @author moro1000
 * 
 */
@Controller
public class BewertungController extends BaseController {

	@Autowired
	private FragebogenModel fragebogenModel;
	@FXML
	private TableView<Fragebogen> tableView;
	@FXML
	private Button show;

	/**
	 * Initialisierierung
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		ObservableList<Fragebogen> frageboegenOl = FXCollections.observableArrayList();
		List<Fragebogen> frageboegen = fragebogenModel.getFragebogenFor(null, null, null, null, null, null, null, false);
		for (Fragebogen fragebogen : frageboegen) {
			if (!fragebogen.getBewertungen().isEmpty()) {
				frageboegenOl.add(fragebogen);
			}
		}

		tableView.setItems(frageboegenOl);

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());

					}
				});

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getEigenschaft().getName());

					}
				});

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getPerspektive().getName());

					}
				});

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(3))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {

						Lehrveranstaltung t = p.getValue().getLehrveranstaltung();

						return new ReadOnlyObjectWrapper<String>(t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " "
								+ t.getDozent());

					}
				});

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(4))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(SimpleDateFormat.getDateInstance().format(p.getValue().getErstellungsDatum()));

					}
				});

		((TableColumn<Fragebogen, String>) tableView.getColumns().get(5))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

					}
				});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			private Stage stage;

			@Override
			public void handleAction(ActionEvent t) {

			}

			@Override
			public void updateUI() {
				if (getSelectedFragebogen() != null) {
					Stage stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/fragebogen/BewertungAnzeigen.fxml");
					stage.show();
				}
			}

		}, show, true);
	}

	/**
	 * Gibt den aktuell selektierten Fragebogen zurueck.
	 * 
	 * @return {@link Fragebogen}
	 */
	public Fragebogen getSelectedFragebogen() {
		return tableView.getSelectionModel().getSelectedItem();

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.evaluation.lable.title";
	}
}
