package de.hscoburg.evelin.secat.util.javafx;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;

/**
 * Erweitertes Event, das alle Bildschirmeingaben blockiert und die eigentliche Arbeit in einen extra Thread auslagert, um den GUI-Thread
 * nicht zu blockieren.
 * 
 * @author zuch1000
 * 
 * @param <T>
 *            Type des Events
 */
public abstract class SeCatEventHandle<T extends Event> implements EventHandler<T> {

	/**
	 * Instance des Loggers
	 */
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SeCatEventHandle.class);

	/**
	 * Entgegennehmen des eigentlichen Events
	 * 
	 * @param event
	 *            Das original Event
	 */
	@Override
	public final void handle(final T event) {
		final Scene s = ((Stage) org.controlsfx.tools.Utils.getWindow(null)).getScene();
		final EventHandler<javafx.scene.input.InputEvent> handler = new EventHandler<javafx.scene.input.InputEvent>() {
			@Override
			public void handle(javafx.scene.input.InputEvent inputEvent) {

				inputEvent.consume();
			}
		};
		try {

			/**
			 * Methode aufrufen um moegliche arbeiten im GUI-Thread vor dem blockieren (zB Save/Load Dialge) abzuarbeiten.
			 */
			performBeforeEventsBlocked(event);

			Service service = new Service() {
				@Override
				protected javafx.concurrent.Task createTask() {
					return new javafx.concurrent.Task() {
						@Override
						protected Void call() throws Exception {
							try {
								handleAction(event);

								// Thread.sleep(5000);
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											updateUI();
										} catch (Exception e) {
											showErrorDialog(e);
										}
										s.removeEventFilter(javafx.scene.input.InputEvent.ANY, handler);

										// TODO: fix exception handling

									}
								});
							} catch (final Exception e) {
								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										s.removeEventFilter(javafx.scene.input.InputEvent.ANY, handler);

										showErrorDialog(e);

									}
								});

							}
							return null;
						}
					};
				}
			};

			// s.getRoot().disableProperty().bind(service.runningProperty());

			// s.getRoot().g

			s.getRoot().cursorProperty().bind(Bindings.when(service.runningProperty()).then(Cursor.WAIT).otherwise(Cursor.DEFAULT));
			s.addEventFilter(javafx.scene.input.InputEvent.ANY, handler);

			service.restart();

		} catch (final Exception e) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showErrorDialog(e);
				}
			});

		}

	}

	/**
	 * Zeigt einen allgemeinen Fehlerdialog an, sollte beim ausfuehren des Event eine Exception aufgetreten sein.
	 * 
	 * @param e
	 *            {@link Exception} der aufgetretene Fehler
	 */
	private void showErrorDialog(Exception e) {
		logger.error("Fehler beim verarbeiten eines Events", e);
		DialogHelper.showGeneralErrorDialog(e);
	}

	/**
	 * Methode die innerhalb des extra Threads aufgerufen wird, hier sollte die eigentliche Arbeit des Events passieren.
	 * 
	 * @param event
	 *            T Das ausloesende Event
	 * @throws Exception
	 *             Bei einem Fehler
	 */
	public abstract void handleAction(T event) throws Exception;

	/**
	 * Mehtode zum verarbeiten von Aktionen bevor die GUI Blockiert wird und ein neuer Thread gestartet wird.
	 * 
	 * @param event
	 *            T Das ausloesende Event
	 * @throws Exception
	 *             Bei einem Fehler
	 */
	public void performBeforeEventsBlocked(T event) throws Exception {

	}

	/**
	 * Methode die nach dem stoppen des extra Threads aufgeruden wird. Dies dient zum aktualisieren der GUI. Diese Methode wird innerhalb
	 * des JavaFX Threads ausgefuehrt.
	 */
	public void updateUI() {

	}

}
