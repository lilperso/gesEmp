package ca.ulaval.glo4003.ULavalWebTimeSheet.Web.ViewModels;

import java.util.ArrayList;
import java.util.List;

//Les check de null sont là parce que Spring nous retourne une liste null si rien n'est sélectionné...
//On a donc pas le choix si on veut pas vérifié la nullité partout dans notre application.
public class AssignerProjetForm
{
    List<String> projetSelectionne;
    List<String> tacheSelectionnee;
    List<String> employeSelectionne;
    
    public List<String> getProjetSelectionne() {
	if(projetSelectionne == null){
	    projetSelectionne = new ArrayList<String>();
	}
        return projetSelectionne;
    }
    public void setProjetSelectionne(List<String> projetSelectionne) {
        this.projetSelectionne = projetSelectionne;
    }
    public List<String> getTacheSelectionnee() {
	if(tacheSelectionnee == null){
	    tacheSelectionnee = new ArrayList<String>();
	}
        return tacheSelectionnee;
    }
    public void setTacheSelectionnee(List<String> tacheSelectionnee) {
        this.tacheSelectionnee = tacheSelectionnee;
    }
    public List<String> getEmployeSelectionne() {
	if(employeSelectionne == null){
	    employeSelectionne = new ArrayList<String>();
	}
        return employeSelectionne;
    }
    public void setEmployeSelectionne(List<String> employeSelectionne) {
        this.employeSelectionne = employeSelectionne;
    }
}
