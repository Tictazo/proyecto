import java.util.LinkedList;
import java.util.Queue;

public class ColaEdiciones {
    private Queue<String> cola = new LinkedList<>();

    public void encolar(String id) {
        cola.offer(id);
    }

    public String desencolar() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public Queue<String> obtenerCola() {
        return cola;
    }
}