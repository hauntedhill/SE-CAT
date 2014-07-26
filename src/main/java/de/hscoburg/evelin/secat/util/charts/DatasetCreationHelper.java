package de.hscoburg.evelin.secat.util.charts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;

import de.hscoburg.evelin.secat.controller.BewertungAnzeigenController;
import de.hscoburg.evelin.secat.controller.helper.EvaluationHelper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

/**
 * Hilfklasse um Datasets für Charts zu erzeugen
 * 
 * @author moro1000
 * 
 */
public class DatasetCreationHelper {
	@Autowired
	private BewertungAnzeigenController bewertungAnzeigenController;

	static private DecimalFormat doubleFormat = new DecimalFormat("#0.00");

	static public DefaultCategoryDataset getAverageDataSetForBereich(ArrayList<Bereich> bereiche, double[] avValueBereich) {
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

	static public DefaultCategoryDataset createAverageRadarDatasetForBereich(DefaultCategoryDataset data, ArrayList<Bereich> bereiche, double[] avValueBereich) {
		DefaultCategoryDataset defaultcategorydataset;
		if (data != null) {
			defaultcategorydataset = data;
		} else {
			defaultcategorydataset = DatasetCreationHelper.getAverageDataSetForBereich(bereiche, avValueBereich);
		}
		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset createDatasetForStudentRadarChart(ObservableList<EvaluationHelper> allEvaluationHelper) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (EvaluationHelper eh : allEvaluationHelper) {

			ArrayList<Double> values = CalculationHelper.getAverageDataPerStudent(allEvaluationHelper);

			defaultcategorydataset.addValue(
					values.get(allEvaluationHelper.indexOf(eh)),
					SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"),
					eh.getRawId() + " " + SeCatResourceBundle.getInstance().getString("scene.chart.all.value.lable") + ": "
							+ doubleFormat.format(values.get(allEvaluationHelper.indexOf(eh))));
		}

		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset createDatasetForStudentBereich(EvaluationHelper eh, Fragebogen fragebogen, ArrayList<Bereich> bereiche,
			double[] avValueBereich) {

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

		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset createDataSetForStudent(ObservableList<EvaluationHelper> allEvaluationHelper) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (EvaluationHelper eh : allEvaluationHelper) {

			ArrayList<Double> values = CalculationHelper.getAverageDataPerStudent(allEvaluationHelper);

			defaultcategorydataset.addValue(
					values.get(allEvaluationHelper.indexOf(eh)),
					SeCatResourceBundle.getInstance().getString("scene.chart.studentincrease"),
					eh.getRawId() + " " + SeCatResourceBundle.getInstance().getString("scene.chart.all.value.lable") + ": "
							+ doubleFormat.format(values.get(allEvaluationHelper.indexOf(eh))));
		}

		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset getAverageDataSetForBereichCompare(ObservableList<EvaluationHelper> ehToCompare, ArrayList<Bereich> bereicheToCompare,
			double[] avValueToCompare, String fragebogenNameToCompare, Fragebogen fragebogen, ArrayList<Bereich> bereiche, double[] avValueBereich) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		for (int i = 0; i < bereiche.size(); i++) {
			defaultcategorydataset.addValue(avValueBereich[i], fragebogen.getName(),
					bereiche.get(i).getName() + " Values: " + doubleFormat.format(avValueBereich[i]) + ", " + doubleFormat.format(avValueToCompare[i]));

			defaultcategorydataset
					.addValue(
							avValueToCompare[i],
							fragebogenNameToCompare,
							bereicheToCompare.get(i).getName() + " Values: " + doubleFormat.format(avValueBereich[i]) + ", "
									+ doubleFormat.format(avValueToCompare[i]));
		}

		return defaultcategorydataset;
	}

	static public DefaultBoxAndWhiskerCategoryDataset getDatasetForBoxAndWhiskers(ObservableList<EvaluationHelper> allEvaluationHelper,
			ArrayList<Bereich> bereiche) {

		int categoryCount = bereiche.size();
		int entityCount = allEvaluationHelper.size();

		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for (int j = 0; j < categoryCount; j++) {
			final List<Double> list = new ArrayList<Double>();
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
		return dataset;
	}

	static public DefaultCategoryDataset getDatasetForCriterionBarChart(double[] avValueBereich, ArrayList<Bereich> bereiche) {
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

		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset createDatasetForCriterionEvaluationCompareBarChart(ArrayList<Bereich> bereiche, double[] avValueBereich,
			Fragebogen fragebogen, ArrayList<Bereich> bereicheToCompare, double[] avValuesToCompare, String name) {
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
		return defaultcategorydataset;
	}
	/*
	 * static public JFreeChart createStudentItemDatasetForKiviat(EvaluationHelper eh, Fragebogen fragebogen, List<EvaluationHelper>
	 * allEvaluationHelper) {
	 * 
	 * DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
	 * 
	 * int z = 0; int anzItems = allEvaluationHelper.get(0).getItems().size(); int iBewertung = 0; double[] werte = new double[anzItems];
	 * 
	 * for (EvaluationHelper evalHelper : allEvaluationHelper) { int iWerte = 0; for (Item item : evalHelper.getItems()) { if (item != null)
	 * { werte[iWerte] += ((Double.parseDouble(evalHelper.getItemWertung().get(iWerte++)))); } } z = 0; for (int j = 0; j < anzItems; j++)
	 * defaultcategorydataset.addValue((werte[j] / allEvaluationHelper.size()),
	 * SeCatResourceBundle.getInstance().getString("scene.chart.all.averagevalues"), z++ + ": " +
	 * allEvaluationHelper.get(0).getItems().get(j).getName()); }
	 * 
	 * z = 0; for (Item item : eh.getItems()) { if (item != null) {
	 * 
	 * defaultcategorydataset.addValue((Double.parseDouble(eh.getItemWertung().get(iBewertung++))), eh.getRawId(), z++ + ": " +
	 * item.getName()); }
	 * 
	 * }
	 * 
	 * return ChartCreationHelper.createKiviatChart(defaultcategorydataset, fragebogen);
	 * 
	 * }
	 */

}
