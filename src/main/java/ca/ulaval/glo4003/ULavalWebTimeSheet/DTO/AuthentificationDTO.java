package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.io.Serializable;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;

@SuppressWarnings("serial")
public class AuthentificationDTO implements Serializable
{
    private String adresseCourriel;
    private String role;
    private String superieurImmediat;

    public AuthentificationDTO() {
    }

    public AuthentificationDTO(Utilisateur utilisateur) {
	setAdresseCourriel(utilisateur.getAdresseCourriel());
	setRole(utilisateur.getRole().toString());

	if (utilisateur instanceof RessourceHumaine) {
	    setSuperieurImmediat(((RessourceHumaine) utilisateur).getSuperieur());
	} else {
	    setSuperieurImmediat("aucun");
	}
    }

    public String getAdresseCourriel() {
	return adresseCourriel;
    }

    public void setAdresseCourriel(String adresseCourriel) {
	this.adresseCourriel = adresseCourriel;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public String getSuperieurImmediat() {
	return superieurImmediat;
    }

    public void setSuperieurImmediat(String superieurImmediat) {
	this.superieurImmediat = superieurImmediat;
    }
}
