package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;

public class CreerProjetForm
{
    private String nomProjet;
    private String descriptionProjet;
    private List<TacheDTO> listeTachesDTO;

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

    public List<TacheDTO> getListeTaches() {
	return this.listeTachesDTO;
    }

    public void setListeTaches(List<TacheDTO> listeTachesDTO) {
	this.listeTachesDTO = listeTachesDTO;
    }
}