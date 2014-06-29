package de.hscoburg.evelin.secat.test.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.test.mock.JPALayerMock;

/**
 * Test fuer den DAO layer.
 * 
 * @author zuch1000
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPALayerMock.class })
@Transactional(propagation = Propagation.REQUIRED)
public class HandlungsfeldDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private HandlungsfeldDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Handlungsfeld handlungsfeld1;

	private Handlungsfeld handlungsfeld2;

	private Item i1;

	private Item i2;

	private Item i3;

	private Eigenschaft e1;

	private Eigenschaft e2;

	private Eigenschaft e3;

	private Perspektive p1;

	private Perspektive p2;

	private Lehrveranstaltung l1;

	private Lehrveranstaltung l2;

	private Fach f1;

	private Fach f2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		handlungsfeld1 = new Handlungsfeld();
		handlungsfeld2 = new Handlungsfeld();

		// handlungsfeld1.setId(1);
		handlungsfeld1.setAktiv(true);
		handlungsfeld1.setName("test");
		handlungsfeld1.setNotiz("test");

		// handlungsfeld2.setId(2);
		handlungsfeld2.setAktiv(true);
		handlungsfeld2.setName("_test2");
		handlungsfeld2.setNotiz("_test2");

		e1 = new Eigenschaft();
		// e1.setId(1);
		e1.setName("teamleistung");

		em.persist(e1);

		e2 = new Eigenschaft();
		// e2.setId(2);
		e2.setName("einzelleistung");

		em.persist(e2);

		e3 = new Eigenschaft();
		// e3.setId(3);
		e3.setName("einzelleistung123");

		em.persist(e3);

		p1 = new Perspektive();
		// p1.setId(1);
		p1.setName("Dozent");
		em.persist(p1);

		p2 = new Perspektive();
		// p2.setId(2);
		p2.setName("Kunde");
		em.persist(p2);

		l1 = new Lehrveranstaltung();
		// l1.setId(1);
		em.persist(l1);

		l2 = new Lehrveranstaltung();
		// l2.setId(2);
		em.persist(l2);

		f1 = new Fach();
		// f1.setId(1);
		f1.setName("SMA");
		em.persist(f1);

		f2 = new Fach();
		// f2.setId(2);
		f2.setName("SWE");
		em.persist(f2);

		i1 = new Item();
		// i1.setId(1);
		i1.setAktiv(true);
		i1.setNotiz("test1");
		i1.setName("Leistungbereit");
		i1.addEigenschaft(e1);
		i1.addEigenschaft(e3);
		i1.addPerspektive(p1);
		i1.addPerspektive(p2);
		em.persist(i1);

		i2 = new Item();
		// i2.setId(2);
		i2.setAktiv(true);
		i2.setNotiz("_test2");
		i2.setName("Wortgewand");
		i2.addEigenschaft(e2);
		i2.addPerspektive(p2);
		em.persist(i2);

		i3 = new Item();
		// i3.setId(3);
		em.persist(i3);

		Fragebogen fb1 = new Fragebogen();
		// fb1.setId(1);

		i1.addFragebogen(fb1);

		fb1.setLehrveranstaltung(l1);
		l1.setFach(f1);
		em.merge(l1);
		em.persist(fb1);

		Fragebogen fb2 = new Fragebogen();
		// fb2.setId(2);

		i1.addFragebogen(fb2);
		i2.addFragebogen(fb2);

		fb2.setLehrveranstaltung(l2);
		l2.setFach(f2);
		em.merge(l2);
		em.persist(fb2);

		em.merge(i1);
		em.merge(i2);

		Bereich b1 = new Bereich();
		Bereich b2 = new Bereich();
		Bereich b3 = new Bereich();

		b1.addItem(i1);

		b2.addItem(i2);
		b3.addItem(i3);

		em.persist(b1);
		em.persist(b2);
		em.persist(b3);

		handlungsfeld1.addBereich(b1);

		handlungsfeld2.addBereich(b2);
		handlungsfeld2.addBereich(b3);

		em.persist(handlungsfeld1);

		em.persist(handlungsfeld2);
		em.flush();

	}

	@Test
	public void findAll() {

		List<Handlungsfeld> result = dao.findAll();

		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
	}

	@Test
	public void persist() {
		Handlungsfeld e = new Handlungsfeld();
		// e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

	@Test
	public void getByCriteria() {
		List<Handlungsfeld> result = null;

		result = dao.getHandlungsfelderBy(true, true, null, null, null, null, null);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 2);

		result = dao.getHandlungsfelderBy(null, null, null, null, null, null, null);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 2);

		result = dao.getHandlungsfelderBy(true, true, null, null, "", "", null);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 2);
		// result = dao.getHandlungsfelderBy(false, false, null, null, null, null, null);
		// Assert.assertTrue(result.contains(handlungsfeld2));
		// Assert.assertTrue(result.size() == 1);
		// result = dao.getHandlungsfelderBy(true, false, null, null, null, null, null);

		// Assert.assertTrue(result.isEmpty());
		// result = dao.getHandlungsfelderBy(false, true, null, null, null, null, null);

		// Assert.assertTrue(result.isEmpty());

		result = dao.getHandlungsfelderBy(true, true, p1, null, null, null, null);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.size() == 1);
		result = dao.getHandlungsfelderBy(true, true, p2, null, null, null, null);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 2);

		result = dao.getHandlungsfelderBy(true, true, null, e1, null, null, null);

		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getHandlungsfelderBy(true, true, null, e2, null, null, null);
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 1);

		result = dao.getHandlungsfelderBy(true, true, null, null, null, null, f1);

		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getHandlungsfelderBy(true, true, null, null, null, null, f2);
		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
		Assert.assertTrue(result.size() == 2);

		result = dao.getHandlungsfelderBy(true, true, null, null, "_tEsT", "", null);
		Assert.assertTrue(result.contains(handlungsfeld2));

		Assert.assertTrue(result.size() == 1);

		result = dao.getHandlungsfelderBy(true, true, null, null, "", "_tEsT", null);
		Assert.assertTrue(result.contains(handlungsfeld2));

		Assert.assertTrue(result.size() == 1);
	}

}
