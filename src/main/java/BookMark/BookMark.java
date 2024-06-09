package BookMark;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMark {                    
	private String group_id        ; // 북마크그룹키
	private String book_mark_id    ; // 북마크키
	private String x_swifi_mgr_no  ; // 관리번호
	private String regist_dt       ; // 등록일자
	
	private String group_nm        ; // 북마크 이름
	private String x_swifi_main_nm ; // 와이파이명
}
