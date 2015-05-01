package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceSecurite;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceSecuriteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.UtilisateurForm;

@Controller
public class ConnexionControleur
{
    private ServiceSecurite serviceSecurite = new ServiceSecurite();
    private ModelAndView modelAndView;

    @ModelAttribute("utilisateurForm")
    public UtilisateurForm utilisateurForm() {
	return new UtilisateurForm();
    }

    @RequestMapping(value = ConstantesMVC.ACTION_LOGIN, method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, UtilisateurForm form) {
	try {
	    AuthentificationDTO authentificationDTO = serviceSecurite.verifierAuthentificationUtilisateur(form.adresseCourriel, form.motDePasse);
	    modelAndView = new ModelAndView("redirect:" + ConstantesMVC.AFFICHER_ACCUEIL);
	    request.getSession().setAttribute(ConstantesMVC.AUTHENTIFICATION, authentificationDTO);
	} catch (ServiceSecuriteException e) {
	    modelAndView = new ModelAndView(ConstantesMVC.VUE_CONNEXION);
	    modelAndView.addObject("messageErreur", e.getMessage());
	}
	modelAndView.addObject("utilisateurForm", form);
	return modelAndView;
    }
}
