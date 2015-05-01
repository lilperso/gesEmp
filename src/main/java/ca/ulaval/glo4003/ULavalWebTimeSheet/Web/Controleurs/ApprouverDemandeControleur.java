package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.InfoEmployeAvecPeriodesDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.ServiceApprobation;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Service.Exception.ServiceApprobationException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Web.Controleurs.Constantes.ConstantesMVC;

@Controller
public class ApprouverDemandeControleur
{
    private ServiceApprobation serviceApprobation = new ServiceApprobation();

    @RequestMapping(value = ConstantesMVC.AFFICHER_LISTE_DEMANDES_APPROBATION, method = RequestMethod.POST)
    public String listeDemandesApprobationsPourUnEmploye(HttpServletRequest request) {

	String courrielUtilisateur = request.getParameter("adresseCourriel").trim();

	InfoEmployeAvecPeriodesDTO periodesPourUnEmployeDTO;
	try {
	    periodesPourUnEmployeDTO = serviceApprobation.obtenirListeDesPeriodesAvecDemandesPourUnEmploye(courrielUtilisateur);
	} catch (ServiceApprobationException e) {
	    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	request.getSession().setAttribute("infoEmployeAvecPeriodes", periodesPourUnEmployeDTO);
	return ConstantesMVC.VUE_LISTE_APPROBATION;
    }

    @RequestMapping(value = ConstantesMVC.ACTION_APPROUVER_DEPENSE, method = RequestMethod.POST)
    public String approuverDemandeDepense(HttpServletRequest request, HttpServletResponse response) {
	DateTimeFormatter formatDate = DateTimeFormat.forPattern("yyyy-MM-dd");

	String adresseCourriel = request.getParameter("adresseCourriel").trim();
	DateTime dateQuart = formatDate.parseDateTime(request.getParameter("dateQuart").trim());

	try {
	    serviceApprobation.trouverEtApprouverDepense(adresseCourriel, dateQuart);
	} catch (ServiceApprobationException ex) {
	    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	return listeDemandesApprobationsPourUnEmploye(request);
    }

    @RequestMapping(value = ConstantesMVC.ACTION_APPROUVER_DEPLACEMENT, method = RequestMethod.POST)
    public String approuverDemandeDeplacement(HttpServletRequest request) throws Exception {
	DateTimeFormatter formatDate = DateTimeFormat.forPattern("yyyy-MM-dd");

	String adresseCourriel = request.getParameter("adresseCourriel").trim();
	DateTime dateQuart = formatDate.parseDateTime(request.getParameter("dateQuart").trim());

	try {
	    serviceApprobation.trouverEtApprouverDeplacement(adresseCourriel, dateQuart);
	} catch (ServiceApprobationException ex) {
	    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	return listeDemandesApprobationsPourUnEmploye(request);
    }

}
