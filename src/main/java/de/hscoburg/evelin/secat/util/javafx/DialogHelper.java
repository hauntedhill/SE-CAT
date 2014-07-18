package de.hscoburg.evelin.secat.util.javafx;

import org.controlsfx.dialog.Dialogs;

import de.hscoburg.evelin.secat.SeCat;

/**
 * Klasse fuer die allgemeinen Dialoge
 * 
 * @author zuch1000
 * 
 */
public class DialogHelper {

	/**
	 * Zeigt einen Errordialog an, mit den uebergebenen TextKeys.
	 * 
	 * @param keyTitle
	 *            - Key fuer den Title
	 * @param keyText
	 *            - Key fuer den Text
	 * @param e
	 *            - {@link Exception}
	 */
	public static void showErrorDialog(String keyTitle, String keyText, Exception e) {
		Dialogs d = Dialogs.create().title(SeCat.MAIN_STAGE_TITLE + " - " + SeCatResourceBundle.getInstance().getString(keyTitle))
				.masthead(SeCatResourceBundle.getInstance().getString(keyText));
		if (e != null) {
			d.showException(e);
		} else {
			d.showError();
		}

	}

	/**
	 * Zeigt einen standard Errordialog an
	 * 
	 * @param e
	 *            - {@link Exception}
	 */
	public static void showGeneralErrorDialog(Exception e) {
		showErrorDialog("scene.error.title", "scene.error.text", e);
	}

	/**
	 * Zeigt einen standard Inputerrordialog an
	 */
	public static void showInputErrorDialog() {
		showErrorDialog("scene.input.error.title", "scene.input.error.txt", null);
	}

	/**
	 * Zeigt einen standard Informationsdialog an
	 * 
	 * @param title
	 *            - Title als Text
	 * @param text
	 *            - Text als text
	 */
	public static void showInformationDialog(String title, String text) {
		Dialogs.create().title(SeCat.MAIN_STAGE_TITLE + " - " + title).masthead(text).showInformation();

	}

}
