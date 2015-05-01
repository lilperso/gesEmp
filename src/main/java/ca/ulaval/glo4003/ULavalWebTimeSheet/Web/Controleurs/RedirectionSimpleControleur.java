package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.CreerEmployeForm;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.QuartTravailForm;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.UtilisateurForm;

@Controller
public class RedirectionSimpleControleur
{

    @RequestMapping("/login")
    public String login(Model model) {
	model.addAttribute("utilisateurForm", new UtilisateurForm());
	return "connexion";
    }

    @RequestMapping("/logout-action")
    public String logout(HttpServletRequest request) {
	request.getSession().invalidate();
	return "redirect:/login";
    }

    @RequestMapping("/redirect-creerEmploye")
    public String redirectCreerEmploye(Model model) {
	model.addAttribute("creerEmployeForm", new CreerEmployeForm());
	return "creerEmploye";
    }
        
    @RequestMapping("/redirect-modalTest")
    public String redirectModalTest(HttpServletRequest request, Model model){
	request.getSession().setAttribute("test", true);
	model.addAttribute("creerQuartTravailForm", new QuartTravailForm());
	return "ModalTest";
    }
}