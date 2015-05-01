package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.NomProjetInvalideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.NomTacheInvalideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions.ProjetInexistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires.UtilitaireChaineDeCaracteres;

public class Gestionnaire extends RessourceHumaine
{
    private static final String PROJET_DEJA_EXISTANT = "Le projet existe deja";
    private static final String NOM_PROJET_VIDE = "Le nom du projet ne peut être vide";
    private static final String NOM_TACHE_VIDE = "Le nom de la tache ne peut être vide";

    private FabriqueUtilisateur factoryUtilisateur;
    private Boolean estGestionnaireDeGestionnaire;
    private List<Projet> listeProjets = new ArrayList<Projet>();
    private UtilitaireChaineDeCaracteres utilitaireChaine = new UtilitaireChaineDeCaracteres();

    public Gestionnaire() {
	factoryUtilisateur = new FabriqueUtilisateur();
    }

    public Gestionnaire(String adresseCourriel, String motDePasse, String role, String superieurImmediat, InformationsPersonnelles informationsPersonnelles,
	    List<Periode> listePeriodes, List<Projet> listeProjets, String departement) {
	setAdresseCourriel(adresseCourriel);
	setMotDePasse(motDePasse);
	setRole(role);
	setSuperieur(superieurImmediat);
	setInformationsPersonnelles(informationsPersonnelles);
	setListePeriodes(listePeriodes);
	setListeProjets(listeProjets);
	setDepartement(departement);
	factoryUtilisateur = new FabriqueUtilisateur();
    }

    public Boolean EstGestionnaireDeGestionnaire() {
	return estGestionnaireDeGestionnaire;
    }

    public void setEstGestionnaireDeGestionnaire(boolean b) {
	estGestionnaireDeGestionnaire = b;
    }

    public List<Projet> getListeProjets() {
	return listeProjets;
    }

    public void setListeProjets(List<Projet> listeProjets) {
	this.listeProjets = listeProjets;
    }

    public RessourceHumaine creerEmployeDepartemental(String adresseCourriel, String motDePasse, InformationsPersonnelles infoPersonnelles, String departement) {

	RessourceHumaine nouvelEmploye;

	if (estGestionnaireDeGestionnaire) {
	    nouvelEmploye = factoryUtilisateur.creerGestionnaire(adresseCourriel, motDePasse, getAdresseCourriel(), infoPersonnelles, departement);
	} else {
	    nouvelEmploye = factoryUtilisateur.creerEmploye(adresseCourriel, motDePasse, getAdresseCourriel(), infoPersonnelles, getDepartement());
	}

	return nouvelEmploye;
    }

    public void ajouterProjet(Projet nouveauProjet) {
	validerAjoutProjet(nouveauProjet);
	listeProjets.add(nouveauProjet);
    }

    private void validerAjoutProjet(Projet projet) {
	validerNomProjet(projet);
	if (projetExiste(projet.getNomProjet())) {
	    throw new ProjetDejaExistantException(PROJET_DEJA_EXISTANT);
	}
    }

    private void validerNomProjet(Projet projet) {
	if (estNomProjetInvalide(projet)) {
	    throw new NomProjetInvalideException(NOM_PROJET_VIDE);
	}
    }

    private boolean estNomProjetInvalide(Projet nouveauProjet) {
	return nouveauProjet.getNomProjet().equals("");
    }

    public void modifierProjet(Projet projetMisAjour, String ancienNomProjet) {
	validerNomProjet(projetMisAjour);
	Projet ancienProjet = obtenirProjet(ancienNomProjet);
	listeProjets.remove(ancienProjet);
	listeProjets.add(projetMisAjour);
    }

    public Projet obtenirProjet(String nomProjet) {
	for (Projet projet : listeProjets) {
	    if (projet.getNomProjet().equals(nomProjet)) {
		return projet;
	    }
	}
	throw new ProjetInexistantException(nomProjet);
    }

    public void supprimerProjet(String nomProjet) {
	Projet projetASupprimer = obtenirProjet(nomProjet);
	listeProjets.remove(projetASupprimer);
    }

    public void ajouterTache(String nomProjet, Tache nouvelleTache) {
	validerNomTache(nouvelleTache);
	Projet projet = obtenirProjet(nomProjet);
	projet.ajouterTache(nouvelleTache);
    }

    private void validerNomTache(Tache nouvelleTache) {
	if (nouvelleTache.getNomTache().equals("")) {
	    throw new NomTacheInvalideException(NOM_TACHE_VIDE);
	}
    }

    public void modifierTache(String nomProjet, Tache tacheMisAJour, String ancienNomTache) {
	validerNomTache(tacheMisAJour);
	Projet projet = obtenirProjet(nomProjet);
	projet.supprimerTache(ancienNomTache);
	projet.ajouterTache(tacheMisAJour);
    }

    public void supprimerTache(String nomProjet, String nomTache) {
	Projet projet = obtenirProjet(nomProjet);
	projet.supprimerTache(nomTache);
    }

    private boolean projetExiste(String nomProjet) {
	for (Projet projet : listeProjets) {
	    if (projet.getNomProjet().equals(nomProjet)) {
		return true;
	    }
	}
	return false;
    }

    public void assignerLesTaches(List<String> tacheSelectionnee, List<String> employeSelectionne) {
	for (String nomTacheEtProjet : tacheSelectionnee) {
	    String nomTache = utilitaireChaine.obtenirNomTacheSeulement(nomTacheEtProjet);
	    String nomProjet = utilitaireChaine.obtenirNomProjetSeulement(nomTacheEtProjet);
	    Projet projet = obtenirProjet(nomProjet);
	    projet.assignerLesEmployesALaTache(nomTache, employeSelectionne);
	}

    }

    public void assignerLesProjets(List<String> projetSelectionne, List<String> employeSelectionne) {
	for (String nomProjet : projetSelectionne) {
	    Projet projet = obtenirProjet(nomProjet);
	    projet.assignerLesEmployesAToutesLesTaches(employeSelectionne);
	}
    }

    public List<Projet> obtenirTacheAssigneACetEmploye(String adresseCourrielRessourceHumaine) {
	List<Projet> listeProjetDeLemploye = new ArrayList<Projet>();
	for (Projet projet : listeProjets) {
	    if (projet.estAssigneAuProjet(adresseCourrielRessourceHumaine)) {
		listeProjetDeLemploye.add(obtenirTacheDeLemploye(projet, adresseCourrielRessourceHumaine));
	    }
	}
	return listeProjetDeLemploye;
    }

    private Projet obtenirTacheDeLemploye(Projet projet, String adresseCourrielRessourceHumaine) {
	projet.filtrerTachesNonAssigneesAUneRessourceHumaine(adresseCourrielRessourceHumaine);
	return projet;
    }
}
