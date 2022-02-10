package sporting.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Lezione {
private Integer id;
private String codice;
private String nome;
private LocalDate data;
private LocalTime orarioInizio; 

private PersonalTrainer personalTrainer;
public PersonalTrainer getPersonalTrainer() {
	return personalTrainer;
}

public void setPersonalTrainer(PersonalTrainer personalTrainer) {
	this.personalTrainer = personalTrainer;
}

private Set<Cliente> partecipanti = new HashSet<Cliente>();

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

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public LocalDate getData() {
	return data;
}

public void setData(LocalDate data) {
	this.data = data;
}

public LocalTime getOrarioInizio() {
	return orarioInizio;
}

public void setOrarioInizio(LocalTime orarioInizio) {
	this.orarioInizio = orarioInizio;
}

public Set<Cliente> getPartecipanti() {
	return partecipanti;
}

public void setPartecipanti(Set<Cliente> partecipanti) {
	this.partecipanti = partecipanti;
}
}
