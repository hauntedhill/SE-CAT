package de.hscoburg.evelin.secat.model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.LehrveranstaltungDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;

@Repository
@Transactional
public class LehrveranstaltungModel {
	@Autowired
	private LehrveranstaltungDAO lehrveranstaltungsDAO;;

	public List<Lehrveranstaltung> getLehrveranstaltung() {
		return lehrveranstaltungsDAO.findAll();
	}

	// public void persist(Lehrveranstaltung e) {
	// lehrveranstaltungsDAO.persist(e);
	// }

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
}
