package de.hscoburg.evelin.secat.util.javafx;


public interface ValueTypeHandler<T> {

	public abstract T merge(T value, String newValue);

	public abstract String getText(T value);

	public abstract boolean update(T value);

	public abstract boolean isLocked(T value);
}