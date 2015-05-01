package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ProjetDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Projet;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Tache;

public class FabriqueProjetDTOTest
{
    private static final String NOM_TACHE = "nom_tache";
    private static final String DESC_TACHE = "desc_tache";
    private static final String NOM_PROJET = "nom_projet";
    private static final String DESC_PROJET = "desc_projet";
    private static final List<String>  LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET = new ArrayList<String>();
    private static final List<String> LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_TACHE = new ArrayList<String>();

    private FabriqueProjetDTO factoryProjetDTO = new FabriqueProjetDTO();

    @Test
    public void creerListeProjetDTOAvecUnProjetContenantUneTacheRetourneUnProjetDTOContenantUneTacheDTO(){
	List<Projet> listeProjet = preparerUnProjetAvecUneTache();
	
	List<ProjetDTO> listeProjetDTO = factoryProjetDTO.creerListeProjetDTO(listeProjet);
	
	assertEquals(1,listeProjetDTO.size());
	assertEquals(NOM_PROJET,listeProjetDTO.get(0).getNomProjet());
	assertEquals(DESC_PROJET,listeProjetDTO.get(0).getDescriptionProjet());
	assertEquals(1,listeProjetDTO.get(0).getListeTachesDTO().size());
	assertEquals(NOM_TACHE,listeProjetDTO.get(0).getListeTachesDTO().get(0).getNomTache());
	assertEquals(DESC_TACHE,listeProjetDTO.get(0).getListeTachesDTO().get(0).getDescriptionTache());
    }

    private List<Projet> preparerUnProjetAvecUneTache() {
	List<Projet> listeProjet = new ArrayList<Projet>();
	List<Tache> listeTache = new ArrayList<Tache>();
	listeTache.add(new Tache(NOM_TACHE, DESC_TACHE, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_TACHE));
	listeProjet.add(new Projet(NOM_PROJET, DESC_PROJET, listeTache, LISTE_RESSOURCE_HUMAINES_ASSIGNEES_A_PROJET));
	return listeProjet;
    }
}
