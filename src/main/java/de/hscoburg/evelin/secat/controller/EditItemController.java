package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Item;

/**
 * Controller zur �nderung eines Items
 * 
 * @author moro1000
 * 
 */
@Controller
public class EditItemController extends BaseController {

	@Autowired
	private TreeTableController treeTableController;
	@Autowired
	private AddItemController addItemController;

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

		Item edit = treeTableController.getSelectedTreeItem().getValue().getItem();
		addItemController.setItemToEdit(edit);

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.editItem.lable.title";
	}

	@Override
	public void setTitle() {

		setTitle("( " + treeTableController.getSelectedTreeItem().getValue().getItem().getBereich().getHandlungsfeld().getName() + " - "
				+ treeTableController.getSelectedTreeItem().getValue().getItem().getBereich().getName() + " )");
	}
}
