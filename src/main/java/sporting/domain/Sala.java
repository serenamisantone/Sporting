package sporting.domain;

public class Sala {
private Integer id;
private String codice;
private String nome;
private int capienza;

private Specializzazione specializzazione;

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

}
