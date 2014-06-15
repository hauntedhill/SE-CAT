package de.hscoburg.evelin.secat.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.BewertungDAO;
import de.hscoburg.evelin.secat.dao.FrageDAO;
import de.hscoburg.evelin.secat.dao.FragebogenDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.entity.Bewertung;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.util.javafx.SeCatResourceBundle;

/**
 * Model zur Verarbeitung aller Funktionalitaeten die Bewertungen betreffen.
 * 
 * @author zuch1000
 * 
 */
@Controller
@Transactional
public class BewertungModel {

	@Autowired
	private FragebogenDAO fragebogenDAO;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private FrageDAO frageDAO;

	@Autowired
	private BewertungDAO bewertungDAO;

	/**
	 * Liest aus dem uebergebenen {@link File} alle Bewertungen von QUestorProaus und speichert diese in der Datenbank.
	 * 
	 * @param f
	 *            - Das {@link File}
	 * @return {@link Integer} mit dem Wert der eingelesenen Bewertungen
	 * @throws Exception
	 *             Sollte die importierten Bewertungen nicht dem Fragebogen entsprechen.
	 */
	public int importBewertungen(File f) throws Exception {

		Fragebogen fragebogen = null;

		int anzCVSRows = 0;

		List<BaseEntity> fragen = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(f));
		try {
			String line;

			line = br.readLine();
			if (line != null) {
				String[] fields = line.split(";");

				if (fields.length <= 4) {
					throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectCountAnswers"));
				}

				for (int i = 4; i < fields.length; i++) {
					String[] ids = fields[i].split("_");
					if (ids.length != 4 && ids.length != 5) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectId") + fields[i]);
					}

					Fragebogen tmpFragebogen = fragebogenDAO.findById(Integer.parseInt(ids[1]));

					if (tmpFragebogen.getBewertungen().size() != 0) {
						for (Bewertung b : tmpFragebogen.getBewertungen()) {
							bewertungDAO.remove(b);

						}
						tmpFragebogen.getBewertungen().clear();
					}

					if (fragebogen != null && !tmpFragebogen.equals(fragebogen)) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectQuestionaryId")
								+ fragebogen + ", " + tmpFragebogen);
					} else if (!tmpFragebogen.getExportiert()) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString(
								"scene.evaluation.import.error.incorrectQuestionaryStatus")
								+ tmpFragebogen);

					} else {
						fragebogen = tmpFragebogen;
					}

					int countMCQUentsions = 0;
					for (Frage_Fragebogen ff : fragebogen.getFrageFragebogen()) {
						if (ff.getFrage().getSkala().getType().equals(SkalaType.MC)) {
							countMCQUentsions++;
						}
					}
					if (fragebogen.getSkala().equals(SkalaType.MC)) {
						countMCQUentsions += fragebogen.getItems().size();
					}
					// TODO:
					// if (fragebogen.getItems().size() + fragebogen.getFrageFragebogen().size() + countMCQUentsions != fields.length - 4) {
					// throw new IllegalArgumentException(SeCatResourceBundle.getInstance()
					// .getString("scene.evaluation.import.error.incorrectEvaluationCount")
					// + (fragebogen.getItems().size() + fragebogen.getFrageFragebogen().size() + countMCQUentsions) + ", " + (fields.length
					// - 4));
					// }

					if (ids[2].equals("frage")) {

						Frage frage = frageDAO.findById(Integer.parseInt(ids[3]));

						boolean foundQuestion = false;
						for (Frage_Fragebogen cf : fragebogen.getFrageFragebogen()) {
							if (cf.getFrage().equals(frage)) {
								foundQuestion = true;
								break;
							}
						}

						if (!foundQuestion) {
							throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.questionNotFound")
									+ frage);
						}

						fragen.add(frage);
					} else if (ids[2].equals("item")) {

						Item item = itemDAO.findById(Integer.parseInt(ids[3]));

						if (!fragebogen.getItems().contains(item)) {
							throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.itemNotFound") + item);
						}
						fragen.add(itemDAO.findById(Integer.parseInt(ids[3])));
					}

				}

			}

			while ((line = br.readLine()) != null) {
				String fields[] = line.split(";");
				anzCVSRows++;
				for (int i = 4; i < fields.length; i++) {
					Bewertung b = new Bewertung();

					b.setWelle(fields[0]);
					b.setZeilenid(fields[1]);
					b.setQuelle(fields[2]);
					b.setZeit(fields[3]);
					b.setWert(fields[i]);
					if (fragen.get(i - 4) instanceof Frage) {
						b.setFrage((Frage) fragen.get(i - 4));
					} else if (fragen.get(i - 4) instanceof Item) {
						b.setItem((Item) fragen.get(i - 4));
					}
					b.setFragebogen(fragebogen);

					bewertungDAO.persist(b);

				}

			}
		} finally {
			br.close();
		}
		return anzCVSRows;
	}

}
