package ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires;

public class UtilitaireChaineDeCaracteres
{

    public UtilitaireChaineDeCaracteres() {

    }

    public String obtenirNomProjetSeulement(String nomProjetEtTache) {
	String chaine;
	if (nomProjetEtTache.contains("/")){
	    chaine = nomProjetEtTache.substring(0, nomProjetEtTache.indexOf("/"));
	}else chaine = nomProjetEtTache;
	return chaine;
    }

    public String obtenirNomTacheSeulement(String nomTacheEtProjet) {
	return nomTacheEtProjet.substring(nomTacheEtProjet.indexOf("/") + 1);
    }
}
