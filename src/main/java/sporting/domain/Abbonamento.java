package sporting.domain;

public class Abbonamento {
private Integer id;
private String nome;
private int durata; //in giorni 
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
}
