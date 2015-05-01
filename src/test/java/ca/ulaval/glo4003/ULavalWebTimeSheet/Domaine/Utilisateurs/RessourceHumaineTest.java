
package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

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
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;


public class RessourceHumaineTest
{
    private static final DateTime dateQuart = new DateTime("2014-01-01");
    private static final DateTime dateDebutPeriodeLundi = new DateTime("2013-12-30");
    private static final DateTime dateDebutPeriodeDimanche = new DateTime("2014-01-05");
    private static final String HEURE_ENTREE = "2h";
    private static final String HEURE_SORTIE = "3h";
    private static final String HEURE_ENTREE_MODIFIEE = "heureEntreModifie";
    private static final String HEURE_SORTIE_MODIFIEE = "heureSortieModifie";
    
    @Test
    public void ajouterQuartTravailDansPeriodeQuiNexistePasRetourneNouvellePeriodeAvecQuart(){
	RessourceHumaine rH = creerUneRessourceHumaineVide();
	QuartTravail qt = creerQuartTravail(dateQuart);
	
	rH.ajouterQuartTravailPourPeriodeCourante(qt);
	
	assertEquals(1,rH.getListePeriodes().size());
	assertEquals(dateDebutPeriodeLundi, rH.getListePeriodes().get(0).getDateDebut());
	assertEquals(dateQuart, rH.getListePeriodes().get(0).getListeQuartsTravail().get(0).getDateQuartTravail());
    }
    
    @Test
    public void ajouterQuartTravailDansPeriodeQuiExisteDejaRetourneMemePeriodeAvecQuart(){
	RessourceHumaine rH = creerUneRessourceHumaineVide();
	rH.setListePeriodes(creerUnePeriode());
	QuartTravail qt = creerQuartTravail(dateQuart);
	
	rH.ajouterQuartTravailPourPeriodeCourante(qt);
	
	assertEquals(1,rH.getListePeriodes().size());
	assertEquals(dateDebutPeriodeLundi, rH.getListePeriodes().get(0).getDateDebut());
	assertEquals(1, rH.getListePeriodes().get(0).getListeQuartsTravail().size());
    }
    
    @Test
    public void modifierQuartDeTravailModifieLeQuartExistant(){
	RessourceHumaine rH = creerUneRessourceHumaineVide();
	rH.setListePeriodes(creerUnePeriodeAvecUnQuart(dateQuart));
	QuartTravail qtModifie = creerQuartTravailModifie(dateQuart);

	rH.modifierQuartDeTravail(qtModifie);
	
	assertEquals(HEURE_ENTREE_MODIFIEE, rH.getListePeriodes().get(0).getListeQuartsTravail().get(0).getHeureEntree());
	assertEquals(HEURE_SORTIE_MODIFIEE, rH.getListePeriodes().get(0).getListeQuartsTravail().get(0).getHeureSortie());
    }
    
    @Test
    public void approuverDepenseApprouveLaDepense()
    {
	RessourceHumaine rh = creerRessourceHumaineAvecDepense();
	rh.approuverDepense(dateQuart);
	assertTrue(rh.getListePeriodes().get(0).getListeQuartsTravail().get(1).getDepense().estApprouve());
    }
    
    @Test
    public void approuverDeplacementApprouveLeDeplacement()
    {
	RessourceHumaine rh = creerRessourceHumaineAvecDeplacement();
	rh.approuverDeplacement(dateQuart);
	assertTrue(rh.getListePeriodes().get(0).getListeQuartsTravail().get(1).getDeplacement().estApprouve());
    }

    private RessourceHumaine creerRessourceHumaineAvecDepense() {
	Depense depense = new Depense("", "", false);
	QuartTravail quartTrNonValide = new QuartTravail(dateDebutPeriodeLundi, "", "", null, null, "");
	QuartTravail quartTrValide = new QuartTravail(dateQuart, "", "", depense, null, "");
	List<QuartTravail> listeQuartsTravail = new ArrayList<QuartTravail>();
	listeQuartsTravail.add(quartTrNonValide);
	listeQuartsTravail.add(quartTrValide);
	Periode pr = new Periode(listeQuartsTravail, dateDebutPeriodeLundi, dateDebutPeriodeDimanche);
	List<Periode> listePeriodes = new ArrayList<Periode>();
	listePeriodes.add(pr);
	RessourceHumaine rh = new Employe();
	rh.setListePeriodes(listePeriodes);
	return rh;
    }
    
    private RessourceHumaine creerRessourceHumaineAvecDeplacement() {
	Deplacement deplacement= new Deplacement("", "", false);
	QuartTravail quartTr = new QuartTravail(dateQuart, "", "", null, deplacement, "");
	QuartTravail quartTrNonValide = new QuartTravail(dateDebutPeriodeLundi, "", "", null, null, "");
	List<QuartTravail> listeQuartsTravail = new ArrayList<QuartTravail>();
	listeQuartsTravail.add(quartTrNonValide);
	listeQuartsTravail.add(quartTr);
	Periode pr = new Periode(listeQuartsTravail, dateDebutPeriodeLundi, dateDebutPeriodeDimanche);
	List<Periode> listePeriodes = new ArrayList<Periode>();
	listePeriodes.add(pr);
	RessourceHumaine rh = new Employe();
	rh.setListePeriodes(listePeriodes);
	return rh;
    }

    private List<Periode> creerUnePeriodeAvecUnQuart(DateTime date) {
	List<Periode> lp = creerUnePeriode();
	QuartTravail qt = creerQuartTravail(date);
	lp.get(0).getListeQuartsTravail().add(qt);
	return lp;
    }

    private QuartTravail creerQuartTravailModifie(DateTime date) {
	QuartTravail qt = new QuartTravail();
	qt.setDateQuartTravail(date);
	qt.setHeureEntree(HEURE_ENTREE_MODIFIEE);
	qt.setHeureSortie(HEURE_SORTIE_MODIFIEE);
	return qt;
    }

    private List<Periode> creerUnePeriode() {
	List<Periode> lp = new ArrayList<Periode>();
	lp.add(new Periode(dateDebutPeriodeLundi));
	return lp;
    }

    private RessourceHumaine creerUneRessourceHumaineVide() {
	RessourceHumaine rh = new Employe();
	rh.setListePeriodes(new ArrayList<Periode>());
	return rh;
    }

    private QuartTravail creerQuartTravail(DateTime date) {
	QuartTravail qt = new QuartTravail();
	qt.setDateQuartTravail(date);
	qt.setHeureEntree(HEURE_ENTREE);
	qt.setHeureSortie(HEURE_SORTIE);
	return qt;
	
    }
    
}
