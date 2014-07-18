package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;

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

	/**
	 * Aktualisiert ein Fach
	 * 
	 * @param e
	 *            - {@link Fach}
	 * @throws IllegalArgumentException
	 */
	public void updateFach(Fach e) throws IllegalArgumentException {
		if (!"".equals(e.getName()) && !isLocked(e)) {

			fachDAO.merge(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Prueft ob ein Fach editiert werden darf.
	 * 
	 * @param e
	 *            {@link Fach}
	 * @return true/false
	 */
	public boolean isLocked(Fach e) {
		e = fachDAO.findById(e.getId());

		for (Lehrveranstaltung i : e.getLehrveranstaltungen() != null ? e.getLehrveranstaltungen() : new ArrayList<Lehrveranstaltung>()) {
			for (Fragebogen f : i.getFrageboegen() != null ? i.getFrageboegen() : new ArrayList<Fragebogen>()) {
				if (f.getExportiertQuestorPro()) {
					return true;
				}
			}
		}

		return false;
	}
}
