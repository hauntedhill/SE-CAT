package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;

/**
 * Model zur Verwaltung von Skalen
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class SkalenModel {

	@Autowired
	private SkalaDAO skalaDAO;

	/**
	 * Gibt alle Skalen zurueck.
	 * 
	 * @return {@link List} mit {@link Skala}
	 */
	public List<Skala> getSkalen() {
		return skalaDAO.findAll();
	}

	/**
	 * Speichert eine Skala im System
	 * 
	 * @param type
	 *            {@link SkalaType} der Skala
	 * @param name
	 *            {@link String} mit dem Namen der Skala
	 * @param zeilen
	 *            Zeilen der Skala bei Freitext
	 * @param schritte
	 *            Schritte der Skala bei Diskret
	 * @param schrittWeite
	 *            SchrittWeite bei Diskret
	 * @param minText
	 *            MinText als {@link String} bei Diskret
	 * @param maxText
	 *            MaxText als {@link String} bei Diskret
	 * @param optimum
	 *            Definiert das Optimum bei Diskret
	 * @param keys
	 *            Definiert die Auswahl,oeglichkeiten bei MC
	 * @param schrittWeiteMC
	 *            SchrittWeite bei MC
	 * @param defaultAnswer
	 *            definiert die default ANtwort bei MC
	 * @param refuseAnswer
	 *            definiert die refuse Antowrt bei MC
	 * @throws NumberFormatException
	 *             Bei fehlerhaften Eingabewerten
	 */
	public void saveSkala(SkalaType type, String name, String zeilen, String schritte, String schrittWeite, String minText, String maxText, String optimum,
			List<String> keys, String schrittWeiteMC, String defaultAnswer, String refuseAnswer) throws NumberFormatException {

		Skala s = new Skala();
		if ("".equals(name.trim()) || type == null) {
			throw new NumberFormatException("Empty String");
		}
		s.setName(name);
		s.setType(type);
		if (SkalaType.FREE.equals(type)) {
			s.setZeilen(Integer.parseInt(zeilen));
		} else if (SkalaType.DISCRET.equals(type)) {
			if ("".equals(minText.trim()) || "".equals(maxText.trim())) {
				throw new NumberFormatException("Empty String");
			}
			s.setMaxText(maxText);
			s.setMinText(minText);
			s.setOptimum(Integer.parseInt(optimum));
			s.setSchrittWeite(Integer.parseInt(schrittWeite));
			s.setSchritte(Integer.parseInt(schritte));
		} else if (SkalaType.MC.equals(type)) {
			if (keys.size() < 1) {
				throw new NumberFormatException("Empty String");
			}
			s.setAuswahl(keys);
			s.setAndereAntwort(defaultAnswer);
			s.setSchrittWeite(Integer.parseInt(schrittWeiteMC));
			s.setVerweigerungsAntwort(refuseAnswer);
		}
		skalaDAO.persist(s);

	}

	/**
	 * Aktualisisert die uebergebene Skala
	 * 
	 * @param s
	 *            {@link Skala}
	 * @throws IllegalArgumentException
	 *             Bei fehlerhaften Eingabewerten oder gesperrter Entity
	 */
	public void updateSkala(Skala s) throws IllegalArgumentException {
		if (isLocked(s)) {
			throw new IllegalArgumentException();
		}
		if (s.getType().equals(SkalaType.FREE) && (s.getZeilen() == null || s.getName() == null || "".equals(s.getName()))) {
			throw new IllegalArgumentException();
		}

		if (s.getType().equals(SkalaType.MC)
				&& (s.getAuswahl() == null || s.getAuswahl().size() == 0 || s.getSchrittWeite() == null || s.getName() == null || "".equals(s.getName()))) {
			throw new IllegalArgumentException();
		}

		if (s.getType().equals(SkalaType.DISCRET)
				&& (s.getOptimum() == null || s.getSchrittWeite() == null || s.getSchritte() == null || s.getMaxText() == null || s.getMinText() == null
						|| "".equals(s.getMaxText()) || "".equals(s.getMinText()) || s.getName() == null || "".equals(s.getName()))) {
			throw new IllegalArgumentException();
		}

		skalaDAO.merge(s);
	}

	/**
	 * Prueft ob eine Skala editiert werden darf.
	 * 
	 * @param s
	 *            {@link Skala}
	 * @return true/false
	 */
	public boolean isLocked(Skala s) {
		s = skalaDAO.findById(s.getId());

		for (Fragebogen f : s.getFrageboegen() != null ? s.getFrageboegen() : new ArrayList<Fragebogen>()) {
			if (f.getExportiertQuestorPro()) {
				return true;
			}
		}

		for (Frage f : s.getFragen() != null ? s.getFragen() : new ArrayList<Frage>()) {
			for (Frage_Fragebogen ff : f.getFrageFragebogen() != null ? f.getFrageFragebogen() : new ArrayList<Frage_Fragebogen>()) {

				if (ff.getFragebogen().getExportiertQuestorPro()) {
					return true;
				}
			}

		}

		return false;
	}

}
