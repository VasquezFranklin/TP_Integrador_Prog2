package LogicaNegocio;

import Datos.AccesoDatos;
import LogicaNegocio.Excepciones.*;
import LogicaNegocio.Modelos.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

// Capa de Logica de Negocio: reglas del dominio. Recibe AccesoDatos por inyeccion (constructor)
// y no conoce nada de la interfaz de usuario.
public class Logica {
    // Recargo que se factura por cada dia de mora en la devolucion de equipamiento
    public static final double RECARGO_POR_DIA_MORA = 300.0;

    private static final Pattern PATRON_DNI = Pattern.compile("\\d{7,8}");
    private static final Pattern PATRON_NOMBRE = Pattern.compile("[\\p{L}][\\p{L} .'-]*");

    private final AccesoDatos datos;

    // [TS1-03]: Indexacion en memoria Map<String, Socio> por DNI (busquedas O(1) y control de duplicados)
    private final Map<String, Socio> sociosPorDni;

    // [TS2-01] y [TS2-04]: Catalogo de equipamiento (Equipamiento.txt) y registro de alquileres (Alquileres.txt)
    private final Map<String, Equipamiento> equipamientoPorCodigo;
    private final List<Alquiler> alquileres;

    // [TS2-02] y [TS2-04]: Coleccion anidada Map<String, Set<Reserva>> con unicidad por clase (Reservas.txt)
    private final Map<String, Set<Reserva>> reservasPorActividad;
    private final Map<String, Actividad> actividadesPorCodigo;

    // [TS3-01]: Todas las facturas emitidas (Facturas.txt). Cada Socio mantiene ademas su
    // Map<String, List<Factura>> por periodo fiscal, reconstruido al iniciar.
    private final List<Factura> facturas;
    private int proximoNumeroFactura;

    // [TS1-05]: Inyeccion de dependencias (recibe AccesoDatos) y recuperacion automatica multi-archivo.
    // Si un archivo esta danado se informa el error (no se parte de datos vacios para no pisarlos).
    public Logica(AccesoDatos datos) throws PersistenciaException {
        this.datos = datos;
        this.sociosPorDni = orVacio(datos.leerSocios(), new HashMap<String, Socio>());
        this.equipamientoPorCodigo = orVacio(datos.leerEquipamiento(), new HashMap<String, Equipamiento>());
        this.alquileres = orVacio(datos.leerAlquileres(), new ArrayList<Alquiler>());
        this.reservasPorActividad = orVacio(datos.leerReservas(), new HashMap<String, Set<Reserva>>());
        this.actividadesPorCodigo = orVacio(datos.leerActividades(), new HashMap<String, Actividad>());
        this.facturas = orVacio(datos.leerFacturas(), new ArrayList<Factura>());

        // Reconstruir el indice de facturas por periodo dentro de cada socio y el contador de numeracion
        int maximo = 0;
        for (Factura f : facturas) {
            Socio s = sociosPorDni.get(f.getDniSocio());
            if (s != null) {
                s.agregarFactura(f);
            }
            maximo = Math.max(maximo, f.getNumero());
        }
        this.proximoNumeroFactura = maximo + 1;
    }

    private static <T> T orVacio(T cargado, T vacio) {
        return cargado != null ? cargado : vacio;
    }

    // =====================================================================
    // SOCIOS (HU-01 / HU-03)
    // =====================================================================
    // [TS1-03]: Registro de socios con validacion de DNI duplicado en O(1) y persistencia inmediata
    public void agregarSocio(Socio socio) throws NegocioException, PersistenciaException {
        if (socio == null) {
            throw new ReglaNegocioException("El socio no puede ser null.");
        }
        String dni = normalizarDni(socio.getDni());
        validarNombre(socio.getNombre(), "nombre");
        validarNombre(socio.getApellido(), "apellido");
        if (sociosPorDni.containsKey(dni)) {
            throw new SocioDuplicadoException(dni);
        }
        sociosPorDni.put(dni, socio);
        datos.guardarSocios(sociosPorDni);
    }

    public Socio registrarSocioEstandar(String dni, String nombre, String apellido)
            throws NegocioException, PersistenciaException {
        Socio s = new SocioEstandar(normalizarDni(dni), nombre.trim(), apellido.trim());
        agregarSocio(s);
        return s;
    }

    public Socio registrarSocioVip(String dni, String nombre, String apellido, double cargoAdicional)
            throws NegocioException, PersistenciaException {
        if (cargoAdicional < 0) {
            throw new ReglaNegocioException("El cargo adicional no puede ser negativo.");
        }
        Socio s = new SocioVip(normalizarDni(dni), nombre.trim(), apellido.trim(), cargoAdicional);
        agregarSocio(s);
        return s;
    }

    public Socio registrarSocioVitalicio(String dni, String nombre, String apellido, double porcentajeDescuento)
            throws NegocioException, PersistenciaException {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new ReglaNegocioException("El descuento debe estar entre 0 y 100.");
        }
        Socio s = new SocioVitalicio(normalizarDni(dni), nombre.trim(), apellido.trim(), porcentajeDescuento);
        agregarSocio(s);
        return s;
    }

    public void actualizarSocio(Socio socio) throws PersistenciaException {
        if (socio != null && sociosPorDni.containsKey(socio.getDni())) {
            sociosPorDni.put(socio.getDni(), socio);
            datos.guardarSocios(sociosPorDni);
        }
    }

    // Devuelve el socio o null si no existe
    public Socio buscarSocioPorDni(String dni) throws NegocioException {
        return sociosPorDni.get(normalizarDni(dni));
    }

    public Socio exigirSocio(String dni) throws NegocioException {
        Socio s = buscarSocioPorDni(dni);
        if (s == null) {
            throw new ReglaNegocioException("No existe un socio registrado con el DNI " + dni.trim() + ".");
        }
        return s;
    }

    public boolean existeSocio(String dni) throws NegocioException {
        return sociosPorDni.containsKey(normalizarDni(dni));
    }

    // Coleccion de solo lectura para la capa de presentacion
    public Collection<Socio> obtenerTodosLosSocios() {
        return Collections.unmodifiableCollection(sociosPorDni.values());
    }

    public void cambiarEstadoActivo(String dni, boolean activo) throws NegocioException, PersistenciaException {
        Socio s = exigirSocio(dni);
        s.setActivo(activo);
        datos.guardarSocios(sociosPorDni);
    }

    public void sancionarSocio(String dni, String motivo) throws NegocioException, PersistenciaException {
        Socio s = exigirSocio(dni);
        s.setSancionado(true);
        s.registrarAdvertencia(LocalDate.now() + " - Sancion: " + motivo);
        datos.guardarSocios(sociosPorDni);
    }

    public void levantarSancion(String dni) throws NegocioException, PersistenciaException {
        Socio s = exigirSocio(dni);
        if (!s.isSancionado()) {
            throw new ReglaNegocioException("El socio no posee sanciones vigentes.");
        }
        s.setSancionado(false);
        datos.guardarSocios(sociosPorDni);
    }

    public void marcarCuotaVencida(String dni) throws NegocioException, PersistenciaException {
        Socio s = exigirSocio(dni);
        s.setCuotaAlDia(false);
        datos.guardarSocios(sociosPorDni);
    }

    private String normalizarDni(String dni) throws ReglaNegocioException {
        if (dni == null || dni.trim().isEmpty()) {
            throw new ReglaNegocioException("El DNI no puede estar vacio.");
        }
        String limpio = dni.trim();
        if (!PATRON_DNI.matcher(limpio).matches()) {
            throw new ReglaNegocioException("El DNI debe tener 7 u 8 digitos numericos (sin puntos).");
        }
        return limpio;
    }

    private void validarNombre(String valor, String campo) throws ReglaNegocioException {
        if (valor == null || !PATRON_NOMBRE.matcher(valor.trim()).matches()) {
            throw new ReglaNegocioException("El " + campo + " es invalido (solo letras y espacios).");
        }
    }

    // =====================================================================
    // CONTROL DE ACCESO / MOLINETE (HU-06)
    // =====================================================================
    // [TS2-03]: Valida existencia, estado activo, sin sanciones y cuota al dia. Retorna el socio habilitado.
    public Socio verificarAcceso(String dni) throws AccesoDenegadoException {
        Socio socio;
        try {
            socio = buscarSocioPorDni(dni);
        } catch (NegocioException e) {
            throw new AccesoDenegadoException("Acceso rechazado: " + e.getMessage());
        }
        if (socio == null) {
            throw new AccesoDenegadoException(
                    "Acceso rechazado: el DNI " + dni.trim() + " no se encuentra registrado.");
        }
        String motivo = socio.motivoInhabilitacion();
        if (motivo != null) {
            throw new AccesoDenegadoException(
                    "Acceso rechazado: el socio " + socio.getNombreCompleto() + " " + motivo + ".");
        }
        return socio;
    }

    // =====================================================================
    // FACTURACION E HISTORIAL (HU-07)
    // =====================================================================
    private Factura emitirFactura(Socio socio, Factura.Tipo tipo, String concepto, double monto)
            throws PersistenciaException {
        Factura f = new Factura(proximoNumeroFactura, socio.getDni(), socio.getNombreCompleto(), tipo, concepto,
                monto, LocalDate.now());
        facturas.add(f);
        proximoNumeroFactura++;
        socio.agregarFactura(f);
        datos.guardarFacturas(facturas);
        return f;
    }

    // Liquida la cuota del periodo actual: cuota polimorfica + recargos pendientes por mora.
    public Factura liquidarCuota(String dni) throws NegocioException, PersistenciaException {
        Socio socio = exigirSocio(dni);
        if (!socio.isActivo()) {
            throw new ReglaNegocioException("El socio esta inactivo: no se le puede liquidar la cuota.");
        }
        String periodo = Factura.periodoDe(LocalDate.now());
        for (Factura previa : socio.getFacturasDelPeriodo(periodo)) {
            if (previa.getTipo() == Factura.Tipo.CUOTA) {
                throw new ReglaNegocioException(
                        "La cuota del periodo " + periodo + " ya fue liquidada (comprobante #" + previa.getNumero()
                                + ").");
            }
        }
        double cuota = socio.calcularCuotaMensual(); // despacho dinamico: Estandar / VIP / Vitalicio
        double recargo = socio.liquidarRecargos();
        String concepto = "Cuota " + periodo + " (" + socio.getTipo() + ")";
        if (recargo > 0) {
            concepto += " + recargo mora $" + String.format("%.2f", recargo);
        }
        Factura f = emitirFactura(socio, Factura.Tipo.CUOTA, concepto, cuota + recargo);
        socio.setCuotaAlDia(true);
        datos.guardarSocios(sociosPorDni);
        return f;
    }

    // Historial de un socio agrupado por periodo fiscal (anio-mes)
    public Map<String, List<Factura>> obtenerFacturasDeSocio(String dni) throws NegocioException {
        return exigirSocio(dni).getFacturasPorPeriodo();
    }

    public Factura buscarFacturaPorNumero(int numero) {
        for (Factura f : facturas) {
            if (f.getNumero() == numero) {
                return f;
            }
        }
        return null;
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return Collections.unmodifiableList(facturas);
    }

    // =====================================================================
    // EQUIPAMIENTO Y ALQUILERES (HU-04)
    // =====================================================================
    public void agregarEquipamiento(Equipamiento equipamiento) throws NegocioException, PersistenciaException {
        if (equipamiento == null || equipamiento.getCodigo() == null || equipamiento.getCodigo().trim().isEmpty()) {
            throw new ReglaNegocioException("El codigo del equipamiento no puede estar vacio.");
        }
        if (equipamiento.getDescripcion() == null || equipamiento.getDescripcion().trim().isEmpty()) {
            throw new ReglaNegocioException("La descripcion del equipamiento no puede estar vacia.");
        }
        if (equipamiento.getPrecioDiario() < 0) {
            throw new ReglaNegocioException("El precio diario no puede ser negativo.");
        }
        String codigo = equipamiento.getCodigo().trim().toUpperCase(Locale.ROOT);
        if (equipamientoPorCodigo.containsKey(codigo)) {
            throw new ReglaNegocioException("Ya existe un equipamiento con el codigo " + codigo + ".");
        }
        equipamiento.setCodigo(codigo);
        equipamientoPorCodigo.put(codigo, equipamiento);
        datos.guardarEquipamiento(equipamientoPorCodigo);
    }

    public Equipamiento buscarEquipamientoPorCodigo(String codigo) {
        if (codigo == null)
            return null;
        return equipamientoPorCodigo.get(codigo.trim().toUpperCase(Locale.ROOT));
    }

    public Collection<Equipamiento> obtenerTodoElEquipamiento() {
        return Collections.unmodifiableCollection(equipamientoPorCodigo.values());
    }

    // [TS2-01]: Alquiler: verifica que el socio este habilitado, que el equipo este libre,
    // registra la fecha limite de devolucion y factura el alquiler.
    public Alquiler registrarAlquiler(String dni, String codigoEquipamiento, int diasDePrestamo)
            throws NegocioException, PersistenciaException {
        Socio socio = exigirSocio(dni);
        Equipamiento equipo = buscarEquipamientoPorCodigo(codigoEquipamiento);
        if (equipo == null) {
            throw new ReglaNegocioException("No existe equipamiento con el codigo " + codigoEquipamiento + ".");
        }
        Alquiler alquiler = new Alquiler(alquileres.size() + 1, socio, equipo, LocalDate.now(), diasDePrestamo);
        equipo.setDisponible(false);
        alquileres.add(alquiler);
        datos.guardarEquipamiento(equipamientoPorCodigo);
        datos.guardarAlquileres(alquileres);
        emitirFactura(socio, Factura.Tipo.ALQUILER, "Alquiler " + equipo.getCodigo() + " " + equipo.getDescripcion()
                + " (" + diasDePrestamo + " dias)", alquiler.getCostoTotal());
        return alquiler;
    }

    // [TS2-01]: Devolucion. Si hay mora: sancion automatica, recargo en la proxima factura y advertencia.
    // Retorna los dias de mora (0 si fue en termino).
    public int registrarDevolucion(int idAlquiler, LocalDate fechaReal)
            throws NegocioException, PersistenciaException {
        Alquiler alquiler = null;
        for (Alquiler a : alquileres) {
            if (a.getId() == idAlquiler) {
                alquiler = a;
                break;
            }
        }
        if (alquiler == null) {
            throw new ReglaNegocioException("No existe el alquiler #" + idAlquiler + ".");
        }
        int diasMora = alquiler.procesarDevolucion(fechaReal);

        Equipamiento equipo = equipamientoPorCodigo.get(alquiler.getCodigoEquipamiento());
        if (equipo != null) {
            equipo.setDisponible(true);
        }
        if (diasMora > 0) {
            Socio socio = sociosPorDni.get(alquiler.getDniSocio());
            if (socio != null) {
                double recargo = diasMora * RECARGO_POR_DIA_MORA;
                socio.setSancionado(true);
                socio.agregarRecargo(recargo);
                socio.registrarAdvertencia(LocalDate.now() + " - Advertencia: devolucion tardia de "
                        + alquiler.getCodigoEquipamiento() + " (" + diasMora + " dias de mora, recargo $"
                        + String.format("%.2f", recargo) + ")");
                datos.guardarSocios(sociosPorDni);
            }
        }
        datos.guardarEquipamiento(equipamientoPorCodigo);
        datos.guardarAlquileres(alquileres);
        return diasMora;
    }

    public List<Alquiler> obtenerTodosLosAlquileres() {
        return Collections.unmodifiableList(alquileres);
    }

    public List<Alquiler> obtenerAlquileresActivos() {
        List<Alquiler> activos = new ArrayList<>();
        for (Alquiler a : alquileres) {
            if (!a.isEstaDevuelto()) {
                activos.add(a);
            }
        }
        return Collections.unmodifiableList(activos);
    }

    // =====================================================================
    // ACTIVIDADES Y RESERVAS (HU-05) - Map<String, Set<Reserva>>
    // =====================================================================
    public void agregarActividad(Actividad actividad) throws NegocioException, PersistenciaException {
        if (actividad == null || actividad.getCodigo() == null || actividad.getCodigo().trim().isEmpty()) {
            throw new ReglaNegocioException("El codigo de la actividad no puede estar vacio.");
        }
        if (actividad.getNombre() == null || actividad.getNombre().trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre de la actividad no puede estar vacio.");
        }
        if (actividad.getHorario() == null || actividad.getHorario().trim().isEmpty()) {
            throw new ReglaNegocioException("El horario de la actividad no puede estar vacio.");
        }
        if (actividad.getCupoMaximo() < 1) {
            throw new ReglaNegocioException("El cupo debe ser de al menos 1 lugar.");
        }
        if (actividad.getPrecioReserva() < 0) {
            throw new ReglaNegocioException("El precio de la reserva no puede ser negativo.");
        }
        String codigo = actividad.getCodigo().trim().toUpperCase(Locale.ROOT);
        if (actividadesPorCodigo.containsKey(codigo)) {
            throw new ReglaNegocioException("Ya existe una actividad con el codigo " + codigo + ".");
        }
        actividadesPorCodigo.put(codigo, actividad);
        datos.guardarActividades(actividadesPorCodigo);
    }

    public Actividad buscarActividad(String codigo) {
        if (codigo == null)
            return null;
        return actividadesPorCodigo.get(codigo.trim().toUpperCase(Locale.ROOT));
    }

    public Collection<Actividad> obtenerTodasLasActividades() {
        return Collections.unmodifiableCollection(actividadesPorCodigo.values());
    }

    // Inscripcion con control estricto de cupo y de duplicados (misma clase y horario para el mismo socio)
    public Reserva registrarReserva(String codigoActividad, String dni)
            throws NegocioException, PersistenciaException {
        Actividad actividad = buscarActividad(codigoActividad);
        if (actividad == null) {
            throw new ReglaNegocioException("No existe la actividad con codigo " + codigoActividad + ".");
        }
        Socio socio = exigirSocio(dni);
        String motivo = socio.motivoInhabilitacion();
        if (motivo != null) {
            throw new ReglaNegocioException(
                    "No se puede inscribir: el socio " + socio.getNombreCompleto() + " " + motivo + ".");
        }

        String clave = actividad.getCodigo().trim().toUpperCase(Locale.ROOT);
        Set<Reserva> inscriptos = reservasPorActividad.get(clave);
        if (inscriptos == null) {
            inscriptos = new HashSet<>();
        }
        Reserva nueva = new Reserva(clave, socio, actividad.getHorario());

        // [TS2-02]: la unicidad la garantizan equals() y hashCode() de Reserva dentro del Set
        if (inscriptos.contains(nueva)) {
            throw new ReglaNegocioException("Reserva duplicada rechazada: " + socio.getNombreCompleto()
                    + " (DNI " + socio.getDni() + ") ya esta inscripto en " + actividad.getNombre() + " ("
                    + actividad.getHorario() + ").");
        }
        if (inscriptos.size() >= actividad.getCupoMaximo()) {
            throw new ReglaNegocioException("Cupo completo en " + actividad.getNombre() + " ("
                    + actividad.getCupoMaximo() + " lugares).");
        }
        inscriptos.add(nueva);
        reservasPorActividad.put(clave, inscriptos);
        datos.guardarReservas(reservasPorActividad);

        if (actividad.getPrecioReserva() > 0) {
            emitirFactura(socio, Factura.Tipo.RESERVA,
                    "Reserva " + actividad.getNombre() + " " + actividad.getHorario(),
                    actividad.getPrecioReserva());
        }
        return nueva;
    }

    public void cancelarReserva(String codigoActividad, String dni) throws NegocioException, PersistenciaException {
        Actividad actividad = buscarActividad(codigoActividad);
        if (actividad == null) {
            throw new ReglaNegocioException("No existe la actividad con codigo " + codigoActividad + ".");
        }
        Socio socio = exigirSocio(dni);
        String clave = actividad.getCodigo().trim().toUpperCase(Locale.ROOT);
        Set<Reserva> inscriptos = reservasPorActividad.get(clave);
        Reserva buscada = new Reserva(clave, socio, actividad.getHorario());
        if (inscriptos == null || !inscriptos.remove(buscada)) {
            throw new ReglaNegocioException("El socio no tiene una reserva en " + actividad.getNombre() + ".");
        }
        datos.guardarReservas(reservasPorActividad);
    }

    public Set<Reserva> obtenerReservasPorActividad(String codigoActividad) {
        if (codigoActividad == null)
            return Collections.emptySet();
        Set<Reserva> res = reservasPorActividad.get(codigoActividad.trim().toUpperCase(Locale.ROOT));
        return res != null ? Collections.unmodifiableSet(res) : Collections.<Reserva>emptySet();
    }

    public Map<String, Set<Reserva>> obtenerTodasLasReservas() {
        return Collections.unmodifiableMap(reservasPorActividad);
    }
}
