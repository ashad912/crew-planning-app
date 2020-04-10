package pl.jee.klos.configuration;

import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


//import pl.jee.klos.dao.UserDAOImpl;
import pl.jee.klos.domain.User;
import pl.jee.klos.domain.UserRole;
import pl.jee.klos.service2.PdfView;

import pl.jee.klos.utils.CrewMemberConverter;
import pl.jee.klos.utils.CrewMemberListConverter;
import pl.jee.klos.utils.DateConverter;
import pl.jee.klos.utils.FlightConverter;
import pl.jee.klos.utils.LongConverter;
import pl.jee.klos.utils.MultipartFileConverter;
import pl.jee.klos.utils.PersonRoleConverter;
import pl.jee.klos.utils.PersonRoleListConverter;
import pl.jee.klos.utils.PlaneConverter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "pl.jee.klos")
@EnableJpaRepositories(basePackages={"pl.jee.klos.dao2"})
@EnableTransactionManagement
@Import({SecurityConfiguration.class})
public class Spring4Configuration extends WebMvcConfigurerAdapter {

	/**
	* Configure TilesConfigurer.
	*/
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/views/**/tiles.xml" });
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	 /**
     * Configure ViewResolvers to deliver preferred views.
     */
	
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
        registry.enableContentNegotiation(new PdfView());
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
	/**
	 * Configure MultipartFileResolver.
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}
     
    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Bean
	public MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver()
	{
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en"));
		resolver.setCookieName("myLocaleCookie");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);
		
	}
	
	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.TEXT_HTML)
                .parameterName("type")
                .favorParameter(true)
                .ignoreUnknownPathExtensions(false)
                .ignoreAcceptHeader(false)
                .useJaf(true);
    }

		// Hibernate configuration - DAO and JPA
		@Bean(name = "dataSource")
		public DataSource getDataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUrl("jdbc:postgresql://localhost:5432/spring4_LAB_KLOS?characterEncoding=utf-8");
			dataSource.setUsername("postgres");
			dataSource.setPassword("admin");
	
			return dataSource;
		}
	
		private Properties getHibernateProperties() {
			Properties properties = new Properties();
			properties.put("hibernate.show_sql", "true");
			properties.put("hibernate.hbm2ddl.auto", "update"); //bez tej linii nie wygeneruje nowej struktury w bazie danych			
			properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
			properties.put("hibernate.default_schema", "public");
			return properties;
		}
		
		// DAO specific
		
//		  @Autowired	  
//		  @Bean(name = "sessionFactory") public SessionFactory
//		  getSessionFactory(DataSource dataSource) {
//		  
//		  LocalSessionFactoryBuilder sessionBuilder = new
//		  LocalSessionFactoryBuilder(dataSource);
//		  sessionBuilder.addProperties(getHibernateProperties());
//		  sessionBuilder.addAnnotatedClasses(User.class);
//		  sessionBuilder.addAnnotatedClasses(UserRole.class);
//		  
//		  return sessionBuilder.buildSessionFactory(); }
//		  
//		  @Autowired  
//		  @Bean(name = "transactionManager") public HibernateTransactionManager
//		  getTransactionManager( SessionFactory sessionFactory) {
//		  HibernateTransactionManager transactionManager = new
//		  HibernateTransactionManager( sessionFactory);
//		  
//		  return transactionManager; }
//		  
//		  @Autowired
//		  @Bean(name = "userDAO") public UserDAO getUserDAO(SessionFactory
//		  sessionFactory) { return new UserDAOImpl(sessionFactory); }

		
	/*@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}*/
		
		@Override
	    public void addFormatters(FormatterRegistry formatterRegistry)
	    {
	        formatterRegistry.addConverter(getMyUserRoleConverter());
	        formatterRegistry.addConverter(getMyUserRoleListConverter());
	        formatterRegistry.addConverter(getMyMultipartFileConverter());
	        formatterRegistry.addConverter(getMyDateConverter());
	        formatterRegistry.addConverter(getMyCrewMemberConverter());
	        formatterRegistry.addConverter(getMyCrewMemberListConverter());
	        formatterRegistry.addConverter(getMyPlaneConverter());
	        formatterRegistry.addConverter(getMyLongConverter());
	        formatterRegistry.addConverter(getMyFlightConverter());
	    }

	    

	    @Bean
	    public PersonRoleConverter getMyUserRoleConverter() {
	        return new PersonRoleConverter();
	    }
	    
	    @Bean
	    public PersonRoleListConverter getMyUserRoleListConverter() {
	        return new PersonRoleListConverter();
	    }
	    @Bean
	    public MultipartFileConverter getMyMultipartFileConverter() {
	        return new MultipartFileConverter();
	    }
	    @Bean
	    public DateConverter getMyDateConverter() {
	        return new DateConverter();
	    }
	    @Bean
	    public CrewMemberConverter getMyCrewMemberConverter() {
	        return new CrewMemberConverter();
	    }
	    @Bean
	    public CrewMemberListConverter getMyCrewMemberListConverter() {
	        return new CrewMemberListConverter();
	    }
	    
	    @Bean
	    public PlaneConverter getMyPlaneConverter() {
	        return new PlaneConverter();
	    }
	    @Bean
	    public LongConverter getMyLongConverter() {
	        return new LongConverter();
	    }
	    
	    @Bean
	    public FlightConverter getMyFlightConverter() {
	        return new FlightConverter();
	    }
	// JPA specific
		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			em.setDataSource(getDataSource());
			em.setPackagesToScan(new String[] { "pl.jee.klos.domain2" });
	
			JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			em.setJpaVendorAdapter(vendorAdapter);
			em.setJpaProperties(getHibernateProperties());
			return em;
		}
	
		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(emf);
			return transactionManager;
		}
	
		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
			return new PersistenceExceptionTranslationPostProcessor();
		}
		@Bean
		public JavaMailSender getJavaMailSender() {
		    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		    mailSender.setHost("smtp.gmail.com");
		    mailSender.setPort(587);
		     
		    mailSender.setUsername("spring.app.sender.klos@gmail.com");
		    mailSender.setPassword("spring4spring4");
		     
		    Properties props = mailSender.getJavaMailProperties();
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.debug", "true");
		     
		    return mailSender;
		}
}
