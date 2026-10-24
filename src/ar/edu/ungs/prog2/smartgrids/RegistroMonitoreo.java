package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreo {

	double energiaGenerada;
	int cantidadIncidencias;
	int idRegistro;
	int idPlanta;
	LocalDateTime fechaHora;

	public RegistroMonitoreo(double energiaGenerada , int cantidadIncidencias, int idRegistro, int idPlanta, LocalDateTime fechaHora) {
		this.energiaGenerada = energiaGenerada;
		this.cantidadIncidencias = cantidadIncidencias;
		this.idPlanta = idPlanta;
		this.idRegistro = idRegistro;
		this.fechaHora = fechaHora;
	}
	
	public int getIdRegistro() {
		return idRegistro;
	}

	public int getIdPlanta() {
		return idPlanta;
	}
	
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	
	public double getEnergiaGenerada() {
		return energiaGenerada;
	}
	
	public int getCantidadIncidencias() {
		return cantidadIncidencias;
	}
}
