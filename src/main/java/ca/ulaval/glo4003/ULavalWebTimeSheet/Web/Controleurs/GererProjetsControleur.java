package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceConsulterAssignationsException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;

@Controller
public class GererProjetsControleur
{
    private ServiceProjet serviceProjet;
    
    @RequestMapping(value = ConstantesMVC.AFFICHER_GERER_PROJET)
    public ModelAndView genererVueGererProjets(HttpServletRequest request) throws ServiceProjetException, Exception {
	List<ProjetDTO> listeProjets = new ArrayList<ProjetDTO>();
	try {
	    AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute(
		    ConstantesMVC.AUTHENTIFICATION);
	    serviceProjet = new ServiceProjet(authentificationDTO.getAdresseCourriel());
	    listeProjets = serviceProjet.obtenirListeProjetsDTO();
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException (e.getMessage());
	}
	ModelAndView modelAndView = new ModelAndView(ConstantesMVC.VUE_GERER_PROJET, "listeProjets", listeProjets);
	return modelAndView;
    }
    
    @RequestMapping(value = ConstantesMVC.ACTION_SUPPRIMER_PROJET, method = RequestMethod.POST) 
    public String supprimerProjet(HttpServletRequest request, @RequestParam("nomProjet") String nomProjet) throws ServiceProjetException, Exception {
	try {
	    serviceProjet.supprimerProjet(nomProjet);
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException (e.getMessage());
	}
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    @RequestMapping(value = ConstantesMVC.ACTION_SUPPRIMER_TACHE, method = RequestMethod.POST) 
    public String supprimerTache(HttpServletRequest request, @RequestParam("nomTache") String nomTache,
	    @RequestParam("nomProjet") String nomProjet) throws ServiceProjetException, Exception {
	try {
	    serviceProjet.supprimerTache(nomProjet, nomTache);
	} catch (ServiceProjetException e) {
	    throw new ServiceProjetException (e.getMessage());
	}
	return ("redirect:" + ConstantesMVC.AFFICHER_GERER_PROJET);
    }
    
    @ExceptionHandler(ServiceConsulterAssignationsException.class)
    public ModelAndView gererServiceProjetException(ServiceProjetException ex) {
	
    	ModelAndView model = new ModelAndView("erreurs/erreur_exception");
    	model.addObject("errMsg", ex.getMessage());
    	return model;
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(Exception ex) {

	ModelAndView model = new ModelAndView("erreurs/erreur_generique");
	return model;
    }
}
