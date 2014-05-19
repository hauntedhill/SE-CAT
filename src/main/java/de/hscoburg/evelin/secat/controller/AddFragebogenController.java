package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;

@Controller
public class AddFragebogenController extends BaseController {

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addFragebogen.lable.title";
	}
}
