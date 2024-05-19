package main;

/**
 *
 * @author Thomas Freitas
 */
public class Jugada {

    private int codP1;
    private int codP2;
    private int codArma;
    private int codEscudo;
    
    public Jugada(int codP1, int codArma, int codP2, int codEscudo) {
        this.codP1 = codP1;
        this.codArma = codArma;
        this.codP2 = codP2;
        this.codEscudo = codEscudo;
    }

    public int getCodP1() {
        return codP1;
    }

    public void setCodP1(int codP1) {
        this.codP1 = codP1;
    }

    public int getCodP2() {
        return codP2;
    }

    public void setCodP2(int codP2) {
        this.codP2 = codP2;
    }

    public int getCodArma() {
        return codArma;
    }

    public void setCodArma(int codArma) {
        this.codArma = codArma;
    }

    public int getCodEscudo() {
        return codEscudo;
    }

    public void setCodEscudo(int codEscudo) {
        this.codEscudo = codEscudo;
    }

    @Override
    public String toString() {
        return "Jugada{" + "codP1=" + codP1 + ", codP2=" + codP2 + ", codArma=" + codArma + ", codEscudo=" + codEscudo + '}';
    }
    
}
