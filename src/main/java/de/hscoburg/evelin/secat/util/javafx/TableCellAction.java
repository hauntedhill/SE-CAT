package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;

public interface TableCellAction<T, E> {
	public ObservableValue<E> call(CellDataFeatures<T, E> p);
}
