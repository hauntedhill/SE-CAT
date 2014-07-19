package de.hscoburg.evelin.secat.test.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.model.FragenModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;

public class FragenModelTest extends BaseModelTest {

	private static FragenModel model = new FragenModel();

	@Test(expected = IllegalArgumentException.class)
	public void save1() {

		model.saveFrage(null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save2() {

		model.saveFrage("", null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save3() {

		model.saveFrage("", "", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save4() {

		model.saveFrage("123", "", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save5() {

		model.saveFrage("123", "123", null);
	}

	@Test
	public void save6() {

		model.saveFrage("123", "123", new Skala());

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Frage) persistObjects.get(0)).getName().equals("123"));
	}

	private static FrageDAO perspektivenDAO;

	@BeforeClass
	public static void setup() throws Exception {
		perspektivenDAO = Mockito.mock(FrageDAO.class);
		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).persist(Mockito.any(Frage.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).merge(Mockito.any(Frage.class));

		setValueToField(perspektivenDAO, "frageDAO", model);
	}

}
