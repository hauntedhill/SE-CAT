package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;

/**
 * Controller um einen Fragebogen zu editieren
 * 
 * @author moro1000
 * 
 */
@Controller
public class EditFragebogenController extends BaseController {

	@Autowired
	private FrageboegenController frageboegenController;
	@Autowired
	private AddFragebogenController addFragebogenController;

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

		Fragebogen selected = frageboegenController.getSelectedFragebogen();
		addFragebogenController.setFragebogenToEdit(selected);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.addFragebogen.lable.title";
	}

}
