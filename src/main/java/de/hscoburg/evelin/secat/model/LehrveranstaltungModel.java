package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.LehrveranstaltungDAO;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;

@Repository
@Transactional
public class LehrveranstaltungModel {
	@Autowired
	private LehrveranstaltungDAO lehrveranstaltungsDAO;;

	public List<Lehrveranstaltung> getLehrveranstaltung() {
		return lehrveranstaltungsDAO.findAll();
	}

	public void persist(Lehrveranstaltung e) {
		lehrveranstaltungsDAO.persist(e);
	}
}
