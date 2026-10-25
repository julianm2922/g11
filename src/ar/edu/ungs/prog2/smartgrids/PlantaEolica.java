package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDateTime;

public class PlantaEolica extends PlantaRenovable {
    private int cantidadAerogeneradores;
    private double alturaTorres;
    private double velocidadMinimaViento;

    public PlantaEolica(int id, String nombre, String direccion, double capacidadMax, boolean activo, LocalDateTime fechaApertura, String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
        super(id, nombre, direccion, capacidadMax, activo, fechaApertura, fuentePrimaria, eficienciaPromedio);
        
        this.cantidadAerogeneradores = cantidadAerogeneradores;
        this.alturaTorres = alturaTorres;
        this.velocidadMinimaViento = velocidadMinimaViento;
    }

    public int getCantidadAerogeneradores() {
        return cantidadAerogeneradores;
    }

    public double getAlturaTorres() {
        return alturaTorres;
    }

    public double getVelocidadMinimaViento() {
        return velocidadMinimaViento;
    }

    @Override
    public double cuotaGeneracionEsperada() {
        
        return this.capacidadMax * this.eficienciaPromedio;
    }
}