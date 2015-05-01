package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import org.joda.time.DateTime;

public class QuartTravail
{
    private DateTime dateQuartTravail;
    private String heureEntree;
    private String heureSortie;
    private Depense depense;
    private Deplacement deplacement;
    private String noteQuartTravail;

    public QuartTravail() {
    }

    public QuartTravail(DateTime dateQuartTravail, String heureEntree, String heureSortie, Depense depense, Deplacement deplacement, String noteQuartTravail) {
	this.dateQuartTravail = dateQuartTravail;
	this.heureEntree = heureEntree;
	this.heureSortie = heureSortie;
	this.depense = depense;
	this.deplacement = deplacement;
	this.noteQuartTravail = noteQuartTravail;
    }

    public DateTime getDateQuartTravail() {
	return this.dateQuartTravail;
    }

    public void setDateQuartTravail(DateTime dateQuartTravail) {
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

    public Depense getDepense() {
	return depense;
    }

    public void setDepense(Depense depense) {
	this.depense = depense;
    }

    public Deplacement getDeplacement() {
	return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
	this.deplacement = deplacement;
    }

    public String getNoteQuartTravail() {
	return this.noteQuartTravail;
    }

    public void setNoteQuartTravail(String noteQuartTravail) {
	this.noteQuartTravail = noteQuartTravail;
    }

}
