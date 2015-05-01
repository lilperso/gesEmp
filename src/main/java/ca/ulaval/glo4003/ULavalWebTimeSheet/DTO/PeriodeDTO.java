package ca.ulaval.glo4003.ULavalWebTimeSheet.DTO;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PeriodeDTO implements Serializable
{
    public String dateDebut;
    public String dateFin;
    public List<QuartTravailDTO> listeQuartsTravailDTO;

    public PeriodeDTO(String debutPeriode, String finPeriode, List<QuartTravailDTO> listeQuartDTO){
	dateDebut = debutPeriode;
	dateFin = finPeriode;
	listeQuartsTravailDTO = listeQuartDTO;
    }

    public String getDateDebut() {
	return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
	this.dateDebut = dateDebut;
    }

    public String getDateFin() {
	return dateFin;
    }

    public void setDateFin(String dateFin) {
	this.dateFin = dateFin;
    }

    public List<QuartTravailDTO> getListeQuartsTravailDTO() {
	return listeQuartsTravailDTO;
    }

    public void setListeQuartsTravailDTO(List<QuartTravailDTO> listeQuartsTravailDTO) {
	this.listeQuartsTravailDTO = listeQuartsTravailDTO;
    }
}
