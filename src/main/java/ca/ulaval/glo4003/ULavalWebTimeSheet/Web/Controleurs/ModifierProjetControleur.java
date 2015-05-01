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
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.CreerProjetForm;


@Controller
@SessionAttributes({"projetDTO", "ancienNomProjet"})
public class ModifierProjetControleur
{
    private ServiceProjet serviceProjet;
    
    @ModelAttribute("creerProjetForm")
    public CreerProjetForm creerProjetForm() {
	return new CreerProjetForm();
    }

    @RequestMapping(value = ConstantesMVC.AFFICHER_MODIFIER_PROJET, method = RequestMethod.GET)
    public String obtenirModifierProjet(HttpServletRequest request, CreerProjetForm form, Model model,
	    @RequestParam("nomProjet") String nomProjet) throws ServiceProjetException {
		
	try {
	    model.addAttribute("creerProjetForm", new CreerProjetForm());
	    if ((ProjetDTO) request.getAttribute("projetDTO") == null) {
		AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute(
			ConstantesMVC.AUTHENTIFICATION);
	    	serviceProjet = new ServiceProjet(authentificationDTO.getAdresseCourriel());
	        ProjetDTO projetDTO = serviceProjet.obtenirProjetDTOParNomProjet(nomProjet);
	        request.setAttribute("projetDTO", projetDTO);
	        request.setAttribute("ancienNomProjet", projetDTO.getNomProjet());
	        model.addAttribute("projetDTO", projetDTO);
	        model.addAttribute("ancienNomProjet",  projetDTO.getNomProjet());
	    }
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return ConstantesMVC.VUE_MODIFIER_PROJET;
    }

    @RequestMapping(value =  ConstantesMVC.AFFICHER_MODIFIER_PROJET, method = RequestMethod.POST)
    public String modifierProjet(HttpServletRequest request, @ModelAttribute("projetDTO") ProjetDTO projetDTO, @ModelAttribute("ancienNomProjet") String ancienNomProjet,
	    CreerProjetForm creerProjetForm, Model model) throws ServiceProjetException {
	try {
	    	assignerParametresProjetDepuisInfoUI(projetDTO, creerProjetForm);
	    	serviceProjet.modifierProjet(projetDTO, ancienNomProjet);
	    } catch (ServiceProjetException e) {
		throw new ServiceProjetException(e.getMessage());
	    }
	request.removeAttribute("projetDTO");
	request.removeAttribute("ancienNomProjet");
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    private void assignerParametresProjetDepuisInfoUI(ProjetDTO projetDTO, CreerProjetForm form) {
	projetDTO.setNomProjet(form.getNomProjet());
	projetDTO.setDescriptionProjet(form.getDescriptionProjet());
    }
    
    @ExceptionHandler(ServiceProjetException.class)
    public ModelAndView gererServiceProjetException(HttpServletRequest request, ServiceProjetException ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_MODIFIER_PROJET);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerProjetForm", new CreerProjetForm());
	modelAndView.addObject("ancienNomProjet", request.getAttribute("ancienNomProjet"));
    	return modelAndView;
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(HttpServletRequest request, Exception ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_MODIFIER_PROJET);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerProjetForm", new CreerProjetForm());
	modelAndView.addObject("ancienNomProjet", request.getAttribute("ancienNomProjet"));
    	return modelAndView;
    }
}