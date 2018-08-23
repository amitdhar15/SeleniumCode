import java.sql.*;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class DBTest {
	  String url = "jdbc:mysql://localhost:3306/";
	  String driver = "com.mysql.jdbc.Driver";
	  String dbName = "testing";
	  
	  String userName = "root"; 
	  String password = "root"; 
	
	 Connection conn = null;
	 PreparedStatement pstmt=null;
	 ResultSet rs = null;

	@BeforeTest
	public void connect(){
		try {
			Class.forName(driver).newInstance();
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
	public void testDB(){
		try{
			pstmt = conn.prepareStatement("select * from EMPLOYEE where NAME = ?");
		  pstmt.setString(1, "ASHISH");
		  //pstmt.setString(2, "F");
		  rs = pstmt.executeQuery();
		  
		  while(rs.next()){
				System.out.println(rs.getString(1) + "-- "+rs.getString(2)+" -- "+rs.getString(3));  
		  }
		}catch (Exception e) {
			System.out.println("Exception in firing query");
			e.printStackTrace();
			Assert.fail("Exception  in firing query "+ e.getMessage());
			
		}
	}
}
