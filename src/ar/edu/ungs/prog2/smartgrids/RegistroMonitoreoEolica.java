package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreoEolica extends RegistroMonitoreo{

	double velocidadVientoPromedio;
	
	public RegistroMonitoreoEolica(double energiaGenerada, int cantidadIncidencias, int idRegistro, int idPlanta, double velocidadVientoPromedio, LocalDateTime fechaHora) {
		super(energiaGenerada, cantidadIncidencias, idRegistro, idPlanta, fechaHora);

		if (velocidadVientoPromedio < 0) {
			throw new RuntimeException("La velocidad del viento no puede ser negativa.");
		}
		
		this.velocidadVientoPromedio = velocidadVientoPromedio;
	}
	
	public double getVelocidadVientoPromedio() {
		return velocidadVientoPromedio;
	}
}