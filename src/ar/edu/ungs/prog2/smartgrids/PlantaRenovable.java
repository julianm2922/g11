package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaRenovable extends Planta {

    private String fuentePrimaria;
    protected double eficienciaPromedio;

    public PlantaRenovable(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String fuentePrimaria, double eficienciaPromedio) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura);
        this.fuentePrimaria = fuentePrimaria;
        this.eficienciaPromedio = eficienciaPromedio;
    }

    public String getFuentePrimaria() {
        return fuentePrimaria;
    }

    public double getEficienciaPromedio() {
        return eficienciaPromedio;
    }
    
    @Override
    public boolean esRenovable() {
    	return true;
    }

    @Override
    public String toString() {
    	return new StringBuilder(super.toString())
    		.append("\n\tFuente primaria: ")
    		.append(fuentePrimaria)
    		.append(" | Eficiencia promedio: ")
    		.append(eficienciaPromedio)
    		.toString();
    }
}