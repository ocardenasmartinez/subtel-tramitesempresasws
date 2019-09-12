package cl.subtel.spring;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cl.subtel.business.Business;
import cl.subtel.business.BusinessImpl;
import cl.subtel.dao.DAO;
import cl.subtel.dao.DAOImpl;
import cl.subtel.entities.enviroment.Environments;
import cl.subtel.interceptors.ServiceInterceptor;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "cl.subtel.controllers")
public class ApplicationConfiguration extends DataSources implements WebMvcConfigurer {

	@Autowired
	private Environment enviroment;

	private static final String MAIL_DEBUG = "mail.debug";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private static final String TRUE = "true";
	private static final String SMTP = "smtp";
	private static final String MAIL_USER = "MAIL_USER";
	private static final String MAIL_PORT = "MAIL_PORT";
	private static final String MAIL_HOST = "MAIL_HOST";
	private int maxUploadSizeInMb = 2 * 5 * 1024 * 1024; // 10 MB
	private static final Logger LOGGER = LogManager.getLogger(ApplicationConfiguration.class);

	@Bean
	public DAO getDao() {
		DAO dao = new DAOImpl();
		dao.setDbcDataSource(getDbcDataSource(Environments.Production));
		dao.setSrgDataSource(getSgrDataSource(Environments.Production));
		dao.setPteDataSource(getPteDataSource(Environments.Production));
		dao.setGabineteDataSource(getGabineteDataSource(Environments.Production));
		dao.setArcgisDataSource(getArcgisDataSource(Environments.Production));
		dao.setMongoDataSource(getMongoDataSource(Environments.Production));
		return dao;
	}

	@Bean
	public Business getBusiness() throws Exception {
		Business business = new BusinessImpl();
		business.setDao(getDao());
		business.setEnvironment(enviroment);
		return business;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		Business business = new BusinessImpl();
		try {
			business.setDao(getDao());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		business.setEnvironment(enviroment);
		ServiceInterceptor service = new ServiceInterceptor();
		service.setBusiness(business);
		registry.addInterceptor(service);
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(maxUploadSizeInMb);
		cmr.setMaxUploadSizePerFile(maxUploadSizeInMb);
		return cmr;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(enviroment.getProperty(MAIL_HOST));
		mailSender.setPort(Integer.parseInt(enviroment.getProperty(MAIL_PORT)));
		mailSender.setUsername(enviroment.getProperty(MAIL_USER));
		Properties props = mailSender.getJavaMailProperties();
		props.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
		props.put(MAIL_SMTP_STARTTLS_ENABLE, TRUE);
		props.put(MAIL_DEBUG, TRUE);
		return mailSender;
	}

}
