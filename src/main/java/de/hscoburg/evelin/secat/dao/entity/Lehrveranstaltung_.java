package de.hscoburg.evelin.secat.dao.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Lehrveranstaltung.class)
public class Lehrveranstaltung_ {

	public static volatile SingularAttribute<Lehrveranstaltung, Integer> id;
	public static volatile SingularAttribute<Lehrveranstaltung, Boolean> aktiv;
	public static volatile SingularAttribute<Lehrveranstaltung, String> name;

	public static volatile SingularAttribute<Lehrveranstaltung, Date> jahr;

	public static volatile SingularAttribute<Lehrveranstaltung, Fach> fach;

}
