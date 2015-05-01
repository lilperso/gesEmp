package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeAvecPeriodesDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.PeriodeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DonneesNominatives.InformationsPersonnelles;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires.UtilitaireDate;

public abstract class RessourceHumaine extends Utilisateur
{
    private UtilitaireDate utilitaireDate = new UtilitaireDate();
    private String departement;
    private InformationsPersonnelles informationsPersonnelles;
    private List<Periode> listePeriodes;
    private double tauxHoraire;
    private String superieurImmediat;

    public void ajouterQuartTravailPourPeriodeCourante(QuartTravail quartTravail) {
	Periode periodeCourante = obtenirPeriodeSelonDateDebut(quartTravail.getDateQuartTravail().withDayOfWeek(DateTimeConstants.MONDAY));
	periodeCourante.getListeQuartsTravail().add(quartTravail);
    }

    private Periode obtenirPeriodeSelonDateDebut(DateTime dateDebutPeriode) {
	for (Periode periode : listePeriodes) {
	    if (utilitaireDate.estMemeDate(periode.getDateDebut(), dateDebutPeriode)) {
		return periode;
	    }
	}
	return nouvellePeriodeSiAucunePeriodeTrouvee(dateDebutPeriode);
    }

    private Periode nouvellePeriodeSiAucunePeriodeTrouvee(DateTime dateQuartTravail) {
	Periode nouvellePeriode = new Periode(dateQuartTravail);
	listePeriodes.add(nouvellePeriode);
	return nouvellePeriode;
    }

    public void modifierQuartDeTravail(QuartTravail quartModifie) {
	QuartTravail quartExistant = obtenirQuartExistant(quartModifie.getDateQuartTravail());
	modifierQuartExistant(quartExistant, quartModifie);
    }

    public void approuverDepense(DateTime dateQuart) {
	for (Periode p : this.getListePeriodes()) {
	    for (QuartTravail q : p.getListeQuartsTravail()) {
		if (q.getDateQuartTravail().equals(dateQuart)) {
		    q.getDepense().approuverDepense();
		}
	    }
	}
    }

    public void approuverDeplacement(DateTime dateQuart) {
	for (Periode p : this.getListePeriodes()) {
	    for (QuartTravail q : p.getListeQuartsTravail()) {
		if (q.getDateQuartTravail().equals(dateQuart)) {
		    q.getDeplacement().approuverDeplacement();
		}
	    }
	}
    }

    public InfoEmployeAvecPeriodesDTO obtenirListeDesPeriodesAvecDemandePourUnEmployeDTO() {
	InfoEmployeAvecPeriodesDTO infoEmployeAvecPeriodesDTO = new InfoEmployeAvecPeriodesDTO(this);
	if (this.getListePeriodes().size() == 0) {
	    return infoEmployeAvecPeriodesDTO;
	}

	
	for (Periode periode : this.getListePeriodes()) {
	    ArrayList<QuartTravailDTO> listQuartAvecDemandeDTO = new ArrayList<QuartTravailDTO>();
	    for (QuartTravail quart : periode.getListeQuartsTravail()) {
		if (!quart.getDepense().estApprouve() || !quart.getDeplacement().estApprouve()) {
		    QuartTravailDTO quartDTO = new QuartTravailDTO(quart);
		    listQuartAvecDemandeDTO.add(quartDTO);
		}
	    }
	    PeriodeDTO periodeDTO = new PeriodeDTO(periode.getDateDebut().toString(), periode.getDateFin().toString(), listQuartAvecDemandeDTO);
	    if (!periodeDTO.getListeQuartsTravailDTO().isEmpty())
		infoEmployeAvecPeriodesDTO.getListeDesPeriodes().add(periodeDTO);
	}
	return infoEmployeAvecPeriodesDTO;
    }

    private QuartTravail obtenirQuartExistant(DateTime dateQuartTravail) {
	Periode periodeDuQuart = obtenirPeriodeSelonDateDebut(dateQuartTravail.withDayOfWeek(DateTimeConstants.MONDAY));
	return periodeDuQuart.obtenirQuartSelonDate(dateQuartTravail);
    }

    private void modifierQuartExistant(QuartTravail quartExistant, QuartTravail quartModifie) {
	quartExistant.setDepense(quartModifie.getDepense());
	quartExistant.setDeplacement(quartModifie.getDeplacement());
	quartExistant.setHeureEntree(quartModifie.getHeureEntree());
	quartExistant.setHeureSortie(quartModifie.getHeureSortie());
	quartExistant.setNoteQuartTravail(quartModifie.getNoteQuartTravail());
    }

    public String getSuperieur() {
	return superieurImmediat;
    }

    public void setSuperieur(String superieur) {
	this.superieurImmediat = superieur;
    }

    public InformationsPersonnelles getInformationsPersonnelles() {
	return informationsPersonnelles;
    }

    public void setInformationsPersonnelles(InformationsPersonnelles informationsPersonnelles) {
	this.informationsPersonnelles = informationsPersonnelles;
    }

    public List<Periode> getListePeriodes() {
	return listePeriodes;
    }

    public void setListePeriodes(List<Periode> listePeriodes) {
	this.listePeriodes = listePeriodes;
    }

    public double getTauxHoraire() {
	return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
	this.tauxHoraire = tauxHoraire;
    }

    public String getDepartement() {
	return departement;
    }

    public void setDepartement(String departement) {
	this.departement = departement;
    }

}
