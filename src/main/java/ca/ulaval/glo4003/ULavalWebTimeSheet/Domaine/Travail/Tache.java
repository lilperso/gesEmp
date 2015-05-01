package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import java.util.ArrayList;
import java.util.List;

public class Tache
{
    private String nomTache;
    private String descriptionTache;
    private List<String> listeRessourceHumainesAssignees;

    public Tache() {
	listeRessourceHumainesAssignees = new ArrayList<String>();
    }

    public Tache(String nomTache, String descriptionTache, List<String> listeRessourceHumainesAssignees) {
	this.nomTache = nomTache;
	this.descriptionTache = descriptionTache;
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
    
    public void ajouterEmployesACetteTache(List<String> employeSelectionne) {
	for(String employe:employeSelectionne){
	    if(!listeRessourceHumainesAssignees.contains(employe)){
		listeRessourceHumainesAssignees.add(employe);
	    }
	}
    }
    
    public boolean estAssigneATache(String adresseCourrielRessourceHumaine) {
	return listeRessourceHumainesAssignees.contains(adresseCourrielRessourceHumaine);
    }

    public String getNomTache() {
	return nomTache;
    }

    public String getDescriptionTache() {
	return descriptionTache;
    }

    public List<String> getListeRessourceHumainesAssignees() {
	return listeRessourceHumainesAssignees;
    }
}
