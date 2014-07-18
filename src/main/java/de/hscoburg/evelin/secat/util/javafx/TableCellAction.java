package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 * Interface fuer Tablecell actions
 * 
 * @author zuch1000
 * 
 */
public interface TableCellAction<T, E> {
	/**
	 * Auszufuehrende Action
	 * 
	 * @param p
	 *            {@link CellDataFeatures}
	 * @return {@link ObservableValue}
	 */
	public ObservableValue<E> call(CellDataFeatures<T, E> p);
}
