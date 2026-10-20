package modelo;

/**
 * Representa al usuario ciclista registrado en el sistema.
 * Modela el estado de habilitación, saldo de recompensas y reducción de CO2.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Responsable Módulo 6: YE CAO, FELIX
 */
public class UsuarioCiclista {

    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private String estadoCuenta; // "HABILITADO", "SUSPENDIDO_72H", "EN_MORA"
    private int puntosVerdes;    // Saldo acumulado de recompensas
    private double totalKmRecorridos;
    private double co2AhorradoKg; // Reducción de huella de carbono

    public UsuarioCiclista(String cedula, String nombres, String apellidos, String telefono, String email) {
        setCedula(cedula);
        setNombres(nombres);
        setApellidos(apellidos);
        setTelefono(telefono);
        setEmail(email);
        this.estadoCuenta = "HABILITADO";
        this.puntosVerdes = 50; // Bono de bienvenida
        this.totalKmRecorridos = 0.0;
        this.co2AhorradoKg = 0.0;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public int getPuntosVerdes() {
        return puntosVerdes;
    }

    public double getTotalKmRecorridos() {
        return totalKmRecorridos;
    }

    public double getCo2AhorradoKg() {
        return co2AhorradoKg;
    }

    public void setCedula(String cedula) {
        if (cedula != null && !cedula.isBlank() && cedula.trim().length() == 10) {
            this.cedula = cedula.trim();
        } else {
            throw new IllegalArgumentException("La cédula debe contener exactamente 10 dígitos.");
        }
    }

    public void setNombres(String nombres) {
        if (nombres != null && !nombres.isBlank()) {
            this.nombres = nombres.trim();
        } else {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public void setApellidos(String apellidos) {
        if (apellidos != null && !apellidos.isBlank()) {
            this.apellidos = apellidos.trim();
        } else {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.isBlank()) {
            this.telefono = telefono.trim();
        } else {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email.trim().toLowerCase();
        } else {
            throw new IllegalArgumentException("El email debe tener un formato válido (ej. usuario@dominio.com).");
        }
    }

    public void setEstadoCuenta(String estadoCuenta) {
        if (estadoCuenta != null && !estadoCuenta.isBlank()) {
            this.estadoCuenta = estadoCuenta.trim().toUpperCase();
        }
    }

    public void agregarPuntosVerdes(int puntos) {
        if (puntos > 0) {
            this.puntosVerdes += puntos;
        }
    }

    public void registrarViaje(double kmRecorridos, double co2Kg) {
        if (kmRecorridos > 0) {
            this.totalKmRecorridos += kmRecorridos;
            this.co2AhorradoKg += co2Kg;
        }
    }

    public boolean puedePrestarBicicleta() {
        return "HABILITADO".equalsIgnoreCase(this.estadoCuenta);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Estado: %s | Puntos Verdes: %d pts | CO2 Mitigado: %.2f kg",
                cedula, getNombreCompleto(), estadoCuenta, puntosVerdes, co2AhorradoKg);
    }
}
