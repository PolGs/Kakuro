package Domini;

import java.util.ArrayList;

public class CtrlStringKakuro {

    public Kakuro de_string_a_kakuro(String string) {
        String[] linies = string.split("\n");
        int files = Integer.parseInt(linies[0].split(",")[0]);
        int columnes = Integer.parseInt(linies[0].split(",")[1]);

        Casella[][] caselles = new Casella[files][columnes];
        for (int i = 0; i < files; i++) {
            String[] fila = linies[i + 1].split(",");
            for (int j = 0; j < columnes; j++) {
                Casella casella;
                if (fila[j].equals("?")) {
                    casella = new CasellaBlanca(0);
                } else if (fila[j].equals("*")) {
                    casella = new CasellaNegra(-1, -1);
                } else if (fila[j].charAt(0) != 'F' && fila[j].charAt(0) != 'C'){
                    casella = new CasellaBlanca(fila[j].charAt(0)-48);
                    ((CasellaBlanca) casella).escriure_correcte_a_valor();
                } else {
                    String[] splitted = fila[j].split("F");
                    int pistaHorizontal = -1;
                    int pistaVertical = -1;
                    if (splitted[0].length() > 0 && splitted[0].charAt(0) == 'C') {
                        pistaVertical = Integer.parseInt(splitted[0].substring(1));
                    }
                    if (splitted[0].length() > 0 && splitted[0].charAt(0) == 'F') {
                        pistaHorizontal = Integer.parseInt(splitted[0].substring(1));
                    }
                    if (splitted.length > 1) {
                        pistaHorizontal = Integer.parseInt(splitted[1]);
                    }
                    casella = new CasellaNegra(pistaHorizontal, pistaVertical);
                }
                caselles[i][j] = casella;
            }
        }
        return new Kakuro(caselles);
    }

    public String de_kakuro_a_string(Kakuro kakuro) {
        Casella[][] caselles = kakuro.consultar_caselles();
        StringBuilder builder = new StringBuilder(caselles.length + "," + caselles[0].length);
        for (Casella[] fila : caselles) {
            builder.append('\n');
            for (int i = 0; i < fila.length; i++) {
                if (i != 0) {
                    builder.append(',');
                }
                if (fila[i] instanceof CasellaBlanca) {
                    CasellaBlanca blanca = (CasellaBlanca) fila[i];
                    if (blanca.consultar_valor() == -1) {
                        builder.append('?');
                    } else {
                        builder.append(blanca.consultar_valor());
                    }
                } else {
                    CasellaNegra negra = (CasellaNegra) fila[i];
                    if (negra.consultar_pistaHorizontal() == -1 && negra.consultar_pistaVertical() == -1) {
                        builder.append('*');
                    } else {
                        if (negra.consultar_pistaVertical() != -1) {
                            builder.append("C").append(negra.consultar_pistaVertical());
                        }
                        if (negra.consultar_pistaHorizontal() != -1) {
                            builder.append("F").append(negra.consultar_pistaHorizontal());
                        }
                    }
                }
            }
        }
        return builder.toString();
    }

}
