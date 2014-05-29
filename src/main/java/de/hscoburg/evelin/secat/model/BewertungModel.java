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
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

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

	public void importBewertungen(File f) throws Exception {

		Fragebogen fragebogen = null;

		List<BaseEntity> fragen = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(f));
		try {
			String line;

			line = br.readLine();
			if (line != null) {
				String[] fields = line.split(";");

				if (fields.length <= 4) {
					throw new IllegalArgumentException("Ungültige Anzahl an Antworten.");
				}

				for (int i = 4; i < fields.length; i++) {
					String[] ids = fields[i].split("_");
					if (ids.length != 4) {
						throw new IllegalArgumentException("Ungültige ID (" + fields[i] + ") in Datei.");
					}

					Fragebogen tmpFragebogen = fragebogenDAO.findById(Integer.parseInt(ids[1]));

					if (tmpFragebogen.getBewertungen().size() != 0) {
						for (Bewertung b : tmpFragebogen.getBewertungen()) {
							bewertungDAO.remove(b);

						}
						tmpFragebogen.getBewertungen().clear();
					}

					if (fragebogen != null && !tmpFragebogen.equals(fragebogen)) {
						throw new IllegalArgumentException("Antworten aus mehreren Fragebögen(" + fragebogen + "und " + tmpFragebogen + ") gefunden.");
					} else {
						fragebogen = tmpFragebogen;
					}

					if (fragebogen.getItems().size() + fragebogen.getFragen().size() != fields.length - 4) {
						throw new IllegalArgumentException("Anzahl erwartete Antworten (Erwartet: " + fragebogen.getItems().size()
								+ fragebogen.getFragen().size() + " Gefunden:" + (fields.length - 4) + ") nicht korrekt.");
					}

					if (ids[2].equals("frage")) {

						Frage frage = frageDAO.findById(Integer.parseInt(ids[3]));
						if (!fragebogen.getFragen().contains(frage)) {
							throw new IllegalArgumentException("Frage (" + frage + ") nicht im Fragebogen vorhanden.");
						}

						fragen.add(frage);
					} else if (ids[2].equals("item")) {

						Item item = itemDAO.findById(Integer.parseInt(ids[3]));

						if (!fragebogen.getItems().contains(item)) {
							throw new IllegalArgumentException("Item (" + item + ") nicht im Fragebogen vorhanden.");
						}
						fragen.add(itemDAO.findById(Integer.parseInt(ids[3])));
					}

				}

			}

			while ((line = br.readLine()) != null) {
				String fields[] = line.split(";");
				for (int i = 4; i < fields.length; i++) {
					Bewertung b = new Bewertung();

					b.setWelle(fields[0]);
					b.setRawid(fields[1]);
					b.setSource(fields[2]);
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
	}

}
