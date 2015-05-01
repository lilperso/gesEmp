package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

public class InfoEmployeDTO
{

    private String adresseCourriel;
    private String prenom;
    private String nom;
    private double tauxHoraire;

    public InfoEmployeDTO(String adresseCourriel, String prenom, String nom, double tauxHoraire) {
	this.adresseCourriel = adresseCourriel;
	this.prenom = prenom;
	this.nom = nom;
	this.tauxHoraire = tauxHoraire;
    }
    
    public String getAdresseCourriel(){
	return adresseCourriel;
    }
   
    public String getPrenom(){
	return prenom;
    }

    public String getNom(){
	return nom;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }
}
