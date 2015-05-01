package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives;

public class InformationsPersonnelles
{
    private String adresseResidentielle;
    private String nom;
    private String prenom;
    private String numTelephone;

    public InformationsPersonnelles() {
    }

    public InformationsPersonnelles(String adresseResidentielle, String nom, String prenom, String numTelephone) {
	this.adresseResidentielle = adresseResidentielle;
	this.nom = nom;
	this.prenom = prenom;
	this.numTelephone = numTelephone;
    }

    public String getNom() {
	return nom;
    }

    public String getPrenom() {
	return prenom;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((adresseResidentielle == null) ? 0 : adresseResidentielle.hashCode());
	result = prime * result + ((nom == null) ? 0 : nom.hashCode());
	result = prime * result + ((numTelephone == null) ? 0 : numTelephone.hashCode());
	result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (!(obj instanceof InformationsPersonnelles)) return false;
	InformationsPersonnelles other = (InformationsPersonnelles) obj;
	if (adresseResidentielle == null) {
	    if (other.adresseResidentielle != null) return false;
	} else if (!adresseResidentielle.equals(other.adresseResidentielle)) return false;
	if (nom == null) {
	    if (other.nom != null) return false;
	} else if (!nom.equals(other.nom)) return false;
	if (numTelephone == null) {
	    if (other.numTelephone != null) return false;
	} else if (!numTelephone.equals(other.numTelephone)) return false;
	if (prenom == null) {
	    if (other.prenom != null) return false;
	} else if (!prenom.equals(other.prenom)) return false;
	return true;
    }
}
