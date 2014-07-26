package de.hscoburg.evelin.secat.util.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import de.hscoburg.evelin.secat.controller.helper.EvaluationHelper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;

/**
 * Hilfsklasse zur Berechnung der kumulierten werte
 * 
 * @author moro1000
 * 
 */
public class CalculationHelper {

	static public double getAverageDataForSubCriterion(ArrayList<Bereich> bereiche, double[] avValueBereich) {
		double ret = 0;
		for (int i = 0; i < bereiche.size(); i++) {
			ret += avValueBereich[i];
		}
		return ret / bereiche.size();
	}

	static public double getMedianForAllSubCriterions(double[] avValueBereich) {

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

	static public double getStandarddeviationForAllSubCriterions(ArrayList<Bereich> bereiche, double[] avValueBereich) {

		double av = getAverageDataForSubCriterion(bereiche, avValueBereich);
		double temp = 0;
		for (double d : avValueBereich) {
			temp += (d - av) * (d - av);
		}
		temp /= (avValueBereich.length) - 1;
		return Math.sqrt(temp);

	}

	static public ArrayList<Double> getAverageDataPerStudent(ObservableList<EvaluationHelper> ehList) {
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

	static public double getAverageForAllStudents(ObservableList<EvaluationHelper> ehList) {
		double ret = 0;
		for (double d : getAverageDataPerStudent(ehList)) {
			ret += d;
		}
		return ret / ehList.size();
	}

	static public double getMedianForAllStudents(ObservableList<EvaluationHelper> ehList) {
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

	static public double getStandarddeviationForAllStudents(ObservableList<EvaluationHelper> ehList) {

		double av = getAverageForAllStudents(ehList);
		double temp = 0;
		for (double d : getAverageDataPerStudent(ehList)) {
			temp += (d - av) * (d - av);
		}
		temp /= (getAverageDataPerStudent(ehList).size()) - 1;
		return Math.sqrt(temp);

	}

	static public double[] getAvValueforBereiche(List<Bewertung> bewertungen, List<Bereich> bereiche) {
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

	static public double[] getAverageDataForCriterion(double[] avValueBereich, ArrayList<Bereich> bereiche) {

		ArrayList<Handlungsfeld> hfList = new ArrayList<Handlungsfeld>();
		for (Bereich bereich : bereiche) {
			if (hfList.isEmpty() || !hfList.contains(bereich.getHandlungsfeld())) {
				hfList.add(bereich.getHandlungsfeld());
			}
		}
		double[] values = new double[hfList.size()];

		int valCount = 0;
		for (Handlungsfeld hf : hfList) {
			valCount = 0;
			for (Bereich bereich : bereiche) {
				if (bereich.getHandlungsfeld().equals(hf)) {
					values[hfList.indexOf(hf)] += avValueBereich[bereiche.indexOf(bereich)];
					valCount++;
				}
			}
			values[hfList.indexOf(hf)] /= valCount;

		}
		return values;
	}

	static public double getAverageDataForAllCriterions(double[] avValueBereich, ArrayList<Bereich> bereiche) {
		double ret = 0;
		double[] values = getAverageDataForCriterion(avValueBereich, bereiche);
		for (double d : values) {
			ret += d;
		}
		return ret / values.length;
	}

	static public double getStandarddeviationForAllCriterions(double[] avValueBereich, ArrayList<Bereich> bereiche) {

		double av = getAverageDataForAllCriterions(avValueBereich, bereiche);
		double temp = 0;
		for (double d : getAverageDataForCriterion(avValueBereich, bereiche)) {
			temp += (d - av) * (d - av);
		}
		temp /= (avValueBereich.length) - 1;
		return Math.sqrt(temp);

	}

	static public double getMedianForAllCriterions(double[] avValueBereich, ArrayList<Bereich> bereiche) {
		double[] values = getAverageDataForCriterion(avValueBereich, bereiche);

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

	/*
	 * static public ArrayList<Double> getAverageDataPerCriterion(ObservableList<EvaluationHelper> allEvaluationHelper) { ArrayList<Double>
	 * ret = new ArrayList<Double>(); for (EvaluationHelper eh : allEvaluationHelper) { double value = 0; for (String wert :
	 * eh.getItemWertung()) { value += Double.parseDouble(wert); } value /= eh.getItemWertung().size();
	 * 
	 * ret.add(value); } return ret; }
	 */

}
