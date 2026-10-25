package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class RegistroMonitoreoTermica extends RegistroMonitoreo{


	double cantidadCO2;
	double cantidadCombustibleUtilizado;
	
	public RegistroMonitoreoTermica(double energiaGenerada, int cantidadIncidencias, int idRegistro, int idPlanta, double cantidadCO2, double cantidadCombustibleUtilizado, LocalDateTime fechaHora) {
		super(energiaGenerada, cantidadIncidencias, idRegistro, idPlanta, fechaHora);

		if (cantidadCO2 < 0 || cantidadCombustibleUtilizado < 0) {
			throw new RuntimeException("El CO2 y el combustible utilizado no pueden ser negativos.");
		}
		this.cantidadCO2 = cantidadCO2;
		this.cantidadCombustibleUtilizado = cantidadCombustibleUtilizado;
	}
	
	public double getCantidadCO2() {
		return cantidadCO2;
	}
	
	public double getCantidadCombustibleUtilizado() {
		return cantidadCombustibleUtilizado;
	}
	
	@Override
	public boolean satisfaceCumplimiento(Planta planta) {
		PlantaTermica plantaTermica = (PlantaTermica) planta;
		return getEnergiaGenerada() >= plantaTermica.cuotaGeneracionEsperada() && getCantidadCO2() < plantaTermica.getObjetivoEmisionesCo2PorHora();
	}
}
