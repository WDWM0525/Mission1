package History;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class History {                    
	private String id        ; // 히스토리키     
	private String lat       ; // X좌표   
	private String lnt       ; // Y좌표
	private String search_dt ; // 조회일자
}
