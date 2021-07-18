package Domini;

import java.util.Objects;

public class CasellaNegra extends Casella{
    private int pistaHorizontal;
    private int pistaVertical;
    private char code = '#';

    public CasellaNegra() {
        pistaVertical = -1;
        pistaHorizontal = -1;
    }

    public CasellaNegra(int pistaHorizontal, int pistaVertical) {
        this.pistaHorizontal = pistaHorizontal;
        this.pistaVertical = pistaVertical;
    }

    public int consultar_pistaHorizontal() {
        return pistaHorizontal;
    }

    public int consultar_pistaVertical() {
        return pistaVertical;
    }

    public void setpistaVertical(int pista) { this.pistaVertical = pista; }

    public void setpistaHorizontal(int pista) { this.pistaHorizontal = pista; }

    public char getCode(){return code;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasellaNegra that = (CasellaNegra) o;
        return pistaHorizontal == that.pistaHorizontal &&
                pistaVertical == that.pistaVertical;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pistaHorizontal, pistaVertical);
    }
}
