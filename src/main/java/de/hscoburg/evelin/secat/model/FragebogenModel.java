package de.hscoburg.evelin.secat.model;

import java.io.File;
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

import de.hscoburg.evelin.secat.controller.xml.BaseXML;
import de.hscoburg.evelin.secat.controller.xml.DiskretefrageXML;
import de.hscoburg.evelin.secat.controller.xml.FragebogenXML;
import de.hscoburg.evelin.secat.controller.xml.FragenblockXML;
import de.hscoburg.evelin.secat.controller.xml.FreitextfrageXML;
import de.hscoburg.evelin.secat.controller.xml.MultipleChoicefrageXML;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
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

@Repository
@Transactional
public class FragebogenModel {

	@Autowired
	private FragebogenDAO fragebogenDAO;
	@Autowired
	private FrageDAO frageDAO;

	private ObjectFactory xmlFactory = new ObjectFactory();

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

	private XMLGregorianCalendar createXMLGregorienDate(Date d) throws Exception {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

	public String exportQuestionarieForCore(Fragebogen f) throws Exception {
		f = fragebogenDAO.findById(f.getId());

		Questionarie q = xmlFactory.createQuestionarie();

		q.setId(f.getId());

		q.setCreationDate(createXMLGregorienDate(f.getErstellungsDatum()));
		q.setScale(createScaleType(f.getSkala()));
		q.setProperty(createPropertieType(f.getEigenschaft()));
		q.setPerspective(createPerspectiveType(f.getPerspektive()));
		q.setCourse(createCourseType(f.getLehrveranstaltung()));
		q.setQuestions(createQuestions(f.getCustomFragen()));
		q.setItems(createItems(f.getItems(), f));

		JAXBContext jaxbContext = JAXBContext.newInstance(Questionarie.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// jaxbMarshaller.marshal(customer, file);
		jaxbMarshaller.marshal(q, new File("test.xml"));

		return null;
	}

	private ItemsType createItems(List<Item> items, Fragebogen fb) {
		ItemsType result = xmlFactory.createItemsType();

		for (Item i : items) {
			ItemType it = xmlFactory.createItemType();
			it.setId(i.getId());
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

	private AreaType createArea(Bereich b) {
		AreaType a = xmlFactory.createAreaType();
		a.setId(b.getId());
		a.setName(b.getName());
		a.setSphereActivity(createSphereActivity(b.getHandlungsfeld()));
		return a;
	}

	private SphereActivityType createSphereActivity(Handlungsfeld h) {
		SphereActivityType sphere = xmlFactory.createSphereActivityType();
		sphere.setId(h.getId());
		sphere.setName(h.getName());
		return sphere;
	}

	private QuestionsType createQuestions(List<Frage_Fragebogen> fragen) {

		QuestionsType result = xmlFactory.createQuestionsType();

		for (Frage_Fragebogen f : fragen) {
			QuestionType q = xmlFactory.createQuestionType();
			q.setId(f.getFrage().getId());
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

	private EvaluationsType createEvaluation(List<Bewertung> bewertungen) {
		EvaluationsType result = xmlFactory.createEvaluationsType();

		for (Bewertung f : bewertungen) {
			EvaluationType q = xmlFactory.createEvaluationType();
			q.setId(f.getId());
			q.setRawid(f.getRawid());
			q.setSource(f.getSource());
			q.setValue(f.getWert());
			q.setWave(f.getWelle());
			q.setDate(f.getZeit());

			// q.getEvaluations().addAll();

			result.getEvaluation().add(q);
		}

		return result;
	}

	private CourseType createCourseType(Lehrveranstaltung l) throws Exception {
		CourseType st = xmlFactory.createCourseType();
		st.setId(l.getId());
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

	private SubjectType createSubject(Fach l) throws Exception {
		SubjectType st = xmlFactory.createSubjectType();
		st.setId(l.getId());
		st.setName(l.getName());

		return st;
	}

	private PerspectivesType createPerspectiveType(List<Perspektive> e) {
		PerspectivesType st = xmlFactory.createPerspectivesType();
		for (Perspektive p : e) {
			st.getPerspective().add(createPerspectiveType(p));
		}

		return st;
	}

	private PerspectiveType createPerspectiveType(Perspektive e) {
		PerspectiveType st = xmlFactory.createPerspectiveType();
		st.setId(e.getId());
		st.setName(e.getName());

		return st;
	}

	private PropertiesType createPropertieType(List<Eigenschaft> e) {
		PropertiesType st = xmlFactory.createPropertiesType();
		for (Eigenschaft p : e) {
			st.getProperty().add(createPropertieType(p));
		}

		return st;
	}

	private PropertyType createPropertieType(Eigenschaft e) {
		PropertyType st = xmlFactory.createPropertyType();
		st.setId(e.getId());
		st.setName(e.getName());

		return st;
	}

	private ScaleType createScaleType(Skala s) {
		ScaleType st = xmlFactory.createScaleType();
		st.setId(s.getId());
		st.setMaxText(s.getMaxText());
		st.setMinText(s.getMinText());
		st.setName(s.getName());
		st.setOptimum(s.getOptimum());
		st.setOtherAnswer(s.getOtherAnswer());
		st.setRefuseAnswer(s.getRefuseAnswer());
		st.setRows(s.getRows());
		st.setSteps(s.getSteps());
		st.setWeight(s.getWeight());

		if (s.getType().equals(SkalaType.FREE)) {
			st.setType(ScaleTypeType.FREE);
		} else if (s.getType().equals(SkalaType.MC)) {
			st.setType(ScaleTypeType.MC);
			ChoicesType t1 = xmlFactory.createChoicesType();
			int i = 1;
			for (String value : s.getChoices()) {
				ChoiceType t = xmlFactory.createChoiceType();
				t.setId(i++);
				t.setName(value);
				t1.getChoice().add(t);
			}
			st.setChoices(t1);

		} else if (s.getType().equals(SkalaType.DISCRET)) {
			st.setType(ScaleTypeType.DISCRETE);
		}

		return st;
	}

	public String generateXMLFor(Fragebogen f) {

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

		addFfragen(blockCount++, mainBlock, FragePosition.TOP, f.getCustomFragen(), f);

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
				currentBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(f, item), questionCount++, f.getSkala().getWeight(), f.getSkala()
						.getSteps(), f.getSkala().getOptimum(), f.getSkala().getMinText(), f.getSkala().getMaxText(), item.getFrage()));
			} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
				currentBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(f, item), item.getFrage(), questionCount++, f.getSkala().getRows()));

			} else if (f.getSkala().getType().equals(SkalaType.MC)) {
				currentBlock.addChild(new MultipleChoicefrageXML(f.getSkala().getOtherAnswer(), f.getSkala().getWeight(), item.getFrage(), f.getSkala()
						.getChoices(), BaseXML.generateUniqueId(f, item), questionCount++, f.getSkala().getRefuseAnswer()));

			}

		}
		addFfragen(blockCount++, mainBlock, FragePosition.BOTTOM, f.getCustomFragen(), f);
		// FreitextfrageXML ftf = new FreitextfrageXML(BaseXML.generateUniqueId(f, new Frage(99999), "Bla?", i + 1, 10);

		// block.addChild(ftf);

		f.setExportiert(true);
		fragebogenDAO.merge(f);

		return fXML.generateXML();
	}

	private void addFfragen(int useBlockCount, BaseXML node, FragePosition position, List<Frage_Fragebogen> fragen, Fragebogen fb) {

		BaseXML innerBlock = new FragenblockXML("", useBlockCount);

		int questionCount = 1;
		for (Frage_Fragebogen cf : fragen != null ? fragen : new ArrayList<Frage_Fragebogen>()) {
			Frage f = cf.getFrage();
			if (cf.getPosition().equals(position)) {
				if (f.getSkala().getType().equals(SkalaType.DISCRET)) {
					innerBlock.addChild(new DiskretefrageXML(BaseXML.generateUniqueId(fb, f), questionCount++, f.getSkala().getWeight(), f.getSkala()
							.getSteps(), f.getSkala().getOptimum(), f.getSkala().getMinText(), f.getSkala().getMaxText(), f.getText()));

				} else if (f.getSkala().getType().equals(SkalaType.FREE)) {
					innerBlock.addChild(new FreitextfrageXML(BaseXML.generateUniqueId(fb, f), f.getText(), questionCount++, f.getSkala().getRows()));

				} else if (f.getSkala().getType().equals(SkalaType.MC)) {
					innerBlock.addChild(new MultipleChoicefrageXML(f.getSkala().getOtherAnswer(), f.getSkala().getWeight(), f.getText(), f.getSkala()
							.getChoices(), BaseXML.generateUniqueId(fb, f), questionCount++, f.getSkala().getRefuseAnswer()));

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
