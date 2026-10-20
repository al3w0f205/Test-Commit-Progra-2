package negocio;

import modelo.Bicicleta;
import modelo.UsuarioCiclista;
import modelo.Viaje;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para el control de viajes, liquidación de tiempos y sanciones.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 3: LEMA TAPIA, VICTORIA
 */
public class GestorViajes {

    private List<Viaje> listaViajes;

    public GestorViajes() {
        this.listaViajes = new ArrayList<>();
    }

    public Viaje iniciarViaje(String idViaje, UsuarioCiclista usuario, Bicicleta bici, String codigoOrigen) {
        if (usuario == null || !usuario.puedePrestarBicicleta()) {
            throw new IllegalStateException("El usuario no está habilitado para realizar préstamos.");
        }
        if (bici == null || !bici.estaAptaParaUso()) {
            throw new IllegalStateException("La bicicleta no se encuentra disponible u operativa.");
        }

        Viaje nuevoViaje = new Viaje(idViaje, usuario.getCedula(), bici.getIdBicicleta(), codigoOrigen);
        bici.setEstadoOperativo("EN_USO");
        this.listaViajes.add(nuevoViaje);
        return nuevoViaje;
    }

    public void finalizarViaje(String idViaje, String codigoDestino, LocalDateTime horaFin,
                               double kmRecorridos, UsuarioCiclista usuario, Bicicleta bici) {
        Viaje v = buscarViaje(idViaje);
        if (v == null) {
            throw new IllegalArgumentException("No se encontró el viaje con ID: " + idViaje);
        }
        if (!"EN_CURSO".equals(v.getEstadoViaje())) {
            throw new IllegalStateException("El viaje ya ha sido finalizado previamente.");
        }

        v.finalizarViaje(codigoDestino, horaFin, kmRecorridos);
        if (bici != null) {
            bici.registrarRecorrido(kmRecorridos);
            bici.setEstadoOperativo("DISPONIBLE");
        }

        // Si fue infracción grave, suspender al usuario preventivamente
        if (v.isEsInfraccionGrave() && usuario != null) {
            usuario.setEstadoCuenta("SUSPENDIDO_72H");
        }
    }

    public Viaje buscarViaje(String idViaje) {
        if (idViaje == null) return null;
        for (Viaje v : listaViajes) {
            if (idViaje.equalsIgnoreCase(v.getIdViaje())) {
                return v;
            }
        }
        return null;
    }

    public List<Viaje> getListaViajes() {
        return new ArrayList<>(listaViajes);
    }
}
