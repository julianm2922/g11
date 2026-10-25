package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDateTime fechaDesactivacion;
    private LocalDateTime fechaReactivacion;

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

    public int[] generarHistogramaCumplimiento(String fechaDesde, String fechaHasta) {
    	LocalDate desde = LocalDate.parse(fechaDesde);
    	LocalDate hasta = LocalDate.parse(fechaHasta);
    	if (desde.isAfter(hasta)) throw new RuntimeException("La fecha desde no puede ser posterior a la fecha hasta.");
    	
    	int[] histograma = new int[24];
    	
    	for (RegistroMonitoreo reg : registros) {
    		LocalDateTime fechaHoraRegistro = reg.getFechaHora();
    		if (fechaHoraRegistro.toLocalDate().isAfter(hasta) || fechaHoraRegistro.toLocalDate().isBefore(desde)) continue;
    		
    		if (reg.satisfaceCumplimiento(this)) {
    			histograma[fechaHoraRegistro.getHour()] += 1;
    		}
    		
    	}
    	return histograma;
    }
    
    public abstract boolean esRenovable();

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
    	
        if (!estaInactivaEn(registro.getFechaHora())) {
            throw new RuntimeException("La planta no está activa en este momento.");
        }

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

    // verifica si alguna vez se llegó a cumplir la cuota
    public boolean cumplioCuota() {
    	boolean cumple = false;
    	for (RegistroMonitoreo reg : registros) {
    		cumple = cumple || reg.satisfaceCumplimiento(this);
     	}
    	
    	return cumple;
    }
    
    @Override
    public String toString() {
    	return new StringBuilder(nombre)
			.append(" (").append(activo ? "ACTIVA" : "INACTIVA").append("): ")
			.append(fechaISOcompleta(fechaApertura))
			.toString();
    }
    
    public void setActivo(boolean activo, LocalDateTime fechaHora) {
    	if (fechaHora.isBefore(this.fechaApertura)) {
    		throw new RuntimeException("La fecha no puede ser anterior a la fecha de apertura");
    	}
    	
    	this.activo = activo;
    	if(!activo) {
    		this.fechaDesactivacion = fechaHora;
    		this.fechaReactivacion = null;
    	} else {
    		this.fechaReactivacion = fechaHora;
    	}
    }
    public boolean estaInactivaEn(LocalDateTime fechaHora) {
    	if (this.fechaDesactivacion == null || fechaHora.isBefore(this.fechaDesactivacion)) {
    		return false;
    	}
    	if (this.fechaReactivacion == null || fechaHora.isBefore(this.fechaReactivacion)) {
    		return true;
    	}
    	return false;
    }
    
    // la funcion existe para PlantaEolica, que requiere reportar horas sin velocidad mínima. se agrega aca y con un nombre genérico para no depender de "instanceof" o de algun otro "truco"
    public String reporteOtrosIncumplimientos() {
    	return "";
    }

    /** METODOS PRIVADOS */

    // por defecto el toString de LocalDateTime parece no mostrar los segundos en la hora y hace que falle el test registraYConsultaPlanes
    private String fechaISOcompleta(LocalDateTime fecha) {
    	return fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
