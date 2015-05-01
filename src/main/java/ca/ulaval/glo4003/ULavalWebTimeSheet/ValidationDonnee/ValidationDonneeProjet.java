package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeProjet extends ValidationDonnee
{
    public static String NOM_PROJET_VIDE = "Le nom du projet est vide";
    public static String NOM_DE_TACHE_VIDE = "Le nom de la tache est vide";
    
    public void  validerDonneeProjet(String nomprojet) throws ChampVideException{
	if(verifieSiChampEstVide(nomprojet)){
	    throw new ChampVideException(NOM_PROJET_VIDE);
	}
    }
    
    public void validerDonneeTache(String nomtache) throws ChampVideException{
	if(verifieSiChampEstVide(nomtache)){
	    throw new ChampVideException(NOM_DE_TACHE_VIDE);
	}
    }
}
