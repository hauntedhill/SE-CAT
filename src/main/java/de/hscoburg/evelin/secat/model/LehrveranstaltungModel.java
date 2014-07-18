package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.LehrveranstaltungDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;

/**
 * Model zur Verarbeitung von Lehrveranstaltungen
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class LehrveranstaltungModel {
	@Autowired
	private LehrveranstaltungDAO lehrveranstaltungsDAO;;

	/**
	 * Gibt alle Lehrveranstaltungen zurueck.
	 * 
	 * @return {@link List} {@link Lehrveranstaltung}
	 */
	public List<Lehrveranstaltung> getLehrveranstaltung() {
		return lehrveranstaltungsDAO.findAll();
	}

	/**
	 * Speichert eine Lehrveranstaltung innerhalb des Systems
	 * 
	 * @param dozent
	 *            - {@link String} mit dem Namen des Dozenten
	 * @param fach
	 *            - Das {@link Fach}
	 * @param jahr
	 *            - Das Jahr als {@link Integer}
	 * @param semester
	 *            - Den SemesterType
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("deprecation")
	public void saveLehrveranstaltung(String dozent, Fach fach, Integer jahr, SemesterType semester) throws IllegalArgumentException {
		if (jahr != null && semester != null && fach != null && !"".equals(dozent)) {
			Lehrveranstaltung e = new Lehrveranstaltung();

			e.setAktiv(true);
			e.setFach(fach);

			Date d = new Date();
			d.setYear(jahr);
			// d.setDate(1);
			// d.setMonth(1);

			e.setJahr(d);
			e.setSemester(semester);
			e.setDozent(dozent);

			lehrveranstaltungsDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Aktualisiert die Lehrveranstaltung
	 * 
	 * @param l
	 *            - {@link Lehrveranstaltung}
	 */
	public void updateLehrveranstaltung(Lehrveranstaltung l) {
		if (l.getJahr() != null && l.getSemester() != null && l.getFach() != null && !"".equals(l.getDozent())) {

			lehrveranstaltungsDAO.merge(l);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Prueft ob eine Lehrveranstaltung editiert werden darf.
	 * 
	 * @param e
	 *            {@link Lehrveranstaltung}
	 * @return true/false
	 */
	public boolean isLocked(Lehrveranstaltung l) {
		l = lehrveranstaltungsDAO.findById(l.getId());
		for (Fragebogen f : l.getFrageboegen() != null ? l.getFrageboegen() : new ArrayList<Fragebogen>()) {
			if (f.getExportiertQuestorPro()) {
				return true;
			}
		}
		return false;
	}
}
