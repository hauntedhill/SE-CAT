package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;

@Repository
@Transactional
public class SkalenModel {

	@Autowired
	private SkalaDAO skalaDAO;

	public List<Skala> getSkalen() {
		return skalaDAO.findAll();
	}

	// public void persist(Skala e) {
	// skalaDAO.persist(e);
	// }

	public void saveSkala(SkalaType type, String name, String zeilen, String schritte, String schrittWeite, String minText, String maxText, String optimum,
			List<String> keys) throws NumberFormatException {

		Skala s = new Skala();
		if ("".equals(name.trim()) || type == null) {
			throw new NumberFormatException("Empty String");
		}
		s.setName(name);
		s.setType(type);
		if (SkalaType.FREE.equals(type)) {
			s.setRows(Integer.parseInt(zeilen));
		} else if (SkalaType.DISCRET.equals(type)) {
			if ("".equals(minText.trim()) || "".equals(maxText.trim())) {
				throw new NumberFormatException("Empty String");
			}
			s.setMaxText(maxText);
			s.setMinText(minText);
			s.setOptimum(Integer.parseInt(optimum));
			s.setWeight(Integer.parseInt(schrittWeite));
			s.setSteps(Integer.parseInt(schritte));
		} else if (SkalaType.MULTIPLECHOICE.equals(type)) {
			s.setChoices(keys);
		}
		skalaDAO.persist(s);

	}

}
