package de.hscoburg.evelin.secat.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
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
	 * @param r
	 *            Das {@link File}
	 * @return {@link Integer} mit der Anzahl der eingelesenen Bewertungen
	 * @throws Exception
	 *             Sollte die importierten Bewertungen nicht dem Fragebogen entsprechen.
	 */
	public int importBewertungen(Reader r) throws Exception {

		Fragebogen fragebogen = null;

		int anzCVSRows = 0;

		List<BaseEntity> fragen = new ArrayList<>();

		BufferedReader br = new BufferedReader(r);
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
					if (ids.length != 4) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectId") + fields[i]);
					}

					Fragebogen tmpFragebogen = fragebogenDAO.findById(Integer.parseInt(ids[1]));

					if (fragebogen != null && !fragebogen.equals(tmpFragebogen)) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectQuestionaryId")
								+ fragebogen + ", " + tmpFragebogen);
					} else if (tmpFragebogen == null) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectQuestionaryId")
								+ tmpFragebogen);

					} else if (!tmpFragebogen.getExportiertQuestorPro()) {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString(
								"scene.evaluation.import.error.incorrectQuestionaryStatus")
								+ tmpFragebogen);

					} else {
						fragebogen = tmpFragebogen;
					}

					if (tmpFragebogen.getBewertungen() != null && tmpFragebogen.getBewertungen().size() != 0) {
						for (Bewertung b : tmpFragebogen.getBewertungen()) {
							bewertungDAO.remove(b);

						}
						tmpFragebogen.getBewertungen().clear();
					}

					// int countMCQUentsions = 0;
					// for (Frage_Fragebogen ff : fragebogen.getFrageFragebogen()) {
					// if (ff.getFrage().getSkala().getType().equals(SkalaType.MC)) {
					// countMCQUentsions++;
					// }
					// }
					// if (fragebogen.getSkala().equals(SkalaType.MC)) {
					// countMCQUentsions += fragebogen.getItems().size();
					// }
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
					} else {
						throw new IllegalArgumentException(SeCatResourceBundle.getInstance().getString("scene.evaluation.import.error.incorrectId") + fields[i]);

					}

				}

				while ((line = br.readLine()) != null) {
					String bewertungFields[] = line.split(";");
					anzCVSRows++;
					for (int i = 4; i < bewertungFields.length; i++) {
						Bewertung b = new Bewertung();

						b.setWelle(bewertungFields[0]);
						b.setZeilenid(bewertungFields[1]);
						b.setQuelle(bewertungFields[2]);
						b.setZeit(bewertungFields[3]);
						b.setWert(bewertungFields[i]);
						if (fragen.get(i - 4) instanceof Frage) {
							b.setFrage((Frage) fragen.get(i - 4));
						} else if (fragen.get(i - 4) instanceof Item) {
							b.setItem((Item) fragen.get(i - 4));
						}
						b.setFragebogen(fragebogen);

						bewertungDAO.persist(b);

					}

				}
				fragebogen.setExportiertCore(false);
				fragebogenDAO.merge(fragebogen);
			}
		} finally {
			br.close();
		}
		return anzCVSRows;
	}

	/**
	 * Loescht alle Bewertungen fuer einen Fragebogen
	 * 
	 * @param f
	 *            {@link Fragebogen}
	 */
	public void deleteBewertung(Fragebogen f) {
		f = fragebogenDAO.findById(f.getId());

		for (Bewertung b : f.getBewertungen() != null ? f.getBewertungen() : new ArrayList<Bewertung>()) {
			bewertungDAO.remove(b);
		}

	}

	public void setOutlier(ArrayList<Bewertung> bewertungen) {
		for (Bewertung bewertung : bewertungen) {
			bewertung.setAusreiser(true);
			bewertungDAO.merge(bewertung);
			

		}
	}

	public void removeOutlier(ArrayList<Bewertung> bewertungen) {
		for (Bewertung bewertung : bewertungen) {
			bewertung.setAusreiser(false);
			bewertungDAO.merge(bewertung);

		}
	}

	public void setOutlierAutomatic(ArrayList<Bewertung> bewertungen) {
		for (Bewertung bewertung : bewertungen) {
			bewertung.setAusreiser(null);
			bewertungDAO.merge(bewertung);
		}
	}

}
