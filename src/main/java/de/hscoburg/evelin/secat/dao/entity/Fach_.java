package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

/**
 * Staticmetamodel fuer die {@link Fach}-Entity
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Fach.class)
public class Fach_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Fach, String> name;

	public static volatile ListAttribute<Fach, Lehrveranstaltung> lehrveranstaltungen;
}
