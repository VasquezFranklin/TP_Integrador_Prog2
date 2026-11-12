
package Presentacion;

import LogicaNegocio.Excepciones.NegocioException;
import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Logica;
import LogicaNegocio.Modelos.Actividad;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ActividadesPanel extends javax.swing.JPanel {
    private final Logica logica;

    public ActividadesPanel(Logica logica) {
        this.logica = logica;
        initComponents();

        InscribirNatacion.addActionListener(evt -> inscribir("NAT-01"));
        InscribirSpin.addActionListener(evt -> inscribir("SPIN-01"));
        InscribirTenis.addActionListener(evt -> inscribir("TEN-01"));

        refrescar();
    }

    private void refrescar() {
        actualizarTarjeta("NAT-01", HorariosNatacion, CuposDisponiblesNatacion);
        actualizarTarjeta("SPIN-01", HoraciosSpin, jLabel8);
        actualizarTarjeta("TEN-01", HorariosTenis, CuposDisponiblesTenis);
    }

    private void actualizarTarjeta(String codigo, JLabel lblHorario, JLabel lblCupos) {
        Actividad a = logica.buscarActividad(codigo);
        if (a == null) {
            lblHorario.setText("Horarios: -");
            lblCupos.setText("No disponible");
            return;
        }
        int inscriptos = logica.obtenerReservasPorActividad(codigo).size();
        lblHorario.setText("Horarios: " + a.getHorario());
        lblCupos.setText("Cupos Disponibles: " + (a.getCupoMaximo() - inscriptos)
                + " de " + a.getCupoMaximo());
    }

    private void inscribir(String codigo) {
        String dni = JOptionPane.showInputDialog(this, "DNI del socio a inscribir:",
                "Inscribir socio", JOptionPane.QUESTION_MESSAGE);
        if (dni == null) {
            return; // canceló
        }
        try {
            logica.registrarReserva(codigo, dni);
            JOptionPane.showMessageDialog(this, "Inscripción confirmada.", "Listo",
                    JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (NegocioException | PersistenciaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Atención", JOptionPane.ERROR_MESSAGE);
        }
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        HorariosNatacion = new javax.swing.JLabel();
        CuposDisponiblesNatacion = new javax.swing.JLabel();
        InscribirNatacion = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        HoraciosSpin = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        InscribirSpin = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        HorariosTenis = new javax.swing.JLabel();
        CuposDisponiblesTenis = new javax.swing.JLabel();
        InscribirTenis = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(340, 315));

        jLabel1.setFont(new java.awt.Font("Faculty Glyphic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Natacion");

        HorariosNatacion.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        HorariosNatacion.setForeground(new java.awt.Color(0, 0, 0));
        HorariosNatacion.setText("Horarios:");

        CuposDisponiblesNatacion.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        CuposDisponiblesNatacion.setForeground(new java.awt.Color(0, 0, 0));
        CuposDisponiblesNatacion.setText("Cupos Disponibles");

        InscribirNatacion.setBackground(new java.awt.Color(255, 255, 255));
        InscribirNatacion.setForeground(new java.awt.Color(0, 0, 0));
        InscribirNatacion.setText("Inscribir Socio");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/natacion.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(InscribirNatacion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HorariosNatacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CuposDisponiblesNatacion, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addContainerGap(73, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(HorariosNatacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CuposDisponiblesNatacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InscribirNatacion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jLabel11)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Faculty Glyphic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Spin");

        HoraciosSpin.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        HoraciosSpin.setForeground(new java.awt.Color(0, 0, 0));
        HoraciosSpin.setText("Horarios:");

        jLabel8.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Cupos Disponibles");

        InscribirSpin.setBackground(new java.awt.Color(255, 255, 255));
        InscribirSpin.setForeground(new java.awt.Color(0, 0, 0));
        InscribirSpin.setText("Inscribir Socio");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/spin.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(InscribirSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HoraciosSpin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HoraciosSpin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InscribirSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(32, 32, 32))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Faculty Glyphic", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tenis");

        HorariosTenis.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        HorariosTenis.setForeground(new java.awt.Color(0, 0, 0));
        HorariosTenis.setText("Horarios:");

        CuposDisponiblesTenis.setFont(new java.awt.Font("Faculty Glyphic", 0, 14)); // NOI18N
        CuposDisponiblesTenis.setForeground(new java.awt.Color(0, 0, 0));
        CuposDisponiblesTenis.setText("Cupos Disponibles");

        InscribirTenis.setBackground(new java.awt.Color(255, 255, 255));
        InscribirTenis.setForeground(new java.awt.Color(0, 0, 0));
        InscribirTenis.setText("Inscribir Socio");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tenis.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InscribirTenis, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HorariosTenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CuposDisponiblesTenis, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(33, 33, 33))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HorariosTenis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CuposDisponiblesTenis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InscribirTenis, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                .addContainerGap(422, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CuposDisponiblesNatacion;
    private javax.swing.JLabel CuposDisponiblesTenis;
    private javax.swing.JLabel HoraciosSpin;
    private javax.swing.JLabel HorariosNatacion;
    private javax.swing.JLabel HorariosTenis;
    private javax.swing.JButton InscribirNatacion;
    private javax.swing.JButton InscribirSpin;
    private javax.swing.JButton InscribirTenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
