package cl.subtel.request;

public class SaveUser {

	private String rutUsuario;
	private String nombreUsuario;
	private String email;
	private String tipoPersonalidad;
	private String rutSolicitante;
	private String rutDvSolicitante;
	private String razonSocialSolicitante;
	private String direccionSolicitante;
	private int codigoComuna;

	public String getRutUsuario() {
		return rutUsuario;
	}

	public void setRutUsuario(String rutUsuario) {
		this.rutUsuario = rutUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipoPersonalidad() {
		return tipoPersonalidad;
	}

	public void setTipoPersonalidad(String tipoPersonalidad) {
		this.tipoPersonalidad = tipoPersonalidad;
	}

	public String getRutSolicitante() {
		return rutSolicitante;
	}

	public void setRutSolicitante(String rutSolicitante) {
		this.rutSolicitante = rutSolicitante;
	}

	public String getRutDvSolicitante() {
		return rutDvSolicitante;
	}

	public void setRutDvSolicitante(String rutDvSolicitante) {
		this.rutDvSolicitante = rutDvSolicitante;
	}

	public String getRazonSocialSolicitante() {
		return razonSocialSolicitante;
	}

	public void setRazonSocialSolicitante(String razonSocialSolicitante) {
		this.razonSocialSolicitante = razonSocialSolicitante;
	}

	public String getDireccionSolicitante() {
		return direccionSolicitante;
	}

	public void setDireccionSolicitante(String direccionSolicitante) {
		this.direccionSolicitante = direccionSolicitante;
	}

	public int getCodigoComuna() {
		return codigoComuna;
	}

	public void setCodigoComuna(int codigoComuna) {
		this.codigoComuna = codigoComuna;
	}

}
