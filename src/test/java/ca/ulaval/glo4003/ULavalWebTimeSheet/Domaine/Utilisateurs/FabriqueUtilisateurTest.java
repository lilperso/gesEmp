package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Administrateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Entreprise;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.FabriqueUtilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Exceptions.RoleInvalideException;

public class FabriqueUtilisateurTest
{
    private static final String MOT_DE_PASSE = "motDePasse";
    private static final String COURRIEL = "courriel";
    private static final String ROLE_INVALIDE = "RoleInvalide";
    private static final String SUPERIEUR_IMMEDIAT = "adresseCourriel@employe.com";
    private static final String DEPARTEMENT = "departement";
    private InformationsPersonnelles infoPersonnelles = new InformationsPersonnelles();

    private FabriqueUtilisateur factoryUtilisateur = new FabriqueUtilisateur();

    @Test
    public void creerUtilisateurLorsqueCreeEmployeRetourneEmploye() throws Throwable {
	String roleAttendu = Utilisateur.EMPLOYE;
	Utilisateur utilisateurRetour = factoryUtilisateur.creerUtilisateur(roleAttendu, COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerUtilisateurLorsqueCreeGestionnaireRetourneGestionnaire() throws Throwable {
	String roleAttendu = Utilisateur.GESTIONNAIRE;
	Utilisateur utilisateurRetour = factoryUtilisateur.creerUtilisateur(roleAttendu, COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerUtilisateurLorsqueCreeEntrepriseRetourneEntreprise() throws Throwable {
	String roleAttendu = Utilisateur.ENTREPRISE;
	Utilisateur utilisateurRetour = factoryUtilisateur.creerUtilisateur(roleAttendu, COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerUtilisateurLorsqueCreeAdministrateurRetourneAdministrateur() throws Throwable {
	String roleAttendu = Utilisateur.ADMINISTRATEUR;
	Utilisateur utilisateurRetour = factoryUtilisateur.creerUtilisateur(roleAttendu, COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test(expected = RoleInvalideException.class)
    public void creerUtilisateurLorsqueRoleDemandeEstInexistantLanceException() throws Throwable {
	String roleAttendu = ROLE_INVALIDE;
	factoryUtilisateur.creerUtilisateur(roleAttendu, COURRIEL, MOT_DE_PASSE, SUPERIEUR_IMMEDIAT, infoPersonnelles,
		DEPARTEMENT);
    }

    @Test
    public void creerRessourceHumaineLorsqueDemandeGestionnaireRetourneGestionnaire() throws Throwable {
	String roleAttendu = Utilisateur.GESTIONNAIRE;
	RessourceHumaine utilisateurRetour = factoryUtilisateur.creerRessourceHumaine(roleAttendu, COURRIEL,
		MOT_DE_PASSE, SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerRessourceHumaineLorsqueDemandeEmployeRetourneEmploye() throws Throwable {
	String roleAttendu = Utilisateur.EMPLOYE;
	Utilisateur utilisateurRetour = factoryUtilisateur.creerRessourceHumaine(roleAttendu, COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test(expected = RoleInvalideException.class)
    public void creerRessourseHumaineLorsqueDemandeRoleInexistantLanceException() throws Throwable {
	String roleAttendu = ROLE_INVALIDE;
	factoryUtilisateur.creerRessourceHumaine(roleAttendu, COURRIEL, MOT_DE_PASSE, SUPERIEUR_IMMEDIAT,
		infoPersonnelles, DEPARTEMENT);
    }

    @Test
    public void creerEmployeRetourneEmploye() throws Throwable {
	String roleAttendu = Utilisateur.EMPLOYE;
	Employe utilisateurRetour = factoryUtilisateur.creerEmploye(COURRIEL, MOT_DE_PASSE, SUPERIEUR_IMMEDIAT,
		infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerGestionnaireRetourneGestionnaire() throws Throwable {
	String roleAttendu = Utilisateur.GESTIONNAIRE;
	Gestionnaire utilisateurRetour = factoryUtilisateur.creerGestionnaire(COURRIEL, MOT_DE_PASSE,
		SUPERIEUR_IMMEDIAT, infoPersonnelles, DEPARTEMENT);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerAdministrateurRetourneAdministrateur() throws Throwable {
	String roleAttendu = Utilisateur.ADMINISTRATEUR;
	Administrateur utilisateurRetour = factoryUtilisateur.creerAdministrateur(COURRIEL, MOT_DE_PASSE);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

    @Test
    public void creerEntrepriseRetourneEntreprise() throws Throwable {
	String roleAttendu = Utilisateur.ENTREPRISE;
	Entreprise utilisateurRetour = factoryUtilisateur.creerEntreprise(COURRIEL, MOT_DE_PASSE);
	assertEquals(roleAttendu, utilisateurRetour.getRole());
    }

}
