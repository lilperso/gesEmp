package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotUtilisateurs;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;

@RunWith(MockitoJUnitRunner.class)
public class UtilisateursSystemeTest
{
    private static final String ADRESSE_COURRIEL = "adresseCourriel";
    private static final String MOT_DE_PASSE = "motDePasse";

    @Mock
    private DepotUtilisateurs depotUtilisateurs;

    @InjectMocks
    private UtilisateursSysteme utilisateursSysteme;

    @Test
    public void lorsqueConnecteUtilisateurAvecCourrielEtMotDePasseRetourneUtilisateur() {
	preparerComportementMockDepotUtilisateurs();
	utilisateursSysteme.connecterUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE);
	verify(depotUtilisateurs).trouverUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE);
    }

    private void preparerComportementMockDepotUtilisateurs() {
	Utilisateur utilisateur = mock(Utilisateur.class, Mockito.CALLS_REAL_METHODS);
	utilisateur.setAdresseCourriel(ADRESSE_COURRIEL);
	utilisateur.setMotDePasse(MOT_DE_PASSE);
	utilisateur.setRole(Utilisateur.ADMINISTRATEUR);
	when(depotUtilisateurs.trouverUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE)).thenReturn(utilisateur);
    }

}
