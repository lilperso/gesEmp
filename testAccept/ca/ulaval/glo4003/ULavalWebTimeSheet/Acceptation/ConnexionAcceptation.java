package ca.ulaval.glo4003.ULavalWebTimeSheet.Acceptation;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.AdresseCourrielInexistanteException;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions.MauvaisMotDePasseException;

public class ConnexionAcceptation extends CasSelenium
{
    private static final String MOT_DE_PASSE_ENTREPRISE = "passEntreprise";
    private static final String MOT_DE_PASSE_EMPLOYE = "passEmploye";
    private static final String MOT_DE_PASSE_GESTIONNAIRE = "passGestionnaire";
    private static final String COURRIEL_VALIDE_GESTIONNAIRE = "sambegin@employe.com";
    private static final String COURRIEL_VALIDE_ENTREPRISE = "christMartin@employe.com";
    private static final String COURRIEL_VALIDE_EMPLOYE = "philkelly@employe.com";
    private static final String MOT_DE_PASSE_INVALIDE = "invalide";
    private static final String COURRIEL_INVALIDE = "sam@employe.com";
    private static final String BONJOUR = "Bonjour";

    private static final int ATTENDRE_TRENTE = 30;

    private static final String MAUVAIS_MOT_DE_PASSE = "Votre mot de passe est incorrect";
    private static final String ADRESSE_COURRIEL_INEXISTANTE = "Votre adresse courriel est incorrecte";

    @Test(expected = MauvaisMotDePasseException.class)
    public void testPasseInvalideException() throws Throwable {
	saisirDonneesFormulaireConnexion(COURRIEL_VALIDE_GESTIONNAIRE, MOT_DE_PASSE_INVALIDE);
	WebElement element = rechercheBalise("panel-body");
	assertTrue(afficheMessage(element, MAUVAIS_MOT_DE_PASSE));
    }

    @Test(expected = AdresseCourrielInexistanteException.class)
    public void testAdresseInvalideException() throws Throwable {
	saisirDonneesFormulaireConnexion(COURRIEL_INVALIDE, MOT_DE_PASSE_GESTIONNAIRE);
	WebElement element = rechercheBalise("panel-body");
	assertTrue(afficheMessage(element, ADRESSE_COURRIEL_INEXISTANTE));
    }

    @Test
    public void testConnexionGestionnaireAfficheMessageBienvenue() throws Throwable {
	saisirDonneesFormulaireConnexion(COURRIEL_VALIDE_GESTIONNAIRE, MOT_DE_PASSE_GESTIONNAIRE);
	WebElement element = rechercheBalise("navbar-brand");
	assertTrue(afficheMessage(element, BONJOUR + " " + COURRIEL_VALIDE_GESTIONNAIRE));
    }

    @Test
    public void testConnexionEmployeAfficheMessageBienvenue() throws Throwable {
	saisirDonneesFormulaireConnexion(COURRIEL_VALIDE_EMPLOYE, MOT_DE_PASSE_EMPLOYE);
	WebElement element = rechercheBalise("navbar-brand");
	assertTrue(afficheMessage(element, BONJOUR + " " + COURRIEL_VALIDE_EMPLOYE));
    }

    @Test
    public void testConnexionEntrepriseAfficheMessageBienvenue() throws Throwable {
	saisirDonneesFormulaireConnexion(COURRIEL_VALIDE_ENTREPRISE, MOT_DE_PASSE_ENTREPRISE);
	WebElement element = rechercheBalise("navbar-brand");
	assertTrue(afficheMessage(element, BONJOUR + " " + COURRIEL_VALIDE_ENTREPRISE));
    }

    private void saisirDonneesFormulaireConnexion(String courriel, String motDePasse) {
	driver.get(baseUrl);
	driver.findElement(By.id("adresseCourriel")).clear();
	driver.findElement(By.id("adresseCourriel")).sendKeys(courriel);
	driver.findElement(By.id("motDePasse")).clear();
	driver.findElement(By.id("motDePasse")).sendKeys(motDePasse);
	driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    private WebElement rechercheBalise(String nomBalise) {
	driver.manage().timeouts().implicitlyWait(ATTENDRE_TRENTE, TimeUnit.SECONDS);
	WebElement element = driver.findElement(By.className(nomBalise));
	return element;
    }

    private boolean afficheMessage(WebElement element, String message) {
	return element.getText().contains(message);
    }

}
