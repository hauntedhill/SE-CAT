package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;

/**
 * DAO zum Zugriff auf Lehrveranstaltung Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class LehrveranstaltungDAO extends BaseDAO<Lehrveranstaltung> {

	public LehrveranstaltungDAO() {
		super(Lehrveranstaltung.class);

	}

}
