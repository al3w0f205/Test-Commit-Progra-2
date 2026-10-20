package negocio;

import modelo.Estacion;
import modelo.FurgonetaRebalanceo;
import modelo.OrdenRebalanceo;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para la logística de redistribución de flota y camionetas.
 * Implementa el algoritmo de balance territorial y generación de órdenes (RF-5.2).
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 5: ROSARIO RODRIGUEZ, MARIA
 */
public class GestorRebalanceo {

    private List<FurgonetaRebalanceo> listaFurgonetas;
    private List<OrdenRebalanceo> listaOrdenes;

    public GestorRebalanceo() {
        this.listaFurgonetas = new ArrayList<>();
        this.listaOrdenes = new ArrayList<>();
    }

    public void registrarFurgoneta(FurgonetaRebalanceo furgoneta) {
        if (furgoneta == null) {
            throw new IllegalArgumentException("La furgoneta no puede ser nula.");
        }
        this.listaFurgonetas.add(furgoneta);
    }

    public List<FurgonetaRebalanceo> getListaFurgonetas() {
        return new ArrayList<>(listaFurgonetas);
    }

    public List<OrdenRebalanceo> getListaOrdenes() {
        return new ArrayList<>(listaOrdenes);
    }

    /**
     * Algoritmo de Generación y Priorización de Órdenes de Rebalanceo (RF-5.2)
     */
    public OrdenRebalanceo generarMisionOptima(String idOrden, Estacion saturada, Estacion deficitaria, boolean horaPico) {
        if (saturada == null || deficitaria == null) {
            throw new IllegalArgumentException("Se requieren estación de origen y destino.");
        }

        // Cálculo de excedente en saturada
        int bicisSaturada = saturada.getCantidadBicicletasAncladas();
        int optimoSaturada = (int) (saturada.getCapacidadTotal() * 0.50);
        int excedente = Math.max(1, bicisSaturada - optimoSaturada);

        // Cálculo de faltante en deficitaria
        int bicisDeficitaria = deficitaria.getCantidadBicicletasAncladas();
        int optimoDeficitaria = (int) (deficitaria.getCapacidadTotal() * 0.50);
        int faltante = Math.max(1, optimoDeficitaria - bicisDeficitaria);

        // La cantidad a mover es el mínimo entre lo que sobra y lo que falta (máx 15 por viaje de furgoneta)
        int cantidadAMover = Math.min(excedente, Math.min(faltante, 15));

        // Cálculo de prioridad (1 a 5)
        int prioridad = 2;
        if (deficitaria.isIntermodalMetro()) prioridad += 2; // +2 si conecta con Metro
        if (horaPico) prioridad += 1;                       // +1 en hora pico
        prioridad = Math.min(5, prioridad);

        OrdenRebalanceo orden = new OrdenRebalanceo(idOrden, saturada.getCodigoEstacion(),
                deficitaria.getCodigoEstacion(), cantidadAMover, prioridad);
        this.listaOrdenes.add(orden);
        return orden;
    }

    public void completarMision(String idOrden) {
        for (OrdenRebalanceo o : listaOrdenes) {
            if (idOrden.equalsIgnoreCase(o.getIdOrden())) {
                o.setEstado("COMPLETADA");
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró la orden: " + idOrden);
    }
}
