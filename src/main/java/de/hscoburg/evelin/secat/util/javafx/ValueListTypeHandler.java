package de.hscoburg.evelin.secat.util.javafx;

/**
 * Interface um die Values der editierbaren ListViews zu verwalten
 * 
 * @author zuch1000
 * 
 */
public interface ValueListTypeHandler<T> {

	/**
	 * Speichert den neuen Wert im Object
	 * 
	 * @param value
	 *            Das zu aktualisierende Objekt
	 * @param newValue
	 *            Der neue Wert
	 * @return Das aktualiserte Objekt
	 */
	public abstract T merge(T value, String newValue);

	/**
	 * Gibt den Anzeigetext des Objektes zurueck.
	 * 
	 * @param value
	 *            Objekt
	 * @return Der text
	 */
	public abstract String getText(T value);

	/**
	 * Aktualisiert das Objekt in der DB
	 * 
	 * @param value
	 *            das Objekt
	 * @return true bei erfolg
	 */
	public abstract boolean update(T value);

	/**
	 * Gibt an ob das Objekt gesperrt ist.
	 * 
	 * @param value
	 *            Wert
	 * @return true wenn ja
	 */
	public abstract boolean isLocked(T value);
}