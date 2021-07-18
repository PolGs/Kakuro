package Domini;

import java.util.ArrayList;
import java.util.Random;

public class Partida {
    private long temps;
    private long ultimTemps;
    private boolean pausat;
    private int pistesUsades;
    private final Kakuro kakuro;

    public Partida(Kakuro kakuro) {
        this.kakuro = kakuro;

        temps = 0;
        pistesUsades = 0;
        pausat = false;
        ultimTemps = System.currentTimeMillis();
    }

    public Partida(Kakuro kakuro, long temps, int pistesUsades) {
        this.kakuro = kakuro;
        this.temps = temps;
        this.pistesUsades = pistesUsades;

        pausat = false;
        ultimTemps = System.currentTimeMillis();
    }

    public void pausar_temps() {
        if (!pausat) {
            long tempsActual = System.currentTimeMillis();
            temps += tempsActual - ultimTemps;
            pausat = true;
        }
    }

    public void continuar_temps() {
        if (pausat) {
            ultimTemps = System.currentTimeMillis();
            pausat = false;
        }
    }

    public long consultar_temps() {
        if (!pausat) {
            long tempsActual = System.currentTimeMillis();
            temps += tempsActual - ultimTemps;
            ultimTemps = tempsActual;
        }
        return temps;
    }

    private void revelar_casella_buida() throws Exception {
        Casella[][] caselles = kakuro.consultar_caselles();
        ArrayList<CasellaBlanca> casellesBuides = new ArrayList<>();
        for (Casella[] fila : caselles) {
            for (Casella casella : fila) {
                if (!(casella instanceof CasellaBlanca)) {
                    continue;
                }
                CasellaBlanca blanca = (CasellaBlanca) casella;
                if (blanca.consultar_valor() != -1) {
                    continue;
                }
                casellesBuides.add(blanca);
            }
        }
        if (casellesBuides.size() == 0) {
            throw new Exception("No hi ha cap casella buida per revelar");
        }
        int randomIndex = new Random().nextInt(casellesBuides.size());
        CasellaBlanca seleccionada = casellesBuides.get(randomIndex);
        seleccionada.escriure_correcte_a_valor();
    }

    private void comprovar_caselles_plenes() throws Exception {
        int comprovades = 0;
        Casella[][] caselles = kakuro.consultar_caselles();
        for (Casella[] fila : caselles) {
            for (Casella casella : fila) {
                if (!(casella instanceof CasellaBlanca)) {
                    continue;
                }
                CasellaBlanca blanca = (CasellaBlanca) casella;
                if (blanca.consultar_valor() == -1) {
                    continue;
                }
                comprovades++;
            }
        }
        if (comprovades == 0) {
            throw new Exception("No hi ha cap casella plena per comprovar");
        }
    }

    public void utilitzar_pista(Pista pista) throws Exception {
        switch (pista) {
            case revelar_casella_buida: {
                revelar_casella_buida();
                pistesUsades++;
                break;
            }
            case comprovar_caselles_plenes: {
                comprovar_caselles_plenes();
                pistesUsades++;
                break;
            }
        }
    }

    public int consultar_pistes_usades() {
        return pistesUsades;
    }

    public Kakuro consultar_kakuro() {
        return kakuro;
    }
}
