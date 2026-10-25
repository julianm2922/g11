package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaNoRenovable extends Planta {

    public PlantaNoRenovable(String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura) {
        super(nombre, direccion, capacidadMax, activo, fechaApertura);
    }
    
    @Override
    public boolean esRenovable() {
    	return false;
    }
}
