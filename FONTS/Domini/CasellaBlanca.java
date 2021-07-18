package Domini;

import java.util.Objects;

public class CasellaBlanca extends Casella {
    private int valor;
    private int correcte;
    private char code = '_';

    public CasellaBlanca() {
        valor = -1;
        correcte = 0;
    }


    public CasellaBlanca(int correcte) {
        valor = -1;
        this.correcte = correcte;
    }

    public int consultar_valor() {
        return valor;
    }

    public void escriure_valor_correcte(int correcte) {
        this.correcte = correcte;
    }

    public int consultar_valor_correcte(){return correcte;}

    public void escriure_valor(int valor) {
        this.valor = valor;
    }

    public void escriure_correcte_a_valor() {
        valor = correcte;
    }

    public char getCode(){return code;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasellaBlanca that = (CasellaBlanca) o;
        return valor == that.valor &&
                correcte == that.correcte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, correcte);
    }
}
