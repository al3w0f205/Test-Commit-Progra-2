package negocio;

import modelo.Anclaje;
import modelo.Estacion;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para la administración de estaciones y análisis de saturación.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 1: FIGUEROA VASQUEZ, MIGUEL
 */
public class GestorEstaciones {

    private List<Estacion> listaEstaciones;

    public GestorEstaciones() {
        this.listaEstaciones = new ArrayList<>();
    }

    public void registrarEstacion(Estacion estacion) {
        if (estacion == null) {
            throw new IllegalArgumentException("La estación no puede ser nula.");
        }
        if (buscarEstacion(estacion.getCodigoEstacion()) != null) {
            throw new IllegalArgumentException("Ya existe una estación con el código: " + estacion.getCodigoEstacion());
        }
        this.listaEstaciones.add(estacion);
    }

    public Estacion buscarEstacion(String codigo) {
        if (codigo == null) return null;
        for (Estacion e : listaEstaciones) {
            if (codigo.equalsIgnoreCase(e.getCodigoEstacion())) {
                return e;
            }
        }
        return null;
    }

    public List<Estacion> getListaEstaciones() {
        return new ArrayList<>(listaEstaciones);
    }

    public List<Estacion> obtenerEstacionesSaturadas() {
        List<Estacion> saturadas = new ArrayList<>();
        for (Estacion e : listaEstaciones) {
            if ("SATURADA_BLOQUEO".equalsIgnoreCase(e.clasificarEstadoOperativo())) {
                saturadas.add(e);
            }
        }
        return saturadas;
    }

    public List<Estacion> obtenerEstacionesDeficitarias() {
        List<Estacion> deficitarias = new ArrayList<>();
        for (Estacion e : listaEstaciones) {
            if ("DEFICITARIA_CRITICA".equalsIgnoreCase(e.clasificarEstadoOperativo())) {
                deficitarias.add(e);
            }
        }
        return deficitarias;
    }

    public boolean anclarBicicletaEnEstacion(String codigoEstacion, String idBicicleta) {
        Estacion est = buscarEstacion(codigoEstacion);
        if (est == null) {
            throw new IllegalArgumentException("Estación no encontrada: " + codigoEstacion);
        }
        Anclaje libre = est.buscarAnclajeLibre();
        if (libre != null) {
            libre.anclarBicicleta(idBicicleta);
            return true;
        }
        return false;
    }

    public String desanclarBicicletaDeEstacion(String codigoEstacion, String idBicicleta) {
        Estacion est = buscarEstacion(codigoEstacion);
        if (est == null) return null;
        Anclaje dock = est.buscarAnclajeConBici(idBicicleta);
        if (dock != null) {
            return dock.liberarBicicleta();
        }
        return null;
    }
}
