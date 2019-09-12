package cl.subtel.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessMock extends BaseBusiness implements Business {

	@Override
	public Map<String, Object> saveUser(Map<String, Object> data) throws Exception {
		Map<String, Object> out = new HashMap<>();
		out.put("codigo", "000");
		out.put("retrId", "1881");
		out.put("reinId", "1921");
		out.put("mensaje", "Registro grabado exitosamente");
		return out;
	}

	@Override
	public List<Map<String, Object>> getServiceList(String service) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> datos = new HashMap<>();
		datos.put("LUE", "Limitado de Uso Experimental");
		datos.put("MAM", "Msica Ambiental");
		datos.put("SLR", "Radiocomunicaciones");
		datos.put("TVCA", "Televisin por Cable");
		datos.put("TVST", "Televisin Satelital");
		list.add(datos);
		return list;
	}

	@Override
	public List<Map<String, Object>> getTramiteList(String service) throws Exception {
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
		return list;
	}

	@Override
	public List<Map<String, Object>> insertaArchivos(Map<String, Object> data) throws Exception {
		List<Map<String, Object>> listOut = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map1.put("codigo", "002");
		map1.put("mensaje", "Registro no existe para parametro id Servicio y id tipo de archivo");
		map2.put("codigo", "002");
		map2.put("mensaje", "Registro no existe para parametro id Servicio y id tipo de archivo");
		listOut.add(map1);
		listOut.add(map2);
		return listOut;
	}

	@Override
	public Map<String, Object> actualizaRegistro(Map<String, Object> data) throws Exception {
		Map<String, Object> out = new HashMap<>();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
		return out;
	}

	@Override
	public String sendMail(int retr) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception {
		Map<String, Object> out = new HashMap<>();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
		return out;
	}

	@Override
	public boolean validaToken(String token) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception {
		Map<String, Object> out = new HashMap<>();
    	out.put("codigo", "001");
    	out.put("mensaje", "OK");	
		return out;
	}

}
