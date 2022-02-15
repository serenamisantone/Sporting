package sporting.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
	private Integer id;
	private LocalTime orarioInizio;
	private LocalTime orarioFine;
	private LocalDate data;
	private String qrcode;

	private Lezione lezione;
	private Sala sala;
	private Cliente cliente;

	public LocalTime getOrarioInizio() {
		return orarioInizio;
	}

	public void setOrarioInizio(LocalTime orarioInizio) {
		this.orarioInizio = orarioInizio;
	}

	public LocalTime getOrarioFine() {
		return orarioFine;
	}

	public void setOrarioFine(LocalTime oratioFine) {
		this.orarioFine = oratioFine;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Lezione getLezione() {
		return lezione;
	}

	public void setLezione(Lezione lezione) {
		this.lezione = lezione;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

}
