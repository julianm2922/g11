package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaHidraulica extends PlantaRenovable {

    private int cantidadTurbinas;
    private double capacidadEmbalse;

    public PlantaHidraulica(String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {
        super(nombre, direccion, capacidadMax, activo, fechaApertura, fuentePrimaria, eficienciaPromedio);
        this.cantidadTurbinas = cantidadTurbinas;
        this.capacidadEmbalse = capacidadEmbalse;
    }

    @Override
    public double cuotaGeneracionEsperada() {
    	
        return capacidadMax * eficienciaPromedio;
    }

    @Override
    public String toString() {
    	return new StringBuilder(super.toString())
    		.append("\n\tHidraulica | Turbinas: ")
    		.append(cantidadTurbinas)
    		.append(" | Capacidad del embalse: ")
    		.append(capacidadEmbalse)
    		.toString();
    }

    public int getCantidadTurbinas() {
        return cantidadTurbinas;
    }

    public double getCapacidadEmbalse() {
        return capacidadEmbalse;
    }
}