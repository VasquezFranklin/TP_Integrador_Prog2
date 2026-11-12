package LogicaNegocio.Modelos;

import java.io.Serializable;
import java.util.*;

// [TS1-01]: Jerarquia abstracta de Socios (Modelo de Dominio base)
public abstract class Socio implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final double CUOTA_BASE_DEFECTO = 5000.0;

    protected String dni;
    protected String nombre;
    protected String apellido;
    protected boolean activo;
    // [TS2-01] y [TS2-03]: Estados requeridos para Sanciones de Equipamiento y Control de Acceso (Molinete)
    protected boolean sancionado;
    protected boolean cuotaAlDia;
    protected double cuotaBase;

    // Recargos por mora de devolucion que se cobran en la SIGUIENTE factura de cuota
    protected double recargoPendiente;
    // Registro de advertencias generadas por incumplimientos
    protected ArrayList<String> advertencias = new ArrayList<>();

    // [TS3-01]: Facturas del socio organizadas por periodo fiscal ("yyyy-MM").
    // Es transient: la fuente de verdad es Facturas.txt y Logica reconstruye este indice al iniciar.
    private transient Map<String, List<Factura>> facturasPorPeriodo;

    public Socio(String dni, String nombre, String apellido) {
        this(dni, nombre, apellido, CUOTA_BASE_DEFECTO);
    }

    public Socio(String dni, String nombre, String apellido, double cuotaBase) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuotaBase = cuotaBase;
        this.activo = true;
        this.sancionado = false;
        this.cuotaAlDia = true;
        this.recargoPendiente = 0;
    }

    // [TS1-01]: Metodo polimorfico abstracto para calculo de cuota (sin condicionales ni instanceof)
    public abstract double calcularCuotaMensual();

    // Nombre de la categoria de membresia (resuelto por despacho dinamico)
    public abstract String getTipo();

    // Devuelve null si el socio esta habilitado para operar (acceso, alquiler, reservas);
    // de lo contrario devuelve el motivo de la inhabilitacion.
    public String motivoInhabilitacion() {
        if (!activo) {
            return "se encuentra inactivo (dado de baja)";
        }
        if (sancionado) {
            return "posee sanciones vigentes";
        }
        if (!cuotaAlDia) {
            return "registra cuotas vencidas (moroso)";
        }
        return null;
    }

    public boolean estaHabilitado() {
        return motivoInhabilitacion() == null;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // ---- Recargos y advertencias ----
    public void agregarRecargo(double monto) {
        if (monto > 0) {
            this.recargoPendiente += monto;
        }
    }

    // Devuelve el recargo acumulado y lo deja en cero (se usa al facturar)
    public double liquidarRecargos() {
        double r = this.recargoPendiente;
        this.recargoPendiente = 0;
        return r;
    }

    public double getRecargoPendiente() {
        return recargoPendiente;
    }

    public void registrarAdvertencia(String texto) {
        advertencias.add(texto);
    }

    public List<String> getAdvertencias() {
        return Collections.unmodifiableList(advertencias);
    }

    // ---- Facturas por periodo fiscal ----
    private Map<String, List<Factura>> indiceFacturas() {
        if (facturasPorPeriodo == null) {
            facturasPorPeriodo = new TreeMap<>();
        }
        return facturasPorPeriodo;
    }

    public void agregarFactura(Factura factura) {
        indiceFacturas().computeIfAbsent(factura.getPeriodo(), k -> new ArrayList<>()).add(factura);
    }

    public Map<String, List<Factura>> getFacturasPorPeriodo() {
        return Collections.unmodifiableMap(indiceFacturas());
    }

    public List<Factura> getFacturasDelPeriodo(String periodo) {
        List<Factura> lista = indiceFacturas().get(periodo);
        return lista == null ? Collections.<Factura>emptyList() : Collections.unmodifiableList(lista);
    }

    @Override
    public String toString() {
        return "DNI: " + dni + " - " + apellido + ", " + nombre + " [" + getTipo() + "] (Activo: " + activo
                + ", Sancionado: " + sancionado + ", Cuota al dia: " + cuotaAlDia + ")";
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isSancionado() {
        return sancionado;
    }

    public void setSancionado(boolean sancionado) {
        this.sancionado = sancionado;
    }

    public boolean isCuotaAlDia() {
        return cuotaAlDia;
    }

    public void setCuotaAlDia(boolean cuotaAlDia) {
        this.cuotaAlDia = cuotaAlDia;
    }

    public double getCuotaBase() {
        return cuotaBase;
    }

    public void setCuotaBase(double cuotaBase) {
        this.cuotaBase = cuotaBase;
    }
}
