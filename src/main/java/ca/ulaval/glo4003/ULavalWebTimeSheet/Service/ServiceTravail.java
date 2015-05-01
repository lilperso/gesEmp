package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.PeriodeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Depense;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Deplacement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Periode;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.QuartTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.DepotDepartement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.RessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.DepotDepartementXML;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.NestPasRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.UtilisateurInexistantException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.serviceTravailException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires.UtilitaireDate;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneeTravailEmploye;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.Exceptions.ChampVideException;

public class ServiceTravail
{
    private static final int NB_JOUR_SEMAINE = 7;
    private DepotDepartement dpDepot;
    private UtilitaireDate utilitaireDate = new UtilitaireDate();
    private ValidationDonneeTravailEmploye validation;

    public ServiceTravail() {
	this.dpDepot = new DepotDepartementXML();
	this.validation = new ValidationDonneeTravailEmploye();
    }

    public void ajouterQuartsTravailPourPeriodeCourante(QuartTravailDTO quartTravailDTO, AuthentificationDTO authentificationDTO)
	    throws serviceTravailException {

	try {
	    validation.validerDonnneeQuartDeTravail(quartTravailDTO);
	    RessourceHumaine rH = dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(authentificationDTO.getAdresseCourriel());
	    QuartTravail qt = convertirQuartDeTravailDTOenQuartDeTravail(quartTravailDTO);
	    rH.ajouterQuartTravailPourPeriodeCourante(qt);
	    dpDepot.modifierRessourceHumaine(rH);
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException | UtilisateurInexistantException | ChampVideException e) {
	    throw new serviceTravailException(e.getMessage());
	}

    }

    public void modifierQuartsTravail(QuartTravailDTO quartTravailDTO, AuthentificationDTO authentificationDTO) throws serviceTravailException {
	try {
	    RessourceHumaine rH = dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(authentificationDTO.getAdresseCourriel());
	    QuartTravail qt = convertirQuartDeTravailDTOenQuartDeTravail(quartTravailDTO);
	    rH.modifierQuartDeTravail(qt);
	    dpDepot.modifierRessourceHumaine(rH);
	} catch (NestPasRessourceHumaineException | UtilisateurInexistantException e) {
	    throw new serviceTravailException(e.getMessage());
	}

    }

    /**
     * @return Retourne toujours une période qui commence le lundi et qui se
     *         termine le dimanche.
     */
    public PeriodeDTO obtenirPeriodeDeTravailDTO(DateTime dateDebutPeriode, String adresseCourriel) throws serviceTravailException {
	try {
	    RessourceHumaine rH = dpDepot.obtenirRessourceHumaineAvecAdresseCourriel(adresseCourriel);
	    List<Periode> periodes = rH.getListePeriodes();
	    PeriodeDTO periodeDTO = remplirDTOSelonPeriodeCouranteTrouvee(dateDebutPeriode, periodes);
	    return periodeDTO;
	} catch (AdresseCourrielInexistanteException | NestPasRessourceHumaineException e) {
	    throw new serviceTravailException(e.getMessage());
	}

    }

    public PeriodeDTO remplirDTOSelonPeriodeCouranteTrouvee(DateTime dateDebutPeriode, List<Periode> listePeriode) {
	Periode periodeCourante = null;
	PeriodeDTO periodeDTO;
	for (Periode periode : listePeriode) {
	    if (utilitaireDate.estMemeDate(periode.getDateDebut(), dateDebutPeriode)) {
		periodeCourante = periode;
		break;
	    }
	}
	periodeDTO = creerLeDTOCorrespondantALaPeriode(dateDebutPeriode, periodeCourante);
	return periodeDTO;
    }

    public PeriodeDTO creerLeDTOCorrespondantALaPeriode(DateTime dateDebutPeriode, Periode periode) {
	PeriodeDTO periodeDTO;
	if (periode == null) {
	    periodeDTO = creerUnDTOParDefaut(dateDebutPeriode);
	} else {
	    periodeDTO = creerUnDTOSelonPeriode(periode);
	}
	return periodeDTO;
    }

    private PeriodeDTO creerUnDTOParDefaut(DateTime datePeriode) {
	String lundi = utilitaireDate.formaterDate(obtenirLeDebutDeCettePeriode(datePeriode));
	String dimanche = utilitaireDate.formaterDate(obtenirLaFinDeCettePeriode(datePeriode));
	List<QuartTravailDTO> listeQuartDTO = creerListeQuartDTOParDefaut(DateTime.parse(lundi));
	return new PeriodeDTO(lundi, dimanche, listeQuartDTO);
    }

    private PeriodeDTO creerUnDTOSelonPeriode(Periode periode) {
	String lundi = utilitaireDate.formaterDate(periode.getDateDebut());
	String dimanche = utilitaireDate.formaterDate(periode.getDateFin());
	List<QuartTravailDTO> listeQuartDTO = new ArrayList<QuartTravailDTO>();
	for (int i = 0; i < NB_JOUR_SEMAINE; i++) {
	    listeQuartDTO.add(creerQuartDTO(periode.getDateDebut().plusDays(i), periode.getListeQuartsTravail()));
	}
	return new PeriodeDTO(lundi, dimanche, listeQuartDTO);
    }

    private QuartTravailDTO creerQuartDTO(DateTime dateDuQuart, List<QuartTravail> listeQuartsTravail) {
	QuartTravailDTO quartDTO = new QuartTravailDTO(utilitaireDate.formaterDate(dateDuQuart));
	for (QuartTravail quartTravail : listeQuartsTravail) {
	    if (utilitaireDate.estMemeDate(quartTravail.getDateQuartTravail(), dateDuQuart)) {
		quartDTO = new QuartTravailDTO(quartTravail);
	    }
	}
	return quartDTO;
    }

    private QuartTravail convertirQuartDeTravailDTOenQuartDeTravail(QuartTravailDTO quartdetravaildto) {
	QuartTravail quartdeTravail = new QuartTravail();
	Depense depense = new Depense(quartdetravaildto.getMontantDepense(), quartdetravaildto.getNoteDepense(), depenseEstApprouvee(quartdetravaildto));
	Deplacement deplacement = new Deplacement(quartdetravaildto.getLongueurDeplacement(), quartdetravaildto.getNoteDeplacement(),
		deplacementEstApprouve(quartdetravaildto));

	quartdeTravail.setDateQuartTravail(DateTime.parse(quartdetravaildto.getDateQuartTravail()));
	quartdeTravail.setHeureEntree(quartdetravaildto.getHeureEntree());
	quartdeTravail.setHeureSortie(quartdetravaildto.getHeureSortie());
	quartdeTravail.setDepense(depense);
	quartdeTravail.setDeplacement(deplacement);
	quartdeTravail.setNoteQuartTravail(quartdetravaildto.getNoteQuart());
	return quartdeTravail;
    }

    private boolean deplacementEstApprouve(QuartTravailDTO qtDTO) {
	return qtDTO.getLongueurDeplacement().equals("");
    }

    private boolean depenseEstApprouvee(QuartTravailDTO qtDTO) {
	return qtDTO.getMontantDepense().equals("");
    }

    private DateTime obtenirLeDebutDeCettePeriode(DateTime datePeriode) {
	return datePeriode.withDayOfWeek(DateTimeConstants.MONDAY);
    }

    private DateTime obtenirLaFinDeCettePeriode(DateTime datePeriode) {
	return datePeriode.withDayOfWeek(DateTimeConstants.SUNDAY);
    }

    private List<QuartTravailDTO> creerListeQuartDTOParDefaut(DateTime datePeriode) {
	List<QuartTravailDTO> listeQuartsTravailDTO = new ArrayList<QuartTravailDTO>();
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode)));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(1))));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(2))));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(3))));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(4))));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(5))));
	listeQuartsTravailDTO.add(new QuartTravailDTO(utilitaireDate.formaterDate(datePeriode.plusDays(6))));
	return listeQuartsTravailDTO;
    }

}
