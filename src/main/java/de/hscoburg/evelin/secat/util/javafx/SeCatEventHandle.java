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

public abstract class SeCatEventHandle<T extends Event> implements EventHandler<T> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SeCatEventHandle.class);

	@Override
	public void handle(final T event) {
		final Scene s = ((Stage) org.controlsfx.tools.Utils.getWindow(null)).getScene();
		final EventHandler<javafx.scene.input.InputEvent> handler = new EventHandler<javafx.scene.input.InputEvent>() {
			@Override
			public void handle(javafx.scene.input.InputEvent mouseEvent) {

				mouseEvent.consume();
			}
		};
		try {

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

	private void showErrorDialog(Exception e) {
		logger.error("Fehler beim verarbeiten eines Events", e);
		Dialogs.create().title("Es ist ein Fehler aufgetreten").masthead("Die Aktion konnte aufgrund eines Fehlers nicht ausgeführt werden.").showException(e);
	}

	public abstract void handleAction(T event) throws Exception;

	public void performBeforeEventsBlocked(T event) throws Exception {

	}

	public void updateUI() {

	}

	class Callback {
		public void complete() {

		}
	}

	class CallbackTask implements Runnable {

		private final Runnable task;

		private final Callback callback;

		CallbackTask(Runnable task, Callback callback) {
			this.task = task;
			this.callback = callback;
		}

		public void run() {
			task.run();
			callback.complete();
		}

	}

}
