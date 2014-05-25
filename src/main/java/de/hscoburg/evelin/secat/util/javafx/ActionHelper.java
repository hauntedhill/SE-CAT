package de.hscoburg.evelin.secat.util.javafx;

import java.util.HashMap;
import java.util.Map;

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

	public static void setAutoResizeToggleListenerForTitledPanel(final TitledPane searchPanel, final TitledPane dataPanel, final Control... container) {

		final Map<Control, Double> containerHeights = new HashMap<>();

		for (Control c : container) {
			containerHeights.put(c, c.getPrefHeight());
		}

		searchPanel.expandedProperty().addListener(new ChangeListener<Boolean>() {

			private double originalDataPanelLayoutY = dataPanel.getLayoutY();

			private double originalSearchPanelHeight = searchPanel.getPrefHeight();

			private double originalDataHeight = dataPanel.getPrefHeight();

			// private double originalContainerHeight = container.getPrefHeight();

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {// searchPanel ausgeklappt
					dataPanel.setLayoutY(originalDataPanelLayoutY);

					dataPanel.setPrefHeight(originalDataHeight);
					for (Control c : container) {
						c.setPrefHeight(containerHeights.get(c));
					}
					// container.setPrefHeight(originalContainerHeight);
				} else {// searchPanel eingeklappt
					dataPanel.setLayoutY(searchPanel.getLayoutY() + 30);

					dataPanel.setPrefHeight(dataPanel.getPrefHeight() + originalSearchPanelHeight - 24);
					for (Control c : container) {
						c.setPrefHeight(c.getPrefHeight() + originalSearchPanelHeight - 24);
					}
					// container.setPrefHeight(container.getPrefHeight() + originalSearchPanelHeight - 24);
				}

			}

		});
	}

}
