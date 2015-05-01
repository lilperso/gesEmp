package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UtilisateurTest
{
    private Utilisateur user ;
    private Utilisateur userB;
    
    @Before
    public void initialiserTest(){
	user = new Employe();
	userB = new Employe();
    }
    
    @Test
    public void equalsRetrouneFalseQuandNull(){
	assertFalse(user.equals(null));
    }
    
    @Test
    public void equalsRetrouneFalseQuandPasInstanceUtilisateur(){
	assertFalse(user.equals(""));
    }
    
    @Test
    public void equalsRetrouneFalseQuandAdresseCourrielNull(){
	userB.setAdresseCourriel("");
	assertFalse(user.equals(userB));
    }
    
    @Test
    public void equalsRetrouneFalseQuandMotDePasseNull(){
	userB.setMotDePasse("");
 	assertFalse(user.equals(userB));
     }
    
    @Test
    public void equalsRetrouneFalseQuandMotDePassePasEgale(){
	user.setMotDePasse("");
 	assertFalse(user.equals(userB));
     }
    
    @Test
    public void equalsRetrouneFalseQuandRolesNull(){
	userB.setRole("");
 	assertFalse(user.equals(userB));
     }
    
    @Test
    public void equalsRetrouneFalseQuandRolesPasEgale(){
	user.setRole("");
 	assertFalse(user.equals(userB));
     }
    
    @Test
    public void equalsRetrounetrueQuandToutEstNull(){
 	assertTrue(user.equals(userB));
     }
    
    
}
