package main;

/**
 *
 * @author Thomas Freitas
 */
public class Venta {
    String nVendedor;
    int cod;
    int uds;

    public Venta() {
    }

    public Venta(String nVendedor, int cod, int uds) {
        this.nVendedor = nVendedor;
        this.cod = cod;
        this.uds = uds;
    }

    public String getnVendedor() {
        return nVendedor;
    }

    public void setnVendedor(String nVendedor) {
        this.nVendedor = nVendedor;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getUds() {
        return uds;
    }

    public void setUds(int uds) {
        this.uds = uds;
    }

    @Override
    public String toString() {
        return "Venta{" + "nVendedor=" + nVendedor + ", cod=" + cod + ", uds=" + uds + '}';
    }
    
}
