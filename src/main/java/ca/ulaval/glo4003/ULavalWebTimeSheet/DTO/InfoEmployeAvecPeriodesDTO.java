package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;

public class InfoEmployeAvecPeriodesDTO
{

    private String adresseCourriel;
    private String prenom;
    private String nom;
    private List<PeriodeDTO> listeDesPeriodes;

    public InfoEmployeAvecPeriodesDTO(RessourceHumaine rH) {
	this.adresseCourriel = rH.getAdresseCourriel();
	this.prenom = rH.getInformationsPersonnelles().getPrenom();
	this.nom = rH.getInformationsPersonnelles().getNom();
	this.setListeDesPeriodes(new ArrayList<PeriodeDTO>());

    }

    public String getAdresseCourriel() {
	return adresseCourriel;
    }

    public String getPrenom() {
	return prenom;
    }

    public String getNom() {
	return nom;
    }

    public List<PeriodeDTO> getListeDesPeriodes() {
	return listeDesPeriodes;
    }

    public void setListeDesPeriodes(ArrayList<PeriodeDTO> arrayList) {
	this.listeDesPeriodes = arrayList;
    }

}
