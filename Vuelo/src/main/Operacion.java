package main;

/**
 *
 * @author Thomas Freitas
 */
public class Operacion {

    private int id;
    private int num;
    private String ciudad;
    private String fecha;
    private char operacion;

    public Operacion(int id, int num, String ciudad, String fecha, char operacion) {
        this.id = id;
        this.num = num;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.operacion = operacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public char getOperacion() {
        return operacion;
    }

    public void setOperacion(char operacion) {
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "Operacion{" + "id=" + id + ", num=" + num + ", ciudad=" + ciudad + ", fecha=" + fecha + ", operacion=" + operacion + '}';
    }
    
    
}
