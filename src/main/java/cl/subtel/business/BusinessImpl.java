package cl.subtel.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import static cl.subtel.utilities.StringTool.appender;

public class BusinessImpl extends BaseBusiness implements Business {

	private static final String S = "s";
	private static final String YY = "yy";
	private static final String PRT_PDF = "PRT.pdf";
	private static final String PUNTO_COMA = ";";
	private static final String ANTECEDENTES_LEGALES = "AL";
	private static final String OK = "OK";
	private static final String _PDF = ".pdf";
	private static final String EMAIL_OK = "EMAIL_OK";
	private static final String NUMERO_OP_URL = "NUMERO_OP_URL";
	private static final String SENT = "sent";
	private static final String PUNTO = ".";
	private static final String SLASH = "/";
	private static final String TEMP_PATH = "TEMP_PATH";
	private static final String CONSOLIDADO_PATH = "CONSOLIDADO_PATH";
	private static final String LEGALES_PATH = "LEGALES_PATH";
	private static final String PORTAL_DE_TRAMITES = "Ingreso Web Portal de Trámites";
	private static final String YYY_M_MDD = "yyyMMdd";
	private static final int UN_MILLON = 1_000_000;
	private static final Logger LOGGER = LogManager.getLogger(BusinessImpl.class);
	
	@Autowired
    private JavaMailSender emailSender;

	@Override
	public Map<String, Object> saveUser(final Map<String, Object> data) throws Exception {
		return dao.saveUser(data);
	}

	@Override
	public List<Map<String, Object>> getServiceList(String service) throws Exception {
		return dao.getServiceList(service);
	}

	@Override
	public List<Map<String, Object>> getTramiteList(String service) throws Exception {
		return dao.getTramiteList(service);
	}

	@Override
	public List<Map<String, Object>> insertaArchivos(final Map<String, Object> data) throws Exception {
		final List<Map<String, Object>> out = new ArrayList<>();
		final MultipartFile[] originFile = (MultipartFile[]) data.get("originfile");
		String retrId = (String) data.get("retr");
		String tiar = (String) data.get("tiar");
		String servicio = (String) data.get("servicio");
		String rutaTemporal = creaDirectorioTemporal(retrId);
		for(MultipartFile file : originFile) {
			String nombreOriginal = file.getOriginalFilename();
			String nombreInterno = generaNombreFisico(nombreOriginal, tiar);
			final Map<String, Object> daoRequest = new HashMap<>();
			mueveArchivoTemporal(file, rutaTemporal, nombreInterno);
			daoRequest.put("pRetrId", retrId);
			daoRequest.put("pServicio", servicio);
			daoRequest.put("pTiarCod", tiar);
			daoRequest.put("pNomArchOriginal", nombreOriginal);
			daoRequest.put("pNomArchInterno", appender(nombreInterno, _PDF));
			daoRequest.put("pObs", OK);
			out.add(dao.crearDetalle(daoRequest));
		}
		return out;
	}

	@Override
	public Map<String, Object> actualizaRegistro(final Map<String, Object> data) throws Exception {
		final Map<String, Object> oficinaPartesRequest = new HashMap<>();
		oficinaPartesRequest.put("remitente", data.get("usuario"));
		String rut = String.valueOf(data.get("rut")).split("-")[0];
		oficinaPartesRequest.put("rutSolicitante", rut);
		oficinaPartesRequest.put("tipoPersona", data.get("tipoPersona"));
		oficinaPartesRequest.put("codigoTramite", "PTESLR");
		oficinaPartesRequest.put("codigoTipoDocumento", data.get("servicio"));
		oficinaPartesRequest.put("codigoMateria", data.get("servicio"));	
		oficinaPartesRequest.put("glosa", PORTAL_DE_TRAMITES);
		oficinaPartesRequest.put("url", enviroment.getProperty(NUMERO_OP_URL));	
		LOGGER.info("actualizaRegistro oficinaPartesRequest: ");
		LOGGER.info(oficinaPartesRequest);
		final Map<String, Object> oficinaParteResponse = dao.getNumeroOP(oficinaPartesRequest);
		if (oficinaParteResponse.get("numeroIngreso") == null) throw new Exception("Error consiguiendo número de oficina de partes");
		LOGGER.info(appender("actualizaRegistro número op: ", (String)oficinaParteResponse.get("numeroIngreso")));
		String rutaTemporal = enviroment.getProperty(TEMP_PATH);
		String retrId = (String) data.get("retr");
		final StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(rutaTemporal).append(retrId);
		String pathIn = pathBuilder.toString();
		String numeroOperacion = String.valueOf(oficinaParteResponse.get("numeroIngreso"));
		pathBuilder
			.append(SLASH)
			.append(LocalDate.now().format(DateTimeFormatter.ofPattern(YY)))
			.append(S)
			.append(numeroOperacion)
			.append(PRT_PDF);
		String pathOut = pathBuilder.toString();
		concatenaArchivos(pathIn, pathOut);
		mueveConsolidado(pathOut, retrId, numeroOperacion);
		mueveLegales(retrId);
		final Map<String, Object> daoRequest = new HashMap<>();
		String retr = (String)data.get("retr");
		daoRequest.put("retr", retr);
		daoRequest.put("servicio", data.get("servicio"));
		daoRequest.put("tramite", data.get("tipoTramite"));
		daoRequest.put("numeroIngreso", oficinaParteResponse.get("numeroIngreso"));
		daoRequest.put("fechaIngreso", oficinaParteResponse.get("fechaIngreso"));
		daoRequest.put("observacion", data.get("observacion"));
		daoRequest.put("numeroOperacion", numeroOperacion);
		daoRequest.put("fechaOperacion", data.get("fechaOperacion"));
		dao.actualizarTramite(daoRequest);
		sendMail(Integer.valueOf(retr));
		return dao.insertaPreSolicitud(Integer.valueOf(retr));
	}
	
	@Override
	public String sendMail(int retr) throws Exception {
		final Map<String, Object> datosEmail = dao.getDatosEmail(retr);
		if(datosEmail == null) new Exception("No hay datos para el retr: " + retr);
		String htmlContent = replaceMail(enviroment.getProperty(EMAIL_OK), datosEmail);
		String subject = (String)datosEmail.get("subject");
		String from = (String)datosEmail.get("sender");
		final MimeMessage mimeMessage = emailSender.createMimeMessage();
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, MediaType.APPLICATION_JSON_VALUE);		
		mimeMessage.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
		helper.setTo((String)datosEmail.get("emailUsuario"));
		helper.setSubject(subject);
		helper.setFrom(from);
		helper.setBcc(((String)datosEmail.get("bcc")).split(PUNTO_COMA));
		emailSender.send(mimeMessage);
		return SENT;
	}
	
	@Override
	public Map<String, Object> validaOfinaParte(int numero, String fecha) throws Exception {		
		return dao.validaOfinaParte(numero, fecha);
	}
	
	@Override
	public boolean validaToken(String token) throws Exception {
		return dao.validaToken(token).equals(OK);
	}
	
	@Override
	public Map<String, Object> getDatosUsuario(String rut, String tipoPerson) throws Exception {
		return dao.getDatosUsuario(rut, tipoPerson);
	}
	
	private void mueveConsolidado(String pathIn, String retr, String numeroOperacion) throws IOException {
		String nombreConsolidado = appender(LocalDate.now().format(DateTimeFormatter.ofPattern(YY)), S, numeroOperacion, PRT_PDF);
		String rutaTemporal = enviroment.getProperty(TEMP_PATH);
		String rutaConsolidado = enviroment.getProperty(CONSOLIDADO_PATH);
		String finalPath = appender(rutaConsolidado, nombreConsolidado);		
		String consolidado = appender(rutaTemporal, retr, SLASH, nombreConsolidado);
		Files.copy(Paths.get(consolidado), Paths.get(finalPath), StandardCopyOption.REPLACE_EXISTING);
	}
	
	private void mueveLegales(String retr) throws Exception {
		String legalesPath = enviroment.getProperty(LEGALES_PATH);
		String rutaTemporal = enviroment.getProperty(TEMP_PATH);		
		String legalesTemp = appender(rutaTemporal, retr, SLASH);
		final Stream<Path> walk = Files.walk(Paths.get(legalesTemp));
		walk.filter(Files::isRegularFile)
			.filter(x -> x.toString().contains(ANTECEDENTES_LEGALES))
			.map(x -> x.toString())
			.collect(Collectors.toList())
			.forEach(x -> mueveLegales(x, legalesPath));
		walk.close();
	}
	
	private void mueveLegales(String in, String out) {
		try {
			Files.copy(
				Paths.get(in), 
				Paths.get(appender(out, Paths.get(in).toFile().getName())), 
				StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private static String replaceMail(String mensaje, final Map<String, Object> data) {
		return mensaje.replace("<nombreUsuario>", (String)data.get("nombreUsuario"))
			.replace("<tipoDeServicio>", (String)data.get("tipoServicio"))
			.replace("<link>", (String)data.get("link"))
			.replace("<numeroOP>", (String)data.get("numeroOficinaParte"))
			.replace("<fechaOP>", (String)data.get("fechaOficinaParte"));
	}

	private String creaDirectorioTemporal(String retrId) {
		String rutaTemporal = enviroment.getProperty(TEMP_PATH);
		String temporal = appender(rutaTemporal, retrId, SLASH);
		new File(temporal).mkdir();
		return temporal;
	}

	private String generaNombreFisico(String nombre, String tiar) {
		return appender(
			LocalDate.now().format(DateTimeFormatter.ofPattern(YYY_M_MDD)), 
			String.valueOf(new Random().nextInt(UN_MILLON)),
			tiar);
	}

	private void mueveArchivoTemporal(final MultipartFile originFile, String temporal, String nombreInterno) throws IllegalStateException, IOException {	
		originFile.transferTo(
			new File(
				appender(
					temporal,
					SLASH,
					nombreInterno, 
					PUNTO,
					FilenameUtils.getExtension(originFile.getOriginalFilename()))));
	}

	private void concatenaArchivos(String pahtIn, String pathOut) throws IOException, COSVisitorException {
		final PDFMergerUtility pdfUtility = new PDFMergerUtility();
		final Stream<Path> walk = Files.walk(Paths.get(pahtIn));
		walk.filter(Files::isRegularFile)
			.map(x -> x.toString())
			.collect(Collectors.toList())
			.forEach(pdfUtility::addSource);
		walk.close();
		pdfUtility.setDestinationFileName(pathOut);
		pdfUtility.mergeDocuments();
	}

}
