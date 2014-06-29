package de.hscoburg.evelin.secat.test.mock;

import java.util.Properties;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import de.hscoburg.evelin.secat.dao.BereichDAO;
import de.hscoburg.evelin.secat.dao.BewertungDAO;
import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.EinstellungDAO;
import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.Frage_FragebogenDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.LehrveranstaltungDAO;
import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.SkalaDAO;

/**
 * Configuration zum Mocken der Datenbank. Hierfuer wird eine HSQL Datenbank benutzt und entsprechend initialisiert.
 * 
 * @author zuch1000
 * 
 */
@Configuration
@ComponentScan({ "de.hscoburg.evelin.secat.model" })
public class DAOLayerMock {

	@Bean
	public EigenschaftenDAO getEigenschaftenDAO() {

		return Mockito.mock(EigenschaftenDAO.class, Mockito.withSettings().extraInterfaces(EigenschaftenDAO.class));

	}

	@Bean
	public BereichDAO getBereichDAO() {

		return Mockito.mock(BereichDAO.class, Mockito.withSettings().extraInterfaces(BereichDAO.class));

	}

	@Bean
	public BewertungDAO getBewertungDAO() {

		return Mockito.mock(BewertungDAO.class, Mockito.withSettings().extraInterfaces(BewertungDAO.class));

	}

	@Bean
	public EinstellungDAO getEinstellungDAO() {

		return Mockito.mock(EinstellungDAO.class, Mockito.withSettings().extraInterfaces(EinstellungDAO.class));

	}

	@Bean
	public FachDAO getFachDAO() {

		return Mockito.mock(FachDAO.class, Mockito.withSettings().extraInterfaces(FachDAO.class));

	}

	@Bean
	public Frage_FragebogenDAO getFrageFragebogenDAO() {

		return Mockito.mock(Frage_FragebogenDAO.class, Mockito.withSettings().extraInterfaces(Frage_FragebogenDAO.class));

	}

	@Bean
	public FragebogenDAO getFragebogenDAO() {

		return Mockito.mock(FragebogenDAO.class, Mockito.withSettings().extraInterfaces(FragebogenDAO.class));

	}

	@Bean
	public FrageDAO getFrageDAO() {

		return Mockito.mock(FrageDAO.class, Mockito.withSettings().extraInterfaces(FrageDAO.class));

	}

	@Bean
	public HandlungsfeldDAO getHandlungsfeldDAO() {

		return Mockito.mock(HandlungsfeldDAO.class, Mockito.withSettings().extraInterfaces(HandlungsfeldDAO.class));

	}

	@Bean
	public ItemDAO getItemDAO() {

		return Mockito.mock(ItemDAO.class, Mockito.withSettings().extraInterfaces(ItemDAO.class));

	}

	@Bean
	public LehrveranstaltungDAO getLehrveranstaltungDAO() {

		return Mockito.mock(LehrveranstaltungDAO.class, Mockito.withSettings().extraInterfaces(LehrveranstaltungDAO.class));

	}

	@Bean
	public PerspektiveDAO getPerspektiveDAO() {

		return Mockito.mock(PerspektiveDAO.class, Mockito.withSettings().extraInterfaces(PerspektiveDAO.class));

	}

	@Bean
	public SkalaDAO getSkalaDAO() {

		return Mockito.mock(SkalaDAO.class, Mockito.withSettings().extraInterfaces(SkalaDAO.class));

	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();

		lcemfb.setDataSource(this.dataSource());
		lcemfb.setPackagesToScan(new String[] { "de.hscoburg.evelin.secat.dao.entity" });
		lcemfb.setPersistenceUnitName("PersistenceUnitTest");

		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		lcemfb.setJpaVendorAdapter(va);

		Properties ps = new Properties();
		ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		ps.put("hibernate.hbm2ddl.auto", "create");
		// ps.put("hibernate.show_sql", "true");
		// ps.put("hibernate.connection.autocommit", "true");
		// ps.put("showSql", "true");
		lcemfb.setJpaProperties(ps);

		lcemfb.afterPropertiesSet();

		return lcemfb;

	}

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUrl("jdbc:hsqldb:mem:testdb;shutdown=false;hsqldb.write_delay=0");
		ds.setUsername("sa");
		ds.setPassword("");

		Properties ps = new Properties();
		ps.put("initialSize", "1");
		ps.put("maxActive", "5");
		ps.put("poolPreparedStatements", "true");
		ps.put("maxOpenPreparedStatements", "10");

		ds.setConnectionProperties(ps);

		return ds;

	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager tm = new JpaTransactionManager();

		tm.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());

		return tm;

	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
