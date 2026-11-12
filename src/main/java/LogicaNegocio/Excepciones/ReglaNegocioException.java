package LogicaNegocio.Excepciones;

// Regla de negocio incumplida (reserva duplicada, cupo completo, equipo ocupado, datos invalidos, etc.)
public class ReglaNegocioException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
