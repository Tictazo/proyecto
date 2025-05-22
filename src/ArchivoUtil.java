import java.io.*;
import java.util.ArrayList;

public class ArchivoUtil {

    public static void guardar(ArrayList<Empleado> lista, String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Empleado e : lista) {
                pw.println(e.getId() + "," + e.getNombre() + "," + e.getEdad() + "," + e.getSexo() + "," +
                        e.getDireccion() + "," + e.getTelefono() + "," + e.getPuesto() + "," +
                        e.getDepartamento() + "," + e.getHoras() + "," + e.getCostoHora());
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar en archivo: " + nombreArchivo);
            ex.printStackTrace();
        }
    }

    public static ArrayList<Empleado> cargar(String nombreArchivo) {
        ArrayList<Empleado> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 10) {
                    try {
                        String id = partes[0];
                        String nombre = partes[1];
                        int edad = Integer.parseInt(partes[2]);
                        String sexo = partes[3];
                        String direccion = partes[4];
                        String telefono = partes[5];
                        String puesto = partes[6];
                        String departamento = partes[7];
                        double horas = Double.parseDouble(partes[8]);
                        double costoHora = Double.parseDouble(partes[9]);

                        Empleado e = new Empleado(id, nombre, edad, sexo, direccion, telefono,
                                puesto, departamento, horas, costoHora);
                        lista.add(e);
                    } catch (NumberFormatException ex) {
                        System.err.println("Error de formato numérico en línea: " + linea);
                    }
                } else {
                    System.err.println("Línea con formato incorrecto: " + linea);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + nombreArchivo);
            ex.printStackTrace();
        }
        return lista;
    }
}