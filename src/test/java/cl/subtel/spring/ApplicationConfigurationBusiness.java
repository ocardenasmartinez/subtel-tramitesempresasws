package cl.subtel.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cl.subtel.business.Business;
import cl.subtel.business.BusinessImpl;
import cl.subtel.dao.DAO;
import cl.subtel.dao.DAOMock;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigurationBusiness {
	
	@Autowired
	private Environment enviroment;

	@Bean()
	public Business getBusiness() throws Exception {
		Business business = new BusinessImpl();
		business.setDao(getDao());
		business.setEnvironment(enviroment);
		return business;
	}

	@Bean
	public DAO getDao() throws Exception {
		return new DAOMock();
	}

	@Bean
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }

}
