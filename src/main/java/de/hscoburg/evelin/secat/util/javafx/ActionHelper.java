package de.hscoburg.evelin.secat.util.javafx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

/**
 * Klasse zum setzen einer Action auf einen entsprechenden ActionListener
 * 
 * @author zuch1000
 * 
 */
public class ActionHelper {

	/**
	 * Methode setzt de uebergebenen EventHandler auf den uebergebenen Button und fuegt zusaetzlich einen Enter-Key-Listener hinzu.
	 * 
	 * @param handler
	 *            {@link EventHandler} - Das auszuführende Action-Event
	 * @param button
	 *            {@link Button} - Den Button auf den die Events registriert werden sollen
	 */
	public static void setActionToButton(EventHandler<ActionEvent> handler, final Button button) {
		setActionToButton(handler, button, false);
	}

	/**
	 * Methode setzt de uebergebenen EventHandler auf den uebergebenen Button und fuegt zusaetzlich einen Enter-Key-Listener hinzu.
	 * 
	 * @param handler
	 *            {@link EventHandler} - Das auszuführende Action-Event
	 * @param button
	 *            {@link Button} - Den Button auf den die Events registriert werden sollen
	 */
	public static void setActionToButton(EventHandler<ActionEvent> handler, final Button button, boolean useGlobalEnter) {
		button.setOnAction(handler);

		button.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
					button.fire();
				}

			}

		});
		if (useGlobalEnter) {
			button.getParent().addEventHandler(KeyEvent.KEY_RELEASED, new SeCatEventHandle<KeyEvent>() {

				@Override
				public void handleAction(KeyEvent event) throws Exception {
					if (((KeyEvent) event).getCode() == KeyCode.ENTER && !event.isConsumed()) {
						button.fire();
					}

				}
			});
		}
	}

	public static void setEditFor(final ListView<?> view) {
		view.setEditable(true);

		view.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {

			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					if (event.getClickCount() == 2) {

						view.edit(view.getSelectionModel().getSelectedIndex());
					}
				}

			}
		});

		view.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				System.out.println("view action");
				if (((KeyEvent) event).getCode() == KeyCode.ENTER && !event.isConsumed()) {
					view.edit(view.getSelectionModel().getSelectedIndex());

				}

			}
		});
	}

}
