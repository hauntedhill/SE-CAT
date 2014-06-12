package de.hscoburg.evelin.secat.dao.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel fuer ein {@link StammdatenEntity}
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(StammdatenEntity.class)
public class StammdatenEntity_ extends BaseEntity_ {
	public static volatile SingularAttribute<StammdatenEntity, Boolean> aktiv;

}
