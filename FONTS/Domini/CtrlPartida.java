package Domini;

import Persistencia.CtrlPersistencia;

public class CtrlPartida {
    private Partida partida;
    private final CtrlStringKakuro ctrlStringKakuro;
    private final CtrlPersistencia ctrlPersistencia;

    public CtrlPartida(CtrlStringKakuro ctrlStringKakuro, CtrlPersistencia ctrlPersistencia) {
        this.ctrlStringKakuro = ctrlStringKakuro;
        this.ctrlPersistencia = ctrlPersistencia;
    }

    public void startPartida(CtrlKakuro ctrlKakuro) {
        partida = new Partida(ctrlKakuro.getKakuro());
    }

    public boolean hasPartida(String usuari) {
        String string = ctrlPersistencia.llegirArxiu("partida.txt");
        String[] partides = string.split("\n\n");
        for (String partidaString : partides) {
            String[] infoPartidaKakuro = partidaString.split("\n", 2);
            String[] infoPartida = infoPartidaKakuro[0].split(" ");
            if (infoPartida[0].equals(usuari)) {
                return true;
            }
        }
        return false;
    }

    public void loadPartida(String usuari) throws Exception {
        String string = ctrlPersistencia.llegirArxiu("partida.txt");
        String[] partides = string.split("\n\n");
        boolean loaded = false;
        for (String partidaString : partides) {
            String[] infoPartidaKakuro = partidaString.split("\n", 2);
            String[] infoPartida = infoPartidaKakuro[0].split(" ");
            if (infoPartida[0].equals(usuari)) {
                loaded = true;
                long temps = Integer.parseInt(infoPartida[1]);
                int pistesUsades = Integer.parseInt(infoPartida[2]);
                Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(infoPartidaKakuro[1]);
                partida = new Partida(kakuro, temps, pistesUsades);
                break;
            }
        }
        if (!loaded) {
            throw new Exception("Aquest usuari no té la partida guardada");
        }
    }

    public void savePartida(String usuari) {
        String string = ctrlPersistencia.llegirArxiu("partida.txt");
        String[] partides = string.split("\n\n");
        StringBuilder builder = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < partides.length; i++) {
            if (i != 0) {
                builder.append("\n\n");
            }
            String[] infoPartidaKakuro = partides[i].split("\n", 2);
            String[] infoPartida = infoPartidaKakuro[0].split(" ");
            if (!infoPartida[0].equals(usuari)) {
                builder.append(partides[i]);
            } else {
                found = true;
                builder.append(usuari).append(' ');
                builder.append(partida.consultar_temps()).append(' ');
                builder.append(partida.consultar_pistes_usades()).append('\n');
                String kakuro = ctrlStringKakuro.de_kakuro_a_string(partida.consultar_kakuro());
                builder.append(kakuro);
            }
        }
        if (!found) {
            if (builder.length() != 0) {
                builder.append("\n\n");
            }
            builder.append(usuari).append(' ');
            builder.append(partida.consultar_temps()).append(' ');
            builder.append(partida.consultar_pistes_usades()).append('\n');
            String kakuro = ctrlStringKakuro.de_kakuro_a_string(partida.consultar_kakuro());
            builder.append(kakuro);
        }
        ctrlPersistencia.escriureArxiu("partida.txt", builder.toString());
    }

    public void clearPartida(String usuari) throws Exception {
        String string = ctrlPersistencia.llegirArxiu("partida.txt");
        String[] partides = string.split("\n\n");
        StringBuilder builder = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < partides.length; i++) {
            if (i != 0) {
                builder.append("\n\n");
            }
            String[] infoPartidaKakuro = partides[i].split("\n", 2);
            String[] infoPartida = infoPartidaKakuro[0].split(" ");
            if (!infoPartida[0].equals(usuari)) {
                builder.append(partides[i]);
            } else {
                found = true;
            }
        }
        if (!found) {
            throw new Exception("Aquest usuari no té cap partida guardada");
        }
        ctrlPersistencia.escriureArxiu("partida.txt", builder.toString());
    }

    public long getTemps() {
        return partida.consultar_temps();
    }

    public int getPistesUsades() {
        return partida.consultar_pistes_usades();
    }

    public CtrlKakuro getKakuro() {
        CtrlKakuro ctrlKakuro = new CtrlKakuro();
        ctrlKakuro.setKakuro(partida.consultar_kakuro());
        return ctrlKakuro;
    }

    public void pausePartida() {
        partida.pausar_temps();
    }

    public void continuePartida() {
        partida.continuar_temps();
    }

    public void usePista(Pista pista) throws Exception {
        partida.utilitzar_pista(pista);
    }
}
