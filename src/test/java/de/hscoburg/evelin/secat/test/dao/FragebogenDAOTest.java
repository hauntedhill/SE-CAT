package de.hscoburg.evelin.secat.test.dao;

import java.util.Date;
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

import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
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
public class FragebogenDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private FragebogenDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Fragebogen f1;

	private Fragebogen f2;

	private Eigenschaft e1;

	private Eigenschaft e2;

	private Skala s1;

	private Skala s2;

	private Perspektive p1;

	private Perspektive p2;

	private Lehrveranstaltung l1;

	private Lehrveranstaltung l2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {
		l1 = new Lehrveranstaltung();
		em.persist(l1);

		l2 = new Lehrveranstaltung();
		em.persist(l2);

		e1 = new Eigenschaft();
		em.persist(e1);

		s1 = new Skala();
		em.persist(s1);

		p1 = new Perspektive();
		em.persist(p1);

		e2 = new Eigenschaft();
		em.persist(e2);

		s2 = new Skala();
		em.persist(s2);

		p2 = new Perspektive();
		em.persist(p2);

		f1 = new Fragebogen();

		f1.setArchiviert(false);
		f1.setName("test");
		f1.setErstellungsDatum(new Date());
		f1.setEigenschaft(e1);
		f1.setPerspektive(p1);
		f1.setSkala(s1);
		f1.setLehrveranstaltung(l1);

		f2 = new Fragebogen();
		f2.setArchiviert(true);
		f2.setName("test123");
		f2.setErstellungsDatum(new Date(2014, 11, 10));
		f2.setEigenschaft(e2);
		f2.setPerspektive(p2);
		f2.setSkala(s2);
		f2.setLehrveranstaltung(l2);
		// skala1.setId(1);

		// skala2.setId(2);
		// f2.setName("test2");

		em.persist(f1);

		em.persist(f2);
		em.flush();
	}

	@Test
	public void getCriterie() {
		List<Fragebogen> result = dao.getFrageboegenFor(null, null, null, null, null, null, null, false);
		Assert.assertTrue(result.contains(f1));
		Assert.assertTrue(result.size() == 1);

		result = dao.getFrageboegenFor(e2, p2, l2, "test123", null, null, s2, true);
		Assert.assertTrue(result.contains(f2));
		Assert.assertTrue(result.size() == 1);

		result = dao.getFrageboegenFor(null, null, null, "", null, new Date(), null, false);
		Assert.assertTrue(result.contains(f1));
		Assert.assertTrue(result.size() == 1);
	}

	@Test
	public void findAll() {

		List<Fragebogen> result = dao.findAll();

		Assert.assertTrue(result.contains(f1));
		Assert.assertTrue(result.contains(f2));
	}

	@Test
	public void persist() {
		Fragebogen e = new Fragebogen();
		// e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

}
