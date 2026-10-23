package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class PlantaTermica extends PlantaNoRenovable {

    private String tipoCombustible;
    private double objetivoEmisionesCo2PorHora;
    private double capacidadAlmacenamientoCombustible;

    public PlantaTermica(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDate fechaApertura, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura);
        this.tipoCombustible = tipoCombustible;
        this.objetivoEmisionesCo2PorHora = objetivoEmisionesCo2PorHora;
        this.capacidadAlmacenamientoCombustible = capacidadAlmacenamientoCombustible;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public double getObjetivoEmisionesCo2PorHora() {
        return objetivoEmisionesCo2PorHora;
    }

    public double getCapacidadAlmacenamientoCombustible() {
        return capacidadAlmacenamientoCombustible;
    }

    @Override
    public HashMap<LocalDate, HashMap<LocalDateTime, Double>> generarHistogramaCumplimiento(LocalDate fechaInicio, LocalDate fechaFin) {
        HashMap<LocalDate, HashMap<LocalDateTime, Double>> histograma = new HashMap<>();
        // TODO: implementar
        return histograma;
    }

    @Override
    public double cuotaGeneracionEsperada() {
        // TODO: implementar
        return 0;
    }
}
