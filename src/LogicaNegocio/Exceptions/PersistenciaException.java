package LogicaNegocio.Exceptions;

public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje,causa);
    }
}