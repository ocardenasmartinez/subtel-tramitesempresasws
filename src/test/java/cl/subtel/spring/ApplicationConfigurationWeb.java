package cl.subtel.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cl.subtel.business.Business;
import cl.subtel.business.BusinessMock;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "cl.subtel.controllers")
public class ApplicationConfigurationWeb implements WebMvcConfigurer  {
	
	@Bean()
	public Business getBusiness() throws Exception {
		return new BusinessMock();
	}

}
