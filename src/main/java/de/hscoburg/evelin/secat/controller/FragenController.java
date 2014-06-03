package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.FragenModel;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

@Controller
public class FragenController extends BaseController {

	@FXML
	private TableView<Frage> tableFragen;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextField textNameFragen;

	@Autowired
	private SkalenModel skalenModel;

	@FXML
	private ComboBox<Skala> boxSkala;

	@Autowired
	private FragenModel fragenModel;

	@FXML
	private TextArea textFrage;

	@Override
	public void initializeController(URL location, ResourceBundle resources) {
		boxSkala.setItems(FXCollections.observableList(skalenModel.getSkalen()));

		boxSkala.getSelectionModel().select(boxSkala.getItems().get(0));

		boxSkala.setValue(boxSkala.getItems().get(0));

		boxSkala.setConverter(ConverterHelper.getConverterForSkala());

		((TableColumn<Frage, String>) tableFragen.getColumns().get(0))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getName());

					}
				});

		((TableColumn<Frage, String>) tableFragen.getColumns().get(1))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getSkala().getName());

					}
				});

		((TableColumn<Frage, String>) tableFragen.getColumns().get(2))
				.setCellValueFactory(new Callback<CellDataFeatures<Frage, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Frage, String> p) {
						return new ReadOnlyObjectWrapper<String>(p.getValue().getText());

					}
				});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					fragenModel.saveFrage(textNameFragen.getText(), textFrage.getText(), boxSkala.getValue());
				} catch (IllegalArgumentException iae) {
					javafx.application.Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.input.error.title"))
									.masthead(SeCatResourceBundle.getInstance().getString("scene.input.error.txt")).showError();

						}
					});

				}

			}

			@Override
			public void updateUI() {
				loadTable();
			}
		}, buttonAdd);

		buttonAdd.setGraphic(new ImageView(new Image("/image/icons/edit_add.png", 16, 16, true, true)));

		loadTable();

	}

	private void loadTable() {
		tableFragen.setItems(FXCollections.observableList(fragenModel.getFragen()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.question.lable.title";
	}

}
