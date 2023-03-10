package himedia.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * create table member(
   id bigint generated by default as identity,
   userid varchar (25) unique,
   password varchar (100),
   username varchar (20),
   gender enum ('male', 'female'),
   birthday varchar (15),
   phone varchar (15),
   email varchar (25) unique,
   address varchar (50),
   role enum ('general', 'manager', 'admin'),
   primary key(id)
);
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "userid", unique = true)
	private String userId;
	@Column(name = "password")
	private String password;
	@Column(name = "username")
	private String userName;
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	@Column(name = "birthday")
	private String birthday;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "address")
	private String address;
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Builder
	public Member(String userId, String password, String userName, Gender gender, String birthday, String phone, String email,
			String address, Role role) {
		this.userId = userId;
		this.password = password;
		this.userName = userName;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.role = role;
	}
	
	public void update(String password, String phone, String email, String address) {
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

}