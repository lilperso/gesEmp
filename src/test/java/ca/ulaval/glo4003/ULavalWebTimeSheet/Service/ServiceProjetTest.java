package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeProjet;

@RunWith(MockitoJUnitRunner.class)
public class ServiceProjetTest
{

    private static final String NOM_PROJET = "Projet";
    private static final String DESCRIPTION_PROJET = "Description du projet";
    private static final String NOM_TACHE = "tache";
    private static final String DESCRIPTION_TACHE = "Description de la tache";

    private List<String> listeRessourceHumainesAssigneesAProjetVide = new ArrayList<String>();
    private List<String> listeRessourceHumainesAssigneesATacheVide = new ArrayList<String>();
    private List<TacheDTO> listeTacheDTOVide = new ArrayList<TacheDTO>();
    private TacheDTO tacheDTO = new TacheDTO(NOM_TACHE, DESCRIPTION_TACHE, listeRessourceHumainesAssigneesATacheVide);
    private ProjetDTO projetDTO = new ProjetDTO(NOM_PROJET, DESCRIPTION_PROJET, listeTacheDTOVide, listeRessourceHumainesAssigneesAProjetVide);

    @Mock
    ValidationDonneeProjet validation;

    @Mock
    private Departement departement;

    @InjectMocks
    private ServiceProjet serviceProjet = new ServiceProjet();

    @Test
    public void lorsqueAjouterProjet() throws Throwable {
	doNothing().when(validation).validerDonneeProjet(any(String.class));
	doNothing().when(departement).ajouterProjet(any(Projet.class));

	serviceProjet.ajouterProjet(projetDTO);

	verify(validation).validerDonneeProjet(any(String.class));
	verify(departement).ajouterProjet(any(Projet.class));
    }

    @Test
    public void lorsqueAjouterTacheAUnProjet() throws Throwable {
	doNothing().when(validation).validerDonneeTache(any(String.class));
	doNothing().when(departement).ajouterTache(any(String.class), any(Tache.class));

	serviceProjet.ajouterTache(projetDTO, tacheDTO);

	verify(validation).validerDonneeTache(any(String.class));
	verify(departement).ajouterTache(any(String.class), any(Tache.class));
    }

    @Test
    public void lorsqueSupprimerProjet() throws Throwable {
	doNothing().when(departement).supprimerProjet(any(String.class));

	serviceProjet.supprimerProjet(NOM_PROJET);

	verify(departement).supprimerProjet(any(String.class));
    }

    @Test
    public void lorsqueSupprimerTache() throws Throwable {
	doNothing().when(departement).supprimerTache(any(String.class), any(String.class));

	serviceProjet.supprimerTache(NOM_PROJET, NOM_TACHE);

	verify(departement).supprimerTache(any(String.class), any(String.class));
    }

    @Test
    public void lorsqueModifierProjet() throws Throwable {
	doNothing().when(validation).validerDonneeProjet(any(String.class));
	doNothing().when(departement).modifierProjet(any(Projet.class), any(String.class));

	serviceProjet.modifierProjet(projetDTO, NOM_PROJET);

	verify(validation).validerDonneeProjet(any(String.class));
	verify(departement).modifierProjet(any(Projet.class), any(String.class));
    }

    @Test
    public void lorsqueModifierTache() throws Throwable {
	doNothing().when(validation).validerDonneeProjet(any(String.class));
	doNothing().when(departement).modifierTache(any(String.class), any(Tache.class), any(String.class));

	serviceProjet.modifierTache(NOM_PROJET, tacheDTO, NOM_TACHE);

	verify(validation).validerDonneeTache(any(String.class));
	verify(departement).modifierTache(any(String.class), any(Tache.class), any(String.class));
    }
}
