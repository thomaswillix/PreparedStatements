package main;

/**
 *
 * @author Thomas Freitas
 */
public class Departamento {
    
    private int cod;
    private String nombre;
    private String ubicacion;
    private int presupuesto;

    public Departamento(int cod, String nombre, String ubicacion, int presupuesto) {
        this.cod = cod;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.presupuesto = presupuesto;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    @Override
    public String toString() {
        return "Departamento{" + "cod=" + cod + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", presupuesto=" + presupuesto + '}';
    }
    
}
