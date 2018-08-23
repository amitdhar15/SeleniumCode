package testcases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestA {
	WebDriver driver;
	@BeforeTest
	public void before() throws MalformedURLException{
		DesiredCapabilities cap = DesiredCapabilities.firefox();
		cap.setBrowserName("firefox");
		cap.setJavascriptEnabled(true);
		cap.setPlatform(Platform.WINDOWS);
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
	}
	
	@Test(priority=1)
	public void testA() throws InterruptedException, MalformedURLException{
		System.out.println("Starting Test A");
		driver.get("http://gmail.com");
		Thread.sleep(10000);
		System.out.println("Ending Test A");
	}
	
	@Test(priority=2)
	public void testAA() throws InterruptedException{
		Thread.sleep(5000);
		driver.findElement(By.id("Email")).sendKeys("Hello");

	}
	
	@Test(priority=3)
	public void testAAA() throws InterruptedException{
		Thread.sleep(5000);
		driver.findElement(By.id("Email")).sendKeys(" World");

	}
	@AfterTest
	public void quit() throws InterruptedException{
		Thread.sleep(5000);
		if(driver!=null)
			driver.quit();
	}
}
