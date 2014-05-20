package de.hscoburg.evelin.secat.util.javafx;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ActionHelper {

	public static void setActionToButton(EventHandler handler, final Button button) {
		button.setOnAction(handler);

		button.setOnKeyPressed(new SeCatEventHandle<Event>() {

			@Override
			public void handleAction(Event event) {
				if (((KeyEvent) event).getCode() == KeyCode.ENTER)

				{
					button.fire();
				}

			}

		});
	}

}
