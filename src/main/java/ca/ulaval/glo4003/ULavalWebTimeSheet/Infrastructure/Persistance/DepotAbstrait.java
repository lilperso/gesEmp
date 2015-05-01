package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Employe;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Gestionnaire;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

import com.thoughtworks.xstream.XStream;

public abstract class DepotAbstrait
{
    protected String cheminFichierXML = "DonneesUtilisateurs/Utilisateurs.xml";
    protected static final String ADRESSE_COURRIEL_INEXISTANTE = "Votre adresse courriel est incorrecte";
    private static final String UTILISATEUR_INEXISTANT = "L'utilisateur que vous tentez de mettre a jour est absent de la base de donnees";
    protected XStream xStream = new XStream();

    protected Utilisateurs obtientUtilisateursDansXML() {
	Utilisateurs utilisateurs = null;
	try {
	    utilisateurs = (Utilisateurs) xStream.fromXML(new FileInputStream(cheminFichierXML));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	return utilisateurs;
    }

    protected void persisterChangementsXMLUtilisateurs(Utilisateurs utilisateurs) {
	String xml = xStream.toXML(utilisateurs);
	FileWriter writer;
	try {
	    writer = new FileWriter(cheminFichierXML);
	    writer.write(xml);
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    protected void persisterChangementsUtilisateur(Utilisateur utilisateur) throws UtilisateurInexistantException {
	Utilisateurs utilisateurs = mettreAjourInformationsUtilisateur(utilisateur);
	persisterChangementsXMLUtilisateurs(utilisateurs);
    }

    protected Utilisateur obtenirUtilisateurSelonAdresseCourriel(String adresseCourriel)
	    throws AdresseCourrielInexistanteException {
	Utilisateur utilisateur = null;
	Utilisateurs lesUtilisateurs = obtientUtilisateursDansXML();
	utilisateur = trouveUtilisateurRechercheAvecAdresseCourriel(lesUtilisateurs, adresseCourriel);

	if (utilisateur instanceof Employe) {
	    if (((Employe) utilisateur).getListePeriodes() == null) {
		((Employe) utilisateur).setListePeriodes(new ArrayList<Periode>());
	    }
	} else if (utilisateur instanceof Gestionnaire) {
	    if (((Gestionnaire) utilisateur).getListePeriodes() == null) {
		((Gestionnaire) utilisateur).setListePeriodes(new ArrayList<Periode>());
	    }
	}

	return utilisateur;
    }

    private Utilisateur trouveUtilisateurRechercheAvecAdresseCourriel(Utilisateurs utilisateurs, String adresseCourriel)
	    throws AdresseCourrielInexistanteException {
	List<Utilisateur> listeUtilisateurs = utilisateurs.getUtilisateurs();
	Utilisateur utilisateurRecherche = null;
	for (Utilisateur utilisateur : listeUtilisateurs) {
	    if (adresseCourriel.equals(utilisateur.getAdresseCourriel())) {
		utilisateurRecherche = utilisateur;
		break;
	    }
	}
	if (utilisateurRecherche == null) {
	    throw new AdresseCourrielInexistanteException(ADRESSE_COURRIEL_INEXISTANTE);
	}
	return utilisateurRecherche;
    }

    private Utilisateurs mettreAjourInformationsUtilisateur(Utilisateur utilisateurMisAJour)
	    throws UtilisateurInexistantException {
	Utilisateurs utilisateurs = obtientUtilisateursDansXML();
	List<Utilisateur> listeUtilisateurs = utilisateurs.getUtilisateurs();
	int indexUtilisateurAmettreAjourDansListeUtlisateur = listeUtilisateurs.indexOf(utilisateurMisAJour);
	if (indexUtilisateurAmettreAjourDansListeUtlisateur == -1) {
	    throw new UtilisateurInexistantException(UTILISATEUR_INEXISTANT);
	}
	if (utilisateurMisAJour instanceof RessourceHumaine) {
	    listeUtilisateurs.set(indexUtilisateurAmettreAjourDansListeUtlisateur,
		    (RessourceHumaine) utilisateurMisAJour);
	} else {
	    listeUtilisateurs.set(indexUtilisateurAmettreAjourDansListeUtlisateur, utilisateurMisAJour);
	}
	return utilisateurs;
    }
}
