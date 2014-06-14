package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class ColumnHelper {

	@SuppressWarnings("unchecked")
	public static <E, T> void setTableColumnCellFactory(TableColumn<T, ?> column, final TableCellAction<T, E> action) {
		((TableColumn<T, E>) column).setCellValueFactory(new Callback<CellDataFeatures<T, E>, ObservableValue<E>>() {

			public ObservableValue<E> call(CellDataFeatures<T, E> p) {
				return action.call(p);

			}
		});
	}

	@SuppressWarnings("unchecked")
	public static <E, T> void setTreeTableColumnCellFactory(TreeTableColumn<T, ?> column, final TreeTableCellAction<T, E> action) {
		((TreeTableColumn<T, E>) column).setCellValueFactory(new Callback<javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E>, ObservableValue<E>>() {

			public ObservableValue<E> call(javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E> p) {
				return action.call(p);

			}
		});
	}

}
