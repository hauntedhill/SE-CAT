package de.hscoburg.evelin.secat.util.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import de.hscoburg.evelin.secat.controller.helper.EvaluationHelper;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;

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

}
