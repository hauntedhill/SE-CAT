package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Fach.class)
public class Fach_ {
	public static volatile SingularAttribute<Fach, Integer> id;
	public static volatile SingularAttribute<Fach, Boolean> aktiv;
	public static volatile SingularAttribute<Fach, String> name;

	public static volatile SingularAttribute<Fach, Lehrveranstaltung> lehrveranstaltungen;
}
