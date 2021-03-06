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

/**
 * Hilfsklasse zur Anzeige der Bewertungen
 * 
 * @author moro1000
 * 
 */
public class EvaluationHelper {

	private ArrayList<Item> items;
	private ArrayList<String> itemWertung;
	private ArrayList<String> frageWertung;
	private double[] avValueBereich;

	public double[] getAvValueBereich() {
		return avValueBereich;
	}

	public void setAvValueBereich(double[] avValueBereich) {
		this.avValueBereich = avValueBereich;
	}

	public ArrayList<Bereich> getBereiche() {
	    return bereiche;
	}

	public void setBereiche(ArrayList<Bereich> bereiche) {
		this.bereiche = bereiche;
	}

	private ArrayList<Bereich> bereiche;
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

	public float getAverageOfBereich(Bereich b) {

		float ret = 0;
		int i = 0;
		for (Item item : items) {
			if (item.getBereich().equals(b)) {

				ret += Float.parseFloat(itemWertung.get(items.indexOf(item)));
				i++;
			}
			if (i > 0) {
				ret /= i;
			}
		}

		return ret;
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
			
					eh.setBereiche( getBereicheFromEvaluationHelper(bewertungen) );
					eh.avValueBereich = new double[eh.getBereiche().size()];
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
					for(Bereich bereich:eh.getBereiche()){
					    double value = 0;
					    int valcount = 0;
		                   for (Bewertung be : temp) {
		                       
		                        if (be.getZeilenid().equals(eh.getRawId())) {
		                            if (be.getItem() != null) {
		                                if(be.getItem().getBereich().equals( bereich ) && !be.getWert().isEmpty()){
		                                    value += Double.parseDouble( be.getWert());
		                                    valcount += 1;
		                                    
		                                }
		                                
		                            }

	
		                            }
		    
		                            
		                        }
					    
		                   if(valcount != 0){
		                   eh.avValueBereich[eh.getBereiche().indexOf( bereich )] = (value / valcount);
		                   }
		                   else{
		                       eh.avValueBereich[eh.getBereiche().indexOf( bereich )] = 0;
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
