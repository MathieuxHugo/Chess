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
	
	public boolean save(String name, String computer, String coups) {
	    Partie partie = new Partie();
	    partie.setComputer(computer);
	    partie.setCoups(coups);
	    partie.setName(name);
	    
	    return partieDAO.save(partie);
	}


}
