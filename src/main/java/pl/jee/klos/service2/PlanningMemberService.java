package pl.jee.klos.service2;

import java.util.List;

import pl.jee.klos.domain2.Flight;
import pl.jee.klos.domain2.PlanningMember;
import pl.jee.klos.domain2.VerificationToken;

public interface PlanningMemberService {
	//GET
	public PlanningMember getPlanningMember(int id);
	public PlanningMember getPlanningMemberByPlanningMemberId(long planningMemberId);
	public PlanningMember getPlanningMemberByLastname(String lastname);
	public PlanningMember getPlanningMemberByFlight(Flight flight);
	public List<PlanningMember> getAllPlanningMembers();
	
	//POST, DELETE
	public void addPlanningMember(PlanningMember planningMember);
	public void editPlanningMember(PlanningMember planningMember);
	public void editPlanningMemberWithNoHash(PlanningMember planningMember);
	public void deletePlanningMember(PlanningMember planningMember);

	public String hashPassword(String password);
	
	
	void createVerificationToken(PlanningMember planningMember, String token);

	public VerificationToken getVerificationToken(String VerificationToken);
	public VerificationToken getVerificationTokenByPlanningMember(PlanningMember planningMember);
	public void deleteToken(VerificationToken vT);
	
	
}
