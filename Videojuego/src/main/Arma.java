package main;

/**
 *
 * @author Thomas Freitas
 */
public class Arma {
    
    private int id;
    private String nombre;
    private int dano;
    
    public Arma(int id, String nombre, int dano) {
        this.id = id;
        this.nombre = nombre;
        this.dano = dano;
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

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    @Override
    public String toString() {
        return "Arma{" + "id=" + id + ", nombre=" + nombre + ", dano=" + dano + '}';
    }
    
}
