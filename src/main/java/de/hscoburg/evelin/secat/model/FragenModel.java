package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.Frage_FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Skala;

/**
 * Model zur Verarbeitung von Fragen
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class FragenModel {

	@Autowired
	private FrageDAO frageDAO;

	@Autowired
	private Frage_FragebogenDAO frageFragebogenDAO;

	/**
	 * Gibt alle im System vorhandenen Fragen zurueck.
	 * 
	 * @return {@link List} mit {@link Frage}
	 */
	public List<Frage> getFragen() {
		return frageDAO.findAll();
	}

	/**
	 * Speichert eine Frage im System
	 * 
	 * @param name
	 *            {@link String} als Namen des Systems
	 * @param text
	 *            {@link String} fuer den Text einer Frage
	 * @param skala
	 *            {@link Skala} als Wertebereich einer Frage
	 * @throws IllegalArgumentException
	 *             Bei fehlerhaften Eingabewerten oder gesperrter Entity
	 */
	public void saveFrage(String name, String text, Skala skala) throws IllegalArgumentException {
		if (name == null || text == null || "".equals(name) || "".equals(text) || skala == null) {
			throw new IllegalArgumentException();
		}

		Frage f = new Frage();
		f.setSkala(skala);
		f.setText(text);
		f.setName(name);

		frageDAO.persist(f);
	}

}
