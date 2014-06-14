package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;

public interface TreeTableCellAction<T, E> {
	public ObservableValue<E> call(javafx.scene.control.TreeTableColumn.CellDataFeatures<T, E> p);
}
