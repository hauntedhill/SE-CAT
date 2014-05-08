package de.hscoburg.evelin.secat.controller.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import de.hscoburg.evelin.secat.SeCat;

public abstract class BaseController implements Initializable {

	private Stage currentStage;

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public Stage getCurrentStage() {
		return this.currentStage;

		// return (Stage) org.controlsfx.tools.Utils.getWindow(null);

		// if (_owner == null) {
		// _owner = org.controlsfx.tools.Utils.getWindow(_owner);
		// }
		//
		// if (_owner instanceof Scene) {
		// this.scene = (Scene) _owner;
		// } else if (_owner instanceof Stage) {
		// this.scene = ((Stage) _owner).getScene();
		// } else if (_owner instanceof Tab) {
		// // special case for people wanting to show a lightweight dialog inside
		// // one tab whilst the rest of the TabPane remains responsive.
		// // we keep going up until the styleclass is "tab-content-area"
		// owner = (Parent) ((Tab)_owner).getContent();
		// } else if (_owner instanceof Node) {
		// owner = getFirstParent((Node)_owner);
		// } else {
		//            throw new IllegalArgumentException("Unknown owner: " + _owner.getClass()); //$NON-NLS-1$
		// }
	}

	private Parent getFirstParent(Node n) {
		if (n == null)
			return null;
		return n instanceof Parent ? (Parent) n : getFirstParent(n.getParent());
	}

	public abstract void initializeController(URL location, ResourceBundle resources);

	public abstract String getSceneName();

	@Override
	public final void initialize(URL location, ResourceBundle resources) {

		initializeController(location, resources);

	}

	public void setTitle() {
		getCurrentStage().setTitle(SeCat.MAIN_STAGE_TITLE + " - " + getSceneName());
		getCurrentStage().getIcons().add(new Image("/image/icons/dvi.png"));

	}

}
