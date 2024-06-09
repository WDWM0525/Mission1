package Wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import History.History;
import History.HistoryService;

public class WifiService {

	/** 
	 *  wifiList
	 *  : WIFI_INFO SELECT 
	 */
	public static List<Wifi> wifiList(String v_lat, String v_lnt) {
		
		List<Wifi> wifiList = new ArrayList<>();
		
		if (v_lat != null && !"".equals(v_lat) && v_lnt != null && !"".equals(v_lnt)) {
			
			History history = new History();
			history.setLat(v_lat);
			history.setLnt(v_lat);
			
			HistoryService historyService = new HistoryService();
			historyService.insertSiteHistory(history);
			
			System.out.println("v_lat : " + v_lat + " v_lnt : " + v_lnt);
			
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

	            String sql =  "SELECT FORMAT(6371 * 2 * ATAN2(SQRT(t2.A), SQRT(1-t2.A)), 4) AS DISTANCE\r\n"
				            + "	    , t1.X_SWIFI_MGR_NO\r\n"
				            + "	    , t1.X_SWIFI_WRDOFC\r\n"
				            + "	    , t1.X_SWIFI_MAIN_NM\r\n"
				            + "	    , t1.X_SWIFI_ADRES1\r\n"
				            + "	    , t1.X_SWIFI_ADRES2\r\n"
				            + "	    , t1.X_SWIFI_INSTL_FLOOR\r\n"
				            + "	    , t1.X_SWIFI_INSTL_TY\r\n"
				            + "	    , t1.X_SWIFI_INSTL_MBY\r\n"
				            + "	    , t1.X_SWIFI_SVC_SE\r\n"
				            + "	    , t1.X_SWIFI_CMCWR\r\n"
				            + "	    , t1.X_SWIFI_CNSTC_YEAR\r\n"
				            + "	    , t1.X_SWIFI_INOUT_DOOR\r\n"
				            + "	    , t1.X_SWIFI_REMARS3\r\n"
				            + "	    , t1.LAT\r\n"
				            + "	    , t1.LNT\r\n"
				            + "	    , t1.WORK_DTTM\r\n"
				            + "  FROM WIFI_INFO t1\r\n"
				            + "  LEFT OUTER JOIN (\r\n"
				            + "  		SELECT X_SWIFI_MGR_NO\r\n"
				            + "			     ,   SIN((LAT - ?) * (pi()/180) /2) \r\n"      // 1
				            + "			       * SIN((LAT - ?) * (pi()/180) /2)\r\n"       // 2
				            + "			       + COS(LAT       * (pi()/180)) \r\n"
				            + "			       * COS(? * (pi()/180)) \r\n"                 // 3
				            + "			       * SIN((LNT - ?) * (pi()/180) /2)\r\n"      // 4
				            + "			       * SIN((LNT - ?) * (pi()/180) /2) AS A\r\n" // 5
				            + "		     FROM WIFI_INFO\r\n"
				            + "  ) t2\r\n"
				            + "    ON t1.X_SWIFI_MGR_NO = t2.X_SWIFI_MGR_NO\r\n"
				            + " ORDER BY DISTANCE\r\n"
				            + " LIMIT 1, 20;";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, v_lat);
	            preparedStatement.setString(2, v_lat);
	            preparedStatement.setString(3, v_lat);
	            preparedStatement.setString(4, v_lnt);
	            preparedStatement.setString(5, v_lnt);

	            rs = preparedStatement.executeQuery();

	            System.out.println(rs);
	            while (rs.next()) {     
	            	String distance      	   = rs.getString("DISTANCE");		      // 거리(Km) 
	                String x_swifi_mgr_no      = rs.getString("X_SWIFI_MGR_NO");      // 관리번호       
	                String x_swifi_wrdofc      = rs.getString("X_SWIFI_WRDOFC");      // 자치구          
	                String x_swifi_main_nm     = rs.getString("X_SWIFI_MAIN_NM");     // 와이파이명        
	                String x_swifi_adres1      = rs.getString("X_SWIFI_ADRES1");      // 도로명주소       
	                String x_swifi_adres2      = rs.getString("X_SWIFI_ADRES2");      // 상세주소
	                String x_swifi_instl_floor = rs.getString("X_SWIFI_INSTL_FLOOR"); // 설치위치(층)     
	                String x_swifi_instl_ty    = rs.getString("X_SWIFI_INSTL_TY");    // 설치유형        
	                String x_swifi_instl_mby   = rs.getString("X_SWIFI_INSTL_MBY");   // 설치기관        
	                String x_swifi_svc_se      = rs.getString("X_SWIFI_SVC_SE");      // 설치구분       
	                String x_swifi_cmcwr       = rs.getString("X_SWIFI_CMCWR");       // 망종류         
	                String x_swifi_cnstc_year  = rs.getString("X_SWIFI_CNSTC_YEAR");  // 설치년도         
	                String x_swifi_inout_door  = rs.getString("X_SWIFI_INOUT_DOOR");  // 실내외구분
	                String x_swifi_remars3     = rs.getString("X_SWIFI_REMARS3");     // wifi접속환경  
	                String lat			       = rs.getString("LAT");                 // Y좌표       
	                String lnt			       = rs.getString("LNT");                 // X좌표         
	                String work_dttm	       = rs.getString("WORK_DTTM");           // 작업일자 
	                
	                Wifi wifi = new Wifi();
	                
	                wifi.setDistance(distance);
	                wifi.setX_swifi_mgr_no(x_swifi_mgr_no);
	                wifi.setX_swifi_wrdofc(x_swifi_wrdofc);
	                wifi.setX_swifi_main_nm(x_swifi_main_nm);
	                wifi.setX_swifi_adres1(x_swifi_adres1);
	                wifi.setX_swifi_adres2(x_swifi_adres2);
	                wifi.setX_swifi_instl_floor(x_swifi_instl_floor);
	                wifi.setX_swifi_instl_ty(x_swifi_instl_ty);
	                wifi.setX_swifi_instl_mby(x_swifi_instl_mby);
	                wifi.setX_swifi_svc_se(x_swifi_svc_se);
	                wifi.setX_swifi_cmcwr(x_swifi_cmcwr);
	                wifi.setX_swifi_cnstc_year(x_swifi_cnstc_year);
	                wifi.setX_swifi_inout_door(x_swifi_inout_door);
	                wifi.setX_swifi_remars3(x_swifi_remars3);
	                wifi.setLat(lat);
	                wifi.setLnt(lnt);
	                wifi.setWork_dttm(work_dttm);
	                
	                wifiList.add(wifi);
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
		}

        return wifiList;
	}
	
	/** 
	 *  wifiList
	 *  : WIFI_INFO SELECT 
	 */
	public static List<Wifi> wifiList(String v_x_swifi_mgr_no) {
		
		List<Wifi> wifiList = new ArrayList<>();
		
		if (v_x_swifi_mgr_no != null && !"".equals(v_x_swifi_mgr_no)) {
			
			System.out.println("v_x_swifi_mgr_no : " + v_x_swifi_mgr_no);
			
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

	            String sql =  "SELECT FORMAT(6371 * 2 * ATAN2(SQRT(t2.A), SQRT(1-t2.A)), 4) AS DISTANCE\r\n"
				            + "	    , t1.X_SWIFI_MGR_NO\r\n"
				            + "	    , t1.X_SWIFI_WRDOFC\r\n"
				            + "	    , t1.X_SWIFI_MAIN_NM\r\n"
				            + "	    , t1.X_SWIFI_ADRES1\r\n"
				            + "	    , t1.X_SWIFI_ADRES2\r\n"
				            + "	    , t1.X_SWIFI_INSTL_FLOOR\r\n"
				            + "	    , t1.X_SWIFI_INSTL_TY\r\n"
				            + "	    , t1.X_SWIFI_INSTL_MBY\r\n"
				            + "	    , t1.X_SWIFI_SVC_SE\r\n"
				            + "	    , t1.X_SWIFI_CMCWR\r\n"
				            + "	    , t1.X_SWIFI_CNSTC_YEAR\r\n"
				            + "	    , t1.X_SWIFI_INOUT_DOOR\r\n"
				            + "	    , t1.X_SWIFI_REMARS3\r\n"
				            + "	    , t1.LAT\r\n"
				            + "	    , t1.LNT\r\n"
				            + "	    , t1.WORK_DTTM\r\n"
				            + "  FROM WIFI_INFO t1\r\n"
				            + "  LEFT OUTER JOIN (\r\n"
				            + "  		SELECT X_SWIFI_MGR_NO\r\n"
				            + "			     ,   SIN((LAT - LAT) * (pi()/180) /2) \r\n"      
				            + "			       * SIN((LAT - LAT) * (pi()/180) /2)\r\n"       
				            + "			       + COS(LAT       * (pi()/180)) \r\n"
				            + "			       * COS(LAT * (pi()/180)) \r\n"                
				            + "			       * SIN((LNT - LNT) * (pi()/180) /2)\r\n"      
				            + "			       * SIN((LNT - LNT) * (pi()/180) /2) AS A\r\n" 
				            + "		     FROM WIFI_INFO\r\n"
				            + "		    WHERE X_SWIFI_MGR_NO = ? \r\n"
				            + "  ) t2\r\n"
				            + "    ON t1.X_SWIFI_MGR_NO = t2.X_SWIFI_MGR_NO\r\n"
				            + " WHERE t1.X_SWIFI_MGR_NO = ? ;";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, v_x_swifi_mgr_no);
	            preparedStatement.setString(2, v_x_swifi_mgr_no);

	            rs = preparedStatement.executeQuery();

	            System.out.println(rs);
	            while (rs.next()) {     
	            	String distance      	   = rs.getString("DISTANCE");		      // 거리(Km) 
	                String x_swifi_mgr_no      = rs.getString("X_SWIFI_MGR_NO");      // 관리번호       
	                String x_swifi_wrdofc      = rs.getString("X_SWIFI_WRDOFC");      // 자치구          
	                String x_swifi_main_nm     = rs.getString("X_SWIFI_MAIN_NM");     // 와이파이명        
	                String x_swifi_adres1      = rs.getString("X_SWIFI_ADRES1");      // 도로명주소       
	                String x_swifi_adres2      = rs.getString("X_SWIFI_ADRES2");      // 상세주소
	                String x_swifi_instl_floor = rs.getString("X_SWIFI_INSTL_FLOOR"); // 설치위치(층)     
	                String x_swifi_instl_ty    = rs.getString("X_SWIFI_INSTL_TY");    // 설치유형        
	                String x_swifi_instl_mby   = rs.getString("X_SWIFI_INSTL_MBY");   // 설치기관        
	                String x_swifi_svc_se      = rs.getString("X_SWIFI_SVC_SE");      // 설치구분       
	                String x_swifi_cmcwr       = rs.getString("X_SWIFI_CMCWR");       // 망종류         
	                String x_swifi_cnstc_year  = rs.getString("X_SWIFI_CNSTC_YEAR");  // 설치년도         
	                String x_swifi_inout_door  = rs.getString("X_SWIFI_INOUT_DOOR");  // 실내외구분
	                String x_swifi_remars3     = rs.getString("X_SWIFI_REMARS3");     // wifi접속환경  
	                String lat			       = rs.getString("LAT");                 // Y좌표       
	                String lnt			       = rs.getString("LNT");                 // X좌표         
	                String work_dttm	       = rs.getString("WORK_DTTM");           // 작업일자 
	                
	                Wifi wifi = new Wifi();
	                
	                wifi.setDistance(distance);
	                wifi.setX_swifi_mgr_no(x_swifi_mgr_no);
	                wifi.setX_swifi_wrdofc(x_swifi_wrdofc);
	                wifi.setX_swifi_main_nm(x_swifi_main_nm);
	                wifi.setX_swifi_adres1(x_swifi_adres1);
	                wifi.setX_swifi_adres2(x_swifi_adres2);
	                wifi.setX_swifi_instl_floor(x_swifi_instl_floor);
	                wifi.setX_swifi_instl_ty(x_swifi_instl_ty);
	                wifi.setX_swifi_instl_mby(x_swifi_instl_mby);
	                wifi.setX_swifi_svc_se(x_swifi_svc_se);
	                wifi.setX_swifi_cmcwr(x_swifi_cmcwr);
	                wifi.setX_swifi_cnstc_year(x_swifi_cnstc_year);
	                wifi.setX_swifi_inout_door(x_swifi_inout_door);
	                wifi.setX_swifi_remars3(x_swifi_remars3);
	                wifi.setLat(lat);
	                wifi.setLnt(lnt);
	                wifi.setWork_dttm(work_dttm);
	                
	                wifiList.add(wifi);
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
		}

        return wifiList;
	}
	
	
	/** 
	 *  insertWifiInfo
	 *  : WIFI_INFO INSERT
	 */
	public static int insertWifiInfo(Wifi w) {
		
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

            String sql =  "INSERT INTO WIFI_INFO\r\n"
	            		+ "(\r\n"
	            		+ "	  X_SWIFI_MGR_NO\r\n"
	            		+ "	, X_SWIFI_WRDOFC\r\n"
	            		+ "	, X_SWIFI_MAIN_NM\r\n"
	            		+ "	, X_SWIFI_ADRES1\r\n"
	            		+ "	, X_SWIFI_ADRES2\r\n"
	            		+ "	, X_SWIFI_INSTL_FLOOR\r\n"
	            		+ "	, X_SWIFI_INSTL_TY\r\n"
	            		+ "	, X_SWIFI_INSTL_MBY\r\n"
	            		+ "	, X_SWIFI_SVC_SE\r\n"
	            		+ "	, X_SWIFI_CMCWR\r\n"
	            		+ "	, X_SWIFI_CNSTC_YEAR\r\n"
	            		+ "	, X_SWIFI_INOUT_DOOR\r\n"
	            		+ "	, X_SWIFI_REMARS3\r\n"
	            		+ "	, LAT\r\n"
	            		+ "	, LNT\r\n"
	            		+ "	, WORK_DTTM\r\n"
	            		+ ")\r\n"
	            		+ "VALUES\r\n"
	            		+ "(\r\n"
	            		+ "	  ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ "	, ?\r\n"
	            		+ ");";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  w.getX_swifi_mgr_no());
            preparedStatement.setString(2,  w.getX_swifi_wrdofc());
            preparedStatement.setString(3,  w.getX_swifi_main_nm());
            preparedStatement.setString(4,  w.getX_swifi_adres1());
            preparedStatement.setString(5,  w.getX_swifi_adres2());
            preparedStatement.setString(6,  w.getX_swifi_instl_floor());
            preparedStatement.setString(7,  w.getX_swifi_instl_ty());
            preparedStatement.setString(8,  w.getX_swifi_instl_mby());
            preparedStatement.setString(9,  w.getX_swifi_svc_se());
            preparedStatement.setString(10, w.getX_swifi_cmcwr());
            preparedStatement.setString(11, w.getX_swifi_cnstc_year());
            preparedStatement.setString(12, w.getX_swifi_inout_door());
            preparedStatement.setString(13, w.getX_swifi_remars3());
            preparedStatement.setString(14, w.getLat());
            preparedStatement.setString(15, w.getLnt());
            preparedStatement.setString(16, w.getWork_dttm()); 
   
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
	 *  deleteWifiInfo
	 *  : WIFI_INFO DELETE
	 */
	public static void deleteWifiInfo() {
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

            String sql =  "DELETE FROM WIFI_INFO;";

            preparedStatement = connection.prepareStatement(sql);

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
