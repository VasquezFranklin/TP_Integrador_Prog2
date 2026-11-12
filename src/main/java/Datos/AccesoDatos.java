package Datos;

import LogicaNegocio.Excepciones.PersistenciaException;
import LogicaNegocio.Modelos.*;
import java.io.*;
import java.util.*;

// Capa de Datos: unica clase que toca archivos. No contiene logica de negocio.
// Cada archivo se serializa con ObjectOutputStream / ObjectInputStream y las excepciones
// tecnicas (IOException, ClassNotFoundException) se relanzan como PersistenciaException.
public class AccesoDatos {
    private static final String RUTA_ARCHIVO = "Socios.txt";
    private static final String RUTA_RESERVAS = "Reservas.txt";
    private static final String RUTA_EQUIPAMIENTO = "Equipamiento.txt";
    private static final String RUTA_FACTURAS = "Facturas.txt";
    private static final String RUTA_ALQUILERES = "Alquileres.txt";
    private static final String RUTA_ACTIVIDADES = "Actividades.txt";

    // ---------------- Socios.txt ----------------
    public void guardarSocios(Map<String, Socio> socios) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(socios);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de socios", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Socio> leerSocios() throws PersistenciaException {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<String, Socio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error critico al leer el archivo de socios", e);
        }
    }

    // ---------------- Reservas.txt ----------------
    public void guardarReservas(Map<String, Set<Reserva>> reservas) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_RESERVAS))) {
            oos.writeObject(reservas);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de reservas", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Set<Reserva>> leerReservas() throws PersistenciaException {
        File archivo = new File(RUTA_RESERVAS);
        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<String, Set<Reserva>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error critico al leer el archivo de reservas", e);
        }
    }

    // ---------------- Equipamiento.txt (catalogo de equipamiento por codigo) ----------------
    public void guardarEquipamiento(Map<String, Equipamiento> equipamiento) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_EQUIPAMIENTO))) {
            oos.writeObject(equipamiento);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de equipamiento", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Equipamiento> leerEquipamiento() throws PersistenciaException {
        File archivo = new File(RUTA_EQUIPAMIENTO);
        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<String, Equipamiento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al leer el archivo de equipamiento", e);
        }
    }

    // ---------------- Alquileres.txt (historial de alquileres) ----------------
    public void guardarAlquileres(List<Alquiler> alquileres) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ALQUILERES))) {
            oos.writeObject(alquileres);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de alquileres", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Alquiler> leerAlquileres() throws PersistenciaException {
        File archivo = new File(RUTA_ALQUILERES);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Alquiler>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al leer el archivo de alquileres", e);
        }
    }

    // ---------------- Facturas.txt ----------------
    public void guardarFacturas(List<Factura> facturas) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_FACTURAS))) {
            oos.writeObject(facturas);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de facturas", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Factura> leerFacturas() throws PersistenciaException {
        File archivo = new File(RUTA_FACTURAS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Factura>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al leer el archivo de facturas", e);
        }
    }

    // ---------------- Actividades.txt (catalogo de actividades con cupo) ----------------
    public void guardarActividades(Map<String, Actividad> actividades) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ACTIVIDADES))) {
            oos.writeObject(actividades);
        } catch (IOException e) {
            throw new PersistenciaException("Error al guardar el archivo de actividades", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Actividad> leerActividades() throws PersistenciaException {
        File archivo = new File(RUTA_ACTIVIDADES);
        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Map<String, Actividad>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al leer el archivo de actividades", e);
        }
    }
}
