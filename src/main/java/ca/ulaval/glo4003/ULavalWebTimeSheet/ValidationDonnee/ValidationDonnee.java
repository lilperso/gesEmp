package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

public abstract class ValidationDonnee
{

   public static final String NOM_PROJET_MANQUANT = "Le nom du projet est vide";

   
   protected boolean verifieSiChampEstVide(String valeurduchamp){
       if(valeurduchamp == null || valeurduchamp.isEmpty())
	   return true;
	 
       return false;
   }
}
