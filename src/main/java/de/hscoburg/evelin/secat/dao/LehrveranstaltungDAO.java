package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;

@Repository
public class LehrveranstaltungDAO extends BaseDAO<Lehrveranstaltung> {

	public LehrveranstaltungDAO() {
		super(Lehrveranstaltung.class);
		// TODO Auto-generated constructor stub
	}

}
