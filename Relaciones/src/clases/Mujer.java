package clases;

/**
 *
 * @author Thomas Freitas
 */
public class Mujer {
    int cod;
    String nombre;
    double v1;
    double v2;
    double v3;
    double v4;
    double v5;

    public Mujer(int cod, String nombre, double v1, double v2, double v3, double v4, double v5) {
        this.cod = cod;
        this.nombre = nombre;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
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

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }

    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    public double getV3() {
        return v3;
    }

    public void setV3(double v3) {
        this.v3 = v3;
    }

    public double getV4() {
        return v4;
    }

    public void setV4(double v4) {
        this.v4 = v4;
    }

    public double getV5() {
        return v5;
    }

    public void setV5(double v5) {
        this.v5 = v5;
    }

    @Override
    public String toString() {
        return "Mujer{" + "cod=" + cod + ", nombre=" + nombre + ", v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + ", v4=" + v4 + ", v5=" + v5 + '}';
    }
    
}
