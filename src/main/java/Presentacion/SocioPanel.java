package Presentacion;

import LogicaNegocio.Excepciones.NegocioException;
import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Logica;
import LogicaNegocio.Modelos.Factura;
import LogicaNegocio.Modelos.Socio;
import LogicaNegocio.Reportes;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SocioPanel extends javax.swing.JPanel {

    private final Logica logica;
    private final Reportes reportes;

    public SocioPanel(Logica logica, Reportes reportes) {
        this.logica = logica;
        this.reportes = reportes;
        initComponents();
        NuevoSocio.addActionListener(evt -> nuevoSocio());

        // Doble clic en una fila = perfil completo del socio (con acciones de sanción y baja)
        jTable1.setToolTipText("Doble clic para ver el perfil completo");
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = jTable1.rowAtPoint(e.getPoint());
                    if (fila >= 0) {
                        verPerfil(String.valueOf(jTable1.getValueAt(fila, 0)));
                    }
                }
            }
        });
        cargarTabla(reportes.sociosPorApellidoNombre());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBusquedaDni = new javax.swing.JTextField();
        NuevoSocio = new javax.swing.JButton();
        btnBuscardni = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        NuevoSocio.setText("Nuevo Socio");

        btnBuscardni.setText("Buscar DNI");
        btnBuscardni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscardniActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "DNI", "Apellido y Nombre", "Categoria", "Cuota", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBusquedaDni, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(NuevoSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnBuscardni, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NuevoSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscardni, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusquedaDni, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void cargarTabla(List<Socio> socios) {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // borra las 4 filas vacías que genera NetBeans
        for (Socio s : socios) {
            String motivo = s.motivoInhabilitacion();
            modelo.addRow(new Object[] {
                s.getDni(),
                s.getApellido() + ", " + s.getNombre(),
                s.getTipo(),
                String.format("$%.2f", s.calcularCuotaMensual()),
                motivo == null ? "Habilitado" : motivo
            });
        }
    }
    private void btnBuscardniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscardniActionPerformed
        String dni = txtBusquedaDni.getText().trim();
        if (dni.isEmpty()) {                       // campo vacío = mostrar todos
            cargarTabla(reportes.sociosPorApellidoNombre());
            return;
        }
        try {
            cargarTabla(Collections.singletonList(logica.exigirSocio(dni)));
        } catch (NegocioException e) {
            mostrarError(e.getMessage());
        }     
    }//GEN-LAST:event_btnBuscardniActionPerformed

    // ------------------------------------------------------------------
    // Perfil del socio (doble clic) con acciones: sancionar / levantar sanción, baja / reactivar
    // ------------------------------------------------------------------
    private void verPerfil(String dni) {
        try {
            Socio s = logica.exigirSocio(dni);

            int comprobantes = 0;
            for (List<Factura> lista : s.getFacturasPorPeriodo().values()) {
                comprobantes += lista.size();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("DNI:               ").append(s.getDni()).append("\n");
            sb.append("Nombre:            ").append(s.getNombreCompleto()).append("\n");
            sb.append("Categoría:         ").append(s.getTipo()).append("\n");
            sb.append("Estado:            ").append(s.isActivo() ? "Activo" : "Inactivo (dado de baja)").append("\n");
            sb.append("Cuota al día:      ").append(s.isCuotaAlDia() ? "Sí" : "NO (moroso)").append("\n");
            sb.append("Sancionado:        ").append(s.isSancionado() ? "SÍ" : "No").append("\n");
            sb.append("Cuota mensual:     $").append(String.format("%.2f", s.calcularCuotaMensual())).append("\n");
            sb.append("Recargo pendiente: $").append(String.format("%.2f", s.getRecargoPendiente()))
                    .append("  (se cobra en la próxima cuota)\n");
            sb.append("Comprobantes:      ").append(comprobantes).append("\n\n");

            if (s.getAdvertencias().isEmpty()) {
                sb.append("Advertencias: ninguna");
            } else {
                sb.append("Advertencias:\n");
                for (String adv : s.getAdvertencias()) {
                    sb.append(" - ").append(adv).append("\n");
                }
            }

            JTextArea area = new JTextArea(sb.toString(), 14, 52);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);

            // Los botones cambian según el estado actual del socio
            List<String> acciones = new ArrayList<>();
            acciones.add("Cerrar");
            acciones.add(s.isSancionado() ? "Levantar sanción" : "Sancionar");
            acciones.add(s.isActivo() ? "Dar de baja" : "Reactivar");
            String[] opciones = acciones.toArray(new String[0]);

            int elegido = JOptionPane.showOptionDialog(this, new JScrollPane(area), "Perfil del socio",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (elegido <= 0) {
                return; // Cerrar o cerró la ventana
            }
            ejecutarAccion(s, opciones[elegido]);

        } catch (NegocioException e) {
            mostrarError(e.getMessage());
        }
    }

    private void ejecutarAccion(Socio s, String accion) {
        try {
            String mensaje;
            switch (accion) {
                case "Sancionar":
                    String motivo = JOptionPane.showInputDialog(this, "Motivo de la sanción:",
                            "Sancionar a " + s.getNombreCompleto(), JOptionPane.QUESTION_MESSAGE);
                    if (motivo == null) {
                        return; // canceló
                    }
                    if (motivo.trim().isEmpty()) {
                        mostrarError("Tenés que indicar el motivo de la sanción.");
                        return;
                    }
                    logica.sancionarSocio(s.getDni(), motivo.trim());
                    mensaje = "Sanción aplicada a " + s.getNombreCompleto() + ".";
                    break;
                case "Levantar sanción":
                    logica.levantarSancion(s.getDni());
                    mensaje = "Sanción levantada. " + s.getNombreCompleto() + " ya puede volver a operar.";
                    break;
                case "Dar de baja":
                    if (JOptionPane.showConfirmDialog(this,
                            "¿Dar de baja a " + s.getNombreCompleto() + "?\nNo podrá ingresar, alquilar ni reservar.",
                            "Confirmar baja", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                        return;
                    }
                    logica.cambiarEstadoActivo(s.getDni(), false);
                    mensaje = s.getNombreCompleto() + " fue dado de baja.";
                    break;
                default: // Reactivar
                    logica.cambiarEstadoActivo(s.getDni(), true);
                    mensaje = s.getNombreCompleto() + " fue reactivado.";
            }
            JOptionPane.showMessageDialog(this, mensaje, "Listo", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(reportes.sociosPorApellidoNombre());
        } catch (NegocioException | PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.ERROR_MESSAGE);
    }
    private void nuevoSocio() {
        JComboBox<String> cmbTipo = new JComboBox<>(new String[] {"Estandar", "VIP", "Vitalicio"});
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtExtra = new JTextField("0");
        Object[] campos = {
            "Categoría:", cmbTipo, "DNI:", txtDni, "Nombre:", txtNombre, "Apellido:", txtApellido,
            "Cargo adicional $ (VIP) o descuento % (Vitalicio):", txtExtra
        };
        if (JOptionPane.showConfirmDialog(this, campos, "Nuevo socio",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            double extra = Double.parseDouble(txtExtra.getText().trim().replace(',', '.'));
            switch (cmbTipo.getSelectedIndex()) {
                case 0:
                    logica.registrarSocioEstandar(txtDni.getText(), txtNombre.getText(), txtApellido.getText());
                    break;
                case 1:
                    logica.registrarSocioVip(txtDni.getText(), txtNombre.getText(), txtApellido.getText(), extra);
                    break;
                default:
                    logica.registrarSocioVitalicio(txtDni.getText(), txtNombre.getText(), txtApellido.getText(), extra);
            }
            cargarTabla(reportes.sociosPorApellidoNombre());
        } catch (NumberFormatException e) {
            mostrarError("El valor adicional debe ser un número.");
        } catch (NegocioException | PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NuevoSocio;
    private javax.swing.JButton btnBuscardni;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBusquedaDni;
    // End of variables declaration//GEN-END:variables
}
