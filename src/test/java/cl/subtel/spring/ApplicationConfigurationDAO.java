package cl.subtel.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.subtel.dao.DAO;
import cl.subtel.dao.DAOImpl;
import cl.subtel.entities.enviroment.Environments;

@Configuration
public class ApplicationConfigurationDAO extends DataSources {
	
	@Bean
	public DAO getDao() throws Exception {
		DAO dao = new DAOImpl();
		dao.setDbcDataSource(this.getDbcDataSource(Environments.Local));
		dao.setSrgDataSource(this.getSgrDataSource(Environments.Local));
		dao.setPteDataSource(this.getPteDataSource(Environments.Local));
		dao.setGabineteDataSource(this.getGabineteDataSource(Environments.Local));
		dao.setArcgisDataSource(this.getArcgisDataSource(Environments.Local));
		dao.setMongoDataSource(this.getMongoDataSource(Environments.Local));
		return dao;
	}

}
