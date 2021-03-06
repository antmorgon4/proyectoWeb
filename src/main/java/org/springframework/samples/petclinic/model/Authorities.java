package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "username")
	User user;
	
	@Override
	public String toString() {
		return "Authorities [user=" + user.username + ", password=" + user.password + ", authority=" + authority + "]";
	}

	@Size(min = 3, max = 50)
	String authority;
	
}
