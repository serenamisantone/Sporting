package sporting.domain;

public class Prenotazione {
private Integer id;
private Lezione lezione;
private Sala sala;
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



}
