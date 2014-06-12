package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.SkalaDAO;
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
	 *            - {@link SkalaType} der Skala
	 * @param name
	 *            - {@link String} mit dem Namen der Skala
	 * @param zeilen
	 *            - Zeilen der Skala bei Freitext
	 * @param schritte
	 *            - Schritte der Skala bei Diskret
	 * @param schrittWeite
	 *            - SchrittWeite bei Diskret
	 * @param minText
	 *            - MinText als {@link String} bei Diskret
	 * @param maxText
	 *            - MaxText als {@link String} bei Diskret
	 * @param optimum
	 *            - Definiert das Optimum bei Diskret
	 * @param keys
	 *            - Definiert die Auswahl,oeglichkeiten bei MC
	 * @param schrittWeiteMC
	 *            - SchrittWeite bei MC
	 * @param defaultAnswer
	 *            - definiert die default ANtwort bei MC
	 * @param refuseAnswer
	 *            - definiert die refuse Antowrt bei MC
	 * @throws NumberFormatException
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
			s.setRows(Integer.parseInt(zeilen));
		} else if (SkalaType.DISCRET.equals(type)) {
			if ("".equals(minText.trim()) || "".equals(maxText.trim())) {
				throw new NumberFormatException("Empty String");
			}
			s.setMaxText(maxText);
			s.setMinText(minText);
			s.setOptimum(Integer.parseInt(optimum));
			s.setWeight(Integer.parseInt(schrittWeite));
			s.setSteps(Integer.parseInt(schritte));
		} else if (SkalaType.MC.equals(type)) {
			if (keys.size() > 1) {
				throw new NumberFormatException("Empty String");
			}
			s.setChoices(keys);
			s.setOtherAnswer(defaultAnswer);
			s.setWeight(Integer.parseInt(schrittWeiteMC));
			s.setRefuseAnswer(refuseAnswer);
		}
		skalaDAO.persist(s);

	}

}
