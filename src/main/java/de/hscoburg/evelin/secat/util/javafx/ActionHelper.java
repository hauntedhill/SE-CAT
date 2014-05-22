package de.hscoburg.evelin.secat.util.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ActionHelper {

	public static void setActionToButton(EventHandler handler, final Button button) {
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

	public static void setAutoResizeToggleListenerForTitledPanel(final TitledPane searchPanel, final TitledPane dataPanel, final Control container,
			final int heightDiff) {
		searchPanel.expandedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					dataPanel.setLayoutY(235);
					// tablePanel.setPrefHeight(205);
					dataPanel.setPrefHeight(dataPanel.getPrefHeight() - heightDiff);
					// frageboegen.setPrefHeight(181);
					container.setPrefHeight(container.getPrefHeight() - heightDiff);
				} else {
					dataPanel.setLayoutY(60);
					// tablePanel.setPrefHeight(380);
					dataPanel.setPrefHeight(dataPanel.getPrefHeight() + heightDiff);
					// frageboegen.setPrefHeight(356);
					container.setPrefHeight(container.getPrefHeight() + heightDiff);
				}

			}

		});
	}

}
