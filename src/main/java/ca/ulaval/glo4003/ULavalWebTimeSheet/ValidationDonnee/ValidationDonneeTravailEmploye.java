package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ValidationDonneeTravailEmploye extends ValidationDonnee
{
    public static final String HEURE_ENTREE_VIDE = "Le champs heure d'entree est vide";
    public static final String HEURE_SORTIE_VIDE = "le champs heure de sortie est vide";
    public static final String NOTE_DEPLACEMENT_VIDE = "Le champs note de deplacement est vide";
    public static final String NOTE_DEPENSE_VIDE="Le champs note de depense est vide";
    public static final String DATE_QUART_TRAVAIL_VIDE="Le champs date quart de travail est vide";

    public void validerDonnneeQuartDeTravail(QuartTravailDTO quartTravailDTO) throws ChampVideException {
	
	if (verifieSiChampEstVide(quartTravailDTO.getDateQuartTravail())) {
	    throw new ChampVideException(DATE_QUART_TRAVAIL_VIDE);
	}
	if (verifieSiChampEstVide(quartTravailDTO.getHeureEntree())) {
	    throw new ChampVideException(HEURE_ENTREE_VIDE);
	}
	if (verifieSiChampEstVide(quartTravailDTO.getHeureSortie())) {
	    throw new ChampVideException(HEURE_SORTIE_VIDE);
	}
	if (!verifieSiChampEstVide(quartTravailDTO.getNoteDepense()) && verifieSiChampEstVide(quartTravailDTO.getMontantDepense()) ) {
	    throw new ChampVideException(NOTE_DEPENSE_VIDE);
	}
	if (!verifieSiChampEstVide(quartTravailDTO.getNoteDeplacement()) && verifieSiChampEstVide(quartTravailDTO.getLongueurDeplacement())) {
	    throw new ChampVideException(NOTE_DEPLACEMENT_VIDE);
	}
    }
    
}
