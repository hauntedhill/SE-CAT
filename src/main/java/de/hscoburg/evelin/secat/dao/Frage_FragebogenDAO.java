package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;

/**
 * DAO zum Zugriff auf FrageFragebogen Entites
 * 
 * @author zuch1000
 * 
 */
@Repository
public class Frage_FragebogenDAO extends BaseDAO<Frage_Fragebogen> {

	public Frage_FragebogenDAO() {
		super(Frage_Fragebogen.class);

	}

}
