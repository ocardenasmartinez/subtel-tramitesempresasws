package cl.subtel.business;

import org.springframework.core.env.Environment;

import cl.subtel.dao.DAO;

public class BaseBusiness {
	
	public DAO dao;
	public Environment enviroment;
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}
	
	public void setEnvironment(Environment enviroment) {
		this.enviroment = enviroment;
	}

}
