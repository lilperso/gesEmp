package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.UtilisateursSysteme;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceSecuriteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeConnexion;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ServiceSecurite
{
    private UtilisateursSysteme utilisateursSysteme;
    private AuthentificationDTO authentificationDTO;
    private ValidationDonneeConnexion validation;

    public ServiceSecurite() {
	utilisateursSysteme = new UtilisateursSysteme();
	validation = new ValidationDonneeConnexion();
    }

    public AuthentificationDTO verifierAuthentificationUtilisateur(String adresseCourriel, String motDePasse) throws ServiceSecuriteException {
	try {
	    validation.validerDonnneeConnexion(adresseCourriel, motDePasse);
	    Utilisateur utilisateur = utilisateursSysteme.connecterUtilisateur(adresseCourriel, motDePasse);
	    authentificationDTO = new AuthentificationDTO(utilisateur);
	    return authentificationDTO;
	} catch (AdresseCourrielInexistanteException | MauvaisMotDePasseException | ChampVideException e) {
	    throw new ServiceSecuriteException(e.getMessage());
	}
    }

}
