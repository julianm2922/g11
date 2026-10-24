package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Planta {

    private int id;
    private String nombre;
    private String direccion;
    protected double capacidadMax;
    private boolean activo;
    private LocalDateTime fechaApertura;
    private ArrayList<RegistroMonitoreo> registros;

    public Planta(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMax = capacidadMax;
        this.activo = activo;
        this.fechaApertura = fechaApertura;
        this.registros = new ArrayList<RegistroMonitoreo>();
    }

    public abstract double cuotaGeneracionEsperada();

    public abstract HashMap<LocalDate, HashMap<LocalDateTime, Double>> generarHistogramaCumplimiento(LocalDate fechaInicio, LocalDate fechaFin);

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getCapacidadMax() {
        return capacidadMax;
    }

    public boolean estaActiva() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public List<RegistroMonitoreo> getRegistros() {
        return registros;
    }

    public void agregarRegistro(RegistroMonitoreo registro) {
    	if (registro.getFechaHora().isBefore(fechaApertura))
    		throw new RuntimeException("El registro no puede ser previo a la fecha de apretura de la planta");
    	
    	for (RegistroMonitoreo reg : this.registros) {
    		if (reg.getFechaHora().equals(registro.getFechaHora()))
    			throw new RuntimeException("Ya existe un registro para esa hora y fecha.");
    	}
    	
        registros.add(registro);
    }
    
    public double calcularTotalGenerado() {
    	double total = 0;
    	for (RegistroMonitoreo reg : this.registros) {
    		total += reg.getEnergiaGenerada();
    	}
    	
    	return total;
    }
    
    public int limpiarMonitoreosSinDatos() {
    	int borrados = 0;
    	Iterator<RegistroMonitoreo> it = this.registros.iterator();
    	
    	while(it.hasNext()) {
    		RegistroMonitoreo reg = it.next();
    		if (reg.getEnergiaGenerada() == 0 && reg.getCantidadIncidencias() == 0) {
    			it.remove();
    			borrados++;
    		}
    	}
    	return borrados;
    }
}
