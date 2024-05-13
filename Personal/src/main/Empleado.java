package main;

/**
 *
 * @author Thomas Freitas   
 */
public class Empleado {
    
    private String codigo;
    private String nombre;
    private int anioAlta;
    private int salario;
    private int dpto;
    
    public Empleado(String codigo, String nombre, int anioAlta, int salario, int dpto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.anioAlta = anioAlta;
        this.salario = salario;
        this.dpto = dpto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioAlta() {
        return anioAlta;
    }

    public void setAnioAlta(int anioAlta) {
        this.anioAlta = anioAlta;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getDpto() {
        return dpto;
    }

    public void setDpto(int dpto) {
        this.dpto = dpto;
    }

    @Override
    public String toString() {
        return "Empleado{" + "codigo=" + codigo + ", nombre=" + nombre + ", anioAlta=" + anioAlta + ", salario=" + salario + ", dpto=" + dpto + '}';
    }
    
}
