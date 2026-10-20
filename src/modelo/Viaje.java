package modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Modela una sesión de préstamo de bicicleta entre dos estaciones.
 * Implementa la lógica de cálculo de exceso de tiempo y sanciones reglamentarias.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 3: LEMA TAPIA, VICTORIA
 */
public class Viaje {

    private String idViaje;
    private String cedulaUsuario;
    private String idBicicleta;
    private String codigoEstacionOrigen;
    private String codigoEstacionDestino;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private long duracionMinutos;
    private double distanciaRecorridaKm;
    private double costoRecargo;
    private boolean esInfraccionGrave;
    private boolean esRebalanceoColaborativo;
    private String estadoViaje; // "EN_CURSO", "FINALIZADO_NORMAL", "FINALIZADO_CON_MORA", "ANOMALO"

    public Viaje(String idViaje, String cedulaUsuario, String idBicicleta, String codigoEstacionOrigen) {
        setIdViaje(idViaje);
        this.cedulaUsuario = cedulaUsuario;
        this.idBicicleta = idBicicleta;
        this.codigoEstacionOrigen = codigoEstacionOrigen;
        this.codigoEstacionDestino = null;
        this.fechaHoraInicio = LocalDateTime.now();
        this.fechaHoraFin = null;
        this.duracionMinutos = 0;
        this.distanciaRecorridaKm = 0.0;
        this.costoRecargo = 0.0;
        this.esInfraccionGrave = false;
        this.esRebalanceoColaborativo = false;
        this.estadoViaje = "EN_CURSO";
    }

    public String getIdViaje() {
        return idViaje;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public String getCodigoEstacionOrigen() {
        return codigoEstacionOrigen;
    }

    public String getCodigoEstacionDestino() {
        return codigoEstacionDestino;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public long getDuracionMinutos() {
        return duracionMinutos;
    }

    public double getDistanciaRecorridaKm() {
        return distanciaRecorridaKm;
    }

    public double getCostoRecargo() {
        return costoRecargo;
    }

    public boolean isEsInfraccionGrave() {
        return esInfraccionGrave;
    }

    public boolean isEsRebalanceoColaborativo() {
        return esRebalanceoColaborativo;
    }

    public String getEstadoViaje() {
        return estadoViaje;
    }

    public void setIdViaje(String idViaje) {
        if (idViaje != null && !idViaje.isBlank()) {
            this.idViaje = idViaje.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("El ID del viaje no puede estar vacío.");
        }
    }

    public void setEsRebalanceoColaborativo(boolean esRebalanceoColaborativo) {
        this.esRebalanceoColaborativo = esRebalanceoColaborativo;
    }

    // Regla de Negocio Compleja (RF-3.2): Cierre y liquidación del viaje
    public void finalizarViaje(String codigoEstacionDestino, LocalDateTime horaFin, double km) {
        if (codigoEstacionDestino == null || codigoEstacionDestino.isBlank()) {
            throw new IllegalArgumentException("La estación de destino no puede estar vacía.");
        }
        this.codigoEstacionDestino = codigoEstacionDestino.trim().toUpperCase();
        this.fechaHoraFin = (horaFin != null) ? horaFin : LocalDateTime.now();
        this.distanciaRecorridaKm = (km >= 0) ? km : 1.5;

        // Cálculo de duración en minutos
        this.duracionMinutos = ChronoUnit.MINUTES.between(this.fechaHoraInicio, this.fechaHoraFin);
        if (this.duracionMinutos < 0) this.duracionMinutos = 1;

        // Regla: 45 min gratuitos
        if (this.duracionMinutos <= 45) {
            this.costoRecargo = 0.0;
            this.estadoViaje = "FINALIZADO_NORMAL";
        } else {
            // Fracciones de 15 minutos de exceso = $0.50 cada 15 min
            long minutosExceso = this.duracionMinutos - 45;
            long bloques15 = (long) Math.ceil((double) minutosExceso / 15.0);
            this.costoRecargo = bloques15 * 0.50;

            // Retención indebida grave si supera 90 minutos continuos
            if (this.duracionMinutos > 90) {
                this.esInfraccionGrave = true;
                this.costoRecargo += 5.00; // Multa administrativa fija
                this.estadoViaje = "FINALIZADO_CON_MORA";
            } else {
                this.estadoViaje = "FINALIZADO_NORMAL";
            }
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] Usuario: %s | Bici: %s | %s -> %s | Duración: %d min | Recargo: $%.2f | Estado: %s",
                idViaje, cedulaUsuario, idBicicleta, codigoEstacionOrigen,
                (codigoEstacionDestino != null ? codigoEstacionDestino : "EN RUTA"),
                duracionMinutos, costoRecargo, estadoViaje);
    }
}
