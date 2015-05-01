package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TacheDTO implements Serializable
{
    private String nomTache;
    private String descriptionTache;
    private List<String> listeRessourceHumainesAssignees;
    
    public TacheDTO(String nomTache, String descriptionTache, List<String> listeRessourceHumainesAssignees) {
	this.nomTache = nomTache;
	this.descriptionTache = descriptionTache;
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
    
    public String getNomTache() {
	return this.nomTache;
    }
    
    public void setNomTache(String nomTache) {
	this.nomTache = nomTache;
    }
    
    public String getDescriptionTache() {
	return this.descriptionTache;
    }
    
    public void setDescriptionTache(String descriptionTache) {
	this.descriptionTache = descriptionTache;
    }
    
    public List<String> getListeRessourceHumaineAssignees() {
	return this.listeRessourceHumainesAssignees;
    }
    
    public void setListeRessourceHumaineAssignees(List<String> listeRessourceHumainesAssignees) {
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
}
