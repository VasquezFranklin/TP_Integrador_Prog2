
package Presentacion;

import LogicaNegocio.Modelos.Factura;
import LogicaNegocio.Modelos.Socio;
import LogicaNegocio.Reportes;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class ReportesPanel extends javax.swing.JPanel {

    private final Reportes reportes;

    public ReportesPanel(Reportes reportes) {
        this.reportes = reportes;
        initComponents();

        // Misma medida que las otras tarjetas, para que el monto no agrande la primera
        background.setPreferredSize(new java.awt.Dimension(338, 158));

        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
            "Apellido y Nombre", "Monto de Cuota (mayor a menor)", "Morosos / Sancionados"}));
        jComboBox1.addActionListener(evt -> cargarTabla());

        actualizarIndicadores();
        cargarTabla();
        
    }

    private void actualizarIndicadores() {
        // Recaudación del mes actual
        String periodo = Factura.periodoDe(LocalDate.now());
        txtMontoRe.setText("$ " + String.format("%.2f", reportes.recaudacionMensual(periodo)));

        // Socios morosos o sancionados
        txtCantSocios.setText(String.valueOf(reportes.sociosMorososOSancionados().size()));

        // Ocupación promedio de todas las actividades
        List<Reportes.Ocupacion> ocupacion = reportes.ocupacionDeActividades();
        double suma = 0;
        for (Reportes.Ocupacion o : ocupacion) {
            suma += o.getPorcentaje();
        }
        double promedio = ocupacion.isEmpty() ? 0 : suma / ocupacion.size();
        txtPorcentaje.setText(String.format("%.1f %%", promedio));
    }

    private void cargarTabla() {
        List<Socio> lista;
        switch (jComboBox1.getSelectedIndex()) {
            case 1:
                lista = reportes.sociosPorMontoDeCuota();
                break;
            case 2:
                lista = reportes.sociosMorososOSancionados();
                break;
            default:
                lista = reportes.sociosPorApellidoNombre();
        }
        llenarTabla(lista);
    }

    private void llenarTabla(List<Socio> socios) {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (Socio s : socios) {
            modelo.addRow(new Object[] {
                s.getNombreCompleto(),
                s.getTipo(),
                String.format("$%.2f", s.calcularCuotaMensual())
            });
        }
    }
    
    private void buscar() {
        String apellido = txtBuscarApellido.getText().trim();
        if (apellido.isEmpty()) {
            cargarTabla();
            return;
        }
        List<Socio> encontrados = reportes.buscarPorApellido(apellido);
        llenarTabla(encontrados);
        if (encontrados.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se encontraron socios con el apellido \"" + apellido + "\".",
                    "Sin resultados", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        background = new javax.swing.JPanel();
        txtRecaudacion = new javax.swing.JLabel();
        txtMontoRe = new javax.swing.JLabel();
        background2 = new javax.swing.JPanel();
        txtMorosos = new javax.swing.JLabel();
        txtCantSocios = new javax.swing.JLabel();
        background3 = new javax.swing.JPanel();
        txtOcupacion = new javax.swing.JLabel();
        txtPorcentaje = new javax.swing.JLabel();
        OrdenarPor = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        OrdenarPor1 = new javax.swing.JLabel();
        txtBuscarApellido = new javax.swing.JTextField();
        buttonBuscar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1090, 768));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1090, 768));

        background.setBackground(new java.awt.Color(102, 153, 255));

        txtRecaudacion.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        txtRecaudacion.setForeground(new java.awt.Color(255, 255, 255));
        txtRecaudacion.setText("Recaudacion del mes");

        txtMontoRe.setFont(new java.awt.Font("Faculty Glyphic", 0, 36)); // NOI18N
        txtMontoRe.setForeground(new java.awt.Color(255, 255, 255));
        txtMontoRe.setText("$");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRecaudacion)
                    .addComponent(txtMontoRe))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtRecaudacion)
                .addGap(18, 18, 18)
                .addComponent(txtMontoRe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background2.setBackground(new java.awt.Color(102, 153, 255));
        background2.setPreferredSize(new java.awt.Dimension(338, 158));

        txtMorosos.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        txtMorosos.setForeground(new java.awt.Color(255, 255, 255));
        txtMorosos.setText("Socios morosos");

        txtCantSocios.setFont(new java.awt.Font("Faculty Glyphic", 0, 36)); // NOI18N
        txtCantSocios.setForeground(new java.awt.Color(255, 255, 255));
        txtCantSocios.setText("0");

        javax.swing.GroupLayout background2Layout = new javax.swing.GroupLayout(background2);
        background2.setLayout(background2Layout);
        background2Layout.setHorizontalGroup(
            background2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMorosos)
                    .addComponent(txtCantSocios))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        background2Layout.setVerticalGroup(
            background2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtMorosos)
                .addGap(18, 18, 18)
                .addComponent(txtCantSocios)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        background3.setBackground(new java.awt.Color(102, 153, 255));
        background3.setPreferredSize(new java.awt.Dimension(338, 158));

        txtOcupacion.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        txtOcupacion.setForeground(new java.awt.Color(255, 255, 255));
        txtOcupacion.setText("Ocupacion promedio");

        txtPorcentaje.setFont(new java.awt.Font("Faculty Glyphic", 0, 36)); // NOI18N
        txtPorcentaje.setForeground(new java.awt.Color(255, 255, 255));
        txtPorcentaje.setText("%");

        javax.swing.GroupLayout background3Layout = new javax.swing.GroupLayout(background3);
        background3.setLayout(background3Layout);
        background3Layout.setHorizontalGroup(
            background3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOcupacion)
                    .addComponent(txtPorcentaje))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        background3Layout.setVerticalGroup(
            background3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtOcupacion)
                .addGap(18, 18, 18)
                .addComponent(txtPorcentaje)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        OrdenarPor.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        OrdenarPor.setForeground(new java.awt.Color(0, 0, 0));
        OrdenarPor.setText("Ordenar por:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apellido y Nombre", "Monto de Cuota" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre y apellido", "Categoria", "Cuota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        }

        OrdenarPor1.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        OrdenarPor1.setForeground(new java.awt.Color(0, 0, 0));
        OrdenarPor1.setText("Buscar por apellido:");

        txtBuscarApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarApellidoActionPerformed(evt);
            }
        });

        buttonBuscar.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(background2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(background3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(149, 149, 149)
                                .addComponent(txtBuscarApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(OrdenarPor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OrdenarPor1)
                        .addGap(326, 326, 326))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(background3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(background2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrdenarPor)
                    .addComponent(OrdenarPor1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(txtBuscarApellido))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
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

    private void txtBuscarApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarApellidoActionPerformed
        buscar();
    }//GEN-LAST:event_txtBuscarApellidoActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_buttonBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OrdenarPor;
    private javax.swing.JLabel OrdenarPor1;
    private javax.swing.JPanel background;
    private javax.swing.JPanel background2;
    private javax.swing.JPanel background3;
    private javax.swing.JButton buttonBuscar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscarApellido;
    private javax.swing.JLabel txtCantSocios;
    private javax.swing.JLabel txtMontoRe;
    private javax.swing.JLabel txtMorosos;
    private javax.swing.JLabel txtOcupacion;
    private javax.swing.JLabel txtPorcentaje;
    private javax.swing.JLabel txtRecaudacion;
    // End of variables declaration//GEN-END:variables
}
