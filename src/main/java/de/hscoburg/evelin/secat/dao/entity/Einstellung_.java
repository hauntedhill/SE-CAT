package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity_;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;

/**
 * Staticmetamodel fuer die {@link Einstellung}-Entity
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Einstellung.class)
public class Einstellung_ extends BaseEntity_ {

	public static volatile SingularAttribute<Einstellung, EinstellungenType> name;
	public static volatile SingularAttribute<Einstellung, String> wert;

}
