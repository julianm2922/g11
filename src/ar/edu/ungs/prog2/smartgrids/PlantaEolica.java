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
    
    @Override
    public String toString() {
    	return new StringBuilder(super.toString())
    		.append("\n\tEolica | Aerogeneradores: ")
    		.append(cantidadAerogeneradores)
    		.append(" | Altura de torres: ")
    		.append(alturaTorres)
    		.append(" | Velocidad minima de viento: ")
    		.append(velocidadMinimaViento)
    		.toString();
    }

    @Override
    public String reporteOtrosIncumplimientos() {
    	return reporteHorasSinVelocidadMinima();
    }
    
    /** METODOS PRIVADOS */

    // interpretamos que en cada hora sin cumplimiento deberia ir la maxima velocidad alcanzada (la consigna no lo especifica)
    private String reporteHorasSinVelocidadMinima() {
    	double[] velocidadesMaximas = velocidadesMaximasPorHora();
    	StringBuilder reporte = new StringBuilder();
    	 
    	// Encabezado: "	- <Nombre de la planta>: <Velocidad> m/s <salto de linea>"
    	reporte.append("  - ") .append(getNombre()).append(": ")
    	       .append(formatearVelocidad(velocidadMinimaViento))
    	       .append(" m/s\n");										
    	
    	// Por hora: "		<Hora>: <Velocidad> m/s<salto de linea>"
    	for (int hora = 0; hora < velocidadesMaximas.length; hora++) {
    		if (!nuncaAlcanzoVelocidadMinima(velocidadesMaximas[hora])) continue;
    		reporte.append("      ").append(agregarCero(hora)).append(": ")
    		       .append(formatearVelocidad(velocidadesMaximas[hora]))
    		       .append(" m/s\n");
    	}

    	return reporte.toString();
    }

    private boolean nuncaAlcanzoVelocidadMinima(double velocidadMaxima) {
    	return velocidadMaxima >= 0 && velocidadMaxima < velocidadMinimaViento;
    }
    
    
    // si es 7, devuelve 07; si es 12, devuelve 12
    private String agregarCero(int n) {
    	return String.format("%02d", n);
    }

    private String formatearVelocidad(double velocidad) {
    	if (velocidad == (int) velocidad) return String.valueOf((int) velocidad);
    	return String.valueOf(velocidad);
    }

    private double[] velocidadesMaximasPorHora() {
    	double[] velocidadesMaximas = new double[24];
    	for (int hora = 0; hora < velocidadesMaximas.length; hora++) {
    		velocidadesMaximas[hora] = -1;
    	}

    	for (RegistroMonitoreo reg : getRegistros()) {
    		RegistroMonitoreoEolica regEolica = (RegistroMonitoreoEolica) reg;
    		int horaReg = regEolica.getFechaHora().getHour();
    		if (regEolica.getVelocidadVientoPromedio() > velocidadesMaximas[horaReg])
    			velocidadesMaximas[horaReg] = regEolica.getVelocidadVientoPromedio();
    	}
    	return velocidadesMaximas;
    }
}