package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.NomProjetInvalideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.NomTacheInvalideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetInexistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheDejaExistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;

@RunWith(MockitoJUnitRunner.class)
public class GestionnaireTest
{
    private static final String MOT_DE_PASSE = "pass";
    private static final String COURRIEL = "courriel";
    private static final String DEPARTEMENT = "departement";
    private static final String MAUVAIS_NOM_PROJET = "mauvais nom projet";
    private static final String NOM_PROJET = "Projet";
    private static final String NOM_PROJET2 = "Projet2";
    private static final String DESCRIPTION_PROJET = "Description du projet";
    private static final String DESCRIPTION_PROJET_MODIFIEE = "Description modifie";
    private static final String NOM_TACHE = "tache";
    private static final String DESCRIPTION_TACHE = "Description de la tache";
    private static final String MOT_DE_PASSE_GESTIONNAIRE = "motDePasseCreation";
    private static final String ADRESSE_GESTIONNAIRE = "adresseCreation@employe.com";
    private static final String SUPERIEUR_IMMEDIAT = "sambegin@employe.com";
    private static final List<String> LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET = new ArrayList<String>();
    private static final List<String> LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_TACHE = new ArrayList<String>();

    private FabriqueUtilisateur factoryUtilisateur = new FabriqueUtilisateur();
    private InformationsPersonnelles infoPersonnelles = new InformationsPersonnelles();
    private Tache tache = new Tache(NOM_TACHE, DESCRIPTION_TACHE, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_TACHE);
    private List<Tache> listetacheVide = new ArrayList<Tache>();
    
    
    private Projet projet = new Projet(NOM_PROJET, DESCRIPTION_PROJET, listetacheVide, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET);
    private Projet projetPourMiseAjour = new Projet(NOM_PROJET, "Description modifie", listetacheVide, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET);

    private Gestionnaire gestionnaireProjet;
    private Gestionnaire gestionnaireCreation = new Gestionnaire();

    @Mock
    private Gestionnaire gestionnaire;

    @Mock
    private List<Projet> listeprojetMock;

    @Before
    public void initialiserTest() {
	gestionnaireProjet = factoryUtilisateur.creerGestionnaire(ADRESSE_GESTIONNAIRE, MOT_DE_PASSE_GESTIONNAIRE, SUPERIEUR_IMMEDIAT, infoPersonnelles,
		DEPARTEMENT);
    }

    @Test
    public void lorsqueAjoutProjetAvecNomDuProjetEtDescriptionProjet() {
	gestionnaireProjet.ajouterProjet(projet);
	Projet projet = gestionnaireProjet.obtenirProjet(NOM_PROJET);
	assertTrue(estLeBonProjet(projet, NOM_PROJET));
    }

    @Test(expected = NomProjetInvalideException.class)
    public void ajouterProjetAvecNomVideRetourneErreur() {
	Projet projet = new Projet("", "", null, null);
	gestionnaireProjet.ajouterProjet(projet);
    }

    @Test(expected = NomProjetInvalideException.class)
    public void modifierProjetAvecNomVideRetourneErreur() {
	Projet projet = new Projet("", "", null, null);
	gestionnaireProjet.modifierProjet(projet, "");
    }

    @Test(expected = ProjetInexistantException.class)
    public void lorsqueObtientProjetInexistantLanceException() {
	gestionnaireProjet.obtenirProjet(NOM_PROJET);
    }

    @Test(expected = ProjetDejaExistantException.class)
    public void lorsqueAjoutProjetQuiEXisteDeja() {
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.ajouterProjet(projet);
    }

    @Test(expected = ProjetInexistantException.class)
    public void lorsqueMisAjourProjetInexistant() {
	Projet ProjetMisAjour = new Projet(MAUVAIS_NOM_PROJET, DESCRIPTION_PROJET, listetacheVide, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET);
	gestionnaireProjet.modifierProjet(ProjetMisAjour, MAUVAIS_NOM_PROJET);
    }

    @Test(expected = ProjetInexistantException.class)
    public void lorsqueSuppressionProjetInexistant() {
	gestionnaireProjet.supprimerProjet(NOM_PROJET);
    }

    @Test(expected = NomTacheInvalideException.class)
    public void ajouterUneTacheAvecNomInvalideLanceErreurNomInvalide() {
	gestionnaireProjet.ajouterTache("", new Tache("", "", null));
    }

    @Test(expected = ProjetInexistantException.class)
    public void siProjetInexistantLorsqueModifieTacheLanceException() {
	gestionnaireProjet.modifierProjet(projet, NOM_PROJET);
    }

    @Test(expected = TacheDejaExistanteException.class)
    public void lorsqueAJoutTacheDejaExistanteDansUnProjet() {
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.ajouterTache(NOM_PROJET, tache);
	gestionnaireProjet.ajouterTache(NOM_PROJET, tache);
    }

    @Test(expected = ProjetInexistantException.class)
    public void lorsqueProjetInexistantEnSupprimantTacheLanceException() {
	gestionnaireProjet.supprimerTache(NOM_PROJET, NOM_TACHE);
    }

    @Test
    public void miseAJourTacheDansUnProjet() {
	Tache tacheMisAJour = new Tache(NOM_TACHE, "Description mis a jour", LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_TACHE);
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.ajouterTache(NOM_PROJET, tache);
	gestionnaireProjet.modifierTache(NOM_PROJET, tacheMisAJour, tacheMisAJour.getNomTache());
    }

    @Test(expected = ProjetInexistantException.class)
    public void lorsqueAjoutTacheDansUnProjetInexistantLanceException() {
	gestionnaireProjet.ajouterTache(NOM_PROJET, tache);
    }

    @Test
    public void lorsqueGestionnaireEstCreateurDeGestionnaireIlCreeGestionnaire() {
	gestionnaireCreation.setEstGestionnaireDeGestionnaire(true);
	RessourceHumaine ressourceHumaine = gestionnaireCreation.creerEmployeDepartemental(COURRIEL, MOT_DE_PASSE, infoPersonnelles, DEPARTEMENT);
	assertTrue(ressourceHumaine instanceof Gestionnaire);
    }

    @Test
    public void lorsqueGestionnaireEstCreateurEmployeIlCreeEmploye() {
	gestionnaireCreation.setEstGestionnaireDeGestionnaire(false);
	RessourceHumaine ressourceHumaine = gestionnaireCreation.creerEmployeDepartemental(COURRIEL, MOT_DE_PASSE, infoPersonnelles, DEPARTEMENT);
	assertTrue(ressourceHumaine instanceof Employe);
    }

    @Test
    public void lorsqueGestionnaireModifieProjetDonneesSontModifie() {
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.modifierProjet(projetPourMiseAjour, projet.getNomProjet());
	assertTrue(projetEstModifie());
    }
    
    @Test 
    public void supprimerTacheSupprimeLaTache(){
	projet.ajouterTache(tache);
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.supprimerTache(NOM_PROJET, NOM_TACHE);
	assertEquals(0,gestionnaireProjet.getListeProjets().get(0).getListeTaches().size());
    }

    @Test
    public void supprimerProjetAvecNomDuProjetSupprimeLeProjetDeLaListe() {
	gestionnaireProjet.ajouterProjet(new Projet(NOM_PROJET, "", null, null));
	gestionnaireProjet.ajouterProjet(new Projet(NOM_PROJET2, "", null, null));

	gestionnaireProjet.supprimerProjet(NOM_PROJET);

	assertEquals(1, gestionnaireProjet.getListeProjets().size());
	assertEquals(NOM_PROJET2, gestionnaireProjet.getListeProjets().get(0).getNomProjet());
    }
    
    @Test
    public void assignerLesTachesAuxDifferentsEmployesDeLaListe(){
	creerProjetAvecUneTacheAuGestionnaire();
	List<String> tache = new ArrayList<String>(); tache.add("Projet/tache");
	List<String> employe = new ArrayList<String>(); employe.add(COURRIEL);
	
	gestionnaireProjet.assignerLesTaches(tache,employe);
	
	assertEquals(1,gestionnaireProjet.getListeProjets().get(0).getListeTaches().get(0).getListeRessourceHumainesAssignees().size());
    }
    
    @Test
    public void assignerLesProjetsAssigneLesProjetsAuxDifferentsEmployes(){
	creerProjetAvecUneTacheAuGestionnaire();
	List<String> projet = new ArrayList<String>(); projet.add("Projet");
	List<String> employe = new ArrayList<String>(); employe.add(COURRIEL);
	
	gestionnaireProjet.assignerLesProjets(projet, employe);
	
	assertEquals(1,gestionnaireProjet.getListeProjets().get(0).getListeRessourceHumainesAssignees().size());
    }
    
    @Test
    public void obtenirTacheAssigneALemployeRetourneListeProjetAssigneACeluiCi(){
	preparerProjetAvecEmployeAssigne();
	
	List<Projet> projet = gestionnaireProjet.obtenirTacheAssigneACetEmploye(COURRIEL);
	
	assertEquals(1,projet.size());
    }
    
    private void preparerProjetAvecEmployeAssigne() {
	List<Tache> listeTache = new ArrayList<Tache>();
	List<String> employe = new ArrayList<String>();
	employe.add(COURRIEL);
	listeTache.add(new Tache(NOM_TACHE,"",employe));
	Projet projet = new Projet(NOM_PROJET,"",listeTache,employe);
	Projet projet2 = new Projet("projetBidon","",null,new ArrayList<String>());
	gestionnaireProjet.ajouterProjet(projet);
	gestionnaireProjet.ajouterProjet(projet2);
    }

    private void creerProjetAvecUneTacheAuGestionnaire() {
	projet.ajouterTache(new Tache(NOM_TACHE, "", new ArrayList<String>()));
	gestionnaireProjet.ajouterProjet(projet);
    }

    private boolean projetEstModifie() {
	return gestionnaireProjet.getListeProjets().get(0).getDescriptionProjet().equals(DESCRIPTION_PROJET_MODIFIEE);
    }

    private boolean estLeBonProjet(Projet projet, String nomProjet) {
	if (nomProjet.equals(projet.getNomProjet())) {
	    return true;
	}
	return false;
    }
}
