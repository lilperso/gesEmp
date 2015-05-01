package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceCreationRessourceHumaine;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceModifierEmploye;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceCreationRessourceHumaineException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceModifierEmployeException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.CreerEmployeForm;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.InformationEmployeForm;

@Controller
public class GestionnaireControleur
{

    private ServiceModifierEmploye serviceModifierEmploye = new ServiceModifierEmploye();
    private ServiceCreationRessourceHumaine serviceCreation = new ServiceCreationRessourceHumaine();

    @ModelAttribute("creerEmployeForm")
    public CreerEmployeForm creerFormCreerEmploye() {
	return new CreerEmployeForm();
    }

    @RequestMapping(value = ConstantesMVC.ACTION_CREER_EMPLOYE)
    public ModelAndView creerEmploye(HttpServletRequest request, CreerEmployeForm form) {
	ModelAndView modelAndView;
	try {
	    AuthentificationDTO superieur = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	    serviceCreation = new ServiceCreationRessourceHumaine(superieur.getAdresseCourriel());
	    serviceCreation.demanderCreationNouvelEmploye(form.adresseResidence, form.nom, form.prenom, form.numTelephone, form.adresseCourriel,
		    form.motDePasse);
	    modelAndView = new ModelAndView(ConstantesMVC.VUE_ACCUEIL_UTILISATEUR);
	} catch (ServiceCreationRessourceHumaineException e) {
	    modelAndView = reafficherVueAvecErreurs(form, e.getMessage());
	}
	modelAndView.addObject("creerEmployeForm", form);
	return modelAndView;
    }

    private ModelAndView reafficherVueAvecErreurs(CreerEmployeForm form, String listeErreur) {
	ModelAndView creerEmployeView = new ModelAndView(ConstantesMVC.VUE_CREER_EMPLOYE);
	creerEmployeView.addObject("listeErreur", listeErreur);
	creerEmployeView.addObject("creerEmployeForm", form);
	return creerEmployeView;
    }

    @RequestMapping(ConstantesMVC.AFFICHER_LISTE_EMPLOYE)
    public String redirectListeEmploye(HttpServletRequest request) {
	AuthentificationDTO superieur = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	serviceCreation = new ServiceCreationRessourceHumaine(superieur.getAdresseCourriel());
	List<InfoEmployeDTO> listeEmploye = serviceCreation.obtenirDtoListeEmploye(superieur.getAdresseCourriel());
	request.getSession().setAttribute("listeEmploye", listeEmploye);
	request.getSession().setAttribute("modifierEmploye", false);
	return ConstantesMVC.VUE_LISTE_EMPLOYE;
    }

    @RequestMapping(ConstantesMVC.AFFICHER_MODIFIER_EMPLOYE)
    public ModelAndView redirectModifierEmploye(HttpServletRequest request, InformationEmployeForm infoForm) {
	infoForm = initialiserInformationEmployeForm(request);
	String indiceEmployeModification = request.getParameter("indexEmploye");
	boolean afficherModal = false;
	if (indiceEmployeModification != null) {
	    afficherModal = true;
	}
	return afficherVueSelonModificationEmploye(infoForm, request, afficherModal);

    }

    private InformationEmployeForm initialiserInformationEmployeForm(HttpServletRequest request) {
	InformationEmployeForm infoForm = new InformationEmployeForm();
	InfoEmployeDTO employeAModifier = obtenirEmployeSelonIndexe(request, Integer.parseInt(request.getParameter("indexEmploye")));
	double tauxHoraire = employeAModifier.getTauxHoraire();
	infoForm.setTauxHoraire(Double.toString(tauxHoraire));
	return infoForm;
    }

    private InfoEmployeDTO obtenirEmployeSelonIndexe(HttpServletRequest request, int indexe) {
	AuthentificationDTO superieur = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	List<InfoEmployeDTO> listeEmploye = serviceCreation.obtenirDtoListeEmploye(superieur.getAdresseCourriel());
	InfoEmployeDTO employeAModifier = listeEmploye.get(indexe);
	return employeAModifier;
    }

    @RequestMapping(value = ConstantesMVC.ACTION_MODIFIER_EMPLOYE, method = RequestMethod.POST)
    public ModelAndView modifierEmployeAction(HttpServletRequest request, InformationEmployeForm infoForm) {

	InfoEmployeDTO employeAModifier = obtenirEmployeSelonIndexe(request, Integer.parseInt(request.getSession().getAttribute("indexEmploye").toString()));
	try {
	    AuthentificationDTO superieur = (AuthentificationDTO) request.getSession().getAttribute(ConstantesMVC.AUTHENTIFICATION);
	    serviceModifierEmploye = new ServiceModifierEmploye(superieur.getAdresseCourriel());
	    serviceModifierEmploye.mettreAJourEmploye(employeAModifier.getAdresseCourriel(), infoForm.getTauxHoraire());
	} catch (ServiceModifierEmployeException e) {
	    return reafficherVueModalAvecErreurs(infoForm, e.getMessage(), request);
	}
	return new ModelAndView("redirect:" + ConstantesMVC.AFFICHER_LISTE_EMPLOYE);
    }

    private ModelAndView reafficherVueModalAvecErreurs(InformationEmployeForm infoForm, String erreur, HttpServletRequest request) {
	ModelAndView listeEmployeView = new ModelAndView(ConstantesMVC.VUE_LISTE_EMPLOYE);
	listeEmployeView.addObject("InformationEmployeForm", infoForm);
	request.getSession().setAttribute("modifierEmploye", true);
	listeEmployeView.addObject("listeErreur", erreur);
	return listeEmployeView;
    }

    private ModelAndView afficherVueSelonModificationEmploye(InformationEmployeForm infoForm, HttpServletRequest request, boolean afficherModal) {
	ModelAndView listeEmployeView = new ModelAndView(ConstantesMVC.VUE_LISTE_EMPLOYE);
	if (afficherModal) {
	    listeEmployeView.addObject("InformationEmployeForm", infoForm);
	}
	request.getSession().setAttribute("modifierEmploye", afficherModal);
	request.getSession().setAttribute("indexEmploye", request.getParameter("indexEmploye"));
	return listeEmployeView;
    }

    @ModelAttribute("InformationEmployeForm")
    public InformationEmployeForm informationEmployeForm() {
	return new InformationEmployeForm();
    }
}
