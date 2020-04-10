package pl.jee.klos.configuration;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	@Resource(name="myUserDetailsService")
//	private UserDetailsService userDetailsService;
//	
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//    	
//    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    	
//        auth.inMemoryAuthentication().withUser("kapj").password("kapj").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN","USER");
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
    @Resource(name="myPersonDetailsService")
	private UserDetailsService personDetailsService;
	
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    	
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	
        auth.inMemoryAuthentication().withUser("123456").password("123456").roles("SUPER_ADMIN");
        auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
            
      http.authorizeRequests()
      	.antMatchers("/hello.html").access("hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/delete/**.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/crewMembers.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/crewMemberDetails.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/planningMembers").access("hasRole('ROLE_ADMIN')")
      	.antMatchers("/planningMemberDetails.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/planes.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/planeDetails.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
      	.antMatchers("/flights.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
        .antMatchers("/menuUserCM.html").access("hasRole('ROLE_USER_CM')")
        .antMatchers("/userCrewMember.html").access("hasRole('ROLE_USER_CM')")
        .antMatchers("/menuUserPM.html").access("hasRole('ROLE_USER_PM') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
        .antMatchers("/userPlanningMember.html").access("hasRole('ROLE_USER_PM') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
        .antMatchers("/flightEdit.html").access("hasRole('ROLE_USER_PM')")
        .antMatchers("/flightEditInit.html").access("hasRole('ROLE_USER_PM')")
        .antMatchers("/crewMemberEditInit.html").access("hasRole('ROLE_USER_CM')")
        .antMatchers("/planningMemberEditInit.html").access("hasRole('ROLE_USER_PM')")
        .antMatchers("/flightEditInit.html").access("hasRole('ROLE_USER_PM')")
        .antMatchers("/flightPDF.pdf").access("hasRole('ROLE_USER_CM')")
        .antMatchers("/success.html").access("hasRole('ROLE_USER_PM') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER_CM') or hasRole('ROLE_SUPER_ADMIN')")
        .and().formLogin().loginPage("/login").permitAll()
        .usernameParameter("login").passwordParameter("password").defaultSuccessUrl("/success")
        .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
    