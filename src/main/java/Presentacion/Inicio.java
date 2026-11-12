package Presentacion;

import Datos.AccesoDatos;
import LogicaNegocio.Excepciones.NegocioException;
import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Logica;
import LogicaNegocio.Modelos.Actividad;
import LogicaNegocio.Modelos.Equipamiento;
import LogicaNegocio.Reportes;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;

public class Inicio {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            AccesoDatos datos = new AccesoDatos();
            Logica logica = new Logica(datos);
            Reportes reportes = new Reportes(logica);

            if (logica.obtenerTodosLosSocios().isEmpty()) {
                precargarDatosDePrueba(logica);
            }

            java.awt.EventQueue.invokeLater(()->new Principal(logica, reportes).setVisible(true));

        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo acceder a los datos del sistema: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void precargarDatosDePrueba(Logica l) throws NegocioException, PersistenciaException {
        // Socios de las tres categorias y con distintos estados para probar el molinete
        l.registrarSocioEstandar("30111222", "Franco", "Mastantuono");
        l.registrarSocioVip("30222333", "Luisa", "Lein", 2500.0);
        l.registrarSocioVitalicio("20333444", "Fernando", "Figueroa", 50.0);
        l.registrarSocioEstandar("33444555", "Carlos", "Tevez");
        l.registrarSocioVip("35555666", "Enzo", "Perez", 2000.0);
        l.registrarSocioEstandar("27666777", "Lionel", "Messi");
        l.marcarCuotaVencida("33444555"); // moroso
        l.sancionarSocio("35555666", "Devolucion tardia de equipamiento"); // sancionado
        l.cambiarEstadoActivo("27666777", false); // inactivo

        // Catalogo de equipamiento
        l.agregarEquipamiento(new Equipamiento("EQ-101", "Raqueta de tenis Head", 800.0));
        l.agregarEquipamiento(new Equipamiento("EQ-102", "Raqueta de tenis Wilson", 800.0));
        l.agregarEquipamiento(new Equipamiento("LK-01", "Casillero (locker) N 1", 300.0));
        l.agregarEquipamiento(new Equipamiento("LK-02", "Casillero (locker) N 2", 300.0));
        l.agregarEquipamiento(new Equipamiento("PE-01", "Pelotas de padel (tubo x3)", 200.0));

        // Actividades con cupo limitado
        l.agregarActividad(new Actividad("NAT-01", "Natacion", "Lunes 10:00", 3, 1500.0));
        l.agregarActividad(new Actividad("SPIN-01", "Spin", "Martes 18:00", 10, 1200.0));
        l.agregarActividad(new Actividad("TEN-01", "Tenis", "Jueves 17:00", 4, 1800.0));

        // Reservas, cuotas facturadas y un alquiler en curso para tener historial y reportes
        l.registrarReserva("NAT-01", "30111222");
        l.registrarReserva("NAT-01", "30222333");
        l.liquidarCuota("30111222");
        l.liquidarCuota("30222333");
        l.liquidarCuota("20333444");
        l.registrarAlquiler("30222333", "EQ-101", 3);
    }
}
