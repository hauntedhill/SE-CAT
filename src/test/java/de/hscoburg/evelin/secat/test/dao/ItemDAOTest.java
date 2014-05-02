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

import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.entity.Item;
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
public class ItemDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private ItemDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Item item1;

	private Item item2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		item1 = new Item();
		item2 = new Item();

		item1.setId(1);
		item1.setName("test");

		item2.setId(2);
		item2.setName("test2");

		em.persist(item1);

		em.persist(item2);

	}

	@Test
	public void findAll() {

		List<Item> result = dao.findAll();

		Assert.assertTrue(result.contains(item1));
		Assert.assertTrue(result.contains(item2));
	}

	@Test
	public void persist() {
		Item e = new Item();
		e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(3).getId() == 3);
	}

}
