package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeConnexionTest 
{

   private static final String MOT_DE_PASSE = "motDePasse";
   private static final String MOT_DE_PASSE_VIDE = null;
   private static final String ADRESSE_COURRIEL = "Adresse";
   private static final String ADRESSE_COURRIEL_VIDE = null;
   
   private ValidationDonneeConnexion validationdonneeConnexion = new ValidationDonneeConnexion();
   
   @Test
   public void lorsqueDonneeconnexionValide()throws Throwable{
       validationdonneeConnexion.validerDonnneeConnexion(ADRESSE_COURRIEL, MOT_DE_PASSE);  
   }
   
   @Test(expected = ChampVideException.class)
   public void lorsqueMotDePasseEstVide() throws Throwable{
       validationdonneeConnexion.validerDonnneeConnexion(ADRESSE_COURRIEL, MOT_DE_PASSE_VIDE);
   }
   
   @Test(expected = ChampVideException.class)
   public void lorsqueAdresseEstVide() throws Throwable{
       validationdonneeConnexion.validerDonnneeConnexion(ADRESSE_COURRIEL_VIDE, MOT_DE_PASSE);
   }
   
   @Test(expected = ChampVideException.class)
   public void lorsqueAdresseEtMotDePasseSonttVide() throws Throwable{
       validationdonneeConnexion.validerDonnneeConnexion(ADRESSE_COURRIEL_VIDE, MOT_DE_PASSE_VIDE);
   }

   
   
   
}
