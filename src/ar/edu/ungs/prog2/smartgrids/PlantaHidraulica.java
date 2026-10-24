package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class PlantaHidraulica extends PlantaRenovable {

    private int cantidadTurbinas;
    private double capacidadEmbalse;

    public PlantaHidraulica(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura, fuentePrimaria, eficienciaPromedio);
        this.cantidadTurbinas = cantidadTurbinas;
        this.capacidadEmbalse = capacidadEmbalse;
    }

    public int getCantidadTurbinas() {
        return cantidadTurbinas;
    }

    public double getCapacidadEmbalse() {
        return capacidadEmbalse;
    }

    @Override
    public HashMap<LocalDate, HashMap<LocalDateTime, Double>> generarHistogramaCumplimiento(LocalDate fechaInicio, LocalDate fechaFin) {
        HashMap<LocalDate, HashMap<LocalDateTime, Double>> histograma = new HashMap<>();
        // TODO: implementar según la consigna (recorrer getMonitoreo() y filtrar por fechas)
        return histograma;
    }

    @Override
    public double cuotaGeneracionEsperada() {
    	
        return capacidadMax * eficienciaPromedio;
    }
}