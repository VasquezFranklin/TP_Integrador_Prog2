package Datos;

import LogicaNegocio.Exceptions.PersistenciaException;
import LogicaNegocio.Socio;
import java.io.*; //bueno aca traigo todo porq si, te ahorras como 3 lineas :v
import java.util.HashMap;
import java.util.Map;

public class AccesoDatos {
    private static final String RUTA_ARCHIVO = "Socios.txt";

    public void guardarSocios(Map<String, Socio> socios) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(socios);
        } catch (IOException e) {
            throw new PersistenciaException("Error critico al guardar el archivo de socios: " + RUTA_ARCHIVO, e);
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
            throw new PersistenciaException("Error critico al leer el archivo de socios: " + RUTA_ARCHIVO, e);
        }
    }
}