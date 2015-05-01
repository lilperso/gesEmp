package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

public class Entreprise extends Utilisateur
{
    public Entreprise(){
	
    }

    public Entreprise(String adresseCourriel, String motDePasse, String role) {
	setMotDePasse(motDePasse);
	setRole(role);
    }
}
