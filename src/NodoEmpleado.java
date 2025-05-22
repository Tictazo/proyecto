public class NodoEmpleado {
    private Empleado empleado;
    NodoEmpleado siguiente;

    public NodoEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
}