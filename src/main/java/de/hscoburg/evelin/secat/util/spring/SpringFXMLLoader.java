package de.hscoburg.evelin.secat.util.spring;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import de.hscoburg.evelin.secat.controller.base.BaseController;

public class SpringFXMLLoader {
	private ApplicationContext applicationContext;

	private static Logger logger = LoggerFactory.getLogger(SpringFXMLLoader.class);

	private SpringFXMLLoader() {

		applicationContext = new AnnotationConfigApplicationContext(SpringDefaultConfiguration.class);

	}

	public static SpringFXMLLoader getInstance() {
		return Holder.INSTANCE;
	}

	public Stage loadInNewScene(String url, Stage currentStage, int sizeX, int sizeY, Stage owner) {
		try {
			if (owner != null) {
				currentStage.initModality(Modality.APPLICATION_MODAL);
				currentStage.initOwner(owner);
			}
			InputStream fxmlStream = SpringFXMLLoader.class.getResourceAsStream(url);
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(new Callback<Class<?>, Object>() {

				public Object call(Class<?> clazz) {

					return applicationContext.getBean(clazz);

				}

			});
			Scene scene = new Scene((Parent) loader.load(fxmlStream), sizeX, sizeY);
			Object base = loader.getController();
			if (base instanceof BaseController) {
				((BaseController) base).setCurrentStage(currentStage);
				((BaseController) base).setTitle();
			}

			currentStage.setScene(scene);
			return currentStage;

		} catch (IOException ioException) {
			logger.error("Could not init FXML File", ioException);
			throw new RuntimeException(ioException);

		}
	}

	public Stage loadInNewScene(String url) {

		return loadInNewScene(url, new Stage(), 800, 600, (Stage) org.controlsfx.tools.Utils.getWindow(null));

	}

	public Stage loadInNewScene(String url, Stage currentStage, Stage owner) {

		return loadInNewScene(url, currentStage, 800, 600, owner);

	}

	public Parent load(String url, Stage stage) {
		try {
			InputStream fxmlStream = SpringFXMLLoader.class.getResourceAsStream(url);
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(new Callback<Class<?>, Object>() {

				public Object call(Class<?> clazz) {

					return applicationContext.getBean(clazz);

				}

			});

			Parent result = (Parent) loader.load(fxmlStream);
			Object base = loader.getController();
			if (base instanceof BaseController) {
				((BaseController) base).setCurrentStage(stage);
				((BaseController) base).setTitle();
			}

			return result;

		} catch (IOException ioException) {
			logger.error("Could not init FXML File", ioException);
			throw new RuntimeException(ioException);

		}
	}

	public Parent load(String url) {
		return load(url, (Stage) org.controlsfx.tools.Utils.getWindow(null));
	}

	private static class Holder {
		private static final SpringFXMLLoader INSTANCE = new SpringFXMLLoader();

		private Holder() {
		}
	}

}
