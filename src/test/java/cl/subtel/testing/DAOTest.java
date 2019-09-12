package cl.subtel.testing;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.subtel.dao.DAO;
import cl.subtel.spring.ApplicationConfigurationDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationDAO.class)
public class DAOTest {

	@Autowired
    private DAO dao;
	
	@Test
	public void saveUser() throws Exception {
		dao.saveUser(new HashMap<>());
	}
	
	@Test
	public void getServiceList() throws Exception {
		dao.getServiceList("SL");
	}
	
	@Test
	public void getTramiteList() throws Exception {
		dao.getTramiteList("SL");
	}
	
	@Test
	public void crearDetalle() throws Exception {
		dao.crearDetalle(new HashMap<>());
	}
	
	@Test
	public void actualizarTramite() throws Exception {
		dao.actualizarTramite(new HashMap<>());
	}
	
	@Test
	public void insertaPreSolicitud() throws Exception {
		dao.insertaPreSolicitud(1);
	}

	@Test
	public void validaOfinaParte() throws Exception {
		dao.validaOfinaParte(1, "");
	}
	
	@Test
	public void getDatosEmail() throws Exception {
		dao.getDatosEmail(1);
	}
	
	@Test
	public void validaToken() throws Exception {
		dao.validaToken("");
	}
	
	@Test
	public void getDatosUsuario() throws Exception {
		dao.getDatosUsuario("", "");
	}
	
}
