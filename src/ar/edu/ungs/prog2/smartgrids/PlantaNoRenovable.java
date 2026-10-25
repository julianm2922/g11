package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public abstract class PlantaNoRenovable extends Planta {

    public PlantaNoRenovable(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura);
    }
    
    @Override
    public boolean esRenovable() {
    	return false;
    }
}
