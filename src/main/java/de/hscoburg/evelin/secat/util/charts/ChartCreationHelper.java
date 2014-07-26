package de.hscoburg.evelin.secat.util.charts;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
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

}
