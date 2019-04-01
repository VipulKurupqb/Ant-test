package utility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtility {
	
	 private final String url = "jdbc:postgresql://13.232.139.147:31432/pgdbuser";
	 private final String urlCompany = "jdbc:postgresql://13.232.139.147:31432/pgdbcompany";
	 private final String user = "pguser";
	 private final String password = "pguser";
	 public static String primarySubString;
	 public static String secondarySubString;
	 public static String primaryUserToken;
	 public static String secondaryUserToken;
	 public static String companyToken;
	 public static String primaryCompanyUserToken;
	 public static String secondaryCompanyUserToken;
	 /*
	   * Establishing connection to user database
	   */
	   public Connection connect() {
	          Connection conn = null;
	          try {
	              conn = DriverManager.getConnection(url, user, password);
	              System.out.println("Connected to the PostgreSQL server successfully.");
	          } catch (SQLException e) {
	              System.out.println(e.getMessage());
	          }
	   
	          return conn;
	      }
	   
		  /*
		   * Database result for getting tokens from the user table
		   */
	  
		   public String queryResult(String username) {
			  	String SQL = "select token from tokens where user_id = (select user_id from regularuser where username = ?) AND tokens.type='SIGN_UP'";
		        String id="";
		        try {Connection conn = connect();
		                PreparedStatement stmt = conn.prepareStatement(SQL);
		                stmt.setString(1, username);
		                ResultSet queryOutput  = stmt.executeQuery();
		                queryOutput.next();
		                id=queryOutput.getString("token");
		                System.out.println("The query output is:"+id);
		        }
		        catch (SQLException ex) {
		            System.out.println(ex.getMessage());
		        }
		 
		        return id;
		  }
		   
		   
		   public String queryResultCompanyUser(String username) {
			  	String SQL = "select token from tokens where user_id = (select user_id from regularuser where username = ?) AND tokens.type='COMPANY_REGISTER'";
		        String id="";
		        try {Connection conn = connect();
		                PreparedStatement stmt = conn.prepareStatement(SQL);
		                stmt.setString(1, username);
		                ResultSet queryOutput  = stmt.executeQuery();
		                queryOutput.next();
		                id=queryOutput.getString("token");
		                System.out.println("The query output is:"+id);
		        }
		        catch (SQLException ex) {
		            System.out.println(ex.getMessage());
		        }
		 
		        return id;
		  }
		   
		   public String queryResultCompanyUser2(String username) {
			  	String SQL = "select token from tokens where user_id=(select user_id from companyuser where email=?)";
		        String id="";
		        try {Connection conn = connect();
		                PreparedStatement stmt = conn.prepareStatement(SQL);
		                stmt.setString(1, username);
		                ResultSet queryOutput  = stmt.executeQuery();
		                queryOutput.next();
		                id=queryOutput.getString("token");
		                System.out.println("The query output is:"+id);
		        }
		        catch (SQLException ex) {
		            System.out.println(ex.getMessage());
		        }
		 
		        return id;
		  }
	 
	  
		  /*
		   * URL encoding
		   */
		     public static String encodeMethod(String url)  
		      {  
		                try {  
		                     String encodeURL=URLEncoder.encode( url, "UTF-8" );  
		                     return encodeURL;  
		                } catch (UnsupportedEncodingException e) {  
		                     return "Issue while encoding" +e.getMessage();  
		                }  
		      }
		     
		 /*
		  * Get method for primary user token
		  */
		    public static String userRegistrationPrimaryToken(String primaryEmail) {
		    	DBUtility po1=new DBUtility();
				po1.connect();
				Log.addMessage("connection successful for primary user token");
				primaryUserToken=po1.queryResult(primaryEmail);
				Log.addMessage("The Primary token is: "+primaryUserToken);
				return primaryUserToken;
		    }
		    
		 /*
		  * Get method for a primary user token for a company
		  */
		    public static String companyPrimaryToken(String primaryEmail) {
		    	DBUtility po1=new DBUtility();
				po1.connect();
				Log.addMessage("connection successful for company primary user token");
				primaryCompanyUserToken=po1.queryResultCompanyUser(primaryEmail);
				Log.addMessage("The Primary company user token is: "+primaryCompanyUserToken);
				return primaryCompanyUserToken;
		    }  
		    
		 /*
		  * Get method for a primary user token for a company admin who is not registered into application.
		  */
		    public static String companyPrimaryToken2(String primaryEmail) {
		    	DBUtility po1=new DBUtility();
				//po1.connect();
				Log.addMessage("connection successful for company primary user token");
				String primaryCompanyUserToken=po1.queryResultCompanyUser2(primaryEmail);
				Log.addMessage("The Primary company user token is: "+primaryCompanyUserToken);
				return primaryCompanyUserToken;
		    } 
		    
		 /*
		  * Get method for a secondary user token for a company
		  */
		    public static String companySecondaryToken(String secondaryEmail) {
		    	DBUtility po1=new DBUtility();
				po1.connect();
				Log.addMessage("connection successful for company secondary user token");
				secondaryCompanyUserToken=po1.queryResultCompanyUser(secondaryEmail);
				Log.addMessage("The Secondary company user token is: "+secondaryCompanyUserToken);
				return secondaryCompanyUserToken;
		    }
		    
		 /*
		  * Get method for a secondary user token for a company admin who is not registered into application.
		  */
		    public static String companySecondaryToken2(String secondaryEmail) {
		    	DBUtility po1=new DBUtility();
				//po1.connect();
				Log.addMessage("connection successful for company secondary user token");
				String secondaryCompanyUserToken=po1.queryResultCompanyUser2(secondaryEmail);
				Log.addMessage("The Secondary company user token is: "+secondaryCompanyUserToken);
				return secondaryCompanyUserToken;
		    }
		  
		 /*
		  * Primary User registration link formation method
		  */
		    public static String userRegistrationPrimaryLink(String primaryEmail) {
				String primaryUserToken=DBUtility.userRegistrationPrimaryToken(primaryEmail);
				String urlEncode=DBUtility.encodeMethod(primaryEmail);
				Log.addMessage("The encoded email is: "+urlEncode);
				primarySubString=urlEncode+"&token="+primaryUserToken;
				Log.addMessage("The Primary substring is:"+primarySubString);
				return primarySubString;
		    }

		 /*
		  * Get method for secondary user token
		  */
		    public static String userRegistrationSecondaryToken(String secondaryEmail) {
		    	DBUtility po1=new DBUtility();
				po1.connect();
				Log.addMessage("connection successful for secondary user token");
				secondaryUserToken=po1.queryResult(secondaryEmail);
				Log.addMessage("The Secondary token is: "+secondaryUserToken);
				return secondaryUserToken;
		    }
		    
		 /*
		  * Secondary User registration link formation method
		  */
		    public static String userRegistrationSecondaryLink(String secondaryEmail) {
				String secondaryUserToken=DBUtility.userRegistrationSecondaryToken(secondaryEmail);
				String urlEncode=DBUtility.encodeMethod(secondaryEmail);
				Log.addMessage("The encoded email is: "+urlEncode);
				secondarySubString=urlEncode+"&token="+secondaryUserToken;
				Log.addMessage("The Secondary substring is:"+secondarySubString);
				return secondarySubString;
		    }
			  
	     /*
		   * Database connection to company database
		   */
	     
	     public Connection connectCompany() {
	          Connection conn = null;
	          try {
	              conn = DriverManager.getConnection(urlCompany, user, password);
	              System.out.println("Connected to the PostgreSQL server successfully.");
	          } catch (SQLException e) {
	              System.out.println(e.getMessage());
	          }
	   
	          return conn;
	      }
			 
	     /*
	      * Database result for getting company token
	      */
	     
	   	  public String queryResultCompany(String username) {
	   		  	String SQL = "select token.value from token where token.company_id=(select company.id from company where primary_contact_email= ?)";
	   		  	String id = "";
	   	        try {Connection conn = connectCompany();
	   	                PreparedStatement stmt = conn.prepareStatement(SQL);
	   	                stmt.setString(1, username);
	   	                ResultSet queryOutput  = stmt.executeQuery();
	   	                queryOutput.next();
	   	                id=queryOutput.getString("value");
	   	                System.out.println("The query output is:"+id);
	   	        }
	   	        catch (SQLException ex) {
	   	            System.out.println(ex.getMessage());
	   	        }
	   	 
	   			return id;
	   	  }
		  
			  
		 /*
		  * Get method for company token
		  */
		    public static String getCompanyToken(String primaryEmail) {
		    	DBUtility po1=new DBUtility();
				//po1.connectCompany();
				Log.addMessage("connection successful for company token");
				companyToken=po1.queryResultCompany(primaryEmail);
				Log.addMessage("The Company token is: "+companyToken);
				return companyToken;
		    }
		    
		    
		    /*
		     * Database method for deleting users from company table
		     */
		    
		  	  public void userDeleteCompanyDBQuery(String primaryUserName, String secondaryUserName) throws SQLException {
		  		  	String SQL = "DELETE FROM company "
		  		  			+ "WHERE primary_contact_email = ? OR secondary_contact_email =?";
		  		 	String SQL2 = "DELETE FROM company_user WHERE user_id IN "
		  		  			+ "(SELECT user_id FROM dblink('dbname=pgdbuser','select user_id, username, email from regularuser') "
		  		  			+ "AS  tb2(user_id int, username text, email text) "
		  		  			+ "WHERE email = ? OR username = ?)";
		  		  
		  	        try {	Connection conn = connectCompany();
				  	        PreparedStatement stmt = conn.prepareStatement(SQL);
			                stmt.setString(1, primaryUserName);
			                stmt.setString(2, secondaryUserName);
			                stmt.executeUpdate();
		  	                PreparedStatement stmt2 = conn.prepareStatement(SQL2);
		  	                stmt2.setString(1, primaryUserName);
		  	                stmt2.setString(2, primaryUserName);
		  	                stmt2.executeUpdate();
		  	        }
		  	        catch (SQLException ex) {
		  	            System.out.println(ex.getMessage());
		  	        }
		  	  }
		  	  
		  	  	/*
			     * Database method for deleting users from user table
			     */
		  	  
		  	  public void userDeleteUserDBQuery(String primaryUserName, String secondaryUserName) throws SQLException {
		  		  	String SQL = "DELETE from companyuser where email = ?";
		  		 	String SQL3 = "DELETE FROM public.appuser where id IN "
		  		  			+ "(select user_id from regularuser WHERE email = ? OR username = ?)";
		  		  
		  	        try {	Connection conn = connect();
		  	                PreparedStatement stmt = conn.prepareStatement(SQL);
		  	                stmt.setString(1, primaryUserName);
		  	                stmt.executeUpdate();
		  	                PreparedStatement stmt3 = conn.prepareStatement(SQL3);	  	              
		  	                stmt3.setString(1, primaryUserName);
		  	                stmt3.setString(2, primaryUserName);
		  	                stmt3.executeUpdate();
		  	        }
		  	        catch (SQLException ex) {
		  	            System.out.println(ex.getMessage());
		  	        }
		  	  }
		  	  
		  	/*
		  	 * Database method for deleting normal users from user table
		  	 */

		  	public void userDeleteDB(String userName) throws SQLException {
		  		String SQL = "DELETE FROM public.appuser where id IN "
		  				+ "(select user_id from regularuser WHERE email = ? OR username = ?)";

		  		try {	Connection conn = connect();
		  		PreparedStatement stmt = conn.prepareStatement(SQL);	
		  		stmt.setString(1, userName);
		  		stmt.setString(2, userName);
		  		Log.addMessage(stmt.toString());
		  		int result =  stmt.executeUpdate();
		  		Log.addMessage("Rows affected: " + result);
		  		}
		  		catch (SQLException ex) {
		  			System.out.println(ex.getMessage());
		  		}
		  	}
		  	
		  	/*
		  	 * Database method for deleting social media users from user table
		  	 */

		  	public void socialMediaUserDeleteDB(String userName) throws SQLException {
		  		String SQL = "delete from socialuser where email = ?;";

		  		try {	Connection conn = connect();
		  		PreparedStatement stmt = conn.prepareStatement(SQL);	
		  		stmt.setString(1, userName);
		  		Log.addMessage(stmt.toString());
		  		int result =  stmt.executeUpdate();
		  		Log.addMessage("Rows affected: " + result);
		  		}
		  		catch (SQLException ex) {
		  			System.out.println(ex.getMessage());
		  		}
		  	}

}
