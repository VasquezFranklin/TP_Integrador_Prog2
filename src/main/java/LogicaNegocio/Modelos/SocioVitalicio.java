package LogicaNegocio.Modelos;

// [TS1-01]: Subclase concreta de Socio Vitalicio (descuento porcentual sobre la cuota base)
public class SocioVitalicio extends Socio {
    private static final long serialVersionUID = 1L;

    private double porcentajeDescuento;

    public SocioVitalicio(String dni, String nombre, String apellido, double porcentajeDescuento) {
        super(dni, nombre, apellido);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularCuotaMensual() {
        return cuotaBase * (1 - porcentajeDescuento / 100.0);
    }

    @Override
    public String getTipo() {
        return "Vitalicio";
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public String toString() {
        return super.toString() + " [Descuento: " + porcentajeDescuento + "%]";
    }
}
