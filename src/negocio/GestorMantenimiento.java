package negocio;

import modelo.Bicicleta;
import modelo.ReporteMantenimiento;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para el taller de mantenimiento preventivo y correctivo.
 * Implementa el algoritmo del Score de Salud Mecánica (SSM) (RF-4.2).
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 4: LLERENA VIRACOCHA, ALEX
 */
public class GestorMantenimiento {

    private List<ReporteMantenimiento> listaReportes;

    public GestorMantenimiento() {
        this.listaReportes = new ArrayList<>();
    }

    public void registrarReporte(ReporteMantenimiento reporte, Bicicleta bici) {
        if (reporte == null) {
            throw new IllegalArgumentException("El reporte no puede ser nulo.");
        }
        this.listaReportes.add(reporte);

        if (bici != null) {
            // Algoritmo RF-4.2: Actualizar SSM
            recalcularScoreSaludMecanica(bici, 5); // 5 días de ejemplo desde último servicio
        }
    }

    /**
     * Algoritmo determinístico RF-4.2:
     * SSM = 100 - (Km_desde_service * 0.04) - (Dias_desde_revision * 0.25) - Penalidad_Reportes
     */
    public double recalcularScoreSaludMecanica(Bicicleta bici, int diasDesdeUltimoService) {
        if (bici == null) return 0.0;

        double km = bici.getOdometroKm();
        double penalidadReportes = 0.0;
        boolean tieneFallaCritica = false;

        for (ReporteMantenimiento r : listaReportes) {
            if (bici.getIdBicicleta().equalsIgnoreCase(r.getIdBicicleta()) && !r.isResuelto()) {
                if (r.esCriticoParaSeguridad()) {
                    tieneFallaCritica = true;
                } else if ("MODERADO".equalsIgnoreCase(r.getSeveridad())) {
                    penalidadReportes += 15.0;
                } else {
                    penalidadReportes += 5.0;
                }
            }
        }

        if (tieneFallaCritica) {
            bici.setScoreSaludMecanica(0.0);
            bici.setEstadoOperativo("REQUIERE_TALLER");
            return 0.0;
        }

        double nuevoSSM = 100.0 - (km * 0.04) - (diasDesdeUltimoService * 0.25) - penalidadReportes;
        nuevoSSM = Math.max(0.0, Math.min(100.0, nuevoSSM));
        bici.setScoreSaludMecanica(nuevoSSM);

        if (nuevoSSM < 70.0 && !"EN_USO".equals(bici.getEstadoOperativo())) {
            bici.setEstadoOperativo("REQUIERE_TALLER");
        }

        return nuevoSSM;
    }

    public void resolverReporte(String idReporte, String observaciones, Bicicleta bici) {
        for (ReporteMantenimiento r : listaReportes) {
            if (idReporte.equalsIgnoreCase(r.getIdReporte())) {
                r.resolverIncidencia(observaciones);
                if (bici != null) {
                    recalcularScoreSaludMecanica(bici, 0);
                    if (bici.getScoreSaludMecanica() >= 70.0) {
                        bici.setEstadoOperativo("DISPONIBLE");
                    }
                }
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró el reporte: " + idReporte);
    }

    public List<ReporteMantenimiento> getListaReportes() {
        return new ArrayList<>(listaReportes);
    }

    public List<ReporteMantenimiento> obtenerReportesPendientes() {
        List<ReporteMantenimiento> pendientes = new ArrayList<>();
        for (ReporteMantenimiento r : listaReportes) {
            if (!r.isResuelto()) {
                pendientes.add(r);
            }
        }
        return pendientes;
    }
}
