package Domini.Drivers;

import Domini.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class KakuroDriver {
    private static Scanner scanner = null;
    private static CtrlStringKakuro ctrlStringKakuro;

    public static void main(String[] args) {
        CtrlDomini ctrlDomini = new CtrlDomini();
        ctrlStringKakuro = ctrlDomini.getCtrlStringKakuro();
        while (true) {
            System.out.println("Funcions:");
            System.out.println("[0]: constructor(Casella[][] caselles)");
            System.out.println("[1]: consultar_caselles()");
            System.out.println("[2]: escriure_caselles(Casella[][] caselles)");
            System.out.println("Escriu el número associat a la funció que vols provar:");
            scanner = new Scanner(System.in);
            int num = scanner.nextInt();
            switch (num) {
                case 0:
                    test_constructor();
                    break;
                case 1:
                    test_consultar_caselles();
                    break;
                case 2:
                    test_escriure_caselles();
                    break;
            }
        }
    }

    public static void test_constructor() {
        System.out.println("Introdueix caselles (des d'un kakuro)");
        Kakuro temp = llegir_kakuro();
        if (temp == null) {
            return;
        }
        Casella[][] caselles = temp.consultar_caselles();
        Kakuro kakuro = new Kakuro(caselles);
        System.out.println(ctrlStringKakuro.de_kakuro_a_string(kakuro));
    }

    public static void test_consultar_caselles() {
        System.out.println("Introdueix kakuro");
        Kakuro kakuro = llegir_kakuro();
        if (kakuro == null) {
            return;
        }
        Kakuro consultat = new Kakuro(kakuro.consultar_caselles());
        System.out.println(ctrlStringKakuro.de_kakuro_a_string(consultat));
    }

    public static void test_escriure_caselles() {
        System.out.println("Introdueix kakuro inicial");
        Kakuro kakuro = llegir_kakuro();
        if (kakuro == null) {
            return;
        }
        System.out.println("Introdueix caselles a escriure (des d'un kakuro)");
        Kakuro temp = llegir_kakuro();
        if (temp == null) {
            return;
        }
        Casella[][] caselles = temp.consultar_caselles();
        kakuro.escriure_caselles(caselles);
        System.out.println(ctrlStringKakuro.de_kakuro_a_string(kakuro));
    }

    private static Kakuro llegir_kakuro() {
        System.out.println("Escull el mètode d'entrada: 1- Teclat 2- Arxiu (.txt)");
        int entrada = scanner.nextInt();

        if (entrada == 2) {//Entrada per arxiu

            System.out.println("Escriu el nom del fitxer amb el kakuro, buit si selecciones l'opció 1 y solucionat si selecciones la 3 o 4\n");
            scanner = new Scanner(System.in);
            String kak = scanner.nextLine();
            StringBuilder builder = new StringBuilder();
            try {
                File file = new File(kak);
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    builder.append(data).append('\n');
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("L'arxiu no existeix");
                return null;
            }
            builder.deleteCharAt(builder.length() - 1);
            String string = builder.toString();

            return ctrlStringKakuro.de_string_a_kakuro(string);
        } else if (entrada == 1) { //Leer de System.in
            System.out.println("Introdueix el kakuro");
            StringBuilder builder = new StringBuilder();
            scanner = new Scanner(System.in);
            String[] linia = scanner.nextLine().split(",");
            int nfiles = Integer.parseInt(linia[0]);
            builder.append(nfiles).append(',').append(linia[1]).append('\n');
            for (int i = 0; i < nfiles; i++) {
                String data = scanner.nextLine();
                builder.append(data).append('\n');
            }
            builder.deleteCharAt(builder.length() - 1);
            String s = builder.toString();
            return ctrlStringKakuro.de_string_a_kakuro(s);
        }
        return null;
    }
}
