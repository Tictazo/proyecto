import java.util.Stack;

public class HistorialEliminaciones {
    private Stack<Empleado> pila = new Stack<>();

    public void agregar(Empleado e) {
        pila.push(e);
    }

    public Empleado recuperar() {
        return pila.isEmpty() ? null : pila.pop();
    }
    public Stack<Empleado> obtenerHistorial() {
        Stack<Empleado> copia = new Stack<>();
        copia.addAll(pila);
        return copia;
    }
}