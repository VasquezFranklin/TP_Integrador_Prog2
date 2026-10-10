package LogicaNegocio;
public class SocioBecado extends Socio{
    private double descuento;
    public SocioBecado(String dni, String nombre,  String apellido, double descuento){
        super(dni, nombre, apellido);
        this.descuento = descuento;
    }
    @Override
    public double cuotaMensual(double valorBase){
        return valorBase - descuento;
    }
}
