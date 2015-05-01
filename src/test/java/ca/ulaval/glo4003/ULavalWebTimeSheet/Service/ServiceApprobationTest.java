//package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;
//
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.joda.time.DateTime;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Depense;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Deplacement;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ServiceApprobationTest
//{
//
//    private static final String DATE_FIN_PERIODE = "2014-01-07";
//    private static final String DATE_DEBUT_PERIODE = "2014-01-01";
//    private static final String DATE_DEBUT_QUART = "2014-01-01";
//    private static final String ADRESSE_COURRIEL_EMPLOYE = "christ123@Periodes.com";
//    private static final String MOT_DE_PASSE = "111";
//    private static final String ADRESSE_COURRIEL_INEXISTANTE = "inexistant@abc.com";
//    private static final String ROLE_NON_RESSOURCE_HUMAINE = "bobTheAdmin@employe.com";
//    private RessourceHumaine employe;
//    private DateTime dateQuart = new DateTime(DATE_DEBUT_QUART);
//
//    @Mock
//    private DepotDepartement dpDepot;
//
//    @Mock
//    private RessourceHumaine utilisateur;
//
//    @InjectMocks
//    private ServiceApprobation serviceApprobation;
//
//    @Before
//    public void initialiserTest() throws Throwable {
//	employe = preparerMockRessourceHumaineEmploye();
//	preparerMockDepot();
//    }
//
//    // @Test
//    // public void approuverDepense() throws Throwable {
//    // boolean depenseApprouve = false;
//    // serviceApprobation.trouverEtApprouverDepense(employe.getAdresseCourriel(),
//    // dateQuart);
//    // depenseApprouve = verifierQueLaDepenseEstApprouve(dateQuart,
//    // depenseApprouve);
//    // assertTrue(depenseApprouve);
//    // }
//    //
//    // @Test
//    // public void approuverDeplacement() throws Throwable {
//    // boolean deplacementApprouve = false;
//    // serviceApprobation.trouverEtApprouverDeplacement(employe.getAdresseCourriel(),
//    // dateQuart);
//    // deplacementApprouve = verifierQueLeDeplacementEstApprouve(dateQuart,
//    // deplacementApprouve);
//    // assertTrue(deplacementApprouve);
//    // }
//
//    @Test(expected = AdresseCourrielInexistanteException.class)
//    public void obtenirPeriodeDeTravailDTO_AdresseInexistante_ThrowErreur() throws Throwable {
//	mockAdresseCourrielInexistanteParDepot();
//	serviceApprobation.obtenirListeDesPeriodesAvecDemandesPourUnEmploye(ADRESSE_COURRIEL_INEXISTANTE);
//    }
//
//    @Test(expected = NestPasRessourceHumaineException.class)
//    public void obtenirPeriodeDeTravailDTO_UtilisateurNestPasRessourceHumaine_ThrowErreur() throws Throwable {
//	mockNestPasRessourceHumaineParDepot();
//	serviceApprobation.obtenirListeDesPeriodesAvecDemandesPourUnEmploye(ROLE_NON_RESSOURCE_HUMAINE);
//    }
//
//    private void preparerMockDepot() {
//	try {
//	    when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenReturn(utilisateur);
//	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException e) {
//	    e.printStackTrace();
//	}
//    }
//
//    private RessourceHumaine preparerMockRessourceHumaineEmploye() throws Exception {
//	employe = mock(RessourceHumaine.class, Mockito.CALLS_REAL_METHODS);
//	employe.setAdresseCourriel(ADRESSE_COURRIEL_EMPLOYE);
//	employe.setMotDePasse(MOT_DE_PASSE);
//	employe.setRole(Utilisateur.EMPLOYE);
//	employe.setListePeriodes(preparerListeAvecUnePeriodePourEmploye());
//
//	return employe;
//    }
//
//    private Periode preparerPeriodeAvecUnQuartSaisi() throws Exception {
//	List<QuartTravail> listeQuart = new ArrayList<QuartTravail>();
//	listeQuart.add(new QuartTravail(dateQuart, "heureEntree", "heureSortie", new Depense(), new Deplacement(), "noteQuart"));
//	Periode periode = new Periode(listeQuart, new DateTime(DATE_DEBUT_PERIODE), new DateTime(DATE_FIN_PERIODE));
//	return periode;
//    }
//
//    private List<Periode> preparerListeAvecUnePeriodePourEmploye() throws Exception {
//	List<Periode> periodes = new ArrayList<Periode>();
//	periodes.add(preparerPeriodeAvecUnQuartSaisi());
//	return periodes;
//    }
//
//    private void mockAdresseCourrielInexistanteParDepot() throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException {
//	when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenThrow(
//		new AdresseCourrielInexistanteException(ADRESSE_COURRIEL_INEXISTANTE));
//    }
//
//    private void mockNestPasRessourceHumaineParDepot() throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException {
//	when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenThrow(new NestPasRessourceHumaineException(ROLE_NON_RESSOURCE_HUMAINE));
//    }
//
//    private boolean verifierQueLeDeplacementEstApprouve(DateTime dateQuart, boolean deplacementApprouve) {
//	for (Periode p : employe.getListePeriodes()) {
//	    for (QuartTravail q : p.getListeQuartsTravail()) {
//		if (q.getDateQuartTravail().equals(dateQuart)) {
//		    deplacementApprouve = q.getDeplacement().estApprouve();
//		}
//	    }
//	}
//	return deplacementApprouve;
//    }
//
//    private boolean verifierQueLaDepenseEstApprouve(DateTime dateQuart, boolean depenseApprouve) {
//	for (Periode p : employe.getListePeriodes()) {
//	    for (QuartTravail q : p.getListeQuartsTravail()) {
//		if (q.getDateQuartTravail().equals(dateQuart)) {
//		    depenseApprouve = q.getDepense().estApprouve();
//		}
//	    }
//	}
//	return depenseApprouve;
//    }
//
//}
