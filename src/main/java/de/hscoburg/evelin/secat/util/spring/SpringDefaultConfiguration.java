package de.hscoburg.evelin.secat.util.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "de.hscoburg.evelin.secat" })
// @TransactionConfiguratio(defaultRollback = false)
public class SpringDefaultConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();

		lcemfb.setDataSource(this.dataSource());
		lcemfb.setPackagesToScan(new String[] { "de.hscoburg.evelin.secat.model" });
		lcemfb.setPersistenceUnitName("PersistenceUnitTest");

		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		lcemfb.setJpaVendorAdapter(va);

		Properties ps = new Properties();
		ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		ps.put("hibernate.hbm2ddl.auto", "create");
		ps.put("hibernate.show_sql", "true");
		ps.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		// ps.put("hibernate.connection.autocommit", "true");
		// ps.put("showSql", "true");
		lcemfb.setJpaProperties(ps);

		lcemfb.afterPropertiesSet();

		return lcemfb;

	}

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();

		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/secat");

		ds.setUsername("secat");
		ds.setPassword("secat");

		Properties ps = new Properties();
		// ps.put("initialSize", "1");
		// ps.put("maxActive", "5");
		// ps.put("poolPreparedStatements", "true");
		// ps.put("maxOpenPreparedStatements", "10");

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
