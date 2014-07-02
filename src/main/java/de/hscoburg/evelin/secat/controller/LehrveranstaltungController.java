package de.hscoburg.evelin.secat.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.controller.base.BaseController;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.util.javafx.ActionHelper;
import de.hscoburg.evelin.secat.util.javafx.ColumnHelper;
import de.hscoburg.evelin.secat.util.javafx.ConverterHelper;
import de.hscoburg.evelin.secat.util.javafx.DialogHelper;
import de.hscoburg.evelin.secat.util.javafx.EditableComboBoxTableCell;
import de.hscoburg.evelin.secat.util.javafx.EditableStringTableCell;
import de.hscoburg.evelin.secat.util.javafx.SeCatEventHandle;
import de.hscoburg.evelin.secat.util.javafx.TableCellAction;
import de.hscoburg.evelin.secat.util.javafx.ValueListTypeHandler;
import de.hscoburg.evelin.secat.util.javafx.ValueTableTypeHandler;

/**
 * Controller zur Anzeige von Lehrveranstaltungen
 * 
 * @author zuch1000
 * 
 */
@Controller
public class LehrveranstaltungController extends BaseController {

	@FXML
	private TitledPane addPanel;

	@FXML
	private TitledPane tablePanel;

	@FXML
	private TableView<Lehrveranstaltung> tableLehrveranstaltung;

	@FXML
	private Button buttonAdd;

	@FXML
	private ComboBox<Integer> boxJahr;
	@FXML
	private ComboBox<SemesterType> boxSemester;
	@FXML
	private ComboBox<Fach> boxFach;

	@FXML
	private TextField textDozent;

	@Autowired
	private FachModel fachModel;

	@Autowired
	private LehrveranstaltungModel lehrveranstaltungsModel;

	/**
	 * Initialisiert die View
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initializeController(URL location, ResourceBundle resources) {

		ObservableList<Integer> years = FXCollections.observableArrayList();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = year + 5; i > year - 5; i--) {
			years.add(i);
		}
		boxJahr.setItems(years);

		boxJahr.getSelectionModel().select(year);

		boxJahr.setValue(year);

		boxSemester.setItems(FXCollections.observableList(Arrays.asList(SemesterType.values())));

		boxSemester.getSelectionModel().select(boxSemester.getItems().get(0));

		boxSemester.setValue(boxSemester.getItems().get(0));

		boxFach.setConverter(ConverterHelper.getConverterForFach());

		boxFach.setItems(FXCollections.observableList(fachModel.getFaecher()));
		if (boxFach.getItems().size() > 0) {
			boxFach.getSelectionModel().select(boxFach.getItems().get(0));
			boxFach.setValue(boxFach.getItems().get(0));
		}

		boxFach.setVisibleRowCount(10);

		loadList();
		boxJahr.requestFocus();
		tableLehrveranstaltung.setEditable(true);
		ColumnHelper.setTableColumnCellFactory(tableLehrveranstaltung.getColumns().get(0), new TableCellAction<Lehrveranstaltung, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Lehrveranstaltung, String> p) {

				return new ReadOnlyObjectWrapper<String>(p.getValue().getFach().getName());
			}
		});

		((TableColumn<Lehrveranstaltung, String>) tableLehrveranstaltung.getColumns().get(0))
				.setCellFactory(new Callback<TableColumn<Lehrveranstaltung, String>, TableCell<Lehrveranstaltung, String>>() {

					@Override
					public TableCell<Lehrveranstaltung, String> call(TableColumn<Lehrveranstaltung, String> param) {
						// TODO Auto-generated method stub
						return new EditableComboBoxTableCell<Lehrveranstaltung, String, Fach>(new ValueTableTypeHandler<Lehrveranstaltung, Fach>() {

							@Override
							public Lehrveranstaltung merge(Lehrveranstaltung value, Fach newValue) {
								value.setFach(newValue);
								return value;
							}

							@Override
							public String getText(Fach value) {

								return ConverterHelper.getConverterForFach().toString(value);
							}

							@Override
							public boolean update(Lehrveranstaltung value) {
								try {
									lehrveranstaltungsModel.updateLehrveranstaltung(value);

									return true;
								} catch (IllegalArgumentException iae) {
									return false;
								}
							}

							@Override
							public boolean isLocked(Lehrveranstaltung value) {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public Object getValue(Lehrveranstaltung obj) {

								return obj != null ? obj.getFach() : null;
							}

						}, boxFach.getItems(), ConverterHelper.getConverterForFach());
					}

				});

		ColumnHelper.setTableColumnCellFactory(tableLehrveranstaltung.getColumns().get(1), new TableCellAction<Lehrveranstaltung, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Lehrveranstaltung, String> p) {

				return new ReadOnlyObjectWrapper<String>(p.getValue().getSemester().name());
			}
		});

		((TableColumn<Lehrveranstaltung, String>) tableLehrveranstaltung.getColumns().get(1))
				.setCellFactory(new Callback<TableColumn<Lehrveranstaltung, String>, TableCell<Lehrveranstaltung, String>>() {

					@Override
					public TableCell<Lehrveranstaltung, String> call(TableColumn<Lehrveranstaltung, String> param) {
						// TODO Auto-generated method stub
						return new EditableComboBoxTableCell<Lehrveranstaltung, String, SemesterType>(
								new ValueTableTypeHandler<Lehrveranstaltung, SemesterType>() {

									@Override
									public Lehrveranstaltung merge(Lehrveranstaltung value, SemesterType newValue) {
										value.setSemester(newValue);
										return value;
									}

									@Override
									public String getText(SemesterType value) {

										return value.name();
									}

									@Override
									public boolean update(Lehrveranstaltung value) {
										try {
											lehrveranstaltungsModel.updateLehrveranstaltung(value);

											return true;
										} catch (IllegalArgumentException iae) {
											return false;
										}
									}

									@Override
									public boolean isLocked(Lehrveranstaltung value) {
										return lehrveranstaltungsModel.isLocked(value);
									}

									@Override
									public Object getValue(Lehrveranstaltung obj) {

										return obj.getSemester();
									}

								}, boxSemester.getItems());
					}

				});

		ColumnHelper.setTableColumnCellFactory(tableLehrveranstaltung.getColumns().get(2), new TableCellAction<Lehrveranstaltung, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Lehrveranstaltung, String> p) {

				return new ReadOnlyObjectWrapper<String>(String.valueOf(p.getValue().getJahr().getYear()));
			}
		});

		((TableColumn<Lehrveranstaltung, String>) tableLehrveranstaltung.getColumns().get(2))
				.setCellFactory(new Callback<TableColumn<Lehrveranstaltung, String>, TableCell<Lehrveranstaltung, String>>() {

					@Override
					public TableCell<Lehrveranstaltung, String> call(TableColumn<Lehrveranstaltung, String> param) {
						// TODO Auto-generated method stub
						return new EditableComboBoxTableCell<Lehrveranstaltung, String, Integer>(new ValueTableTypeHandler<Lehrveranstaltung, Integer>() {

							@Override
							public Lehrveranstaltung merge(Lehrveranstaltung value, Integer newValue) {

								Date d = new Date();
								d.setYear(newValue);

								value.setJahr(d);
								return value;
							}

							@Override
							public String getText(Integer value) {

								return String.valueOf(value);
							}

							@Override
							public boolean update(Lehrveranstaltung value) {
								try {
									lehrveranstaltungsModel.updateLehrveranstaltung(value);

									return true;
								} catch (IllegalArgumentException iae) {
									return false;
								}
							}

							@Override
							public boolean isLocked(Lehrveranstaltung value) {
								return lehrveranstaltungsModel.isLocked(value);
							}

							@Override
							public Object getValue(Lehrveranstaltung obj) {
								// TODO Auto-generated method stub
								return obj.getJahr().getYear();
							}

						}, boxJahr.getItems());
					}

				});

		ColumnHelper.setTableColumnCellFactory(tableLehrveranstaltung.getColumns().get(3), new TableCellAction<Lehrveranstaltung, String>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Lehrveranstaltung, String> p) {

				return new ReadOnlyObjectWrapper<String>(p.getValue().getDozent());
			}
		});

		((TableColumn<Lehrveranstaltung, String>) tableLehrveranstaltung.getColumns().get(3))
				.setCellFactory(new Callback<TableColumn<Lehrveranstaltung, String>, TableCell<Lehrveranstaltung, String>>() {

					@Override
					public TableCell<Lehrveranstaltung, String> call(TableColumn<Lehrveranstaltung, String> param) {
						// TODO Auto-generated method stub^

						return new EditableStringTableCell<Lehrveranstaltung, String>(new ValueListTypeHandler<Lehrveranstaltung>() {

							@Override
							public Lehrveranstaltung merge(Lehrveranstaltung value, String newValue) {
								value.setDozent(newValue);
								return value;
							}

							@Override
							public String getText(Lehrveranstaltung value) {

								return value.getDozent();
							}

							@Override
							public boolean update(Lehrveranstaltung value) {
								try {
									lehrveranstaltungsModel.updateLehrveranstaltung(value);

									return true;
								} catch (IllegalArgumentException iae) {
									return false;
								}
							}

							@Override
							public boolean isLocked(Lehrveranstaltung value) {
								// TODO Auto-generated method stub
								return lehrveranstaltungsModel.isLocked(value);
							}

						});
					}

				});

		ActionHelper.setActionToButton(new SeCatEventHandle<ActionEvent>() {

			@Override
			public void handleAction(ActionEvent event) throws Exception {

				try {

					lehrveranstaltungsModel.saveLehrveranstaltung(textDozent.getText(), boxFach.getValue(), boxJahr.getValue(), boxSemester.getValue());
				} catch (IllegalArgumentException iae) {
					javafx.application.Platform.runLater(new Runnable() {

						@Override
						public void run() {
							DialogHelper.showInputErrorDialog();

						}
					});

				}

			}

			@Override
			public void updateUI() {
				loadList();
			}
		}, buttonAdd, true);

	}

	/**
	 * Laed die Lehrveranstaltungen und setzt diese in der View
	 */
	private void loadList() {
		tableLehrveranstaltung.setItems(FXCollections.observableList(lehrveranstaltungsModel.getLehrveranstaltung()));
	}

	@Override
	public String getKeyForSceneName() {

		return "scene.fach.title.lable";
	}

}
