package de.hscoburg.evelin.secat.controller;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.controller.helper.EvaluationHelper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
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
	Button compareItems;
	@FXML
	Button compareEvaluation;
	@FXML
	GridPane gridPane;
	@FXML
	GridPane criterionincreasePaneBarchart;
	@FXML
	GridPane subcriterionincreasePaneKiviat;
	@FXML
	GridPane subcriterionincreasePaneBoxplot;
	@FXML
	GridPane itemincreasePane;
	@FXML
	GridPane studentincreasePaneKiviat;
	@FXML
	GridPane studentincreasePaneBarchart;
	@FXML
	GridPane itemCompareBarPane;
	@FXML
	GridPane evaluationComparePaneBar;
	@FXML
	GridPane evaluationComparePaneKiviat;
	@FXML
	TableView<EvaluationHelper> tableViewAll;
	@FXML
	TableView<EvaluationHelper> tableViewLeast;
	@FXML
	TableView<EvaluationHelper> tableViewItems;
	@FXML
	TableView<EvaluationHelper> tableViewQuestions;
	@FXML
	TableView<Item> itemTable;
	@FXML
	TableView<Fragebogen> evaluationTable;
	@FXML
	TextField studentincreaseAverage;
	@FXML
	TextField studentincreaseMedian;
	@FXML
	TextField studentincreaseDeviation;
	@FXML
	TextField subcriterionincreaseAverage;
	@FXML
	TextField subcriterionincreaseMedian;
	@FXML
	TextField subcriterionincreaseDeviation;
	@FXML
	TextField criterionincreaseAverage;
	@FXML
	TextField criterionincreaseMedian;
	@FXML
	TextField criterionincreaseDeviation;
	@Autowired
	BewertungModel bewertungModel;
	@Autowired
	FragebogenModel fragebogenModel;
	@Autowired
	BewertungController bewertungController;
	@Autowired
	FragenModel fragenModel;

	private int itemCount;
	private int wertungCount = 0;
	private int constColumns;
	private int actualColumn;
	private ArrayList<Bereich> bereiche = new ArrayList<Bereich>();
	private double[] avValueBereich;
	private Fragebogen fragebogen;
	private ObservableList<EvaluationHelper> allEvaluationHelper = FXCollections.observableArrayList();
	private DecimalFormat doubleFormat = new DecimalFormat("#0.00");
	private int[] bereichVisible;
	private ArrayList<Frage> fragenList = new ArrayList<Frage>();

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		setRowFactory(new Callback<TableView<EvaluationHelper>, TableRow<EvaluationHelper>>() {

			public TableRow<EvaluationHelper> call(TableView<EvaluationHelper> treeTableView) {

				final TableRow<EvaluationHelper> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				final MenuItem showRadarChart = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.chart.kiviat.lable.title"), new ImageView(
						new Image("/image/icons/demo.png", 16, 16, true, true)));
				final MenuItem showProfilChart = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.chart.profilplot.lable.title"), new ImageView(
						new Image("/image/icons/demo.png", 16, 16, true, true)));
				final MenuItem outlierAdd = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.assessment.rowlable.outlieradd"), new ImageView(
						new Image("/image/icons/flag.png", 16, 16, true, true)));
				final MenuItem outlierAutomatic = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.assessment.rowlable.outlierautomatic"),
						new ImageView(new Image("/image/icons/run.png", 16, 16, true, true)));
				final MenuItem outlierRemove = new MenuItem(SeCatResourceBundle.getInstance().getString("scene.assessment.rowlable.outlierremove"),
						new ImageView(new Image("/image/icons/editdelete.png", 16, 16, true, true)));

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

				showProfilChart.setOnAction(new SeCatEventHandle<ActionEvent>() {

					private Stage stage;

					@Override
					public void handleAction(ActionEvent t) {

					}

					@Override
					public void updateUI() {
						stage = SpringFXMLLoader.getInstance().loadInNewScene("/gui/charts/profilChart.fxml");
						stage.show();
					}

				});

				outlierAdd.setOnAction(new SeCatEventHandle<ActionEvent>() {

					@Override
					public void handleAction(ActionEvent t) {
						EvaluationHelper eh;
						if (tableViewAll.isFocused()) {
							eh = tableViewAll.getSelectionModel().getSelectedItem();
						} else if (tableViewLeast.isFocused()) {
							eh = tableViewLeast.getSelectionModel().getSelectedItem();
						} else if (tableViewItems.isFocused()) {
							eh = tableViewItems.getSelectionModel().getSelectedItem();
						} else {
							eh = tableViewQuestions.getSelectionModel().getSelectedItem();
						}
						ArrayList<Bewertung> bewertungen = new ArrayList<Bewertung>();
						for (Bewertung b : fragebogen.getBewertungen()) {

							if (b.getZeilenid().equals(eh.getRawId())) {
								bewertungen.add(b);

							}

						}
						bewertungModel.setOutlier(bewertungen);
					}

					@Override
					public void updateUI() {
						System.out.println(tableViewAll.isFocused());
						// tableViewAll.getSelectionModel().getSelectedItem().setOutlier(true);
						// tableViewAll.getItems().clear();
						allEvaluationHelper = EvaluationHelper.createEvaluationHelperList(fragebogen.getBewertungen(), fragebogen.getFrageFragebogen());
						tableViewAll.setItems(allEvaluationHelper);
						tableViewLeast.setItems(allEvaluationHelper);
						tableViewItems.setItems(allEvaluationHelper);
						tableViewQuestions.setItems(allEvaluationHelper);

					}

				});

				outlierRemove.setOnAction(new SeCatEventHandle<ActionEvent>() {

					@Override
					public void handleAction(ActionEvent t) {
						EvaluationHelper eh;
						if (tableViewAll.isFocused()) {
							eh = tableViewAll.getSelectionModel().getSelectedItem();
						} else if (tableViewLeast.isFocused()) {
							eh = tableViewLeast.getSelectionModel().getSelectedItem();
						} else if (tableViewItems.isFocused()) {
							eh = tableViewItems.getSelectionModel().getSelectedItem();
						} else {
							eh = tableViewQuestions.getSelectionModel().getSelectedItem();
						}
						ArrayList<Bewertung> bewertungen = new ArrayList<Bewertung>();
						for (Bewertung b : fragebogen.getBewertungen()) {

							if (b.getZeilenid().equals(eh.getRawId())) {
								bewertungen.add(b);

							}

						}
						bewertungModel.removeOutlier(bewertungen);
					}

					@Override
					public void updateUI() {
						// tableViewAll.getSelectionModel().getSelectedItem().setOutlier(true);
						// tableViewAll.getItems().clear();
						allEvaluationHelper = EvaluationHelper.createEvaluationHelperList(fragebogen.getBewertungen(), fragebogen.getFrageFragebogen());
						tableViewAll.setItems(allEvaluationHelper);
						tableViewLeast.setItems(allEvaluationHelper);
						tableViewItems.setItems(allEvaluationHelper);
						tableViewQuestions.setItems(allEvaluationHelper);

					}

				});

				outlierAutomatic.setOnAction(new SeCatEventHandle<ActionEvent>() {

					@Override
					public void handleAction(ActionEvent t) {
						EvaluationHelper eh;
						if (tableViewAll.isFocused()) {
							eh = tableViewAll.getSelectionModel().getSelectedItem();
						} else if (tableViewLeast.isFocused()) {
							eh = tableViewLeast.getSelectionModel().getSelectedItem();
						} else if (tableViewItems.isFocused()) {
							eh = tableViewItems.getSelectionModel().getSelectedItem();
						} else {
							eh = tableViewQuestions.getSelectionModel().getSelectedItem();
						}
						ArrayList<Bewertung> bewertungen = new ArrayList<Bewertung>();
						for (Bewertung b : fragebogen.getBewertungen()) {

							if (b.getZeilenid().equals(eh.getRawId())) {
								bewertungen.add(b);

							}

						}
						bewertungModel.setOutlierAutomatic(bewertungen);
					}

					@Override
					public void updateUI() {

						// tableViewAll.getSelectionModel().getSelectedItem().setOutlier(true);
						// tableViewAll.getItems().clear();
						allEvaluationHelper = EvaluationHelper.createEvaluationHelperList(fragebogen.getBewertungen(), fragebogen.getFrageFragebogen());
						tableViewAll.setItems(allEvaluationHelper);
						tableViewLeast.setItems(allEvaluationHelper);
						tableViewItems.setItems(allEvaluationHelper);
						tableViewQuestions.setItems(allEvaluationHelper);

					}

				});

				rowMenu.getItems().addAll(showRadarChart, showProfilChart, outlierAdd, outlierAutomatic, outlierRemove);

				row.contextMenuProperty().bind(
						javafx.beans.binding.Bindings.when(javafx.beans.binding.Bindings.isNotNull(row.itemProperty())).then(rowMenu)
								.otherwise((ContextMenu) null));
				return row;

			}

		});

		bereiche.clear();
		// tableView.getColumns().clear();

		ObservableList<EvaluationHelper> ehList = FXCollections.observableArrayList();

		tableViewAll.getColumns().add(createHead(true));
		tableViewLeast.getColumns().add(createHead(false));
		tableViewItems.getColumns().add(createHead(false));
		tableViewQuestions.getColumns().add(createHead(false));

		Fragebogen f = bewertungController.getSelectedFragebogen();
		fragebogen = f;
		List<Frage_Fragebogen> fragen = f.getFrageFragebogen();

		for (Frage_Fragebogen frage : fragen) {
			fragenList.add(frage.getFrage());
		}

		ObservableList<Bewertung> bewertungOl = FXCollections.observableArrayList(f.getBewertungen());
		bereiche = EvaluationHelper.getBereicheFromEvaluationHelper(bewertungOl);

		avValueBereich = getAvValueforBereiche(bewertungOl, bereiche);

		ehList = EvaluationHelper.createEvaluationHelperList(bewertungOl, fragen);

		allEvaluationHelper = setOutliers(ehList);

		tableViewAll.setItems(allEvaluationHelper);
		tableViewLeast.setItems(allEvaluationHelper);
		tableViewItems.setItems(allEvaluationHelper);
		tableViewQuestions.setItems(allEvaluationHelper);

		tableViewAll.getColumns().addAll(createItemColumns(true));
		tableViewLeast.getColumns().addAll(createItemColumns(false));
		tableViewItems.getColumns().addAll(createItemColumns(true));
		tableViewQuestions.getColumns().addAll(createItemColumns(false));

		tableViewAll.getColumns().add(createQuestions(true));
		tableViewQuestions.getColumns().add(createQuestions(true));

		if (allEvaluationHelper != null && !allEvaluationHelper.isEmpty()) {
			ObservableList itemList = FXCollections.observableArrayList(allEvaluationHelper.get(0).getItems());
			itemTable.setItems(itemList);
			itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}

		((TableColumn<Item, String>) itemTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Item, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());
					}
				});
		((TableColumn<Item, String>) itemTable.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Item, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Item, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getBereich().getName());
					}
				});

		compareItems.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent t) {

			}

			@Override
			public void updateUI() {
				ObservableList<Item> items = itemTable.getSelectionModel().getSelectedItems();
				XYSeriesCollection data_series = new XYSeriesCollection();
				XYSeries xy_data = new XYSeries("leer");

				if (items.size() > 1) {
					xy_data.setDescription(items.get(0).getName() + " / " + items.get(1).getName());
					for (EvaluationHelper eh : allEvaluationHelper) {
						if (eh.getItemWertung().get(eh.getItems().indexOf(items.get(0))).isEmpty()) {
							xy_data.add(0, Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(items.get(1)))));
						} else if (eh.getItemWertung().get(eh.getItems().indexOf(items.get(1))).isEmpty()) {
							xy_data.add(Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(items.get(0)))), 0);
						} else {
							xy_data.add(Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(items.get(0)))),
									Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(items.get(1)))));
						}

					}
					data_series.addSeries(xy_data);

				}
				JFreeChart chart = ChartFactory.createScatterPlot(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.itemcomparison"), "", "",
						data_series, PlotOrientation.VERTICAL, true, false, false);

				SwingNode itemCompareBarSwingNode = new SwingNode();
				itemCompareBarSwingNode.setContent(new ChartPanel(chart));
				itemCompareBarPane.add(itemCompareBarSwingNode, 1, 1);

			}

		});
		ObservableList<Fragebogen> frageboegen = FXCollections.observableArrayList();
		for (Fragebogen fr : fragebogenModel.getFragebogenFor(null, null, null, null, null, null, null, false)) {
			if (!fragebogen.equals(fr) && !fr.getBewertungen().isEmpty() && fr.getBewertungen() != null) {
				frageboegen.add(fr);
			}
		}

		evaluationTable.setItems(frageboegen);

		compareEvaluation.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent t) {

			}

			@Override
			public void updateUI() {
				if (evaluationTable.getSelectionModel().getSelectedItem() != null) {
					Fragebogen fragebogenToCompare = evaluationTable.getSelectionModel().getSelectedItem();
					ObservableList<EvaluationHelper> ehToCompare = EvaluationHelper.createEvaluationHelperList(fragebogenToCompare.getBewertungen(),
							fragebogen.getFrageFragebogen());
					ArrayList<Bereich> bereicheToCompare = EvaluationHelper.getBereicheFromEvaluationHelper(fragebogenToCompare.getBewertungen());
					double[] avToCompare = getAvValueforBereiche(fragebogenToCompare.getBewertungen(), bereicheToCompare);

					JFreeChart barchart = createBarChartForCriterionEvaluationCompare(bereicheToCompare, avToCompare, fragebogenToCompare.getName());

					SwingNode evaluationCompareBarSwingNode = new SwingNode();
					evaluationCompareBarSwingNode.setContent(new ChartPanel(barchart));
					evaluationComparePaneBar.add(evaluationCompareBarSwingNode, 1, 1);

					JFreeChart kiviatchart = createAverageRadarChartForBereich(getAverageDataSetForBereichCompare(ehToCompare, bereicheToCompare, avToCompare,
							fragebogenToCompare.getName()));

					SwingNode evaluationCompareKiviatSwingNode = new SwingNode();
					evaluationCompareKiviatSwingNode.setContent(new ChartPanel(kiviatchart));
					evaluationComparePaneKiviat.add(evaluationCompareKiviatSwingNode, 1, 1);
				}
			}

		});

		((TableColumn<Fragebogen, String>) evaluationTable.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());
					}
				});

		((TableColumn<Fragebogen, String>) evaluationTable.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getPerspektive().getName());
					}
				});

		((TableColumn<Fragebogen, String>) evaluationTable.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Fragebogen, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Fragebogen, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getLehrveranstaltung().getFach().getName() + " "
								+ p.getValue().getLehrveranstaltung().getJahr() + " " + p.getValue().getLehrveranstaltung().getDozent());
					}
				});

		SwingNode criterionincreaseSwingNode = new SwingNode();
		criterionincreaseSwingNode.setContent(new ChartPanel(createBarChartForCriterion()));
		criterionincreasePaneBarchart.add(criterionincreaseSwingNode, 1, 1);

		SwingNode studentincreaseSwingNodeKiviat = new SwingNode();
		studentincreaseSwingNodeKiviat.setContent(new ChartPanel(createStudentRadarChart(allEvaluationHelper)));
		studentincreasePaneKiviat.add(studentincreaseSwingNodeKiviat, 1, 1);

		SwingNode chartSwingNode = new SwingNode();
		chartSwingNode.setContent(new ChartPanel(createAverageRadarChartForBereich(null)));
		subcriterionincreasePaneKiviat.add(chartSwingNode, 1, 1);

		SwingNode boxPlotChartSwingNode = new SwingNode();
		boxPlotChartSwingNode.setContent(new ChartPanel(createBoxPlot()));
		subcriterionincreasePaneBoxplot.add(boxPlotChartSwingNode, 1, 1);

		SwingNode studentincreaseSwingNodeBarchart = new SwingNode();
		studentincreaseSwingNodeBarchart.setContent(new ChartPanel(createBarChartStudent(allEvaluationHelper)));
		studentincreasePaneBarchart.add(studentincreaseSwingNodeBarchart, 1, 1);

		studentincreaseAverage.setText(doubleFormat.format(getAverageForAllStudents(allEvaluationHelper)));
		studentincreaseMedian.setText(doubleFormat.format(getMedianForAllStudents(allEvaluationHelper)));
		studentincreaseDeviation.setText(doubleFormat.format(getStandarddeviationForAllStudents(allEvaluationHelper)));
		studentincreaseAverage.setEditable(false);
		studentincreaseMedian.setEditable(false);
		studentincreaseDeviation.setEditable(false);

		subcriterionincreaseAverage.setText(doubleFormat.format(getAverageDataForSubCriterion()));
		subcriterionincreaseMedian.setText(doubleFormat.format(getMedianForAllSubCriterions()));
		subcriterionincreaseDeviation.setText(doubleFormat.format(getStandarddeviationForAllSubCriterions()));

		criterionincreaseAverage.setText(doubleFormat.format(getAverageDataForAllCriterions()));
		criterionincreaseMedian.setText(doubleFormat.format(getMedianForAllCriterions()));
		criterionincreaseDeviation.setText(doubleFormat.format(getStandarddeviationForAllCriterions()));

	}

	public JFreeChart createStudentRadarChart(ObservableList<EvaluationHelper> ehList) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (EvaluationHelper eh : allEvaluationHelper) {

			ArrayList<Double> values = getAverageDataPerStudent(ehList);

			defaultcategorydataset.addValue(
					values.get(allEvaluationHelper.indexOf(eh)),
					SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"),
					eh.getRawId() + " " + SeCatResourceBundle.getInstance().getString("scene.chart.all.value.lable") + ": "
							+ doubleFormat.format(values.get(allEvaluationHelper.indexOf(eh))));
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

	public JFreeChart createAverageRadarChartForBereich(DefaultCategoryDataset data) {
		DefaultCategoryDataset defaultcategorydataset;
		if (data != null) {
			defaultcategorydataset = data;
		} else {
			defaultcategorydataset = getAverageDataSetForBereich();
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

	public JFreeChart createAverageRadarChartForItem() {
		DefaultCategoryDataset defaultcategorydataset = getAverageDataSetForItem();

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

		int iBewertung = 0;
		z = 0;
		for (Item item : eh.getItems()) {
			if (item != null) {

				defaultcategorydataset.addValue((Double.parseDouble(eh.getItemWertung().get(iBewertung++))), eh.getRawId(), z++ + ": " + item.getName());
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

	public JFreeChart createProfilChartForItem(EvaluationHelper eh, Fragebogen fragebogen) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (Item item : eh.getItems()) {
			if (!eh.getItemWertung().get(eh.getItems().indexOf(item)).isEmpty()) {
				defaultcategorydataset.addValue(Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(item))), eh.getRawId(), item.getFrage());
			} else {
				defaultcategorydataset.addValue(0, eh.getRawId(), item.getFrage());
			}

		}

		JFreeChart chart = ChartFactory.createLineChart(fragebogen.getName(), // chart title
				"", // domain axis label
				"", // range axis label
				defaultcategorydataset, // data
				PlotOrientation.HORIZONTAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.lightGray);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(false);

		plot.getRangeAxis().setUpperBound(fragebogen.getSkala().getSchritte());
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);

		Font font = new Font("Courier New", Font.BOLD, 12);

		int categorysize = plot.getCategories().size();
		String categoryName = (String) plot.getCategories().get(categorysize - 1);
		plot.getCategories().get(categorysize - 2);

		CategoryTextAnnotation annotation = new CategoryTextAnnotation("Annotation", categoryName, 8.0);
		annotation.setFont(font);
		annotation.setTextAnchor(TextAnchor.CENTER_LEFT);
		annotation.setCategoryAnchor(CategoryAnchor.START);
		plot.addAnnotation(annotation);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);

		return chart;

	}

	public JFreeChart createMixedRadarChartForBereich(EvaluationHelper eh, Fragebogen fragebogen) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (int i = 0; i < bereiche.size(); i++) {
			defaultcategorydataset.addValue(avValueBereich[i], SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), bereiche.get(i)
					.getName());
		}

		double avValue[] = new double[bereiche.size()];
		int valCount = 0;
		for (Bereich bereich : bereiche) {
			valCount = 0;
			for (Item item : eh.getItems()) {

				if (bereich.equals(item.getBereich())) {
					if (!eh.getItemWertung().get(valCount).isEmpty()) {
						avValue[bereiche.indexOf(bereich)] += Double.parseDouble(eh.getItemWertung().get(valCount));
					}
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

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();
		spiderwebplot.setMaxValue(fragebogen.getSkala().getSchritte());

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;

	}

	public DefaultCategoryDataset getAverageDataSetForBereich() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for (int i = 0; i < bereiche.size(); i++) {
			defaultcategorydataset.addValue(
					avValueBereich[i],
					SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"),
					bereiche.get(i).getName() + " " + SeCatResourceBundle.getInstance().getString("scene.chart.all.value.lable") + ":"
							+ doubleFormat.format(avValueBereich[i]));
		}
		return defaultcategorydataset;
	}

	public DefaultCategoryDataset getAverageDataSetForBereichCompare(ObservableList<EvaluationHelper> ehToCompare, ArrayList<Bereich> bereicheToCompare,
			double[] avValueToCompare, String fragebogenNameToCompare) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (int i = 0; i < bereiche.size(); i++) {
			defaultcategorydataset.addValue(avValueBereich[i], fragebogen.getName(),
					bereiche.get(i).getName() + " Values: " + doubleFormat.format(avValueBereich[i]) + ", " + doubleFormat.format(avValueToCompare[i]));
			// }

			// for (int i = 0; i < bereicheToCompare.size(); i++) {
			defaultcategorydataset
					.addValue(
							avValueToCompare[i],
							fragebogenNameToCompare,
							bereicheToCompare.get(i).getName() + " Values: " + doubleFormat.format(avValueBereich[i]) + ", "
									+ doubleFormat.format(avValueToCompare[i]));
		}

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
		int categoryCount = bereiche.size();
		int entityCount = allEvaluationHelper.size();

		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for (int j = 0; j < categoryCount; j++) {
			final List list = new ArrayList();
			for (int k = 0; k < entityCount; k++) {

				int valCount = 0;
				double avValue = 0;

				for (Item item : allEvaluationHelper.get(k).getItems()) {
					if (bereiche.get(j).equals(item.getBereich())) {
						if (!allEvaluationHelper.get(k).getItemWertung().get(valCount).isEmpty()) {
							avValue += Double.parseDouble(allEvaluationHelper.get(k).getItemWertung().get(valCount));
						}
						valCount++;
					}

				}
				if (valCount != 0) {
					avValue /= valCount;
				}

				list.add(new Double(avValue));
			}
			dataset.add(list, SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), bereiche.get(j).getName());
		}

		final CategoryAxis xAxis = new CategoryAxis("Type");
		final NumberAxis yAxis = new NumberAxis("Value");
		yAxis.setAutoRangeIncludesZero(false);
		// yAxis.setRange(0d, fragebogen.getSkala().getSchritte());
		final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		renderer.setMaximumBarWidth(.03);
		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
		final JFreeChart chart = new JFreeChart(fragebogen.getName(), new Font("SansSerif", Font.BOLD, 14), plot, true);

		return chart;

	}

	public double[] getAverageDataForCriterion(double[] avValuesParameter, ArrayList<Bereich> bereicheParameter) {
		double[] avValue;
		ArrayList<Bereich> bereicheList;
		if (avValuesParameter != null) {
			avValue = avValuesParameter;

		} else {

			avValue = avValueBereich;
		}
		if (bereicheParameter != null) {
			bereicheList = bereicheParameter;
		} else {
			bereicheList = bereiche;

		}

		ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereicheList) {
			if (hfList.isEmpty() || !hfList.contains(bereich.getHandlungsfeld())) {
				hfList.add(bereich.getHandlungsfeld());
			}
		}
		double[] values = new double[hfList.size()];

		int valCount = 0;
		for (Handlungsfeld hf : hfList) {
			valCount = 0;
			for (Bereich bereich : bereicheList) {
				if (bereich.getHandlungsfeld().equals(hf)) {
					values[hfList.indexOf(hf)] += avValue[bereicheList.indexOf(bereich)];
					valCount++;
				}
			}
			values[hfList.indexOf(hf)] /= valCount;

		}
		return values;
	}

	public double getAverageDataForAllCriterions() {
		double ret = 0;
		double[] values = getAverageDataForCriterion(null, null);
		for (double d : values) {
			ret += d;
		}
		return ret / values.length;
	}

	public double getStandarddeviationForAllCriterions() {

		double av = getAverageDataForAllCriterions();
		double temp = 0;
		for (double d : getAverageDataForCriterion(null, null)) {
			temp += (d - av) * (d - av);
		}
		temp /= (avValueBereich.length) - 1;
		return Math.sqrt(temp);

	}

	public double getMedianForAllCriterions() {
		double ret = 0;
		double[] values = getAverageDataForCriterion(null, null);

		Arrays.sort(values);

		if (values.length == 1) {
			return values[0];
		}

		if (values.length % 2 == 0) {

			return (values[values.length / 2] + values[values.length / 2 - 1]) / 2;

		} else {
			return values[values.length / 2];
		}

	}

	public JFreeChart createBarChartForCriterion() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double[] values = getAverageDataForCriterion(null, null);
		ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereiche) {
			if (hfList.isEmpty() || !hfList.contains(bereich.getHandlungsfeld())) {
				hfList.add(bereich.getHandlungsfeld());
			}
		}
		for (Handlungsfeld hf : hfList) {
			defaultcategorydataset.addValue(values[hfList.indexOf(hf)], SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"),
					hf.getName());
		}
		return createBarChart(defaultcategorydataset, fragebogen.getName(), SeCatResourceBundle.getInstance().getString("scene.chart.criterionincrease"));

	}

	public JFreeChart createBarChartForCriterionEvaluationCompare(ArrayList<Bereich> bereicheToCompare, double[] avValuesToCompare, String name) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double[] values = getAverageDataForCriterion(null, null);
		ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereiche) {
			if (hfList.isEmpty() || !hfList.contains(bereich.getHandlungsfeld())) {
				hfList.add(bereich.getHandlungsfeld());
				defaultcategorydataset.addValue(values[bereiche.indexOf(bereich)], fragebogen.getName(), bereich.getHandlungsfeld().getName());
			}
		}

		ArrayList<Handlungsfeld> hfListToCompare = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereicheToCompare) {
			if (hfListToCompare.isEmpty() || !hfListToCompare.contains(bereich.getHandlungsfeld())) {
				hfListToCompare.add(bereich.getHandlungsfeld());
				defaultcategorydataset.addValue(values[bereicheToCompare.indexOf(bereich)], name, bereich.getHandlungsfeld().getName());
			}
		}

		return createBarChart(defaultcategorydataset, fragebogen.getName(), name);

	}

	public JFreeChart createBarChart(DefaultCategoryDataset defaultcategorydataset, String name, String titel) {
		JFreeChart chart = ChartFactory.createBarChart(name, titel, "", defaultcategorydataset, PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, fragebogen.getSkala().getSchritte());

		BarRenderer br = (BarRenderer) plot.getRenderer();
		br.setMaximumBarWidth(.05);
		return chart;

	}

	public DefaultCategoryDataset createDataSetForCriterion() {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereiche) {
			if (hfList.isEmpty() || !hfList.contains(bereich.getHandlungsfeld())) {
				hfList.add(bereich.getHandlungsfeld());
			}
		}

		return defaultcategorydataset;

	}

	public JFreeChart createBarChartStudent(ObservableList<EvaluationHelper> ehList) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (EvaluationHelper eh : allEvaluationHelper) {

			ArrayList<Double> values = getAverageDataPerStudent(ehList);

			defaultcategorydataset.addValue(
					values.get(allEvaluationHelper.indexOf(eh)),
					SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"),
					eh.getRawId() + " " + SeCatResourceBundle.getInstance().getString("scene.chart.all.value.lable") + ": "
							+ doubleFormat.format(values.get(allEvaluationHelper.indexOf(eh))));
		}

		JFreeChart chart = ChartFactory.createBarChart(fragebogen.getName(), SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"), "",
				defaultcategorydataset, PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(0, fragebogen.getSkala().getSchritte());

		BarRenderer br = (BarRenderer) plot.getRenderer();
		br.setMaximumBarWidth(.05);
		return chart;

	}

	public Fragebogen getFragebogen() {
		return fragebogen;
	}

	public ArrayList<Double> getAverageDataPerCriterion() {
		ArrayList<Double> ret = new ArrayList<Double>();
		for (EvaluationHelper eh : allEvaluationHelper) {
			double value = 0;
			for (String wert : eh.getItemWertung()) {
				value += Double.parseDouble(wert);
			}
			value /= eh.getItemWertung().size();

			ret.add(value);
		}
		return ret;
	}

	public double getAverageDataForSubCriterion() {
		double ret = 0;
		for (int i = 0; i < bereiche.size(); i++) {
			ret += avValueBereich[i];
		}
		return ret / bereiche.size();
	}

	public double getMedianForAllSubCriterions() {

		double[] temp2 = avValueBereich;
		Arrays.sort(temp2);

		if (temp2.length == 1) {
			return temp2[0];
		}

		if (temp2.length % 2 == 0) {

			return (temp2[temp2.length / 2] + temp2[temp2.length / 2 - 1]) / 2;

		} else {
			return temp2[temp2.length / 2];
		}

	}

	public double getStandarddeviationForAllSubCriterions() {

		double av = getAverageDataForSubCriterion();
		double temp = 0;
		for (double d : avValueBereich) {
			temp += (d - av) * (d - av);
		}
		temp /= (avValueBereich.length) - 1;
		return Math.sqrt(temp);

	}

	public ArrayList<Double> getAverageDataPerStudent(ObservableList<EvaluationHelper> ehList) {
		ArrayList<Double> ret = new ArrayList<Double>();
		for (EvaluationHelper eh : ehList) {
			double value = 0;
			for (String wert : eh.getItemWertung()) {
				if (!wert.isEmpty()) {
					value += Double.parseDouble(wert);
				}
			}
			value /= eh.getItemWertung().size();

			ret.add(value);

		}
		return ret;
	}

	public double getAverageForAllStudents(ObservableList<EvaluationHelper> ehList) {
		double ret = 0;
		for (double d : getAverageDataPerStudent(ehList)) {
			ret += d;
		}
		return ret / ehList.size();
	}

	public double getMedianForAllStudents(ObservableList<EvaluationHelper> ehList) {
		ArrayList<Double> temp = getAverageDataPerStudent(ehList);
		double[] temp2 = new double[temp.size()];
		for (double d : temp) {
			temp2[temp.indexOf(d)] = d;

		}
		Arrays.sort(temp2);

		if (temp2.length % 2 == 0) {

			return (temp2[temp2.length / 2] + temp2[temp2.length / 2 - 1]) / 2;

		} else {
			return temp2[temp2.length / 2];
		}

	}

	public double getStandarddeviationForAllStudents(ObservableList<EvaluationHelper> ehList) {

		double av = getAverageForAllStudents(ehList);
		double temp = 0;
		for (double d : getAverageDataPerStudent(ehList)) {
			temp += (d - av) * (d - av);
		}
		temp /= (getAverageDataPerStudent(ehList).size()) - 1;
		return Math.sqrt(temp);

	}

	public double[] getAvValueforBereiche(List<Bewertung> bewertungen, List<Bereich> bereiche) {
		double[] values = new double[bereiche.size()];
		int valCount = 0;
		for (Bereich bereich : bereiche) {
			valCount = 0;
			for (Bewertung bewertung : bewertungen) {
				if (bewertung.getItem() != null) {
					if (bereich.equals(bewertung.getItem().getBereich())) {
						if (!bewertung.getWert().isEmpty())
							values[bereiche.indexOf(bereich)] += Double.parseDouble(bewertung.getWert());
						valCount++;
					}
				}
			}
			if (valCount != 0) {
				values[bereiche.indexOf(bereich)] /= valCount;
			}
		}
		return values;
	}

	public EvaluationHelper getSelectedItem() {
		return tableViewAll.getSelectionModel().getSelectedItem();
	}

	public ObservableList<EvaluationHelper> setOutliers(ObservableList<EvaluationHelper> ehList) {
		double deviation = getStandarddeviationForAllStudents(ehList);
		double average = getAverageForAllStudents(ehList);
		double max = average + 2.0d * deviation;
		double min = average - 2.0d * deviation;
		ArrayList<Double> avData = getAverageDataPerStudent(ehList);
		for (EvaluationHelper eh : ehList) {

			if (avData.get(ehList.indexOf(eh)) <= min || avData.get(ehList.indexOf(eh)) >= max) {
				eh.setOutlier(true);
			}

		}
		return ehList;
	}

	private TableColumn createHead(boolean all) {

		final TableColumn colHeadData = new TableColumn();
		final TableColumn<EvaluationHelper, String> col0 = new TableColumn<EvaluationHelper, String>();
		final TableColumn<EvaluationHelper, String> col1 = new TableColumn<EvaluationHelper, String>();
		final TableColumn<EvaluationHelper, String> col2 = new TableColumn<EvaluationHelper, String>();
		final TableColumn<EvaluationHelper, String> col3 = new TableColumn<EvaluationHelper, String>();
		final TableColumn<EvaluationHelper, Node> col4 = new TableColumn<EvaluationHelper, Node>();
		col0.setVisible(true);
		col2.setVisible(true);
		col3.setVisible(true);
		col4.setVisible(true);

		col0.setMinWidth(100);
		col1.setMinWidth(100);
		col2.setMinWidth(100);
		col3.setMinWidth(100);
		col4.setMinWidth(100);
		Text colHeaderTextHead = new Text("Kopfdaten");
		colHeadData.setGraphic(colHeaderTextHead);
		col0.setGraphic(new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.welle")));
		col1.setGraphic(new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.rawid")));
		col2.setGraphic(new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.source")));
		col3.setGraphic(new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.zeit")));
		col4.setGraphic(new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.outlier")));

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
				return new ReadOnlyObjectWrapper<String>(p.getValue().getSource());

			}
		});
		col3.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {

				return new ReadOnlyObjectWrapper<String>(p.getValue().getZeit());

			}
		});
		col3.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {

				return new ReadOnlyObjectWrapper<String>(p.getValue().getZeit());

			}
		});

		col4.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, Node>, ObservableValue<Node>>() {

			public ObservableValue<Node> call(CellDataFeatures<EvaluationHelper, Node> p) {
				if (p.getValue().isOutlier()) {
					return new ReadOnlyObjectWrapper<Node>(new ImageView(new Image("/image/icons/flag.png", 16, 16, true, true)));
				} else {
					return new ReadOnlyObjectWrapper<Node>(null);
				}
			}
		});
		if (all == true) {
			colHeadData.getColumns().addAll(col0, col1, col2, col3, col4);
			return colHeadData;
		} else {

			colHeadData.getColumns().addAll(col1, col4);
			return colHeadData;
		}
	}

	private TableColumn createQuestions(boolean all) {

		final TableColumn colFragenHead = new TableColumn();
		Text tf = new Text(SeCatResourceBundle.getInstance().getString("scene.frageboegen.frage.label"));
		tf.setTextAlignment(TextAlignment.CENTER);
		colFragenHead.setGraphic(tf);

		wertungCount = 0;
		if (!fragenList.isEmpty()) {

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
				colFrage.setVisible(true);
				colFragenHead.getColumns().add(colFrage);

				colFrage.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<Text>>() {

					public ObservableValue<Text> call(CellDataFeatures<EvaluationHelper, String> p) {
						if (wertungCount == p.getValue().getFrageWertung().size()) {
							System.out.println(p.getValue().getFrageWertung().size());
							wertungCount = 0;
						}
						Text t = new Text(p.getValue().getFrageWertung().get(wertungCount++));
						t.setWrappingWidth(125);
						System.out.println(wertungCount);
						return new ReadOnlyObjectWrapper<Text>(t);
					}
				});

			}
		}
		return colFragenHead;
	}

	private ObservableList<TableColumn<EvaluationHelper, String>> createItemColumns(boolean all) {
		ObservableList<TableColumn<EvaluationHelper, String>> colList = FXCollections.observableArrayList();
		constColumns = 1;
		itemCount = 0;
		actualColumn = 0;
		for (Bereich bereich : bereiche) {

			final TableColumn col = new TableColumn();
			col.setMinWidth(300);
			col.setPrefWidth(300);
			col.setMaxWidth(Double.MAX_VALUE);
			String name = bereich.getName();

			Text t = new Text(name);
			t.setTextAlignment(TextAlignment.CENTER);

			if (bereich.getName().length() > 15) {
				t.setWrappingWidth(200);
			}

			col.setGraphic(t);

			if (all == true) {
				int count = 0;

				for (Item item : fragebogen.getItems()) {
					if (item.getBereich().equals(bereich)) {
						TableColumn itemCol = new TableColumn();
						Text itemName = new Text(item.getFrage());
						itemName.setWrappingWidth(125);
						itemCol.setGraphic(itemName);
						itemCol.setMinWidth(225);
						// itemCol.setMaxWidth(125);
						col.getColumns().add(itemCol);

						((TableColumn<EvaluationHelper, String>) col.getColumns().get(count))
								.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

									public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
										if (actualColumn == tableViewAll.getColumns().get(constColumns).getColumns().size()) {
											// constColumns = +1;
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
			} else {
				final TableColumn itemAverageCol = new TableColumn();
				Text itemAvText = new Text(SeCatResourceBundle.getInstance().getString("scene.evaluation.lable.average"));
				itemAvText.setWrappingWidth(125);
				itemAverageCol.setGraphic(itemAvText);
				itemAverageCol.setMinWidth(125);
				itemAverageCol.setPrefWidth(200);

				col.getColumns().add(itemAverageCol);

				itemAverageCol.setCellValueFactory(new Callback<CellDataFeatures<EvaluationHelper, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<EvaluationHelper, String> p) {
						;
						int i = 0;
						float ret = 0;
						for (Item item : p.getValue().getItems()) {
							if (item.getBereich().equals(bereiche.get(itemAverageCol.getParentColumn().getColumns().indexOf(itemAverageCol)))) {
								ret += Float.parseFloat(p.getValue().getItemWertung().get(p.getValue().getItems().indexOf(item)));
								i++;
							}

						}
						if (i > 0) {

							ret /= i;
						}
						return new ReadOnlyObjectWrapper<String>(String.valueOf(doubleFormat.format(ret)));

					}
				});

			}
			colList.add(col);

		}
		return colList;
	}

	public void setRowFactory(Callback<TableView<EvaluationHelper>, TableRow<EvaluationHelper>> arg0) {

		tableViewAll.setRowFactory(arg0);
		tableViewLeast.setRowFactory(arg0);
		tableViewItems.setRowFactory(arg0);
		tableViewQuestions.setRowFactory(arg0);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.evaluation.lable.title";
	}

	@Override
	public void setTitle() {

		setTitle(" " + fragebogen.getName());
	}
}
