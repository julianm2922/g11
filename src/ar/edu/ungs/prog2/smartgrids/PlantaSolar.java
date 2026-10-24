package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class PlantaSolar extends PlantaRenovable {
    private double superficieCubierta;
    private int cantidadPaneles;
    private double horasRadiacion;

    public PlantaSolar(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacion) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura, fuentePrimaria, eficienciaPromedio);
        
        this.superficieCubierta = superficieCubierta;
        this.cantidadPaneles = cantidadPaneles;
        this.horasRadiacion = horasRadiacion;
    }

    public double getSuperficieCubierta() {
        return superficieCubierta;
    }

    public int getCantidadPaneles() {
        return cantidadPaneles;
    }

    public double getHorasRadiacion() {
        return horasRadiacion;
    }

    @Override
    public HashMap<LocalDate, HashMap<LocalDateTime, Double>> generarHistogramaCumplimiento(LocalDate fechaInicio, LocalDate fechaFin) {
    	HashMap<LocalDate, HashMap<LocalDateTime, Double>> histograma = new HashMap<LocalDate, HashMap<LocalDateTime, Double>>();
        // TODO: implementar
        return histograma;
    }

    @Override
    public double cuotaGeneracionEsperada() {
        
        return this.capacidadMax * this.eficienciaPromedio * this.horasRadiacion / 24;
    }
}