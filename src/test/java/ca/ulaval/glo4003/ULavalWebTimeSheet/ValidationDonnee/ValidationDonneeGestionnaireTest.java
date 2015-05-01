package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeGestionnaireTest 
{
    private static final String MOT_DE_PASSE = "motDePasse";
    private static final String MOT_DE_PASSE_VIDE = null;
    private static final String ADRESSE_COURRIEL = "Adresse";
    private static final String ADRESSE_COURRIEL_VIDE = null;
    
    private ValidationDonneeCreationRessourceHumaine validationdonneeGestuonnaire = new ValidationDonneeCreationRessourceHumaine();
    
    @Test
    public void lorsqueCreationEmployeCorrect()throws Throwable{
        validationdonneeGestuonnaire.validerDonneeCreationEmploye(ADRESSE_COURRIEL, MOT_DE_PASSE);  
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueMotDePasseCreationEmployeEstVide() throws Throwable{
        validationdonneeGestuonnaire.validerDonneeCreationEmploye(ADRESSE_COURRIEL, MOT_DE_PASSE_VIDE);
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueAdresseCreationEmployeEstVide() throws Throwable{
        validationdonneeGestuonnaire.validerDonneeCreationEmploye(ADRESSE_COURRIEL_VIDE, MOT_DE_PASSE);
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueAdresseEtMotDePasseSontVide() throws Throwable{
        validationdonneeGestuonnaire.validerDonneeCreationEmploye(ADRESSE_COURRIEL_VIDE, MOT_DE_PASSE_VIDE);
    }

}
