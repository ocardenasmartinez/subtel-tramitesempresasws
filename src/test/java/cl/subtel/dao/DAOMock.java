package cl.subtel.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.subtel.spring.DataBaseConfiguration;

public class DAOMock extends DataBaseConfiguration implements DAO {

	@Override
	public Map<String, Object> saveUser(Map<String, Object> data) throws Exception {
		Map<String, Object> datos = new HashMap<>();
		datos.put("LUE", "Limitado de Uso Experimental");
		datos.put("MAM", "Msica Ambiental");
		datos.put("SLR", "Radiocomunicaciones");
		datos.put("TVCA", "Televisin por Cable");
		datos.put("TVST", "Televisin Satelital");
		return datos;
	}

	@Override
	public List<Map<String, Object>> getServiceList(String service) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getTramiteList(String service) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> crearDetalle(Map<String, Object> data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> actualizarTramite(Map<String, Object> data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> insertaPreSolicitud(int retrId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getNumeroOP(Map<String, Object> data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatosEmail(int retrId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validaToken(String token) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
