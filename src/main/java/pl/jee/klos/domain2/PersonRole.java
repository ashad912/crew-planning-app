package pl.jee.klos.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersonRole {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private Integer id;
	
	public PersonRole() {}
	
	private String role;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}