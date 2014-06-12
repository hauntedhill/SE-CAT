package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;

/**
 * Model zur Verarbeitung von Faechern
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class FachModel {
	@Autowired
	private FachDAO fachDAO;

	/**
	 * Gibt eine Liste mit allen Faechern zurueck.
	 * 
	 * @return {@link List} mit {@link Fach}ern
	 */
	public List<Fach> getFaecher() {
		return fachDAO.findAll();
	}

	/**
	 * Speichert ein {@link Fach} im System
	 * 
	 * @param name
	 *            - {@link String} mit dem Namen des Faches
	 * @throws IllegalArgumentException
	 *             Bei einem Ungueltigen Namen
	 */
	public void saveFach(String name) throws IllegalArgumentException {
		if (!"".equals(name)) {
			Fach e = new Fach();
			e.setName(name);
			fachDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
