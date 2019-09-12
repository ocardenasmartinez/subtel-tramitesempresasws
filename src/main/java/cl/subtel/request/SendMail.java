package cl.subtel.request;

public class SendMail {
	private String nombreUsuario;
	private String tipoDeServicio;
	private String link;
	private String numeroOP;
	private String fechaOP;

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getTipoDeServicio() {
		return tipoDeServicio;
	}

	public void setTipoDeServicio(String tipoDeServicio) {
		this.tipoDeServicio = tipoDeServicio;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getNumeroOP() {
		return numeroOP;
	}

	public void setNumeroOP(String numeroOP) {
		this.numeroOP = numeroOP;
	}

	public String getFechaOP() {
		return fechaOP;
	}

	public void setFechaOP(String fechaOP) {
		this.fechaOP = fechaOP;
	}

}
