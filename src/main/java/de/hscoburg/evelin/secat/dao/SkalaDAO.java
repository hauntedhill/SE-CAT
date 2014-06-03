package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
public class SkalaDAO extends BaseDAO<Skala> {

	public SkalaDAO() {
		super(Skala.class);

	}

}
