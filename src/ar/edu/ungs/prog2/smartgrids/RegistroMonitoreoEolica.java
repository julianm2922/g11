package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreoEolica extends RegistroMonitoreo{

	double velocidadVientoPromedio;
	
	public RegistroMonitoreoEolica(double energiaGenerada, int cantidadIncidencias, double velocidadVientoPromedio, LocalDateTime fechaHora) {
		super(energiaGenerada, cantidadIncidencias, fechaHora);

		if (velocidadVientoPromedio < 0) {
			throw new RuntimeException("La velocidad del viento no puede ser negativa.");
		}
		
		this.velocidadVientoPromedio = velocidadVientoPromedio;
	}

	@Override
	public String toString() {
		return new StringBuilder(super.toString())
			.append(" | Viento promedio: ")
			.append(velocidadVientoPromedio)
			.toString();
	}

	public double getVelocidadVientoPromedio() {
		return velocidadVientoPromedio;
	}
}