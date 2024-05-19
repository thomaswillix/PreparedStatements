package main;

/**
 *
 * @author Thomas Freitas
 */
public class Personaje {

    private int id;
    private String nombre;
    private int vida;
    
    public Personaje(int id, String nombre, int vida) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    @Override
    public String toString() {
        return "Personaje{" + "id=" + id + ", nombre=" + nombre + ", vida=" + vida + '}';
    }
    
}
