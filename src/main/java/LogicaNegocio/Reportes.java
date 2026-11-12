package LogicaNegocio;

import LogicaNegocio.Modelos.*;
import java.util.*;

// [HU-08]: Modulo de reportes y metricas. Usa Collections.sort() con Comparator personalizado y
// Collections.binarySearch() sobre listas ordenadas. Se inyecta la Logica por constructor.
public class Reportes {
    private final Logica logica;

    public Reportes(Logica logica) {
        this.logica = logica;
    }

    // Comparator por apellido y luego nombre (sin distinguir mayusculas)
    private static final Comparator<Socio> POR_APELLIDO_NOMBRE = new Comparator<Socio>() {
        @Override
        public int compare(Socio a, Socio b) {
            int c = a.getApellido().compareToIgnoreCase(b.getApellido());
            return c != 0 ? c : a.getNombre().compareToIgnoreCase(b.getNombre());
        }
    };

    // Comparator solo por apellido: es el que requiere binarySearch (clave de la busqueda)
    private static final Comparator<Socio> SOLO_APELLIDO = new Comparator<Socio>() {
        @Override
        public int compare(Socio a, Socio b) {
            return a.getApellido().compareToIgnoreCase(b.getApellido());
        }
    };

    // Comparator por monto de cuota (resuelto polimorficamente), de mayor a menor
    private static final Comparator<Socio> POR_CUOTA_DESC = new Comparator<Socio>() {
        @Override
        public int compare(Socio a, Socio b) {
            int c = Double.compare(b.calcularCuotaMensual(), a.calcularCuotaMensual());
            return c != 0 ? c : POR_APELLIDO_NOMBRE.compare(a, b);
        }
    };

    public List<Socio> sociosPorApellidoNombre() {
        List<Socio> lista = new ArrayList<>(logica.obtenerTodosLosSocios());
        Collections.sort(lista, POR_APELLIDO_NOMBRE);
        return lista;
    }

    public List<Socio> sociosPorMontoDeCuota() {
        List<Socio> lista = new ArrayList<>(logica.obtenerTodosLosSocios());
        Collections.sort(lista, POR_CUOTA_DESC);
        return lista;
    }

    // Busqueda binaria por apellido sobre la lista ordenada; devuelve todos los socios con ese apellido
    public List<Socio> buscarPorApellido(String apellido) {
        List<Socio> resultado = new ArrayList<>();
        if (apellido == null || apellido.trim().isEmpty()) {
            return resultado;
        }
        List<Socio> ordenados = sociosPorApellidoNombre();
        Socio clave = new SocioEstandar("0", "", apellido.trim());
        int pos = Collections.binarySearch(ordenados, clave, SOLO_APELLIDO);
        if (pos < 0) {
            return resultado;
        }
        // Puede haber varios socios con el mismo apellido contiguos: se expande a ambos lados
        int desde = pos;
        while (desde > 0 && SOLO_APELLIDO.compare(ordenados.get(desde - 1), clave) == 0) {
            desde--;
        }
        int hasta = pos;
        while (hasta < ordenados.size() - 1 && SOLO_APELLIDO.compare(ordenados.get(hasta + 1), clave) == 0) {
            hasta++;
        }
        for (int i = desde; i <= hasta; i++) {
            resultado.add(ordenados.get(i));
        }
        return resultado;
    }

    public List<Socio> sociosMorososOSancionados() {
        List<Socio> lista = new ArrayList<>();
        for (Socio s : sociosPorApellidoNombre()) {
            if (s.isSancionado() || !s.isCuotaAlDia()) {
                lista.add(s);
            }
        }
        return lista;
    }

    // ---- Ocupacion de actividades ----
    public static class Ocupacion {
        private final Actividad actividad;
        private final int inscriptos;

        public Ocupacion(Actividad actividad, int inscriptos) {
            this.actividad = actividad;
            this.inscriptos = inscriptos;
        }

        public Actividad getActividad() {
            return actividad;
        }

        public int getInscriptos() {
            return inscriptos;
        }

        public int getLugaresLibres() {
            return actividad.getCupoMaximo() - inscriptos;
        }

        public double getPorcentaje() {
            return inscriptos * 100.0 / actividad.getCupoMaximo();
        }
    }

    public List<Ocupacion> ocupacionDeActividades() {
        List<Ocupacion> lista = new ArrayList<>();
        for (Actividad a : logica.obtenerTodasLasActividades()) {
            lista.add(new Ocupacion(a, logica.obtenerReservasPorActividad(a.getCodigo()).size()));
        }
        Collections.sort(lista, new Comparator<Ocupacion>() {
            @Override
            public int compare(Ocupacion x, Ocupacion y) {
                return Double.compare(y.getPorcentaje(), x.getPorcentaje());
            }
        });
        return lista;
    }

    // ---- Recaudacion ----
    // Total recaudado por periodo fiscal ("yyyy-MM"), ordenado cronologicamente
    public Map<String, Double> recaudacionPorPeriodo() {
        Map<String, Double> totales = new TreeMap<>();
        for (Factura f : logica.obtenerTodasLasFacturas()) {
            Double actual = totales.get(f.getPeriodo());
            totales.put(f.getPeriodo(), (actual == null ? 0.0 : actual) + f.getMonto());
        }
        return totales;
    }

    public double recaudacionMensual(String periodo) {
        Double total = recaudacionPorPeriodo().get(periodo);
        return total == null ? 0.0 : total;
    }

    // Recaudacion de un periodo desglosada por tipo de comprobante
    public Map<Factura.Tipo, Double> recaudacionPorTipo(String periodo) {
        Map<Factura.Tipo, Double> totales = new EnumMap<>(Factura.Tipo.class);
        for (Factura f : logica.obtenerTodasLasFacturas()) {
            if (f.getPeriodo().equals(periodo)) {
                Double actual = totales.get(f.getTipo());
                totales.put(f.getTipo(), (actual == null ? 0.0 : actual) + f.getMonto());
            }
        }
        return totales;
    }
}
