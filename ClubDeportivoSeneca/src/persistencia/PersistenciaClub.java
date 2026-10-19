package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelo.Club;

public class PersistenciaClub {

    public static final String ARCHIVO = "club.dat";

    public static void guardar(Club club, String carpetaDatos) throws IOException {
        File dir = new File(carpetaDatos);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta de datos: " + carpetaDatos);
        }
        File archivo = new File(dir, ARCHIVO);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(club);
        }
    }

    public static Club cargar(String carpetaDatos) throws IOException, ClassNotFoundException {
        File archivo = new File(carpetaDatos, ARCHIVO);
        if (!archivo.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Club) in.readObject();
        }
    }
}
