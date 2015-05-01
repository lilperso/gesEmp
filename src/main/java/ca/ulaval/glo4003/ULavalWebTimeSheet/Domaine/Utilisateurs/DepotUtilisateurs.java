package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurDejaExistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;

public interface DepotUtilisateurs
{
    public Utilisateur trouverUtilisateur(String adresseCourriel, String motDePasse) throws MauvaisMotDePasseException,
	    AdresseCourrielInexistanteException;

    public Utilisateur obtenirUtilisateurAvecAdresseCourriel(String adresseCourriel) throws AdresseCourrielInexistanteException;

    public void persisterNouvelUtilisateur(Utilisateur nouvelUtilisateur) throws UtilisateurDejaExistantException;

    public List<Utilisateur> obtenirListeDesUtilisateurs();

    public void persisterModificationInformationsUtilisateur(Utilisateur utilisateurMisAjour)
	    throws UtilisateurInexistantException;

}