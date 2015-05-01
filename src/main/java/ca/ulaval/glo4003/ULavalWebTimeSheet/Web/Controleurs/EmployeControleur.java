package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.PeriodeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.QuartTravailDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceTravail;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.serviceTravailException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.QuartTravailForm;

@Controller
public class EmployeControleur
{
    private static final int NB_JOURS_SEMAINE = 7;
    private ServiceTravail serviceTravail = new ServiceTravail();

    @RequestMapping(value = ConstantesMVC.AFFICHER_ACCUEIL)
    public ModelAndView accueil(HttpServletRequest request) {
	if (request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION) == null) {
	    return new ModelAndView("redirect:" + ConstantesMVC.AFFICHER_LOGIN);
	} else {
	    return new ModelAndView("accueilUtilisateur");
	}
    }

    @RequestMapping(value = ConstantesMVC.AFFICHER_ENTRER_TEMPS)
    public ModelAndView redirectEntrerTemps(HttpServletRequest request) {
	DateTime dateDujour = new DateTime();
	return afficherLaPeriodeSelonDate(dateDujour, request);
    }

    @RequestMapping("/entrerTemps")
    public ModelAndView entrerTemps(HttpServletRequest request) {
	PeriodeDTO periodeCourante = (PeriodeDTO) request.getSession().getAttribute("periodeDTO");
	DateTime dateDebutPeriode = new DateTime(periodeCourante.getDateDebut());
	return afficherLaPeriodeSelonDate(dateDebutPeriode, request);
    }

    private ModelAndView afficherLaPeriodeSelonDate(DateTime dateDuJour, HttpServletRequest request) {
	AuthentificationDTO authentification = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	PeriodeDTO periodeCourante = null;
	DateTime lundiDeCettePeriode = dateDuJour.withDayOfWeek(DateTimeConstants.MONDAY);
	try {
	    periodeCourante = serviceTravail.obtenirPeriodeDeTravailDTO(lundiDeCettePeriode, authentification.getAdresseCourriel());
	} catch (serviceTravailException e) {
	    return deconnexionErreur(e);
	}
	request.getSession().setAttribute("periodeDTO", periodeCourante);
	String indiceQuartTravail = request.getParameter("indexPeriode");
	return afficherVue(request, periodeCourante, indiceQuartTravail);
    }

    private ModelAndView afficherVue(HttpServletRequest request, PeriodeDTO periodeCourante, String indiceQuartTravail) {
	if (indiceQuartTravail != null) {
	    QuartTravailForm quartForm = remplirQuartFormAvecQuartCourant(indiceQuartTravail, periodeCourante);
	    return afficherVueAvecModal(quartForm);
	} else {
	    return afficherVueSansModal(request);
	}
    }

    private ModelAndView deconnexionErreur(serviceTravailException e) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_CONNEXION);
	modelAndView.addObject("messageErreur", e.getMessage());
	return modelAndView;
    }

    private QuartTravailForm remplirQuartFormAvecQuartCourant(String indiceQuartTravail, PeriodeDTO periodeCourante) {
	int quartSelectionne = Integer.parseInt(indiceQuartTravail);
	QuartTravailDTO quartCourant = periodeCourante.listeQuartsTravailDTO.get(quartSelectionne);
	QuartTravailForm quartForm = creerQuartForm(quartCourant);
	return quartForm;
    }

    private QuartTravailForm creerQuartForm(QuartTravailDTO quartCourant) {
	QuartTravailForm quartForm = new QuartTravailForm();
	quartForm.dateQuart = quartCourant.getDateQuartTravail();
	quartForm.heureEntree = quartCourant.getHeureEntree();
	quartForm.heureSortie = quartCourant.getHeureSortie();
	quartForm.deplacement = quartCourant.getLongueurDeplacement();
	quartForm.noteDeplacement = quartCourant.getNoteDeplacement();
	quartForm.depense = quartCourant.getMontantDepense();
	quartForm.noteDepense = quartCourant.getNoteDepense();
	quartForm.noteHeure = quartCourant.getNoteQuart();
	return quartForm;
    }

    private ModelAndView afficherVueAvecModal(QuartTravailForm quartForm) {
	ModelAndView entrerTempsView = new ModelAndView(ConstantesMVC.VUE_ENTRER_HEURE);
	entrerTempsView.addObject("QuartTravailForm", quartForm);
	entrerTempsView.addObject("modifierPeriode", true);
	configurerHeaderModalEtRetourService(quartForm, entrerTempsView);
	return entrerTempsView;
    }

    private void configurerHeaderModalEtRetourService(QuartTravailForm quartForm, ModelAndView entrerTempsView) {
	if (quartForm.heureEntree.isEmpty()) {
	    entrerTempsView.addObject("optionQuart", "Ajouter");
	    quartForm.appelService = "Ajouter";
	} else {
	    entrerTempsView.addObject("optionQuart", "Modifier");
	    quartForm.appelService = "Modifier";
	}
    }

    private ModelAndView afficherVueSansModal(HttpServletRequest request) {
	ModelAndView entrerTempsView = new ModelAndView(ConstantesMVC.VUE_ENTRER_HEURE);
	entrerTempsView.addObject("modifierPeriode", false);
	return entrerTempsView;
    }

    @RequestMapping(value = ConstantesMVC.AFFICHER_ENTRER_HEURES)
    public ModelAndView entrerHeuresEmploye(HttpServletRequest request) {
	if (request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION) == null) {
	    return new ModelAndView("redirect:" + ConstantesMVC.AFFICHER_LOGIN);
	} else {
	    return new ModelAndView(ConstantesMVC.VUE_ENTRER_HEURE);
	}
    }

    @RequestMapping(value = ConstantesMVC.ACTION_ENTRER_HEURES, method = RequestMethod.POST)
    public ModelAndView entrerHeuresEmployeAction(HttpServletRequest request, QuartTravailForm quartForm) {

	try {
	    QuartTravailDTO qtDTO = creerDTODepuisForm(quartForm);
	    AuthentificationDTO utilisateurCourant = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	    appelerServiceTravail(qtDTO, utilisateurCourant, quartForm.appelService);
	} catch (serviceTravailException e) {
	    return reafficherVueAvecErreurs(quartForm, e.getMessage(), request);
	}
	return afficherLaPeriodeSelonDate(new DateTime(quartForm.dateQuart), request);

    }

    private void appelerServiceTravail(QuartTravailDTO qtDTO, AuthentificationDTO utilisateurCourant, String methode) throws serviceTravailException {

	if (methode.equals("Ajouter")) {
	    serviceTravail.ajouterQuartsTravailPourPeriodeCourante(qtDTO, utilisateurCourant);
	} else {
	    serviceTravail.modifierQuartsTravail(qtDTO, utilisateurCourant);
	}

    }

    private QuartTravailDTO creerDTODepuisForm(QuartTravailForm quartForm) {
	QuartTravailDTO qtDTO = new QuartTravailDTO(quartForm.dateQuart);
	qtDTO.setHeureEntree(quartForm.heureEntree);
	qtDTO.setHeureSortie(quartForm.heureSortie);
	qtDTO.setLongueurDeplacement(quartForm.deplacement);
	qtDTO.setNoteDeplacement(quartForm.noteDeplacement);
	qtDTO.setMontantDepense(quartForm.depense);
	qtDTO.setNoteDepense(quartForm.noteDepense);
	qtDTO.setNoteQuart(quartForm.noteHeure);
	return qtDTO;
    }

    private ModelAndView reafficherVueAvecErreurs(QuartTravailForm quartForm, String messageErreur, HttpServletRequest request) {
	ModelAndView entrerTempsView = afficherVueAvecModal(quartForm);
	entrerTempsView.addObject("messageErreur", messageErreur);
	return entrerTempsView;
    }

    @RequestMapping(value = ConstantesMVC.ACTION_PERIODE_SUIVANTE)
    public ModelAndView periodeSuivanteAction(HttpServletRequest request) {
	PeriodeDTO periodeCourante = (PeriodeDTO) request.getSession().getAttribute("periodeDTO");
	DateTime dateDebutPeriodeSuivante = DateTime.parse(periodeCourante.getDateFin()).plusDays(1);
	return afficherLaPeriodeSelonDate(dateDebutPeriodeSuivante, request);
    }

    @RequestMapping(value = ConstantesMVC.ACTION_PERIODE_PRECEDENTE)
    public ModelAndView periodePrecedenteAction(HttpServletRequest request) {

	PeriodeDTO periodeCourante = (PeriodeDTO) request.getSession().getAttribute("periodeDTO");
	DateTime dateDebutPeriodePrecedente = DateTime.parse(periodeCourante.getDateDebut()).minusDays(NB_JOURS_SEMAINE);
	return afficherLaPeriodeSelonDate(dateDebutPeriodePrecedente, request);
    }

    @ModelAttribute("QuartTravailForm")
    public QuartTravailForm quartTravailForm() {
	return new QuartTravailForm();
    }
}
