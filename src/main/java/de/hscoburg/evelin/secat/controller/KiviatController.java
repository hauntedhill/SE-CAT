package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;

@Controller
public class KiviatController extends BaseController {

	@FXML
	private GridPane contentPane;
	@Autowired
	private BewertungAnzeigenController bewertungAnzeigenController;

	public void initializeController(URL location, ResourceBundle resources) {

		final SwingNode chartSwingNode = new SwingNode();
		chartSwingNode.setContent(new ChartPanel(bewertungAnzeigenController.createDatasetForStudentBereichContext(bewertungAnzeigenController
				.getSelectedItem())));

		contentPane.add(chartSwingNode, 1, 1);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.chart.kiviat.lable.title";
	}
}