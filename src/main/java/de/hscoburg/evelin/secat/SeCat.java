package de.hscoburg.evelin.secat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import de.hscoburg.evelin.secat.util.spring.SpringFXMLLoader;

public class SeCat extends Application {

	public static Stage PRIMARY_STAGE;

	public void start(final Stage primaryStage) {

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

			Parent splash = (Parent) loader.load(SeCat.class.getResourceAsStream("/gui/splashScreen.fxml"));

			Scene sceneSplash = new Scene(splash);
			splashStage.initStyle(StageStyle.UTILITY);
			splashStage.setScene(sceneSplash);
			splashStage.setResizable(false);

			splashStage.setTitle("SE-CAT");

			splashStage.show();
			new Thread(new Runnable() {

				@Override
				public void run() {

					SpringFXMLLoader.getInstance();

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Parent p = (Parent) SpringFXMLLoader.getInstance().load("/gui/Layout.fxml");
							// Parent p = (Parent) SpringFXMLLoader.getInstance().load("/splashScreen.fxml");

							Scene scene = new Scene(p, 600, 480);

							primaryStage.setScene(scene);

							primaryStage.setTitle("SE-CAT");

							primaryStage.setOnShown(new EventHandler<WindowEvent>() {

								@Override
								public void handle(WindowEvent event) {
									splashStage.hide();
									splashStage.close();

								}
							});
							PRIMARY_STAGE = primaryStage;
							primaryStage.show();

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
