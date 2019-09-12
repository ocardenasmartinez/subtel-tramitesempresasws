package cl.subtel.business;

import java.util.List;
import java.util.Map;

public interface Business extends BasicBusiness {

	public Map<String, Object> saveUser(final Map<String, Object> data) throws Exception;
	public List<Map<String, Object>> getServiceList(String service) throws Exception;
	public List<Map<String, Object>> getTramiteList(String service) throws Exception;	
	public List<Map<String, Object>> insertaArchivos(final Map<String, Object> data) throws Exception;
	public Map<String, Object> actualizaRegistro(final Map<String, Object> data) throws Exception;
	public String sendMail(int retr)  throws Exception;	
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception;
	public boolean validaToken(String token) throws Exception;
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception;
	
}
