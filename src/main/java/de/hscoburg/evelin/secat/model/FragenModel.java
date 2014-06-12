package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.Frage_FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
@Transactional
public class FragenModel {

	@Autowired
	private FrageDAO frageDAO;

	public List<Frage> getFragen() {
		return frageDAO.findAll();
	}

	@Autowired
	private Frage_FragebogenDAO frageFragebogenDAO;

	public void persist(Frage_Fragebogen f) {
		frageFragebogenDAO.persist(f);
	}

	public void merge(Frage_Fragebogen f) {
		frageFragebogenDAO.merge(f);
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
