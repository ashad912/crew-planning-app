package pl.jee.klos.service2;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.jee.klos.dao2.CrewMemberRepository;
import pl.jee.klos.dao2.PlanningMemberRepository;
import pl.jee.klos.dao2.VerificationTokenRepository;
import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.domain2.VerificationToken;

@Service
@Transactional
public class PlanningMemberServiceImp implements PlanningMemberService{

	@Autowired
	PlanningMemberRepository repository;
	
	@Autowired
	VerificationTokenRepository tokenRepository;
	
	@Override
	public PlanningMember getPlanningMember(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	@Override
	public PlanningMember getPlanningMemberByPlanningMemberId(long planningMemberId) {
		// TODO Auto-generated method stub
		System.out.println("List PlanningMember ID: " + planningMemberId);
		return repository.findByPlanningMemberId(planningMemberId);
	}
	
	@Override
	public PlanningMember getPlanningMemberByLastname(String lastname) {
		// TODO Auto-generated method stub
		return repository.findByLastname(lastname);
	}
	
	@Override
	public PlanningMember getPlanningMemberByFlight(Flight flight) {
		// TODO Auto-generated method stub
		
		System.out.println("Listed planningMembers by Flight: " + flight.getFlightId());
		return repository.findByFlight(flight);
	}
	@Override
	public List<PlanningMember> getAllPlanningMembers() {
		// TODO Auto-generated method stub
		
		System.out.println("Listed all planningMembers");
		return repository.findAll();
	}

	

	@Override
	public void addPlanningMember(PlanningMember planningMember) {
		// TODO Auto-generated method stub
		planningMember.setPassword(hashPassword(planningMember.getPassword()));
		System.out.println("Added - First Name: " + planningMember.getFirstname() +
                " Last Name: " + planningMember.getLastname() + " PlanningId: " + 
		   planningMember.getPlanningMemberId());
		
		repository.save(planningMember);
	}
	@Override
	public void editPlanningMember(PlanningMember planningMember) {
		// TODO Auto-generated method stub
		planningMember.setPassword(hashPassword(planningMember.getPassword()));
		System.out.println("Edited - First Name: " + planningMember.getFirstname() +
                " Last Name: " + planningMember.getLastname() + " PlanningId: " + 
		   planningMember.getPlanningMemberId());
		
		repository.save(planningMember);
	}
	
	@Override
	public void editPlanningMemberWithNoHash(PlanningMember planningMember) {
		// TODO Auto-generated method stub
		System.out.println("EditedNoHash - First Name: " + planningMember.getFirstname() +
                " Last Name: " + planningMember.getLastname() + " PlanningId: " + 
		   planningMember.getPlanningMemberId());
		
		repository.save(planningMember);
	}
	@Override
	public void deletePlanningMember(PlanningMember planningMember) {
		// TODO Auto-generated method stub
		
		System.out.println("Added - First Name: " + planningMember.getFirstname() +
                " Last Name: " + planningMember.getLastname() + " PlanningId: " + 
		   planningMember.getPlanningMemberId());
		
		repository.delete(planningMember);
	}
	@Override
	public String hashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	@Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
	
	@Override
    public void createVerificationToken(PlanningMember planningMember, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setPlanningMember(planningMember);
        myToken.setToken(token);
        myToken.setExpiryDate(calculateExpiryDate(60 * 24));
        tokenRepository.save(myToken);
        planningMember.setVerificationToken(myToken);
        repository.save(planningMember);
    }
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	@Override
	public VerificationToken getVerificationTokenByPlanningMember(PlanningMember planningMember) {
		// TODO Auto-generated method stub
		return tokenRepository.findByPlanningMember(planningMember);
	}
	
	@Override
	public void deleteToken(VerificationToken vT) {
	
		tokenRepository.delete(vT);
	}

	
}
