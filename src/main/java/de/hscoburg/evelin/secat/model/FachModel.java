package de.hscoburg.evelin.secat.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;

@Repository
@Transactional
public class FachModel {
	@Autowired
	private FachDAO fachDAO;

	public List<Fach> getEigenschaften() {
		return fachDAO.findAll();
	}

	public void persist(Fach e) {
		fachDAO.persist(e);
	}
}
