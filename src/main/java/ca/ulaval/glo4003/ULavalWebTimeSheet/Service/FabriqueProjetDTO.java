package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.TacheDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;

public class FabriqueProjetDTO
{

    public FabriqueProjetDTO() {
	
    }

    public List<ProjetDTO> creerListeProjetDTO(List<Projet> listeProjet) {
	List<ProjetDTO> listeProjetDTO = new ArrayList<ProjetDTO>();
	for(Projet projet: listeProjet){
	    List<TacheDTO> listeTache = creerTacheDTO(projet);
	    listeProjetDTO.add(new ProjetDTO(projet.getNomProjet(),projet.getDescriptionProjet(),listeTache, projet.getListeRessourceHumainesAssignees()));
	}
	return listeProjetDTO;
    }
    
    private List<TacheDTO> creerTacheDTO(Projet projet) {
   	List<TacheDTO> listeTacheDTO = new ArrayList<TacheDTO>();
   	for (Tache tache: projet.getListeTaches()){
   	    listeTacheDTO.add(new TacheDTO(tache.getNomTache(),tache.getDescriptionTache(), tache.getListeRessourceHumainesAssignees()));
   	}
   	return listeTacheDTO;
       }
    
}
