package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

// TODO: Implementar monitoreo 
public abstract class Planta {

    private int id;
    private String nombre;
    private String direccion;
    private double capacidadMax;
    private boolean activo;
    private LocalDate fechaApertura;
    // private HashMap<Integer, RegistroMonitoreo> monitoreo;

    public Planta(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDate fechaApertura) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMax = capacidadMax;
        this.activo = activo;
        this.fechaApertura = fechaApertura;
        // this.monitoreo = new HashMap<>();
    }

    // Métodos abstractos 
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

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    /*
    public HashMap<Integer, RegistroMonitoreo> getMonitoreo() {
        return monitoreo;
    }

    public void addRegistro(RegistroMonitoreo registro) {
        monitoreo.put(registro.getId(), registro);
    }
    */
}