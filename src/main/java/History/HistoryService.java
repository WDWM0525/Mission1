package History;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
	/** 
	 *  siteHistoryList
	 *  : SITE_HISTORY SELECT 
	 */
	public static List<History> siteHistoryList() {
		
		List<History> siteHistoryList = new ArrayList<>();
		
		String url        = "jdbc:mariadb://43.203.44.208:3306/wifi";
        String dbUserId   = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs          = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql =  "SELECT ID\r\n"
            		   + "	    , LAT\r\n"
            		   + "	    , LNT\r\n"
            		   + "	    , SEARCH_DT\r\n"
            		   + "   FROM SITE_HISTORY\r\n"
            		   + "  ORDER BY ID DESC";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {     
            	String id      	 = rs.getString("ID");		  // 히스토리키 
                String lat       = rs.getString("LAT");       // X좌표      
                String lnt       = rs.getString("LNT");       // Y좌표          
                String search_dt = rs.getString("SEARCH_DT"); // 조회일자
                
                History history = new History();
                history.setId(id);
                history.setLat(lat);
                history.setLnt(lnt);
                history.setSearch_dt(search_dt);
                
                siteHistoryList.add(history);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return siteHistoryList;
	}
	
	/** 
	 *  insertSiteHistory
	 *  : SITE_HISTORY INSERT
	 */
	public static void insertSiteHistory(History h) {
		
		String url        = "jdbc:mariadb://43.203.44.208:3306/wifi";
        String dbUserId   = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection        connection        = null;
        PreparedStatement preparedStatement = null;
        ResultSet         rs                = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            
        }

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "INSERT INTO SITE_HISTORY (\r\n"
            		    + "	      ID\r\n"
            		    + "	    , LAT\r\n"
            		    + "	    , LNT\r\n"
            		    + "	    , SEARCH_DT\r\n"
            		    + ")\r\n"
            		    + "SELECT NVL(MAX(ID), 0) + 1 AS ID\r\n"
            		    + "	    , ?\r\n"
            		    + "	    , ?\r\n"
            		    + "	    , NOW()\r\n"
            		    + "  FROM SITE_HISTORY\r\n"
            		    + ";";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  h.getLat());
            preparedStatement.setString(2,  h.getLnt());
   
            int affected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }

        }
        
    }
	
	/** 
	 *  deleteSiteHistory
	 *  : SITE_HISTORY DELETE
	 */
	public static void deleteSiteHistory(String id) {
		String url        = "jdbc:mariadb://43.203.44.208:3306/wifi";
        String dbUserId   = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection        connection        = null;
        PreparedStatement preparedStatement = null;
        ResultSet         rs                = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            
        }

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql =  "DELETE FROM SITE_HISTORY WHERE ID = ?;";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  id);

            int affected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }

        }
        
    }
}
