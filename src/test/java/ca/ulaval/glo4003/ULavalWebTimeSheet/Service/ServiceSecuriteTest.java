package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.UtilisateursSysteme;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;

@RunWith(MockitoJUnitRunner.class)
public class ServiceSecuriteTest
{
    private static final String ADRESSE_COURRIEL = "AdresseCourriel";
    private static final String MOT_DE_PASSE = "MotDePasse";

    @Mock
    private UtilisateursSysteme utilisateursSysteme;

    @InjectMocks
    private ServiceSecurite serviceSecurite;

    @Test
    public void verifierAuthentificationUtilisateurAppelleTrouverUtilisateurDeUtilisateurDepot() throws Throwable {
	preparerComportementMockUtilisateursSysteme();
	serviceSecurite.verifierAuthentificationUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE);
	verify(utilisateursSysteme).connecterUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE);
    }

    @Test
    public void verifierAuthentificationUtilisateurRetourneDTOcontenantCourrielEtRole() throws Throwable {
	preparerComportementMockUtilisateursSysteme();
	AuthentificationDTO authentificationDTO = serviceSecurite.verifierAuthentificationUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE);
	assertTrue(dtoContientCourrielEtRole(authentificationDTO));
    }

    private void preparerComportementMockUtilisateursSysteme() throws MauvaisMotDePasseException, AdresseCourrielInexistanteException {
	Utilisateur utilisateur = mock(Utilisateur.class, Mockito.CALLS_REAL_METHODS);
	utilisateur.setAdresseCourriel(ADRESSE_COURRIEL);
	utilisateur.setMotDePasse(MOT_DE_PASSE);
	utilisateur.setRole(Utilisateur.ADMINISTRATEUR);
	when(utilisateursSysteme.connecterUtilisateur(ADRESSE_COURRIEL, MOT_DE_PASSE)).thenReturn(utilisateur);
    }

    private boolean dtoContientCourrielEtRole(AuthentificationDTO authentificationDTO) {
	return authentificationDTO.getAdresseCourriel().equals(ADRESSE_COURRIEL) && authentificationDTO.getRole().equals(Utilisateur.ADMINISTRATEUR);
    }

}
