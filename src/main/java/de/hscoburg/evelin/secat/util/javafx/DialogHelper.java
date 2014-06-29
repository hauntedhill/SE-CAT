package de.hscoburg.evelin.secat.util.javafx;

import org.controlsfx.dialog.Dialogs;

import de.hscoburg.evelin.secat.SeCat;

public class DialogHelper {

	public static void showErrorDialog(String keyTitle, String keyText, Exception e) {
		Dialogs d = Dialogs.create().title(SeCat.MAIN_STAGE_TITLE + " - " + SeCatResourceBundle.getInstance().getString(keyTitle))
				.masthead(SeCatResourceBundle.getInstance().getString(keyText));
		if (e != null) {
			d.showException(e);
		} else {
			d.showError();
		}

	}

	public static void showGeneralErrorDialog(Exception e) {
		showErrorDialog("scene.error.title", "scene.error.text", e);
	}

	public static void showInputErrorDialog() {
		showErrorDialog("scene.input.error.title", "scene.input.error.txt", null);
	}

	public static void showInformationDialog(String title, String text) {
		Dialogs.create().title(SeCat.MAIN_STAGE_TITLE + " - " + title).masthead(text).showInformation();

	}

}
