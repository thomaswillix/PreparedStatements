package clases;

/**
 *
 * @author Thomas Freitas
 */
public class Emparejamiento {
    int codM;
    int codH;
    double afinidad;

    public Emparejamiento(int codH, int codM, double afinidad) {
        this.codM = codM;
        this.codH = codH;
        this.afinidad = afinidad;
    }
    public Emparejamiento(int codH, int codM) {
        this.codM = codM;
        this.codH = codH;
    }

    public int getCodM() {
        return codM;
    }

    public void setCodM(int codM) {
        this.codM = codM;
    }

    public int getCodH() {
        return codH;
    }

    public void setCodH(int codH) {
        this.codH = codH;
    }

    public double getAfinidad() {
        return afinidad;
    }

    public void setAfinidad(double afinidad) {
        this.afinidad = afinidad;
    }

    @Override
    public String toString() {
        return "Emparejamiento{" + "codM=" + codM + ", codH=" + codH + ", afinidad=" + afinidad + '}';
    }
    
}
