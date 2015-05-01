package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels;

public class CreerTacheForm
{
    private String nomTache;
    private String descriptionTache;
    
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
}