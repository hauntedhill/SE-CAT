package de.hscoburg.evelin.secat.model;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.exchange.dto.AreaType;
import de.hscoburg.evelin.secat.exchange.dto.ChoiceType;
import de.hscoburg.evelin.secat.exchange.dto.ChoicesType;
import de.hscoburg.evelin.secat.exchange.dto.CourseType;
import de.hscoburg.evelin.secat.exchange.dto.EvaluationType;
import de.hscoburg.evelin.secat.exchange.dto.EvaluationsType;
import de.hscoburg.evelin.secat.exchange.dto.ItemType;
import de.hscoburg.evelin.secat.exchange.dto.ItemsType;
import de.hscoburg.evelin.secat.exchange.dto.ObjectFactory;
import de.hscoburg.evelin.secat.exchange.dto.PerspectiveType;
import de.hscoburg.evelin.secat.exchange.dto.PerspectivesType;
import de.hscoburg.evelin.secat.exchange.dto.PropertiesType;
import de.hscoburg.evelin.secat.exchange.dto.PropertyType;
import de.hscoburg.evelin.secat.exchange.dto.QuestionType;
import de.hscoburg.evelin.secat.exchange.dto.Questionarie;
import de.hscoburg.evelin.secat.exchange.dto.QuestionsType;
import de.hscoburg.evelin.secat.exchange.dto.ScaleType;
import de.hscoburg.evelin.secat.exchange.dto.ScaleTypeType;
import de.hscoburg.evelin.secat.exchange.dto.SemesterType;
import de.hscoburg.evelin.secat.exchange.dto.SphereActivityType;
import de.hscoburg.evelin.secat.exchange.dto.SubjectType;
import de.hscoburg.evelin.secat.model.xml.BaseXML;
import de.hscoburg.evelin.secat.model.xml.DiskretefrageXML;
import de.hscoburg.evelin.secat.model.xml.FragebogenXML;
import de.hscoburg.evelin.secat.model.xml.FragenblockXML;
import de.hscoburg.evelin.secat.model.xml.FreitextfrageXML;
import de.hscoburg.evelin.secat.model.xml.MultipleChoicefrageXML;

/**
 * Model fuer die Verarbeitung von Frageboegen
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class FragebogenModel {

	@Autowired
	private FragebogenDAO fragebogenDAO;
	@Autowired
	private FrageDAO frageDAO;
	@Autowired
	private HandlungsfeldModel handlungsfeldModel;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private EinstellungDAO einstellungDAO;

	@Autowired
	private Frage_FragebogenDAO frageFragebogenDAO;

	private ObjectFactory xmlFactory = new ObjectFactory();

	/**
	 * Erzeugt eine ID mit dem Standort als Prefix
	 * 
	 * @param id
	 * @return
	 */
	private String getIDWithStandort(int id) {
		Einstellung e = einstellungDAO.findByName(EinstellungenType.STANDORT);

		if (e == null || "".equals(e.getWert())) {
			throw new IllegalArgumentException();
		}

		return e.getWert() + "-" + id;
	}

	/**
	 * Gibt alle Frageboegen die den Uebergabeparametern entsprechen zurueck, werden bei null ignoriert.
	 * 
	 * @param e
	 *            - Die zu suchende {@link Eigenschaft} oder null
	 * @param p
	 *            - Die zu suchende {@link Perspektive} oder null
	 * @param l
	 *            - Die zu suchende {@link Lehrveranstaltung} der null
	 * @param name
	 *            - Den zu suchenden Namen
	 * @param von
	 *            - Von {@link LocalDate}
	 * @param bis
	 *            - Bis {@link LocalDate}
	 * @param s
	 *            - Die zu suchende {@link Skala}
	 * @return Eine {@link List} mit allen {@link Fragebogen}
	 */
	public List<Fragebogen> getFragebogenFor(Eigenschaft e, Perspektive p, Lehrveranstaltung l, String name, LocalDate von, LocalDate bis, Skala s) {
		Date vonDate = von != null ? Date.from(von.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;
		Date bisDate = bis != null ? Date.from(bis.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;

		return fragebogenDAO.getFrageboegenFor(e, p, l, name, vonDate, bisDate, s);
	}

	/**
	 * Erzeugt ein {@link XMLGregorianCalendar} aus einem {@link Date}
	 * 
	 * @param d
	 *            - Das zu verwendende {@link Date}
	 * @return Das {@link XMLGregorianCalendar}-Object.
	 * @throws Exception
	 */
	private XMLGregorianCalendar createXMLGregorienDate(Date d) throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

	/**
	 * Erzeugt ein XML des {@link Fragebogen}s fuer den export zu CORE.
	 * 
	 * @param f
	 *            - Der zu exportierende {@link Fragebogen}
	 * @return Das XML als {@link String}
	 * @throws Exception
	 */
	public String exportQuestionarieToCore(Fragebogen f, File file) throws Exception {
		f = fragebogenDAO.findById(f.getId());

		Questionarie q = xmlFactory.createQuestionarie();

		q.setId(getIDWithStandort(f.getId()));

		q.setCreationDate(createXMLGregorienDate(f.getErstellungsDatum()));
		q.setScale(createScaleType(f.getSkala()));
		q.setProperty(createPropertieType(f.getEigenschaft()));
		q.setPerspective(createPerspectiveType(f.getPerspektive()));
		q.setCourse(createCourseType(f.getLehrveranstaltung()));
		q.setQuestions(createQuestions(f.getFrageFragebogen()));
		q.setItems(createItems(f.getItems(), f));

		JAXBContext jaxbContext = JAXBContext.newInstance(Questionarie.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// jaxbMarshaller.marshal(customer, file);
		jaxbMarshaller.marshal(q, file);

		return null;
	}

	/**
	 * Erzeugt ein {@link ItemType} aus einem {@link Item}
	 * 
	 * @param items
	 *            - {@link List} mit {@link Item}s
	 * @param fb
	 *            - {@link Fragebogen} der Items.
	 * @return Der erzeugte {@link ItemsType}
	 */
	private ItemsType createItems(List<Item> items, Fragebogen fb) {
		ItemsType result = xmlFactory.createItemsType();

		for (Item i : items) {
			ItemType it = xmlFactory.createItemType();
			it.setId(getIDWithStandort(i.getId()));
			it.setName(i.getName());
			it.setQuestion(i.getFrage());

			List<Bewertung> currentBewertungen = new ArrayList<>();

			for (Bewertung b : i.getBewertungen()) {
				if (b.getFragebogen().equals(fb)) {
					currentBewertungen.add(b);
				}
			}

			it.setArea(createArea(i.getBereich()));

			it.setEvaluations(createEvaluation(currentBewertungen));
			it.setPerspectives(createPerspectiveType(i.getPerspektiven()));
			it.setProperties(createPropertieType(i.getEigenschaften()));
			result.getItem().add(it);

		}

		return result;
	}

	/**
	 * Erzeugt einen {@link AreaType} aus einem {@link Bereich}
	 * 
	 * @param b
	 *            - Der zu verwendende {@link Bereich}
	 * @return Der erzeugte {@link AreaType}
	 */
	private AreaType createArea(Bereich b) {
		AreaType a = xmlFactory.createAreaType();
		a.setId(getIDWithStandort(b.getId()));
		a.setName(b.getName());
		a.setSphereActivity(createSphereActivity(b.getHandlungsfeld()));
		return a;
	}

	/**
	 * Erzeugt eine {@link SphereActivityType} aus einem {@link Handlungsfeld}
	 * 
	 * @param h
	 *            - Das zu verwendende {@link Handlungsfeld}
	 * @return Das erzeugte {@link SphereActivityType}-Object.
	 */
	private SphereActivityType createSphereActivity(Handlungsfeld h) {
		SphereActivityType sphere = xmlFactory.createSphereActivityType();
		sphere.setId(getIDWithStandort(h.getId()));
		sphere.setName(h.getName());
		return sphere;
	}

	/**
	 * Erzeugt eine {@link QuestionsType} aus den uebergebenen {@link Frage_Fragebogen}
	 * 
	 * @param fragen
	 *            - Die zu verwendenden {@link Frage_Fragebogen}
	 * @return Das erzeugte {@link QuestionsType}-Object.
	 */
	private QuestionsType createQuestions(List<Frage_Fragebogen> fragen) {

		QuestionsType result = xmlFactory.createQuestionsType();

		for (Frage_Fragebogen f : fragen) {
			QuestionType q = xmlFactory.createQuestionType();
			q.setId(getIDWithStandort(f.getFrage().getId()));
			q.setName(f.getFrage().getName());
			q.setText(f.getFrage().getText());
			q.setScale(createScaleType(f.getFrage().getSkala()));

			List<Bewertung> currentBewertungen = new ArrayList<>();

			for (Bewertung b : f.getFrage().getBewertungen()) {
				if (b.getFragebogen().equals(f.getFragebogen())) {
					currentBewertungen.add(b);
				}
			}

			q.setEvaluations(createEvaluation(currentBewertungen));
			result.getQuestion().add(q);

		}

		return result;
	}

	/**
	 * Erzeugt einen {@link EvaluationsType} aus den uebergebenen {@link Bewertung}en
	 * 
	 * @param bewertungen
	 *            - Die zu verwendenden {@link Bewertung}
	 * @return Das erzeugte {@link EvaluationsType}-Object.
	 */
	private EvaluationsType createEvaluation(List<Bewertung> bewertungen) {
		EvaluationsType result = xmlFactory.createEvaluationsType();

		for (Bewertung f : bewertungen) {
			EvaluationType q = xmlFactory.createEvaluationType();
			q.setId(getIDWithStandort(f.getId()));
			q.setRawid(f.getZeilenid());
			q.setSource(f.getQuelle());
			q.setValue(f.getWert());
			q.setWave(f.getWelle());
			q.setDate(f.getZeit());

			// q.getEvaluations().addAll();

			result.getEvaluation().add(q);
		}

		return result;
	}

	/**
	 * Erzeugt einen {@link CourseType} aus der uebergebenen {@link Lehrveranstaltung}
	 * 
	 * @param l
	 *            - DIe zu verwendende {@link Lehrveranstaltung}
	 * @return Das erzeugte {@link CourseType}-Object.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private CourseType createCourseType(Lehrveranstaltung l) throws Exception {
		CourseType st = xmlFactory.createCourseType();
		st.setId(getIDWithStandort(l.getId()));
		st.setInstructor(l.getDozent());

		if (l.getSemester().equals(de.hscoburg.evelin.secat.dao.entity.base.SemesterType.SS)) {
			st.setSemester(SemesterType.SS);
		} else if (l.getSemester().equals(de.hscoburg.evelin.secat.dao.entity.base.SemesterType.WS)) {
			st.setSemester(SemesterType.WS);
		}

		st.setYear(l.getJahr().getYear());
		st.setSubject(createSubject(l.getFach()));

		return st;
	}

	/**
	 * Erzeugt ein {@link SubjectType} aus einem {@link Fach}
	 * 
	 * @param l
	 *            - Das zu verwendende {@link Fach}
	 * @return Das erzeugte {@link SubjectType}-Object.
	 * @throws Exception
	 */
	private SubjectType createSubject(Fach l) throws Exception {
		SubjectType st = xmlFactory.createSubjectType();
		st.setId(getIDWithStandort(l.getId()));
		st.setName(l.getName());
		st.setLocation(einstellungDAO.findByName(EinstellungenType.STANDORT).getWert());

		return st;
	}

	/**
	 * Erzeugt ein {@link PerspectivesType} aus den uebergebenen {@link Perspektive}n
	 * 
	 * @param e
	 *            - Die zu verwendenden {@link Perspektive}n
	 * @return Das erzeugte {@link PerspectivesType}-Object.
	 */
	private PerspectivesType createPerspectiveType(List<Perspektive> e) {
		PerspectivesType st = xmlFactory.createPerspectivesType();
		for (Perspektive p : e) {
			st.getPerspective().add(createPerspectiveType(p));
		}

		return st;
	}

	/**
	 * Erzeugt ein {@link PerspectiveType} aus der {@link Perspektive}
	 * 
	 * @param e
	 *            - Die zu verwendende {@link Perspektive}
	 * @return Das erzeugte {@link PerspectiveType}-Object.
	 */
	private PerspectiveType createPerspectiveType(Perspektive e) {
		PerspectiveType st = xmlFactory.createPerspectiveType();
		st.setId(getIDWithStandort(e.getId()));
		st.setName(e.getName());

		return st;
	}

	/**
	 * Erzeugt ein {@link PropertiesType} aus den {@link Eigenschaft}en
	 * 
	 * @param e
	 *            - Die zu verwendenden {@link Eigenschaft}en
	 * @return Das erzeugte {@link PropertiesType}-Object.
	 */
	private PropertiesType createPropertieType(List<Eigenschaft> e) {
		PropertiesType st = xmlFactory.createPropertiesType();
		for (Eigenschaft p : e) {
			st.getProperty().add(createPropertieType(p));
		}

		return st;
	}

	/**
	 * Erzeugt ein {@link PropertyType} aus der uebergebenen {@link Eigenschaft}
	 * 
	 * @param e
	 *            - Die zu verwendende {@link Eigenschaft}
	 * @return Das erzeugte {@link PropertyType}-Object.
	 */
	private PropertyType createPropertieType(Eigenschaft e) {
		PropertyType st = xmlFactory.createPropertyType();
		st.setId(getIDWithStandort(e.getId()));
		st.setName(e.getName());

		return st;
	}

	/**
	 * Erzeugt einen {@link ScaleType} aus der uebergebenen {@link Skala}
	 * 
	 * @param s
	 *            - Die zu verwendende {@link Skala}
	 * @return Das erzeugte {@link ScaleType}-Object.
	 */
	private ScaleType createScaleType(Skala s) {
		ScaleType st = xmlFactory.createScaleType();
		st.setId(getIDWithStandort(s.getId()));
		st.setMaxText(s.getMaxText());
		st.setMinText(s.getMinText());
		st.setName(s.getName());
		st.setOptimum(s.getOptimum());
		st.setOtherAnswer(s.getAndereAntwort());
		st.setRefuseAnswer(s.getVerweigerungsAntwort());
		st.setRows(s.getZeilen());
		st.setSteps(s.getSchritte());
		st.setWeight(s.getSchrittWeite());

		if (s.getType().equals(SkalaType.FREE)) {
			st.setType(ScaleTypeType.FREE);
		} else if (s.getType().equals(SkalaType.MC)) {
			st.setType(ScaleTypeType.MC);
			ChoicesType t1 = xmlFactory.createChoicesType();
			int i = 1;
			for (String value : s.getAuswahl()) {
				ChoiceType t = xmlFactory.createChoiceType();
				t.setId(getIDWithStandort(i++));
				t.setName(value);
				t1.getChoice().add(t);
			}
			st.setChoices(t1);

		} else if (s.getType().equals(SkalaType.DISCRET)) {
			st.setType(ScaleTypeType.DISCRETE);
		}

		return st;
	}

	/**
	 * Speichert im uebergebenen File einen Fragbogen der in QuestorPro importiert werden kann.
	 * 
	 * @param fb
	 *            - {@link Fragebogen}
	 * @param f
	 *            - {@link File}
	 * @throws Exception
	 */
	public void exportFragebogenToQuestorPro(Fragebogen fb, File f) throws Exception {

		FileWriter fw = new FileWriter(f);
		fw.write(generateXMLtoQuestorPro(fb).toString());
		fw.close();

	}

	/**
	 * Erzeugt ein XML fuer den export zu CORE fuer einen Fragebogen
	 * 
	 * @param f
	 *            - Den zu exportierenden {@link Fragebogen}
	 * @return Das erzeugte XML
	 */
	public String generateXMLtoQuestorPro(Fragebogen f) {

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

		addFfragen(blockCount++, mainBlock, FragePosition.TOP, f.getFrageFragebogen());

		for (i = 1; i <= f.getItems().size(); i++) {
			Item item = f.getItems().get(i - 1);

			if (!item.getBereich().equals(currentBereich)) {
				BaseXML innerBlock = new FragenblockXML(item.getBereich().getName(), blockCount++);
				questionCount = 1;
				mainBlock.addChild(innerBlock);
				currentBlock = innerBlock;
				currentBereich = item.getBereich();
			}

			if (f.getSkala().getType().equals(SkalaType.DISCRET)) {
				currentBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(f, item), questionCount++, f.getSkala().getSchrittWeite(), f.getSkala()
						.getSchritte(), f.getSkala().getOptimum(), f.getSkala().getMinText(), f.getSkala().getMaxText(), item.getFrage()));
			} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
				currentBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(f, item), item.getFrage(), questionCount++, f.getSkala().getZeilen()));

			} else if (f.getSkala().getType().equals(SkalaType.MC)) {
				currentBlock.addChild(new MultipleChoicefrageXML(f.getSkala().getAndereAntwort(), f.getSkala().getSchrittWeite(), item.getFrage(), f.getSkala()
						.getAuswahl(), BaseXML.generateUniqueId(f, item), questionCount++, f.getSkala().getVerweigerungsAntwort()));

			}

		}
		addFfragen(blockCount++, mainBlock, FragePosition.BOTTOM, f.getFrageFragebogen());
		// FreitextfrageXML ftf = new FreitextfrageXML(BaseXML.generateUniqueId(f, new Frage(99999), "Bla?", i + 1, 10);

		// block.addChild(ftf);

		f.setExportiert(true);
		fragebogenDAO.merge(f);

		return fXML.generateXML();
	}

	/**
	 * Fuegt einem {@link BaseXML} Fragen als Kind-Elemente hinzu.
	 * 
	 * @param useBlockCount
	 *            - Aktueller Blockcount
	 * @param node
	 *            - Element an den die Fragen angehaengt werden sollen
	 * @param position
	 *            - Bestimmt ob TOP oder BOTTOM Fragen beruecksichtig werden
	 * @param fragen
	 *            - Die Fragen die hinzugefuegt werden sollen
	 */
	private void addFfragen(int useBlockCount, BaseXML node, FragePosition position, List<Frage_Fragebogen> fragen) {

		BaseXML innerBlock = new FragenblockXML("", useBlockCount);

		int questionCount = 1;
		for (Frage_Fragebogen cf : fragen != null ? fragen : new ArrayList<Frage_Fragebogen>()) {
			Frage f = cf.getFrage();
			if (cf.getPosition().equals(position)) {
				if (f.getSkala().getType().equals(SkalaType.DISCRET)) {
					innerBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(cf.getFragebogen(), f), questionCount++, f.getSkala().getSchrittWeite(),
							f.getSkala().getSchritte(), f.getSkala().getOptimum(), f.getSkala().getMinText(), f.getSkala().getMaxText(), f.getText()));

				} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
					innerBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(cf.getFragebogen(), f), f.getText(), questionCount++, f.getSkala()
							.getZeilen()));

				} else if (f.getSkala().getType().equals(SkalaType.MC)) {
					innerBlock.addChild(new MultipleChoicefrageXML(f.getSkala().getAndereAntwort(), f.getSkala().getSchrittWeite(), f.getText(), f.getSkala()
							.getAuswahl(), BaseXML.generateUniqueId(cf.getFragebogen(), f), questionCount++, f.getSkala().getVerweigerungsAntwort()));

				}
			}
		}
		if (innerBlock.getChildSize() > 0) {
			node.addChild(innerBlock);
		}
	}

	/**
	 * Comperator zum sortieren der Items nach Bereich
	 * 
	 * @author zuch1000
	 * 
	 */
	private class ItemComparator implements Comparator<Item> {
		public int compare(Item a, Item b) {
			// int dateComparison = a.getBereich().compareTo(b.date);
			return a.getBereich().getName().compareTo(b.getBereich().getName());
		}
	}

	public void addFragebogen(String name, List<Item> itemList, Perspektive p, Eigenschaft e, Skala s, Lehrveranstaltung l, List<Frage> fragenList,
			FragePosition positionFrage) {
		Fragebogen f = new Fragebogen();
		fragebogenDAO.persist(f);
		f.setName(name);
		f.setItems(itemList);
		f.setPerspektive(p);
		f.setEigenschaft(e);
		f.setSkala(s);
		f.setLehrveranstaltung(l);
		f.setExportiert(false);
		f.setErstellungsDatum(new Date());

		fragebogenDAO.merge(f);
		for (Frage frage : fragenList) {

			Frage_Fragebogen frageFragebogen = new Frage_Fragebogen();
			frageFragebogen.setPosition(positionFrage);
			frageFragebogen.setFrage(frage);
			f.addFrageFragebogen(frageFragebogen);
			frageFragebogenDAO.persist(frageFragebogen);
		}
		fragebogenDAO.merge(f);

		for (Item item : itemList) {
			item.addFragebogen(f);
			itemDAO.merge(item);
		}

	}

	public void editFragebogen(Fragebogen edit, String name, List<Item> itemList, Perspektive p, Eigenschaft e, Skala s, Lehrveranstaltung l,
			List<Frage> fragenList, List<Frage> fragenToRemove, List<Item> itemsToRemove, FragePosition positionFrage) {

		edit.setName(name);
		edit.setItems(itemList);
		edit.setPerspektive(p);
		edit.setEigenschaft(e);
		edit.setSkala(s);
		edit.setLehrveranstaltung(l);
		edit.setExportiert(false);
		edit.setErstellungsDatum(new Date());

		ArrayList<Frage> fragenExist = new ArrayList<Frage>();
		for (Frage_Fragebogen frage_fragebogen : edit.getFrageFragebogen()) {
			fragenExist.add(frage_fragebogen.getFrage());
		}

		if (fragenToRemove != null) {
			for (Frage frage : fragenToRemove) {
				for (Frage_Fragebogen frage_fragebogen : edit.getFrageFragebogen()) {
					if (frage_fragebogen.getFrage().equals(frage)) {
						fragenExist.remove(frage);
						frage_fragebogen.setFragebogen(null);
						frageFragebogenDAO.merge(frage_fragebogen);
					}
				}
			}
		}

		for (Frage frage : fragenList) {

			if (!fragenExist.contains(frage) || fragenExist.isEmpty()) {
				Frage_Fragebogen frageFragebogen = new Frage_Fragebogen();
				frageFragebogen.setPosition(positionFrage);
				frageFragebogen.setFrage(frage);
				edit.addFrageFragebogen(frageFragebogen);
				frageFragebogenDAO.persist(frageFragebogen);
				fragebogenDAO.merge(edit);
			} else {
				for (Frage_Fragebogen frageFragebogen : edit.getFrageFragebogen()) {
					if (frageFragebogen.getFrage().equals(frage)) {
						frageFragebogen.setPosition(positionFrage);
						frageFragebogenDAO.merge(frageFragebogen);
					}

				}

			}

		}
		ArrayList<Fragebogen> itemFb = new ArrayList<Fragebogen>();
		itemFb.add(edit);

		for (Item item : itemList) {
			if (!itemsToRemove.contains(item)) {
				item.setFrageboegen(itemFb);
				itemDAO.merge(item);
			}
		}

		for (Item item : itemsToRemove) {
			ArrayList<Fragebogen> fbs = new ArrayList<Fragebogen>();
			for (Fragebogen f : item.getFrageboegen()) {
				if (!edit.equals(f)) {
					fbs.add(f);
				}
				item.setFrageboegen(fbs);
				itemDAO.merge(item);
			}

		}

	}

}
