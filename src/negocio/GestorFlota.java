package negocio;

import modelo.Bicicleta;
import modelo.BicicletaElectrica;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de negocio para el parque de bicicletas y telemetría de baterías.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 2: HERRERA CUJANO, DARIKSON
 */
public class GestorFlota {

    private List<Bicicleta> listaBicicletas;

    public GestorFlota() {
        this.listaBicicletas = new ArrayList<>();
    }

    public void registrarBicicleta(Bicicleta bici) {
        if (bici == null) {
            throw new IllegalArgumentException("La bicicleta no puede ser nula.");
        }
        if (buscarBicicleta(bici.getIdBicicleta()) != null) {
            throw new IllegalArgumentException("Ya existe una unidad con el ID: " + bici.getIdBicicleta());
        }
        this.listaBicicletas.add(bici);
    }

    public Bicicleta buscarBicicleta(String id) {
        if (id == null) return null;
        for (Bicicleta b : listaBicicletas) {
            if (id.equalsIgnoreCase(b.getIdBicicleta())) {
                return b;
            }
        }
        return null;
    }

    public List<Bicicleta> getListaBicicletas() {
        return new ArrayList<>(listaBicicletas);
    }

    public List<BicicletaElectrica> obtenerFlotaElectrica() {
        List<BicicletaElectrica> electricas = new ArrayList<>();
        for (Bicicleta b : listaBicicletas) {
            if (b instanceof BicicletaElectrica) {
                electricas.add((BicicletaElectrica) b);
            }
        }
        return electricas;
    }

    public List<Bicicleta> obtenerBicicletasDisponibles() {
        List<Bicicleta> disponibles = new ArrayList<>();
        for (Bicicleta b : listaBicicletas) {
            if (b.estaAptaParaUso()) {
                disponibles.add(b);
            }
        }
        return disponibles;
    }

    public List<Bicicleta> obtenerBicicletasEnTaller() {
        List<Bicicleta> enTaller = new ArrayList<>();
        for (Bicicleta b : listaBicicletas) {
            if ("REQUIERE_TALLER".equalsIgnoreCase(b.getEstadoOperativo())) {
                enTaller.add(b);
            }
        }
        return enTaller;
    }
}
