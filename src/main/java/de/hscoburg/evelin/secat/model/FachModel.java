package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;

@Repository
@Transactional
public class FachModel {
	@Autowired
	private FachDAO fachDAO;

	public List<Fach> getFaecher() {
		return fachDAO.findAll();
	}

	// public void persist(Fach e) {
	// fachDAO.persist(e);
	// }

	public void saveFach(String name) throws IllegalArgumentException {
		if (!"".equals(name)) {
			Fach e = new Fach();
			e.setName(name);
			fachDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public List<Lehrveranstaltung> getLehrveranstaltungenForFach(Fach f) {
		return fachDAO.findById(f.getId()).getLehrveranstaltungen();
	}
}
