package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.io.Serializable;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires.UtilitaireDate;

@SuppressWarnings("serial")
public class QuartTravailDTO implements Serializable
{

    private String dateQuartTravail;
    private String heureEntree;
    private String heureSortie;
    private String noteQuart;
    private String montantDepense;
    private String noteDepense;


    private Boolean depenseApprouve;
    private Boolean deplacementApprouve;
    private String longueurDeplacement;
    private String noteDeplacement;
    private UtilitaireDate gestionnaireDate = new UtilitaireDate();
    
    public QuartTravailDTO(QuartTravail quartTravail) {
	dateQuartTravail = gestionnaireDate.formaterDate(quartTravail.getDateQuartTravail());
	heureEntree = quartTravail.getHeureEntree();
	heureSortie = quartTravail.getHeureSortie();
	noteQuart = quartTravail.getNoteQuartTravail();
	montantDepense = quartTravail.getDepense().getMontantDepense();
	noteDepense = quartTravail.getDepense().getNoteDepense();
	longueurDeplacement = quartTravail.getDeplacement().getKilometreParcouru();
	noteDeplacement = quartTravail.getDeplacement().getNoteDeplacement();
	depenseApprouve = quartTravail.getDepense().estApprouve();
	deplacementApprouve = quartTravail.getDeplacement().estApprouve();
    }
    
    public QuartTravailDTO(String dateQuart){
	dateQuartTravail = dateQuart;
	heureEntree = "";
	heureSortie = "";
	montantDepense = "";
	noteDepense = "";
	longueurDeplacement = "";
	noteDeplacement = "";
    }

    public String getDateQuartTravail() {
	return dateQuartTravail;
    }

    public void setDateQuartTravail(String dateQuartTravail) {
	this.dateQuartTravail = dateQuartTravail;
    }

    public String getHeureEntree() {
	return heureEntree;
    }

    public void setHeureEntree(String heureEntree) {
	this.heureEntree = heureEntree;
    }

    public String getHeureSortie() {
	return heureSortie;
    }

    public void setHeureSortie(String heureSortie) {
	this.heureSortie = heureSortie;
    }
    
    public String getNoteQuart() {
        return noteQuart;
    }

    public void setNoteQuart(String noteQuart) {
        this.noteQuart = noteQuart;
    }

    public String getMontantDepense() {
	return montantDepense;
    }

    public void setMontantDepense(String montantDepense) {
	this.montantDepense = montantDepense;
    }

    public String getNoteDepense() {
	return noteDepense;
    }

    public void setNoteDepense(String noteDepense) {
	this.noteDepense = noteDepense;
    }

    public String getLongueurDeplacement() {
	return longueurDeplacement;
    }

    public void setLongueurDeplacement(String longueurDeplacement) {
	this.longueurDeplacement = longueurDeplacement;
    }

    public String getNoteDeplacement() {
	return noteDeplacement;
    }

    public void setNoteDeplacement(String noteDeplacement) {
	this.noteDeplacement = noteDeplacement;
    }
    
    public Boolean estDepenseApprouve() {
        return depenseApprouve;
    }

    public void setDepenseApprouve(Boolean depenseApprouve) {
        this.depenseApprouve = depenseApprouve;
    }

    public Boolean estDeplacementApprouve() {
        return deplacementApprouve;
    }

    public void setDeplacementApprouve(Boolean deplacementApprouve) {
        this.deplacementApprouve = deplacementApprouve;
    }
}
