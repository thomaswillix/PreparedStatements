package main;

/**
 *
 * @author Thomas Freitas
 */
public class Escudo {

    private int id;
    private String nombre;
    private int defensa;
    
    public Escudo(int id, String nombre, int defensa) {
        this.id = id;
        this.nombre = nombre;
        this.defensa = defensa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    @Override
    public String toString() {
        return "Escudo{" + "id=" + id + ", nombre=" + nombre + ", defensa=" + defensa + '}';
    }
    
}
