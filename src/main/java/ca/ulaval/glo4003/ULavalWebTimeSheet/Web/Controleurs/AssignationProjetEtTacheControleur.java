package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ListeAdresseCourrielEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceAssignationProjet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceAssignationProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels.AssignerProjetForm;

@Controller
public class AssignationProjetEtTacheControleur
{
    private ServiceAssignationProjet serviceAssignation;

    @RequestMapping(value = ConstantesMVC.AFFICHER_LISTE_EMPLOYE_ET_PROJET)
    public ModelAndView afficherVueAssignerProjet(HttpSession session, AssignerProjetForm assignerProjetForm) throws ServiceAssignationProjetException {
	ModelAndView vueAssignationProjet = new ModelAndView(ConstantesMVC.VUE_ASSIGNATION_PROJET);
	AuthentificationDTO authentificationDTO = (AuthentificationDTO) session.getAttribute(ConstantesMVC.AUTHENTIFICATION);
	serviceAssignation = new ServiceAssignationProjet(authentificationDTO.getAdresseCourriel());
	List<ProjetDTO> listeProjet = serviceAssignation.obtenirListeDesProjets();
	ListeAdresseCourrielEmployeDTO listeEmail = serviceAssignation.obtenirListeAdresseEmailEmployeDuGestionnaire();
	vueAssignationProjet.addObject(ConstantesMVC.LISTE_PROJET_DTO, listeProjet);
	vueAssignationProjet.addObject(ConstantesMVC.LISTE_COURRIEL_EMPLOYE_DTO, listeEmail);
	return vueAssignationProjet;
    }

    @RequestMapping(value = ConstantesMVC.ACTION_ASSIGNER_PROJET)
    public ModelAndView assignerProjetAuxEmployes(HttpSession session, AssignerProjetForm assignerProjetForm) throws ServiceAssignationProjetException {
	ModelAndView vueListeEmploye = new ModelAndView(ConstantesMVC.SUCCES);
	AuthentificationDTO authentificationDTO = (AuthentificationDTO) session.getAttribute(ConstantesMVC.AUTHENTIFICATION);
	serviceAssignation = new ServiceAssignationProjet(authentificationDTO.getAdresseCourriel());
	serviceAssignation.assignerLesTachesAuxEmployes(assignerProjetForm.getEmployeSelectionne(), assignerProjetForm.getProjetSelectionne(),
		assignerProjetForm.getTacheSelectionnee());
	vueListeEmploye.addObject(ConstantesMVC.SUCCES_MESSAGE,ConstantesMVC.SUCCES_ASSIGNATION);
	return vueListeEmploye;
    }

    @ModelAttribute("assignerProjetForm")
    public AssignerProjetForm assignerProjetForm() {
	return new AssignerProjetForm();
    }

}
