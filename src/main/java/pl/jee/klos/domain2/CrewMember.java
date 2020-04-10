package pl.jee.klos.domain2;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity 
public class CrewMember {
	
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 	
 	public CrewMember() {}
    
	@Column(name="crewMemberId", nullable=false)
	private Long crewMemberId; //login for crewMember

	@Column(name="firstname", nullable=false)
	private String firstname;

	@Column(name="lastname", nullable=false)
	private String lastname;
    
    private String password;

    @Lob
    private byte[] avatar;
    
    @ManyToMany(mappedBy = "crewMember", fetch = FetchType.EAGER)
    @JsonBackReference
	private Set<Flight> flight = new HashSet<Flight>(0);
    
    
    @ManyToMany(fetch = FetchType.EAGER)
	private Set<PersonRole> personRole = new HashSet<PersonRole>(0);


	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    
    public Long getCrewMemberId() {
		return crewMemberId;
	}
	public void setCrewMemberId(Long crewMemberId) {
		this.crewMemberId = crewMemberId;
	}

	public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	public Set<Flight> getFlight() {
		return flight;
	}
	public void setFlight(Set<Flight> flight) {
		this.flight = flight;
	}
	public Set<PersonRole> getPersonRole() {
		return personRole;
	}
	public void setPersonRole(Set<PersonRole> personRole) {
		this.personRole = personRole;
	}
   	
	

}
