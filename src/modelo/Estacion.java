package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una estación física de micromovilidad en la red urbana de Quito.
 * Demuestra AGREGACIÓN al gestionar una colección de objetos Anclaje.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 1: FIGUEROA VASQUEZ, MIGUEL
 */
public class Estacion {

    // 1. Atributos privados
    private String codigoEstacion;      // ej. "EST-001"
    private String nombre;              // ej. "La Carolina - Naciones Unidas"
    private double latitud;
    private double longitud;
    private String sector;              // "NORTE", "CENTRO", "SUR", "VALLES"
    private boolean intermodalMetro;    // true si conecta con Metro de Quito
    private int capacidadTotal;
    private List<Anclaje> anclajes;
    private int desanclajesUltimos20Min; // Métrica para tasa de variación en hora pico

    // 2. Constructor con parámetros
    public Estacion(String codigoEstacion, String nombre, double latitud, double longitud,
                    String sector, boolean intermodalMetro, int capacidadTotal) {
        setCodigoEstacion(codigoEstacion);
        setNombre(nombre);
        this.latitud = latitud;
        this.longitud = longitud;
        this.sector = (sector != null && !sector.isBlank()) ? sector.trim().toUpperCase() : "NORTE";
        this.intermodalMetro = intermodalMetro;
        setCapacidadTotal(capacidadTotal);

        // Inicializar los anclajes de la estación
        this.anclajes = new ArrayList<>();
        for (int i = 1; i <= this.capacidadTotal; i++) {
            this.anclajes.add(new Anclaje(i));
        }
        this.desanclajesUltimos20Min = 0;
    }

    // 3. Métodos Getter
    public String getCodigoEstacion() {
        return codigoEstacion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getSector() {
        return sector;
    }

    public boolean isIntermodalMetro() {
        return intermodalMetro;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public List<Anclaje> getAnclajes() {
        return anclajes;
    }

    public int getDesanclajesUltimos20Min() {
        return desanclajesUltimos20Min;
    }

    // 4. Métodos Setter con validaciones
    public void setCodigoEstacion(String codigoEstacion) {
        if (codigoEstacion != null && !codigoEstacion.isBlank()) {
            this.codigoEstacion = codigoEstacion.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("El código de estación no puede estar vacío.");
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre.trim();
        } else {
            throw new IllegalArgumentException("El nombre de la estación no puede estar vacío.");
        }
    }

    public void setCapacidadTotal(int capacidadTotal) {
        if (capacidadTotal >= 4) {
            this.capacidadTotal = capacidadTotal;
        } else {
            throw new IllegalArgumentException("La estación debe tener al menos 4 anclajes.");
        }
    }

    public void setDesanclajesUltimos20Min(int desanclajes) {
        if (desanclajes >= 0) {
            this.desanclajesUltimos20Min = desanclajes;
        }
    }

    // 5. Métodos de Negocio y Conteo
    public int getCantidadBicicletasAncladas() {
        int conteo = 0;
        for (Anclaje a : anclajes) {
            if ("OCUPADO".equalsIgnoreCase(a.getEstado())) {
                conteo++;
            }
        }
        return conteo;
    }

    public int getCantidadAnclajesLibres() {
        int conteo = 0;
        for (Anclaje a : anclajes) {
            if ("LIBRE".equalsIgnoreCase(a.getEstado())) {
                conteo++;
            }
        }
        return conteo;
    }

    public int getCantidadAnclajesOperativos() {
        int conteo = 0;
        for (Anclaje a : anclajes) {
            if (a.estaOperativo()) {
                conteo++;
            }
        }
        return conteo;
    }

    // Algoritmo RF-1.2: Cálculo del Índice de Saturación y Diagnóstico Operativo
    public double calcularIndiceSaturacion() {
        int operativos = getCantidadAnclajesOperativos();
        if (operativos == 0) return 0.0;
        return ((double) getCantidadBicicletasAncladas() / operativos) * 100.0;
    }

    public String clasificarEstadoOperativo() {
        double sat = calcularIndiceSaturacion();
        if (sat < 15.0) {
            return "DEFICITARIA_CRITICA";
        } else if (sat > 85.0) {
            return "SATURADA_BLOQUEO";
        } else {
            return "EQUILIBRADA";
        }
    }

    public boolean tieneAlertaDesabastecimientoPico(boolean esHoraPico) {
        double tasaPorMinuto = this.desanclajesUltimos20Min / 20.0;
        return esHoraPico && (tasaPorMinuto >= 0.8) && (calcularIndiceSaturacion() < 30.0);
    }

    public Anclaje buscarAnclajeLibre() {
        for (Anclaje a : anclajes) {
            if ("LIBRE".equalsIgnoreCase(a.getEstado())) {
                return a;
            }
        }
        return null;
    }

    public Anclaje buscarAnclajeConBici(String idBicicleta) {
        for (Anclaje a : anclajes) {
            if (idBicicleta.equalsIgnoreCase(a.getIdBicicletaAnclada())) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Ocupación: %d/%d (%.1f%%) - Estado: %s",
                codigoEstacion, nombre, (intermodalMetro ? "METRO" : "BUS"),
                getCantidadBicicletasAncladas(), getCantidadAnclajesOperativos(),
                calcularIndiceSaturacion(), clasificarEstadoOperativo());
    }
}
