package BookMarkGroup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkGroup {                    
	private String group_id  ; // 북마크그룹키
	private String group_nm  ; // 북마크그룹이름
	private String order_num ; // 순서
	private String regist_dt ; // 등록일자
	private String update_dt ; // 수정일자
}
