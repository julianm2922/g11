package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreo {

	double energiaGenerada;
	int cantidadIncidencias;
	int idRegistro;
	int idPlanta;
	LocalDateTime fechaHora;

	public RegistroMonitoreo(double energiaGenerada , int cantidadIncidencias, int idRegistro, int idPlanta, LocalDateTime fechaHora) {
		if (energiaGenerada < 0 || cantidadIncidencias < 0) {
			throw new RuntimeException("La energía generada y las incidencias no pueden ser negativas.");
		}
		if (fechaHora == null) {
			throw new RuntimeException("La fecha y hora no puede ser nula.");
		}
		
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
	
	public boolean satisfaceCumplimiento(Planta planta) {
		return getEnergiaGenerada() >= planta.cuotaGeneracionEsperada();
	}
}
