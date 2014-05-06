package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import org.springframework.stereotype.Controller;

@Controller
public class LayoutController implements Initializable {

	@FXML
	private BorderPane layout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("####" + layout);

	}

}
