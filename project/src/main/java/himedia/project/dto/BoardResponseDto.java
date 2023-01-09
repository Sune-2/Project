package himedia.project.dto;

import java.time.LocalDateTime;

import himedia.project.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
	
	private Long id; // PK
	private String title; // 제목
	private String content; // 내용
	private String writer; // 작성자
	private Long hits; // 조회수
	private LocalDateTime createDate; // 생성일
	private LocalDateTime updateDate; // 수정일
	
	public BoardResponseDto(Board entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.writer = entity.getWriter();
		this.hits = entity.getHits();
		this.createDate = entity.getCreateDate();
		this.updateDate = entity.getUpdateDate();
	}
	
}
