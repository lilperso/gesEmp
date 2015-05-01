package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.DepotDepartementXML;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasGestionnaireException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;

public class Departement
{
    private DepotDepartement depotDepartement;
    private Gestionnaire gestionnaire;

    public Departement() {
    }

    public Departement(String adresseGestionnaire) throws AdresseCourrielInexistanteException, NestPasRessourceHumaineException, NestPasGestionnaireException {
	depotDepartement = new DepotDepartementXML();
	gestionnaire = depotDepartement.obtenirGestionnaireDepartemental(adresseGestionnaire);
    }

    public void creerNouvelEmploye(String adresseResidence, String nom, String prenom, String adresseCourriel, String numTelephone, String motDePasse) {
	InformationsPersonnelles informationsPersonnelles = new InformationsPersonnelles(adresseResidence, nom, prenom, numTelephone);
	RessourceHumaine nouvelEmploye = gestionnaire.creerEmployeDepartemental(adresseCourriel, motDePasse, informationsPersonnelles,
		gestionnaire.getDepartement());
	depotDepartement.ajouterNouvelleRessourceHumaine(nouvelEmploye);
    }

    public List<RessourceHumaine> obtenirListeRessourcesHumainesDepartementales(String adresseDuGestionnaire) {
	return depotDepartement.obtenirRessourcesHumainesDepartementales(adresseDuGestionnaire);
    }

    public List<String> obtenirListeEmailEmploye() {
	List<RessourceHumaine> rH = depotDepartement.obtenirRessourcesHumainesDepartementales(gestionnaire.getAdresseCourriel());
	List<String> listeEmailEmploye = obtenirListeEmailSeulement(rH);
	return listeEmailEmploye;
    }

    public void assignerLesProjetsEtTachesAuxEmployes(List<String> employeSelectionne, List<String> projetSelectionne, List<String> tacheSelectionnee) {
	gestionnaire.assignerLesTaches(tacheSelectionnee, employeSelectionne);
	gestionnaire.assignerLesProjets(projetSelectionne, employeSelectionne);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public List<RessourceHumaine> obtenirEmployes(List<String> listeAdresseCourrielDesEmployes) {
	List<RessourceHumaine> rH = depotDepartement.obtenirRessourcesHumainesDepartementales(gestionnaire.getAdresseCourriel());
	List<RessourceHumaine> employesSelectionnes = obtenirLesEmployesSelonListeCourriel(rH, listeAdresseCourrielDesEmployes);
	return employesSelectionnes;
    }

    private List<RessourceHumaine> obtenirLesEmployesSelonListeCourriel(List<RessourceHumaine> rH, List<String> listeAdresseCourrielDesEmployes) {
	List<RessourceHumaine> listeRH = new ArrayList<RessourceHumaine>();
	for (RessourceHumaine ressourceHumaine : rH) {
	    if (listeAdresseCourrielDesEmployes.contains(ressourceHumaine.getAdresseCourriel())) {
		listeRH.add(ressourceHumaine);
	    }
	}
	return listeRH;
    }

    private List<String> obtenirListeEmailSeulement(List<RessourceHumaine> rH) {
	List<String> listeEmail = new ArrayList<String>();
	for (RessourceHumaine ressource : rH) {
	    listeEmail.add(ressource.getAdresseCourriel());
	}
	return listeEmail;
    }

    public List<Projet> obtenirListeDesProjet() {
	return gestionnaire.getListeProjets();
    }

    public List<Projet> obtenirListeDesProjetsAssignesARessourceHumaine(String adresseCourrielRessourceHumaine) {
	return gestionnaire.obtenirTacheAssigneACetEmploye(adresseCourrielRessourceHumaine);
    }

    public Projet obtenirProjetParIndex(int indexProjet) {
	List<Projet> listeProjets = obtenirListeDesProjet();
	return listeProjets.get(indexProjet);
    }

    public Projet obtenirProjetParNomProjet(String nomProjet) {
	return gestionnaire.obtenirProjet(nomProjet);
    }

    public void modifierProjet(Projet projetMisAJour, String ancienNomProjet) {
	gestionnaire.modifierProjet(projetMisAJour, ancienNomProjet);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void ajouterProjet(Projet nouveauProjet) {
	gestionnaire.ajouterProjet(nouveauProjet);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void ajouterTache(String nomProjet, Tache nouvelleTache) {
	gestionnaire.ajouterTache(nomProjet, nouvelleTache);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void supprimerTache(String nomProjet, String nomTacheASupprimer) {
	gestionnaire.supprimerTache(nomProjet, nomTacheASupprimer);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void modifierTache(String nomProjet, Tache tacheMisAJour, String ancienNomTache) {
	gestionnaire.modifierTache(nomProjet, tacheMisAJour, ancienNomTache);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void supprimerProjet(String nomProjet) {
	gestionnaire.supprimerProjet(nomProjet);
	depotDepartement.modifierRessourceHumaine(gestionnaire);
    }

    public void modifierTauxHoraireRessourceHumaine(String adresseCourriel, String tauxhoraire) {
	RessourceHumaine resHumaine = depotDepartement.obtenirRessourceHumaineAvecAdresseCourriel(adresseCourriel);
	resHumaine.setTauxHoraire(Double.parseDouble(tauxhoraire));
	depotDepartement.modifierRessourceHumaine(resHumaine);
    }

}
