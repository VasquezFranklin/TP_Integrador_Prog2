package LogicaNegocio.Modelos;

// [TS1-01]: Subclase concreta de Socio VIP (cargo adicional por acceso a zonas exclusivas)
public class SocioVip extends Socio {
    private static final long serialVersionUID = 1L;

    private double cargoAdicional;

    public SocioVip(String dni, String nombre, String apellido, double cargoAdicional) {
        super(dni, nombre, apellido);
        this.cargoAdicional = cargoAdicional;
    }

    @Override
    public double calcularCuotaMensual() {
        return cuotaBase + cargoAdicional;
    }

    @Override
    public String getTipo() {
        return "VIP";
    }

    public double getCargoAdicional() {
        return cargoAdicional;
    }

    @Override
    public String toString() {
        return super.toString() + " [Cargo zonas exclusivas: $" + cargoAdicional + "]";
    }
}
