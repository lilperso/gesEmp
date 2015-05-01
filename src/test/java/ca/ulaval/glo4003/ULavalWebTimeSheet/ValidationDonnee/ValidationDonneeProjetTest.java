package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeProjetTest
{
    private static final String NOM_PROJET = "nomProjet";
    private static final String NOM_PROJET_VIDE = "";
    private static final String NOM_TACHE = "tache";
    private static final String NOM_TACHE_VIDE = "";

    private ValidationDonneeProjet validationdonneeprojet = new ValidationDonneeProjet();

    @Test
    public void lorsqueDonneeProjetValide() throws Throwable {
	validationdonneeprojet.validerDonneeProjet(NOM_PROJET);
    }

    @Test(expected = ChampVideException.class)
    public void lorsqueNomProjetEstVide() throws Throwable {
	validationdonneeprojet.validerDonneeProjet(NOM_PROJET_VIDE);
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueNomProjetEstNull() throws Throwable {
	validationdonneeprojet.validerDonneeProjet(null);
    }

    @Test(expected = ChampVideException.class)
    public void lorsqueNomTacheEstVide() throws Throwable {
	validationdonneeprojet.validerDonneeTache(NOM_TACHE_VIDE);
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueNomTacheEstNull() throws Throwable {
	validationdonneeprojet.validerDonneeTache(null);
    }

    public void lorsquenomtache() throws Throwable {
	validationdonneeprojet.validerDonneeTache(NOM_TACHE);
    }

}
