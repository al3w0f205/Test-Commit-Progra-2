package modelo;

/**
 * Representa un punto individual de amarre electromecánico (dock) dentro de una estación.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 1: FIGUEROA VASQUEZ, MIGUEL
 */
public class Anclaje {

    private int numeroDock;
    private String estado; // "LIBRE", "OCUPADO", "AVERIADO", "BLOQUEADO"
    private String idBicicletaAnclada; // null si está libre

    public Anclaje(int numeroDock) {
        if (numeroDock > 0) {
            this.numeroDock = numeroDock;
            this.estado = "LIBRE";
            this.idBicicletaAnclada = null;
        } else {
            throw new IllegalArgumentException("El número de dock debe ser positivo.");
        }
    }

    public int getNumeroDock() {
        return numeroDock;
    }

    public String getEstado() {
        return estado;
    }

    public String getIdBicicletaAnclada() {
        return idBicicletaAnclada;
    }

    public void setEstado(String estado) {
        if (estado != null && !estado.isBlank()) {
            this.estado = estado.trim().toUpperCase();
        }
    }

    public void anclarBicicleta(String idBicicleta) {
        if (idBicicleta != null && !idBicicleta.isBlank()) {
            this.idBicicletaAnclada = idBicicleta.trim().toUpperCase();
            this.estado = "OCUPADO";
        } else {
            throw new IllegalArgumentException("El ID de bicicleta no puede estar vacío al anclar.");
        }
    }

    public String liberarBicicleta() {
        if ("OCUPADO".equalsIgnoreCase(this.estado) && this.idBicicletaAnclada != null) {
            String idLiberada = this.idBicicletaAnclada;
            this.idBicicletaAnclada = null;
            this.estado = "LIBRE";
            return idLiberada;
        }
        return null;
    }

    public boolean estaOperativo() {
        return !"AVERIADO".equalsIgnoreCase(this.estado);
    }

    @Override
    public String toString() {
        return String.format("Dock #%d [%s]%s", numeroDock, estado,
                (idBicicletaAnclada != null ? " -> Bici: " + idBicicletaAnclada : ""));
    }
}
