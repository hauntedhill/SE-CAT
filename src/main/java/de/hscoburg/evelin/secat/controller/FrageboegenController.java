package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class FrageboegenController extends BaseController {

	@FXML
	private TextField searchName;
	@FXML
	private ComboBox<Perspektive> searchPerspektive;

	@FXML
	private ComboBox<Eigenschaft> searchEigenschaft;

	@FXML
	private ComboBox<Lehrveranstaltung> searchLehrveransteltung;

	@FXML
	private ComboBox<Skala> searchSkala;

	@FXML
	private Button search;

	@FXML
	private Button reset;
	@FXML
	private DatePicker searchFromDate;
	@FXML
	private DatePicker searchToDate;

	@Autowired
	private EigenschaftenModel eigenschaftenModel;

	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private LehrveranstaltungModel lehrveranstaltungModel;
	@Autowired
	private SkalenModel skalenModel;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {
		searchName.requestFocus();
		searchEigenschaft.setConverter(ConverterHelper.getConverterForEigenschaft());
		searchPerspektive.setConverter(ConverterHelper.getConverterForPerspektive());
		searchLehrveransteltung.setConverter(ConverterHelper.getConverterForLehrveranstaltung());
		searchSkala.setConverter(ConverterHelper.getConverterForSkala());

		searchEigenschaft.setItems(FXCollections.observableArrayList(eigenschaftenModel.getEigenschaften()));
		searchPerspektive.setItems(FXCollections.observableArrayList(perspektivenModel.getPerspektiven()));
		searchLehrveransteltung.setItems(FXCollections.observableArrayList(lehrveranstaltungModel.getLehrveranstaltung()));
		searchSkala.setItems(FXCollections.observableArrayList(skalenModel.getSkalen()));

		search.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		reset.setGraphic(new ImageView(new Image("/image/icons/reload.png", 16, 16, true, true)));

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void updateUI() {
				resetSearchBox();
			}
		}, reset);

	}

	private void resetSearchBox() {

		searchName.setText("");
		searchEigenschaft.getSelectionModel().clearSelection();
		searchPerspektive.getSelectionModel().clearSelection();
		searchLehrveransteltung.getSelectionModel().clearSelection();
		searchSkala.getSelectionModel().clearSelection();

		searchFromDate.setValue(null);
		searchToDate.setValue(null);
	}

	@Override
	public String getKeyForSceneName() {
		// TODO Auto-generated method stub
		return "scene.frageboegen.lable.title";
	}

}
