package pl.jee.klos.utils;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import pl.jee.klos.domain2.PlanningMember;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	 	private String appUrl;
	    private Locale locale;
	    private PlanningMember planningMember;
	 
	    public OnRegistrationCompleteEvent(
	      PlanningMember planningMember, Locale locale, String appUrl) {
	        super(planningMember);
	         
	        this.planningMember = planningMember;
	        this.locale = locale;
	        this.appUrl = appUrl;
	    }

		public String getAppUrl() {
			return appUrl;
		}

		public void setAppUrl(String appUrl) {
			this.appUrl = appUrl;
		}

		public Locale getLocale() {
			return locale;
		}

		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		public PlanningMember getPlanningMember() {
			return planningMember;
		}

		public void setPlanningMember(PlanningMember planningMember) {
			this.planningMember = planningMember;
		}
	    
	    
}
