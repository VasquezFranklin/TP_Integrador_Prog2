package LogicaNegocio.Modelos;

import java.io.Serializable;

// [TS2-01]: Entidad de dominio para inventario de equipamiento deportivo y control de disponibilidad
public class Equipamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final double PRECIO_DIARIO_DEFECTO = 500.0;

    private String codigo;
    private String descripcion;
    private boolean disponible;
    private double precioDiario;

    public Equipamiento(String codigo, String descripcion) {
        this(codigo, descripcion, PRECIO_DIARIO_DEFECTO);
    }

    public Equipamiento(String codigo, String descripcion, double precioDiario) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioDiario = precioDiario;
        this.disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getPrecioDiario() {
        return precioDiario;
    }

    @Override
    public String toString() {
        return "Equipamiento [Codigo: " + codigo + ", Descripcion: " + descripcion + ", Precio/dia: $"
                + precioDiario + ", Disponible: " + disponible + "]";
    }
}
