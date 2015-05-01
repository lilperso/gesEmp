package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Convertisseurs.JodaTimeConvertisseur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

public class DepotDepartementXML extends DepotAbstrait implements DepotDepartement
{
    private static final String NEST_PAS_RH = "L'utilisateur n'est pas une ressource humaine";
    private static final String RESSOURCE_HUMAINE_DEJA_EXISTANT = "L'employe est déjà enregistré";
    private static final String NEST_PAS_GEST = "L'utilisateur n'est pas un gestionnaire";

    public DepotDepartementXML() {
	xStream.registerConverter(new JodaTimeConvertisseur());
    }

    @Override
    public RessourceHumaine obtenirRessourceHumaineAvecAdresseCourriel(String adresseCourriel) throws AdresseCourrielInexistanteException,
	    NestPasRessourceHumaineException {
	Utilisateur utilisateur;
	utilisateur = obtenirUtilisateurSelonAdresseCourriel(adresseCourriel);
	if (!(utilisateur instanceof RessourceHumaine)) {
	    throw new NestPasRessourceHumaineException(NEST_PAS_RH);
	}
	if (utilisateur instanceof Gestionnaire) {
	    return (Gestionnaire) utilisateur;
	}
	return (Employe) utilisateur;
    }

    @Override
    public void modifierRessourceHumaine(RessourceHumaine rhAJour) throws UtilisateurInexistantException {
	persisterChangementsUtilisateur(rhAJour);
    }

    @Override
    public void ajouterNouvelleRessourceHumaine(RessourceHumaine nouvelleRessourceHumaine) throws UtilisateurDejaExistantException {
	Utilisateurs utilisateurs = ajouterNouvelleRessourceHumaineAUtilisateurs(nouvelleRessourceHumaine);
	persisterChangementsXMLUtilisateurs(utilisateurs);
    }

    private Utilisateurs ajouterNouvelleRessourceHumaineAUtilisateurs(Utilisateur nouvelUtilisateur) throws UtilisateurDejaExistantException {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();

	if (utilisateurExisteDeja(nouvelUtilisateur, utilisateurs.getUtilisateurs())) {
	    throw new UtilisateurDejaExistantException(RESSOURCE_HUMAINE_DEJA_EXISTANT);
	}
	utilisateurs.getUtilisateurs().add(nouvelUtilisateur);

	return utilisateurs;
    }

    private boolean utilisateurExisteDeja(Utilisateur nouvelUtilisateur, List<Utilisateur> utilisateurs) {
	return utilisateurs.contains(nouvelUtilisateur);
    }

    @Override
    public List<RessourceHumaine> obtenirRessourcesHumainesDepartementales(String adresseCourrielGestionnaire) {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	List<Utilisateur> listeUtilisateur = obtientUtilisateursDansXML().getUtilisateurs();
	for (Utilisateur utilisateur : listeUtilisateur) {
	    obtenirRessourceHumaineDuSuperieur(adresseCourrielGestionnaire, listeRH, utilisateur);
	}

	return listeRH;
    }

    public void obtenirRessourceHumaineDuSuperieur(String adresseCourrielGestionnaire, List<RessourceHumaine> listeRH, Utilisateur utilisateur) {
	if (utilisateur instanceof RessourceHumaine) {
	    RessourceHumaine rh = (RessourceHumaine) utilisateur;
	    if (rh.getSuperieur() != null) {
		if (rh.getSuperieur().equals(adresseCourrielGestionnaire)) {
		    listeRH.add(rh);
		}
	    }

	}
    }

    @Override
    public Gestionnaire obtenirGestionnaireDepartemental(String adresseCourrielGestionnaire) throws AdresseCourrielInexistanteException,
	    NestPasRessourceHumaineException, NestPasGestionnaireException {
	RessourceHumaine resHumaine = obtenirRessourceHumaineAvecAdresseCourriel(adresseCourrielGestionnaire);
	if (!(resHumaine instanceof Gestionnaire)) {
	    throw new NestPasGestionnaireException(NEST_PAS_GEST);
	}
	return (Gestionnaire) resHumaine;
    }
}
