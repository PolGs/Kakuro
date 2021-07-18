package Domini;

import Persistencia.CtrlPersistencia;

import java.util.ArrayList;
import java.util.Arrays;

public class Repositori {
    private final ArrayList<Kakuro> kakuros;

    public Repositori(ArrayList<Kakuro> kakuros) {
        this.kakuros = kakuros;
    }

    public void afegir(Kakuro kakuro) throws Exception {
        if (kakuros.contains(kakuro)) {
            throw new Exception("Has intentat afegir un kakuro que ja està al repositori");
        }
        kakuros.add(kakuro);
    }

    public void eliminar(Kakuro kakuro) throws Exception {
        if (!kakuros.contains(kakuro)) {
            throw new Exception("Has intentat eliminar un kakuro que no està al repositori");
        }
        kakuros.remove(kakuro);
    }

    public ArrayList<Kakuro> consultar() {
        return kakuros;
    }

    public void buidar() {
        kakuros.clear();
    }
}
