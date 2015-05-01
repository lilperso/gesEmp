package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceConsulterAssignationsException;

public class ServiceConsulterAssignations
{
    private Departement departement;
    private FabriqueProjetDTO fabriqueProjetDTO;

    public ServiceConsulterAssignations() {
	fabriqueProjetDTO = new FabriqueProjetDTO();
    }

    public ServiceConsulterAssignations(String adresseCourrielDuGestionnaire) throws ServiceConsulterAssignationsException {
	try {
	    departement = new Departement(adresseCourrielDuGestionnaire);
	    fabriqueProjetDTO = new FabriqueProjetDTO();
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException | NestPasGestionnaireException e) {
	    throw new ServiceConsulterAssignationsException(e.getMessage());
	}
    }

    public List<ProjetDTO> obtenirListeProjetsDTOAssignesARessourceHumaine(String adresseCourrielRessourceHumaine) {
	List<Projet> listeProjets = departement.obtenirListeDesProjetsAssignesARessourceHumaine(adresseCourrielRessourceHumaine);
	List<ProjetDTO> listeProjetsDTO = preparerListeProjetsDTOAssignesARessourceHumaine(listeProjets, adresseCourrielRessourceHumaine);
	return listeProjetsDTO;
    }

    private List<ProjetDTO> preparerListeProjetsDTOAssignesARessourceHumaine(List<Projet> listeProjets, String adresseCourrielRessourceHumaine) {
	return fabriqueProjetDTO.creerListeProjetDTO(listeProjets);
    }
}
