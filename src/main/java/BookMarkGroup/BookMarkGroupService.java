package BookMarkGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookMarkGroupService {

	/** 
	 *  bookMarkGroupList
	 *  : BOOK_MARK_GROUP SELECT 
	 */
	public List<BookMarkGroup> bookMarkGroupList() {
		
		List<BookMarkGroup> bookMarkGroupList = new ArrayList<>();
		
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

            String sql =  "SELECT GROUP_ID\r\n"
            		   + "	    , GROUP_NM\r\n"
            		   + "	    , ORDER_NUM\r\n"
            		   + "	    , REGIST_DT\r\n"
            		   + "	    , NVL(UPDATE_DT, '') AS UPDATE_DT\r\n"
            		   + "   FROM BOOK_MARK_GROUP\r\n"
            		   + "  ORDER BY GROUP_ID ;";
            preparedStatement = connection.prepareStatement(sql);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {     
            	String group_id  = rs.getString("GROUP_ID");  // 북마크그룹키
                String group_nm  = rs.getString("GROUP_NM");  // 북마크그룹이름  
                String order_num = rs.getString("ORDER_NUM"); // 순서
                String regist_dt = rs.getString("REGIST_DT"); // 등록일자
                String update_dt = rs.getString("UPDATE_DT"); // 수정일자
                
                BookMarkGroup group = new BookMarkGroup();
                group.setGroup_id(group_id);
                group.setGroup_nm(group_nm);
                group.setOrder_num(order_num);
                group.setRegist_dt(regist_dt);
                group.setUpdate_dt(update_dt);
                
                bookMarkGroupList.add(group);
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

        return bookMarkGroupList;
	}
	
	/** 
	 *  bookMarkGroupList
	 *  : BOOK_MARK_GROUP SELECT 
	 */
	public List<BookMarkGroup> bookMarkGroupList(String v_group_id) {
		
		List<BookMarkGroup> bookMarkGroupList = new ArrayList<>();
		
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

            String sql =  "SELECT GROUP_ID\r\n"
            		   + "	    , GROUP_NM\r\n"
            		   + "	    , ORDER_NUM\r\n"
            		   + "	    , REGIST_DT\r\n"
            		   + "	    , NVL(UPDATE_DT, '') AS UPDATE_DT\r\n"
            		   + "   FROM BOOK_MARK_GROUP\r\n"
            		   + "  WHERE GROUP_ID = ?; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, v_group_id);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {     
            	String group_id  = rs.getString("GROUP_ID");  // 북마크그룹키
                String group_nm  = rs.getString("GROUP_NM");  // 북마크그룹이름  
                String order_num = rs.getString("ORDER_NUM"); // 순서
                String regist_dt = rs.getString("REGIST_DT"); // 등록일자
                String update_dt = rs.getString("UPDATE_DT"); // 수정일자
                
                BookMarkGroup group = new BookMarkGroup();
                group.setGroup_id(group_id);
                group.setGroup_nm(group_nm);
                group.setOrder_num(order_num);
                group.setRegist_dt(regist_dt);
                group.setUpdate_dt(update_dt);
                
                bookMarkGroupList.add(group);
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

        return bookMarkGroupList;
	}
	
	/** 
	 *  insertBookMarkGroup
	 *  : BOOK_MARK_GROUP INSERT
	 */
	public int insertBookMarkGroup(BookMarkGroup g) {
		
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

            String sql =  "INSERT INTO BOOK_MARK_GROUP (\r\n"
        		       + "	      GROUP_ID\r\n"
        		       + "	    , GROUP_NM\r\n"
        		       + "	    , ORDER_NUM\r\n"
        		       + "	    , REGIST_DT\r\n"
        		       + ")\r\n"
        		       + "SELECT NVL(MAX(GROUP_ID), 0) + 1 AS GROUP_ID\r\n"
        		       + "	    , ?\r\n"
        		       + "	    , ?\r\n"
        		       + "	    , NOW()\r\n"
        		       + "  FROM BOOK_MARK_GROUP\r\n"
        		       + ";";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, g.getGroup_nm());
            preparedStatement.setString(2, g.getOrder_num());
   
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
	 *  updateBookMarkGroup
	 *  : BOOK_MARK_GROUP UPDATE
	 */
	public int updateBookMarkGroup(BookMarkGroup g) {
		
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

            String sql = "UPDATE BOOK_MARK_GROUP\r\n"
            		   + "   SET GROUP_NM  = ?\r\n"
            		   + "     , ORDER_NUM = ?\r\n"
            		   + "     , UPDATE_DT = NOW()\r\n"
            		   + " WHERE GROUP_ID  = ? ;";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  g.getGroup_nm());
            preparedStatement.setString(2,  g.getOrder_num());
            preparedStatement.setString(3,  g.getGroup_id());
   
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
	 *  deleteBookMarkGroup
	 *  : BOOK_MARK_GROUP Delete
	 */
	public int deleteBookMarkGroup(String group_id) {
		
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

            String sql = "DELETE FROM BOOK_MARK_GROUP WHERE GROUP_ID = ? ;" ;

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group_id);
   
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
