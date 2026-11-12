
package Presentacion;

import LogicaNegocio.Excepciones.NegocioException;
import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Logica;
import LogicaNegocio.Modelos.Alquiler;
import LogicaNegocio.Modelos.Equipamiento;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AlquileresPanel extends javax.swing.JPanel {

    private final Logica logica;
    // Códigos de equipamiento, en el mismo orden que los ítems del combo
    private final List<String> codigosCombo = new ArrayList<>();

    public AlquileresPanel(Logica logica) {
        this.logica = logica;
        initComponents();
        AlquilerDni.setText("");
        bottonRegistrar.addActionListener(evt -> registrar());
        refrescar();
    }

    private void refrescar() {
        cargarCombo();
        cargarTabla();
    }

    // Solo se ofrece el equipamiento que está disponible
    private void cargarCombo() {
        List<Equipamiento> libres = new ArrayList<>();
        for (Equipamiento e : logica.obtenerTodoElEquipamiento()) {
            if (e.isDisponible()) {
                libres.add(e);
            }
        }
        Collections.sort(libres, (a, b) -> a.getCodigo().compareTo(b.getCodigo()));

        codigosCombo.clear();
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (Equipamiento e : libres) {
            codigosCombo.add(e.getCodigo());
            modelo.addElement(e.getCodigo() + " - " + e.getDescripcion()
                    + "  ($" + String.format("%.2f", e.getPrecioDiario()) + " por día)");
        }
        if (libres.isEmpty()) {
            modelo.addElement("(no hay equipamiento disponible)");
        }
        EquipamientoBox.setModel(modelo);
    }

    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        List<Alquiler> todos = new ArrayList<>(logica.obtenerTodosLosAlquileres());
        Collections.reverse(todos); // los más recientes arriba
        for (Alquiler a : todos) {
            modelo.addRow(new Object[] {
                a.getId(),
                a.getNombreSocio(),
                a.getCodigoEquipamiento() + " - " + a.getDescripcionEquipamiento(),
                a.getFechaLimiteDevolucion(),
                estadoDe(a)
            });
        }
    }

    private String estadoDe(Alquiler a) {
        if (a.isEstaDevuelto()) {
            return "Devuelto";
        }
        return LocalDate.now().isAfter(a.getFechaLimiteDevolucion()) ? "VENCIDO" : "Pendiente";
    }

    private void registrar() {
        int idx = EquipamientoBox.getSelectedIndex();
        if (idx < 0 || idx >= codigosCombo.size()) {
            mostrarError("No hay equipamiento disponible para alquilar.");
            return;
        }
        int dias = (Integer) jSpinner1.getValue();
        try {
            Alquiler a = logica.registrarAlquiler(AlquilerDni.getText(), codigosCombo.get(idx), dias);
            JOptionPane.showMessageDialog(this,
                    "Alquiler registrado.\nDevolver antes del " + a.getFechaLimiteDevolucion()
                    + "\nCosto: $" + String.format("%.2f", a.getCostoTotal()),
                    "Listo", JOptionPane.INFORMATION_MESSAGE);
            AlquilerDni.setText("");
            refrescar();
        } catch (NegocioException | PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.ERROR_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        AlquilerDni = new javax.swing.JTextField();
        tittleEquipamiento = new javax.swing.JLabel();
        tittleDni = new javax.swing.JLabel();
        EquipamientoBox = new javax.swing.JComboBox<>();
        jSpinner1 = new javax.swing.JSpinner();
        tittleTiempo = new javax.swing.JLabel();
        tIttleDias = new javax.swing.JLabel();
        bottonDevolucion = new javax.swing.JButton();
        bottonRegistrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1090, 768));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1090, 768));

        tittleEquipamiento.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleEquipamiento.setForeground(new java.awt.Color(0, 0, 0));
        tittleEquipamiento.setText("Equipamiento");

        tittleDni.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleDni.setForeground(new java.awt.Color(0, 0, 0));
        tittleDni.setText("DNI");

        EquipamientoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EquipamientoBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquipamientoBoxActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));

        tittleTiempo.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleTiempo.setForeground(new java.awt.Color(0, 0, 0));
        tittleTiempo.setText("Tiempo de alquiler");

        tIttleDias.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        tIttleDias.setForeground(new java.awt.Color(0, 0, 0));
        tIttleDias.setText("Dias de alquiler");

        bottonDevolucion.setBackground(new java.awt.Color(255, 255, 255));
        bottonDevolucion.setFont(new java.awt.Font("Faculty Glyphic", 0, 12)); // NOI18N
        bottonDevolucion.setForeground(new java.awt.Color(0, 0, 0));
        bottonDevolucion.setText("Registrar Devolucion");
        bottonDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bottonDevolucionActionPerformed(evt);
            }
        });

        bottonRegistrar.setBackground(new java.awt.Color(255, 255, 255));
        bottonRegistrar.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        bottonRegistrar.setForeground(new java.awt.Color(0, 0, 0));
        bottonRegistrar.setText("Registrar Alquiler");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "N°", "Socio", "Elemento", "Vencimiento", "Estado"
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
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1031, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tittleDni)
                            .addComponent(AlquilerDni, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EquipamientoBox, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bottonDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(614, 614, 614)
                                .addComponent(bottonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tittleEquipamiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tIttleDias)
                            .addComponent(tittleTiempo)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(285, 285, 285))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(tittleDni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AlquilerDni, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tittleEquipamiento)
                    .addComponent(tittleTiempo))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EquipamientoBox, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bottonRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(bottonDevolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tIttleDias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EquipamientoBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EquipamientoBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EquipamientoBoxActionPerformed

    private void bottonDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bottonDevolucionActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            mostrarError("Seleccioná un alquiler de la tabla.");
            return;
        }
        int id = Integer.parseInt(String.valueOf(jTable1.getValueAt(fila, 0)));

        String texto = JOptionPane.showInputDialog(this,
                "Fecha real de devolución (yyyy-MM-dd).\nDejá vacío para usar la fecha de hoy:",
                "Registrar devolución", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return; // canceló
        }
        try {
            LocalDate fecha = texto.trim().isEmpty() ? LocalDate.now() : LocalDate.parse(texto.trim());
            int mora = logica.registrarDevolucion(id, fecha);
            if (mora == 0) {
                JOptionPane.showMessageDialog(this, "Devolución registrada en término.", "Listo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Devolución tardía: " + mora + " día(s) de mora.\n"
                        + "El socio quedó SANCIONADO y se registró una advertencia.\n"
                        + "El recargo de $" + String.format("%.2f", mora * Logica.RECARGO_POR_DIA_MORA)
                        + " se cobrará en su próxima cuota.",
                        "Sanción aplicada", JOptionPane.WARNING_MESSAGE);
            }
            refrescar();
        } catch (DateTimeParseException e) {
            mostrarError("Fecha inválida. Usá el formato yyyy-MM-dd (por ejemplo 2026-11-20).");
        } catch (NegocioException | PersistenciaException e) {
            mostrarError(e.getMessage());
        }
    }//GEN-LAST:event_bottonDevolucionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AlquilerDni;
    private javax.swing.JComboBox<String> EquipamientoBox;
    private javax.swing.JButton bottonDevolucion;
    private javax.swing.JButton bottonRegistrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel tIttleDias;
    private javax.swing.JLabel tittleDni;
    private javax.swing.JLabel tittleEquipamiento;
    private javax.swing.JLabel tittleTiempo;
    // End of variables declaration//GEN-END:variables
}
