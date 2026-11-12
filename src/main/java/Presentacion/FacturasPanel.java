
package Presentacion;

import LogicaNegocio.Excepciones.NegocioException;
import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Logica;
import LogicaNegocio.Modelos.Factura;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class FacturasPanel extends javax.swing.JPanel {

    private static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    private final Logica logica;

    public FacturasPanel(Logica logica) {
        this.logica = logica;
        initComponents();
        txtDni.setText("");

        // Años: los últimos 4, con el actual seleccionado
        int anioActual = LocalDate.now().getYear();
        DefaultComboBoxModel<String> anios = new DefaultComboBoxModel<>();
        for (int a = anioActual - 3; a <= anioActual; a++) {
            anios.addElement(String.valueOf(a));
        }
        boxAno.setModel(anios);
        boxAno.setSelectedItem(String.valueOf(anioActual));

        // Meses: Enero..Diciembre, con el actual seleccionado
        boxMes.setModel(new DefaultComboBoxModel<>(MESES));
        boxMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);

        textTotalPeriodo.setText("Total del periodo $ 0.00");

        buttonConsultar.addActionListener(evt -> consultar());
        txtDni.addActionListener(evt -> consultar());          // también con Enter

        // Doble clic en una fila = ver el comprobante completo
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verComprobante();
                }
            }
        });
    }

    private void consultar() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        textTotalPeriodo.setText("Total del periodo $ 0.00");
        try {
            int anio = Integer.parseInt((String) boxAno.getSelectedItem());
            int mes = boxMes.getSelectedIndex() + 1;
            String periodo = Factura.periodoDe(LocalDate.of(anio, mes, 1));

            double total = 0;
            for (Factura f : logica.obtenerFacturasDeSocio(txtDni.getText()).getOrDefault(
                    periodo, java.util.Collections.<Factura>emptyList())) {
                modelo.addRow(new Object[] {
                    f.getNumero(), f.getConcepto(), f.getFecha(), String.format("$%.2f", f.getMonto())
                });
                total += f.getMonto();
            }
            textTotalPeriodo.setText("Total del periodo $ " + String.format("%.2f", total));
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "El socio no tiene comprobantes en " + MESES[mes - 1]
                        + " de " + anio + ".", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Atención", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verComprobante() {
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            return;
        }
        int numero = Integer.parseInt(String.valueOf(jTable1.getValueAt(fila, 0)));
        Factura f = logica.buscarFacturaPorNumero(numero);
        if (f == null) {
            return;
        }
        JTextArea area = new JTextArea(f.comprobante());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, area, "Comprobante", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void liquidar() {
        try {
            Factura f = logica.liquidarCuota(txtDni.getText());
            JTextArea area = new JTextArea(f.comprobante());
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
            area.setEditable(false);
            JOptionPane.showMessageDialog(this, area, "Cuota liquidada", JOptionPane.PLAIN_MESSAGE);
            boxAno.setSelectedItem(String.valueOf(LocalDate.now().getYear()));
            boxMes.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
            consultar();
        } catch (NegocioException | PersistenciaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Atención", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtDni = new javax.swing.JTextField();
        boxAno = new javax.swing.JComboBox<>();
        boxMes = new javax.swing.JComboBox<>();
        tittleDni = new javax.swing.JLabel();
        tittleAno = new javax.swing.JLabel();
        tittleMes = new javax.swing.JLabel();
        buttonConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        textTotalPeriodo = new javax.swing.JLabel();
        buttonLiquidar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1090, 768));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1090, 768));

        boxAno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        boxMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tittleDni.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleDni.setForeground(new java.awt.Color(0, 0, 0));
        tittleDni.setText("DNI");

        tittleAno.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleAno.setForeground(new java.awt.Color(0, 0, 0));
        tittleAno.setText("Año");

        tittleMes.setFont(new java.awt.Font("Faculty Glyphic", 1, 18)); // NOI18N
        tittleMes.setForeground(new java.awt.Color(0, 0, 0));
        tittleMes.setText("Mes");

        buttonConsultar.setBackground(new java.awt.Color(255, 255, 255));
        buttonConsultar.setFont(new java.awt.Font("Faculty Glyphic", 0, 16)); // NOI18N
        buttonConsultar.setForeground(new java.awt.Color(0, 0, 0));
        buttonConsultar.setText("Consultar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "N°", "Concepto", "Fecha", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }

        textTotalPeriodo.setFont(new java.awt.Font("Faculty Glyphic", 0, 18)); // NOI18N
        textTotalPeriodo.setForeground(new java.awt.Color(0, 0, 0));
        textTotalPeriodo.setText("Total del periodo $");

        buttonLiquidar.setBackground(new java.awt.Color(255, 255, 255));
        buttonLiquidar.setFont(new java.awt.Font("Faculty Glyphic", 0, 16)); // NOI18N
        buttonLiquidar.setForeground(new java.awt.Color(0, 0, 0));
        buttonLiquidar.setText("Liquidar Cuota");
        buttonLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLiquidarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textTotalPeriodo)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tittleDni)
                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tittleAno)
                                .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tittleMes)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(38, 38, 38)
                                    .addComponent(buttonLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(tittleDni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tittleAno)
                    .addComponent(tittleMes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxAno, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textTotalPeriodo)
                .addContainerGap(50, Short.MAX_VALUE))
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

    private void buttonLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLiquidarActionPerformed
        liquidar();
    }//GEN-LAST:event_buttonLiquidarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxAno;
    private javax.swing.JComboBox<String> boxMes;
    private javax.swing.JButton buttonConsultar;
    private javax.swing.JButton buttonLiquidar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel textTotalPeriodo;
    private javax.swing.JLabel tittleAno;
    private javax.swing.JLabel tittleDni;
    private javax.swing.JLabel tittleMes;
    private javax.swing.JTextField txtDni;
    // End of variables declaration//GEN-END:variables
}
