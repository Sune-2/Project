package himedia.project.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {
	TITLE("제목", "title"),
	WRITER("작성자", "writer"), 
	TITCONT("제목+내용", "title+content");
	
	private final String key;
	private final String value;
	
}
