package de.hscoburg.evelin.secat.controller.helper;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;

public class EvaluationHelper {

	private ArrayList<Item> items;
	private ArrayList<String> itemWertung;
	private ArrayList<String> frageWertung;
	private String welle;
	private String rawId;
	private String source;
	private String zeit;
	private boolean outlier;

	public EvaluationHelper() {
		this.items = new ArrayList<Item>();
		this.itemWertung = new ArrayList<String>();
		this.frageWertung = new ArrayList<String>();
		this.setOutlier(false);

	}

	public void addItem(Item i) {

		items.add(i);
	}

	public void addItemWertung(String w) {

		itemWertung.add(w);
	}

	public void addFrageWertung(String w) {

		frageWertung.add(w);
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

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public List<String> getItemWertung() {
		return itemWertung;
	}

	public ArrayList<String> getFrageWertung() {
		return frageWertung;
	}

	public void setFrageWertung(ArrayList<String> frageWertung) {
		this.frageWertung = frageWertung;
	}

	public boolean isOutlier() {
		return outlier;
	}

	public void setOutlier(boolean outlier) {
		this.outlier = outlier;
	}

	static public ObservableList<EvaluationHelper> createEvaluationHelperList(List<Bewertung> bewertungen, List<Frage_Fragebogen> fragen) {

		ArrayList<String> erste = new ArrayList<String>();
		ObservableList<EvaluationHelper> ehList = FXCollections.observableArrayList();
		if (!bewertungen.isEmpty()) {

			for (Bewertung bewertung : bewertungen) {
				if (!erste.contains(bewertung.getZeilenid())) {
					erste.add(bewertung.getZeilenid());
					EvaluationHelper eh = new EvaluationHelper();
					if (bewertung.getAusreiser() != null && bewertung.getAusreiser() == true) {
						eh.setOutlier(true);
					}
					if (bewertung.getAusreiser() != null && bewertung.getAusreiser() == false) {
						eh.setOutlier(false);
					}
					eh.setWelle(bewertung.getWelle());
					eh.setRawId(bewertung.getZeilenid());
					eh.setSource(bewertung.getQuelle());
					eh.setZeit(bewertung.getZeit());
					List<Bewertung> temp = bewertungen;
					for (Bewertung b : temp) {

						if (b.getZeilenid().equals(eh.getRawId())) {
							if (b.getItem() != null) {

								eh.addItemWertung(b.getWert());
								eh.addItem(b.getItem());
							}

						}

					}

					ehList.add(eh);
				}
			}
		}
		if (fragen != null) {
			ArrayList<Frage> fragenList = new ArrayList<Frage>();

			for (Frage_Fragebogen frage : fragen) {
				fragenList.add(frage.getFrage());

			}

			for (EvaluationHelper eh : ehList) {
				ArrayList<Integer> id = new ArrayList<Integer>();
				ArrayList<String> frageWertungen = new ArrayList<String>();
				frageWertungen.clear();
				for (Frage f : fragenList) {
					frageWertungen.add("");
				}
				for (Bewertung bewertung : bewertungen) {
					if (eh.getRawId().equals(bewertung.getZeilenid()) && bewertung.getFrage() != null) {
						for (Frage frage : fragenList) {
							if (bewertung.getFrage().getId() == frage.getId()) {
								if (!id.contains(frage.getId())) {
									id.add(frage.getId());
									frageWertungen.set(fragenList.indexOf(frage), bewertung.getWert());

								} else {
									String s = frageWertungen.get(id.indexOf(frage.getId())) + " " + bewertung.getWert();
									frageWertungen.add(fragenList.indexOf(frage), s);
								}
							}
						}

					}
				}
				eh.setFrageWertung(frageWertungen);
			}

		}

		return ehList;
	}

	static public ArrayList<Bereich> getBereicheFromEvaluationHelper(List<Bewertung> bewertungen) {

		ArrayList<Bereich> bereiche = new ArrayList<Bereich>();
		for (Bewertung bewertung : bewertungen) {
			if (bewertung.getItem() != null) {
				if (!bereiche.contains(bewertung.getItem().getBereich()))
					bereiche.add(bewertung.getItem().getBereich());
			}
		}
		return bereiche;

	}

}
