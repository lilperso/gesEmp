package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceAssignationProjetException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceConsulterAssignationsException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;

public class GestionErreurControleur
{

    @ExceptionHandler(ServiceConsulterAssignationsException.class)
    public ModelAndView gererServiceConsulterAssignationsException(ServiceConsulterAssignationsException ex) {
	ModelAndView model = new ModelAndView("erreurs/erreur_exception");
	model.addObject("errMsg", ex.getMessage());
	return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView gererExceptions(Exception ex) {

	ModelAndView model = new ModelAndView("erreurs/erreur_generique");
	return model;
    }
    
    @ExceptionHandler(ServiceAssignationProjetException.class)
    public ModelAndView gererServiceAssignationProjetException(ServiceAssignationProjetException ex) {
	ModelAndView model = new ModelAndView(ConstantesMVC.VUE_ERREUR_EXCEPTION);
	model.addObject(ConstantesMVC.ERR_MESSAGE, ex.getMessage());
	return model;
    }

}
