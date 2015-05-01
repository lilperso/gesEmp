package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.ArrayList;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Exceptions.RoleInvalideException;

public class FabriqueUtilisateur
{
    private static final String ROLE_INEXISTANT = "Le role demande n'existe pas dans l'application";

    public Utilisateur creerUtilisateur(String role, String adresseCourriel, String motDePasse, String superieurImmediat,
	    InformationsPersonnelles informationsPersonnelles, String departement) 
		    throws RoleInvalideException {

	switch (role) {
	    case Utilisateur.EMPLOYE:
		return new Employe(adresseCourriel, motDePasse, role, superieurImmediat, informationsPersonnelles,
			new ArrayList<Periode>(), departement);
	    case Utilisateur.GESTIONNAIRE:
		return new Gestionnaire(adresseCourriel, motDePasse, role, superieurImmediat, informationsPersonnelles,
			new ArrayList<Periode>(), new ArrayList<Projet>(), departement);
	    case Utilisateur.ENTREPRISE:
		return new Entreprise(adresseCourriel, motDePasse, Utilisateur.ENTREPRISE);
	    case Utilisateur.ADMINISTRATEUR:
		return new Administrateur(adresseCourriel, motDePasse, Utilisateur.ADMINISTRATEUR);
	    default:
		throw new RoleInvalideException(ROLE_INEXISTANT);
	}
    }

    public RessourceHumaine creerRessourceHumaine(String role, String adresseCourriel, String motDePasse,
	    String superieurImmediat, InformationsPersonnelles infoPersonnelles, String departement)
	    throws RoleInvalideException {

	switch (role) {
	    case Utilisateur.EMPLOYE:
		return new Employe(adresseCourriel, motDePasse, role, superieurImmediat, infoPersonnelles,
			new ArrayList<Periode>(), departement);
	    case Utilisateur.GESTIONNAIRE:
		return new Gestionnaire(adresseCourriel, motDePasse, role, superieurImmediat, infoPersonnelles,
			new ArrayList<Periode>(), new ArrayList<Projet>(), departement);
	    default:
		throw new RoleInvalideException("Le role demande n'existe pas dans l'application");
	}
    }

    public Administrateur creerAdministrateur(String adresseCourriel, String motDePasse) {
	return new Administrateur(adresseCourriel, motDePasse, Utilisateur.ADMINISTRATEUR);
    }

    public Entreprise creerEntreprise(String adresseCourriel, String motDePasse) {
	return new Entreprise(adresseCourriel, motDePasse, Utilisateur.ENTREPRISE);
    }

    public Gestionnaire creerGestionnaire(String adresseCourriel, String motDePasse, String superieurImmediat,
	    InformationsPersonnelles infoPersonnelles, String departement) {
	return new Gestionnaire(adresseCourriel, motDePasse, Utilisateur.GESTIONNAIRE, superieurImmediat,
		infoPersonnelles, new ArrayList<Periode>(), new ArrayList<Projet>(), departement);
    }

    public Employe creerEmploye(String adresseCourriel, String motDePasse, String superieurImmediat,
	    InformationsPersonnelles infoPersonnelles, String departement) {
	return new Employe(adresseCourriel, motDePasse, Utilisateur.EMPLOYE, superieurImmediat, infoPersonnelles,
		new ArrayList<Periode>(), departement);
    }
}