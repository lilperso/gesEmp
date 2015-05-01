package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.ArrayList;

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
@SessionAttributes({"projetDTO", "tacheDTO"})
public class CreerNouvelleTacheControleur
{
    ServiceProjet serviceProjet;
    
    @ModelAttribute("creerTacheForm")
    public CreerTacheForm creerTacheForm() {
	return new CreerTacheForm();
    }
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_CREER_TACHE, method = RequestMethod.GET, params = "indexProjet")
    public String obtenirAjouterTache(HttpServletRequest request, Model model, CreerTacheForm creerTacheFrom, @RequestParam("indexProjet") int indexProjet) throws ServiceProjetException {
	ProjetDTO projetDTO;
	try {
	    model.addAttribute("creerTacheFrom", new CreerTacheForm());
	    
	    if ((ProjetDTO) request.getAttribute("projetDTO") == null) {
		AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute(
			ConstantesMVC.AUTHENTIFICATION);
		serviceProjet = new ServiceProjet(authentificationDTO.getAdresseCourriel());
		projetDTO = serviceProjet.obtenirProjetDTOParIndexProjet(indexProjet);
		model.addAttribute("projetDTO", projetDTO);
		request.setAttribute("projetDTO", projetDTO);
	    }
   
	    if ((TacheDTO) request.getAttribute("tacheDTO") == null) {
		TacheDTO tacheDTO = new TacheDTO("", "", new ArrayList<String>());
		request.setAttribute("tacheDTO", tacheDTO);
		model.addAttribute("tacheDTO", tacheDTO);
	    }
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	return ConstantesMVC.VUE_CREER_TACHE;
    }
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_CREER_TACHE, method = RequestMethod.POST)
    public String ajouterTache(HttpServletRequest request, Model model, @ModelAttribute("tacheDTO") TacheDTO tacheDTO,
	    @ModelAttribute("projetDTO") ProjetDTO projetDTO, CreerTacheForm form) throws ServiceProjetException {
	
	try {
	    assignerParametresTacheDepuisInfoUI(tacheDTO, form);
	    request.setAttribute("tacheDTO", tacheDTO);
	    model.addAttribute("tacheDTO", tacheDTO);
	    serviceProjet.ajouterTache(projetDTO, tacheDTO);
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException(e.getMessage());
	}
	
	request.removeAttribute("tacheDTO");
	request.removeAttribute("projetDTO");
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    private void assignerParametresTacheDepuisInfoUI(TacheDTO tacheDTO, CreerTacheForm form) {
	tacheDTO.setNomTache(form.getNomTache());
	tacheDTO.setDescriptionTache(form.getDescriptionTache());
	tacheDTO.setListeRessourceHumaineAssignees(new ArrayList<String>());
    }
    
    @ExceptionHandler(ServiceProjetException.class)
    public ModelAndView gererServiceProjetException(HttpServletRequest request, ServiceProjetException ex) {
	
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_CREER_TACHE);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerTacheForm", new CreerTacheForm());
	modelAndView.addObject("indexTache", -1);
    	return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(HttpServletRequest request, Exception ex) {
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_CREER_TACHE);
	modelAndView.addObject("errMsg", ex.getMessage());
	modelAndView.addObject("creerTacheForm", new CreerTacheForm());
	modelAndView.addObject("indexTache", -1);
    	return modelAndView;
    }
}
