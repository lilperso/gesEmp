package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

public abstract class Utilisateur
{
    public static final String EMPLOYE = "Employe";
    public static final String GESTIONNAIRE = "Gestionnaire";
    public static final String ENTREPRISE = "Entreprise";
    public static final String ADMINISTRATEUR = "Administrateur";

    private String adresseCourriel;
    private String motDePasse;
    private String role;

    public String getMotDePasse() {
	return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public String getAdresseCourriel() {
	return adresseCourriel;
    }

    public void setAdresseCourriel(String adresseCourriel) {
	this.adresseCourriel = adresseCourriel;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((adresseCourriel == null) ? 0 : adresseCourriel.hashCode());
	result = prime * result + ((motDePasse == null) ? 0 : motDePasse.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (!(obj instanceof Utilisateur)) return false;
	Utilisateur other = (Utilisateur) obj;
	if (adresseCourriel == null) {
	    if (other.adresseCourriel != null) return false;
	} else if (!adresseCourriel.equals(other.adresseCourriel)) return false;
	if (motDePasse == null) {
	    if (other.motDePasse != null) return false;
	} else if (!motDePasse.equals(other.motDePasse)) return false;
	if (role == null) {
	    if (other.role != null) return false;
	} else if (!role.equals(other.role)) return false;
	return true;
    }

}
