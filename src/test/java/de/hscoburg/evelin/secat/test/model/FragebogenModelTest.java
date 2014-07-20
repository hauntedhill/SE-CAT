package de.hscoburg.evelin.secat.test.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import de.hscoburg.evelin.secat.dao.BewertungDAO;
import de.hscoburg.evelin.secat.dao.EinstellungDAO;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.Frage_FragebogenDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.model.FragebogenModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;

public class FragebogenModelTest extends BaseModelTest {

	private static FragebogenModel model = new FragebogenModel();

	@Test
	public void exportQuestorPro() throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/questorProExample.txt")));

		String xml1 = "";
		String line = null;

		while ((line = reader.readLine()) != null) {
			xml1 += line;
		}
		reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/questorProExample2.txt")));

		String xml2 = "";

		while ((line = reader.readLine()) != null) {
			xml2 += line;
		}
		reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/questorProExample3.txt")));

		String xml3 = "";

		while ((line = reader.readLine()) != null) {
			xml3 += line;
		}
		f1.setSkala(sFree);
		l.setSemester(SemesterType.WS);
		fragebogen1.setSkala(sDiscrete);

		model.generateXMLtoQuestorPro(fragebogen1).equals(xml1);

		fragebogen1.setSkala(sFree);

		model.generateXMLtoQuestorPro(fragebogen1).equals(xml2);

		fragebogen1.setSkala(sMQ);
		f1.setSkala(sDiscrete);
		l.setSemester(SemesterType.SS);
		model.generateXMLtoQuestorPro(fragebogen1).equals(xml3);

	}

	@Test
	public void exportCore() throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/coreExample1.txt")));

		String xml1 = "";
		String line = null;

		while ((line = reader.readLine()) != null) {
			xml1 += line;
		}
		reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/coreExample2.txt")));

		String xml2 = "";

		while ((line = reader.readLine()) != null) {
			xml2 += line;
		}
		reader = new BufferedReader(new InputStreamReader(FragebogenModelTest.class.getResourceAsStream("/coreExample3.txt")));

		String xml3 = "";

		while ((line = reader.readLine()) != null) {
			xml3 += line;
		}
		f1.setSkala(sFree);
		l.setSemester(SemesterType.WS);
		fragebogen1.setSkala(sMQ);
		(new String(model.exportQuestionarieToCore(fragebogen1).toByteArray())).equals(xml1);

		fragebogen1.setSkala(sDiscrete);
		f1.setSkala(sDiscrete);
		l.setSemester(SemesterType.SS);

		(new String(model.exportQuestionarieToCore(fragebogen1).toByteArray())).equals(xml2);

		fragebogen1.setSkala(sFree);

		(new String(model.exportQuestionarieToCore(fragebogen1).toByteArray())).equals(xml3);

	}

	private static FragebogenDAO fragebogenDAO;

	private static FrageDAO frageDAO;

	private static BewertungDAO bewertungsDAO;

	private static ItemDAO itemDAO;

	private static EinstellungDAO einstellungDAO;

	private static Frage_FragebogenDAO frageFragebogenDAO;

	private static Fragebogen fragebogen1;

	private static Skala sMQ;

	private static Skala sDiscrete;

	private static Skala sFree;

	public static Frage f1;

	private static Lehrveranstaltung l;

	@BeforeClass
	public static void setup() throws Exception {
		fragebogenDAO = Mockito.mock(FragebogenDAO.class);
		frageDAO = Mockito.mock(FrageDAO.class);
		bewertungsDAO = Mockito.mock(BewertungDAO.class);
		itemDAO = Mockito.mock(ItemDAO.class);
		einstellungDAO = Mockito.mock(EinstellungDAO.class);
		frageFragebogenDAO = Mockito.mock(Frage_FragebogenDAO.class);

		fragebogen1 = new Fragebogen();

		Eigenschaft e = new Eigenschaft();
		e.setId(1);
		e.setName("testEigenschaft");

		Perspektive p = new Perspektive();
		p.setId(1);
		p.setName("testPerspektive");

		Fach f = new Fach();
		f.setId(1);
		f.setName("test>Fach");

		l = new Lehrveranstaltung();

		l.setId(1);
		l.setDozent("testDozent");
		l.setJahr(new Date());
		f.addLehrveranstaltung(l);
		l.setSemester(SemesterType.SS);

		fragebogen1.setEigenschaft(e);
		fragebogen1.setPerspektive(p);
		fragebogen1.setLehrveranstaltung(l);

		Handlungsfeld h = new Handlungsfeld();
		h.setId(1);
		h.setName("testName");
		h.setNotiz("testNotiz");

		Bereich b = new Bereich();
		b.setId(1);
		b.setName("testBereich");

		h.addBereich(b);

		Item i1 = new Item();
		i1.setId(1);
		i1.setName("testItem1");
		i1.setNotiz("testNotiz1");
		b.addItem(i1);
		i1.addEigenschaft(e);
		i1.addPerspektive(p);
		i1.addFragebogen(fragebogen1);
		i1.setFrage("item1Frage");

		Item i2 = new Item();
		i2.setId(2);
		i2.setName("testItem1");
		i2.setNotiz("testNotiz1");
		b.addItem(i2);
		i2.addEigenschaft(e);
		i2.addPerspektive(p);
		i2.addFragebogen(fragebogen1);
		i2.setFrage("item2Frage");

		sDiscrete = new Skala();

		sDiscrete.setId(1);
		sDiscrete.setType(SkalaType.DISCRET);
		sDiscrete.setMaxText("testMax");
		sDiscrete.setMinText("testMin");
		sDiscrete.setOptimum(4);
		sDiscrete.setSchritte(4);
		sDiscrete.setSchrittWeite(1);

		fragebogen1.setSkala(sDiscrete);

		sFree = new Skala();

		sFree.setId(2);
		sFree.setType(SkalaType.FREE);
		sFree.setZeilen(4);

		sMQ = new Skala();

		sMQ.setId(3);
		sMQ.setType(SkalaType.MC);
		sMQ.setSchrittWeite(1);
		sMQ.setAndereAntwort("tetDefaultAnswer");
		sMQ.setVerweigerungsAntwort("testVerweigerungsAntwort");
		sMQ.setAuswahl(Arrays.asList(new String[] { "test1", "test2" }));

		f1 = new Frage();
		f1.setId(1);
		f1.setName("frage1");
		f1.setSkala(sFree);
		f1.setText("Was?");

		Frage f2 = new Frage();
		f2.setId(2);
		f2.setName("frage2");
		f2.setSkala(sMQ);
		f2.setText("Wo?");

		Frage_Fragebogen ff1 = new Frage_Fragebogen();
		ff1.setFrage(f1);
		ff1.setId(1);
		ff1.setPosition(FragePosition.BOTTOM);
		ff1.setFragebogen(fragebogen1);

		Frage_Fragebogen ff2 = new Frage_Fragebogen();
		ff2.setFrage(f2);
		ff2.setId(2);
		ff2.setPosition(FragePosition.TOP);
		ff2.setFragebogen(fragebogen1);
		fragebogen1.setId(1);

		fragebogen1.setFrageFragebogen(Arrays.asList(new Frage_Fragebogen[] { ff1, ff2 }));

		Bewertung b1 = new Bewertung();
		b1.setId(1);
		b1.setFrage(f1);
		b1.setQuelle("quelle");
		b1.setWert("1");
		b1.setZeilenid("test");
		b1.setZeit("2014");
		b1.setFragebogen(fragebogen1);

		f1.setBewertungen(Arrays.asList(new Bewertung[] { b1 }));

		Bewertung b2 = new Bewertung();
		b2.setId(2);
		b2.setFrage(f2);
		b2.setQuelle("quelle");
		b2.setWert("2");
		b2.setZeilenid("test");
		b2.setZeit("2014");
		b2.setFragebogen(fragebogen1);

		f2.setBewertungen(Arrays.asList(new Bewertung[] { b2 }));

		Bewertung b3 = new Bewertung();
		b3.setId(3);
		b3.setItem(i1);
		b3.setQuelle("quelle");
		b3.setWert("3");
		b3.setZeilenid("test");
		b3.setZeit("2014");
		b3.setFragebogen(fragebogen1);

		i1.setBewertungen(Arrays.asList(new Bewertung[] { b3 }));

		Bewertung b4 = new Bewertung();
		b4.setId(4);
		b4.setItem(i2);
		b4.setQuelle("quelle");
		b4.setWert("3");
		b4.setZeilenid("test");
		b4.setZeit("2014");
		b4.setFragebogen(fragebogen1);

		i2.setBewertungen(Arrays.asList(new Bewertung[] { b4 }));

		Einstellung ei = new Einstellung();
		ei.setId(1);
		ei.setName(EinstellungenType.STANDORT);
		ei.setWert("HSC");

		Mockito.when(einstellungDAO.findByName(EinstellungenType.STANDORT)).thenReturn(ei);

		fragebogen1.setBewertungen(Arrays.asList(new Bewertung[] { b1, b2, b3, b4 }));

		fragebogen1.setErstellungsDatum(new Date());

		List<Item> items = new ArrayList<Item>();
		items.add(i2);
		items.add(i1);

		fragebogen1.setItems(items);
		fragebogen1.setName("testFragebogen");

		Mockito.when(fragebogenDAO.findById(fragebogen1.getId())).thenReturn(fragebogen1);

		setValueToField(fragebogenDAO, "fragebogenDAO", model);
		setValueToField(frageDAO, "frageDAO", model);
		setValueToField(bewertungsDAO, "bewertungsDAO", model);
		setValueToField(itemDAO, "itemDAO", model);
		setValueToField(einstellungDAO, "einstellungDAO", model);
		setValueToField(frageFragebogenDAO, "frageFragebogenDAO", model);
	}

}
