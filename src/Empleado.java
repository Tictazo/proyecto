public class Empleado {
    private String id, nombre, sexo, direccion, telefono, puesto, departamento;
    private int edad;
    private double horas, costoHora, salario;

    public Empleado(String id, String nombre, int edad, String sexo, String direccion, String telefono,
                    String puesto, String departamento, double horas, double costoHora) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.puesto = puesto;
        this.departamento = departamento;
        this.horas = horas;
        this.costoHora = costoHora;
        this.salario = horas * costoHora;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public double getHoras() {
        return horas;
    }

    public double getCostoHora() {
        return costoHora;
    }

    public double getSalario() {
        return salario;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setHoras(double horas) {
        this.horas = horas;
        this.salario = this.horas * this.costoHora;  // recalcula salario
    }

    public void setCostoHora(double costoHora) {
        this.costoHora = costoHora;
        this.salario = this.horas * this.costoHora;  // recalcula salario
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad + ", Sexo: " + sexo +
                ", Dirección: " + direccion + ", Teléfono: " + telefono +
                ", Puesto: " + puesto + ", Departamento: " + departamento +
                ", Horas: " + horas + ", Costo/Hora: " + costoHora + ", Sueldo: $" + salario;
    }
}
