package de.hscoburg.evelin.secat.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
@Transactional
public class FragebogenModel {

	@Autowired
	private FragebogenDAO fragebogenDAO;

	public List<Fragebogen> getFragebogenFor(Eigenschaft e, Perspektive p, Lehrveranstaltung l, String name, LocalDate von, LocalDate bis, Skala s) {
		Date vonDate = von != null ? Date.from(von.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;
		Date bisDate = bis != null ? Date.from(bis.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;

		return fragebogenDAO.getFrageboegenFor(e, p, l, name, vonDate, bisDate, s);
	}

}
