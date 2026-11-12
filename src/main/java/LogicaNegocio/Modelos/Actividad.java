package LogicaNegocio.Modelos;

import java.io.Serializable;

// Actividad deportiva de cupo limitado (Natacion, Spin, Tenis, etc.)
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private String horario;
    private int cupoMaximo;
    private double precioReserva;

    public Actividad(String codigo, String nombre, String horario, int cupoMaximo, double precioReserva) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.precioReserva = precioReserva;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public double getPrecioReserva() {
        return precioReserva;
    }

    @Override
    public String toString() {
        return "Actividad [" + codigo + " - " + nombre + ", Horario: " + horario + ", Cupo: " + cupoMaximo
                + ", Precio: $" + precioReserva + "]";
    }
}
