package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ProjetDTO implements Serializable
{
    private String nomProjet;
    private String descriptionProjet;
    private List<TacheDTO> listeTachesDTO;
    private List<String> listeRessourceHumainesAssignees = new ArrayList<String>();
    
    public ProjetDTO() {
	this.listeTachesDTO = new ArrayList<TacheDTO>();
    }
    
    public ProjetDTO(String nomProjet, String descriptionProjet, List<TacheDTO> listeTaches, List<String> listeRessourceHumainesAssignees ) {
	this.nomProjet = nomProjet;
	this.descriptionProjet = descriptionProjet;
	this.listeTachesDTO = listeTaches;
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
   
    public String getNomProjet() {
	return this.nomProjet;
    }
    
    public void setNomProjet(String nomProjet) {
	this.nomProjet = nomProjet;
    }
    
    public String getDescriptionProjet() {
	return this.descriptionProjet;
    }
    
    public void setDescriptionProjet(String descriptionProjet) {
	this.descriptionProjet = descriptionProjet;
    }
    
    public List<TacheDTO> getListeTachesDTO() {
	return this.listeTachesDTO;
    }
    
    public void setListeTachesDTO(List<TacheDTO> listeTachesDTO) {
	this.listeTachesDTO = listeTachesDTO;
    }
    
    public List<String> getListeRessourceHumainesAssignees() {
	return this.listeRessourceHumainesAssignees;
    }
    
    public void setListeRessourceHumainesAssignees(List<String> listeRessourceHumainesAssignees) {
	this.listeRessourceHumainesAssignees = listeRessourceHumainesAssignees;
    }
}
