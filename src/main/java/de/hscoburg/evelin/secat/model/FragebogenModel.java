package de.hscoburg.evelin.secat.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.controller.xml.BaseXML;
import de.hscoburg.evelin.secat.controller.xml.DiskretefrageXML;
import de.hscoburg.evelin.secat.controller.xml.FragebogenXML;
import de.hscoburg.evelin.secat.controller.xml.FragenblockXML;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
@Transactional
public class FragebogenModel {

	@Autowired
	private FragebogenDAO fragebogenDAO;
	@Autowired
	private FrageDAO frageDAO;

	public List<Fragebogen> getFragebogenFor(Eigenschaft e, Perspektive p, Lehrveranstaltung l, String name, LocalDate von, LocalDate bis, Skala s) {
		Date vonDate = von != null ? Date.from(von.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;
		Date bisDate = bis != null ? Date.from(bis.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;

		return fragebogenDAO.getFrageboegenFor(e, p, l, name, vonDate, bisDate, s);
	}

	public void persistFragebogen(Fragebogen f) {
		fragebogenDAO.persist(f);
	}

	public void mergeFragebogen(Fragebogen f) {
		fragebogenDAO.merge(f);
	}

	public void persistFrage(Frage f) {
		frageDAO.persist(f);
	}

	public void mergeFrage(Frage f) {
		frageDAO.merge(f);
	}

	public StringBuilder generateXMLFor(Fragebogen f) {
		f = fragebogenDAO.findById(f.getId());

		FragebogenXML fXML = new FragebogenXML(f.getName());
		FragenblockXML block = new FragenblockXML("omfg", 1);

		fXML.addChild(block);

		int i = 1;

		for (i = 1; i <= f.getItems().size(); i++) {
			Item item = f.getItems().get(i - 1);

			block.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(f, item), i, f.getSkala().getWeight(), f.getSkala().getSteps(), f.getSkala()
					.getOptimum(), f.getSkala().getMinText(), f.getSkala().getMaxText(), item.getFrage()));
		}

		// FreitextfrageXML ftf = new FreitextfrageXML(BaseXML.generateUniqueId(f, new Frage(99999), "Bla?", i + 1, 10);

		// block.addChild(ftf);

		return fXML.generateXML();
	}

}
