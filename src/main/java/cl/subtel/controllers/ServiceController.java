package cl.subtel.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.subtel.business.Business;
import cl.subtel.entities.response.ServiceResponse;
import cl.subtel.request.ActualizarTramite;
import cl.subtel.request.SaveUser;

@RestController
@RequestMapping("/service")
public class ServiceController {

	private static final String NOK = "NOK";
	private static final String OK = "OK";
	private static final Logger LOGGER = LogManager.getLogger(ServiceController.class);

	@Autowired
	private Business business;

	@RequestMapping(value = "/guardardatos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Map<String, Object>> saveUser(@RequestBody SaveUser request) {
		Map<String, Object> data = new HashMap<>();
		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>();
		data.put("pRutUsu", request.getRutUsuario());
		data.put("pNombreUsu", request.getNombreUsuario());
		data.put("pEmailUsu", request.getEmail());
		data.put("pTipoPersonalidad", request.getTipoPersonalidad());
		data.put("pRutSolicitante", request.getRutSolicitante());
		data.put("pDvSolicitante", request.getRutDvSolicitante());
		data.put("pRSocialSolicitante", request.getRazonSocialSolicitante());
		data.put("pDirSolicitante", request.getDireccionSolicitante());
		data.put("pCodComuna", request.getCodigoComuna());
		try {
			response.setMessage(OK);
			response.setData(business.saveUser(data));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getservicelist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<Map<String, Object>>> getServiceList(@RequestParam(value = "service") String request) {
		ServiceResponse<List<Map<String, Object>>> response = new ServiceResponse<>();
		try {
			response.setMessage(OK);
			response.setData(business.getServiceList(request));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getramiteslist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<Map<String, Object>>> geTramitesList(@RequestParam(value = "service") String request) {
		ServiceResponse<List<Map<String, Object>>> response = new ServiceResponse<>();
		try {
			response.setMessage(OK);
			response.setData(business.getTramiteList(request));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/insertaarchivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<Map<String, Object>>> creadetalle(
			@RequestParam(value = "originfile") MultipartFile[] files, 
			@RequestParam(value = "retr") String retr,
			@RequestParam(value = "tiar") String tiar, 
			@RequestParam(value = "servicio") String servicio) {
		ServiceResponse<List<Map<String, Object>>> response = new ServiceResponse<>();
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("originfile", files);
			data.put("retr", retr);
			data.put("tiar", tiar);
			data.put("servicio", servicio);
			response.setMessage(OK);
			response.setData(business.insertaArchivos(data));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/actualizaregistro", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Map<String, Object>> actualizaRegistro(@RequestBody ActualizarTramite request) {
		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>();
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("retr", request.getRetr());
			data.put("tipoTramite", request.getTramite());
			data.put("servicio", request.getServicio());
			data.put("usuario", request.getUsuario());
			data.put("rut", request.getRut());
			data.put("tipoPersona", request.getTipoPersona());
			data.put("numeroOperacion", request.getNumeroOperacion());
			data.put("fechaOperacion", request.getFechaOperacion());
			response.setMessage(OK);
			response.setData(business.actualizaRegistro(data));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/validaop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Map<String, Object>> validaOp(
			@RequestParam(value = "numero") int numero,
			@RequestParam(value = "fecha") String fecha) {
		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>();
		try {
			response.setMessage(OK);
			response.setData(business.validaOfinaParte(numero, fecha));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos.");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/consultadatosusuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Map<String, Object>> getDatosUsuario(
			@RequestParam(value = "rut") String rut,
			@RequestParam(value = "tipo") String tipoPersona) {
		ServiceResponse<Map<String, Object>> response = new ServiceResponse<>();
		try {
			response.setMessage(OK);
			response.setData(business.getDatosUsuario(rut, tipoPersona));
		} catch (Exception e) {
			response.setMessage(NOK);
			response.setError("Error procesando datos."); 
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		return response;
	}

}
