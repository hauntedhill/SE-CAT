package de.hscoburg.evelin.secat.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.controller.xml.BaseXML;
import de.hscoburg.evelin.secat.controller.xml.DiskretefrageXML;
import de.hscoburg.evelin.secat.controller.xml.FragebogenXML;
import de.hscoburg.evelin.secat.controller.xml.FragenblockXML;
import de.hscoburg.evelin.secat.controller.xml.FreitextfrageXML;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;

@Repository
@Transactional
public class FragebogenModel {

	private static CharSequenceTranslator stringXMLEscaper = StringEscapeUtils.ESCAPE_XML.with(NumericEntityEscaper.between(0x0a, 0x0a));

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

	public String generateXMLFor(Fragebogen f) {
		System.out.println("start");
		f = fragebogenDAO.findById(f.getId());

		FragebogenXML fXML = new FragebogenXML(f.getName());

		Collections.sort(f.getItems(), new ItemComparator());

		int i = 1;
		Bereich currentBereich = null;
		int blockCount = 1;
		int questionCount = 1;

		BaseXML mainBlock = new FragenblockXML("", 1);
		fXML.addChild(mainBlock);
		BaseXML currentBlock = mainBlock;

		addFfragen(blockCount++, mainBlock, FragePosition.TOP, f.getFragen(), f);

		for (i = 1; i <= f.getItems().size(); i++) {
			Item item = f.getItems().get(i - 1);

			if (!item.getBereich().equals(currentBereich)) {
				BaseXML innerBlock = new FragenblockXML(stringXMLEscaper.translate(item.getBereich().getName()), blockCount++);
				questionCount = 1;
				mainBlock.addChild(innerBlock);
				currentBlock = innerBlock;
				currentBereich = item.getBereich();
			}

			if (f.getSkala().getType().equals(SkalaType.DISCRET)) {
				currentBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(f, item), questionCount++, f.getSkala().getWeight(), f.getSkala()
						.getSteps(), f.getSkala().getOptimum(), stringXMLEscaper.translate(f.getSkala().getMinText()), stringXMLEscaper.translate(f.getSkala()
						.getMaxText()), stringXMLEscaper.translate(item.getFrage())));
			} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
				currentBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(f, item), stringXMLEscaper.translate(item.getFrage()), questionCount++, f
						.getSkala().getRows()));

			}

		}
		addFfragen(blockCount++, mainBlock, FragePosition.BOTTOM, f.getFragen(), f);
		// FreitextfrageXML ftf = new FreitextfrageXML(BaseXML.generateUniqueId(f, new Frage(99999), "Bla?", i + 1, 10);

		// block.addChild(ftf);

		f.setExportiert(true);
		fragebogenDAO.merge(f);

		return fXML.generateXML();
	}

	private void addFfragen(int useBlockCount, BaseXML node, FragePosition position, List<Frage> fragen, Fragebogen fb) {

		BaseXML innerBlock = new FragenblockXML("", useBlockCount);

		int questionCount = 1;
		for (Frage f : fragen != null ? fragen : new ArrayList<Frage>()) {
			if (f.getPosition().equals(position)) {
				if (f.getSkala().getType().equals(SkalaType.DISCRET)) {
					innerBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(fb, f), questionCount++, f.getSkala().getWeight(), f.getSkala()
							.getSteps(), f.getSkala().getOptimum(), stringXMLEscaper.translate(f.getSkala().getMinText()), stringXMLEscaper.translate(f
							.getSkala().getMaxText()), stringXMLEscaper.translate(f.getText())));

				} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
					innerBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(fb, f), stringXMLEscaper.translate(f.getText()), questionCount++, f
							.getSkala().getRows()));

				}
			}
		}
		if (innerBlock.getChildSize() > 0) {
			node.addChild(innerBlock);
		}
	}

	public class ItemComparator implements Comparator<Item> {
		public int compare(Item a, Item b) {
			// int dateComparison = a.getBereich().compareTo(b.date);
			return a.getBereich().getName().compareTo(b.getBereich().getName());
		}
	}

}
