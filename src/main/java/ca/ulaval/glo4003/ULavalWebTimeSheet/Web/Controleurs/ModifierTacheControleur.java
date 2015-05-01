package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.CreerTacheForm;

@Controller
@SessionAttributes({"nomProjet", "tacheDTO", "ancienNomTache", "indexTache"})
public class ModifierTacheControleur
{
    ServiceProjet serviceProjet;
    
    @ModelAttribute("creerTacheForm")
    public CreerTacheForm creerTacheForm() {
	return new CreerTacheForm();
    }
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_MODIFIER_TACHE, method = RequestMethod.GET)
    public String obtenirModifierrTache(HttpServletRequest request, Model model, CreerTacheForm creerTacheFrom,
	    @RequestParam("indexTache") int indexTache, @RequestParam("nomProjet") String nomProjet) throws ServiceProjetException {
	ProjetDTO projetDTO;
	try {
	    if ((TacheDTO) request.getAttribute("tacheDTO") == null) {
		model.addAttribute("creerTacheFrom", new CreerTacheForm());
		
		AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute(
			ConstantesMVC.AUTHENTIFICATION);
		serviceProjet = new ServiceProjet(authentificationDTO.getAdresseCourriel());
		projetDTO = serviceProjet.obtenirProjetDTOParNomProjet(nomProjet);
		TacheDTO tacheDTO = serviceProjet.obtenirTacheDTOParIndexTache(projetDTO, indexTache);
		request.setAttribute("nomProjet", projetDTO.getNomProjet());
		model.addAttribute("nomProjet", projetDTO.getNomProjet());
		request.setAttribute("tacheDTO", tacheDTO);
		model.addAttribute("tacheDTO", tacheDTO);
		request.setAttribute("ancienNomTache", tacheDTO.getNomTache());
		model.addAttribute("ancienNomTache", tacheDTO.getNomTache());
		request.setAttribute("indexTache", indexTache);
		model.addAttribute("indexTache", indexTache);
	    }

	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return ConstantesMVC.VUE_MODIFIER_TACHE;
    }
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_MODIFIER_TACHE, method = RequestMethod.POST)
    public String modifierTache(HttpServletRequest request, Model model, @ModelAttribute("tacheDTO") TacheDTO tacheDTO,
	    @ModelAttribute("nomProjet") String nomProjet, @ModelAttribute("ancienNomTache") String ancienNomTache, CreerTacheForm form) throws ServiceProjetException {
	
	try {
	    assignerParametresTacheDepuisInfoUI(tacheDTO, form);
	    serviceProjet.modifierTache(nomProjet, tacheDTO, ancienNomTache);
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	
	request.removeAttribute("tacheDTO");
	request.removeAttribute("nomProjet");
	request.removeAttribute("ancienNomTache");
	request.removeAttribute("indexTache");
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    private void assignerParametresTacheDepuisInfoUI(TacheDTO tacheDTO, CreerTacheForm form) {
	tacheDTO.setNomTache(form.getNomTache());
	tacheDTO.setDescriptionTache(form.getDescriptionTache());
    }
    
    @ExceptionHandler(ServiceProjetException.class)
    public ModelAndView gererServiceProjetException(HttpServletRequest request, ServiceProjetException ex) {
	
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_MODIFIER_TACHE);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerTacheForm", new CreerTacheForm());
	modelAndView.addObject("nomProjet", request.getAttribute("nomProjet"));
	modelAndView.addObject("ancienNomTache", request.getAttribute("ancienNomTache"));
    	return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(HttpServletRequest request, Exception ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_MODIFIER_TACHE);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerTacheForm", new CreerTacheForm());
	modelAndView.addObject("nomProjet", request.getAttribute("nomProjet"));
	modelAndView.addObject("ancienNomTache", request.getAttribute("ancienNomTache"));
	modelAndView.addObject("indexTache", request.getAttribute("indexTache"));
    	return modelAndView;
    }
}
