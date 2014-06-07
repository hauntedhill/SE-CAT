package de.hscoburg.evelin.secat.util.javafx;

import java.util.ArrayList;
import java.util.List;

import de.hscoburg.evelin.secat.dao.entity.Item;

public class EvaluationHelper {

	private List<Item> items;
	private ArrayList<String> itemWertung;
	private String welle;
	private String rawId;
	private String source;
	private String zeit;

	public EvaluationHelper() {
		itemWertung = new ArrayList<String>();
	}

	public void addItem(Item i) {

		items.add(i);
	}

	public void addItemWertung(String w) {

		itemWertung.add(w);
	}

	public String getWelle() {
		return welle;
	}

	public void setWelle(String welle) {
		this.welle = welle;
	}

	public String getRawId() {
		return rawId;
	}

	public void setRawId(String rawId) {
		this.rawId = rawId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getZeit() {
		return zeit;
	}

	public void setZeit(String zeit) {
		this.zeit = zeit;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<String> getItemWertung() {
		return itemWertung;
	}

}
