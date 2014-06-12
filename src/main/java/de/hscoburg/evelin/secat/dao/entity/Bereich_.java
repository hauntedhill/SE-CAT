package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity_;

/**
 * Staticmetamodel fuer die {@link Bereich}-Entitie
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Bereich.class)
public class Bereich_ extends BaseEntity_ {

	public static volatile SingularAttribute<Bereich, Handlungsfeld> handlungsfeld;

	public static volatile SingularAttribute<Bereich, String> name;

	public static volatile ListAttribute<Bereich, Item> items;

}
