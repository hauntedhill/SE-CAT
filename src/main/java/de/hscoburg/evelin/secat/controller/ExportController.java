package de.hscoburg.evelin.secat.controller;

import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.model.FragebogenModel;

@Controller
public class ExportController {

	@Autowired
	private FragebogenModel fragebogenModel;

	public void exportFragebogen(Fragebogen fb) {

		try {

			FileWriter fw = new FileWriter("file.txt");
			fw.write(fragebogenModel.generateXMLFor(fb).toString());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
