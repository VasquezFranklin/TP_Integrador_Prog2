package LogicaNegocio;
import java.io.Serializable;

public abstract class Socio implements Serializable{
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected boolean activo;
    protected boolean sancionado;

    public Socio(String dni, String nombre, String apellido){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.activo = true;
        this.sancionado = false;
    }
    public abstract double cuotaMensual(double valorBase);

    //getters
    public String getDni() {return dni;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public boolean isActivo() {return activo;}
    public boolean isSancionado() {return sancionado;}

}
