package de.hscoburg.evelin.secat.dao.entity;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

/**
 * Staticmetamodel fuer die {@link Lehrveranstaltung}-Entity
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Lehrveranstaltung.class)
public class Lehrveranstaltung_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Lehrveranstaltung, SemesterType> semester;

	public static volatile SingularAttribute<Lehrveranstaltung, String> dozent;

	public static volatile SingularAttribute<Lehrveranstaltung, Date> jahr;

	public static volatile SingularAttribute<Lehrveranstaltung, Fach> fach;

	public static volatile ListAttribute<Lehrveranstaltung, Fragebogen> frageboegen;

}
