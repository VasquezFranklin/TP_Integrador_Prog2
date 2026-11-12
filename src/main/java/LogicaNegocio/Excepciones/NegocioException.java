package LogicaNegocio.Excepciones;

// Excepcion base de dominio: toda violacion de una regla de negocio hereda de esta clase
// (la interfaz solo necesita capturar NegocioException y PersistenciaException).
public class NegocioException extends Exception {
    private static final long serialVersionUID = 1L;

    public NegocioException(String mensaje) {
        super(mensaje);
    }
}
