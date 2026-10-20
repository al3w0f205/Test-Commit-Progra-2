package negocio;

import modelo.Estacion;
import modelo.UsuarioCiclista;
import modelo.Viaje;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para el programa de recompensas "Puntos Verdes"
 * y cálculo matemático de reducción de emisiones de CO2.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 6: YE CAO, FELIX
 */
public class GestorIncentivos {

    private List<UsuarioCiclista> listaUsuarios;
    private double totalCo2GlobalMitigadoKg;

    public GestorIncentivos() {
        this.listaUsuarios = new ArrayList<>();
        this.totalCo2GlobalMitigadoKg = 0.0;
    }

    public void registrarUsuario(UsuarioCiclista usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (buscarUsuario(usuario.getCedula()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con la cédula: " + usuario.getCedula());
        }
        this.listaUsuarios.add(usuario);
    }

    public UsuarioCiclista buscarUsuario(String cedula) {
        if (cedula == null) return null;
        for (UsuarioCiclista u : listaUsuarios) {
            if (cedula.equalsIgnoreCase(u.getCedula())) {
                return u;
            }
        }
        return null;
    }

    public List<UsuarioCiclista> getListaUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    public double getTotalCo2GlobalMitigadoKg() {
        return totalCo2GlobalMitigadoKg;
    }

    /**
     * Algoritmo RF-6.2: Rebalanceo Colaborativo (Crowdsourced) y Cálculo de CO2
     */
    public int procesarRecompensasViaje(Viaje viaje, Estacion origen, Estacion destino, UsuarioCiclista usuario) {
        if (viaje == null || usuario == null) return 0;

        double km = viaje.getDistanciaRecorridaKm();
        // Factor oficial: 178.5 gramos de CO2 por km frente al automóvil a combustión
        double co2Kg = (km * 178.5) / 1000.0;
        this.totalCo2GlobalMitigadoKg += co2Kg;

        int puntosOtorgados = 10; // Puntos base por viaje en bicicleta
        puntosOtorgados += (int) (km * 5); // 5 puntos por km

        // Condición de Rebalanceo Colaborativo: origen saturado (>80%) y destino deficitaria (<20%)
        if (origen != null && destino != null) {
            boolean origenLleno = origen.calcularIndiceSaturacion() >= 80.0;
            boolean destinoVacio = destino.calcularIndiceSaturacion() <= 20.0;

            if (origenLleno && destinoVacio) {
                viaje.setEsRebalanceoColaborativo(true);
                puntosOtorgados += 50; // Bono de rebalanceo urgente
            }
        }

        usuario.agregarPuntosVerdes(puntosOtorgados);
        usuario.registrarViaje(km, co2Kg);

        return puntosOtorgados;
    }
}
