package de.hscoburg.evelin.secat.controller;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.text.DecimalFormat;
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
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
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
import de.hscoburg.evelin.secat.util.charts.CalculationHelper;
import de.hscoburg.evelin.secat.util.charts.ChartCreationHelper;
import de.hscoburg.evelin.secat.util.charts.DatasetCreationHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Controller zur Anzeige der Auswertung
 * 
 * @author moro1000
 * 
 */
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

	private Fragebogen fragebogen;
	private ArrayList<Frage> fragenList = new ArrayList<Frage>();
	private ObservableList<EvaluationHelper> allEvaluationHelper = FXCollections.observableArrayList();
	private ArrayList<Bereich> bereiche = new ArrayList<Bereich>();
	private double[] avValueBereich;

	private int itemCount;
	private int wertungCount = 0;
	private int constColumns;
	private int actualColumn;

	private DecimalFormat doubleFormat = new DecimalFormat("#0.00");

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
		fragebogen = bewertungController.getSelectedFragebogen();

		for (Frage_Fragebogen frage : fragebogen.getFrageFragebogen()) {
			fragenList.add(frage.getFrage());
		}

		ObservableList<Bewertung> bewertungOl = FXCollections.observableArrayList(fragebogen.getBewertungen());

		bereiche = EvaluationHelper.getBereicheFromEvaluationHelper(bewertungOl);
		avValueBereich = CalculationHelper.getAvValueforBereiche(bewertungOl, bereiche);

		allEvaluationHelper = setOutliers(EvaluationHelper.createEvaluationHelperList(bewertungOl, fragebogen.getFrageFragebogen()));

		tableViewAll.getColumns().add(createHead(true));
		tableViewLeast.getColumns().add(createHead(false));
		tableViewItems.getColumns().add(createHead(false));
		tableViewQuestions.getColumns().add(createHead(false));

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
					double[] avToCompare = CalculationHelper.getAvValueforBereiche(fragebogenToCompare.getBewertungen(), bereicheToCompare);

					JFreeChart barchart = createBarChartForCriterionEvaluationCompare(bereicheToCompare, avToCompare, fragebogenToCompare.getName());

					SwingNode evaluationCompareBarSwingNode = new SwingNode();
					evaluationCompareBarSwingNode.setContent(new ChartPanel(barchart));
					evaluationComparePaneBar.add(evaluationCompareBarSwingNode, 1, 1);

					JFreeChart kiviatchart = ChartCreationHelper.createKiviatChart(
							DatasetCreationHelper.createAverageRadarChartForBereich(
									DatasetCreationHelper.getAverageDataSetForBereichCompare(ehToCompare, bereicheToCompare, avToCompare,
											fragebogenToCompare.getName(), fragebogen, bereiche, avValueBereich), bereiche, avValueBereich),
							fragebogenToCompare);

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
		criterionincreaseSwingNode.setContent(new ChartPanel(createBarChartForCriterion(avValueBereich, bereiche)));
		criterionincreasePaneBarchart.add(criterionincreaseSwingNode, 1, 1);

		SwingNode studentincreaseSwingNodeKiviat = new SwingNode();
		studentincreaseSwingNodeKiviat.setContent(new ChartPanel(ChartCreationHelper.createKiviatChart(
				DatasetCreationHelper.createDatasetForStudentRadarChart(allEvaluationHelper), fragebogen)));
		studentincreasePaneKiviat.add(studentincreaseSwingNodeKiviat, 1, 1);

		SwingNode chartSwingNode = new SwingNode();

		chartSwingNode.setContent(new ChartPanel(ChartCreationHelper.createKiviatChart(
				DatasetCreationHelper.createAverageRadarChartForBereich(null, bereiche, avValueBereich), fragebogen)));
		subcriterionincreasePaneKiviat.add(chartSwingNode, 1, 1);

		SwingNode boxPlotChartSwingNode = new SwingNode();
		boxPlotChartSwingNode.setContent(new ChartPanel(createBoxPlot()));
		subcriterionincreasePaneBoxplot.add(boxPlotChartSwingNode, 1, 1);

		SwingNode studentincreaseSwingNodeBarchart = new SwingNode();
		studentincreaseSwingNodeBarchart.setContent(new ChartPanel(ChartCreationHelper.createBarChart(
				DatasetCreationHelper.createDataSetForStudent(allEvaluationHelper), fragebogen.getName(),
				SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"), fragebogen)));

		studentincreasePaneBarchart.add(studentincreaseSwingNodeBarchart, 1, 1);
		studentincreaseAverage.setText(doubleFormat.format(CalculationHelper.getAverageForAllStudents(allEvaluationHelper)));
		studentincreaseMedian.setText(doubleFormat.format(CalculationHelper.getMedianForAllStudents(allEvaluationHelper)));
		studentincreaseDeviation.setText(doubleFormat.format(CalculationHelper.getStandarddeviationForAllStudents(allEvaluationHelper)));
		studentincreaseAverage.setEditable(false);
		studentincreaseMedian.setEditable(false);
		studentincreaseDeviation.setEditable(false);

		subcriterionincreaseAverage.setText(doubleFormat.format(CalculationHelper.getAverageDataForSubCriterion(bereiche, avValueBereich)));
		subcriterionincreaseMedian.setText(doubleFormat.format(CalculationHelper.getMedianForAllSubCriterions(avValueBereich)));
		subcriterionincreaseDeviation.setText(doubleFormat.format(CalculationHelper.getStandarddeviationForAllSubCriterions(bereiche, avValueBereich)));

		criterionincreaseAverage.setText(doubleFormat.format(CalculationHelper.getAverageDataForAllCriterions(avValueBereich, bereiche)));
		criterionincreaseMedian.setText(doubleFormat.format(CalculationHelper.getMedianForAllCriterions(avValueBereich, bereiche)));
		criterionincreaseDeviation.setText(doubleFormat.format(CalculationHelper.getStandarddeviationForAllCriterions(avValueBereich, bereiche)));

	}

	public JFreeChart createDatasetForStudentBereichContext(EvaluationHelper eh) {

		return ChartCreationHelper
				.createKiviatChart(DatasetCreationHelper.createDatasetForStudentBereich(eh, fragebogen, bereiche, avValueBereich), fragebogen);

	}

	public JFreeChart createProfilChartForItem(EvaluationHelper eh, Fragebogen fragebogen) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (Item item : eh.getItems()) {
			if (!eh.getItemWertung().get(eh.getItems().indexOf(item)).isEmpty()) {
				defaultcategorydataset.addValue(Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(item))), eh.getRawId(), item.getName());
			} else {
				defaultcategorydataset.addValue(0, eh.getRawId(), item.getName());
			}

		}

		JFreeChart chart = ChartFactory.createLineChart(fragebogen.getName(), "", "", // range axis label
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
		rangeAxis.setTickUnit(new NumberTickUnit(1.0));

		final CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
		categoryAxis.setMaximumCategoryLabelLines(2);

		plot.getRangeAxis().setUpperBound(fragebogen.getSkala().getSchritte());
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);

		Font font = new Font("Courier New", Font.PLAIN, 12);

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

		return chart;

	}

	/*
	 * public DefaultCategoryDataset getAverageDataSetForItem() { DefaultCategoryDataset defaultcategorydataset = new
	 * DefaultCategoryDataset();
	 * 
	 * if (!allEvaluationHelper.isEmpty()) {
	 * 
	 * int anzItems = allEvaluationHelper.get(0).getItems().size(); double[] werte = new double[anzItems];
	 * 
	 * for (EvaluationHelper evalHelper : allEvaluationHelper) { int iWerte = 0; for (Item item : evalHelper.getItems()) { if (item != null)
	 * {
	 * 
	 * werte[iWerte] += ((Double.parseDouble(evalHelper.getItemWertung().get(iWerte++)))); } }
	 * 
	 * for (int j = 0; j < anzItems; j++) defaultcategorydataset.addValue((werte[j] / allEvaluationHelper.size()),
	 * SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), j + ": " +
	 * allEvaluationHelper.get(0).getItems().get(j).getName());
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return defaultcategorydataset; }
	 */

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
		final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		renderer.setFillBox(false);
		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		renderer.setMaximumBarWidth(.03);
		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

		CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
		categoryAxis.setMaximumCategoryLabelLines(5);

		final JFreeChart chart = new JFreeChart(fragebogen.getName(), new Font("SansSerif", Font.BOLD, 14), plot, true);

		return chart;

	}

	public JFreeChart createBarChartForCriterion(double[] avValueBereich, ArrayList<Bereich> bereiche) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double[] values = CalculationHelper.getAverageDataForCriterion(avValueBereich, bereiche);
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

		return ChartCreationHelper.createBarChart(defaultcategorydataset, fragebogen.getName(),
				SeCatResourceBundle.getInstance().getString("scene.chart.criterionincrease"), fragebogen);

	}

	public JFreeChart createBarChartForCriterionEvaluationCompare(ArrayList<Bereich> bereicheToCompare, double[] avValuesToCompare, String name) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double[] values = CalculationHelper.getAverageDataForCriterion(avValueBereich, bereiche);
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

		return ChartCreationHelper.createBarChart(defaultcategorydataset, fragebogen.getName(), name, fragebogen);

	}

	/*
	 * public DefaultCategoryDataset createDataSetForCriterion() {
	 * 
	 * DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
	 * 
	 * ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>(); for (Bereich bereich : bereiche) { if (hfList.isEmpty() ||
	 * !hfList.contains(bereich.getHandlungsfeld())) { hfList.add(bereich.getHandlungsfeld()); } }
	 * 
	 * return defaultcategorydataset;
	 * 
	 * }
	 */

	public Fragebogen getFragebogen() {
		return fragebogen;
	}

	public EvaluationHelper getSelectedItem() {
		if (tableViewAll.isFocused()) {
			return tableViewAll.getSelectionModel().getSelectedItem();
		} else if (tableViewLeast.isFocused()) {
			return tableViewLeast.getSelectionModel().getSelectedItem();
		} else if (tableViewItems.isFocused()) {
			return tableViewItems.getSelectionModel().getSelectedItem();
		} else {
			return tableViewQuestions.getSelectionModel().getSelectedItem();
		}

	}

	public ObservableList<EvaluationHelper> setOutliers(ObservableList<EvaluationHelper> ehList) {
		double deviation = CalculationHelper.getStandarddeviationForAllStudents(ehList);
		double average = CalculationHelper.getAverageForAllStudents(ehList);
		double max = average + 2.0d * deviation;
		double min = average - 2.0d * deviation;
		ArrayList<Double> avData = CalculationHelper.getAverageDataPerStudent(ehList);
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
							wertungCount = 0;
						}
						Text t = new Text(p.getValue().getFrageWertung().get(wertungCount++));
						t.setWrappingWidth(125);
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

	public ObservableList<EvaluationHelper> getAllEvaluationHelper() {
		return allEvaluationHelper;
	}

	public ArrayList<Bereich> getBereiche() {
		return bereiche;
	}

	public double[] getAvValueBereich() {
		return avValueBereich;
	}

}
