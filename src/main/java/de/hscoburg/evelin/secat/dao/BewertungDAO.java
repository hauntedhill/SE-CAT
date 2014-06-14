package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;

/**
 * DAO zum Zugriff auf Bewertungs Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class BewertungDAO extends BaseDAO<Bewertung> {

	public BewertungDAO() {
		super(Bewertung.class);

	}

}
