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

import de.hscoburg.evelin.secat.dao.BereichDAO;
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
public class BereichDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private BereichDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Item item1;

	private Item item2;

	private Eigenschaft e1;

	private Eigenschaft e2;

	private Perspektive p1;

	private Perspektive p2;

	private Lehrveranstaltung l1;

	private Lehrveranstaltung l2;

	private Fach f1;

	private Fach f2;

	private Bereich b1;

	private Fragebogen frag1;

	private Fragebogen frag2;

	private Handlungsfeld h1;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		item1 = new Item();
		item2 = new Item();

		item1.setName("test");

		item2.setName("test2");

		h1 = new Handlungsfeld();
		h1.setName("test");
		em.persist(h1);

		b1 = new Bereich();
		b1.setName("test");
		b1.setHandlungsfeld(h1);
		em.persist(b1);
		item1.setBereich(b1);

		item2.setBereich(b1);

		em.persist(item1);

		em.persist(item2);
		em.flush();

		e1 = new Eigenschaft();
		e1.setName("test");
		em.persist(e1);
		item1.addEigenschaft(e1);

		p1 = new Perspektive();

		p1.setName("test");
		em.persist(p1);

		item1.addPerspektive(p1);

		f1 = new Fach();

		f1.setAktiv(true);
		f1.setName("test");
		em.persist(f1);

		l1 = new Lehrveranstaltung();
		l1.setAktiv(true);
		em.persist(l1);
		f1.addLehrveranstaltung(l1);
		em.merge(f1);
		item1.setAktiv(true);

		frag1 = new Fragebogen();
		frag1.setName("test");
		frag1.setLehrveranstaltung(l1);
		em.persist(frag1);

		item1.addFragebogen(frag1);

		em.merge(item1);

		// /
		e2 = new Eigenschaft();
		e2.setName("test");
		em.persist(e2);
		item2.addEigenschaft(e2);

		p2 = new Perspektive();

		p2.setName("test");
		em.persist(p2);

		item2.addPerspektive(p2);

		f2 = new Fach();

		f2.setAktiv(true);
		f2.setName("test");
		em.persist(f2);

		l2 = new Lehrveranstaltung();
		l2.setAktiv(true);
		em.persist(l2);
		f2.addLehrveranstaltung(l2);
		em.merge(f2);
		item2.setAktiv(false);

		frag2 = new Fragebogen();
		frag2.setName("test");
		frag2.setLehrveranstaltung(l2);
		em.persist(frag2);

		item2.addFragebogen(frag2);

		em.merge(item2);

	}

	@Test
	public void findAll() {

		List<Bereich> result = dao.findAll();

		Assert.assertTrue(result.contains(b1));

	}

	@Test
	public void persist() {
		Bereich e = new Bereich();
		// e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

	@Test
	public void getByCiretria() {

		List<Bereich> result = dao.getBereicheBy(h1, null, null, null, null, null, null);

		Assert.assertTrue(result.contains(b1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getBereicheBy(h1, true, null, null, null, null, null);

		Assert.assertTrue(result.contains(b1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getBereicheBy(h1, null, p1, e1, "", "", null);

		Assert.assertTrue(result.contains(b1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getBereicheBy(h1, null, p1, e1, "123", "123", null);

		// Assert.assertTrue(result.contains(b1));
		Assert.assertTrue(result.size() == 0);

		result = dao.getBereicheBy(h1, null, p1, e1, "", "", f1);

		Assert.assertTrue(result.contains(b1));
		Assert.assertTrue(result.size() == 1);

	}
}
