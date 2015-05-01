package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.AuthentificationDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceConsulterAssignations;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceConsulterAssignationsException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;

@Controller
public class ConsulterAssignationsControleur
{
    @RequestMapping(ConstantesMVC.AFFICHER_ASSIGNATION)
    public ModelAndView redirectConsulterAssignations(HttpServletRequest request, Model model) throws ServiceConsulterAssignationsException {
	ServiceConsulterAssignations serviceConsulterAssignation;
	List<ProjetDTO> listeProjetsDTO = new ArrayList<ProjetDTO>();
	AuthentificationDTO authentificationDTO = (AuthentificationDTO) request.getSession().getAttribute("authentificationDTO");
	serviceConsulterAssignation = new ServiceConsulterAssignations(authentificationDTO.getSuperieurImmediat());
	listeProjetsDTO = serviceConsulterAssignation.obtenirListeProjetsDTOAssignesARessourceHumaine(authentificationDTO.getAdresseCourriel());
	ModelAndView modelAndView = new ModelAndView("consulterAssignations", "listeProjetsDTO", listeProjetsDTO);
	return modelAndView;
    }
    
}