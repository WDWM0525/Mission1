package BookMark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookMarkService {

	/** 
	 *  bookMarkList
	 *  : BOOK_MARK SELECT 
	 */
	public static List<BookMark> bookMarkList() {
		
		List<BookMark> bookMarkList = new ArrayList<>();
		
		String url        = "jdbc:mariadb://43.203.44.208:3306/wifi";
        String dbUserId   = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection connection               = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs                        = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql =  "SELECT t1.BOOK_MARK_ID \r\n"
            		   + "	    , t1.GROUP_ID \r\n"
            		   + "	    , t1.X_SWIFI_MGR_NO \r\n"
            		   + "	    , t1.REGIST_DT \r\n"
            		   + "	    , t2.GROUP_NM \r\n"
            		   + "	    , t3.X_SWIFI_MAIN_NM \r\n"
            		   + "   FROM BOOK_MARK t1\r\n"
            		   + "   JOIN BOOK_MARK_GROUP t2\r\n"
            		   + "     ON t1.GROUP_ID = t2.GROUP_ID\r\n"
            		   + "   JOIN WIFI_INFO t3\r\n"
            		   + "     ON t1.X_SWIFI_MGR_NO = t3.X_SWIFI_MGR_NO \r\n"
            		   + "  ORDER BY BOOK_MARK_ID ;";
            
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	String book_mark_id    = rs.getString("BOOK_MARK_ID");    // 북마크키
            	String group_id        = rs.getString("GROUP_ID");        // 북마크그룹키 
                String x_swifi_mgr_no  = rs.getString("X_SWIFI_MGR_NO");  // 관리번호  
                String regist_dt       = rs.getString("REGIST_DT");       // 등록일자
                String group_nm        = rs.getString("GROUP_NM");        // 북마크그룹이름
                String x_swifi_main_nm = rs.getString("X_SWIFI_MAIN_NM"); // 와이파이명
                
                BookMark bookMark = new BookMark();
                bookMark.setBook_mark_id(book_mark_id);
                bookMark.setGroup_id(group_id);
                bookMark.setX_swifi_mgr_no(x_swifi_mgr_no);
                bookMark.setRegist_dt(regist_dt);
                bookMark.setGroup_nm(group_nm);
                bookMark.setX_swifi_main_nm(x_swifi_main_nm);
                
                bookMarkList.add(bookMark);
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

        return bookMarkList;
	}
	
	/** 
	 *  bookMarkList
	 *  : BOOK_MARK SELECT 
	 */
	public static List<BookMark> bookMarkList(String v_group_id, String v_book_mark_id) {
		
		List<BookMark> bookMarkList = new ArrayList<>();
		
		String url        = "jdbc:mariadb://43.203.44.208:3306/wifi";
        String dbUserId   = "wifi_user";
        String dbPassword = "zerobase";
        
        Connection connection               = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs                        = null;
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql =  "SELECT BOOK_MARK_ID\r\n"
            		   + "	    , GROUP_ID\r\n"
            		   + "	    , X_SWIFI_MGR_NO\r\n"
            		   + "	    , REGIST_DT\r\n"
            		   + "   FROM BOOK_MARK\r\n"
            		   + "  WHERE GROUP_ID     = ?\r\n"
            		   + "	  AND BOOK_MARK_ID = ? ; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  v_group_id);
            preparedStatement.setString(2,  v_book_mark_id);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {     
            	String book_mark_id   = rs.getString("BOOK_MARK_ID");   // 북마크키
            	String group_id       = rs.getString("GROUP_ID");       // 북마크그룹키 
                String x_swifi_mgr_no = rs.getString("X_SWIFI_MGR_NO"); // 관리번호  
                String regist_dt      = rs.getString("REGIST_DT");      // 등록일자  
                
                BookMark bookMark = new BookMark();
                bookMark.setBook_mark_id(book_mark_id);
                bookMark.setGroup_id(group_id);
                bookMark.setX_swifi_mgr_no(x_swifi_mgr_no);
                bookMark.setRegist_dt(regist_dt);
                
                bookMarkList.add(bookMark);
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

        return bookMarkList;
	}
	
	/** 
	 *  insertBookMark
	 *  : BOOK_MARK INSERT
	 */
	public static int insertBookMark(BookMark b) {
		
		int affected = 0;
		
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

            String sql =  "INSERT INTO BOOK_MARK (\r\n"
        		       + "	      BOOK_MARK_ID\r\n"
        		       + "	    , GROUP_ID\r\n"
        		       + "	    , X_SWIFI_MGR_NO\r\n"
        		       + "	    , REGIST_DT\r\n"
        		       + ")\r\n"
        		       + "SELECT NVL(MAX(BOOK_MARK_ID), 0) + 1 AS BOOK_MARK_ID\r\n"
        		       + "     , ?\r\n"
        		       + "	   , ?\r\n"
        		       + "	   , NOW()\r\n"
        		       + "  FROM BOOK_MARK\r\n"
        		       + ";";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  b.getGroup_id());
            preparedStatement.setString(2,  b.getX_swifi_mgr_no());
   
            affected = preparedStatement.executeUpdate();

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
        
        return affected;
        
    }

	/** 
	 *  deleteBookMark
	 *  : BOOK_MARK Delete
	 */
	public static int deleteBookMark(BookMark b) {
		
		int affected = 0;
		
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

            String sql = "DELETE FROM BOOK_MARK WHERE BOOK_MARK_ID = ? ;" ;

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  b.getBook_mark_id());
   
            affected = preparedStatement.executeUpdate();

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
        
        return affected;
        
    }
	
}
