package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;

/**
 * DAO zum Zugriff auf Fach Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class FachDAO extends BaseDAO<Fach> {

	public FachDAO() {
		super(Fach.class);

	}

}
