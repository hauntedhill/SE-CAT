package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

@Repository
public class PerspektiveDAO extends BaseDAO<Perspektive> {

	public PerspektiveDAO() {
		super(Perspektive.class);
		// TODO Auto-generated constructor stub
	}

}
