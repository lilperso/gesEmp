package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires.UtilitaireDate;

public class Periode
{
    private UtilitaireDate utilitaireDate = new UtilitaireDate();
    private List<QuartTravail> listeQuartsTravail;
    private DateTime dateDebut;
    private DateTime dateFin;

    public Periode() {
    }

    /**
     * @param date
     * @return Cr�e une p�riode du lundi au dimanche, peu importe la date pass�e
     *         en param�tre
     */
    public Periode(DateTime date) {
	listeQuartsTravail = new ArrayList<QuartTravail>();
	dateDebut = date.withDayOfWeek(DateTimeConstants.MONDAY);
	dateFin = date.withDayOfWeek(DateTimeConstants.SUNDAY);
    }

    public Periode(List<QuartTravail> listeQuartsTravail, DateTime dateDebut, DateTime dateFin) {
	this.listeQuartsTravail = listeQuartsTravail;
	this.dateDebut = dateDebut;
	this.dateFin = dateFin;
    }

    public List<QuartTravail> getListeQuartsTravail() {
	return listeQuartsTravail;
    }

    public DateTime getDateDebut() {
	return dateDebut;
    }

    public DateTime getDateFin() {
	return dateFin;
    }

    public QuartTravail obtenirQuartSelonDate(DateTime dateQuartTravail) {
	QuartTravail quartTravail = null;
	for (QuartTravail quart : listeQuartsTravail) {
	    if (utilitaireDate.estMemeDate(quart.getDateQuartTravail(), dateQuartTravail)) {
		quartTravail = quart;
	    }
	}
	return quartTravail;
    }
}
