package LogicaNegocio.Excepciones;

// Se intenta registrar un socio con un DNI que ya existe en el indice
public class SocioDuplicadoException extends NegocioException {
    private static final long serialVersionUID = 1L;

    public SocioDuplicadoException(String dni) {
        super("Ya existe un socio registrado con el DNI " + dni + ".");
    }
}
