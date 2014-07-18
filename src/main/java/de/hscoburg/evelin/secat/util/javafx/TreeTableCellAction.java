package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;

/**
 * Interface fuer Treetablecell actions
 * 
 * @author zuch1000
 * 
 */
public interface TreeTableCellAction<T, E> {
	/**
	 * Auszufuehrende Action
	 * 
	 * @param p
	 *            {@link CellDataFeatures}
	 * @return {@link ObservableValue}
	 */
	public ObservableValue<E> call(javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E> p);
}
