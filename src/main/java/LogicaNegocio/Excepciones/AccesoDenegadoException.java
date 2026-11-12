package LogicaNegocio.Excepciones;

// [TS2-03]: Excepcion de negocio para rechazo de ingreso en el modulo de Control de Acceso (Molinete)
public class AccesoDenegadoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public AccesoDenegadoException(String mensaje) {
        super(mensaje);
    }
}
