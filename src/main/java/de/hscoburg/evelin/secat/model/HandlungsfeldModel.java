package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
public class HandlungsfeldModel {

	@Autowired
	private HandlungsfeldDAO handlungsfeldDAO;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private EigenschaftenDAO eigenschaftenDAO;
	@Autowired
	private PerspektiveDAO perspektiveDAO;
	@Autowired
	private SkalaDAO skalaDAO;
	@Autowired
	private FachDAO fachDAO;

	public List<Handlungsfeld> getHandlungsfelderBy(boolean handlungsfeldAktiv, boolean itemAktiv) {
		return getHandlungsfelderBy(handlungsfeldAktiv, itemAktiv, null, null, null, null, null);
	}

	public List<Handlungsfeld> getHandlungsfelderBy(boolean handlungsfeldAktiv, boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld,
			String notizItem, Fach f) {

		// List<Handlungsfeld> dummy = new ArrayList<>();
		//
		// Handlungsfeld e1 = new Handlungsfeld();
		// e1.setName("Clarity");
		//
		// Item i = new Item();
		// i.setAktiv(true);
		// Eigenschaft eig = new Eigenschaft();
		// eig.setName("teamleistung");
		// i.addEigenschaft(eig);
		// Perspektive p1 = new Perspektive();
		// p1.setName("Kunde");
		// i.addPerspektive(p1);
		//
		// i.setName("irgend etwas");
		// e1.addItem(i);
		//
		// i = new Item();
		// i.setAktiv(true);
		// eig = new Eigenschaft();
		// eig.setName("einzelleistung");
		// i.addEigenschaft(eig);
		// p1 = new Perspektive();
		// p1.setName("Dozent");
		// i.addPerspektive(p1);
		//
		// i.setName("irgend etwas²");
		// e1.addItem(i);
		//
		// dummy.add(e1);
		// e1 = new Handlungsfeld();
		// e1.setName("Creativity of solutions");
		//
		// i = new Item();
		// i.setAktiv(true);
		// eig = new Eigenschaft();
		// eig.setName("teamleistung1");
		// i.addEigenschaft(eig);
		// p1 = new Perspektive();
		// p1.setName("Kunde1");
		// i.addPerspektive(p1);
		//
		// i.setName("irgend etwa1s");
		// e1.addItem(i);
		//
		// i = new Item();
		// i.setAktiv(true);
		// eig = new Eigenschaft();
		// eig.setName("einzelleistung1");
		// i.addEigenschaft(eig);
		// p1 = new Perspektive();
		// p1.setName("Dozent1");
		// i.addPerspektive(p1);
		//
		// i.setName("irgend etwas²1");
		// e1.addItem(i);
		//
		// dummy.add(e1);

		return handlungsfeldDAO.getHandlungsfelderBy(handlungsfeldAktiv, itemAktiv, p, e, notizHandlungsfeld, notizItem, f);
	}

	public void persistHandlungsfeld(Handlungsfeld h) {
		handlungsfeldDAO.persist(h);
	}

	public void mergeHandlugsfeld(Handlungsfeld h) {
		handlungsfeldDAO.merge(h);
	}

	public void persistItem(Item i) {
		itemDAO.persist(i);
	}

	public void mergeItem(Item i) {
		itemDAO.merge(i);
	}

	public List<Eigenschaft> getEigenschaften() {

		return eigenschaftenDAO.findAll();
	}

	public List<Perspektive> getPerspektiven() {

		return perspektiveDAO.findAll();
	}

	public List<Skala> getSkalen() {

		return skalaDAO.findAll();
	}

	public List<Fach> getFaecher() {

		return fachDAO.findAll();
	}

}
