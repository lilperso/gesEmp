package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

public class Deplacement
{
    private String kilometreParcouru;
    private String noteDeplacement;
    private boolean approuve;

    public Deplacement() {
    }

    public Deplacement(String kilometreParcouru, String noteDeplacement, boolean approuve) {
	this.kilometreParcouru = kilometreParcouru;
	this.noteDeplacement = noteDeplacement;
	this.approuve = approuve;
    }

    public String getKilometreParcouru() {
	return kilometreParcouru;
    }

    public String getNoteDeplacement() {
	return noteDeplacement;
    }

    public void approuverDeplacement() {
	approuve = true;
    }

    public boolean estApprouve() {
	return approuve;
    }
}
