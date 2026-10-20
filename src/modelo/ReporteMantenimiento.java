package modelo;

import java.time.LocalDateTime;

/**
 * Representa un ticket de incidencia técnica o reporte de avería de una bicicleta.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 4: LLERENA VIRACOCHA, ALEX
 */
public class ReporteMantenimiento {

    private String idReporte;
    private String idBicicleta;
    private String componenteAfectado; // "FRENOS", "CADENA", "RUEDA", "CAMBIOS", "BATERIA", "SISTEMA_ELECTRICO"
    private String descripcionFalla;
    private String severidad;          // "LEVE", "MODERADO", "CRITICO"
    private LocalDateTime fechaReporte;
    private boolean resuelto;
    private String observacionesMecanico;

    public ReporteMantenimiento(String idReporte, String idBicicleta, String componenteAfectado,
                                String descripcionFalla, String severidad) {
        setIdReporte(idReporte);
        this.idBicicleta = idBicicleta;
        setComponenteAfectado(componenteAfectado);
        this.descripcionFalla = (descripcionFalla != null) ? descripcionFalla.trim() : "Sin detalle";
        setSeveridad(severidad);
        this.fechaReporte = LocalDateTime.now();
        this.resuelto = false;
        this.observacionesMecanico = null;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public String getComponenteAfectado() {
        return componenteAfectado;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public String getSeveridad() {
        return severidad;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public String getObservacionesMecanico() {
        return observacionesMecanico;
    }

    public void setIdReporte(String idReporte) {
        if (idReporte != null && !idReporte.isBlank()) {
            this.idReporte = idReporte.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("El ID del reporte no puede estar vacío.");
        }
    }

    public void setComponenteAfectado(String componenteAfectado) {
        if (componenteAfectado != null && !componenteAfectado.isBlank()) {
            this.componenteAfectado = componenteAfectado.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("Debe indicar el componente afectado.");
        }
    }

    public void setSeveridad(String severidad) {
        if (severidad != null && !severidad.isBlank()) {
            this.severidad = severidad.trim().toUpperCase();
        } else {
            this.severidad = "LEVE";
        }
    }

    public void resolverIncidencia(String observaciones) {
        this.resuelto = true;
        this.observacionesMecanico = observaciones;
    }

    public boolean esCriticoParaSeguridad() {
        return "CRITICO".equalsIgnoreCase(this.severidad) || "FRENOS".equalsIgnoreCase(this.componenteAfectado);
    }

    @Override
    public String toString() {
        return String.format("[%s] Bici: %s | Comp: %s | Sev: %s | Resuelto: %s",
                idReporte, idBicicleta, componenteAfectado, severidad, (resuelto ? "SÍ" : "NO"));
    }
}
