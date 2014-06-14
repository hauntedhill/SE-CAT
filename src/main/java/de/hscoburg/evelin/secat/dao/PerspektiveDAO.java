package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

/**
 * DAO zum Zugriff auf Perspektiven Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class PerspektiveDAO extends BaseDAO<Perspektive> {

	public PerspektiveDAO() {
		super(Perspektive.class);

	}

}
