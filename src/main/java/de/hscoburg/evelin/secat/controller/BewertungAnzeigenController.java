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
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.model.BewertungModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.FragenModel;
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
	@Autowired
	FragenModel fragenModel;

	private int itemCount;
	private int frageColCount;
	private int wertungCount;
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
		List<Frage_Fragebogen> fragen = f.getCustomFragen();
		ArrayList<Frage> fragenList = new ArrayList<Frage>();

		for (Frage_Fragebogen frage : fragen) {
			fragenList.add(frage.getFrage());
		}

		ObservableList<Bewertung> bewertungOl = FXCollections.observableArrayList(f.getBewertungen());

		for (Bewertung bewertung : bewertungOl) {
			if (bewertung.getItem() != null) {
				if (!bereiche.contains(bewertung.getItem().getBereich()))
					bereiche.add(bewertung.getItem().getBereich());
			}
		}
		ObservableList<EvaluationHelper> ehList = FXCollections.observableArrayList();
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

		for (EvaluationHelper eh : ehList) {
			ArrayList<String> frageWertungen = new ArrayList<String>();
			for (Bewertung bewertung : bewertungOl) {
				if (eh.getRawId().equals(bewertung.getRawid()) && bewertung.getFrage() != null) {
					for (Frage frage : fragenList) {
						if (bewertung.getFrage().getId() == frage.getId()) {
							frageWertungen.add(fragenList.indexOf(frage), bewertung.getWert());
						}
					}

				}
			}

			eh.setFrageWertung(frageWertungen);
		}

		tableView.setItems(ehList);

		col0.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getWelle());

			}
		});

		col1.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getRawId());

			}
		});
		col2.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
				return new ReadOnlyObjectWrapper<String>(p.getValue().getZeit());

			}
		});
		col3.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

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

		if (!fragenList.isEmpty()) {
			wertungCount = 0;

			for (Frage frage : fragenList) {

				TableColumn colFrage = new TableColumn();
				colFrage.setMinWidth(125);
				colFrage.setMaxWidth(125);
				Text frageText;
				if (frage.getText().length() > 170) {
					frageText = new Text(frage.getText().substring(0, 170) + "...");
				} else {
					frageText = new Text(frage.getText());
				}
				if (frage.getText().length() > 15) {
					frageText.setWrappingWidth(125);
				}
				colFrage.setGraphic(frageText);
				tableView.getColumns().add(colFrage);

				colFrage.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<Text>>() {

					public ObservableValue<Text> call(CellDataFeatures<EvaluationHelper, String> p) {
						if (wertungCount == p.getValue().getFrageWertung().size()) {
							wertungCount = 0;
						}
						Text t = new Text(p.getValue().getFrageWertung().get(wertungCount++));
						t.setWrappingWidth(125);
						return new ReadOnlyObjectWrapper<Text>(t);
					}
				});

			}
		}

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.evaluation.lable.title";
	}
}
