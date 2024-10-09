package main.service;

import java.util.List;

import com.google.inject.Inject;

import main.DAO.PartieDAO;
import main.model.Partie;

public class PartieService {
	
	private PartieDAO partieDAO;

	@Inject
	public PartieService(PartieDAO partieDAO) {
		this.partieDAO = partieDAO;
	}

	public List<String> getNomsDesParties() {
	    return partieDAO.getAllNames();
	}
	
	public List<Partie> getAllParties(){
	    return partieDAO.findAll();
	}
	
	public boolean save(String name, String computer, boolean finie, String fen) {
	    Partie partie = new Partie();
	    partie.setComputer(computer);
	    partie.setName(name);
	    partie.setFinie(finie);
	    partie.setFen(fen);
	    return partieDAO.save(partie);
	}
	
	public void delete(Partie partie) {
	    partieDAO.delete(partie);
	}


}
