package de.hscoburg.evelin.secat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import org.controlsfx.dialog.Dialogs;

import de.hscoburg.evelin.secat.controller.base.LayoutController;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

/**
 * Main-Klasse als Einstiegspunkt der Applikation
 * 
 * @author zuch1000
 * 
 */
public class SeCat extends Application {

	/**
	 * Globale Instance der Primary Stage
	 */
	public static Stage PRIMARY_STAGE;

	/**
	 * Name der Applikation
	 */
	public static String MAIN_STAGE_TITLE = "SE-CAT v. ";

	/**
	 * Methode fuehrt das initialisieren der Applikation durch und laed die default Seite.
	 * 
	 * @param primarStage
	 *            - PrimaryStage
	 */
	public void start(final Stage primaryStage) {
		PRIMARY_STAGE = primaryStage;

		try {

			MAIN_STAGE_TITLE = MAIN_STAGE_TITLE + new BufferedReader(new InputStreamReader(SeCat.class.getResourceAsStream("/version.txt"))).readLine();

			FXMLLoader loader = new FXMLLoader();

			final Stage splashStage = new Stage();

			Parent splash = (Parent) loader.load(SeCat.class.getResourceAsStream(LayoutController.SPLASHSCREEN_FXML));

			Scene sceneSplash = new Scene(splash);
			splashStage.initStyle(StageStyle.UTILITY);
			splashStage.setScene(sceneSplash);
			splashStage.setResizable(false);
			splashStage.initOwner(primaryStage);

			splashStage.setTitle(MAIN_STAGE_TITLE);

			splashStage.show();
			Executors.defaultThreadFactory().newThread(new Runnable() {

				@Override
				public void run() {

					SpringFXMLLoader.getInstance();

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								SpringFXMLLoader.getInstance().loadInNewScene(LayoutController.LAYOUT_FXML, primaryStage, null);
								// Parent p = (Parent) SpringFXMLLoader.getInstance().load("/splashScreen.fxml");

								primaryStage.getIcons().add(new Image("/image/icons/dvi.png"));

								primaryStage.setOnShown(new EventHandler<WindowEvent>() {

									@Override
									public void handle(WindowEvent event) {
										splashStage.hide();
										splashStage.close();

									}
								});
								primaryStage.show();

							} catch (Exception e) {
								splashStage.hide();
								splashStage.close();
								Dialogs.create().title(SeCatResourceBundle.getInstance().getString("scene.error.title"))
										.masthead(SeCatResourceBundle.getInstance().getString("scene.error.text")).showException(e);

							}

						}
					});

				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Einstiegspunkt der Applikation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
