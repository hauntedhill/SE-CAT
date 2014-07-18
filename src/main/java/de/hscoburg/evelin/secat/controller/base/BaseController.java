package de.hscoburg.evelin.secat.controller.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import de.hscoburg.evelin.secat.SeCat;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

/**
 * Abstrakter-Controller zur grundlegenden Steuerung der einzelnen Scenen
 * 
 * @author zuch1000
 * 
 */
public abstract class BaseController implements Initializable {

	/**
	 * Stage der Scene
	 */
	private Stage currentStage;

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public Stage getCurrentStage() {
		return this.currentStage;

	}

	/**
	 * Methode zum initialisieren des Controllers
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	public abstract void initializeController(URL location, ResourceBundle resources);

	/**
	 * Gibt den Namen der Scenen zurueck
	 * 
	 * @return {@link String}
	 */
	public String getSceneName() {

		return SeCatResourceBundle.getInstance().getObject(getKeyForSceneName()).toString();
	}

	/**
	 * Definiert den Key fuer den Namen der Scene
	 * 
	 * @return {@link String}
	 */
	public abstract String getKeyForSceneName();

	/**
	 * Methode die von JavaFX aufgerufen wird.
	 * 
	 * @param location
	 *            Der Pfad zur View
	 * @param resources
	 *            Das verwendete ResourcebUndle
	 */
	@Override
	public final void initialize(URL location, ResourceBundle resources) {

		initializeController(location, resources);

	}

	/**
	 * Setzt den Titles sowie das Icon der Scene in die Titlebar
	 */
	public void setTitle() {

		setTitle("");

	}

	/**
	 * Setzt den Titles sowie das Icon der Scene in die Titlebar
	 * 
	 * @param additionalTitle
	 *            Setzt eine Customtitle zur Scene
	 * 
	 */
	public void setTitle(final String additionalTitle) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				getCurrentStage().setTitle(SeCat.MAIN_STAGE_TITLE + " - " + getSceneName() + (additionalTitle != null ? " " + additionalTitle : ""));
				getCurrentStage().getIcons().add(new Image("/image/icons/dvi.png"));

			}

		});

	}

}
