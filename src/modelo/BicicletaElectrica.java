package modelo;

/**
 * Subclase que representa una bicicleta eléctrica asistida (E-Bike).
 * Demuestra el principio de HERENCIA extendiendo la clase Bicicleta
 * e incorporando telemetría de baterías y autonomía dinámica.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 2: HERRERA CUJANO, DARIKSON
 */
public class BicicletaElectrica extends Bicicleta {

    // 1. Atributos propios de la bicicleta eléctrica
    private double capacidadBateriaWh; // Watts-hora (ej. 360 Wh)
    private double voltajeNominalV;    // Voltios (ej. 36 V)
    private double porcentajeCargaSoC; // State of Charge (0.0% a 100.0%)
    private int ciclosRecarga;         // Ciclos completos acumulados
    private double factorDegradacion;  // Porcentaje de degradación química
    private double autonomiaEstimadaKm;// Autonomía proyectada en km

    // 2. Constructor con parámetros invocando super()
    public BicicletaElectrica(String idBicicleta, String numeroSerie, String marca, String modelo,
                              double pesoKg, int rodadoPulgadas, double capacidadBateriaWh,
                              double voltajeNominalV, double porcentajeCargaSoC) {
        super(idBicicleta, numeroSerie, marca, modelo, pesoKg, rodadoPulgadas);
        setCapacidadBateriaWh(capacidadBateriaWh);
        setVoltajeNominalV(voltajeNominalV);
        this.ciclosRecarga = 0;
        this.factorDegradacion = 0.0;
        setPorcentajeCargaSoC(porcentajeCargaSoC);
    }

    // 3. Métodos Getter
    public double getCapacidadBateriaWh() {
        return capacidadBateriaWh;
    }

    public double getVoltajeNominalV() {
        return voltajeNominalV;
    }

    public double getPorcentajeCargaSoC() {
        return porcentajeCargaSoC;
    }

    public int getCiclosRecarga() {
        return ciclosRecarga;
    }

    public double getFactorDegradacion() {
        return factorDegradacion;
    }

    public double getAutonomíaEstimadaKm() {
        return autonomiaEstimadaKm;
    }

    // 4. Métodos Setter con validaciones
    public void setCapacidadBateriaWh(double capacidadBateriaWh) {
        if (capacidadBateriaWh > 0) {
            this.capacidadBateriaWh = capacidadBateriaWh;
        } else {
            throw new IllegalArgumentException("La capacidad de batería debe ser mayor a 0 Wh.");
        }
    }

    public void setVoltajeNominalV(double voltajeNominalV) {
        if (voltajeNominalV > 0) {
            this.voltajeNominalV = voltajeNominalV;
        } else {
            throw new IllegalArgumentException("El voltaje nominal debe ser mayor a 0 V.");
        }
    }

    public void setPorcentajeCargaSoC(double porcentajeCargaSoC) {
        if (porcentajeCargaSoC >= 0.0 && porcentajeCargaSoC <= 100.0) {
            this.porcentajeCargaSoC = porcentajeCargaSoC;
            actualizarAutonomia(false);
            
            // Regla de negocio RF-2.2: Bloqueo si SoC <= 20%
            if (this.porcentajeCargaSoC <= 20.0 && !"EN_USO".equals(getEstadoOperativo())) {
                setEstadoOperativo("BLOQUEADA_BATERIA");
            } else if ("BLOQUEADA_BATERIA".equals(getEstadoOperativo()) && this.porcentajeCargaSoC > 20.0) {
                setEstadoOperativo("DISPONIBLE");
            }
        } else {
            throw new IllegalArgumentException("El porcentaje de carga debe estar entre 0.0% y 100.0%.");
        }
    }

    public void setCiclosRecarga(int ciclosRecarga) {
        if (ciclosRecarga >= 0) {
            this.ciclosRecarga = ciclosRecarga;
            // 0.05% de degradación por ciclo
            this.factorDegradacion = Math.min(50.0, this.ciclosRecarga * 0.05);
            actualizarAutonomia(false);
        }
    }

    // 5. Algoritmo de Negocio Complejo (RF-2.2)
    /**
     * Calcula la autonomía restante en km considerando:
     * - SoC actual (State of Charge)
     * - Capacidad Wh base y consumo promedio (aprox. 8 Wh/km)
     * - Degradación química por ciclos de recarga
     * - Factor topográfico de Quito (castigo de 20% si hay pendiente pronunciada)
     */
    public void actualizarAutonomia(boolean sectorConPendiente) {
        double capacidadEfectiva = this.capacidadBateriaWh * (1.0 - (this.factorDegradacion / 100.0));
        double energiaDisponible = capacidadEfectiva * (this.porcentajeCargaSoC / 100.0);
        double consumoPorKm = 8.0; // Wh/km en terreno plano

        if (sectorConPendiente) {
            consumoPorKm *= 1.25; // Aumenta 25% de consumo en laderas
        }

        this.autonomiaEstimadaKm = (consumoPorKm > 0) ? (energiaDisponible / consumoPorKm) : 0.0;
    }

    @Override
    public boolean estaAptaParaUso() {
        return super.estaAptaParaUso() && this.porcentajeCargaSoC > 20.0 && !"BLOQUEADA_BATERIA".equalsIgnoreCase(getEstadoOperativo());
    }

    @Override
    public String toString() {
        return String.format("%s | [E-BIKE] Batería: %.1f%% (%.1f Wh) | Autonomía: %.1f km | Degradación: %.1f%%",
                super.toString(), porcentajeCargaSoC, capacidadBateriaWh, autonomiaEstimadaKm, factorDegradacion);
    }
}
