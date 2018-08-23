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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {
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
	
	@AfterMethod
	public void quit(){
		if(driver!=null)
			driver.quit();
	}
	
	
	@Test(dataProvider="getData")
	public void doLogin(String browser,String username,String password){
		
		
		String actualResult="";
		String expectedResult="";
		
		
		
		if(browser.equals("Mozilla")){
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "F:\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://localhost:8080/leadapp/");
		driver.findElement(By.name("email")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
		
		String title=driver.getTitle();
		
		
		if(title.equals("Lead Listing"))
			actualResult="LoginSuccess";
		else
			actualResult="LoginFailure";
		// expectedResult
		 
		 
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from user where USR_EMAIL=?");
			pstmt.setString(1, username);
			ResultSet rs=pstmt.executeQuery();
			
			if(!rs.next())
				expectedResult="LoginFailure";
			else{// something is present
				String pwdDB = rs.getString("USR_PASSWORD");
				System.out.println("Password from DB-> "+ pwdDB);
				if(pwdDB.equals(password))
					expectedResult="LoginSuccess";
				else
					expectedResult="LoginFailure";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(expectedResult, actualResult);
		
	}
	
	@DataProvider
	public Object[][] getData(){
		Object[][] data = new Object[3][3];
		//1st row
		data[0][0]="Mozilla";
		data[0][1]="user@javachap.com";
		data[0][2]="javachap";
		
		//2nd row
		data[1][0]="Chrome";
		data[1][1]="user@javachap.com";
		data[1][2]="javachap";
		
		//3rd row
		data[2][0]="Chrome";
		data[2][1]="user@javachap.com";
		data[2][2]="xxxx";
		
		return data;
	}
}
