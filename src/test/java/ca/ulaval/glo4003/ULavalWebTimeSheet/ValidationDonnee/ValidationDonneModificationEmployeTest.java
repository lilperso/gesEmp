package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.MauvaisFormatVariableException;


public class ValidationDonneModificationEmployeTest
{
    private static ValidationDonneModificationEmploye validationDonneModificationEmploye = new ValidationDonneModificationEmploye();
    private static final String TAUX_HORAIRE_VIDE = "";
    private static final String TAUX_HORAIRE_NON_CONFORME = "a";

    @Test(expected = ChampVideException.class)
    public void validerTauxHoraireEstVideRetourneErreur() throws Throwable {
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye(TAUX_HORAIRE_VIDE);
    }
    
    @Test(expected = ChampVideException.class)
    public void validerTauxHoraireEstNullRetourneErreur() throws Throwable {
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye(null);
    }


    @Test(expected = MauvaisFormatVariableException.class)
    public void validerTauxHoraireFormatMotRetourneErreur() throws Throwable {
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye(TAUX_HORAIRE_NON_CONFORME);
    }

    @Test(expected = MauvaisFormatVariableException.class)
    public void validerTauxHoraireFormatAvecPointRetourneErreur() throws Throwable {
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye("1.");
    }

    @Test
    public void validerTauxHoraireEntierRetournePasDErreurs() throws Throwable{
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye("1");
    }

    @Test
    public void validerTauxHoraireDecimalRetournePasDErreurs() throws Throwable {
	validationDonneModificationEmploye.validerDonnneeModifcationEmploye("1.1");
    }
}
