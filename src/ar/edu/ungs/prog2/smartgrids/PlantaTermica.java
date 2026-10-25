package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaTermica extends PlantaNoRenovable {

    private String tipoCombustible;
    private double objetivoEmisionesCo2PorHora;
    private double capacidadAlmacenamientoCombustible;

    public PlantaTermica(String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {
        super(nombre, direccion, capacidadMax, activo, fechaApertura);
        this.tipoCombustible = tipoCombustible;
        this.objetivoEmisionesCo2PorHora = objetivoEmisionesCo2PorHora;
        this.capacidadAlmacenamientoCombustible = capacidadAlmacenamientoCombustible;
    }

    @Override
    public double cuotaGeneracionEsperada() {
        return capacidadMax / 2;
    }

    @Override
    public String toString() {
    	return new StringBuilder(super.toString())
    		.append("\n\tTermica | Combustible: ")
    		.append(tipoCombustible)
    		.append(" | Objetivo CO2 por hora: ")
    		.append(objetivoEmisionesCo2PorHora)
    		.append(" | Almacenamiento de combustible: ")
    		.append(capacidadAlmacenamientoCombustible)
    		.toString();
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
}
