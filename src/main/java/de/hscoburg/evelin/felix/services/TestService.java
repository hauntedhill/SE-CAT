package de.hscoburg.evelin.felix.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.felix.model.Hauptfeld;

@Service
@Transactional
public class TestService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DataSource ds;

	public void save() {

		Hauptfeld h = new Hauptfeld();

		h.setName("test");

		em.persist(h);

	}

	public void saveDBToFile(File f) {
		java.sql.Connection connection = null;

		Statement st = null;
		try {
			connection = ds.getConnection();
			st = connection.createStatement();
			st.execute("SCRIPT '" + f + "'");
			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void loadDBFromFile(File f) {
		java.sql.Connection connection = null;

		Statement st = null;
		BufferedReader br = null;
		try {
			connection = ds.getConnection();
			st = connection.createStatement();
			st.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
			connection.commit();
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("ReadLine: " + line);
				// if (line.toUpperCase().contains("CREATE USER SA") || line.toUpperCase().contains("CREATE SCHEMA PUBLIC")
				// || line.toUpperCase().contains("GRANT DBA TO SA")) {
				// continue;
				// }
				if (line.toUpperCase().contains("INSERT ") && !line.toUpperCase().contains("INSERT INTO BLOCKS")) {
					st.execute(line);
				}
			}

			connection.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				if (connection != null)
					connection.close();

				if (br != null)
					br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
