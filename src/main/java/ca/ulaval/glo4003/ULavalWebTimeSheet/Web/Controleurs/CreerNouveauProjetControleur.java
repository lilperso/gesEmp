package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.CreerProjetForm;

@Controller
@SessionAttributes("projetDTO")
public class CreerNouveauProjetControleur
{
    private ServiceProjet serviceProjet;
    
    @ModelAttribute("creerProjetForm")
    public CreerProjetForm creerProjetForm() {
	return new CreerProjetForm();
    }
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_CREER_PROJET, method = RequestMethod.GET)
    public String obtenirNouveauProjet(HttpServletRequest request, CreerProjetForm form, Model model) {
		
	model.addAttribute("creerProjetForm", new CreerProjetForm());
	if ((ProjetDTO) request.getAttribute("projetDTO") == null) {
	    ProjetDTO projetDTO = new ProjetDTO("", "", new ArrayList<TacheDTO>(), new ArrayList<String>());
	    request.setAttribute("projetDTO", projetDTO);
	    model.addAttribute("projetDTO", projetDTO);
	}
	
	return ConstantesMVC.VUE_CREER_PROJET;
    }

    @RequestMapping(value = ConstantesMVC.AFFICHER_CREER_PROJET, method = RequestMethod.POST)
    public String nouveauProjet(HttpServletRequest request, @ModelAttribute("projetDTO") ProjetDTO projetDTO,
	    CreerProjetForm creerProjetForm, Model model) throws ServiceProjetException {
	try {
	    	AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute(
			"authentificationDTO");
	    	serviceProjet = new ServiceProjet(authentificationDTO.getAdresseCourriel());
	    	assignerParametresProjetDepuisInfoUI(projetDTO, creerProjetForm);
	    	serviceProjet.ajouterProjet(projetDTO);
	    } catch (ServiceProjetException e) {
		throw new ServiceProjetException(e.getMessage());
	    }
	request.removeAttribute("projetDTO");
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    private void assignerParametresProjetDepuisInfoUI(ProjetDTO projetDTO, CreerProjetForm form) {
	projetDTO.setNomProjet(form.getNomProjet());
	projetDTO.setDescriptionProjet(form.getDescriptionProjet());
	projetDTO.setListeRessourceHumainesAssignees(new ArrayList<String>());
    }
    
    @ExceptionHandler(ServiceProjetException.class)
    public ModelAndView gererServiceProjetException(HttpServletRequest request, ServiceProjetException ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_CREER_PROJET);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerProjetForm", new CreerProjetForm());
	modelAndView.addObject("projetDTO", request.getAttribute("projetDTO"));
    	return modelAndView;
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(HttpServletRequest request, Exception ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_CREER_PROJET);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerProjetForm", new CreerProjetForm());
	modelAndView.addObject("projetDTO", request.getAttribute("projetDTO"));
    	return modelAndView;
    }
}
