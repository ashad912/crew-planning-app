package pl.jee.klos.domain2;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class PlanningMember {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public PlanningMember() {}
    
	@Column(name="planningMemberId", nullable=false)
	private Long planningMemberId; //login for planningMember

	@Column(name="firstname", nullable=false)
	private String firstname;

	@Column(name="lastname", nullable=false)
	private String lastname;
    
    private String password;
    
    private Boolean enabled = false;

    private Boolean admin;
    
    @OneToOne
    @JsonManagedReference
	private Flight flight;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private VerificationToken verificationToken;
    
    @ManyToMany(fetch = FetchType.EAGER)
	private Set<PersonRole> personRole = new HashSet<PersonRole>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public Long getPlanningMemberId() {
		return planningMemberId;
	}

	public void setPlanningMemberId(Long planningMemberId) {
		this.planningMemberId = planningMemberId;
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

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Set<PersonRole> getPersonRole() {
		return personRole;
	}

	public void setPersonRole(Set<PersonRole> personRole) {
		this.personRole = personRole;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	
    
	
    

}
