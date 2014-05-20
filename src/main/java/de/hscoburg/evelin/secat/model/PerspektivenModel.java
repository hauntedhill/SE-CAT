package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

@Repository
@Transactional
public class PerspektivenModel {

	@Autowired
	private PerspektiveDAO perspektivenDAO;

	public List<Perspektive> getPerspektiven() {
		return perspektivenDAO.findAll();
	}

	// public void persist(Perspektive e) {
	// perspektivenDAO.persist(e);
	// }

	public void savePerspektive(String name) throws IllegalArgumentException {
		if (!"".equals(name)) {
			Perspektive e = new Perspektive();
			e.setName(name);
			perspektivenDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
