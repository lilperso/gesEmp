package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.MauvaisFormatVariableException;

public class ValidationDonneModificationEmploye extends ValidationDonnee
{
    public static final String TAUX_HORAIRE_VIDE = "Le taux horaire n'est pas precise";
    public static final String MAUVAIS_FORMAT_TAUX_HOIRAIRE = "Le taux horaire doit �tre de format digital et positif";

    public void validerDonnneeModifcationEmploye(String tauxhoraire) throws ChampVideException, MauvaisFormatVariableException {
	if (verifieSiChampEstVide(tauxhoraire)) {
	    throw new ChampVideException(TAUX_HORAIRE_VIDE);
	}
	if (!tauxhoraire.matches("[0-9]+(.[0-9]+)?")) {
	    throw new MauvaisFormatVariableException(MAUVAIS_FORMAT_TAUX_HOIRAIRE);
	}
    }

}
