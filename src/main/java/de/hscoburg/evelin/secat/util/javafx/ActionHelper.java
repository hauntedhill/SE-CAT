package de.hscoburg.evelin.secat.util.javafx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
		button.setOnAction(handler);

		button.setOnKeyPressed(new SeCatEventHandle<Event>() {

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
					button.fire();
				}

			}

		});
	}

}
