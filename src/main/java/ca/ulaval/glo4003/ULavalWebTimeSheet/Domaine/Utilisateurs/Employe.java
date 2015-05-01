package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;

public class Employe extends RessourceHumaine
{
    public Employe() {
    }

    public Employe(String adresseCourriel, String motDePasse, String role, String superieurImmediat, InformationsPersonnelles informationsPersonnelles,
	    List<Periode> listePeriodes, String departement) {
	setAdresseCourriel(adresseCourriel);
	setMotDePasse(motDePasse);
	setRole(role);
	setSuperieur(superieurImmediat);
	setInformationsPersonnelles(informationsPersonnelles);
	setListePeriodes(listePeriodes);
	setDepartement(departement);
    }
}