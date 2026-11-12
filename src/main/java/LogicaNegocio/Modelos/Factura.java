package LogicaNegocio.Modelos;

import java.io.Serializable;
import java.time.LocalDate;

// [TS3-01]: Comprobante de facturacion (cuotas, reservas y alquileres) con periodo fiscal "yyyy-MM"
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Tipo {
        CUOTA, RESERVA, ALQUILER
    }

    private int numero;
    private String dniSocio;
    private String nombreSocio;
    private Tipo tipo;
    private String concepto;
    private double monto;
    private LocalDate fecha;
    private String periodo;

    public Factura(int numero, String dniSocio, String nombreSocio, Tipo tipo, String concepto, double monto,
            LocalDate fecha) {
        this.numero = numero;
        this.dniSocio = dniSocio;
        this.nombreSocio = nombreSocio;
        this.tipo = tipo;
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
        this.periodo = periodoDe(fecha);
    }

    public static String periodoDe(LocalDate fecha) {
        return String.format("%04d-%02d", fecha.getYear(), fecha.getMonthValue());
    }

    public int getNumero() {
        return numero;
    }

    public String getDniSocio() {
        return dniSocio;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getPeriodo() {
        return periodo;
    }

    // Comprobante completo listo para imprimir
    public String comprobante() {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------------------------------------------+\n");
        sb.append(String.format("| COMPROBANTE Nro. %-31s |%n", String.format("%08d", numero)));
        sb.append("| Centro Deportivo de Alto Rendimiento             |\n");
        sb.append("+--------------------------------------------------+\n");
        sb.append(String.format("| Fecha   : %-38s |%n", fecha));
        sb.append(String.format("| Periodo : %-38s |%n", periodo));
        sb.append(String.format("| Socio   : %-38s |%n", cortar(nombreSocio + " (" + dniSocio + ")", 38)));
        sb.append(String.format("| Tipo    : %-38s |%n", tipo));
        String resto = concepto;
        String etiqueta = "Detalle";
        while (!resto.isEmpty()) {
            int corte = Math.min(38, resto.length());
            if (resto.length() > 38) {
                int espacio = resto.lastIndexOf(' ', 38);
                if (espacio > 0) {
                    corte = espacio;
                }
            }
            sb.append(String.format("| %-7s : %-38s |%n", etiqueta, resto.substring(0, corte)));
            resto = resto.substring(corte).trim();
            etiqueta = "";
        }
        sb.append(String.format("| TOTAL   : $ %-36.2f |%n", monto));
        sb.append("+--------------------------------------------------+");
        return sb.toString();
    }

    private static String cortar(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }

    @Override
    public String toString() {
        return String.format("#%08d | %s | %-8s | $%10.2f | %s", numero, fecha, tipo, monto, concepto);
    }
}
