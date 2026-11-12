package LogicaNegocio.Modelos;

// [TS1-01]: Subclase concreta de Socio Estandar (retorna la cuota base)
public class SocioEstandar extends Socio {
    private static final long serialVersionUID = 1L;

    public SocioEstandar(String dni, String nombre, String apellido) {
        super(dni, nombre, apellido);
    }

    @Override
    public double calcularCuotaMensual() {
        return cuotaBase;
    }

    @Override
    public String getTipo() {
        return "Estandar";
    }
}
