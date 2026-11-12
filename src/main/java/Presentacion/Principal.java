package Presentacion;

import LogicaNegocio.Logica;
import LogicaNegocio.Reportes;
import Presentacion.SocioPanel;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Principal extends javax.swing.JFrame {

    private final Logica logica;
    private final Reportes reportes;

    public Principal(Logica logica, Reportes reportes) {
        this.logica = logica;
        this.reportes = reportes;
        initComponents();
        InitContent();
    }

    private void InitContent() {
        content.setLayout(new BorderLayout());   // ver punto 2
        ShowJpanel(new SocioPanel(logica, reportes));
    }
    private void ShowJpanel(JPanel p) {
    content.removeAll();
    content.add(p, BorderLayout.CENTER);
    content.revalidate();
    content.repaint();
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Bandgrood = new javax.swing.JPanel();
        panelIzquierdo = new javax.swing.JPanel();
        Deportivo = new javax.swing.JLabel();
        buttonSocios = new javax.swing.JButton();
        buttonMolinete = new javax.swing.JButton();
        buttonActividades = new javax.swing.JButton();
        buttonAlquileres = new javax.swing.JButton();
        buttonFacturas = new javax.swing.JButton();
        buttonReportes = new javax.swing.JButton();
        txtLobos = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Bandgrood.setBackground(new java.awt.Color(255, 255, 255));
        Bandgrood.setPreferredSize(new java.awt.Dimension(1360, 768));

        panelIzquierdo.setBackground(new java.awt.Color(102, 153, 255));

        Deportivo.setFont(new java.awt.Font("Faculty Glyphic", 1, 24)); // NOI18N
        Deportivo.setForeground(new java.awt.Color(255, 255, 255));
        Deportivo.setText("Deportivo");

        buttonSocios.setBackground(new java.awt.Color(102, 153, 255));
        buttonSocios.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonSocios.setForeground(new java.awt.Color(255, 255, 255));
        buttonSocios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/socios_32.png"))); // NOI18N
        buttonSocios.setText("Socios");
        buttonSocios.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonSocios.setBorderPainted(false);
        buttonSocios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonSocios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonSocios.setIconTextGap(15);
        buttonSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSociosActionPerformed(evt);
            }
        });

        buttonMolinete.setBackground(new java.awt.Color(102, 153, 255));
        buttonMolinete.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonMolinete.setForeground(new java.awt.Color(255, 255, 255));
        buttonMolinete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/molinete_32.png"))); // NOI18N
        buttonMolinete.setText("Molinete");
        buttonMolinete.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonMolinete.setBorderPainted(false);
        buttonMolinete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonMolinete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonMolinete.setIconTextGap(15);
        buttonMolinete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMolineteActionPerformed(evt);
            }
        });

        buttonActividades.setBackground(new java.awt.Color(102, 153, 255));
        buttonActividades.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonActividades.setForeground(new java.awt.Color(255, 255, 255));
        buttonActividades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/actividades_32.png"))); // NOI18N
        buttonActividades.setText("Actividades");
        buttonActividades.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonActividades.setBorderPainted(false);
        buttonActividades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonActividades.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonActividades.setIconTextGap(15);
        buttonActividades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActividadesActionPerformed(evt);
            }
        });

        buttonAlquileres.setBackground(new java.awt.Color(102, 153, 255));
        buttonAlquileres.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonAlquileres.setForeground(new java.awt.Color(255, 255, 255));
        buttonAlquileres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/alquileres_32.png"))); // NOI18N
        buttonAlquileres.setText("Alquileres");
        buttonAlquileres.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonAlquileres.setBorderPainted(false);
        buttonAlquileres.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonAlquileres.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonAlquileres.setIconTextGap(15);
        buttonAlquileres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlquileresActionPerformed(evt);
            }
        });

        buttonFacturas.setBackground(new java.awt.Color(102, 153, 255));
        buttonFacturas.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonFacturas.setForeground(new java.awt.Color(255, 255, 255));
        buttonFacturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/facturas_32.png"))); // NOI18N
        buttonFacturas.setText("Facturas ");
        buttonFacturas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonFacturas.setBorderPainted(false);
        buttonFacturas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonFacturas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonFacturas.setIconTextGap(15);
        buttonFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFacturasActionPerformed(evt);
            }
        });

        buttonReportes.setBackground(new java.awt.Color(102, 153, 255));
        buttonReportes.setFont(new java.awt.Font("Faculty Glyphic", 1, 16)); // NOI18N
        buttonReportes.setForeground(new java.awt.Color(255, 255, 255));
        buttonReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reportes_32.png"))); // NOI18N
        buttonReportes.setText("Reportes");
        buttonReportes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        buttonReportes.setBorderPainted(false);
        buttonReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonReportes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonReportes.setIconTextGap(15);
        buttonReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReportesActionPerformed(evt);
            }
        });

        txtLobos.setFont(new java.awt.Font("Faculty Glyphic", 1, 24)); // NOI18N
        txtLobos.setForeground(new java.awt.Color(255, 255, 255));
        txtLobos.setText("Los Lobos");

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lobo.png"))); // NOI18N

        javax.swing.GroupLayout panelIzquierdoLayout = new javax.swing.GroupLayout(panelIzquierdo);
        panelIzquierdo.setLayout(panelIzquierdoLayout);
        panelIzquierdoLayout.setHorizontalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonSocios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonMolinete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonActividades, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
            .addComponent(buttonAlquileres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonFacturas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Deportivo)
                    .addComponent(txtLobos))
                .addGap(27, 27, 27)
                .addComponent(Logo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIzquierdoLayout.setVerticalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                        .addComponent(Deportivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLobos))
                    .addComponent(Logo))
                .addGap(57, 57, 57)
                .addComponent(buttonSocios, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonMolinete, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonAlquileres, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonFacturas, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        content.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BandgroodLayout = new javax.swing.GroupLayout(Bandgrood);
        Bandgrood.setLayout(BandgroodLayout);
        BandgroodLayout.setHorizontalGroup(
            BandgroodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BandgroodLayout.createSequentialGroup()
                .addComponent(panelIzquierdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BandgroodLayout.setVerticalGroup(
            BandgroodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIzquierdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bandgrood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bandgrood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void buttonSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSociosActionPerformed
        ShowJpanel(new SocioPanel(logica, reportes));
    }//GEN-LAST:event_buttonSociosActionPerformed

    private void buttonMolineteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMolineteActionPerformed
        ShowJpanel(new MolinetePanel(logica));
    }//GEN-LAST:event_buttonMolineteActionPerformed

    private void buttonAlquileresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlquileresActionPerformed
        ShowJpanel(new AlquileresPanel(logica));
    }//GEN-LAST:event_buttonAlquileresActionPerformed

    private void buttonFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFacturasActionPerformed
        ShowJpanel(new FacturasPanel(logica));
    }//GEN-LAST:event_buttonFacturasActionPerformed

    private void buttonReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReportesActionPerformed
        ShowJpanel(new ReportesPanel(reportes));
    }//GEN-LAST:event_buttonReportesActionPerformed

    private void buttonActividadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActividadesActionPerformed
        ShowJpanel(new ActividadesPanel(logica));
    }//GEN-LAST:event_buttonActividadesActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Bandgrood;
    private javax.swing.JLabel Deportivo;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton buttonActividades;
    private javax.swing.JButton buttonAlquileres;
    private javax.swing.JButton buttonFacturas;
    private javax.swing.JButton buttonMolinete;
    private javax.swing.JButton buttonReportes;
    private javax.swing.JButton buttonSocios;
    private javax.swing.JPanel content;
    private javax.swing.JPanel panelIzquierdo;
    private javax.swing.JLabel txtLobos;
    // End of variables declaration//GEN-END:variables
}
