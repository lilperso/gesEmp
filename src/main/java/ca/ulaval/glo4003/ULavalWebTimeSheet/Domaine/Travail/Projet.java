package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheDejaExistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheInexistanteException;

public class Projet
{
    private String nomProjet;
    private String descriptionProjet;
    private List<Tache> listeTaches;
    private List<String> listeRessourceHumainesAssignees;

    public Projet() {
	listeTaches = new ArrayList<Tache>();
	listeRessourceHumainesAssignees = new ArrayList<String>(); 
    }

    public Projet(String nomProjet, String descriptionProjet, List<Tache> listeTaches, List<String> listeRessourceHumainesAssignees) {
	this.nomProjet = nomProjet;
	this.descriptionProjet = descriptionProjet;
	this.listeTaches = listeTaches;
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }

    public void ajouterTache(Tache nouvelleTache) {
	if(!tacheExisteDansProjet(nouvelleTache.getNomTache())){
	    this.listeTaches.add(nouvelleTache);
	} else throw new TacheDejaExistanteException(nouvelleTache.getNomTache());
    }

    public void supprimerTache(String nomTache) {
	for (Tache tache : listeTaches) {
	    if (tache.getNomTache().equals(nomTache)) {
		listeTaches.remove(tache);
		break;
	    }
	}
    }

    public boolean tacheExisteDansProjet(String nomtache) {
	for (Tache tache : this.getListeTaches()) {
	    if (tache.getNomTache().equals(nomtache)) {
		return true;
	    }
	}
	return false;
    }

    public boolean estAssigneAuProjet(String adresseCourrielRessourceHumaine) {
	return listeRessourceHumainesAssignees.contains(adresseCourrielRessourceHumaine);
    }
    
    public void assignerLesEmployesALaTache(String nomTache, List<String> employeSelectionne) {
	ajouterLesEmployesAuProjet(employeSelectionne);
	Tache tache = obtenirTache(nomTache);
	tache.ajouterEmployesACetteTache(employeSelectionne);
    }

    private void ajouterLesEmployesAuProjet(List<String> employeSelectionne) {
	for(String employe:employeSelectionne){
	    if(!listeRessourceHumainesAssignees.contains(employe)){
		listeRessourceHumainesAssignees.add(employe);
	    }
	}
    }

    public Tache obtenirTache(String nomTache) {
	for(Tache tache: listeTaches){
	    if(tache.getNomTache().equals(nomTache)){
		return tache;
	    }
	}
	throw new TacheInexistanteException(nomTache);
    }
    
    public void assignerLesEmployesAToutesLesTaches(List<String> employeSelectionne) {
	ajouterLesEmployesAuProjet(employeSelectionne);
	for (Tache tache: listeTaches){
	    tache.ajouterEmployesACetteTache(employeSelectionne);
	}
    }

    public void filtrerTachesNonAssigneesAUneRessourceHumaine(String adresseCourrielRessourceHumaine) {
	for (Iterator<Tache> iterator = listeTaches.iterator(); iterator.hasNext();) {
	    Tache tache = iterator.next();
            if (!(tache.estAssigneATache(adresseCourrielRessourceHumaine))) {
                iterator.remove();
            }
    	}
    }

    public String getNomProjet() {
	return nomProjet;
    }

    public String getDescriptionProjet() {
	return descriptionProjet;
    }

    public List<Tache> getListeTaches() {
	return listeTaches;
    }

    public List<String> getListeRessourceHumainesAssignees() {
	return listeRessourceHumainesAssignees;
    }

    public void setListeRessourceHumainesAssignees(List<String> listeRessourceHumainesAssignees) {
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
}
