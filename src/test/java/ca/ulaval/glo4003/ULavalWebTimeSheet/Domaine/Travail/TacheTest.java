package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TacheTest
{
    private static final String EMPLOYE1 = "employe1";
    private static final String EMPLOYE2 = "employe2";
    private Tache tache;
    
    @Before
    public void initialiser(){
	tache = new Tache();
    }
    
    @Test 
    public void etantDonneUneListeDemployeSelectionneCeuxCiSontAssignerALaTache(){
	List<String> listeEmploye = creerDeuxEmailEmploye();
	
	tache.ajouterEmployesACetteTache(listeEmploye);
	
	assertEquals(2, tache.getListeRessourceHumainesAssignees().size());
    }
    
    @Test 
    public void etantDonneUneListeDemployeSelectionneDejaDansLaTacheCeuxCiNeSontPasAssignerEnDouble(){
	List<String> listeEmploye = creerDeuxEmailEmploye();
	tache.ajouterEmployesACetteTache(listeEmploye);
	
	tache.ajouterEmployesACetteTache(listeEmploye);
	
	assertEquals(2, tache.getListeRessourceHumainesAssignees().size());
    }

    private List<String> creerDeuxEmailEmploye() {
	List<String> listeEmail = new ArrayList<String>();
	listeEmail.add(EMPLOYE1);
	listeEmail.add(EMPLOYE2);
	return listeEmail;
    }
    
}
