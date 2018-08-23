package testcases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestB {
	WebDriver driver;
	@Test
	public void testB() throws InterruptedException, MalformedURLException{
		System.out.println("Starting Test B");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		cap.setJavascriptEnabled(true);
		cap.setPlatform(Platform.WINDOWS);
		 driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
		Thread.sleep(10000);
		System.out.println("Ending Test B");
	}
	
	@AfterTest
	public void quit(){
		if(driver!=null)
			driver.quit();
	}
}
