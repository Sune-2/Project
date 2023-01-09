package himedia.project.entity;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SearchCondition {
	public String content;
	public SearchType type;
	
	public SearchCondition(String content, SearchType type) {
		this.content = content;
		this.type = type;
	}
}
