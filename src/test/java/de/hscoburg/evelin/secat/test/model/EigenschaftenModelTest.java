package de.hscoburg.evelin.secat.test.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;
import edu.emory.mathcs.backport.java.util.Arrays;

public class EigenschaftenModelTest extends BaseModelTest {

	private static EigenschaftenModel model = new EigenschaftenModel();

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter() {
		model.saveEigenschaft("");
	}

	@Test(expected = NullPointerException.class)
	public void saveNPE() {
		model.saveEigenschaft(null);
	}

	@Test
	public void saveEigenschaft() {
		model.saveEigenschaft("test");

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Eigenschaft) persistObjects.get(0)).getName().equals("test"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateWrongParameter() {

		Eigenschaft e = new Eigenschaft();
		e.setName("");

		model.updateEigenschaft(e);
	}

	@Test(expected = NullPointerException.class)
	public void updateNPE() {
		model.updateEigenschaft(null);
	}

	@Test
	public void updateNoDependencies() {
		updateEigenschaft1.setName("test1");
		model.updateEigenschaft(updateEigenschaft1);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Eigenschaft) mergedObjects.get(0)).getName().equals("test1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked1() {
		updateEigenschaft2.setName("test1");
		model.updateEigenschaft(updateEigenschaft2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked2() {
		updateEigenschaft3.setName("test1");
		model.updateEigenschaft(updateEigenschaft3);

	}

	@Test
	public void update2() {
		updateEigenschaft4.setName("test1");
		model.updateEigenschaft(updateEigenschaft4);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Eigenschaft) mergedObjects.get(0)).getName().equals("test1"));

	}

	private static EigenschaftenDAO eigenschaftenDAO;

	private static Eigenschaft updateEigenschaft1;

	private static Eigenschaft updateEigenschaft2;

	private static Eigenschaft updateEigenschaft3;

	private static Eigenschaft updateEigenschaft4;

	@BeforeClass
	public static void setup() throws Exception {
		eigenschaftenDAO = Mockito.mock(EigenschaftenDAO.class);

		updateEigenschaft1 = new Eigenschaft();
		updateEigenschaft1.setId(1);
		updateEigenschaft1.setName("test");

		Fragebogen f1 = new Fragebogen();
		f1.setExportiertQuestorPro(false);

		Fragebogen f2 = new Fragebogen();
		f2.setExportiertQuestorPro(true);

		updateEigenschaft2 = new Eigenschaft();
		updateEigenschaft2.setId(2);
		updateEigenschaft2.setName("asdf");

		updateEigenschaft2.setItems(Arrays.asList(new Item[] { new Item() }));

		updateEigenschaft2.setFrageboegen((Arrays.asList(new Fragebogen[] { f1, f2 })));

		updateEigenschaft3 = new Eigenschaft();
		updateEigenschaft3.setId(3);
		updateEigenschaft3.setName("asdf");

		Item i1 = new Item();

		i1.addFragebogen(f1);
		i1.addFragebogen(f2);

		updateEigenschaft3.setItems(Arrays.asList(new Item[] { i1 }));

		updateEigenschaft4 = new Eigenschaft();
		updateEigenschaft4.setId(4);
		updateEigenschaft4.setName("asdf");

		updateEigenschaft4.setItems(Arrays.asList(new Item[] { new Item() }));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(eigenschaftenDAO).persist(Mockito.any(Eigenschaft.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(eigenschaftenDAO).merge(Mockito.any(Eigenschaft.class));

		Mockito.when(eigenschaftenDAO.findById(updateEigenschaft1.getId())).thenReturn(updateEigenschaft1);

		Mockito.when(eigenschaftenDAO.findById(updateEigenschaft2.getId())).thenReturn(updateEigenschaft2);

		Mockito.when(eigenschaftenDAO.findById(updateEigenschaft3.getId())).thenReturn(updateEigenschaft3);

		Mockito.when(eigenschaftenDAO.findById(updateEigenschaft4.getId())).thenReturn(updateEigenschaft4);

		setValueToField(eigenschaftenDAO, "eigenschaftenDAO", model);
	}

}
