package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceModifierEmployeException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneModificationEmploye;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.MauvaisFormatVariableException;

public class ServiceModifierEmploye
{
    private Departement departement;
    private ValidationDonneModificationEmploye validation;

    public ServiceModifierEmploye(){
    }
    
    public ServiceModifierEmploye(String adresseCourrielGestionnaire) {
	departement = new Departement(adresseCourrielGestionnaire);
	this.validation = new ValidationDonneModificationEmploye();
    }

    public void mettreAJourEmploye(String adresseCourriel, String tauxhoraire) throws ServiceModifierEmployeException {
	try {
	    validation.validerDonnneeModifcationEmploye(tauxhoraire);
	    departement.modifierTauxHoraireRessourceHumaine(adresseCourriel, tauxhoraire);
	    
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException | UtilisateurInexistantException | ChampVideException
		| MauvaisFormatVariableException e) {
	    throw new ServiceModifierEmployeException(e.getMessage());
	}

    }
}
