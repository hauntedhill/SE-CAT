package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
@Transactional
public class SkalenModel {

	@Autowired
	private SkalaDAO skalaDAO;

	public List<Skala> getSkalen() {
		return skalaDAO.findAll();
	}

	public void persist(Skala e) {
		skalaDAO.persist(e);
	}

}
