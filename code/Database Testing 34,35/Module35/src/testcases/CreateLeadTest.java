package testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateLeadTest {
	String url = "jdbc:mysql://localhost:3306/";
	  String dbDriver = "com.mysql.jdbc.Driver";
	  String dbName = "lead";
	  
	  String userName = "root"; 
	  String password = "root"; 
	
	 Connection conn = null;
	 PreparedStatement pstmt=null;
	 ResultSet rs = null;
	 WebDriver driver = null;
	 
	@BeforeTest
	public void connect(){
		try {
			Class.forName(dbDriver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
		} catch (Exception e) {
			System.out.println("Could not establish connection");
			e.printStackTrace();
			//Assert.fail("Could not establish connection");
			throw new SkipException("Could not establish connection");
		}// create object of Driver
		
	}
	
	@AfterTest
	public void disConnect(){
		try {
			if(rs!=null)
				rs.close();
			
			if(pstmt!=null)
				pstmt.close();
			
			
			if((conn!=null) && (!conn.isClosed()))
				  conn.close();
				 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void createLeadTest(){
		String firstName="Aditya";
		String lastName="Singh";
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/leadapp/");
		driver.findElement(By.name("email")).sendKeys("user@javachap.com");
		driver.findElement(By.name("password")).sendKeys("javachap");
		driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id='container']/div/div/div/a")).click();
		
		driver.findElement(By.xpath("//*[@id='Field7']")).sendKeys("Software");
		driver.findElement(By.xpath("//*[@id='Field0']")).sendKeys("Mr.");
		driver.findElement(By.xpath("//*[@id='Field1']")).sendKeys("Wants to buy software");
		driver.findElement(By.xpath("//*[@id='Field4']")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id='Field5']")).sendKeys(lastName);
		driver.findElement(By.xpath("//*[@id='Field6']")).sendKeys("Aditya@gmail.com");
		driver.findElement(By.xpath("//*[@id='Field8']")).sendKeys("33314433333");
		driver.findElement(By.xpath("//*[@id='Field9']")).sendKeys("100444400");
		driver.findElement(By.xpath("//*[@id='saveForm']")).click();
		// validate if lead is generated from fe
		// connect to db and validate lead
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from lead order by LD_ID desc");
			ResultSet rs =pstmt.executeQuery();
			if(rs.next()){
				String dbFirstName=rs.getString("LD_FIRST_NAME");
				String dbLastName=rs.getString("LD_LAST_NAME");
				
				Assert.assertEquals(firstName, dbFirstName);
				Assert.assertEquals(lastName, dbLastName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
