package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotUtilisateurs;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Convertisseurs.JodaTimeConvertisseur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

public class DepotUtilisateurXML extends DepotAbstrait implements DepotUtilisateurs
{

    private static final String MAUVAIS_MOT_DE_PASSE = "Votre mot de passe est incorrect";
    private static final String UTILISATEUR_DEJA_EXISTANT = "L'utilisateur que vous tentez de créer est déjà dans la base de données";

    public DepotUtilisateurXML() {
	xStream.registerConverter(new JodaTimeConvertisseur());
    }

    @Override
    public Utilisateur trouverUtilisateur(String adresseCourriel, String motDePasse)
	    throws AdresseCourrielInexistanteException, MauvaisMotDePasseException {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();
	Utilisateur utilisateurRecherche = trouveUtilisateurRecherche(utilisateurs, adresseCourriel, motDePasse);
	return utilisateurRecherche;
    }

    @Override
    public Utilisateur obtenirUtilisateurAvecAdresseCourriel(String adresseCourriel)
	    throws AdresseCourrielInexistanteException {
	Utilisateur utilisateurRecherche = obtenirUtilisateurSelonAdresseCourriel(adresseCourriel);
	return utilisateurRecherche;
    }

    @Override
    public void persisterNouvelUtilisateur(Utilisateur nouvelUtilisateur) throws UtilisateurDejaExistantException {
	Utilisateurs utilisateurs = ajouterNouvelUtilisateurAUtilisateurs(nouvelUtilisateur);
	persisterChangementsXMLUtilisateurs(utilisateurs);
    }

    @Override
    public List<Utilisateur> obtenirListeDesUtilisateurs() {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();
	return utilisateurs.getUtilisateurs();
    }

    @Override
    public void persisterModificationInformationsUtilisateur(Utilisateur utilisateurMisAjour)
	    throws UtilisateurInexistantException {
	persisterChangementsUtilisateur(utilisateurMisAjour);
    }

    public void supprimerUtilisateurXML(Utilisateur utilisateur) {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();
	utilisateurs = retirerUtilisateur(utilisateurs, utilisateur);
	persisterChangementsXMLUtilisateurs(utilisateurs);
    }

    private Utilisateurs retirerUtilisateur(Utilisateurs utilisateurs, Utilisateur utilisateurAsupprimer) {
	List<Utilisateur> listeUtilisateurs = utilisateurs.getUtilisateurs();
	listeUtilisateurs.remove(utilisateurAsupprimer);
	utilisateurs.setUtilisateurs(listeUtilisateurs);
	return utilisateurs;
    }

    private Utilisateurs ajouterNouvelUtilisateurAUtilisateurs(Utilisateur nouvelUtilisateur)
	    throws UtilisateurDejaExistantException {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();

	if (utilisateurExisteDeja(nouvelUtilisateur, utilisateurs.getUtilisateurs())) {
	    throw new UtilisateurDejaExistantException(UTILISATEUR_DEJA_EXISTANT);
	}
	utilisateurs.getUtilisateurs().add(nouvelUtilisateur);

	return utilisateurs;
    }

    private boolean utilisateurExisteDeja(Utilisateur nouvelUtilisateur, List<Utilisateur> utilisateurs) {
	return utilisateurs.contains(nouvelUtilisateur);
    }

    private Utilisateur trouveUtilisateurRecherche(Utilisateurs utilisateurs, String adresseCourriel, String motDePasse)
	    throws AdresseCourrielInexistanteException, MauvaisMotDePasseException {

	List<Utilisateur> listeUtilisateurs = utilisateurs.getUtilisateurs();
	Utilisateur utilisateurRecherche = null;

	for (Utilisateur utilisateur : listeUtilisateurs) {
	    if (estBonneAdresseCourriel(adresseCourriel, utilisateur)) {
		utilisateurRecherche = utilisateur;
		break;
	    }
	}

	gererExceptions(motDePasse, utilisateurRecherche);
	return utilisateurRecherche;
    }

    private void gererExceptions(String motDePasse, Utilisateur utilisateurRecherche)
	    throws AdresseCourrielInexistanteException, MauvaisMotDePasseException {

	if (adresseCourrielNonTrouvee(utilisateurRecherche)) {
	    throw new AdresseCourrielInexistanteException(ADRESSE_COURRIEL_INEXISTANTE);
	}
	if (!estBonMotDePasse(motDePasse, utilisateurRecherche)) {
	    throw new MauvaisMotDePasseException(MAUVAIS_MOT_DE_PASSE);
	}
    }

    private boolean adresseCourrielNonTrouvee(Utilisateur utilisateurRecherche) {
	return utilisateurRecherche == null;
    }

    private boolean estBonneAdresseCourriel(String adresseCourriel, Utilisateur utilisateur) {
	return utilisateur.getAdresseCourriel().equals(adresseCourriel);
    }

    private boolean estBonMotDePasse(String motDePasse, Utilisateur utilisateur) {
	return utilisateur.getMotDePasse().equals(motDePasse);
    }
}
