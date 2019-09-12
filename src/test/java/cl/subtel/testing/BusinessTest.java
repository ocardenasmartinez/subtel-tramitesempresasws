package cl.subtel.testing;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.subtel.business.Business;
import cl.subtel.spring.ApplicationConfigurationBusiness;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationBusiness.class)
public class BusinessTest  {
	
	@Autowired
    private Business business;
	
	@Test
	public void getServiceList() throws Exception {
		business.getServiceList("SL");
	}
	
	@Test
	public void getTramiteList() throws Exception {
		business.getTramiteList("SL");
	}
	
	@Test
	public void saveUser() throws Exception {
		business.saveUser(new HashMap<>());
	}
	
	@Test
	public void insertaArchivos() throws Exception {
		Map<String, Object> data = new HashMap<>();
		MockMultipartFile file1 = new MockMultipartFile("originfile", "file1.pdf", null, "data".getBytes());
    	MockMultipartFile file2 = new MockMultipartFile("originfile", "file2.pdf", null, "data".getBytes());
    	MockMultipartFile[] originfile = new MockMultipartFile[2];
    	originfile[0] = file1;
    	originfile[1] = file2;
    	data.put("retr", "2000");
    	data.put("tiar", "TE_01");
    	data.put("servicio", "8");
    	data.put("originfile", originfile);
		business.insertaArchivos(data);
	}

}
