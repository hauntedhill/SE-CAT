package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

/**
 * Model zur Verwaltung von Perspektiven
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class PerspektivenModel {

	@Autowired
	private PerspektiveDAO perspektivenDAO;

	/**
	 * Gibt alle Perspektiven zurueck.
	 * 
	 * @return {@link List} mit {@link Perspektive}n
	 */
	public List<Perspektive> getPerspektiven() {
		return perspektivenDAO.findAll();
	}

	/**
	 * Speichert eine Perspektive innerhalb des Systems
	 * 
	 * @param name
	 *            - {@link String} mit dem Namen der Perspektive
	 * @throws IllegalArgumentException
	 */
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
