package Wifi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wifi {                    
	private String x_swifi_mgr_no      ; // 관리번호     
	private String x_swifi_wrdofc      ; // 자치구      
	private String x_swifi_main_nm     ; // 와이파이명    
	private String x_swifi_adres1      ; // 도로명주소    
	private String x_swifi_adres2      ; // 상세주소     
	private String x_swifi_instl_floor ; // 설치위치(층)  
	private String x_swifi_instl_ty    ; // 설치유형     
	private String x_swifi_instl_mby   ; // 설치기관     
	private String x_swifi_svc_se      ; // 설치구분     
	private String x_swifi_cmcwr       ; // 망종류      
	private String x_swifi_cnstc_year  ; // 설치년도     
	private String x_swifi_inout_door  ; // 실내외구분    
	private String x_swifi_remars3     ; // wifi접속환경
	private String lat			       ; // Y좌표      
	private String lnt			       ; // X좌표      
	private String work_dttm	       ; // 작업일자  
	private String distance 		   ; // 거리(km)
	
}
