package cl.subtel.business;

import org.springframework.core.env.Environment;

import cl.subtel.dao.DAO;

public interface BasicBusiness {

	public void setDao(DAO dao);
	public void setEnvironment(Environment enviroment);
	
}
