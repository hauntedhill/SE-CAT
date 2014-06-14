package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;

/**
 * DAO zum Zugriff auf Frage Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class FrageDAO extends BaseDAO<Frage> {

	public FrageDAO() {
		super(Frage.class);

	}

}
