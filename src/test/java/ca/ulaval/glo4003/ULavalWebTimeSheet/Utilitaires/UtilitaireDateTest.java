package ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires;

import static org.junit.Assert.*;

import org.junit.Test;
import org.joda.time.DateTime;

public class UtilitaireDateTest
{
    private UtilitaireDate gestionnaireDate = new UtilitaireDate();
    
    @Test
    public void formaterDate_premierJanvier2014_bonFormatRetourne(){
	String chaineAttendue = "2014-01-01";
	DateTime date= new DateTime(chaineAttendue);
	
	String chaineRetour = gestionnaireDate.formaterDate(date);
	
	assertEquals(chaineAttendue,chaineRetour);
    }
    
    @Test
    public void estMemeDate_deuxDatesIdentiques_RetourneTrue(){
	DateTime date1 = new DateTime("2014-01-01");
	DateTime date2 = new DateTime("2014-01-01");
	
	boolean retour = gestionnaireDate.estMemeDate(date1, date2);
	
	assertEquals(true,retour);
    }
    
    @Test
    public void estMemeDate_deuxDatesDifferentes_RetourneFalse(){
	DateTime date1 = new DateTime("2014-01-01");
	DateTime date2 = new DateTime("2014-01-02");
	
	boolean retour = gestionnaireDate.estMemeDate(date1, date2);
	
	assertEquals(false,retour);
    }
}
