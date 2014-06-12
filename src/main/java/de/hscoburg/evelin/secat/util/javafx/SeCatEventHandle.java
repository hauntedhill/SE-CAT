package de.hscoburg.evelin.secat.util.javafx;

import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;

/**
 * Erweitertes Event, das alle Bildschirmeingaben blockiert und die eigentliche Arbeit in einen extra Thread auslagert, um den GUI-Thread
 * nicht zu blockieren.
 * 
 * @author zuch1000
 * 
 * @param <T>
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
	 */
	@Override
	public final void handle(final T event) {
		final Scene s = ((Stage) org.controlsfx.tools.Utils.getWindow(null)).getScene();
		/**
		 * Eventhandler der alle events konsumiert und diese niht weiterleitet.
		 */
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

			s.setCursor(Cursor.WAIT);

			s.addEventFilter(javafx.scene.input.InputEvent.ANY, handler);

			CallbackTask c = new CallbackTask(new Runnable() {

				@Override
				public void run() {
					try {
						handleAction(event);
					} catch (final Exception e) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								showErrorDialog(e);
								s.removeEventFilter(javafx.scene.input.InputEvent.ANY, handler);
								s.setCursor(Cursor.DEFAULT);
							}
						});

					}

				}
			}, new Callback() {
				@Override
				public void complete() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							updateUI();
							s.removeEventFilter(javafx.scene.input.InputEvent.ANY, handler);
							s.setCursor(Cursor.DEFAULT);
						}
					});
				}
			});

			Thread t = Executors.defaultThreadFactory().newThread(c);
			t.start();
		} catch (Exception e) {

			showErrorDialog(e);
			s.removeEventFilter(javafx.scene.input.InputEvent.ANY, handler);
			s.setCursor(Cursor.DEFAULT);
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
		Dialogs.create().title("Es ist ein Fehler aufgetreten").masthead("Die Aktion konnte aufgrund eines Fehlers nicht ausgeführt werden.").showException(e);
	}

	/**
	 * Methode die innerhalb des extra Threads aufgerufen wird, hier sollte die eigentliche Arbeit des Events passieren.
	 * 
	 * @param event
	 *            T Das ausloesende Event
	 * @throws Exception
	 */
	public abstract void handleAction(T event) throws Exception;

	/**
	 * Mehtode zum verarbeiten von Aktionen bevor die GUI Blockiert wird und ein neuer Thread gestartet wird.
	 * 
	 * @param event
	 *            T Das ausloesende Event
	 * @throws Exception
	 */
	public void performBeforeEventsBlocked(T event) throws Exception {

	}

	/**
	 * Methode die nach dem stoppen des extra Threads aufgeruden wird. Dies dient zum aktualisieren der GUI. Diese Methode wird innerhalb
	 * des JavaFX Threads ausgefuehrt.
	 */
	public void updateUI() {

	}

	/**
	 * Innere Klasse fuer den Callback-Listener fuer die Thread beendigung.
	 * 
	 * @author zuch1000
	 * 
	 */
	private class Callback {
		public void complete() {

		}
	}

	/**
	 * Innere Klasse um einen {@link Runnable} zu starten und ueber dessen Beendigung informiert zu werden
	 * 
	 * @author zuch1000
	 * 
	 */
	private class CallbackTask implements Runnable {

		private final Runnable task;

		private final Callback callback;

		/**
		 * Konstruktor zum erzeugen des Objektes
		 * 
		 * @param task
		 *            - Das {@link Runnable} das ausgefuehrt werden soll.
		 * @param callback
		 *            - Der auszufuehrende Callback nach Beendigung des Threads.
		 */
		public CallbackTask(Runnable task, Callback callback) {
			this.task = task;
			this.callback = callback;
		}

		/**
		 * Run-Methode zum starten des Threads.
		 */
		public final void run() {
			task.run();
			callback.complete();
		}

	}

}
