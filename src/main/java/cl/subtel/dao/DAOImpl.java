package cl.subtel.dao;

import static cl.subtel.utilities.LoggerTool.loggerParameters;
import static cl.subtel.utilities.StringTool.appender;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import cl.subtel.spring.DataBaseConfiguration;
import cl.subtel.utilities.NumeroOP;

public class DAOImpl extends DataBaseConfiguration implements DAO {

	private static final String ESPACIO = " ";
	private static final String BDC_SUBTEL = "BDC_SUBTEL";
	private static final String PKG_JAVA_SERVICIOS_TRAM = "PKG_JAVA_SERVICIOS_TRAM";
	private static final String PKG_BDC_TOKEN = "PKG_BDC_TOKEN";
	private static final String PRC_CREAR_INGRESO_USU = "PRC_CREAR_INGRESO_USU";
	private static final String PRC_GET_TIPO_SERVICIOS = "PRC_GET_TIPO_SERVICIOS";
	private static final String PRC_GET_TIPO_TRAMITES = "PRC_GET_TIPO_TRAMITES";
	private static final String PRC_CREAR_DETALLE = "PRC_CREAR_DETALLE";
	private static final String PRC_ACTUALIZAR_TRAMITE = "PRC_ACTUALIZAR_TRAMITE";
	private static final String PRC_INSERTA_PRESOL = "PRC_INSERTA_PRESOL";		
	private static final String PRC_VALIDAR_NUMFECHA_OP = "PRC_VALIDAR_NUMFECHA_OP";
	private static final String PRC_RETORNA_DATOS_EMAIL_USU = "PRC_RETORNA_DATOS_EMAIL_USU";
	private static final String VALIDATOKEN = "VALIDATOKEN";
	private static final String PRC_RETORNA_DATOS_RUT = "PRC_RETORNA_DATOS_RUT";
	private static final Logger LOGGER = LogManager.getLogger(DAOImpl.class);

	@Override
	public Map<String, Object> saveUser(Map<String, Object> data) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_CREAR_INGRESO_USU);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pRutUsu", data.get("pRutUsu"))
			.addValue("pNombreUsu", data.get("pNombreUsu"))
			.addValue("pEmailUsu", data.get("pEmailUsu"))
			.addValue("pTipoPersonalidad", data.get("pTipoPersonalidad"))
			.addValue("pRutSolicitante", data.get("pRutSolicitante"))
			.addValue("pDvSolicitante", data.get("pDvSolicitante"))
			.addValue("pRSocialSolicitante", data.get("pRSocialSolicitante"))
			.addValue("pDirSolicitante", data.get("pDirSolicitante"))
			.addValue("pCodComuna", data.get("pCodComuna"));		
		LOGGER.info(appender("Parametros procedimiento: ", ESPACIO, PRC_CREAR_INGRESO_USU));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento: ", ESPACIO, PRC_CREAR_INGRESO_USU));
		LOGGER.info(out);
		response.put("codigo", (String) out.get("PCODIGOOPERACION"));
		response.put("mensaje", (String) out.get("PMENSAJEOPERACION"));
		response.put("reinId", (BigDecimal) out.get("PREINID")); 
		response.put("retrId", (BigDecimal) out.get("PRETRID"));
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getServiceList(String service) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_GET_TIPO_SERVICIOS);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pServicio", service);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_GET_TIPO_SERVICIOS));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_GET_TIPO_SERVICIOS));
		LOGGER.info(out);
		return (List<Map<String, Object>>) out.get("IOCURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTramiteList(String service) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_GET_TIPO_TRAMITES);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pServicio", service);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_GET_TIPO_TRAMITES));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_GET_TIPO_TRAMITES));
		LOGGER.info(out);
		return (List<Map<String, Object>>) out.get("IOCURSOR");
	}

	@Override
	public Map<String, Object> crearDetalle(Map<String, Object> data) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_CREAR_DETALLE);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pRetrId", data.get("pRetrId"))
			.addValue("pServicio", data.get("pServicio"))
			.addValue("pTiarCod", data.get("pTiarCod"))
			.addValue("pNomArchOriginal", data.get("pNomArchOriginal"))
			.addValue("pNomArchInterno", data.get("pNomArchInterno"))
			.addValue("pObs", data.get("pObs"));
		LOGGER.info(appender("Parametros procedimiento: ", PRC_CREAR_DETALLE));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_CREAR_DETALLE));
		LOGGER.info(out);
		response.put("codigo", (String) out.get("PCODIGOOPERACION"));
		response.put("mensaje", (String) out.get("PMENSAJEOPERACION"));
		return response;
	}
	
	@Override
	public Map<String, Object> actualizarTramite(Map<String, Object> data) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_ACTUALIZAR_TRAMITE);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pRetrId", data.get("retr"))
			.addValue("pCodServicio", data.get("servicio"))
			.addValue("pTipoTramite", data.get("tramite"))
			.addValue("pNumOp", data.get("numeroIngreso"))
			.addValue("pFecOp", data.get("fechaIngreso"))
			.addValue("pObs", data.get("observacion"))
			.addValue("pNumOpComplemento", data.get("numeroOperacion"))
			.addValue("pFecOpComplemento", data.get("fechaOperacion"));		
		LOGGER.info(appender("Parametros procedimiento: ", PRC_ACTUALIZAR_TRAMITE));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_ACTUALIZAR_TRAMITE));
		LOGGER.info(out);
		response.put("codigo", (String) out.get("PCODIGOOPERACION"));
		response.put("mensaje", (String) out.get("PMENSAJEOPERACION"));
		return response;
	}

	@Override
	public Map<String, Object> insertaPreSolicitud(int retrId) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_INSERTA_PRESOL);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pRetrId", retrId);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_INSERTA_PRESOL));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_INSERTA_PRESOL));
		LOGGER.info(out);
		response.put("codigo", (String) out.get("PCODIGOOPERACION"));
		response.put("mensaje", (String) out.get("PMENSAJEOPERACION"));
		return response;
	}
	
	@Override
	public String validaToken(String token) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_BDC_TOKEN)
			.withProcedureName(VALIDATOKEN);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pToken", token);
		LOGGER.info(appender("Parametros procedimiento: ", VALIDATOKEN));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", VALIDATOKEN));
		LOGGER.info(out);
		return (String) out.get("PRESPUESTA");
	}

	@Override
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_VALIDAR_NUMFECHA_OP);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("pNumOp", numero)
			.addValue("pFecOp", fecha);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_VALIDAR_NUMFECHA_OP));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_VALIDAR_NUMFECHA_OP));
		LOGGER.info(out);
		response.put("codigo", (String) out.get("PCODIGOOPERACION"));
		response.put("mensaje", (String) out.get("PMENSAJEOPERACION"));
		return response;
	}
	
	@Override
	public Map<String, Object> getDatosEmail(int retrId) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
			.withSchemaName(BDC_SUBTEL)
			.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
			.withProcedureName(PRC_RETORNA_DATOS_EMAIL_USU);
		Map<String, Object> response = new HashMap<>();
		SqlParameterSource in = new MapSqlParameterSource().addValue("pRetrId", retrId);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_RETORNA_DATOS_EMAIL_USU));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_RETORNA_DATOS_EMAIL_USU));
		LOGGER.info(out);
		response.put("emailUsuario", out.get("PEMAILUSU"));
		response.put("nombreUsuario", out.get("PNOMUSUARIO"));
		response.put("tipoServicio", out.get("PTIPOSERVICIO"));
		response.put("numeroOficinaParte", out.get("PNUMOP"));
		response.put("fechaOficinaParte", out.get("PFECOP"));		
		response.put("link", out.get("LINK"));
		response.put("sender", out.get("SENDER"));
		response.put("bcc", out.get("BCC"));
		response.put("subject", out.get("SUBJECT"));		
		response.put("codigoOperacion", out.get("PCODIGOOPERACION"));
		response.put("mensajeOperacion", out.get("PMENSAJEOPERACION"));
		return response;
	}

	@Override
	public Map<String, Object> getNumeroOP(Map<String, Object> data) throws Exception {
		return NumeroOP.getNumeroOP(data);
	}

	@Override
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.bdcDataSource)
				.withSchemaName(BDC_SUBTEL)
				.withCatalogName(PKG_JAVA_SERVICIOS_TRAM)
				.withProcedureName(PRC_RETORNA_DATOS_RUT);
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("pRut", rut)
				.addValue("pTipoPersona", tipoPerson);
		LOGGER.info(appender("Parametros procedimiento: ", PRC_RETORNA_DATOS_RUT));
		loggerParameters(in, LOGGER);
		Map<String, Object> out = jdbcCall.execute(in);
		LOGGER.info(appender("Respuesta del procedimiento ", PRC_RETORNA_DATOS_RUT));
		LOGGER.info(out);
		return out;
	}

}
