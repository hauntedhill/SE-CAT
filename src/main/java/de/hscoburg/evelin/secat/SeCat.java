package de.hscoburg.evelin.secat;

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
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

public class SeCat extends Application {

	public static Stage PRIMARY_STAGE;

	public static String MAIN_STAGE_TITLE = "SE-CAT";

	public static void setSubTitle(String title) {
		PRIMARY_STAGE.setTitle(MAIN_STAGE_TITLE + " - " + title);
	}

	public void start(final Stage primaryStage) {
		PRIMARY_STAGE = primaryStage;
		// final ImageView imageView = new ImageView(new Image(Felix.class.getResourceAsStream("/ajax_loader_blue_512.gif")));
		//
		// imageView.setFitHeight(29.0);
		// imageView.setFitWidth(34.0);
		//
		// StackPane glass = new StackPane();
		//
		// glass.getChildren().addAll(imageView);
		// glass.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5);");

		// primaryStage.setScene(new Scene(glass));
		// primaryStage.show();
		try {

			FXMLLoader loader = new FXMLLoader();

			final Stage splashStage = new Stage();

			Parent splash = (Parent) loader.load(SeCat.class.getResourceAsStream(LayoutController.SPLASHSCREEN_FXML));

			Scene sceneSplash = new Scene(splash);
			splashStage.initStyle(StageStyle.UTILITY);
			splashStage.setScene(sceneSplash);
			splashStage.setResizable(false);

			splashStage.setTitle(MAIN_STAGE_TITLE);

			splashStage.show();
			new Thread(new Runnable() {

				@Override
				public void run() {

					SpringFXMLLoader.getInstance();

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							try {
								SpringFXMLLoader.getInstance().loadInNewScene(LayoutController.LAYOUT_FXML, primaryStage);
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
								Dialogs.create().title("Es ist ein Fehler aufgetreten").masthead("Die Anwendung konnte nicht gestartet werden.")
										.showException(e);

							}

						}
					});

				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
