package modelo;

/**
 * Clase base que representa una bicicleta del sistema de micromovilidad.
 * Aplica los principios de encapsulamiento con atributos privados,
 * constructores con parámetros, getters y setters con validación.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 2: HERRERA CUJANO, DARIKSON
 */
public class Bicicleta {

    // 1. Atributos privados (Estado del objeto)
    private String idBicicleta;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private double pesoKg;
    private int rodadoPulgadas;
    private double odometroKm;
    private String estadoOperativo; // "DISPONIBLE", "EN_USO", "REQUIERE_TALLER", "BLOQUEADA_BATERIA"
    private double scoreSaludMecanica; // SSM (0.0 a 100.0)

    // 2. Constructor con parámetros
    public Bicicleta(String idBicicleta, String numeroSerie, String marca, String modelo,
                     double pesoKg, int rodadoPulgadas) {
        setIdBicicleta(idBicicleta);
        setNumeroSerie(numeroSerie);
        this.marca = (marca != null && !marca.isBlank()) ? marca : "Genérica";
        this.modelo = (modelo != null && !modelo.isBlank()) ? modelo : "Urbana";
        setPesoKg(pesoKg);
        setRodadoPulgadas(rodadoPulgadas);
        this.odometroKm = 0.0;
        this.estadoOperativo = "DISPONIBLE";
        this.scoreSaludMecanica = 100.0;
    }

    // Constructor completo para carga de datos
    public Bicicleta(String idBicicleta, String numeroSerie, String marca, String modelo,
                     double pesoKg, int rodadoPulgadas, double odometroKm, String estadoOperativo,
                     double scoreSaludMecanica) {
        this(idBicicleta, numeroSerie, marca, modelo, pesoKg, rodadoPulgadas);
        setOdometroKm(odometroKm);
        setEstadoOperativo(estadoOperativo);
        setScoreSaludMecanica(scoreSaludMecanica);
    }

    // 3. Métodos Getter
    public String getIdBicicleta() {
        return idBicicleta;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public int getRodadoPulgadas() {
        return rodadoPulgadas;
    }

    public double getOdometroKm() {
        return odometroKm;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public double getScoreSaludMecanica() {
        return scoreSaludMecanica;
    }

    // 4. Métodos Setter con validaciones
    public void setIdBicicleta(String idBicicleta) {
        if (idBicicleta != null && !idBicicleta.isBlank()) {
            this.idBicicleta = idBicicleta.trim().toUpperCase();
        } else {
            throw new IllegalArgumentException("El ID de bicicleta no puede estar vacío.");
        }
    }

    public void setNumeroSerie(String numeroSerie) {
        if (numeroSerie != null && !numeroSerie.isBlank()) {
            this.numeroSerie = numeroSerie.trim();
        } else {
            throw new IllegalArgumentException("El número de serie no puede estar vacío.");
        }
    }

    public void setMarca(String marca) {
        if (marca != null && !marca.isBlank()) {
            this.marca = marca.trim();
        }
    }

    public void setModelo(String modelo) {
        if (modelo != null && !modelo.isBlank()) {
            this.modelo = modelo.trim();
        }
    }

    public void setPesoKg(double pesoKg) {
        if (pesoKg > 0) {
            this.pesoKg = pesoKg;
        } else {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 kg.");
        }
    }

    public void setRodadoPulgadas(int rodadoPulgadas) {
        if (rodadoPulgadas >= 16 && rodadoPulgadas <= 29) {
            this.rodadoPulgadas = rodadoPulgadas;
        } else {
            throw new IllegalArgumentException("El rodado debe estar entre 16 y 29 pulgadas.");
        }
    }

    public void setOdometroKm(double odometroKm) {
        if (odometroKm >= 0) {
            this.odometroKm = odometroKm;
        } else {
            throw new IllegalArgumentException("El kilometraje no puede ser negativo.");
        }
    }

    public void setEstadoOperativo(String estadoOperativo) {
        if (estadoOperativo != null && !estadoOperativo.isBlank()) {
            this.estadoOperativo = estadoOperativo.trim().toUpperCase();
        }
    }

    public void setScoreSaludMecanica(double scoreSaludMecanica) {
        if (scoreSaludMecanica >= 0 && scoreSaludMecanica <= 100) {
            this.scoreSaludMecanica = scoreSaludMecanica;
            if (this.scoreSaludMecanica < 70.0 && !"EN_USO".equals(this.estadoOperativo)) {
                this.estadoOperativo = "REQUIERE_TALLER";
            }
        }
    }

    // 5. Métodos de Comportamiento del Negocio
    public void registrarRecorrido(double kmRecorridos) {
        if (kmRecorridos > 0) {
            this.odometroKm += kmRecorridos;
        }
    }

    public boolean estaAptaParaUso() {
        return "DISPONIBLE".equalsIgnoreCase(this.estadoOperativo) && this.scoreSaludMecanica >= 70.0;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s - Estado: %s | Km: %.1f | SSM: %.1f/100",
                idBicicleta, marca, modelo, estadoOperativo, odometroKm, scoreSaludMecanica);
    }
}
