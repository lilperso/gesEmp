//package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetDejaExistantException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetInexistantException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheDejaExistanteException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.TacheInexistanteException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.DepotDepartementXML;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeProjet;
//import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;
//
//public class ServiceProjetF
//{
//    private DepotDepartement departementDepot;
//    private Gestionnaire gestionnaireProjet;
//    private ValidationDonneeProjet validation;
//
//    public ServiceProjetF() {
//
//	this.departementDepot = new DepotDepartementXML();
//	this.validation = new ValidationDonneeProjet();
//    }
//
//    public ProjetDTO obtenirProjetDTOParNomProjet(String courrielResponsable, String nomProjet) throws ServiceProjetException {
//	try {
//	    ProjetDTO projetDTO = new ProjetDTO();
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    Projet projetrecherhcer = new Projet();
//	    projetrecherhcer = gestionnaireProjet.obtenirProjet(nomProjet);
//
//	    projetDTO = genererProjetDTOAvecProjet(projetrecherhcer);
//	    return projetDTO;
//	} catch (UtilisateurInexistantException | ProjetInexistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException e) {
//	    throw new ServiceProjetException(e.getMessage());
//	}
//    }
//
//    public void modifierProjet(String courrielResponsable, ProjetDTO projetDTOMisAjour, String ancienNomProjet) throws ServiceProjetException {
//
//	try {
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    gestionnaireProjet.modifierProjet(genererProjetAvecProjetDTO(projetDTOMisAjour), ancienNomProjet);
//	    departementDepot.modifierRessourceHumaine(gestionnaireProjet);
//
//	} catch (UtilisateurInexistantException | ProjetInexistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//
//    }
//
//    public void ajouterProjet(String courrielResponsable, ProjetDTO nouveauProjetDTO) throws ServiceProjetException {
//
//	try {
//	    validation.validerDonneeProjet(nouveauProjetDTO.getNomProjet());
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    gestionnaireProjet.ajouterProjet(genererProjetAvecProjetDTO(nouveauProjetDTO));
//	    departementDepot.modifierRessourceHumaine(gestionnaireProjet);
//	} catch (UtilisateurInexistantException | ProjetDejaExistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException | ChampVideException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//
//    }
//
//    public void ajouterTache(String courrielResponsable, String nomProjet, TacheDTO nouvelleTacheDTO) throws ServiceProjetException {
//	try {
//
//	    validation.validerDonneeTache(nouvelleTacheDTO.getNomTache());
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    gestionnaireProjet.ajouterTache(nomProjet, convertirTacheDTOEnTache(nouvelleTacheDTO));
//	    departementDepot.modifierRessourceHumaine(gestionnaireProjet);
//
//	} catch (UtilisateurInexistantException | ProjetInexistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException|TacheDejaExistanteException | ChampVideException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//
//    }
//
//    public void supprimerTache(String courrielResponsable, String nomProjet, String nomTacheASupprimer) throws ServiceProjetException {
//	try {
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    gestionnaireProjet.supprimerTache(nomProjet, nomTacheASupprimer);
//	    departementDepot.modifierRessourceHumaine(gestionnaireProjet);
//	} catch (UtilisateurInexistantException | ProjetInexistantException |TacheInexistanteException| AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//
//    }
//
//    public void supprimerProjet(String adresseDuGestionnaire, String nomProjet) throws ServiceProjetException {
//	try {
//	    Gestionnaire gestionnaire = departementDepot.obtenirGestionnaireDepartemental(adresseDuGestionnaire);
//	    gestionnaire.supprimerProjet(nomProjet);
//	    departementDepot.modifierRessourceHumaine(gestionnaire);
//	} catch (UtilisateurInexistantException | ProjetInexistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//
//    }
//
//    public void modifierTache(String courrielResponsable, String nomProjet, TacheDTO tacheMisAjourDTO) throws ServiceProjetException {
//	try {
//
//	    gestionnaireProjet = departementDepot.obtenirGestionnaireDepartemental(courrielResponsable);
//	    gestionnaireProjet.modifierTache(nomProjet, convertirTacheDTOEnTache(tacheMisAjourDTO));
//	    departementDepot.modifierRessourceHumaine(gestionnaireProjet);
//	}
//
//	catch (UtilisateurInexistantException | ProjetInexistantException | AdresseCourrielInexistanteException | NestPasRessourceHumaineException
//		| NestPasGestionnaireException|TacheInexistanteException e) {
//	    throw new ServiceProjetException(e.getMessage());
//
//	}
//    }
//
//    private ProjetDTO genererProjetDTOAvecProjet(Projet projet) {
//	return new ProjetDTO(projet.getNomProjet(), projet.getDescriptionProjet(), convertirListeTachesEnListeTachesDTO(projet.getListeTaches()),
//		projet.getListeRessourceHumainesAssignees());
//    }
//
//    private Projet genererProjetAvecProjetDTO(ProjetDTO projetDTO) {
//	Projet projet = new Projet();
//	projet.setNomProjet(projetDTO.getNomProjet());
//	projet.setDescriptionProjet(projetDTO.getDescriptionProjet());
//	projet.setListeRessourceHumainesAssignees(projetDTO.getListeRessourceHumainesAssignees());
//	projet.setListeTaches(convertirListeTachesDTOEnListeTaches(projetDTO.getListeTachesDTO()));
//
//	return projet;
//    }
//
//    private TacheDTO convertirTacheEnTacheDTO(Tache tache) {
//	return new TacheDTO(tache.getNomTache(), tache.getDescriptionTache(), tache.getListeRessourceHumainesAssignees());
//    }
//
//    private List<TacheDTO> convertirListeTachesEnListeTachesDTO(List<Tache> listeTache) {
//	List<TacheDTO> listeTachesDTO = new ArrayList<TacheDTO>();
//	for (Tache tache : listeTache) {
//	    listeTachesDTO.add(convertirTacheEnTacheDTO(tache));
//	}
//	return listeTachesDTO;
//    }
//
//    private Tache convertirTacheDTOEnTache(TacheDTO tacheDTO) {
//	return new Tache(tacheDTO.getNomTache(), tacheDTO.getDescriptionTache(), tacheDTO.getListeRessourceHumaineAssignees());
//    }
//
//    private List<Tache> convertirListeTachesDTOEnListeTaches(List<TacheDTO> listeTachesDTO) {
//
//	List<Tache> listeTaches = new ArrayList<Tache>();
//	for (TacheDTO tacheDTO : listeTachesDTO) {
//	    listeTaches.add(convertirTacheDTOEnTache(tacheDTO));
//	}
//
//	return listeTaches;
//    }
//
//    public List<ProjetDTO> obtenirListeProjetsDTO(String adresseDuGestionnaire) throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException,
//	    NestPasGestionnaireException {
//	List<ProjetDTO> listeProjetsDTO = new ArrayList<ProjetDTO>();
//
//	Gestionnaire gestionnaire = departementDepot.obtenirGestionnaireDepartemental(adresseDuGestionnaire);
//
//	for (Projet projet : gestionnaire.getListeProjets()) {
//	    listeProjetsDTO.add(genererProjetDTOAvecProjet(projet));
//	}
//
//	return listeProjetsDTO;
//    }
//}
