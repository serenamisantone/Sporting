package sporting.domain;

public class Sala {
private Integer id;
private String codice;

private int capienza;

private Specializzazione specializzazione;
private PersonalTrainer trainer;
public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getCodice() {
	return codice;
}

public void setCodice(String codice) {
	this.codice = codice;
}



public int getCapienza() {
	return capienza;
}

public void setCapienza(int capienza) {
	this.capienza = capienza;
}

public Specializzazione getSpecializzazione() {
	return specializzazione;
}

public void setSpecializzazione(Specializzazione specializzazione) {
	this.specializzazione = specializzazione;
}

public PersonalTrainer getTrainer() {
	return trainer;
}

public void setTrainer(PersonalTrainer trainer) {
	this.trainer = trainer;
}

}
