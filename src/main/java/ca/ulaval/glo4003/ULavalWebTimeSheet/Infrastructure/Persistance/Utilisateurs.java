package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Utilisateur;

public class Utilisateurs
{
    private List<Utilisateur> utilisateurs;

    public List<Utilisateur> getUtilisateurs() {
	return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
	this.utilisateurs = utilisateurs;
    }

}
