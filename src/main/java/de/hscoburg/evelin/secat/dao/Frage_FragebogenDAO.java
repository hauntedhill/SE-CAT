package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;

@Repository
public class Frage_FragebogenDAO extends BaseDAO<Frage_Fragebogen> {

	public Frage_FragebogenDAO() {
		super(Frage_Fragebogen.class);
		// TODO Auto-generated constructor stub
	}

}
