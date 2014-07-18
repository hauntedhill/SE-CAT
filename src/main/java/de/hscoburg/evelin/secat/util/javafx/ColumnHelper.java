package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 * Klasse fuer allgemein benutzbarer CellFactoriers fuer (Tree)Tables
 * 
 * @author zuch1000
 * 
 */
public class ColumnHelper {

	/**
	 * Setzt fuer die column die Action
	 * 
	 * @param column
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	public static <E, T> void setTableColumnCellFactory(TableColumn<T, ?> column, final TableCellAction<T, E> action) {
		((TableColumn<T, E>) column).setCellValueFactory(new Callback<CellDataFeatures<T, E>, ObservableValue<E>>() {

			public ObservableValue<E> call(CellDataFeatures<T, E> p) {
				return action.call(p);

			}

		});
	}

	/**
	 * Setzt fuer die column die Action
	 * 
	 * @param column
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	public static <E, T> void setTreeTableColumnCellFactory(TreeTableColumn<T, ?> column, final TreeTableCellAction<T, E> action) {
		((TreeTableColumn<T, E>) column).setCellValueFactory(new Callback<javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E>, ObservableValue<E>>() {

			public ObservableValue<E> call(javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E> p) {
				return action.call(p);

			}
		});
	}

}
