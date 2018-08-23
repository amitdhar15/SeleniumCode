import java.sql.*;

//mysqladmin.exe -u root password NEWPASS


public class DBConnection {

	public static void main(String[] args) {
		  Connection conn = null;
		  
		  String url = "jdbc:mysql://localhost:3306/";
		  String driver = "com.mysql.jdbc.Driver";
		  String dbName = "testing";
		  String username="root";
		  String password="root";
		  
		  // connect to DB
		  // fire queries
		  // disconnect
		  
		  try{
			// connect to DB
			 Class.forName(driver).newInstance();// create object of Driver 
			 conn = DriverManager.getConnection(url+dbName,username,password);
			 System.out.println(conn.isClosed());
			 
			// fire queries
			 
			 /***********************Statement interface*****************************/
			 Statement st=conn.createStatement();
			 ResultSet rs = st.executeQuery("select * from EMPLOYEE");
			 
			 while(rs.next()){
				 System.out.println(rs.getString(1)+" --- "+rs.getString(2)+" ---- "+rs.getInt("Age"));
			 }
			//st.executeUpdate("INSERT INTO EMPLOYEE VALUES('JOHN','USA',44)");// insert,update,delete
			 
			 System.out.println("---------------------------------------------------");
			
		
			 /***********************PreparedStatement interface*****************************/
			 PreparedStatement pstmt = conn.prepareStatement("select * from EMPLOYEE where PLACE=? and AGE=?");
			 pstmt.setString(1, "JAPAN");
			 pstmt.setInt(2, 40);
			  rs = pstmt.executeQuery();
			  
			  while(rs.next()){
					 System.out.println(rs.getString(1)+" --- "+rs.getString(2)+" ---- "+rs.getInt("Age"));
			  }
			  
			 // pstmt.executeUpdate() - TRY -  insert,update,delete
			  
			/*********STORED PROCEDURES**************CallableStatement interface*****************************/
			  CallableStatement cstmt= conn.prepareCall("{call getTestData(?,?,?,?)}");
			  cstmt.registerOutParameter(1, java.sql.Types.DECIMAL);
			  cstmt.setString(2, "TestName");
			  cstmt.setString(3, "xyz");
			  cstmt.setString(4, "abc");
			  
			  cstmt.executeUpdate();
			  double d = cstmt.getDouble(1);
			  
 
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  try {
				if((conn!=null) && (!conn.isClosed())){
					  conn.close();
				  }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  }

	}

}
