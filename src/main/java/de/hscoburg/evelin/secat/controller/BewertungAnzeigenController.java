package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.model.BewertungModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.util.javafx.EvaluationHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class BewertungAnzeigenController extends BaseController {

	@FXML
	GridPane gridPane;
	@FXML
	TableView<EvaluationHelper> tableView;
	@Autowired
	BewertungModel bewertungModel;
	@Autowired
	FragebogenModel fragebogenModel;
	@Autowired
	BewertungController bewertungController;

	private int itemCount;
	private int constColumns;
	private int actualColumn;
	private ArrayList<Bereich> bereiche = new ArrayList<Bereich>();

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		final Text welle = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.welle"));
		final Text rawid = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.rawid"));
		final Text source = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.source"));
		final Text zeit = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.zeit"));

		bereiche.clear();
		tableView.getColumns().clear();
		TableColumn col0 = new TableColumn();
		TableColumn col1 = new TableColumn();
		TableColumn col2 = new TableColumn();
		TableColumn col3 = new TableColumn();

		col0.setMinWidth(100);
		col1.setMinWidth(100);
		col2.setMinWidth(100);
		col3.setMinWidth(100);
		Text colHeaderTextWelle = welle;
		Text colHeaderTextRawId = rawid;
		Text colHeaderTextSource = source;
		Text colHeaderTextZeit = zeit;
		col0.setGraphic(colHeaderTextWelle);
		col1.setGraphic(colHeaderTextRawId);
		col2.setGraphic(colHeaderTextSource);
		col3.setGraphic(colHeaderTextZeit);
		tableView.getColumns().addAll(col0, col1, col2, col3);

		Fragebogen f = bewertungController.getSelectedFragebogen();
		List<Bewertung> bewertungenList = f.getBewertungen();

		ObservableList<EvaluationHelper> ehList = FXCollections.observableArrayList();
		ObservableList<Bewertung> bewertungOl = FXCollections.observableArrayList(f.getBewertungen());

		for (Bewertung bewertung : bewertungOl) {
			if (bewertung.getItem() != null) {
				if (!bereiche.contains(bewertung.getItem().getBereich()))
					bereiche.add(bewertung.getItem().getBereich());
			}
		}

		ArrayList<String> erste = new ArrayList<String>();

		if (!bewertungOl.isEmpty()) {

			for (Bewertung bewertung : bewertungOl) {
				if (!erste.contains(bewertung.getRawid())) {
					erste.add(bewertung.getRawid());
					EvaluationHelper eh = new EvaluationHelper();
					eh.setWelle(bewertung.getWelle());
					eh.setRawId(bewertung.getRawid());
					eh.setSource(bewertung.getSource());
					eh.setZeit(bewertung.getZeit());
					List<Bewertung> temp = bewertungOl.subList(bewertungOl.indexOf(bewertung), bewertungOl.size() - 1);
					for (Bewertung b : temp) {
						if (b.getRawid().equals(eh.getRawId())) {
							if (b.getItem() != null) {
								eh.addItemWertung(b.getWert());
							}

						}
					}
					ehList.add(eh);
				}
			}
		}

		tableView.setItems(ehList);
		((TableColumn<EvaluationHelper, String>) tableView.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getWelle());

					}
				});

		((TableColumn<EvaluationHelper, String>) tableView.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getRawId());

					}
				});
		((TableColumn<EvaluationHelper, String>) tableView.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getZeit());

					}
				});
		((TableColumn<EvaluationHelper, String>) tableView.getColumns().get(3))
				.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {

						return new ReadOnlyObjectWrapper<String>(p.getValue().getSource());

					}
				});
		constColumns = 4;
		itemCount = 0;
		actualColumn = 1;
		for (Bereich bereich : bereiche) {

			TableColumn col = new TableColumn();
			Text t = new Text(bereich.getName());
			if (bereich.getName().length() > 15) {
				t.setWrappingWidth(200);
			}
			col.setGraphic(t);
			int count = 0;
			for (Item item : f.getItems()) {
				if (item.getBereich().equals(bereich)) {
					TableColumn itemCol = new TableColumn();
					Text itemName = new Text(item.getFrage());
					itemName.setWrappingWidth(125);
					itemCol.setGraphic(itemName);
					itemCol.setMinWidth(125);
					itemCol.setMaxWidth(125);
					col.getColumns().add(itemCol);

					((TableColumn<EvaluationHelper, String>) col.getColumns().get(count))
							.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

								public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
									if (actualColumn == tableView.getColumns().get(constColumns).getColumns().size()) {
										constColumns = +1;
										actualColumn = 1;
									}

									if (itemCount == p.getValue().getItemWertung().size()) {
										itemCount = 0;

									}
									actualColumn++;
									return new ReadOnlyObjectWrapper<String>(p.getValue().getItemWertung().get(itemCount++));

								}
							});

					count++;
				}
			}

			tableView.getColumns().add(col);

		}

		// itemCount = 0;
		// for (Item item : f.getItems()) {
		// TableColumn col = new TableColumn();
		// Text t = new Text(item.getFrage());
		// t.setWrappingWidth(125);
		// col.setGraphic(t);
		// col.setMinWidth(125);
		// col.setMaxWidth(125);
		// tableView.getColumns().add(col);

		/*
		 * ((TableColumn<EvaluationHelper, String>) tableView.getColumns().get(colCount)) .setCellValueFactory(new
		 * Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) { if (itemCount ==
		 * (tableView.getColumns().size() - (4 + bereiche.size()))) { itemCount = 0; } return new
		 * ReadOnlyObjectWrapper<String>(p.getValue().getItemWertung().get(itemCount++));
		 * 
		 * } });
		 */

		// }

	}

	/*
	 * ListView list = new ListView(); ListView list2 = new ListView(); ListView list3 = new ListView(); ListView list4 = new ListView();
	 * ListView list5 = new ListView(); ListView list6 = new ListView(); ListView list7 = new ListView();
	 * 
	 * ObservableList<String> values = FXCollections.observableArrayList(); values.addAll("test1", "test2", "test2", "test2", "test2",
	 * "test2", "test2", "test2"); list.setItems(values); list2.setItems(values); list3.setItems(values); list4.setItems(values);
	 * list5.setItems(values); list6.setItems(values); list7.setItems(values);
	 * 
	 * list.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	 * 
	 * final Text welle = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.welle")); final Text rawid = new
	 * Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.rawid")); final Text source = new
	 * Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.source")); final Text zeit = new
	 * Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.zeit"));
	 * 
	 * welle.setWrappingWidth(125); rawid.setWrappingWidth(125); source.setWrappingWidth(125); zeit.setWrappingWidth(125);
	 * 
	 * gridPane.add(welle, 0, 0); gridPane.add(rawid, 1, 0); gridPane.add(source, 2, 0); gridPane.add(zeit, 3, 0);
	 * 
	 * colCount = 4;
	 * 
	 * Fragebogen f = fragebogenModel.getFragebogenFor(null, null, null, "SECAT SMA 2014 Zwischeneva", null, null, null).get(0); for (Item
	 * item : f.getItems()) { Text t = new Text(item.getFrage()); t.setWrappingWidth(125); gridPane.add(t, ++colCount, 0);
	 * 
	 * }
	 * 
	 * final Text welle5 = new Text(); welle5.setWrappingWidth(125); welle5.setText(
	 * "Ich finde das Thema Anforderungserhebung aufgrund der Lehrveranstaltung interessanter als zu Beginn der Lehrveranstaltung.");
	 * 
	 * final Text welle6 = new Text(); welle6.setWrappingWidth(120); welle6.setText(
	 * "Ich finde das Thema Anforderungserhebung aufgrund der Lehrveranstaltung interessanter als zu Beginn der Lehrveranstaltung.");
	 * 
	 * final Text welle7 = new Text(); welle7.setWrappingWidth(120); welle7.setText(
	 * "Ich finde das Thema Anforderungserhebung aufgrund der Lehrveranstaltung interessanter als zu Beginn der Lehrveranstaltung.");
	 * 
	 * gridPane.getColumnConstraints().clear(); gridPane.setMaxWidth(Double.MAX_VALUE); gridPane.setHgap(5); gridPane.setVgap(5);
	 * 
	 * gridPane.add(list, 0, 1); gridPane.add(list2, 1, 1); gridPane.add(list3, 2, 1); gridPane.add(list4, 3, 1); gridPane.add(list5, 4, 1);
	 * gridPane.add(list6, 5, 1); gridPane.add(list7, 6, 1);
	 */

	@Override
	public String getKeyForSceneName() {

		return "scene.evaluation.lable.title";
	}
}
