package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotUtilisateurs;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceConsulterAssignationsException;

@RunWith(MockitoJUnitRunner.class)
public class ServiceConsulterAssignationsTest
{
    private static final String ADRESSE_COURRIEL_GESTIONNAIRE = "philkelly@gestionnaire.com";
    private static final String ADRESSE_COURRIEL_EMPLOYE_ASSIGNE = "assigne@employe.com";
    private static final String ADRESSE_COURRIEL_EMPLOYE_NON_ASSIGNE = "nonassigner@employe.com";
    private static final String NOM_PROJET_1 = "Projet 1";
    private static final String DESCRIPTION_PROJET_1 = "Description du projet 1";
    private static final String NOM_TACHE_1 = "tache 1";
    private static final String DESCRIPTION_TACHE_1 = "Description de la tache 1";
    
    private List<Projet> listeProjets = new ArrayList<Projet>();
    private List<Tache> listeTaches = new ArrayList<Tache>();
    private List<ProjetDTO> listeProjetsDTO = new ArrayList<ProjetDTO>();
    private List<TacheDTO> listeTachesDTO = new ArrayList<TacheDTO>();
    private List<String> listeRessourceHumainesAssignes = new ArrayList<String>();

    @Mock 
    private Departement departement;
    
    @Mock
    private DepotUtilisateurs depotUtilisateurs;
    
    @Mock 
    private FabriqueProjetDTO fabriqueProjetDTO;
    
    @InjectMocks
    private ServiceConsulterAssignations serviceConsulterAssignations = new ServiceConsulterAssignations();
    
    @Test
    public void onObtientUneListeDeProjetsAvecTachesAssigneeARessoureHumaineSiEmployeAssigne() throws Throwable {
	preparerComportementMockUtilisateurDepot();
	preparerComportementMockDepartement();
	preparerFactoryProjetDTO();
	
	List<ProjetDTO> projetsDTO = serviceConsulterAssignations.obtenirListeProjetsDTOAssignesARessourceHumaine(ADRESSE_COURRIEL_EMPLOYE_ASSIGNE);
	assertEquals(1, projetsDTO.size());
    }
    
    @Test
    public void onObtientUneListeDeProjetsVideSiEmployeNonAssigne() throws Throwable {
	preparerComportementMockUtilisateurDepot();
	preparerComportementMockDepartement();
	preparerFactoryProjetDTO();
	
	List<ProjetDTO> listeProjetsDTO = serviceConsulterAssignations.obtenirListeProjetsDTOAssignesARessourceHumaine(ADRESSE_COURRIEL_EMPLOYE_NON_ASSIGNE);
	assertTrue(listeProjetsDTO.isEmpty());
    }

    private void preparerComportementMockDepartement() {
	listeRessourceHumainesAssignes.add(ADRESSE_COURRIEL_EMPLOYE_ASSIGNE);
	Projet projet = new Projet(NOM_PROJET_1, DESCRIPTION_PROJET_1, listeTaches, listeRessourceHumainesAssignes);
	Tache tache = new Tache(NOM_TACHE_1, DESCRIPTION_TACHE_1, listeRessourceHumainesAssignes);
	projet.ajouterTache(tache);
	listeProjets.add(projet);
	when(departement.obtenirListeDesProjetsAssignesARessourceHumaine(ADRESSE_COURRIEL_EMPLOYE_ASSIGNE)).thenReturn(listeProjets);
    }
    
    private void preparerComportementMockUtilisateurDepot() throws ServiceConsulterAssignationsException {
	RessourceHumaine ressourceHumaine = mock(RessourceHumaine.class, Mockito.CALLS_REAL_METHODS);
	ressourceHumaine.setAdresseCourriel(ADRESSE_COURRIEL_EMPLOYE_ASSIGNE);
	ressourceHumaine.setSuperieur(ADRESSE_COURRIEL_GESTIONNAIRE);
	when(depotUtilisateurs.obtenirUtilisateurAvecAdresseCourriel(ADRESSE_COURRIEL_EMPLOYE_ASSIGNE)).thenReturn(ressourceHumaine);
    }
    
    private void preparerFactoryProjetDTO() {
	ProjetDTO projetDTO = new ProjetDTO(NOM_PROJET_1, DESCRIPTION_PROJET_1, listeTachesDTO, listeRessourceHumainesAssignes);
	TacheDTO tacheDTO = new TacheDTO(NOM_TACHE_1, DESCRIPTION_TACHE_1, listeRessourceHumainesAssignes);
	listeTachesDTO.add(tacheDTO);
	projetDTO.setListeTachesDTO(listeTachesDTO);
	listeProjetsDTO.add(projetDTO);
	when(fabriqueProjetDTO.creerListeProjetDTO(listeProjets)).thenReturn(listeProjetsDTO);
    }
}
