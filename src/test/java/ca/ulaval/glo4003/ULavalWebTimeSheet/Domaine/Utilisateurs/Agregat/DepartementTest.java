package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.FabriqueUtilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;

@RunWith(MockitoJUnitRunner.class)
public class DepartementTest
{
    private static final String EMAIL_BIDON = "yes@test.com";
    private static final String EMAIL_BIDON2 = "yes2@test.com";
    private static final String EMAIL_BIDON3 = "yes3@test.com";
    private static final String ADRESSE_COURRIEL = "AdresseCourriel";
    private static final String ADRESSE_RESIDENCE = "adresse";
    private static final String NOM = "nom";
    private static final String PRENOM = "prenom";
    private static final String NO_TELEPHONE = "noTel";
    private static final String MOT_DE_PASSE = "pass";
    private static final String SUPERIEUR_IMMEDIAT = "adresseSuperieur";
    private static final String DEPARTEMENT = "departement";
    private static final InformationsPersonnelles INFO_PERSONNELLES = new InformationsPersonnelles(ADRESSE_RESIDENCE, NOM, PRENOM, NO_TELEPHONE);

    private FabriqueUtilisateur fabrique = new FabriqueUtilisateur();
    private RessourceHumaine ressourceHumaine = fabrique.creerEmploye(ADRESSE_COURRIEL, MOT_DE_PASSE, SUPERIEUR_IMMEDIAT, INFO_PERSONNELLES, DEPARTEMENT);
    List<RessourceHumaine> listeRessourcesHumaine = new ArrayList<RessourceHumaine>();

    @Mock
    private DepotDepartement depotDepartement;

    @Mock
    private Gestionnaire gestionnaire;

    @InjectMocks
    private Departement departement = new Departement();

    @Test
    public void obtenirListeEmailDesEmployesLorsqueDepotContientUnEmployeRetourneEmailDeLemploye() {
	mockDepartementRetourneUnEmploye();
	List<String> listeEmail = departement.obtenirListeEmailEmploye();
	assertEquals(1, listeEmail.size());
	assertEquals(EMAIL_BIDON, listeEmail.get(0));
    }

    @Test
    public void obtenirListeDesProjetAppelBienObtenirListeDesProjetsDeGestionnaire() {
	departement.obtenirListeDesProjet();
	verify(gestionnaire).getListeProjets();
    }

    @Test
    public void obtenirEmployeSelonListeAdresseCourrielRetourneLesEmployesDeCetteListe() {
	mockDepartementRetourneTroisEmploye();
	List<String> listeAdresseCourriel = new ArrayList<String>();
	listeAdresseCourriel.add(EMAIL_BIDON2);

	List<RessourceHumaine> listeRH = departement.obtenirEmployes(listeAdresseCourriel);

	assertEquals(1, listeRH.size());
	assertEquals(EMAIL_BIDON2, listeRH.get(0).getAdresseCourriel());
    }

    @Test
    public void assignerProjetEtTachesAppelDeuxModifAGestionnaireEtUnAuDepot() {
	List<String> listeVerifTache = new ArrayList<String>();
	List<String> listeVerifProjet = new ArrayList<String>();
	List<String> listeVerifEmploye = new ArrayList<String>();
	departement.assignerLesProjetsEtTachesAuxEmployes(listeVerifEmploye, listeVerifProjet, listeVerifTache);
	verify(gestionnaire).assignerLesTaches(listeVerifTache, listeVerifEmploye);
	verify(gestionnaire).assignerLesProjets(listeVerifProjet, listeVerifEmploye);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }

    @Test
    public void creerUnEmployeAppelleGestionnaireEtDepot() {
	preparerComportementMockGestionnaire();
	preparerComportementMockDepotAjoutNouvelEmploye();

	departement.creerNouvelEmploye(ADRESSE_RESIDENCE, NOM, PRENOM, ADRESSE_COURRIEL, NO_TELEPHONE, MOT_DE_PASSE);

	verify(gestionnaire).creerEmployeDepartemental(any(String.class), any(String.class), any(InformationsPersonnelles.class), any(String.class));
	verify(depotDepartement).ajouterNouvelleRessourceHumaine(any(RessourceHumaine.class));
    }

    @Test
    public void obtenirListeRessourcesHumaineDepartementaleAppelleDepot() {
	preparerComportementMockDepotObtentionRessourcesHumaines();
	departement.obtenirListeRessourcesHumainesDepartementales(ADRESSE_COURRIEL);
	verify(depotDepartement).obtenirRessourcesHumainesDepartementales(ADRESSE_COURRIEL);
    }
    
    @Test
    public void lorsqueTauxHoraireEstModifierAppelDepotDepartement() {
	when(depotDepartement.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenReturn(ressourceHumaine);
	departement.modifierTauxHoraireRessourceHumaine(any(String.class), "15.5");
	verify(depotDepartement).obtenirRessourceHumaineAvecAdresseCourriel(any(String.class));
	verify(depotDepartement).modifierRessourceHumaine(any(Employe.class));
    }
    
    @Test
    public void lesProjetAssignesAassigneARHAppelObtenirTacheAssigneACetEmploye(){
	departement.obtenirListeDesProjetsAssignesARessourceHumaine(ADRESSE_COURRIEL);
	verify(gestionnaire).obtenirTacheAssigneACetEmploye(ADRESSE_COURRIEL);
    }
    
    @Test
    public void obtenirProjetParNomProjetAppelObtenirProjet(){
	departement.obtenirProjetParNomProjet(NOM);
	verify(gestionnaire).obtenirProjet(NOM);
    }
    
    @Test
    public void modifierProjetAppelModifierProjet(){
	Projet proj = new Projet();
	departement.modifierProjet(proj,NOM);
	verify(gestionnaire).modifierProjet(proj, NOM);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }
    
    @Test
    public void ajouterProjetAppelAjouterProjet(){
	Projet proj = new Projet();
	departement.ajouterProjet(proj);
	verify(gestionnaire).ajouterProjet(proj);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }
    
    @Test
    public void ajouterTacheAppelAjouterTache(){
	Tache tache = new Tache();
	departement.ajouterTache(NOM, tache);
	verify(gestionnaire).ajouterTache(NOM, tache);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }
    
    @Test
    public void supprimerTacheAppelSupprimerTache(){
	departement.supprimerTache(NOM, NOM);
	verify(gestionnaire).supprimerTache(NOM, NOM);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }
    
    @Test
    public void modifierTacheAppelModifierTache(){
	Tache tache = new Tache();
	departement.modifierTache(NOM, tache, NOM);
	verify(gestionnaire).modifierTache(NOM, tache, NOM);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }
    
    @Test
    public void supprimerProjetAppelSupprimerProjet(){
	departement.supprimerProjet(NOM);
	verify(gestionnaire).supprimerProjet(NOM);
	verify(depotDepartement).modifierRessourceHumaine(gestionnaire);
    }

    private void preparerComportementMockDepotObtentionRessourcesHumaines() {
	when(depotDepartement.obtenirRessourcesHumainesDepartementales(any(String.class))).thenReturn(listeRessourcesHumaine);
    }

    private void preparerComportementMockDepotAjoutNouvelEmploye() {
	doNothing().when(depotDepartement).ajouterNouvelleRessourceHumaine(ressourceHumaine);
    }

    private void preparerComportementMockGestionnaire() {
	when(gestionnaire.creerEmployeDepartemental(any(String.class), any(String.class), any(InformationsPersonnelles.class), any(String.class))).thenReturn(
		ressourceHumaine);
    }

    private void mockDepartementRetourneUnEmploye() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	Employe emp = new Employe();
	emp.setAdresseCourriel(EMAIL_BIDON);
	listeRH.add(emp);
	when(depotDepartement.obtenirRessourcesHumainesDepartementales(any(String.class))).thenReturn(listeRH);
    }

    private void mockDepartementRetourneTroisEmploye() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	Employe emp1 = new Employe();
	Employe emp2 = new Employe();
	Employe emp3 = new Employe();
	emp1.setAdresseCourriel(EMAIL_BIDON);
	emp2.setAdresseCourriel(EMAIL_BIDON2);
	emp3.setAdresseCourriel(EMAIL_BIDON3);
	listeRH.add(emp1);
	listeRH.add(emp2);
	listeRH.add(emp3);
	when(depotDepartement.obtenirRessourcesHumainesDepartementales(any(String.class))).thenReturn(listeRH);
    }
}
