package pl.jee.klos.domain2;

import java.util.Date;

import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Flight {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public Flight() {}

	@Column(name="flightId", nullable=false)
    private Long flightId;
	
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonManagedReference
    @JoinTable(
    		name = "Flight_CrewMember", 
            joinColumns = { @JoinColumn(name = "flight_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "crewMember_id") }
    )
	private Set<CrewMember> crewMember = new HashSet<CrewMember>(0);
    
    @ManyToOne//(cascade = CascadeType.MERGE)
	@JsonManagedReference
	private Plane plane;
    
    @OneToOne(mappedBy="flight")
	@JsonBackReference
	private PlanningMember planningMember;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public PlanningMember getPlanningMember() {
		return planningMember;
	}

	public void setPlanningMember(PlanningMember planningMember) {
		this.planningMember = planningMember;
	}

	public Set<CrewMember> getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(Set<CrewMember> crewMember) {
		this.crewMember = crewMember;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
    
    


}
