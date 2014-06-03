package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
@Transactional
public class FragenModel {

	@Autowired
	private FrageDAO frageDAO;

	public List<Frage> getFragen() {
		return frageDAO.findAll();
	}

	public void saveFrage(String name, String text, Skala skala) throws IllegalArgumentException {
		if (name == null || text == null || "".equals(name) || "".equals(text) || skala == null) {
			throw new IllegalArgumentException();
		}

		Frage f = new Frage();
		f.setSkala(skala);
		f.setText(text);
		f.setName(name);

		frageDAO.persist(f);
	}

}
