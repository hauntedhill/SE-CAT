package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

@Repository
public class HandlungsfeldModel {

	public List<Handlungsfeld> getHandlungsfelder(boolean aktiv, Perspektive p, Eigenschaft e, String Notiz, Fach f) {
		List<Handlungsfeld> dummy = new ArrayList<>();

		Handlungsfeld e1 = new Handlungsfeld();
		e1.setName("Clarity");

		Item i = new Item();
		i.setAktiv(true);
		Eigenschaft eig = new Eigenschaft();
		eig.setName("teamleistung");
		i.addEigenschaft(eig);
		Perspektive p1 = new Perspektive();
		p1.setName("Kunde");
		i.addPerspektive(p1);
		// i.setSkala(skala);
		i.setName("irgend etwas");
		e1.addItem(i);

		i = new Item();
		i.setAktiv(true);
		eig = new Eigenschaft();
		eig.setName("einzelleistung");
		i.addEigenschaft(eig);
		p1 = new Perspektive();
		p1.setName("Dozent");
		i.addPerspektive(p1);
		// i.setSkala(skala);
		i.setName("irgend etwas²");
		e1.addItem(i);

		dummy.add(e1);
		e1 = new Handlungsfeld();
		e1.setName("Creativity of solutions");

		i = new Item();
		i.setAktiv(true);
		eig = new Eigenschaft();
		eig.setName("teamleistung1");
		i.addEigenschaft(eig);
		p1 = new Perspektive();
		p1.setName("Kunde1");
		i.addPerspektive(p1);
		// i.setSkala(skala);
		i.setName("irgend etwa1s");
		e1.addItem(i);

		i = new Item();
		i.setAktiv(true);
		eig = new Eigenschaft();
		eig.setName("einzelleistung1");
		i.addEigenschaft(eig);
		p1 = new Perspektive();
		p1.setName("Dozent1");
		i.addPerspektive(p1);
		// i.setSkala(skala);
		i.setName("irgend etwas²1");
		e1.addItem(i);

		dummy.add(e1);

		return dummy;
	}

	public void saveHandlungsfeld(Handlungsfeld h) {

	}

	public void updateHandlugsfeld(Handlungsfeld h) {

	}

	public void saveItem(Item i) {

	}

	public void updateItem(Item i) {

	}

	public List<Eigenschaft> getEigenschaften() {
		List<Eigenschaft> dummy = new ArrayList<>();

		Eigenschaft e = new Eigenschaft();
		e.setName("teamfaehigkeit");
		dummy.add(e);
		e = new Eigenschaft();
		e.setName("einzelleistung");
		dummy.add(e);

		return dummy;
	}

	public List<Perspektive> getPerspektiven() {
		List<Perspektive> dummy = new ArrayList<>();

		Perspektive e = new Perspektive();
		e.setName("Kunde");
		dummy.add(e);
		e = new Perspektive();
		e.setName("Dozent");
		dummy.add(e);

		return dummy;
	}

	public List<Skala> getSkalen() {
		List<Skala> dummy = new ArrayList<>();

		Skala e = new Skala();
		e.setName("1-3");
		dummy.add(e);
		e = new Skala();
		e.setName("gut - schlecht");
		dummy.add(e);

		return dummy;
	}

	public List<Fach> getFaecher() {
		List<Fach> dummy = new ArrayList<>();

		Fach e = new Fach();
		e.setName("SMA");
		dummy.add(e);
		e = new Fach();
		e.setName("SWE");
		dummy.add(e);

		return dummy;
	}

}
