import java.util.ArrayList;

public class ListaEmpleados {
    private NodoEmpleado cabeza;

    public void agregar(Empleado e) {
        NodoEmpleado nuevo = new NodoEmpleado(e);
        if (cabeza == null) cabeza = nuevo;
        else {
            NodoEmpleado aux = cabeza;
            while (aux.getSiguiente() != null) aux = aux.getSiguiente();
            aux.setSiguiente(nuevo);
        }
    }

    public boolean eliminar(String id) {
        if (cabeza == null) return false;
        if (cabeza.getEmpleado().getId().equals(id)) {
            cabeza = cabeza.getSiguiente();
            return true;
        }
        NodoEmpleado ant = cabeza, act = cabeza.getSiguiente();
        while (act != null && !act.getEmpleado().getId().equals(id)) {
            ant = act;
            act = act.getSiguiente();
        }
        if (act != null) {
            ant.setSiguiente(act.getSiguiente());
            return true;
        }
        return false;
    }

    public Empleado buscar(String id) {
        NodoEmpleado aux = cabeza;
        while (aux != null) {
            if (aux.getEmpleado().getId().equals(id)) return aux.getEmpleado();
            aux = aux.getSiguiente();
        }
        return null;
    }

    public ArrayList<Empleado> obtenerTodos() {
        ArrayList<Empleado> lista = new ArrayList<>();
        NodoEmpleado aux = cabeza;
        while (aux != null) {
            lista.add(aux.getEmpleado());
            aux = aux.getSiguiente();
        }
        return lista;
    }

    public void insertarOrdenado(Empleado nuevo) {
        NodoEmpleado nuevoNodo = new NodoEmpleado(nuevo);
        if (cabeza == null || nuevo.getNombre().compareToIgnoreCase(cabeza.getEmpleado().getNombre()) < 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
            return;
        }

        NodoEmpleado actual = cabeza;
        while (actual.getSiguiente() != null &&
                nuevo.getNombre().compareToIgnoreCase(actual.getSiguiente().getEmpleado().getNombre()) > 0) {
            actual = actual.getSiguiente();
        }

        nuevoNodo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevoNodo);
    }

    // Clase interna NodoEmpleado
    public static class NodoEmpleado {
        private Empleado empleado;
        private NodoEmpleado siguiente;

        public NodoEmpleado(Empleado empleado) {
            this.empleado = empleado;
        }

        public Empleado getEmpleado() {
            return empleado;
        }

        public NodoEmpleado getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoEmpleado siguiente) {
            this.siguiente = siguiente;
        }
    }
}
