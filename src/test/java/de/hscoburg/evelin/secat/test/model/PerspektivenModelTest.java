package de.hscoburg.evelin.secat.test.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.model.PerspektivenModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;
import edu.emory.mathcs.backport.java.util.Arrays;

public class PerspektivenModelTest extends BaseModelTest {

	private static PerspektivenModel model = new PerspektivenModel();

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter() {
		model.savePerspektive("");
	}

	@Test(expected = NullPointerException.class)
	public void saveNPE() {
		model.savePerspektive(null);
	}

	@Test
	public void saveEigenschaft() {
		model.savePerspektive("test");

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Perspektive) persistObjects.get(0)).getName().equals("test"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateWrongParameter() {

		Perspektive e = new Perspektive();
		e.setName("");

		model.updatePerspektive(e);
	}

	@Test(expected = NullPointerException.class)
	public void updateNPE() {
		model.updatePerspektive(null);
	}

	@Test
	public void updateNoDependencies() {
		updatePerspektive1.setName("test1");
		model.updatePerspektive(updatePerspektive1);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Perspektive) mergedObjects.get(0)).getName().equals("test1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked1() {
		updatePerspektive2.setName("test1");
		model.updatePerspektive(updatePerspektive2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked2() {
		updatePerspektive3.setName("test1");
		model.updatePerspektive(updatePerspektive3);

	}

	@Test
	public void update2() {
		updatePerspektive4.setName("test1");
		model.updatePerspektive(updatePerspektive4);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Perspektive) mergedObjects.get(0)).getName().equals("test1"));

	}

	private static PerspektiveDAO perspektivenDAO;

	private static Perspektive updatePerspektive1;

	private static Perspektive updatePerspektive2;

	private static Perspektive updatePerspektive3;

	private static Perspektive updatePerspektive4;

	@BeforeClass
	public static void setup() throws Exception {
		perspektivenDAO = Mockito.mock(PerspektiveDAO.class);

		updatePerspektive1 = new Perspektive();
		updatePerspektive1.setId(1);
		updatePerspektive1.setName("test");

		Fragebogen f1 = new Fragebogen();
		f1.setExportiertQuestorPro(false);

		Fragebogen f2 = new Fragebogen();
		f2.setExportiertQuestorPro(true);

		updatePerspektive2 = new Perspektive();
		updatePerspektive2.setId(2);
		updatePerspektive2.setName("asdf");

		updatePerspektive2.setItems(Arrays.asList(new Item[] { new Item() }));

		updatePerspektive2.setFrageboegen((Arrays.asList(new Fragebogen[] { f1, f2 })));

		updatePerspektive3 = new Perspektive();
		updatePerspektive3.setId(3);
		updatePerspektive3.setName("asdf");

		Item i1 = new Item();

		i1.addFragebogen(f1);
		i1.addFragebogen(f2);

		updatePerspektive3.setItems(Arrays.asList(new Item[] { i1 }));

		updatePerspektive4 = new Perspektive();
		updatePerspektive4.setId(4);
		updatePerspektive4.setName("asdf");

		updatePerspektive4.setItems(Arrays.asList(new Item[] { new Item() }));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).persist(Mockito.any(Perspektive.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).merge(Mockito.any(Perspektive.class));

		Mockito.when(perspektivenDAO.findById(updatePerspektive1.getId())).thenReturn(updatePerspektive1);

		Mockito.when(perspektivenDAO.findById(updatePerspektive2.getId())).thenReturn(updatePerspektive2);

		Mockito.when(perspektivenDAO.findById(updatePerspektive3.getId())).thenReturn(updatePerspektive3);

		Mockito.when(perspektivenDAO.findById(updatePerspektive4.getId())).thenReturn(updatePerspektive4);

		setValueToField(perspektivenDAO, "perspektivenDAO", model);
	}

}
