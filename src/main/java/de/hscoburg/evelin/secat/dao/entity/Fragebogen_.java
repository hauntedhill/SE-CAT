package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity_;

@StaticMetamodel(Fragebogen.class)
public class Fragebogen_ extends BaseEntity_ {

	public static volatile ListAttribute<Fragebogen, Bewertung> bewertungen;

	public static volatile ListAttribute<Fragebogen, Item> items;

	public static volatile ListAttribute<Fragebogen, Student> studenten;

	public static volatile SingularAttribute<Fragebogen, Lehrveranstaltung> lehrveranstaltung;

	public static volatile SingularAttribute<Fragebogen, Skala> skala;

}
