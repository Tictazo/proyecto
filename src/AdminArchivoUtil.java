import java.io.*;
import java.util.ArrayList;

public class AdminArchivoUtil {
    private static final String ARCHIVO = "admins.txt";

    public static void guardar(Admin admin) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Admin> cargar() {
        ArrayList<Admin> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2)
                    lista.add(new Admin(partes[0], partes[1]));
            }
        } catch (IOException e) {
            // Silencioso si no existe
        }
        return lista;
    }
}
