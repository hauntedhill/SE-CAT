package de.hscoburg.evelin.secat.util.charts;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.RectangleEdge;

import de.hscoburg.evelin.secat.dao.entity.Fragebogen;

/**
 * Auslagerung der Charterzeugung
 * 
 * @author moro1000
 * 
 */
public class ChartCreationHelper {

	static public JFreeChart createKiviatChart(DefaultCategoryDataset defaultcategorydataset, Fragebogen fragebogen) {

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();
		spiderwebplot.setMaxValue(fragebogen.getSkala().getSchritte());

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;
	}

	static public JFreeChart createKiviatChartLinesOnly(DefaultCategoryDataset defaultcategorydataset, Fragebogen fragebogen) {

		RadarChart rc = new RadarChart(defaultcategorydataset, fragebogen.getSkala().getSchritte());
		SpiderWebPlot spiderwebplot = rc.getPlot();
		spiderwebplot.setMaxValue(fragebogen.getSkala().getSchritte());
		spiderwebplot.setWebFilled(false);

		JFreeChart jfreechart = new JFreeChart(fragebogen.getName(), TextTitle.DEFAULT_FONT, spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		return jfreechart;
	}

	static public JFreeChart createBarChart(DefaultCategoryDataset defaultcategorydataset, String name, String titel, Fragebogen fragebogen) {
		JFreeChart chart = ChartFactory.createBarChart(name, titel, "", defaultcategorydataset, PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(1, fragebogen.getSkala().getSchritte());

		CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
		categoryAxis.setMaximumCategoryLabelLines(4);

		BarRenderer br = (BarRenderer) plot.getRenderer();
		br.setDrawBarOutline(true);
		br.setMaximumBarWidth(.05);
		return chart;

	}

	static public JFreeChart createBoxPlot(DefaultBoxAndWhiskerCategoryDataset dataset, String fragebogenName) {

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

		final JFreeChart chart = new JFreeChart(fragebogenName, new Font("SansSerif", Font.BOLD, 14), plot, true);

		return chart;
	}
}
