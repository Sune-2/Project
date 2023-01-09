package himedia.project.entity;

import lombok.Getter;

@Getter
public enum Gender {
	male("male"), female("female");
	
	// field
	private final String description;
	
	// constructor
	private Gender(String description) {
		this.description = description;
	}
}
