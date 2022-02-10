package sporting.domain;

public class PersonalTrainer extends Persona {
	private Specializzazione specializzazione;
	private String descrizione;

	public Specializzazione getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(Specializzazione specializzazione) {
		this.specializzazione = specializzazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
