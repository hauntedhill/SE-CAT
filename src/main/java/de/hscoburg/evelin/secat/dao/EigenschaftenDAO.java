package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;

@Repository
public class EigenschaftenDAO extends BaseDAO<Eigenschaft> {

	public EigenschaftenDAO() {
		super(Eigenschaft.class);
		// TODO Auto-generated constructor stub
	}

}
