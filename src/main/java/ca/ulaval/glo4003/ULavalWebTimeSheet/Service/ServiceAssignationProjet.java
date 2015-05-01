package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ListeAdresseCourrielEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceAssignationProjetException;

public class ServiceAssignationProjet
{
    private Departement departement;
    private FabriqueProjetDTO fabriqueProjetDTO;

    public ServiceAssignationProjet() {
    }

    public ServiceAssignationProjet(String adresseGestionnaire) throws ServiceAssignationProjetException {
	try {
	    departement = new Departement(adresseGestionnaire);
	    fabriqueProjetDTO = new FabriqueProjetDTO();
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException | NestPasGestionnaireException e) {
	    throw new ServiceAssignationProjetException(e.getMessage());
	}
    }

    public ListeAdresseCourrielEmployeDTO obtenirListeAdresseEmailEmployeDuGestionnaire() {
	ListeAdresseCourrielEmployeDTO listeEmailEmploye = new ListeAdresseCourrielEmployeDTO();
	listeEmailEmploye.setListeEmploye(departement.obtenirListeEmailEmploye());
	return listeEmailEmploye;
    }

    public List<ProjetDTO> obtenirListeDesProjets() {
	List<Projet> listeProjet = departement.obtenirListeDesProjet();
	List<ProjetDTO> listeProjetDTO = fabriqueProjetDTO.creerListeProjetDTO(listeProjet);
	return listeProjetDTO;
    }

    public void assignerLesTachesAuxEmployes(List<String> employeSelectionne, List<String> projetSelectionne, List<String> tacheSelectionnee) {
	departement.assignerLesProjetsEtTachesAuxEmployes(employeSelectionne,projetSelectionne,tacheSelectionnee);
    }

}
