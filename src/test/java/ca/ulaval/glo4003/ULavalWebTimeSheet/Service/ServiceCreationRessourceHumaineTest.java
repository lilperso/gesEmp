package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

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

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.FabriqueUtilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceCreationRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeCreationRessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

@RunWith(MockitoJUnitRunner.class)
public class ServiceCreationRessourceHumaineTest
{
    private static final String ADRESSE_COURRIEL = "AdresseCourriel";
    private static final String ADRESSE_RESIDENCE = "adresse";
    private static final String NOM = "nom";
    private static final String PRENOM = "prenom";
    private static final String NO_TELEPHONE = "noTel";
    private static final String MOT_DE_PASSE = "pass";
    private static final String superieurImmediat = "adresseSuperieur";
    private static final InformationsPersonnelles infoPersonnelles = new InformationsPersonnelles(ADRESSE_RESIDENCE, NOM, PRENOM, NO_TELEPHONE);
    private static final String DEPARTEMENT = "departement";

    private FabriqueUtilisateur fabrique = new FabriqueUtilisateur();

    @Mock
    private ValidationDonneeCreationRessourceHumaine validationDonnees;

    @Mock
    private Departement departement;

    @InjectMocks
    private ServiceCreationRessourceHumaine serviceCreation = new ServiceCreationRessourceHumaine();

    @Test
    public void verifierLorsqueRecoitDemandeCreationEmployeAppelleValidationEtDemandeCreation() throws Throwable {
	preparerComportementMockValidationDonneesDonneesValides();
	preparerComportementMockDepartementAppelCreation();

	serviceCreation.demanderCreationNouvelEmploye(ADRESSE_RESIDENCE, NOM, PRENOM, NO_TELEPHONE, ADRESSE_COURRIEL, MOT_DE_PASSE);

	verifieAppelsDansBonOrdre();
    }

    @Test(expected = ServiceCreationRessourceHumaineException.class)
    public void lorsqueRecuesDonneesInvalidesDemandeCreationLanceException() throws Throwable {
	preparerComportementMockValidationDonneesDonneesInvalides();
	serviceCreation.demanderCreationNouvelEmploye(ADRESSE_RESIDENCE, NOM, PRENOM, NO_TELEPHONE, ADRESSE_COURRIEL, MOT_DE_PASSE);
    }

    @Test(expected = ServiceCreationRessourceHumaineException.class)
    public void lorsqueDepartementRefuseCreationEmployeCarPersistanceLanceExceptionDemandeCreationLanceException() throws Throwable {
	preparerComportementMockValidationDonneesDonneesValides();
	preparerComportementMockDepartementLanceException();
	serviceCreation.demanderCreationNouvelEmploye(ADRESSE_RESIDENCE, NOM, PRENOM, NO_TELEPHONE, ADRESSE_COURRIEL, MOT_DE_PASSE);
    }

    @Test
    public void demanderListeEmployeDeDeuxRetourneListeDtoEmployeDeDeux() {
	preparerComportementMockDepartementRetourListeEmploye();
	List<InfoEmployeDTO> infoEmployeDtoListe = serviceCreation.obtenirDtoListeEmploye(ADRESSE_COURRIEL);
	assertTrue(infoEmployeDtoListe.size() == 2);
    }

    @Test
    public void demanderListeVideEmployeRetourneListeDtoEmployeVide() {
	preparerComportementMockDepartementRetourListeEmployeVide();
	List<InfoEmployeDTO> infoEmployeDtoListe = serviceCreation.obtenirDtoListeEmploye(ADRESSE_COURRIEL);
	assertTrue(infoEmployeDtoListe.size() == 0);
    }

    private void preparerComportementMockDepartementRetourListeEmployeVide() {
	List<RessourceHumaine> listeEmploye = new ArrayList<RessourceHumaine>();
	when(departement.obtenirListeRessourcesHumainesDepartementales(any(String.class))).thenReturn(listeEmploye);
    }

    private void preparerComportementMockDepartementRetourListeEmploye() {
	List<RessourceHumaine> listeEmploye = preparerListeEmploye();
	when(departement.obtenirListeRessourcesHumainesDepartementales(any(String.class))).thenReturn(listeEmploye);
    }

    private List<RessourceHumaine> preparerListeEmploye() {
	List<RessourceHumaine> listeEmploye = new ArrayList<RessourceHumaine>();
	Employe employe = fabrique.creerEmploye(ADRESSE_COURRIEL, MOT_DE_PASSE, superieurImmediat, infoPersonnelles, DEPARTEMENT);
	listeEmploye.add(employe);
	listeEmploye.add(employe);

	return listeEmploye;
    }

    private void preparerComportementMockDepartementLanceException() {
	doThrow(NestPasGestionnaireException.class).when(departement).creerNouvelEmploye(any(String.class), any(String.class), any(String.class),
		any(String.class), any(String.class), any(String.class));
    }

    private void preparerComportementMockValidationDonneesDonneesInvalides() throws ChampVideException {
	doThrow(ChampVideException.class).when(validationDonnees).validerDonneeCreationEmploye(any(String.class), any(String.class));
    }

    private void preparerComportementMockValidationDonneesDonneesValides() throws Throwable {
	doNothing().when(validationDonnees).validerDonneeCreationEmploye(any(String.class), any(String.class));
    }

    private void preparerComportementMockDepartementAppelCreation() {
	doNothing().when(departement).creerNouvelEmploye(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class),
		any(String.class));
    }

    private void verifieAppelsDansBonOrdre() throws Throwable {
	verify(validationDonnees).validerDonneeCreationEmploye(any(String.class), any(String.class));
	verify(departement)
		.creerNouvelEmploye(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
    }

}