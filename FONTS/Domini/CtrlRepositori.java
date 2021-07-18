package Domini;

import Persistencia.CtrlPersistencia;

import java.util.ArrayList;

public class CtrlRepositori {
    private final Repositori repositori;
    private final CtrlStringKakuro ctrlStringKakuro;
    private final CtrlPersistencia ctrlPersistencia;

    public CtrlRepositori(CtrlStringKakuro ctrlStringKakuro, CtrlPersistencia ctrlPersistencia) {
        this.ctrlStringKakuro = ctrlStringKakuro;
        this.ctrlPersistencia = ctrlPersistencia;
        repositori = loadRepositori();
    }

    public void addKakuro(CtrlKakuro ctrlKakuro) throws Exception {
        Kakuro kakuro = ctrlKakuro.getKakuro();
        repositori.afegir(kakuro);
        saveRepositori();
    }

    public String[] getKakuroInfoList() {
        ArrayList<Kakuro> kakuros = repositori.consultar();
        String[] kakuroInfos = new String[kakuros.size()];
        for (int i = 0; i < kakuros.size(); i++) {
            Kakuro kakuro = kakuros.get(i);
            int[] size = kakuro.consultarMida();
            String difficulty = kakuro.consultar_dificultat().name();
            kakuroInfos[i] = size[0] + "x" + size[1] + " " + difficulty;
        }
        return kakuroInfos;
    }

    public CtrlKakuro[] getKakuroList() {
        ArrayList<Kakuro> kakuros = repositori.consultar();
        CtrlKakuro[] ctrlKakuros = new CtrlKakuro[kakuros.size()];
        for (int i = 0; i < kakuros.size(); i++) {
            ctrlKakuros[i] = new CtrlKakuro();
            ctrlKakuros[i].setKakuro(kakuros.get(i));
        }
        return ctrlKakuros;
    }

    public void removeAt(int index) {
        ArrayList<Kakuro> kakuros = repositori.consultar();
        Kakuro toRemove = kakuros.get(index);
        try {
            repositori.eliminar(toRemove);
            saveRepositori();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void empty() {
        repositori.buidar();
        saveRepositori();
    }

    private Repositori loadRepositori() {
        String string = ctrlPersistencia.llegirArxiu("repositori.txt");
        ArrayList<Kakuro> kakuros;
        if (string.equals("")) {
            kakuros = new ArrayList<>(0);
        } else {
            String[] kakuroStrings = string.split("\n\n");
            kakuros = new ArrayList<>(kakuroStrings.length);
            for (String kakuroString : kakuroStrings) {
                kakuros.add(ctrlStringKakuro.de_string_a_kakuro(kakuroString));
            }
        }
        return new Repositori(kakuros);
    }

    private void saveRepositori() {
        ArrayList<Kakuro> kakuros = repositori.consultar();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < kakuros.size(); i++) {
            if (i != 0) {
                builder.append("\n\n");
            }
            String kakuroString = ctrlStringKakuro.de_kakuro_a_string(kakuros.get(i));
            builder.append(kakuroString);
        }
        ctrlPersistencia.escriureArxiu("repositori.txt", builder.toString());
    }
}
