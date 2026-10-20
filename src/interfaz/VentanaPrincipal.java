package interfaz;

import modelo.*;
import negocio.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Interfaz Gráfica Principal del Sistema "EcoMove Urban".
 * Desarrollada con Java Swing respetando la arquitectura MVC / 3 Capas
 * (modelo, negocio, interfaz) requerida por la asignatura.
 * 
 * Asignatura: Programación II (ISWZ1103) - UDLA
 * Equipo de Desarrollo: FIGUEROA, HERRERA, LEMA, LLERENA, ROSARIO, YE.
 */
public class VentanaPrincipal extends JFrame {

    // Instancias de la capa de Negocio
    private GestorEstaciones gestorEstaciones;
    private GestorFlota gestorFlota;
    private GestorViajes gestorViajes;
    private GestorMantenimiento gestorMantenimiento;
    private GestorRebalanceo gestorRebalanceo;
    private GestorIncentivos gestorIncentivos;

    // Componentes de interfaz
    private JTabbedPane tabbedPane;

    public VentanaPrincipal() {
        super("EcoMove Urban - Gestión de Microrred de Bicicletas Públicas (Quito)");
        inicializarCapaNegocio();
        cargarDatosDePrueba();
        construirInterfaz();
    }

    private void inicializarCapaNegocio() {
        gestorEstaciones = new GestorEstaciones();
        gestorFlota = new GestorFlota();
        gestorViajes = new GestorViajes();
        gestorMantenimiento = new GestorMantenimiento();
        gestorRebalanceo = new GestorRebalanceo();
        gestorIncentivos = new GestorIncentivos();
    }

    private void cargarDatosDePrueba() {
        // 1. Estaciones en Quito (Intermodales y Estándar)
        Estacion e1 = new Estacion("EST-01", "La Carolina (Metro Naciones Unidas)", -0.1795, -78.4832, "NORTE", true, 20);
        Estacion e2 = new Estacion("EST-02", "El Labrador (Metro Terminal Norte)", -0.1550, -78.4890, "NORTE", true, 20);
        Estacion e3 = new Estacion("EST-03", "Plaza Grande (Centro Histórico)", -0.2198, -78.5123, "CENTRO", false, 15);
        Estacion e4 = new Estacion("EST-04", "Quitumbe (Metro Terminal Sur)", -0.2890, -78.5520, "SUR", true, 20);
        
        gestorEstaciones.registrarEstacion(e1);
        gestorEstaciones.registrarEstacion(e2);
        gestorEstaciones.registrarEstacion(e3);
        gestorEstaciones.registrarEstacion(e4);

        // 2. Flota de bicicletas
        Bicicleta b1 = new Bicicleta("BIC-01", "SER-101", "Oxford", "Urban City", 14.5, 26);
        Bicicleta b2 = new Bicicleta("BIC-02", "SER-102", "Giant", "Escape 3", 13.8, 28);
        BicicletaElectrica eb1 = new BicicletaElectrica("EBIC-01", "SER-E201", "Specialized", "Turbo Vado", 19.2, 28, 360, 36, 85.0);
        BicicletaElectrica eb2 = new BicicletaElectrica("EBIC-02", "SER-E202", "Trek", "Allant+", 20.1, 28, 400, 36, 18.0); // Batería baja

        gestorFlota.registrarBicicleta(b1);
        gestorFlota.registrarBicicleta(b2);
        gestorFlota.registrarBicicleta(eb1);
        gestorFlota.registrarBicicleta(eb2);

        // Anclar bicicletas en estaciones
        gestorEstaciones.anclarBicicletaEnEstacion("EST-01", "BIC-01");
        gestorEstaciones.anclarBicicletaEnEstacion("EST-01", "EBIC-01");
        gestorEstaciones.anclarBicicletaEnEstacion("EST-02", "BIC-02");
        gestorEstaciones.anclarBicicletaEnEstacion("EST-03", "EBIC-02");

        // 3. Usuarios ciclistas
        UsuarioCiclista u1 = new UsuarioCiclista("1723456789", "Juan Carlos", "Paredes", "0991234567", "juan.paredes@udla.edu.ec");
        UsuarioCiclista u2 = new UsuarioCiclista("1798765432", "Valeria Sofia", "Mendoza", "0987654321", "valeria.m@udla.edu.ec");
        gestorIncentivos.registrarUsuario(u1);
        gestorIncentivos.registrarUsuario(u2);

        // 4. Logística: Furgoneta de rebalanceo
        FurgonetaRebalanceo furgon = new FurgonetaRebalanceo("PBA-4589", "Carlos Benalcázar", 18, "NORTE");
        gestorRebalanceo.registrarFurgoneta(furgon);
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 680);
        setLocationRelativeTo(null);

        // Barra superior de encabezado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(192, 0, 0)); // Rojo institucional UDLA
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel titleLabel = new JLabel("EcoMove Urban - Plataforma de Gestión y Rebalanceo Inteligente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Universidad de Las Américas (UDLA) - Programación II (ISWZ1103) - Arquitectura MVC");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(245, 245, 245));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Pestañas por módulos
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tabbedPane.addTab("1. Estaciones & Saturación", crearPanelEstaciones());
        tabbedPane.addTab("2. Flota & E-Bikes", crearPanelFlota());
        tabbedPane.addTab("3. Préstamos & Viajes", crearPanelViajes());
        tabbedPane.addTab("4. Taller & Desgaste SSM", crearPanelMantenimiento());
        tabbedPane.addTab("5. Logística & Rebalanceo", crearPanelRebalanceo());
        tabbedPane.addTab("6. Puntos Verdes & CO2", crearPanelIncentivos());

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    // PANEL 1: MÓDULO 1 (ESTACIONES Y ANCLAJES)
    private JPanel crearPanelEstaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"Código", "Nombre", "Sector", "Metro", "Ocupación", "Índice Sat.", "Diagnóstico"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);
        actualizarTablaEstaciones(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEvaluarHoraPico = new JButton("Simular Alerta de Hora Pico");
        btnEvaluarHoraPico.addActionListener(e -> {
            Estacion est = gestorEstaciones.buscarEstacion("EST-01");
            if (est != null) {
                est.setDesanclajesUltimos20Min(18); // Alta demanda
                boolean alerta = est.tieneAlertaDesabastecimientoPico(true);
                JOptionPane.showMessageDialog(this,
                        String.format("Estación: %s\nTasa: %.2f bicis/min\n¿Alerta de Desabastecimiento?: %s",
                                est.getNombre(), est.getDesanclajesUltimos20Min() / 20.0, (alerta ? "¡SÍ, CRÍTICA!" : "Normal")),
                        "Resultado RF-1.2: Detección de Anomalías", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> actualizarTablaEstaciones(model));

        btnPanel.add(btnEvaluarHoraPico);
        btnPanel.add(btnRefrescar);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void actualizarTablaEstaciones(DefaultTableModel model) {
        model.setRowCount(0);
        for (Estacion e : gestorEstaciones.getListaEstaciones()) {
            model.addRow(new Object[]{
                    e.getCodigoEstacion(),
                    e.getNombre(),
                    e.getSector(),
                    e.isIntermodalMetro() ? "SÍ" : "NO",
                    e.getCantidadBicicletasAncladas() + "/" + e.getCapacidadTotal(),
                    String.format("%.1f%%", e.calcularIndiceSaturacion()),
                    e.clasificarEstadoOperativo()
            });
        }
    }

    // PANEL 2: MÓDULO 2 (FLOTA Y BATERÍAS)
    private JPanel crearPanelFlota() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Tipo", "Marca/Modelo", "Odómetro", "Estado", "SSM", "Batería SoC", "Autonomía"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);
        actualizarTablaFlota(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRecargar = new JButton("Recargar Batería EBIC-02 a 100%");
        btnRecargar.addActionListener(e -> {
            Bicicleta b = gestorFlota.buscarBicicleta("EBIC-02");
            if (b instanceof BicicletaElectrica) {
                ((BicicletaElectrica) b).setPorcentajeCargaSoC(100.0);
                actualizarTablaFlota(model);
                JOptionPane.showMessageDialog(this, "Batería recargada. Unidad desbloqueada para uso.");
            }
        });
        btnPanel.add(btnRecargar);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void actualizarTablaFlota(DefaultTableModel model) {
        model.setRowCount(0);
        for (Bicicleta b : gestorFlota.getListaBicicletas()) {
            String tipo = (b instanceof BicicletaElectrica) ? "Eléctrica (E-Bike)" : "Mecánica";
            String soc = (b instanceof BicicletaElectrica) ? String.format("%.1f%%", ((BicicletaElectrica) b).getPorcentajeCargaSoC()) : "N/A";
            String auto = (b instanceof BicicletaElectrica) ? String.format("%.1f km", ((BicicletaElectrica) b).getAutonomíaEstimadaKm()) : "N/A";

            model.addRow(new Object[]{
                    b.getIdBicicleta(), tipo, b.getMarca() + " " + b.getModelo(),
                    String.format("%.1f km", b.getOdometroKm()), b.getEstadoOperativo(),
                    String.format("%.1f/100", b.getScoreSaludMecanica()), soc, auto
            });
        }
    }

    // PANEL 3: MÓDULO 3 (PRÉSTAMOS Y VIAJES)
    private JPanel crearPanelViajes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID Viaje", "Usuario", "Bicicleta", "Origen", "Destino", "Duración", "Recargo", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);

        JPanel acciones = new JPanel(new GridLayout(2, 2, 8, 8));
        JButton btnIniciar = new JButton("1. Iniciar Préstamo (Juan Paredes -> BIC-01 en EST-01)");
        JButton btnFinalizarNormal = new JButton("2. Simular Devolución a Tiempo (35 min, $0.00)");
        JButton btnFinalizarMora = new JButton("3. Simular Devolución con Exceso (110 min, Multa)");

        btnIniciar.addActionListener(e -> {
            try {
                UsuarioCiclista u = gestorIncentivos.buscarUsuario("1723456789");
                Bicicleta b = gestorFlota.buscarBicicleta("BIC-01");
                Viaje v = gestorViajes.iniciarViaje("VIA-001", u, b, "EST-01");
                model.addRow(new Object[]{v.getIdViaje(), u.getNombres(), b.getIdBicicleta(), "EST-01", "EN RUTA", "En curso", "$0.00", v.getEstadoViaje()});
                JOptionPane.showMessageDialog(this, "Viaje iniciado con éxito. Anclaje liberado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnFinalizarNormal.addActionListener(e -> {
            try {
                UsuarioCiclista u = gestorIncentivos.buscarUsuario("1723456789");
                Bicicleta b = gestorFlota.buscarBicicleta("BIC-01");
                LocalDateTime inicio = LocalDateTime.now().minusMinutes(35);
                Viaje v = gestorViajes.buscarViaje("VIA-001");
                if (v != null) {
                    gestorViajes.finalizarViaje("VIA-001", "EST-02", LocalDateTime.now(), 4.2, u, b);
                    // Actualizar tabla
                    model.setRowCount(0);
                    model.addRow(new Object[]{v.getIdViaje(), u.getNombres(), b.getIdBicicleta(), v.getCodigoEstacionOrigen(), v.getCodigoEstacionDestino(), v.getDuracionMinutos() + " min", "$0.00", v.getEstadoViaje()});
                    JOptionPane.showMessageDialog(this, "Viaje completado dentro de los 45 min gratuitos. Recargo: $0.00");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnFinalizarMora.addActionListener(e -> {
            try {
                UsuarioCiclista u = gestorIncentivos.buscarUsuario("1723456789");
                Bicicleta b = gestorFlota.buscarBicicleta("BIC-02");
                Viaje v = gestorViajes.iniciarViaje("VIA-002", u, b, "EST-02");
                gestorViajes.finalizarViaje("VIA-002", "EST-03", LocalDateTime.now().plusMinutes(110), 12.0, u, b);
                model.addRow(new Object[]{v.getIdViaje(), u.getNombres(), b.getIdBicicleta(), v.getCodigoEstacionOrigen(), v.getCodigoEstacionDestino(), v.getDuracionMinutos() + " min", String.format("$%.2f", v.getCostoRecargo()), v.getEstadoViaje()});
                JOptionPane.showMessageDialog(this, String.format("¡Infracción detectada! Duración: %d min.\nRecargo: $%.2f\nEstado Usuario: %s",
                        v.getDuracionMinutos(), v.getCostoRecargo(), u.getEstadoCuenta()), "RF-3.2: Sanción por Exceso", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        acciones.add(btnIniciar);
        acciones.add(btnFinalizarNormal);
        acciones.add(btnFinalizarMora);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    // PANEL 4: MÓDULO 4 (TALLER Y DESGASTE PREDICTIVO SSM)
    private JPanel crearPanelMantenimiento() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID Ticket", "Bicicleta", "Componente", "Severidad", "Resuelto", "Score Salud Mecánica"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnReportarFrenos = new JButton("Simular Falla Crítica de Frenos en BIC-01 (SSM -> 0)");
        btnReportarFrenos.addActionListener(e -> {
            Bicicleta b = gestorFlota.buscarBicicleta("BIC-01");
            ReporteMantenimiento rep = new ReporteMantenimiento("REP-101", "BIC-01", "FRENOS", "Pastillas cristalizadas sin frenado", "CRITICO");
            gestorMantenimiento.registrarReporte(rep, b);
            model.addRow(new Object[]{rep.getIdReporte(), rep.getIdBicicleta(), rep.getComponenteAfectado(), rep.getSeveridad(), "NO", b.getScoreSaludMecanica() + "/100"});
            JOptionPane.showMessageDialog(this,
                    String.format("Reporte de frenos procesado.\nNuevo SSM: %.1f/100\nEstado: %s (Anclaje bloqueado automáticamente)",
                            b.getScoreSaludMecanica(), b.getEstadoOperativo()),
                    "RF-4.2: Retiro Preventivo Automático", JOptionPane.WARNING_MESSAGE);
        });

        btnPanel.add(btnReportarFrenos);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // PANEL 5: MÓDULO 5 (LOGÍSTICA Y REBALANCEO)
    private JPanel crearPanelRebalanceo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID Misión", "Prioridad", "Estación Origen", "Estación Destino", "Cant. Bicis", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnGenerarMision = new JButton("Calcular y Generar Misión Óptima de Rebalanceo (RF-5.2)");
        btnGenerarMision.addActionListener(e -> {
            Estacion saturada = gestorEstaciones.buscarEstacion("EST-01");
            Estacion deficitaria = gestorEstaciones.buscarEstacion("EST-04");
            OrdenRebalanceo orden = gestorRebalanceo.generarMisionOptima("ORD-501", saturada, deficitaria, true);
            model.addRow(new Object[]{orden.getIdOrden(), orden.getNivelPrioridad() + "/5", orden.getCodigoEstacionOrigen(), orden.getCodigoEstacionDestino(), orden.getCantidadBicicletas() + " bicis", orden.getEstado()});
            JOptionPane.showMessageDialog(this,
                    String.format("Misión generada con éxito.\nOrigen: %s\nDestino: %s (Intermodal Metro: +2 Prioridad)\nCantidad a transferir: %d bicis\nNivel de urgencia: %d/5",
                            orden.getCodigoEstacionOrigen(), orden.getCodigoEstacionDestino(), orden.getCantidadBicicletas(), orden.getNivelPrioridad()),
                    "RF-5.2: Despacho Logístico", JOptionPane.INFORMATION_MESSAGE);
        });

        btnPanel.add(btnGenerarMision);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // PANEL 6: MÓDULO 6 (INCENTIVOS PUNTOS VERDES Y CO2)
    private JPanel crearPanelIncentivos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"Cédula", "Nombre", "Puntos Verdes", "Km Activos", "CO2 Ahorrado (kg)", "Estado Cuenta"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);
        actualizarTablaUsuarios(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSimularRebalanceoCiudadano = new JButton("Simular Rebalanceo Colaborativo (+50 Puntos Bono + CO2)");
        btnSimularRebalanceoCiudadano.addActionListener(e -> {
            UsuarioCiclista u = gestorIncentivos.buscarUsuario("1723456789");
            Viaje v = new Viaje("VIA-CROWD", u.getCedula(), "BIC-01", "EST-01");
            v.finalizarViaje("EST-04", LocalDateTime.now().plusMinutes(25), 6.5);

            Estacion eOrig = gestorEstaciones.buscarEstacion("EST-01"); // Saturada
            Estacion eDest = gestorEstaciones.buscarEstacion("EST-04"); // Deficitaria

            int puntos = gestorIncentivos.procesarRecompensasViaje(v, eOrig, eDest, u);
            actualizarTablaUsuarios(model);

            JOptionPane.showMessageDialog(this,
                    String.format("¡Bono por Rebalanceo Colaborativo Acreditado!\nPuntos Ganados: +%d pts\nCO2 Mitigado: %.2f kg\nSaldo Total: %d Puntos Verdes",
                            puntos, (6.5 * 178.5) / 1000.0, u.getPuntosVerdes()),
                    "RF-6.2: Sostenibilidad Urbana", JOptionPane.INFORMATION_MESSAGE);
        });

        btnPanel.add(btnSimularRebalanceoCiudadano);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void actualizarTablaUsuarios(DefaultTableModel model) {
        model.setRowCount(0);
        for (UsuarioCiclista u : gestorIncentivos.getListaUsuarios()) {
            model.addRow(new Object[]{
                    u.getCedula(), u.getNombreCompleto(), u.getPuntosVerdes() + " pts",
                    String.format("%.1f km", u.getTotalKmRecorridos()),
                    String.format("%.2f kg", u.getCo2AhorradoKg()), u.getEstadoCuenta()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal v = new VentanaPrincipal();
            v.setVisible(true);
        });
    }
}
