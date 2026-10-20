package modelo;

/**
 * Representa un vehículo de logística y cuadrilla de rebalanceo en calle.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 5: ROSARIO RODRIGUEZ, MARIA
 */
public class FurgonetaRebalanceo {

    private String placa;
    private String conductor;
    private int capacidadMaximaBicis;
    private int inventarioActualBicis;
    private String sectorAsignado;     // "NORTE", "CENTRO", "SUR", "VALLES"
    private String estadoOperativo;    // "DISPONIBLE", "EN_RUTA", "TALLER"

    public FurgonetaRebalanceo(String placa, String conductor, int capacidadMaxima, String sectorAsignado) {
        setPlaca(placa);
        setConductor(conductor);
        setCapacidadMaximaBicis(capacidadMaxima);
        this.inventarioActualBicis = 0;
        this.sectorAsignado = (sectorAsignado != null) ? sectorAsignado.trim().toUpperCase() : "NORTE";
        this.estadoOperativo = "DISPONIBLE";
    }

    public String getPlaca() {
        return placa;
    }

    public String getConductor() {
        return conductor;
    }

    public int getCapacidadMaximaBicis() {
        return capacidadMaximaBicis;
    }

    public int getInventarioActualBicis() {
        return inventarioActualBicis;
    }

    public String getSectorAsignado() {
        return sectorAsignado;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setPlaca(String placa) {
        if (placa != null && !placa.isBlank()) {
            this.placa = placa.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }
    }

    public void setConductor(String conductor) {
        if (conductor != null && !conductor.isBlank()) {
            this.conductor = conductor.trim();
        } else {
            throw new IllegalArgumentException("El nombre del conductor no puede estar vacío.");
        }
    }

    public void setCapacidadMaximaBicis(int capacidadMaximaBicis) {
        if (capacidadMaximaBicis >= 5) {
            this.capacidadMaximaBicis = capacidadMaximaBicis;
        } else {
            throw new IllegalArgumentException("La capacidad mínima de la furgoneta debe ser de al menos 5 bicis.");
        }
    }

    public void setEstadoOperativo(String estadoOperativo) {
        if (estadoOperativo != null && !estadoOperativo.isBlank()) {
            this.estadoOperativo = estadoOperativo.trim().toUpperCase();
        }
    }

    public void cargarBicicletas(int cantidad) {
        if (cantidad > 0 && (inventarioActualBicis + cantidad) <= capacidadMaximaBicis) {
            this.inventarioActualBicis += cantidad;
        } else {
            throw new IllegalArgumentException("Excede la capacidad de carga máxima de la furgoneta.");
        }
    }

    public void descargarBicicletas(int cantidad) {
        if (cantidad > 0 && cantidad <= inventarioActualBicis) {
            this.inventarioActualBicis -= cantidad;
        } else {
            throw new IllegalArgumentException("No hay suficientes bicicletas cargadas para descargar.");
        }
    }

    public int getEspacioDisponible() {
        return capacidadMaximaBicis - inventarioActualBicis;
    }

    @Override
    public String toString() {
        return String.format("[%s] Conductor: %s | Carga: %d/%d bicis | Sector: %s | Estado: %s",
                placa, conductor, inventarioActualBicis, capacidadMaximaBicis, sectorAsignado, estadoOperativo);
    }
}
