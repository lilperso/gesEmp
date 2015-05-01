package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeAvecPeriodesDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.DepotDepartementXML;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceApprobationException;

public class ServiceApprobation
{
    private DepotDepartement depDepot;

    public ServiceApprobation() {
	this.depDepot = new DepotDepartementXML();
    }

    public InfoEmployeAvecPeriodesDTO obtenirListeDesPeriodesAvecDemandesPourUnEmploye(String adresseCouriel) throws ServiceApprobationException {

	InfoEmployeAvecPeriodesDTO periodesPourUnEmployeDTO;

	try {
	    RessourceHumaine rH = depDepot.obtenirRessourceHumaineAvecAdresseCourriel(adresseCouriel);
	    periodesPourUnEmployeDTO = rH.obtenirListeDesPeriodesAvecDemandePourUnEmployeDTO();
	} catch (Exception ex) {
	    throw new ServiceApprobationException(ex.getMessage());
	}

	return periodesPourUnEmployeDTO;
    }

    public void trouverEtApprouverDepense(String adresseCourriel, DateTime dateQuart) throws ServiceApprobationException {
	try {
	    RessourceHumaine rH = depDepot.obtenirRessourceHumaineAvecAdresseCourriel(adresseCourriel);
	    rH.approuverDepense(dateQuart);

	    depDepot.modifierRessourceHumaine(rH);
	} catch (Exception ex) {
	    throw new ServiceApprobationException(ex.getMessage());
	}
    }

    public void trouverEtApprouverDeplacement(String adresseCourriel, DateTime dateQuart) throws ServiceApprobationException {
	try {
	    RessourceHumaine rH = depDepot.obtenirRessourceHumaineAvecAdresseCourriel(adresseCourriel);
	    rH.approuverDeplacement(dateQuart);

	    depDepot.modifierRessourceHumaine(rH);
	} catch (Exception ex) {
	    throw new ServiceApprobationException(ex.getMessage());
	}
    }
}
