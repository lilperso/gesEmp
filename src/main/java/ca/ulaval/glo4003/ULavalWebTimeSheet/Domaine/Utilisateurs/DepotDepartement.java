package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

public interface DepotDepartement
{
    public RessourceHumaine obtenirRessourceHumaineAvecAdresseCourriel(String adresseCourriel) throws AdresseCourrielInexistanteException,
	    NestPasRessourceHumaineException;

    public void modifierRessourceHumaine(RessourceHumaine rhAJour) throws UtilisateurInexistantException;

    public void ajouterNouvelleRessourceHumaine(RessourceHumaine nouvelleRessourceHumaine) throws UtilisateurDejaExistantException;

    public List<RessourceHumaine> obtenirRessourcesHumainesDepartementales(String adresseCourrielGestionnaire);

    public Gestionnaire obtenirGestionnaireDepartemental(String adresseCourrielGestionnaire) throws AdresseCourrielInexistanteException,
	    NestPasRessourceHumaineException, NestPasGestionnaireException;

}
