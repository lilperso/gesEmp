package ca.ulaval.glo4003.ULavalWebTimeSheet.Acceptation;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class CasSelenium extends TestCase
{
    protected static WebDriver driver;
    protected static String baseUrl = "http://localhost:8080";

    @Override
    public void setUp() throws Exception {
	super.setUp();
	driver = new FirefoxDriver();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Override
    public void tearDown() throws Exception {
	super.tearDown();
	driver.quit();
    }

}
