package sporting.domain;

public class Abbonamento {
private Integer id;
private String codice;
private String nome;
private int durata; //in giorni 
private Double prezzo;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public int getDurata() {
	return durata;
}
public void setDurata(int durata) {
	this.durata = durata;
}
public Double getPrezzo() {
	return prezzo;
}
public void setPrezzo(Double prezzo) {
	this.prezzo = prezzo;
}
public String getCodice() {
	return codice;
}
public void setCodice(String codice) {
	this.codice = codice;
}
}
