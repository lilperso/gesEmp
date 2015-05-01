package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

public class Administrateur extends Utilisateur
{

    public Administrateur() {
    }

    public Administrateur(String adresseCourriel, String motDePasse, String role) {
	setAdresseCourriel(adresseCourriel);
	setMotDePasse(motDePasse);
	setRole(role);
    }

}
