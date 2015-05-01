package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheDejaExistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheInexistanteException;

public class ProjetTest
{
    private static final String DESC_PROJET= "descriptionProjet";
    private static final String NOM_PROJET = "nomProjet";
    private static final String NOM_TACHE1 = "nomTache1";
    private static final String NOM_TACHE2 = "nomTache2";
    private static final String EMPLOYE1 = "employe1";
    private static final String EMPLOYE2 = "employe2";
    private static final String EMPLOYE3 = "employe3";

    private Projet projet;

    @Before
    public void initialiser() {
	projet = new Projet();
    }

    @Test
    public void obtenirTacheLorsquElleEstPresenteRetourneLaTacheEnQuestion() {
	projet.ajouterTache(new Tache(NOM_TACHE1, null, null));

	Tache tache = projet.obtenirTache(NOM_TACHE1);

	assertEquals(NOM_TACHE1, tache.getNomTache());
    }

    @Test(expected = TacheInexistanteException.class)
    public void obtenirTacheLorqueCelleCiEstAbsenteRetourneTacheInexistanteErreur() {
	projet.obtenirTache(NOM_TACHE1);
    }

    @Test
    public void etantDonneUneListeDemployeSelectionneCeuxCiSontAssignerAuProjet() {
	List<String> listeEmploye = creerTroisEmailEmploye();
	preparerProjetAvecUneTache();

	projet.assignerLesEmployesALaTache(NOM_TACHE1, listeEmploye);

	assertEquals(3, projet.getListeRessourceHumainesAssignees().size());
    }

    @Test
    public void etantDonneUneListeDemployeSelectionneDejaDansLeProjetCeuxCiNeSontPasAssignerEnDouble() {
	List<String> listeEmploye = creerTroisEmailEmploye();
	preparerProjetAvecUneTache();

	projet.assignerLesEmployesALaTache(NOM_TACHE1, listeEmploye);
	projet.assignerLesEmployesALaTache(NOM_TACHE1, listeEmploye);

	assertEquals(3, projet.getListeRessourceHumainesAssignees().size());
    }

    @Test
    public void etantDonneUneListeDemployeSelectionneCeuxCiSontAssignesAToutesLesTachesDuProjet() {
	List<String> listeEmploye = creerTroisEmailEmploye();
	preparerProjetAvecDeuxTaches();

	projet.assignerLesEmployesAToutesLesTaches(listeEmploye);

	assertEquals(3, projet.getListeTaches().get(0).getListeRessourceHumainesAssignees().size());
	assertEquals(3, projet.getListeTaches().get(1).getListeRessourceHumainesAssignees().size());
    }

    @Test
    public void etantDonneUneListeDemployeSelectionneDejaDansLeProjetCeuxCiNeSontPasReassignerEnDouble() {
	List<String> listeEmploye = creerTroisEmailEmploye();
	preparerProjetAvecDeuxTaches();

	projet.assignerLesEmployesAToutesLesTaches(listeEmploye);
	projet.assignerLesEmployesAToutesLesTaches(listeEmploye);

	assertEquals(3, projet.getListeTaches().get(0).getListeRessourceHumainesAssignees().size());
	assertEquals(3, projet.getListeTaches().get(1).getListeRessourceHumainesAssignees().size());
    }

    @Test
    public void etantDonneUnEmployeAssigneAuProjetRetourneTrue() {
	List<String> employeAssigne = new ArrayList<String>();
	employeAssigne.add(EMPLOYE1);
	projet.setListeRessourceHumainesAssignees(employeAssigne);

	boolean estAssigne = projet.estAssigneAuProjet(EMPLOYE1);

	assertEquals(true, estAssigne);
    }

    @Test
    public void etantDonneUnEmployeNonAssigneAuProjetRetourneFalse() {
	boolean estAssigne = projet.estAssigneAuProjet(EMPLOYE2);
	assertEquals(false, estAssigne);
    }

    @Test
    public void filtrerTacheDuneRessourceHumaineRetourneSeulementSesTachesAssignees() {
	preparerProjetAvecTacheAssigneAEmploye();

	projet.filtrerTachesNonAssigneesAUneRessourceHumaine(EMPLOYE2);

	assertEquals(1, projet.getListeTaches().size());
	assertEquals(NOM_TACHE2, projet.getListeTaches().get(0).getNomTache());
    }
    
    @Test
    public void etantDonneUneTacheExistanteDansUnProjetRetourneTrue(){
	projet.ajouterTache(new Tache(NOM_TACHE1, null, null));
	
	boolean tacheExiste = projet.tacheExisteDansProjet(NOM_TACHE1);
	
	assertEquals(true,tacheExiste);
    }
    
    @Test
    public void etantDonneUneTacheNonExistanteDansUnProjetRetourneFalse(){
	projet.ajouterTache(new Tache(NOM_TACHE1, null, null));
	
	boolean tacheExiste = projet.tacheExisteDansProjet(NOM_TACHE2);
	
	assertEquals(false,tacheExiste);
    }
    
    @Test
    public void etantDonneUnNomDeTacheCetteTacheSeraSupprime(){
	projet.ajouterTache(new Tache(NOM_TACHE1, null, null));
	
	projet.supprimerTache(NOM_TACHE1);
	
	assertEquals(0,projet.getListeTaches().size());
    }
    
    @Test
    public void etantDonneUnNomDeTacheDontLaTacheNexistePasCetteTacheNeSeraPasSupprime(){
	projet.ajouterTache(new Tache(NOM_TACHE1, null, null));
	
	projet.supprimerTache(NOM_TACHE2);
	
	assertEquals(1,projet.getListeTaches().size());
    }
    
    @Test(expected = TacheDejaExistanteException.class)
    public void ajouterUneTacheQuiExisteDejaLanceUneException(){
	preparerProjetAvecUneTache();
	projet.ajouterTache(new Tache(NOM_TACHE1,"",null));
    }
   
    @Test
    public void instancierUnNouveauProjetAssigneTousCesAttributs(){
	List<Tache> listeTache = new ArrayList<Tache>();
	listeTache.add(new Tache(NOM_TACHE1,"",null));
	List<String> listeRH = new ArrayList<String>();
	listeRH.add(EMPLOYE1);
	Projet projet = new Projet(NOM_PROJET,DESC_PROJET,listeTache,listeRH);
	
	assertEquals(NOM_PROJET,projet.getNomProjet());
	assertEquals(DESC_PROJET,projet.getDescriptionProjet());
	assertEquals(1, projet.getListeTaches().size());
	assertEquals(1, projet.getListeRessourceHumainesAssignees().size());
    }
   
    
    
    private void preparerProjetAvecTacheAssigneAEmploye() {
	List<String> ressourceAssignee = new ArrayList<String>();
	ressourceAssignee.add(EMPLOYE1);
	projet.ajouterTache(new Tache(NOM_TACHE1, "", ressourceAssignee));

	List<String> ressourceAssignee2 = new ArrayList<String>();
	ressourceAssignee2.add(EMPLOYE2);
	ressourceAssignee2.add(EMPLOYE1);
	projet.ajouterTache(new Tache(NOM_TACHE2, "", ressourceAssignee2));
    }

    private void preparerProjetAvecDeuxTaches() {
	projet.ajouterTache(new Tache(NOM_TACHE1, null, new ArrayList<String>()));
	projet.ajouterTache(new Tache(NOM_TACHE2, null, new ArrayList<String>()));
    }

    private void preparerProjetAvecUneTache() {
	projet.ajouterTache(new Tache(NOM_TACHE1, null, new ArrayList<String>()));
    }

    private List<String> creerTroisEmailEmploye() {
	List<String> listeEmail = new ArrayList<String>();
	listeEmail.add(EMPLOYE1);
	listeEmail.add(EMPLOYE2);
	listeEmail.add(EMPLOYE3);
	return listeEmail;
    }

}
