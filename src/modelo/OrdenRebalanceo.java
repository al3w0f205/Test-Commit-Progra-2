package modelo;

import java.time.LocalDateTime;

/**
 * Modela una misión u orden logística de rebalanceo de bicicletas entre estaciones.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 5: ROSARIO RODRIGUEZ, MARIA
 */
public class OrdenRebalanceo {

    private String idOrden;
    private String codigoEstacionOrigen;   // Estación saturada (donante)
    private String codigoEstacionDestino;  // Estación deficitaria (receptora)
    private int cantidadBicicletas;
    private int nivelPrioridad;            // 1 (baja) a 5 (urgente)
    private String estado;                 // "PENDIENTE", "EN_CURSO", "COMPLETADA"
    private LocalDateTime fechaCreacion;

    public OrdenRebalanceo(String idOrden, String origen, String destino, int cantidad, int prioridad) {
        setIdOrden(idOrden);
        this.codigoEstacionOrigen = origen;
        this.codigoEstacionDestino = destino;
        setCantidadBicicletas(cantidad);
        setNivelPrioridad(prioridad);
        this.estado = "PENDIENTE";
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getIdOrden() {
        return idOrden;
    }

    public String getCodigoEstacionOrigen() {
        return codigoEstacionOrigen;
    }

    public String getCodigoEstacionDestino() {
        return codigoEstacionDestino;
    }

    public int getCantidadBicicletas() {
        return cantidadBicicletas;
    }

    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setIdOrden(String idOrden) {
        if (idOrden != null && !idOrden.isBlank()) {
            this.idOrden = idOrden.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("El ID de orden no puede estar vacío.");
        }
    }

    public void setCantidadBicicletas(int cantidad) {
        if (cantidad > 0) {
            this.cantidadBicicletas = cantidad;
        } else {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
    }

    public void setNivelPrioridad(int prioridad) {
        if (prioridad >= 1 && prioridad <= 5) {
            this.nivelPrioridad = prioridad;
        } else {
            this.nivelPrioridad = 3;
        }
    }

    public void setEstado(String estado) {
        if (estado != null && !estado.isBlank()) {
            this.estado = estado.trim().toUpperCase();
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] Prioridad: %d/5 | %s -> %s (%d bicis) | Estado: %s",
                idOrden, nivelPrioridad, codigoEstacionOrigen, codigoEstacionDestino, cantidadBicicletas, estado);
    }
}
