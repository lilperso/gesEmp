package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.PeriodeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Depense;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Deplacement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.serviceTravailException;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTravailTest
{
    private static final int SEPT = 7;
    private static final int SIX = 6;
    private static final String DATE_FIN_PERIODE = "2014-01-07";
    private static final String DATE_DEBUT_PERIODE = "2014-01-01";
    private static final String ADRESSE_COURRIEL = "philkelly@employe.com";
    private static final String MOT_DE_PASSE = "MotDePasse";
    private static final String ADRESSE_COURRIEL_INEXISTANTE = "inexistant@abc.com";
    private static final String ROLE_NON_RESSOURCE_HUMAINE = "bobTheAdmin@employe.com";
    private QuartTravailDTO nouveauQuartDTO = new QuartTravailDTO("2014-01-01");
    private AuthentificationDTO aDTO;

    @Mock
    private DepotDepartement dpDepot;

    @Mock
    private RessourceHumaine utilisateur;

    @InjectMocks
    private ServiceTravail serviceTravail;

    @Before
    public void initialiserTest() {
	aDTO = preparerMockRessourceHumaine();
	preparerMockDepot();
	mettreADoNothingLesMethodesDuDomain();
    }

    @Test
    public void ajouterQuartsTravailPourPeriodeCouranteAppelleobtenirRessourceHumaineAvecAdresseCourrielDeDepot() throws Throwable {
	nouveauQuartDTO = creerNouveauQuartDto();
	serviceTravail.ajouterQuartsTravailPourPeriodeCourante(nouveauQuartDTO, aDTO);

	verify(dpDepot).obtenirRessourceHumaineAvecAdresseCourriel(ADRESSE_COURRIEL);
	verify(utilisateur).ajouterQuartTravailPourPeriodeCourante(any(QuartTravail.class));
	verify(dpDepot).modifierRessourceHumaine(utilisateur);
    }

    @Test
    public void creerPeriodeDtoAvecPeriodeNulleRetourneListeDeSeptQuarts() {
	Periode periode = null;
	DateTime dateDebutPeriode = new DateTime(DATE_DEBUT_PERIODE);

	PeriodeDTO periodeDTO = serviceTravail.creerLeDTOCorrespondantALaPeriode(dateDebutPeriode, periode);

	assertTrue(periodeDTO.listeQuartsTravailDTO.size() == SEPT);
    }

    @Test
    public void creerPeriodeDtoAvecPeriodeNonNulleRetourneListeDeSeptQuarts() {
	Periode periode = preparerPeriodeAvecUnQuartSaisi();
	DateTime dateDebutPeriode = new DateTime(DATE_DEBUT_PERIODE);

	PeriodeDTO periodeDTO = serviceTravail.creerLeDTOCorrespondantALaPeriode(dateDebutPeriode, periode);

	assertTrue(periodeDTO.listeQuartsTravailDTO.size() == SEPT);
    }

    @Test
    public void creerPeriodeDtoAvecPeriodeNulleRetournePeriodeDtoAvecSeptQuartVides() {
	Periode periode = null;
	DateTime dateDebutPeriode = new DateTime(DATE_DEBUT_PERIODE);

	PeriodeDTO periodeDTO = serviceTravail.creerLeDTOCorrespondantALaPeriode(dateDebutPeriode, periode);

	assertTrue(nombreDeQuartParDefaut(periodeDTO) == SEPT);
    }

    @Test
    public void creerPeriodeDtoAvecUnQuartsSaisiRetournePeriodeDtoAvecSixQuartsParDefaut() {
	Periode periode = preparerPeriodeAvecUnQuartSaisi();
	DateTime dateDebutPeriode = new DateTime(DATE_DEBUT_PERIODE);

	PeriodeDTO periodeDTO = serviceTravail.creerLeDTOCorrespondantALaPeriode(dateDebutPeriode, periode);

	assertTrue(nombreDeQuartParDefaut(periodeDTO) == SIX);
    }

    @Test
    public void remplirDTOSelonPeriodeCouranteTrouveeSiaucunePeriodeDansListeRetournePeriodeDTOParDefaut() {
	List<Periode> listePeriodes = new ArrayList<Periode>();
	PeriodeDTO periodeDTO = serviceTravail.remplirDTOSelonPeriodeCouranteTrouvee(new DateTime(), listePeriodes);
	assertTrue(nombreDeQuartParDefaut(periodeDTO) == SEPT);
    }

    @Test
    public void remplirDTOSelonPeriodeCouranteTrouveeSiAucunePeriodeCorrespondanteRetournePeriodeDTOParDefaut() {
	List<Periode> listePeriodes = preparerListeAvecUnePeriode();
	PeriodeDTO periodeDTO = serviceTravail.remplirDTOSelonPeriodeCouranteTrouvee(new DateTime("2014-01-08"), listePeriodes);
	assertTrue(nombreDeQuartParDefaut(periodeDTO) == SEPT);
    }

    @Test
    public void remplirDTOSelonPeriodeCourranteTrouveeSiUnePeriodeCorrespondanteRetournePeriodeDTOContientUnQuart() {
	List<Periode> listePeriodes = preparerListeAvecUnePeriode();
	PeriodeDTO periodeDTO = serviceTravail.remplirDTOSelonPeriodeCouranteTrouvee(new DateTime(DATE_DEBUT_PERIODE), listePeriodes);
	assertTrue(nombreDeQuartParDefaut(periodeDTO) == SIX);
    }

    @Test(expected = serviceTravailException.class)
    public void obtenirPeriodeDeTravailDTO_AdresseInexistante_ThrowErreur() throws Throwable {
	mockAdresseCourrielInexistanteParDepot();
	serviceTravail.obtenirPeriodeDeTravailDTO(new DateTime(), ADRESSE_COURRIEL_INEXISTANTE);
    }

    @Test(expected = serviceTravailException.class)
    public void obtenirPeriodeDeTravailDTO_UtilisateurNestPasRessourceHumaine_ThrowErreur() throws Throwable {
	mockNestPasRessourceHumaineParDepot();
	serviceTravail.obtenirPeriodeDeTravailDTO(new DateTime(), ROLE_NON_RESSOURCE_HUMAINE);
    }

    private List<Periode> preparerListeAvecUnePeriode() {
	List<Periode> periodes = new ArrayList<Periode>();
	periodes.add(preparerPeriodeAvecUnQuartSaisi());
	return periodes;
    }

    private int nombreDeQuartParDefaut(PeriodeDTO periodeDTO) {
	int nombreQuartParDefaut = 0;
	for (QuartTravailDTO quart : periodeDTO.getListeQuartsTravailDTO()) {
	    if (quart.getDateQuartTravail() != null && quart.getHeureEntree() == "") {
		nombreQuartParDefaut++;
	    }
	}
	return nombreQuartParDefaut;
    }

    private Periode preparerPeriodeAvecUnQuartSaisi() {
	List<QuartTravail> listeQuart = new ArrayList<QuartTravail>();
	listeQuart.add(new QuartTravail(new DateTime(DATE_DEBUT_PERIODE), "heureEntree", "heureSortie", new Depense(), new Deplacement(), "noteQuart"));
	Periode periode = new Periode(listeQuart, new DateTime(DATE_DEBUT_PERIODE), new DateTime(DATE_FIN_PERIODE));
	return periode;
    }

    private void mockAdresseCourrielInexistanteParDepot() throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException {
	when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenThrow(
		new AdresseCourrielInexistanteException(ADRESSE_COURRIEL_INEXISTANTE));
    }

    private void mockNestPasRessourceHumaineParDepot() throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException {
	when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenThrow(new NestPasRessourceHumaineException(ROLE_NON_RESSOURCE_HUMAINE));
    }

    private void mettreADoNothingLesMethodesDuDomain() {
	try {
	    doNothing().when(utilisateur).ajouterQuartTravailPourPeriodeCourante(any(QuartTravail.class));
	    doNothing().when(dpDepot).ajouterNouvelleRessourceHumaine(utilisateur);
	} catch (UtilisateurDejaExistantException e) {
	    e.printStackTrace();
	}
    }

    private void preparerMockDepot() {
	try {
	    when(dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(any(String.class))).thenReturn(utilisateur);
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException e) {
	    e.printStackTrace();
	}
    }

    private AuthentificationDTO preparerMockRessourceHumaine() {
	utilisateur = mock(RessourceHumaine.class, Mockito.CALLS_REAL_METHODS);
	utilisateur.setAdresseCourriel(ADRESSE_COURRIEL);
	utilisateur.setMotDePasse(MOT_DE_PASSE);
	utilisateur.setRole(Utilisateur.ADMINISTRATEUR);
	utilisateur.setListePeriodes(preparerListeAvecUnePeriode());

	AuthentificationDTO authentificationDTO = new AuthentificationDTO(utilisateur);
	return authentificationDTO;
    }

    private QuartTravailDTO creerNouveauQuartDto() {
	QuartTravailDTO quartDto = new QuartTravailDTO("");
	quartDto.setDateQuartTravail("2014-01-01");
	quartDto.setHeureEntree("heureEntree");
	quartDto.setHeureSortie("heureSortie");
	return quartDto;
    }
}