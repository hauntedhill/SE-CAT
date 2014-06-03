package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.model.HandlungsfeldModel;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;

@Controller
public class FilterAllItemsController extends BaseController {

	@FXML
	private Button filter;

	@FXML
	private Button cancel;
	@FXML
	private TextArea notiz;
	@FXML
	private CheckBox isInaktiv;
	@FXML
	private ListView<Eigenschaft> eigenschaftList;
	@FXML
	private ListView<Perspektive> perspektiveList;
	@Autowired
	private HandlungsfeldController handlungsfeldController;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;
	@Autowired
	private PerspektivenModel perspektivenModel;
	@Autowired
	private SkalenModel skalenModel;
	@Autowired
	private EigenschaftenModel eigenschaftModel;
	@Autowired
	private TreeTableController treeTableController;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		filter.setGraphic(new ImageView(new Image("/image/icons/viewmag.png", 16, 16, true, true)));
		cancel.setGraphic(new ImageView(new Image("/image/icons/button_cancel.png", 16, 16, true, true)));

		perspektiveList.setCellFactory(new Callback<ListView<Perspektive>, ListCell<Perspektive>>() {

			@Override
			public ListCell<Perspektive> call(ListView<Perspektive> s) {

				ListCell<Perspektive> cell = new ListCell<Perspektive>() {

					@Override
					protected void updateItem(Perspektive t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

		ObservableList<Perspektive> perspektivenOl = FXCollections.observableArrayList();
		List<Perspektive> persList = perspektivenModel.getPerspektiven();

		for (Perspektive perspektive : persList) {

			perspektivenOl.add(perspektive);
		}

		perspektiveList.setItems(perspektivenOl);

		eigenschaftList.setCellFactory(new Callback<ListView<Eigenschaft>, ListCell<Eigenschaft>>() {

			@Override
			public ListCell<Eigenschaft> call(ListView<Eigenschaft> s) {

				ListCell<Eigenschaft> cell = new ListCell<Eigenschaft>() {

					@Override
					protected void updateItem(Eigenschaft t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getName());
						}
					}

				};

				return cell;
			}
		});

		ObservableList<Eigenschaft> eigenschaftOl = FXCollections.observableArrayList();
		List<Eigenschaft> eigenList = eigenschaftModel.getEigenschaften();

		for (Eigenschaft eigenschaft : eigenList) {

			eigenschaftOl.add(eigenschaft);
		}

		eigenschaftList.setItems(eigenschaftOl);

		filter.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
			}

			@Override
			public void updateUI() {
				List<Handlungsfeld> result = handlungsfeldModel.getHandlungsfelderBy(true, !isInaktiv.isSelected(), perspektiveList.getSelectionModel()
						.getSelectedItem(), eigenschaftList.getSelectionModel().getSelectedItem(), null, notiz.getText(), null);

				treeTableController.buildFilteredTreeTable(result, true, !isInaktiv.isSelected(), perspektiveList.getSelectionModel().getSelectedItem(),
						eigenschaftList.getSelectionModel().getSelectedItem(), null, notiz.getText(), null);

				Stage stage = (Stage) filter.getScene().getWindow();
				stage.close();
			}

		});

		cancel.setOnAction(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {
			}

			@Override
			public void updateUI() {
				Stage stage = (Stage) filter.getScene().getWindow();
				stage.close();
			}

		});

	}

	@Override
	public String getKeyForSceneName() {

		return "scene.filterallitems.lable.title";
	}

}
