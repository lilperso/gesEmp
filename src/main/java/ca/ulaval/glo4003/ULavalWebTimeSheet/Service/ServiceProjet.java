package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.NomProjetInvalideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetInexistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheDejaExistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ServiceProjet
{
    private Departement departement;
    private ValidationDonneeProjet validation;

    public ServiceProjet() {
    }

    public ServiceProjet(String adresseCourrielGestionnaireDepartement) throws ServiceProjetException {
	try {
	    departement = new Departement(adresseCourrielGestionnaireDepartement);
	    validation = new ValidationDonneeProjet();
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException | NestPasGestionnaireException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public ProjetDTO obtenirProjetDTOParNomProjet(String nomProjet) throws ServiceProjetException {
	Projet projetRecherche;
	try {
	    projetRecherche = departement.obtenirProjetParNomProjet(nomProjet);
	} catch (ProjetInexistantException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return genererProjetDTOAvecProjet(projetRecherche);
    }

    public ProjetDTO obtenirProjetDTOParIndexProjet(int indexProjet) throws ServiceProjetException {
	Projet projetRecherche;
	try {
	    projetRecherche = departement.obtenirProjetParIndex(indexProjet);
	} catch (ProjetInexistantException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return genererProjetDTOAvecProjet(projetRecherche);
    }

    public TacheDTO obtenirTacheDTOParIndexTache(ProjetDTO projetDTO, int indexTache) throws ServiceProjetException {
	TacheDTO tacheRecherchee;
	try {
	    tacheRecherchee = projetDTO.getListeTachesDTO().get(indexTache);
	} catch (Exception e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return tacheRecherchee;
    }

    public void modifierProjet(ProjetDTO projetDTOMisAjour, String ancienNomProjet) throws ServiceProjetException {
	try {
	    validation.validerDonneeProjet(projetDTOMisAjour.getNomProjet());
	    departement.modifierProjet(genererProjetAvecProjetDTO(projetDTOMisAjour), ancienNomProjet);
	} catch (ProjetInexistantException | ChampVideException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public void ajouterProjet(ProjetDTO nouveauProjetDTO) throws ServiceProjetException {
	try {
	    validation.validerDonneeProjet(nouveauProjetDTO.getNomProjet());
	    departement.ajouterProjet(genererProjetAvecProjetDTO(nouveauProjetDTO));
	} catch (ProjetDejaExistantException | NomProjetInvalideException | ChampVideException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public void supprimerProjet(String nomProjet) throws ServiceProjetException {
	try {
	    departement.supprimerProjet(nomProjet);
	} catch (ProjetInexistantException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public void ajouterTache(ProjetDTO projetDTO, TacheDTO nouvelleTacheDTO) throws ServiceProjetException {
	try {
	    validation.validerDonneeTache(nouvelleTacheDTO.getNomTache());
	    departement.ajouterTache(projetDTO.getNomProjet(), convertirTacheDTOEnTache(nouvelleTacheDTO));
	} catch (ProjetInexistantException | TacheDejaExistanteException | ChampVideException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public void supprimerTache(String nomProjet, String nomTacheASupprimer) throws ServiceProjetException {
	try {
	    departement.supprimerTache(nomProjet, nomTacheASupprimer);
	} catch (ProjetInexistantException | TacheInexistanteException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    public void modifierTache(String nomProjet, TacheDTO tacheMisAjourDTO, String ancienNomTache) throws ServiceProjetException {
	try {
	    validation.validerDonneeTache(tacheMisAjourDTO.getNomTache());
	    departement.modifierTache(nomProjet, convertirTacheDTOEnTache(tacheMisAjourDTO), ancienNomTache);
	} catch (ProjetInexistantException | TacheInexistanteException | ChampVideException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
    }

    private ProjetDTO genererProjetDTOAvecProjet(Projet projet) {
	return new ProjetDTO(projet.getNomProjet(), projet.getDescriptionProjet(), convertirListeTachesEnListeTachesDTO(projet.getListeTaches()),
		projet.getListeRessourceHumainesAssignees());
    }

    private Projet genererProjetAvecProjetDTO(ProjetDTO projetDTO) {
	List<Tache> listeTache = convertirListeTachesDTOEnListeTaches(projetDTO.getListeTachesDTO());
	Projet projet = new Projet(projetDTO.getNomProjet(), projetDTO.getDescriptionProjet(), listeTache, projetDTO.getListeRessourceHumainesAssignees());
	return projet;
    }

    private TacheDTO convertirTacheEnTacheDTO(Tache tache) {
	return new TacheDTO(tache.getNomTache(), tache.getDescriptionTache(), tache.getListeRessourceHumainesAssignees());
    }

    private List<TacheDTO> convertirListeTachesEnListeTachesDTO(List<Tache> listeTache) {
	List<TacheDTO> listeTachesDTO = new ArrayList<TacheDTO>();
	for (Tache tache : listeTache) {
	    listeTachesDTO.add(convertirTacheEnTacheDTO(tache));
	}
	return listeTachesDTO;
    }

    private Tache convertirTacheDTOEnTache(TacheDTO tacheDTO) {
	return new Tache(tacheDTO.getNomTache(), tacheDTO.getDescriptionTache(), tacheDTO.getListeRessourceHumaineAssignees());
    }

    private List<Tache> convertirListeTachesDTOEnListeTaches(List<TacheDTO> listeTachesDTO) {

	List<Tache> listeTaches = new ArrayList<Tache>();
	for (TacheDTO tacheDTO : listeTachesDTO) {
	    listeTaches.add(convertirTacheDTOEnTache(tacheDTO));
	}

	return listeTaches;
    }

    public List<ProjetDTO> obtenirListeProjetsDTO() {
	List<ProjetDTO> listeProjetsDTO = new ArrayList<ProjetDTO>();
	for (Projet projet : departement.obtenirListeDesProjet()) {
	    listeProjetsDTO.add(genererProjetDTOAvecProjet(projet));
	}
	return listeProjetsDTO;
    }
}
