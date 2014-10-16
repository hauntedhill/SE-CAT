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
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
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

	static public DefaultCategoryDataset createDatasetForMultiperspektive(List<Fragebogen> fragebogenList) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		ArrayList<Bereich> bereiche = EvaluationHelper.getBereicheFromEvaluationHelper(fragebogenList.get(0).getBewertungen());

		for (Fragebogen f : fragebogenList) {
			ObservableList<EvaluationHelper> ehList = EvaluationHelper.createEvaluationHelperList(f.getBewertungen(), null);
			for (EvaluationHelper eh : ehList) {
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
					} else {

						avValue[bereiche.indexOf(bereich)] = 0;
					}
				}
				for (int j = 0; j < avValue.length; j++) {
					defaultcategorydataset.addValue(avValue[j], f.getPerspektive().getName() + " : " + eh.getRawId(), bereiche.get(j).getName());

				}

			}

		}

		return defaultcategorydataset;

	}

	static public DefaultCategoryDataset createDatasetForEvaluationItemCompare(List<Fragebogen> fragebogenList) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		ObservableList<EvaluationHelper> ehListinit = EvaluationHelper.createEvaluationHelperList(fragebogenList.get(0).getBewertungen(), null);
		List<Item> items = ehListinit.get(0).getItems();

		for (Fragebogen f : fragebogenList) {
			ObservableList<EvaluationHelper> ehList = EvaluationHelper.createEvaluationHelperList(f.getBewertungen(), null);

			for (Item item : items) {
				double ret = 0;
				int i = 0;
				if (ehList.get(0).getItems().contains(item)) {
					for (EvaluationHelper eh : ehList) {
					    
					    String s = eh.getItemWertung().get(eh.getItems().indexOf(item));
					    if(s.isEmpty()){
					        ret += 0;
					    }
					    else{
					        ret += Double.parseDouble(eh.getItemWertung().get(eh.getItems().indexOf(item)));   
					        i++;
					    }
						

						
					}
				}
				if (i > 0) {
					ret /= i;
				}

				defaultcategorydataset.addValue(ret, f.getPerspektive().getName(), item.getName());
			}
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

	static public DefaultCategoryDataset getAverageDataSetForBereichCompare(List<Fragebogen> fragebogenList) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double ret = 0;
		ArrayList<Bereich> bereiche = EvaluationHelper.getBereicheFromEvaluationHelper(fragebogenList.get(0).getBewertungen());

		for (Fragebogen f : fragebogenList) {

			double[] values = new double[bereiche.size()];
			int valCount = 0;
			for (Bereich bereich : bereiche) {
				valCount = 0;
				for (Bewertung bewertung : f.getBewertungen()) {
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
				} else {
					values[bereiche.indexOf(bereich)] = 0;
				}
			}

			for (Bereich bereich : bereiche) {
				defaultcategorydataset.addValue(values[bereiche.indexOf(bereich)], f.getPerspektive().getName(), bereich.getName());

			}
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

	static public DefaultCategoryDataset createDatasetForCriterionEvaluationCompareBarChart(List<Fragebogen> fragebogenList) {

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		ArrayList<Bereich> bereiche = EvaluationHelper.getBereicheFromEvaluationHelper(fragebogenList.get(0).getBewertungen());

		for (Fragebogen f : fragebogenList) {

			double[] values = new double[bereiche.size()];
			int valCount = 0;
			for (Bereich bereich : bereiche) {
				valCount = 0;
				for (Bewertung bewertung : f.getBewertungen()) {
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
				} else {
					values[bereiche.indexOf(bereich)] = 0;
				}
			}
			ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();

			for (Bereich bereich : bereiche) {
				if (!hfList.contains(bereich.getHandlungsfeld())) {
					hfList.add(bereich.getHandlungsfeld());
				}

			}

			for (Handlungsfeld hf : hfList) {
				double ret = 0;
				int i = 0;
				for (Bereich bereich : bereiche) {
					if (bereich.getHandlungsfeld().equals(hf)) {
						ret += values[bereiche.indexOf(bereich)];
						i++;
					}

				}
				if (i > 0) {
					ret /= i;
				} else {
					ret = 0;
				}
				defaultcategorydataset.addValue(ret, f.getPerspektive().getName(), hf.getName());

			}
		}

		return defaultcategorydataset;
	}

}
