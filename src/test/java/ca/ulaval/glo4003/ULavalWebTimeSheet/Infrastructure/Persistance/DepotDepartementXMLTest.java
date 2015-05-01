package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Administrateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;

public class DepotDepartementXMLTest
{

    private static final String ADRESSE_INEXISTANTE = "adresseInexistante@inexiste.com";
    private static final String ADRESSE_NON_RH = "bobTheAdmin@employe.com";
    private static final String ADRESSE_RH = "sambegin@employe.com";
    private static final String ADRESSE_EMPL = "philkelly@employe.com";

    private DepartementDepotTesteur depDepot = new DepartementDepotTesteur();

    @Test(expected = NestPasRessourceHumaineException.class)
    public void lorsqueObtientUtilisateurQuiNestPasRessourceHumaineLanceException() throws AdresseCourrielInexistanteException,
	    NestPasRessourceHumaineException {
	depDepot.obtenirRessourceHumaineAvecAdresseCourriel(ADRESSE_NON_RH);
    }

    @Test(expected = AdresseCourrielInexistanteException.class)
    public void lorsqueObtientUtilisateurQuiNexistePasLanceException() throws Throwable, NestPasRessourceHumaineException {
	depDepot.obtenirRessourceHumaineAvecAdresseCourriel(ADRESSE_INEXISTANTE);
    }

    @Test
    public void lorsqueObtientUtilisateurQuiExisteRetourneBonUtilisateur() throws Throwable {
	RessourceHumaine rh = depDepot.obtenirRessourceHumaineAvecAdresseCourriel(ADRESSE_RH);
	assertEquals(ADRESSE_RH, rh.getAdresseCourriel());
    }

    @Test(expected = NestPasGestionnaireException.class)
    public void lorsqueObtientGestionnaireQuiNestPasGestionnaireLanceException() throws Throwable {
	depDepot.obtenirGestionnaireDepartemental(ADRESSE_EMPL);
    }

    @Test
    public void lorsqueObtientGestionnaireQuiEstGestionnaireRetourneBonUtilisateur() throws Throwable {
	Gestionnaire gestionnaire = depDepot.obtenirGestionnaireDepartemental(ADRESSE_RH);
	assertEquals(ADRESSE_RH, gestionnaire.getAdresseCourriel());
	assertTrue(gestionnaire instanceof Gestionnaire);
    }

    @Test
    public void lorsqueObtientRHDuSuppPourUtilisateurNonRessourceHumaineNestPasUneRessourceHumaineListeResteVide() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	depDepot.obtenirRessourceHumaineDuSuperieur(ADRESSE_RH, listeRH, new Administrateur());
	assertEquals(0, listeRH.size());
    }

    @Test
    public void lorsqueObtientRHDuSuppQuiNestPasSonSuppListeResteVide() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	Employe employe = new Employe();
	employe.setSuperieur(ADRESSE_INEXISTANTE);
	depDepot.obtenirRessourceHumaineDuSuperieur(ADRESSE_RH, listeRH, employe);
	assertEquals(0, listeRH.size());
    }

    @Test
    public void lorsqueObtientRHDuSuppQueSuppEstVideListeResteVide() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	Employe employe = new Employe();
	depDepot.obtenirRessourceHumaineDuSuperieur(ADRESSE_RH, listeRH, employe);
	assertEquals(0, listeRH.size());
    }

    @Test
    public void lorsqueObtientRHDuSuppQueSuppEstOkEmployeEstAjoute() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	Employe employe = new Employe();
	employe.setSuperieur(ADRESSE_RH);
	depDepot.obtenirRessourceHumaineDuSuperieur(ADRESSE_RH, listeRH, employe);
	assertEquals(1, listeRH.size());
    }

    @Test
    public void lorsqueObtientRHsDuDepListeEmployePeuple() {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	listeRH = depDepot.obtenirRessourcesHumainesDepartementales(ADRESSE_RH);
	assertEquals(1, listeRH.size());
    }

    private class DepartementDepotTesteur extends DepotDepartementXML
    {
	private static final String CHEMIN_FICHIER_XML_TEST = "src/test/java/ca/ulaval/glo4003/ULavalWebTimeSheet/Infrastructure/Persistance/UtilisateursTestsXStream.xml";

	public DepartementDepotTesteur() {
	    super.cheminFichierXML = CHEMIN_FICHIER_XML_TEST;
	}
    }

}
