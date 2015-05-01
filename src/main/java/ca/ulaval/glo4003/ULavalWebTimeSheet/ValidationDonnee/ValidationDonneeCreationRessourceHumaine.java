package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeCreationRessourceHumaine extends ValidationDonnee
{
    public static final String ADRESSE_COURRIEL_VIDE = "L'adresse courriel est vide";
    public static final String MOT_DE_PASSE_MANQUANT = "le mot de passe est vide";

    public void validerDonneeCreationEmploye(String adressecourrielle, String motdepasse) throws ChampVideException {
	if (verifieSiChampEstVide(adressecourrielle)) {
	    throw new ChampVideException(ADRESSE_COURRIEL_VIDE);
	}
	if (verifieSiChampEstVide(motdepasse)) {
	    throw new ChampVideException(MOT_DE_PASSE_MANQUANT);
	}
    }
}
