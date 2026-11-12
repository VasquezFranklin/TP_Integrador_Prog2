package LogicaNegocio.Modelos;

import java.io.Serializable;
import java.util.Objects;

// [TS2-02]: Reserva con equals() y hashCode() sobreescritos para garantizar unicidad dentro de un Set.
// Guarda el DNI (no el objeto Socio) para evitar copias desincronizadas al deserializar.
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigoActividad;
    private String dniSocio;
    private String nombreSocio;
    private String horario;

    public Reserva(String codigoActividad, Socio socio, String horario) {
        if (codigoActividad == null || codigoActividad.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo de actividad no puede estar vacio.");
        }
        if (socio == null) {
            throw new IllegalArgumentException("El socio no puede ser null.");
        }
        if (horario == null || horario.trim().isEmpty()) {
            throw new IllegalArgumentException("El horario no puede estar vacio.");
        }
        this.codigoActividad = codigoActividad.trim().toUpperCase();
        this.dniSocio = socio.getDni();
        this.nombreSocio = socio.getNombreCompleto();
        this.horario = horario.trim();
    }

    public String getCodigoActividad() {
        return codigoActividad;
    }

    public String getDniSocio() {
        return dniSocio;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public String getHorario() {
        return horario;
    }

    // [TS2-02]: equals() basado en actividad, horario y DNI del socio
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reserva otra = (Reserva) o;
        return Objects.equals(codigoActividad, otra.codigoActividad)
                && Objects.equals(horario, otra.horario)
                && Objects.equals(dniSocio, otra.dniSocio);
    }

    // [TS2-02]: hashCode() coherente con equals()
    @Override
    public int hashCode() {
        return Objects.hash(codigoActividad, horario, dniSocio);
    }

    @Override
    public String toString() {
        return "Reserva [Actividad: " + codigoActividad + ", Horario: " + horario + ", Socio: " + nombreSocio
                + " (DNI: " + dniSocio + ")]";
    }
}
