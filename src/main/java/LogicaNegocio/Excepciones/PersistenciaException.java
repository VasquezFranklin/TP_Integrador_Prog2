package LogicaNegocio.Excepciones;

// [TS1-04]: Excepcion encapsulada para desacoplar errores tecnicos de almacenamiento y persistencia
public class PersistenciaException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
