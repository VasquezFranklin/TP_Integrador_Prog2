package LogicaNegocio;
public class SocioEstandar extends Socio {
    public SocioEstandar(String dni, String nombre, String apellido){
        super(dni, nombre, apellido);
    }
    @Override
    public double cuotaMensual(double valorBase){
        return valorBase;
    }
}