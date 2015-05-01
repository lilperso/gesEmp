package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotUtilisateurs;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.DepotUtilisateurXML;

public class UtilisateursSysteme
{
    private DepotUtilisateurs depotUtilisateurs;

    public UtilisateursSysteme() {
	depotUtilisateurs = new DepotUtilisateurXML();
    }

    public Utilisateur connecterUtilisateur(String adresseCourriel, String motDePasse) {
	return depotUtilisateurs.trouverUtilisateur(adresseCourriel, motDePasse);
    }

}
