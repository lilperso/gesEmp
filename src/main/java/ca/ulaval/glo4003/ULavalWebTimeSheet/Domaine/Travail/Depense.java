package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

public class Depense
{
    private String montantDepense;
    private String noteDepense;
    private boolean approuve;

    public Depense() {
    }

    public Depense(String montantDepense, String noteDepense, boolean approuve) {
	this.montantDepense = montantDepense;
	this.noteDepense = noteDepense;
	this.approuve = approuve;
    }

    public String getMontantDepense() {
	return montantDepense;
    }

    public String getNoteDepense() {
	return this.noteDepense;
    }

    public void approuverDepense() {
	approuve = true;
    }

    public boolean estApprouve() {
	return approuve;
    }
}
