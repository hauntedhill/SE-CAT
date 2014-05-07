package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;

@Repository
@Transactional
public class EigenschaftenModel {

	@Autowired
	private EigenschaftenDAO eigenschaftenDAO;

	public List<Eigenschaft> getEigenschaften() {
		return eigenschaftenDAO.findAll();
	}

	public void persist(Eigenschaft e) {
		eigenschaftenDAO.persist(e);
	}

}
