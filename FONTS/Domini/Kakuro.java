package Domini;

import java.util.Arrays;

public class Kakuro {
    private Casella[][] caselles;
    private final Dificultat dificultat;

    public Kakuro(Casella[][] caselles) {
        this.caselles = caselles;
        this.dificultat = Dificultat.Facil;
    }

    public int[] consultarMida() {
        return new int[] { caselles.length, caselles[0].length };
    }

    public Casella[][] consultar_caselles() {
        return caselles;
    }

    public Dificultat consultar_dificultat() {
        return dificultat;
    }

    public void escriure_caselles(Casella[][] caselles) {
        this.caselles = caselles;
    }

    public void solucionar(){
        for(int i=0; i< caselles.length; ++i){
            for(int j=0; j<caselles[i].length; ++j){
                if (caselles[i][j] instanceof CasellaBlanca) ((CasellaBlanca) caselles[i][j]).escriure_correcte_a_valor();
            }
        }
    }

    public void printKakuro() {
        //noinspection ForLoopReplaceableByForEach
        System.out.printf("%d.   ",0);
        for(int i = 0; i < caselles[0].length; ++i){
            if(i<10)System.out.printf("%d  ",i);
            else System.out.printf("%d ",i);
        }
        System.out.printf("\n");
        for (int i = 0; i < caselles.length; ++i) {
            if(i<10)System.out.printf("%d.   ",i);
            else System.out.printf("%d.  ",i);
            for (int j = 0; j < caselles[0].length; ++j) {
                if(caselles[i][j] instanceof CasellaNegra) System.out.print((caselles[i][j]).getCode());
                else {
                    if(((CasellaBlanca)caselles[i][j]).consultar_valor() == -1){
                        System.out.print(0);
                    }else System.out.print(((CasellaBlanca)caselles[i][j]).consultar_valor());
                }
                System.out.printf("  ");
            }
            System.out.print('\n');
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kakuro kakuro = (Kakuro) o;
        return Arrays.deepEquals(caselles, kakuro.caselles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(caselles);
    }
}
