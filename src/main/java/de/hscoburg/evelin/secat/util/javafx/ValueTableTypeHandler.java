package de.hscoburg.evelin.secat.util.javafx;

public interface ValueTableTypeHandler<T, E> {

	public abstract T merge(T value, E newValue);

	// public abstract String getText(T value);

	public abstract String getText(E value);

	public abstract boolean update(T value);

	public abstract boolean isLocked(T value);

	public abstract Object getValue(T obj);

}