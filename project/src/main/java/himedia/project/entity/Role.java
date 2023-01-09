package himedia.project.entity;

import lombok.Getter;

@Getter
public enum Role {
	general("GENERAL"), manager("MANAGER"), admin("ADMIN");
	
	Role(String value) {
		this.value = value;
	}
	
	private String value;
}
