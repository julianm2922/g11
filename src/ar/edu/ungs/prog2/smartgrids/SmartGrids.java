package ar.edu.ungs.prog2.smartgrids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//holamundo
public class SmartGrids implements ISmartGrids {
	private Map<String, Planta> plantas;
	private int proximoIdPlanta;
	private int proximoIdRegistro;

	public SmartGrids() {
		this.plantas = new LinkedHashMap<String, Planta>();
		this.proximoIdPlanta = 1;
		this.proximoIdRegistro = 1;
	}

	@Override
	public void registrarPlantaSolar(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, double superficieCubierta, int cantidadPaneles, double horasRadiacionDiaria) {
		validarNombrePlanta(nombre);

		Planta planta = new PlantaSolar(proximoIdPlanta, nombre, direccion, capacidadMaxima, true, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, superficieCubierta, cantidadPaneles, horasRadiacionDiaria);
		plantas.put(nombre, planta);
		proximoIdPlanta++;
	}

	@Override
	public void registrarPlantaEolica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadAerogeneradores, double alturaTorres, double velocidadMinimaViento) {
		validarNombrePlanta(nombre);

		Planta planta = new PlantaEolica(proximoIdPlanta, nombre, direccion, capacidadMaxima, true, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadAerogeneradores, alturaTorres, velocidadMinimaViento);
		plantas.put(nombre, planta);
		proximoIdPlanta++;
	}

	@Override
	public void registrarPlantaHidraulica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String fuentePrimaria, double eficienciaPromedio, int cantidadTurbinas, double capacidadEmbalse) {
		validarNombrePlanta(nombre);

		Planta planta = new PlantaHidraulica(proximoIdPlanta, nombre, direccion, capacidadMaxima, true, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), fuentePrimaria, eficienciaPromedio, cantidadTurbinas, capacidadEmbalse);
		plantas.put(nombre, planta);
		proximoIdPlanta++;
	}

	@Override
	public void registrarPlantaTermica(String nombre, String direccion, double capacidadMaxima, String fechaHoraPuestaEnFuncionamiento, String tipoCombustible, double objetivoEmisionesCo2PorHora, double capacidadAlmacenamientoCombustible) {
		validarNombrePlanta(nombre);
		
		Planta planta = new PlantaTermica(proximoIdPlanta, nombre, direccion, capacidadMaxima, true, LocalDateTime.parse(fechaHoraPuestaEnFuncionamiento), tipoCombustible, objetivoEmisionesCo2PorHora, capacidadAlmacenamientoCombustible);
		plantas.put(nombre, planta);
		proximoIdPlanta++;
	}

	private void validarNombrePlanta(String nombre) {
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("El nombre de la planta es obligatorio");
		if (plantas.containsKey(nombre))
			throw new IllegalArgumentException("Ya existe una planta con el nombre " + nombre);
	}

	private Planta buscarPlanta(String nombre) {
		Planta planta = plantas.get(nombre);
		if (planta == null)
			throw new RuntimeException("No existe la planta " + nombre);
		return planta;
	}

	private Planta buscarPlanta(String nombre, boolean soloActiva) {
		Planta planta = buscarPlanta(nombre);
		
		if (!planta.estaActiva() && soloActiva)
			throw new RuntimeException("La planta " + nombre + " esta inactiva");
		return planta;
	}

	@Override
	public List<String> consultarPlantas() {
		ArrayList<String> lista = new ArrayList<>();
		for (Map.Entry<String, Planta> tupla : plantas.entrySet()) {
			Planta planta = tupla.getValue();
			lista.add(planta.toString());
		}
		return lista;
	}

	@Override
	public double capacidadMinimaCuota(String nombre) {
		Planta planta = buscarPlanta(nombre);
		
		return planta.cuotaGeneracionEsperada();
	}

	@Override
	public void activarPlanta(String nombre, String fechaHora) {
		LocalDateTime fh = LocalDateTime.parse(fechaHora);
		buscarPlanta(nombre).setActivo(true, fh);
	}

	@Override
	public void desactivarPlanta(String nombre, String fechaHora) {
		LocalDateTime fh = LocalDateTime.parse(fechaHora);
		buscarPlanta(nombre).setActivo(false, fh);
	}

	@Override
	public boolean estaActiva(String nombre) {
		return buscarPlanta(nombre).estaActiva();
	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias) {
		Planta planta = buscarPlanta(nombre, true);
		if (planta instanceof PlantaEolica || planta instanceof PlantaTermica)
			throw new RuntimeException("La planta " + nombre + " requiere datos de monitoreo adicionales");
		planta.agregarRegistro(new RegistroMonitoreo(energiaGenerada, incidencias, proximoIdRegistro, planta.getId(), LocalDateTime.parse(fechaHora)));
		proximoIdRegistro++;
	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double velocidadViento) {
		Planta planta = buscarPlanta(nombre, true);
		if (!(planta instanceof PlantaEolica))
			throw new RuntimeException("La planta " + nombre + " no es eolica");
		
		planta.agregarRegistro(new RegistroMonitoreoEolica(energiaGenerada, incidencias, proximoIdRegistro, planta.getId(), velocidadViento, LocalDateTime.parse(fechaHora)));
		proximoIdRegistro++;
	}

	@Override
	public void registrarMonitoreo(String nombre, String fechaHora, double energiaGenerada, int incidencias, double co2Emitido, double combustibleUtilizado) {
		Planta planta = buscarPlanta(nombre, true);
		if (!(planta instanceof PlantaTermica))
			throw new RuntimeException("La planta " + nombre + " no es termica");
		planta.agregarRegistro(new RegistroMonitoreoTermica(energiaGenerada, incidencias, proximoIdRegistro, planta.getId(), co2Emitido, combustibleUtilizado, LocalDateTime.parse(fechaHora)));
		proximoIdRegistro++;
	}

	@Override
	public double totalGenerado(String nombre) {
		Planta planta = buscarPlanta(nombre);
		// TODO Auto-generated method stub
		return planta.calcularTotalGenerado();
	}

	@Override
	public int[] incidenciasPorHora(String nombre) {
		Planta planta = buscarPlanta(nombre);
		
		int[] incidencias = new int[24];
		for (RegistroMonitoreo reg : planta.getRegistros()) {
			int hora = reg.getFechaHora().getHour();
			
			incidencias[hora] += reg.getCantidadIncidencias();
		}
		
		return incidencias;
	}

	@Override
	public int[] histogramaCumplimiento(String nombre, String fechaDesde, String fechaHasta) {
		Planta planta = buscarPlanta(nombre);
		
		return planta.generarHistogramaCumplimiento(fechaDesde, fechaHasta);
	}

	@Override
	public Map<String, Integer> plantasRenovablesPorFuente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> plantasQueNuncaCumplieronCuota() {
		ArrayList<String> nombresPlantas = new ArrayList<String>();
		
		for (Map.Entry<String, Planta> tupla : plantas.entrySet()) {
			Planta planta = tupla.getValue();
			
			if (!planta.cumplioCuota()) {
				nombresPlantas.add(tupla.getKey());
			}
		}
		return nombresPlantas;
	}

	@Override
	public String horasSinVelocidadMinimaPorPlantaEolica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int limpiarMonitoreosSinDatos() {
		// TODO Auto-generated method stub
		int totalBorrados = 0;
		for (Planta planta : plantas.values()) {
			totalBorrados += planta.limpiarMonitoreosSinDatos();
		}
		return totalBorrados;
	}

	@Override
	public List<String> plantasOrdenadasPorEnergiaGenerada() {
		// TODO Auto-generated method stub
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>(plantas.values());
		
		for(int i = 0; i < listaPlantas.size()-1; i++) {
			for(int j = i + 1; j < listaPlantas.size(); j++) {
				Planta p1 = listaPlantas.get(i);
				Planta p2 = listaPlantas.get(j);
				
				if(p2.calcularTotalGenerado() > p1.calcularTotalGenerado()) {
					listaPlantas.set(i, p2);
					listaPlantas.set(j, p1);
				}
			}
		}
		
		ArrayList<String> resultado = new ArrayList<String>();
		for (Planta planta : listaPlantas) {
			resultado.add(planta.getNombre() + ": " + planta.calcularTotalGenerado());
		}
		return resultado;
	}

	@Override
	public List<String> plantasInactivasEn(String fechaHora) {
		// TODO Auto-generated method stub
		LocalDateTime fh = LocalDateTime.parse(fechaHora);
		ArrayList<String> inactivas = new ArrayList<String>();
		
		for (Map.Entry<String, Planta> tupla : plantas.entrySet()) {
			Planta planta = tupla.getValue();
			
			if (planta.estaInactivaEn(fh)) {
				inactivas.add(planta.getNombre());
			}
		}
		return inactivas;
	}

}
