package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.PersistanceException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceCreationRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeCreationRessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ServiceCreationRessourceHumaine
{
    private Departement departement;
    private ValidationDonneeCreationRessourceHumaine validation;

    public ServiceCreationRessourceHumaine() {
    }

    public ServiceCreationRessourceHumaine(String adresseCourrielGestionnaire) {
	departement = new Departement(adresseCourrielGestionnaire);
	validation = new ValidationDonneeCreationRessourceHumaine();
    }

    public void demanderCreationNouvelEmploye(String adresseResidence, String nom, String prenom, String numTelephone, String adresseCourriel, String motDePasse)
	    throws ServiceCreationRessourceHumaineException {
	try {
	    validation.validerDonneeCreationEmploye(adresseCourriel, motDePasse);
	    departement.creerNouvelEmploye(adresseResidence, nom, prenom, numTelephone, adresseCourriel, motDePasse);
	} catch (PersistanceException | ChampVideException e) {
	    throw new ServiceCreationRessourceHumaineException(e.getMessage());
	}
    }

    public List<InfoEmployeDTO> obtenirDtoListeEmploye(String adresseDuGestionnaire) {
	List<InfoEmployeDTO> listeEmploye = new ArrayList<InfoEmployeDTO>();
	List<RessourceHumaine> listeRessourceHumaine = departement.obtenirListeRessourcesHumainesDepartementales(adresseDuGestionnaire);

	if (listeRessourceHumaine.size() != 0) {
	    for (RessourceHumaine employe : listeRessourceHumaine) {
		listeEmploye.add(new InfoEmployeDTO(employe.getAdresseCourriel(), employe.getInformationsPersonnelles().getPrenom(), employe
			.getInformationsPersonnelles().getNom(), employe.getTauxHoraire()));
	    }
	}

	return listeEmploye;
    }
}
