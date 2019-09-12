package cl.subtel.dao;

import java.util.List;
import java.util.Map;

public interface DAO extends BasicDAO {

	public Map<String, Object> saveUser(final Map<String, Object> data) throws Exception;
	public List<Map<String, Object>> getServiceList(String service) throws Exception;
	public List<Map<String, Object>> getTramiteList(String service) throws Exception;
	public Map<String, Object> crearDetalle(final Map<String, Object> data) throws Exception;
	public Map<String, Object> actualizarTramite(final Map<String, Object> data) throws Exception;
	public Map<String, Object> insertaPreSolicitud(int retrId) throws Exception;
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception;
	public Map<String, Object> getNumeroOP(final Map<String, Object> data) throws Exception;
	public Map<String, Object> getDatosEmail(int retrId) throws Exception;
	public String validaToken(String token) throws Exception;
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception;
	
}
