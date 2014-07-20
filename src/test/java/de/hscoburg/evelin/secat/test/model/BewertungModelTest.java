package de.hscoburg.evelin.secat.test.model;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.BewertungDAO;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.model.BewertungModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;

public class BewertungModelTest extends BaseModelTest {

	private static BewertungModel model = new BewertungModel();

	@Test
	public void import1() throws Exception {
		f1.setExportiertQuestorPro(true);
		int anz = model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_korrekt.csv")));

		Assert.assertTrue(anz == 16);
	}

	@Test(expected = IllegalArgumentException.class)
	public void import2() throws Exception {
		f1.setExportiertQuestorPro(false);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_korrekt.csv")));
	}

	@Test
	public void import3() throws Exception {
		f1.setExportiertQuestorPro(true);
		int anz = model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_empty.csv")));

		Assert.assertTrue(anz == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void import4() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_missing_headers.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import5() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_missing_item.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import6() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_missing_frage.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import7() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_missing_fragebogen.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import8() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_missing_fragebogen2.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import9() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_inkorrekt_frageId.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import10() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_inkorrekt_itemId.csv")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void import11() throws Exception {
		f1.setExportiertQuestorPro(true);
		model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_inkorrekt_id.csv")));
	}

	@Test
	public void import12() throws Exception {
		f1.setExportiertQuestorPro(true);

		Bewertung b1 = new Bewertung();
		Bewertung b2 = new Bewertung();

		List<Bewertung> b = new ArrayList<Bewertung>();
		b.add(b2);
		b.add(b1);

		f1.setBewertungen(b);

		int anz = model.importBewertungen(new InputStreamReader(BewertungModelTest.class.getResourceAsStream("/import_korrekt.csv")));

		Assert.assertTrue(anz == 16);
	}

	@Test
	public void deleteBewertung1() {
		f2.setBewertungen(null);
		model.deleteBewertung(f2);
	}

	@Test
	public void deleteBewertung2() {
		f2.setBewertungen(null);

		Bewertung b1 = new Bewertung();
		Bewertung b2 = new Bewertung();

		List<Bewertung> b = new ArrayList<>();
		b.add(b2);
		b.add(b1);
		f2.setBewertungen(b);

		model.deleteBewertung(f2);

	}

	// @Test(expected = NullPointerException.class)
	// public void saveNPE() {
	// model.savePerspektive(null);
	// }

	private static Fragebogen f1;

	private static Fragebogen f2;

	private static FragebogenDAO fragebogenDAO;

	private static ItemDAO itemDAO;

	private static FrageDAO frageDAO;

	private static BewertungDAO bewertungDAO;

	@BeforeClass
	public static void setup() throws Exception {
		fragebogenDAO = Mockito.mock(FragebogenDAO.class);
		itemDAO = Mockito.mock(ItemDAO.class);
		frageDAO = Mockito.mock(FrageDAO.class);
		bewertungDAO = Mockito.mock(BewertungDAO.class);

		f1 = new Fragebogen();
		f1.setId(1);
		f1.setExportiertQuestorPro(true);

		f2 = new Fragebogen();
		f2.setId(20);

		Frage fra1 = new Frage();
		fra1.setId(1);
		Frage fra2 = new Frage();
		fra2.setId(2);

		Frage_Fragebogen ff1 = new Frage_Fragebogen();

		ff1.setFrage(fra1);

		Frage_Fragebogen ff2 = new Frage_Fragebogen();

		ff2.setFrage(fra2);

		f1.addFrageFragebogen(ff1);
		f1.addFrageFragebogen(ff2);

		Item i1 = new Item();
		i1.setId(1);

		Item i2 = new Item();
		i2.setId(2);

		f1.setItems(Arrays.asList(new Item[] { i1, i2 }));

		Mockito.when(fragebogenDAO.findById(f1.getId())).thenReturn(f1);
		Mockito.when(fragebogenDAO.findById(f2.getId())).thenReturn(f2);

		Mockito.when(frageDAO.findById(fra1.getId())).thenReturn(fra1);
		Mockito.when(frageDAO.findById(fra2.getId())).thenReturn(fra2);

		Mockito.when(itemDAO.findById(i1.getId())).thenReturn(i1);
		Mockito.when(itemDAO.findById(i2.getId())).thenReturn(i2);

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(bewertungDAO).persist(Mockito.any(Bewertung.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(bewertungDAO).merge(Mockito.any(Bewertung.class));

		setValueToField(fragebogenDAO, "fragebogenDAO", model);
		setValueToField(bewertungDAO, "bewertungDAO", model);
		setValueToField(frageDAO, "frageDAO", model);
		setValueToField(itemDAO, "itemDAO", model);
	}

}
