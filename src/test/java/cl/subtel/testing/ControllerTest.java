package cl.subtel.testing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.subtel.request.ActualizarTramite;
import cl.subtel.request.SaveUser;
import cl.subtel.spring.ApplicationConfigurationWeb;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfigurationWeb.class)
public class ControllerTest {
		
	private static final String TOKEN_VALUE = "1";
	private static final String TOKEN = "token";

	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup () {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getServiceList() throws Exception {
    	List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> datos = new HashMap<>();
		datos.put("LUE", "Limitado de Uso Experimental");
		datos.put("MAM", "Msica Ambiental");
		datos.put("SLR", "Radiocomunicaciones");
		datos.put("TVCA", "Televisin por Cable");
		datos.put("TVST", "Televisin Satelital");
		list.add(datos);
        this.mockMvc.perform(get("/service/getservicelist?service=SL")
       		.header(TOKEN, TOKEN_VALUE)
       		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(list));
    }
    
    @Test
    public void geTramitesList() throws Exception {
    	List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> datos = new HashMap<>();
		datos.put("8", "Modifica Permiso");
		datos.put("10", "Otorga Permiso");
		datos.put("11", "Solicitud de Renovación");
		datos.put("12", "Solicitud de Extinción");
		datos.put("37", "Solicitud de Autorización Previa para Transferir");
		datos.put("9998", "Antecedentes Complementarios");
		datos.put("9999", "Reparos");
		list.add(datos);
        this.mockMvc.perform(get("/service/getramiteslist?service=SL")
        	.header(TOKEN, TOKEN_VALUE)
        	.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(list));
    }

    @Test
    public void guarDardatos() throws Exception {
    	Map<String, Object> out = new HashMap<>();
    	SaveUser request = new SaveUser();
		out.put("codigo", "000");
		out.put("retrId", "1881");
		out.put("reinId", "1921");
		out.put("mensaje", "Registro grabado exitosamente");		
		request.setCodigoComuna(10);
		request.setDireccionSolicitante("direccion");
		request.setEmail("email@email.com");
		request.setNombreUsuario("nombreusuario");
		request.setRazonSocialSolicitante("razon social");
		request.setRutDvSolicitante("1");
		request.setRutUsuario("11111111");
		request.setTipoPersonalidad("J");
        this.mockMvc.perform(post("/service/guardardatos")
        	.content(new ObjectMapper().writeValueAsString(request))
        	.header(TOKEN, TOKEN_VALUE)
        	.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(out));
    }
    
    @Test
    public void insertaArchivos() throws Exception {
    	List<Map<String, Object>> listOut = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map1.put("codigo", "002");
		map1.put("mensaje", "Registro no existe para parametro id Servicio y id tipo de archivo");
		map2.put("codigo", "002");
		map2.put("mensaje", "Registro no existe para parametro id Servicio y id tipo de archivo");
		listOut.add(map1);
		listOut.add(map2);
    	this.mockMvc.perform(multipart("/service/insertaarchivos?retr=2000&tiar=TE_01&servicio=8")
    		.file(new MockMultipartFile("originfile", "file1.pdf", null, "data".getBytes()))
    		.file(new MockMultipartFile("originfile", "file2.pdf", null, "data".getBytes()))
    		.header(TOKEN, TOKEN_VALUE)
    		.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.message").value("OK"))
    	.andExpect(jsonPath("$.error").isEmpty())
    	.andExpect(jsonPath("$.data").value(listOut));
    }
    
    @Test
    public void actualizaRegistro() throws Exception {
    	Map<String, Object> out = new HashMap<>();
    	ActualizarTramite request = new ActualizarTramite();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
        this.mockMvc.perform(post("/service/actualizaregistro")
        	.content(new ObjectMapper().writeValueAsString(request))
        	.header(TOKEN, TOKEN_VALUE)
        	.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(out));
    }
    
    @Test
    public void validaOp() throws Exception {
    	Map<String, Object> out = new HashMap<>();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
        this.mockMvc.perform(post("/service/validaop?numero=1&fecha=01-01-2019")
        	.header(TOKEN, TOKEN_VALUE)
        	.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(out));
    }
    
    @Test
    public void getDatosUsuario() throws Exception {
    	Map<String, Object> out = new HashMap<>();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
        this.mockMvc.perform(get("/service/consultadatosusuario?rut=76203967&tipo=J")
        	.header(TOKEN, TOKEN_VALUE)
        	.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.message").value("OK"))
        		.andExpect(jsonPath("$.error").isEmpty())
        		.andExpect(jsonPath("$.data").value(out));
    }

}
