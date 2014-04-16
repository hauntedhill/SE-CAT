package de.hscoburg.evelin.felix.util.spring;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringFXMLLoader {
	private ApplicationContext applicationContext;

	private static Logger logger = LoggerFactory.getLogger(SpringFXMLLoader.class);

	private SpringFXMLLoader() {

		applicationContext = new AnnotationConfigApplicationContext(SpringDefaultConfiguration.class);

	}

	public static SpringFXMLLoader getInstance() {
		return Holder.INSTANCE;
	}

	public Object load(String url) {

		try {
			InputStream fxmlStream = SpringFXMLLoader.class.getResourceAsStream(url);
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(new Callback<Class<?>, Object>() {

				public Object call(Class<?> clazz) {

					return applicationContext.getBean(clazz);

				}

			});

			return loader.load(fxmlStream);

		} catch (IOException ioException) {
			logger.error("Could not init FXML File", ioException);
			throw new RuntimeException(ioException);

		}

	}

	private static class Holder {
		private static final SpringFXMLLoader INSTANCE = new SpringFXMLLoader();

		private Holder() {
		}
	}

}
