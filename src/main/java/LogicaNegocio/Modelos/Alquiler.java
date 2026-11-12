package LogicaNegocio.Modelos;

import LogicaNegocio.Excepciones.ReglaNegocioException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// [TS2-01]: Modulo de Alquiler de Equipamiento Deportivo y Gestion de Prestamos.
// Guarda el DNI del socio y el codigo del equipo (no referencias a objetos) para que al
// recuperar los archivos no existan copias desincronizadas de Socio / Equipamiento.
public class Alquiler implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_DIAS_PRESTAMO = 30;

    private int id;
    private String dniSocio;
    private String nombreSocio;
    private String codigoEquipamiento;
    private String descripcionEquipamiento;
    private double costoTotal;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimiteDevolucion;
    private LocalDate fechaDevolucionReal;
    private boolean estaDevuelto;

    // [TS2-01]: Reglas de negocio para concretar el prestamo (socio habilitado y equipo disponible)
    public Alquiler(int id, Socio socio, Equipamiento equipamiento, LocalDate fechaPrestamo, int diasDePrestamo)
            throws ReglaNegocioException {
        if (socio == null) {
            throw new ReglaNegocioException("El socio no puede ser null.");
        }
        if (equipamiento == null) {
            throw new ReglaNegocioException("El equipamiento no puede ser null.");
        }
        if (diasDePrestamo < 1 || diasDePrestamo > MAX_DIAS_PRESTAMO) {
            throw new ReglaNegocioException(
                    "Los dias de prestamo deben estar entre 1 y " + MAX_DIAS_PRESTAMO + ".");
        }
        String motivo = socio.motivoInhabilitacion();
        if (motivo != null) {
            throw new ReglaNegocioException(
                    "No se puede alquilar: el socio " + socio.getNombreCompleto() + " " + motivo + ".");
        }
        if (!equipamiento.isDisponible()) {
            throw new ReglaNegocioException("Este equipo ya esta en uso: " + equipamiento.getDescripcion() + ".");
        }
        this.id = id;
        this.dniSocio = socio.getDni();
        this.nombreSocio = socio.getNombreCompleto();
        this.codigoEquipamiento = equipamiento.getCodigo();
        this.descripcionEquipamiento = equipamiento.getDescripcion();
        this.fechaPrestamo = fechaPrestamo != null ? fechaPrestamo : LocalDate.now();
        this.fechaLimiteDevolucion = this.fechaPrestamo.plusDays(diasDePrestamo);
        this.costoTotal = equipamiento.getPrecioDiario() * diasDePrestamo;
        this.estaDevuelto = false;
    }

    // [TS2-01]: Registra la devolucion y retorna los dias de mora (0 si se devolvio en termino).
    // La sancion y el recargo los aplica la capa de Logica sobre el socio.
    public int procesarDevolucion(LocalDate fechaRealDeDevolucion) throws ReglaNegocioException {
        if (estaDevuelto) {
            throw new ReglaNegocioException("Este alquiler ya fue devuelto.");
        }
        if (fechaRealDeDevolucion == null) {
            fechaRealDeDevolucion = LocalDate.now();
        }
        if (fechaRealDeDevolucion.isBefore(fechaPrestamo)) {
            throw new ReglaNegocioException("La fecha de devolucion no puede ser anterior al prestamo.");
        }
        this.estaDevuelto = true;
        this.fechaDevolucionReal = fechaRealDeDevolucion;
        if (fechaRealDeDevolucion.isAfter(fechaLimiteDevolucion)) {
            return (int) ChronoUnit.DAYS.between(fechaLimiteDevolucion, fechaRealDeDevolucion);
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getDniSocio() {
        return dniSocio;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public String getCodigoEquipamiento() {
        return codigoEquipamiento;
    }

    public String getDescripcionEquipamiento() {
        return descripcionEquipamiento;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimiteDevolucion() {
        return fechaLimiteDevolucion;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public boolean isEstaDevuelto() {
        return estaDevuelto;
    }

    @Override
    public String toString() {
        return "Alquiler #" + id + " [Socio: " + nombreSocio + " (DNI: " + dniSocio + "), Equipo: "
                + codigoEquipamiento + " - " + descripcionEquipamiento + ", Prestamo: " + fechaPrestamo
                + ", Limite: " + fechaLimiteDevolucion
                + (estaDevuelto ? ", Devuelto el " + fechaDevolucionReal : ", PENDIENTE DE DEVOLUCION") + "]";
    }
}
