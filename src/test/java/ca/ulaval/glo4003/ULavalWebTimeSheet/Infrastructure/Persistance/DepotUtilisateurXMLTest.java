package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Depense;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Deplacement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.FabriqueUtilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

public class DepotUtilisateurXMLTest
{

    private static final String DEPARTEMENT = "departement";
    private static final String NOTE = "note";
    private static final String HEURE_SORTIE = "sortie";
    private static final String HEURE_ENTRE = "entre";
    private static final int VALEUR_DATE = 4;
    private static final String SUPERIEUR_IMMEDIAT = "sambegin@employe.com";
    private static final String MOT_DE_PASSE_CREATION_EMPLOYE = "motDePasseCreation";
    private static final String ADRESSE_CREATION_EMPLOYE = "adresseCreation@employe.com";
    private static final String ADRESSE_EMPLOYE_A_METTRE_JOUR = "johnsimth@employe.ca";
    private static final String MOT_DE_PASSE_EMPLOYE_A_METTRE_A_JOUR = "passEmployeSmith";
    private static final String BONNE_ADRESSE_COURRIEL = "philkelly@employe.com";
    private static final String BON_MOT_DE_PASSE = "passEmploye";
    private static final String MAUVAISE_ADRESSE_COURRIEL = "mauvaireAdresse@erreur.com";
    private static final String MAUVAIS_MOT_DE_PASSE = "mauvais_mot_de_passe";
    private static final String NOM_CREATION_EMPLOYE = "Smith";
    private static final String PRENOM_CREATION_EMPLOYE = "John";
    private static final String RESIDENCE_CREATION_EMPLOYE = "Smith";
    private static final String TELEPHONE_CREATION_EMPLOYE = "418-999-8888";
    private static final String RESIDENCE_MIS_A_JOUR = "uniservite";

    private UtilisateurPersistanceXMLTesteur utilisateurPersistance = new UtilisateurPersistanceXMLTesteur();
    FabriqueUtilisateur factoryUtilisateur = new FabriqueUtilisateur();
    private InformationsPersonnelles infoPersonnelles = new InformationsPersonnelles();
    private InformationsPersonnelles infoPersonnellesComplet = new InformationsPersonnelles(RESIDENCE_CREATION_EMPLOYE,
	    NOM_CREATION_EMPLOYE, PRENOM_CREATION_EMPLOYE, TELEPHONE_CREATION_EMPLOYE);

    @Test
    public void lorsqueRechercheUtilisateurAvecMotDePasseEtCourrielRetourneUtilisateur() throws Throwable {
	Utilisateur utilisateur = utilisateurPersistance.trouverUtilisateur(BONNE_ADRESSE_COURRIEL, BON_MOT_DE_PASSE);
	assertTrue(estLeBonUtilisateur(utilisateur, BONNE_ADRESSE_COURRIEL, BON_MOT_DE_PASSE));
    }

    @Test
    public void lorsqueRechercheUtilisateurAvecMotDePasseEtCourrielRetourneUtilisateurAvecInfoPersonnelles()
	    throws Throwable {
	Utilisateur nouvelUtilisateur = factoryUtilisateur.creerRessourceHumaine(Utilisateur.EMPLOYE,
		ADRESSE_CREATION_EMPLOYE, MOT_DE_PASSE_CREATION_EMPLOYE, SUPERIEUR_IMMEDIAT, infoPersonnellesComplet,
		DEPARTEMENT);
	utilisateurPersistance.persisterNouvelUtilisateur(nouvelUtilisateur);

	Employe employe = (Employe) utilisateurPersistance.trouverUtilisateur(ADRESSE_CREATION_EMPLOYE,
		MOT_DE_PASSE_CREATION_EMPLOYE);
	assertTrue(employe.getInformationsPersonnelles().equals(infoPersonnellesComplet));

	// Pour retirer l'utilisateur du fichier XML apres le test.
	utilisateurPersistance.supprimerUtilisateurXML(nouvelUtilisateur);
    }

    @Test
    public void lorsqueUtilisateurAMettreJourEstBienMisJour() throws Throwable {
	Utilisateur employeAmettreAJour = creerUtilisateurAmettreAjour();
	InformationsPersonnelles infoPersonnellesAmettreAjour = new InformationsPersonnelles(RESIDENCE_MIS_A_JOUR,
		NOM_CREATION_EMPLOYE, PRENOM_CREATION_EMPLOYE, TELEPHONE_CREATION_EMPLOYE);
	((RessourceHumaine) employeAmettreAJour).setInformationsPersonnelles(infoPersonnellesAmettreAjour);

	utilisateurPersistance.persisterModificationInformationsUtilisateur(employeAmettreAJour);

	assertTrue(employeEstMisAjour(infoPersonnellesAmettreAjour));

	// Pour retirer l'utilisateur du fichier xml apres le test.
	Utilisateur utilisateurApresMiseAjour = utilisateurPersistance.trouverUtilisateur(
		ADRESSE_EMPLOYE_A_METTRE_JOUR, MOT_DE_PASSE_EMPLOYE_A_METTRE_A_JOUR);
	utilisateurPersistance.supprimerUtilisateurXML(utilisateurApresMiseAjour);
    }

    @Test(expected = UtilisateurInexistantException.class)
    public void lorsqueUtilisateurAMettreJourEstAbsentDuFichierDesUtilisateursLanceException() throws Throwable {
	Utilisateur nouvelUtilisateur = factoryUtilisateur.creerRessourceHumaine(Utilisateur.EMPLOYE,
		MAUVAISE_ADRESSE_COURRIEL, MAUVAIS_MOT_DE_PASSE, SUPERIEUR_IMMEDIAT, infoPersonnellesComplet,
		DEPARTEMENT);

	utilisateurPersistance.persisterModificationInformationsUtilisateur(nouvelUtilisateur);
    }

    @Test(expected = AdresseCourrielInexistanteException.class)
    public void lorsqueRechercheUtilisateurMauvaiseAdresseCourrielLanceException() throws Throwable {
	utilisateurPersistance.trouverUtilisateur(MAUVAISE_ADRESSE_COURRIEL, BON_MOT_DE_PASSE);
    }

    @Test(expected = MauvaisMotDePasseException.class)
    public void lorsqueRechercheUtilisateurAvecMauvaisMotDePasseLanceException() throws Throwable {
	utilisateurPersistance.trouverUtilisateur(BONNE_ADRESSE_COURRIEL, MAUVAIS_MOT_DE_PASSE);
    }

    @Test
    public void lorsqueCreeUtilisateurUtilisateurCreeCorrespondAceluiDemande() throws Throwable {
	RessourceHumaine nouvelUtilisateur = factoryUtilisateur.creerRessourceHumaine(Utilisateur.EMPLOYE,
		ADRESSE_CREATION_EMPLOYE, MOT_DE_PASSE_CREATION_EMPLOYE, SUPERIEUR_IMMEDIAT, infoPersonnelles,
		DEPARTEMENT);
	utilisateurPersistance.persisterNouvelUtilisateur(nouvelUtilisateur);

	assertTrue(creationDuBonUtilisateur(nouvelUtilisateur, ADRESSE_CREATION_EMPLOYE, MOT_DE_PASSE_CREATION_EMPLOYE));

	// Pour retirer l'utilisateur du fichier xml apres le test.
	utilisateurPersistance.supprimerUtilisateurXML(nouvelUtilisateur);
    }

    @Test(expected = UtilisateurDejaExistantException.class)
    public void lorsqueCreeUtilisateurMaisUtilisateurExisteLanceException() throws Throwable {
	Utilisateur utilisateurExistant = utilisateurPersistance
		.obtenirUtilisateurAvecAdresseCourriel(BONNE_ADRESSE_COURRIEL);
	utilisateurPersistance.persisterNouvelUtilisateur(utilisateurExistant);

	utilisateurPersistance.supprimerUtilisateurXML(utilisateurExistant);
    }

    @Test
    public void persisterPlusieursPeriodePersisteBonneDonnees() throws Throwable {
	RessourceHumaine nouvelUtilisateur = (RessourceHumaine) factoryUtilisateur.creerRessourceHumaine(
		Utilisateur.EMPLOYE, "bidon@test.com", "123", SUPERIEUR_IMMEDIAT, infoPersonnellesComplet, DEPARTEMENT);
	nouvelUtilisateur.setListePeriodes(preparerListePeriodes());

	utilisateurPersistance.persisterNouvelUtilisateur(nouvelUtilisateur);

	assertTrue(persistanceDeBonneDonnees(nouvelUtilisateur));

	utilisateurPersistance.supprimerUtilisateurXML(nouvelUtilisateur);
    }

    @Test
    public void obtenirListeDesUtilisateursRetourneTousLesUtilisateurs() {
	List<Utilisateur> listeUtilisateurs = utilisateurPersistance.obtenirListeDesUtilisateurs();
	assertEquals(3, listeUtilisateurs.size());
    }

    private boolean persistanceDeBonneDonnees(RessourceHumaine nouvelUtilisateur) throws Throwable {
	Utilisateur utilisateurPersiste = utilisateurPersistance
		.obtenirUtilisateurAvecAdresseCourriel(nouvelUtilisateur.getAdresseCourriel());

	List<Periode> periodesPersistees = ((RessourceHumaine) utilisateurPersiste).getListePeriodes();
	List<Periode> periodesNouvelUtilisateur = ((RessourceHumaine) nouvelUtilisateur).getListePeriodes();

	return periodesPersistees.size() == periodesNouvelUtilisateur.size();
    }

    //
    private class UtilisateurPersistanceXMLTesteur extends DepotUtilisateurXML
    {
	private static final String CHEMIN_FICHIER_XML_TEST = "src/test/java/ca/ulaval/glo4003/ULavalWebTimeSheet/Infrastructure/Persistance/UtilisateursTestsXStream.xml";

	public UtilisateurPersistanceXMLTesteur() {
	    super.cheminFichierXML = CHEMIN_FICHIER_XML_TEST;
	}

    }

    private List<Periode> preparerListePeriodes() {
	List<QuartTravail> listeQuartsTravail = new ArrayList<QuartTravail>();
	DateTime dateQuart = new DateTime(VALEUR_DATE);
	listeQuartsTravail.add(new QuartTravail(dateQuart, HEURE_ENTRE, HEURE_SORTIE, new Depense(), new Deplacement(),
		NOTE));
	listeQuartsTravail.add(new QuartTravail(dateQuart, HEURE_ENTRE, HEURE_SORTIE, new Depense(), new Deplacement(),
		NOTE));
	DateTime dateDebut = new DateTime(VALEUR_DATE, VALEUR_DATE, VALEUR_DATE, VALEUR_DATE, VALEUR_DATE);
	DateTime dateFin = new DateTime(VALEUR_DATE);
	List<Periode> listePeriodes = new ArrayList<Periode>();
	listePeriodes.add(new Periode(listeQuartsTravail, dateDebut, dateFin));
	listePeriodes.add(new Periode(listeQuartsTravail, dateDebut, dateFin));

	return listePeriodes;
    }

    private Utilisateur creerUtilisateurAmettreAjour() throws Throwable {
	Utilisateur employeAmettreAJour = factoryUtilisateur.creerRessourceHumaine(Utilisateur.EMPLOYE,
		ADRESSE_EMPLOYE_A_METTRE_JOUR, MOT_DE_PASSE_EMPLOYE_A_METTRE_A_JOUR, SUPERIEUR_IMMEDIAT,
		infoPersonnellesComplet, DEPARTEMENT);
	utilisateurPersistance.persisterNouvelUtilisateur(employeAmettreAJour);
	return employeAmettreAJour;
    }

    private boolean employeEstMisAjour(InformationsPersonnelles infoPersonnellesAmettreAjour)
	    throws AdresseCourrielInexistanteException, MauvaisMotDePasseException {
	Utilisateur utilisateurApresMiseAjour = utilisateurPersistance.trouverUtilisateur(
		ADRESSE_EMPLOYE_A_METTRE_JOUR, MOT_DE_PASSE_EMPLOYE_A_METTRE_A_JOUR);
	InformationsPersonnelles informationsPersonnellesApresMiseAjour = ((RessourceHumaine) utilisateurApresMiseAjour)
		.getInformationsPersonnelles();

	return informationsPersonnellesApresMiseAjour.equals(infoPersonnellesAmettreAjour);
    }

    private boolean creationDuBonUtilisateur(Utilisateur nouvelUtilisateur, String adresseCreation,
	    String motDePasseCreation) throws Throwable {
	Utilisateur utilisateur = utilisateurPersistance.trouverUtilisateur(nouvelUtilisateur.getAdresseCourriel(),
		nouvelUtilisateur.getMotDePasse());
	return estLeBonUtilisateur(utilisateur, adresseCreation, motDePasseCreation);
    }

    private Boolean estLeBonUtilisateur(Utilisateur utilisateurExistant, String adresseCourriel, String motDePasse) {
	if (utilisateurExistant.getAdresseCourriel().equals(adresseCourriel)
		&& utilisateurExistant.getMotDePasse().equals(motDePasse)) {
	    return true;
	}
	return false;
    }
}