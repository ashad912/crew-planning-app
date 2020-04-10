package pl.jee.klos.service2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.jee.klos.dao2.CrewMemberRepository;
import pl.jee.klos.dao2.PlanningMemberRepository;
import pl.jee.klos.domain.UserRole;
import pl.jee.klos.domain2.PersonRole;

@Service("myPersonDetailsService")
public class MyPersonDetailsService implements UserDetailsService {
 
	@Autowired
	private CrewMemberRepository repository;
	
	@Autowired
	private PlanningMemberRepository repository1;
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String login) 
		throws UsernameNotFoundException {
		long loginLong = Long.parseLong(login);
		if(repository.findByCrewMemberId(loginLong)!=null) {
			pl.jee.klos.domain2.CrewMember person = repository.findByCrewMemberId(loginLong);
			List<GrantedAuthority> authorities = 
                buildPersonAuthority(person.getPersonRole());
			
			return buildPersonForAuthentication(person, authorities);
			
		}else {
			pl.jee.klos.domain2.PlanningMember person  = repository1.findByPlanningMemberId(loginLong);
			List<GrantedAuthority> authorities = 
                buildPersonAuthority(person.getPersonRole());
			return buildPersonForAuthentication(person, authorities);
		}
	}
 
	// Converts service.User user to
	// org.springframework.security.core.userdetails.User
	private User buildPersonForAuthentication(pl.jee.klos.domain2.CrewMember person, 
		List<GrantedAuthority> authorities) {
		return new User(person.getCrewMemberId().toString(), person.getPassword(), 
			true, true, true, true, authorities);
	}
	
	private User buildPersonForAuthentication(pl.jee.klos.domain2.PlanningMember person, 
			List<GrantedAuthority> authorities) {
			return new User(person.getPlanningMemberId().toString(), person.getPassword(), 
				person.getEnabled(), true, true, true, authorities);
		}
 
	private List<GrantedAuthority> buildPersonAuthority(Set<PersonRole> personRoles) {
 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (PersonRole personRole : personRoles) {
			setAuths.add(new SimpleGrantedAuthority(personRole.getRole()));
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

	public CrewMemberRepository getCrewMemberRepository() {
		return repository;
	}
	public void setCrewMemberRepository(CrewMemberRepository repository) {
		this.repository = repository;
	}
	
	public PlanningMemberRepository getPlanningMemberRepository() {
		return repository1;
	}
	public void setPlanningMemberRepository(PlanningMemberRepository repository1) {
		this.repository1 = repository1;
	}
}
