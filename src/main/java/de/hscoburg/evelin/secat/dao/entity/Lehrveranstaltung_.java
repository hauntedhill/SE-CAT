package de.hscoburg.evelin.secat.dao.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

@StaticMetamodel(Lehrveranstaltung.class)
public class Lehrveranstaltung_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Lehrveranstaltung, String> name;

	public static volatile SingularAttribute<Lehrveranstaltung, Date> jahr;

	public static volatile SingularAttribute<Lehrveranstaltung, Fach> fach;

}
