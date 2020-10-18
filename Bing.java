import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.Select;

import io.flood.selenium.FloodSump;

import java.util.List;
import java.util.Random;

public class Bing {
  public static void main(String[] args) throws Exception {
    int iterations = 0;
    By by;
    /* Create a new instance of the html unit driver
       Notice that the remainder of the code relies on the interface,
       not the implementation. */
    WebDriver driver = new RemoteWebDriver(new URL("http://" + System.getenv("WEBDRIVER_HOST") + ":" + System.getenv("WEBDRIVER_PORT") + "/wd/hub"), DesiredCapabilities.chrome());
    JavascriptExecutor js = (JavascriptExecutor)driver;

    /* Create a new instance of the Flood IO agent */
    FloodSump flood = new FloodSump();

    /* Inform Flood IO the test has started */
    flood.started();

    /* It's up to you to control test duration / iterations programatically. */
    while( iterations < 50 ) {
      try {

        

        driver.get("https://www.bing.com/?cc=de/");
        /* Log a passed transaction in Flood IO */
        flood.passed_transaction(driver, "Website front page loaded successfully.", 200, 200.0);
        
           
        typeTextIfVisibleWeb = VisibleElementsOperations.typeTextIfVisibleWeb("Test","");
        by = By.cssSelector("#sb_form_q");
        typeTextIfVisibleWeb = (VisibleElementsOperations.TypeTextIfVisibleWeb)((ReportingDriver)driver).addons().execute(typeTextIfVisibleWeb, by, -1);
        flood.passed_transaction(driver,"");

    
        by = By.xpath("//label/*");
        driver.findElement(by).click();
        flood.passed_transaction(driver,"");

    
        by = By.xpath("//a/strong[. = 'Test']");
        driver.findElement(by).click();
        flood.passed_transaction(driver, "");

        iterations++;

        /* Good idea to introduce some form of pacing / think time into your scripts */
        Thread.sleep(4000);

      } catch (WebDriverException e) {
        String[] lines = e.getMessage().split("\\r?\\n");
        System.err.println("Webdriver exception: " + lines[0]);
        flood.failed_transaction(driver);
      } catch(InterruptedException e) {
        Thread.currentThread().interrupt();
        String[] lines = e.getMessage().split("\\r?\\n");
        System.err.println("Browser terminated early: " + lines[0]);
      } catch(Exception e) {
        String[] lines = e.getMessage().split("\\r?\\n");
        System.err.println("Other exception: " + lines[0]);
      } finally {
        iterations++;
      }
    }

    driver.quit();

    /* Inform Flood IO the test has finished */
    flood.finished();
  }
}
