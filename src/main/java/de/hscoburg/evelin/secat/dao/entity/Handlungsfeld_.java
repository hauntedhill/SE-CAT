package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

/**
 * Staticmetamodel fuer die {@link Handlungsfeld}-Entity
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Handlungsfeld.class)
public class Handlungsfeld_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Handlungsfeld, String> name;
	public static volatile SingularAttribute<Handlungsfeld, String> notiz;
	public static volatile ListAttribute<Handlungsfeld, Bereich> bereiche;

}
