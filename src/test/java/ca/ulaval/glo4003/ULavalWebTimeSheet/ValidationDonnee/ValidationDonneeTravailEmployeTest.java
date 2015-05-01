package ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;



public class ValidationDonneeTravailEmployeTest 
{
    public static final String DATE_QUART = "date quart";
    public static final String HEURE_ENTREE = "heure d'entree";
    public static final String HEURE_SORTIE = "heure de sortie";
    public static final String DEPLACEMENT = "deplacement";
    public static final String DEPENSE = "depense";
    public static final String NOTE_DEPLACEMENT= "deplacement";
    public static final String NOTE_DEPENSE="depense";
    public static final String HEURE_ENTREE_VIDE = null;
    public static final String HEURE_SORTIE_VIDE = null;
    public static final String NOTE_DEPLACEMENT_VIDE = null;
    public static final String NOTE_DEPENSE_VIDE=null;
    private QuartTravailDTO quartDto;
    
    
    private ValidationDonneeTravailEmploye validationdonnetravail = new ValidationDonneeTravailEmploye();
    
    @Test
    public void lorsqueQuartDetravailValidePasDErreur() throws Throwable{
	quartDto = creerQuartDtoComplet();
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
    }

    @Test
    public void lorsqueDeplacementEtDepenseSontVidePasDErreur() throws Throwable{
	quartDto = creerQuartDtoValideSansDeplacementEtDepsense();
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
    }
    
    @Test
    public void lorsqueNoteDeplacementEtNoteDepenseSontVidePasDErreur() throws Throwable{
	quartDto = creerQuartDtoValideSansNoteDeplacementEtNoteDepsense();
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueDateQuartEstVideLanceChampVideExceptionEst() throws Throwable{
	quartDto=creerQuartDtoComplet();
	quartDto.setDateQuartTravail(null);
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
	
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueHeureEntreeEstvideLanceChampVideExceptionEst() throws Throwable{
	quartDto=creerQuartDtoComplet();
	quartDto.setHeureEntree(null);
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
	
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueHeureSortieEstVideLanceChampVideException() throws Throwable{
	quartDto=creerQuartDtoComplet();
	quartDto.setHeureSortie(null);
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
	
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueDepenseEstvideMaisPasNoteDepenseLanceChampVideException() throws Throwable{
	quartDto=creerQuartDtoComplet();
	quartDto.setMontantDepense(null);
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
	
    }
    
    @Test(expected = ChampVideException.class)
    public void lorsqueDeplacementEstvideMaisPasNoteDeplacementLanceChampVideException() throws Throwable{
	quartDto=creerQuartDtoComplet();
	quartDto.setLongueurDeplacement(null);;
	validationdonnetravail.validerDonnneeQuartDeTravail(quartDto);
	
    }
    
    private QuartTravailDTO creerQuartDtoComplet() {
	QuartTravailDTO quartDto = new QuartTravailDTO("");
	quartDto.setDateQuartTravail(DATE_QUART);
	quartDto.setHeureEntree(HEURE_ENTREE);
	quartDto.setHeureSortie(HEURE_SORTIE);
	quartDto.setLongueurDeplacement(DEPLACEMENT);
	quartDto.setMontantDepense(DEPENSE);
	quartDto.setNoteDepense(NOTE_DEPENSE);
	quartDto.setNoteDeplacement(NOTE_DEPLACEMENT);
	return quartDto;
    }
    
    private QuartTravailDTO creerQuartDtoValideSansDeplacementEtDepsense() {
	QuartTravailDTO quartDto = new QuartTravailDTO("");
	quartDto.setDateQuartTravail(DATE_QUART);
	quartDto.setHeureEntree(HEURE_ENTREE);
	quartDto.setHeureSortie(HEURE_SORTIE);
	return quartDto;
    }
    
    private QuartTravailDTO creerQuartDtoValideSansNoteDeplacementEtNoteDepsense() {
	QuartTravailDTO quartDto = new QuartTravailDTO("");
	quartDto.setDateQuartTravail(DATE_QUART);
	quartDto.setHeureEntree(HEURE_ENTREE);
	quartDto.setHeureSortie(HEURE_SORTIE);
	quartDto.setLongueurDeplacement(DEPLACEMENT);
	quartDto.setMontantDepense(DEPENSE);
	return quartDto;
    }
}
