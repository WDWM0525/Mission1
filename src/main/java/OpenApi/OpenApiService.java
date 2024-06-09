package OpenApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Wifi.Wifi;
import Wifi.WifiService;

public class OpenApiService {

	/** 
	 *  loadWifi
	 *  : Open API 와이파이 정보 가져오기
	 */
	public static HashMap<String, String> loadWifi() {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "Fail");
		
		
		try {
			int connectionResult = checkConnection();
			
			WifiService wifiService = new WifiService();
			wifiService.deleteWifiInfo();
			
			if (connectionResult > -1) {
				final int maxIdx = 1000;
				int start = 1;
				int end   = maxIdx;
				int insertResult = 0;
				
				while (start <= connectionResult) {
					System.out.println("start : " + start + " / end : " + end);
					insertResult += insertWifiInfoList(start, end);
					start += maxIdx;
					end   += maxIdx;
				} ;
				
				resultMap.put("result", "Success");
				resultMap.put("content", insertResult + "개의 WIFI정보를 정상적으로 저장하였습니다.");
				
			} else {
				resultMap.put("result", "Fail");
				resultMap.put("content", "연결 실패");
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		return resultMap;
	}
	
	/** 
	 *  checkConnection
	 *  : OPEN API 연결 확인 
	 */
	public static int checkConnection() throws IOException {
		int result = -1;
		
		/*URL*/
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		
		/*인증키*/
		urlBuilder.append("/" + URLEncoder.encode("4f6976437332317335326d7a4d7979","UTF-8") ); 
		
		/*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); 
		
		/*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
		
		/*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode(Integer.toString(1),"UTF-8")); 
		
		/*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		urlBuilder.append("/" + URLEncoder.encode(Integer.toString(1),"UTF-8"));
		
		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
		// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
		/* 서비스별 추가 요청인자들*/
		//urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8"));
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		
		/* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader json;
		
		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			json = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			json = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = json.readLine()) != null) {
			sb.append(line);
		}
		
		String str = sb.toString();
		System.out.println(str);
		
		// list_total_count = wifi 총 건수 
		if (str.indexOf("list_total_count") > -1) {
			JsonElement element = JsonParser.parseString(str);
			JsonObject tbPublicWifiInfo = element.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject();
			result = Integer.parseInt(tbPublicWifiInfo.get("list_total_count").getAsString());
		}
		
		return result;
	}
	
	/** 
	 *  insertWifiInfoList
	 *  : wifi정보 INSERT 
	 */
	public static int insertWifiInfoList (int start_index, int end_index) throws IOException {
		int insertResult = 0;
		
		/*URL*/
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
		
		/*인증키*/
		urlBuilder.append("/" + URLEncoder.encode("4f6976437332317335326d7a4d7979","UTF-8") ); 
		
		/*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); 
		
		/*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
		
		/*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode(Integer.toString(start_index),"UTF-8")); 
		
		/*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		urlBuilder.append("/" + URLEncoder.encode(Integer.toString(end_index),"UTF-8"));
		
		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
		// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
		/* 서비스별 추가 요청인자들*/
		//urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8"));
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		
		/* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader json;
		String result = "Fail";
		
		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			json = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = "Success";
		} else {
			json = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = json.readLine()) != null) {
			sb.append(line);
		}
		
		
		String str = sb.toString();
		System.out.println(str);
		
		if ("Success".equals(result)) {
			
			JsonElement element = JsonParser.parseString(str);
			JsonObject tbPublicWifiInfo = element.getAsJsonObject().get("TbPublicWifiInfo").getAsJsonObject();
			JsonArray rows = tbPublicWifiInfo.get("row").getAsJsonArray();
			
			WifiService wifiService = new WifiService();
			for (int i = 0; i < rows.size(); i++) {
				Wifi wifi = new Wifi();
				JsonObject row = rows.get(i).getAsJsonObject();
				
				wifi.setX_swifi_mgr_no(row.get("X_SWIFI_MGR_NO").getAsString());
				wifi.setX_swifi_wrdofc(row.get("X_SWIFI_WRDOFC").getAsString());
				wifi.setX_swifi_main_nm(row.get("X_SWIFI_MAIN_NM").getAsString());
				wifi.setX_swifi_adres1(row.get("X_SWIFI_ADRES1").getAsString());
				wifi.setX_swifi_adres2(row.get("X_SWIFI_ADRES2").getAsString());
				wifi.setX_swifi_instl_floor(row.get("X_SWIFI_INSTL_FLOOR").getAsString());
				wifi.setX_swifi_instl_ty(row.get("X_SWIFI_INSTL_TY").getAsString());
				wifi.setX_swifi_instl_mby(row.get("X_SWIFI_INSTL_MBY").getAsString());
				wifi.setX_swifi_svc_se(row.get("X_SWIFI_SVC_SE").getAsString());
				wifi.setX_swifi_cmcwr(row.get("X_SWIFI_CMCWR").getAsString());
				wifi.setX_swifi_cnstc_year(row.get("X_SWIFI_CNSTC_YEAR").getAsString());
				wifi.setX_swifi_inout_door(row.get("X_SWIFI_INOUT_DOOR").getAsString());
				wifi.setX_swifi_remars3(row.get("X_SWIFI_REMARS3").getAsString());
				wifi.setLat(row.get("LAT").getAsString());
				wifi.setLnt(row.get("LNT").getAsString());
				wifi.setWork_dttm(row.get("WORK_DTTM").getAsString());
				
				insertResult += wifiService.insertWifiInfo(wifi);
			}
			
		} else {
			System.out.println("시작" + str.indexOf("<MESSAGE>"));
			System.out.println("종료" + str.indexOf("</MESSAGE>"));
		}
		
		json.close();
		conn.disconnect();
		
		return insertResult;
	}

}
