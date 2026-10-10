package LogicaNegocio;
public class SocioVip extends Socio{
    private double cargoAdicional;
    public SocioVip(String dni, String nombre, String apellido, double cargoAdicional){
        super(dni, nombre, apellido);
        this.cargoAdicional = cargoAdicional;
    }
    @Override
    public double cuotaMensual(double valorBase){
        return valorBase + cargoAdicional;
    }
}
