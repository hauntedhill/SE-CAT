package de.hscoburg.evelin.secat.controller;

import java.awt.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.helper.EvaluationHelper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.model.BewertungModel;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.model.FragenModel;
import de.hscoburg.evelin.secat.util.charts.RadarChart;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

@Controller
public class BewertungAnzeigenController extends BaseController {

	@FXML
	GridPane gridPane;
	@FXML
	GridPane visuals;
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
	private double[] avValueBereich;
	private Fragebogen fragebogen;
	private List<EvaluationHelper> allEvaluationHelper;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		tableView.setRowFactory(new Callback<TableView<EvaluationHelper>, TableRow<EvaluationHelper>>() {

			public TableRow<EvaluationHelper> call(TableView<EvaluationHelper> treeTableView) {

				final TableRow<EvaluationHelper> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				final MenuItem showRadarChart = new MenuItem("Kiviat Chart", new ImageView(new Image("/image/icons/demo.png", 16, 16, true, true)));

				row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {

					}
				});

				showRadarChart.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private Stage stage;

					@Override
					public void handleAction(ActionEvent t) {

					}

					@Override
					public void updateUI() {
						stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/charts/kiviatChart.fxml");
						stage.show();
					}

				});

				rowMenu.getItems().add(showRadarChart);

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(rowMenu)
								.otherwise((ContextMenu) null));
				return row;

			}

		});

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
		fragebogen = f;
		List<Bewertung> bewertungenList = f.getBewertungen();
		List<Frage_Fragebogen> fragen = f.getFrageFragebogen();
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
		avValueBereich = new double[bereiche.size()];
		int valCount = 0;
		for (Bereich bereich : bereiche) {
			valCount = 0;
			for (Bewertung bewertung : bewertungOl) {
				if (bewertung.getItem() != null) {
					if (bereich.equals(bewertung.getItem().getBereich())) {
						avValueBereich[bereiche.indexOf(bereich)] += Double.parseDouble(bewertung.getWert());
						valCount++;
					}
				}
			}
			if (valCount != 0) {
				avValueBereich[bereiche.indexOf(bereich)] /= valCount;
			}
		}

		ObservableList<EvaluationHelper> ehList = FXCollections.observableArrayList();
		ArrayList<String> erste = new ArrayList<String>();

		if (!bewertungOl.isEmpty()) {

			for (Bewertung bewertung : bewertungOl) {
				if (!erste.contains(bewertung.getZeilenid())) {
					erste.add(bewertung.getZeilenid());
					EvaluationHelper eh = new EvaluationHelper();
					eh.setWelle(bewertung.getWelle());
					eh.setRawId(bewertung.getZeilenid());
					eh.setSource(bewertung.getQuelle());
					eh.setZeit(bewertung.getZeit());
					List<Bewertung> temp = bewertungOl;
					for (Bewertung b : temp) {

						if (b.getZeilenid().equals(eh.getRawId())) {
							if (b.getItem() != null) {

								eh.addItemWertung(b.getWert());
								eh.addItem(b.getItem());
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
				if (eh.getRawId().equals(bewertung.getZeilenid()) && bewertung.getFrage() != null) {
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

		for (EvaluationHelper eh : ehList) {

		}

		allEvaluationHelper = ehList;

		final SwingNode chartSwingNode = new SwingNode();
		chartSwingNode.setContent(new ChartPanel(createAverageRadarChartForBereich()));
		visuals.add(chartSwingNode, 1, 1);
		final SwingNode boxPlotChartSwingNode = new SwingNode();
		boxPlotChartSwingNode.setContent(new ChartPanel(createBoxPlot()));
		visuals.add(boxPlotChartSwingNode, 2, 1);

	}

	public JFreeChart createRadarChart(EvaluationHelper eh, Fragebogen fragebogen) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		String s;

		int iBewertung = 0;
		s = eh.getRawId();
		int z = 0;
		for (Item item : eh.getItems()) {
			if (item != null) {
				defaultcategorydataset.addValue((Double.parseDouble(eh.getItemWertung().get(iBewertung++))), s, z + ": " + item.getName());
			}

		}

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();
		spiderwebplot.setMaxValue(fragebogen.getSkala().getSchritte());
		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;

	}

	public JFreeChart createAverageRadarChartForBereich() {
		DefaultCategoryDataset defaultcategorydataset = getAverageDataSetForBereich();

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();
		spiderwebplot.setMaxValue(fragebogen.getSkala().getSchritte());

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;
	}

	public JFreeChart createMixedRadarChartForItem(EvaluationHelper eh, Fragebogen fragebogen) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		int z = 0;

		int anzItems = allEvaluationHelper.get(0).getItems().size();
		double[] werte = new double[anzItems];

		for (EvaluationHelper evalHelper : allEvaluationHelper) {
			int iWerte = 0;
			for (Item item : evalHelper.getItems()) {
				if (item != null) {

					werte[iWerte] += ((Double.parseDouble(evalHelper.getItemWertung().get(iWerte++))));
				}
			}
			z = 0;
			for (int j = 0; j < anzItems; j++)
				defaultcategorydataset.addValue((werte[j] / allEvaluationHelper.size()),
						SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), z++ + ": "
								+ allEvaluationHelper.get(0).getItems().get(j).getName());

		}

		String s;

		int iBewertung = 0;
		s = eh.getRawId();
		z = 0;
		for (Item item : eh.getItems()) {
			if (item != null) {

				defaultcategorydataset.addValue((Double.parseDouble(eh.getItemWertung().get(iBewertung++))), s, z++ + ": " + item.getName());
			}

		}

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;

	}

	public JFreeChart createMixedRadarChartForBereich(EvaluationHelper eh, Fragebogen fragebogen) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		double avValue[] = new double[bereiche.size()];
		int valCount = 0;
		for (Bereich bereich : bereiche) {
			valCount = 0;
			for (Item item : eh.getItems()) {

				if (bereich.equals(item.getBereich())) {
					avValue[bereiche.indexOf(bereich)] += Double.parseDouble(eh.getItemWertung().get(valCount));
					valCount++;
				}

			}
			if (valCount != 0) {
				avValue[bereiche.indexOf(bereich)] /= valCount;
			}
		}
		for (int j = 0; j < avValue.length; j++) {
			defaultcategorydataset.addValue(avValue[j], eh.getRawId(), bereiche.get(j).getName());

		}
		for (int i = 0; i < bereiche.size(); i++) {
			defaultcategorydataset.addValue(avValueBereich[i], SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), bereiche.get(i)
					.getName());
		}

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;

	}

	public DefaultCategoryDataset getAverageDataSetForBereich() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (int i = 0; i < bereiche.size(); i++)
			defaultcategorydataset.addValue(avValueBereich[i], SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), bereiche.get(i)
					.getName());

		return defaultcategorydataset;
	}

	public DefaultCategoryDataset getAverageDataSetForItem() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		if (!allEvaluationHelper.isEmpty()) {

			int anzItems = allEvaluationHelper.get(0).getItems().size();
			double[] werte = new double[anzItems];

			for (EvaluationHelper evalHelper : allEvaluationHelper) {
				int iWerte = 0;
				for (Item item : evalHelper.getItems()) {
					if (item != null) {

						werte[iWerte] += ((Double.parseDouble(evalHelper.getItemWertung().get(iWerte++))));
					}
				}

				for (int j = 0; j < anzItems; j++)
					defaultcategorydataset.addValue((werte[j] / allEvaluationHelper.size()),
							SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"),
							j + ": " + allEvaluationHelper.get(0).getItems().get(j).getName());

			}

		}

		return defaultcategorydataset;
	}

	public JFreeChart createBoxPlot() {
		final int seriesCount = 3;
		final int categoryCount = 4;
		final int entityCount = 22;

		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		for (int i = 0; i < seriesCount; i++) {
			for (int j = 0; j < categoryCount; j++) {
				final List list = new ArrayList();
				// add some values...
				for (int k = 0; k < entityCount; k++) {
					final double value1 = 10.0 + Math.random() * 3;
					list.add(new Double(value1));
					final double value2 = 11.25 + Math.random(); // concentrate values in the middle
					list.add(new Double(value2));
				}
				dataset.add(list, "Series " + i, " Type " + j);
			}

		}

		final CategoryAxis xAxis = new CategoryAxis("Type");
		final NumberAxis yAxis = new NumberAxis("Value");
		yAxis.setAutoRangeIncludesZero(false);
		final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

		final JFreeChart chart = new JFreeChart("BoxPLot", new Font("SansSerif", Font.BOLD, 14), plot, true);
		return chart;

	}

	public Fragebogen getFragebogen() {
		return fragebogen;
	}

	public EvaluationHelper getSelectedItem() {
		return tableView.getSelectionModel().getSelectedItem();
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.evaluation.lable.title";
	}
}
