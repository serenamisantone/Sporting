package sporting.domain;

import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona {
	private String Email;
	
	private Abbonamento abbonamento;
	private Set<Prenotazione> prenotazioni = new HashSet<Prenotazione>();
	public Abbonamento getAbbonamento() {
		return abbonamento;
	}
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}
	public Set<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(Set<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	}
	
	

