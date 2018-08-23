package testcases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class TestB {

	@Test
	public void testB() throws InterruptedException, MalformedURLException{
		System.out.println("Starting test B");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		cap.setJavascriptEnabled(true);
		cap.setPlatform(Platform.WINDOWS);		
		
		WebDriver driver = new RemoteWebDriver((new URL("http://localhost:4444/wd/hub")),cap);
		
		
		Thread.sleep(5000);
		System.out.println("Ending Test B");
		driver.quit();
	}
}
