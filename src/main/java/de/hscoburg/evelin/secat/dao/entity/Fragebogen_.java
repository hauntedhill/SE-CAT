package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Fragebogen.class)
public class Fragebogen_ {

	public static volatile SingularAttribute<Fragebogen, Integer> id;

	public static volatile ListAttribute<Fragebogen, Bewertung> bewertungen;

	public static volatile ListAttribute<Fragebogen, Item> items;

	public static volatile ListAttribute<Fragebogen, Student> studenten;

	public static volatile SingularAttribute<Fragebogen, Lehrveranstaltung> lehrveranstaltung;
}
