package Domini.Drivers;

import Domini.*;
import Persistencia.CtrlPersistencia;

import java.util.Scanner;

public class PartidaDriver {

    private static Scanner scanner;
    private static CtrlStringKakuro ctrlStringKakuro;
    private static CtrlPersistencia ctrlPersistencia;
    private static Partida partida;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        CtrlDomini ctrlDomini = new CtrlDomini();
        ctrlStringKakuro = ctrlDomini.getCtrlStringKakuro();
        ctrlPersistencia = new CtrlPersistencia();
        System.out.println("Per inicialitzar una partida necessites un kakuro");
        System.out.println("Introdueix el nom d'un arxiu que contingui el kakuro");
        String arxiu = scanner.next();
        String string = ctrlPersistencia.llegirArxiu(arxiu);
        Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(string);
        partida = new Partida(kakuro);
        boolean loop = true;
        while (loop) {
            System.out.println("Escriu el numero de la funcio a provar, -1 per sortir:");
            System.out.println("1. pausar_temps()");
            System.out.println("2. continuar_temps()");
            System.out.println("3. consultar_temps()");
            System.out.println("4. utilitzar_pista(Pista pista)");
            System.out.println("5. consultar_pistes_usades()");
            System.out.println("6. consultar_kakuro()");
            int num = scanner.nextInt();
            switch (num) {
                case 1:
                    partida.pausar_temps();
                    System.out.println("Temps pausat");
                    break;
                case 2:
                    partida.continuar_temps();
                    System.out.println("Temps continuat");
                    break;
                case 3:
                    System.out.println("Temps: " + partida.consultar_temps() + " ms");
                    break;
                case 4:
                    test_utilitzar_pista();
                    break;
                case 5:
                    System.out.println("Pistes usades: " + partida.consultar_pistes_usades());
                    break;
                case 6:
                    test_consultar_kakuro();
                    break;
                case -1:
                    loop = false;
                    break;
            }
        }
    }

    private static void test_utilitzar_pista() {
        boolean loop = true;
        while (loop) {
            System.out.println("Escriu el numero de la pista a utilitzar, -1 per sortir:");
            System.out.println("1. revelar casella buida");
            System.out.println("2. comprovar caselles plenes");
            int num = scanner.nextInt();
            switch (num) {
                case 1:
                    try {
                        partida.utilitzar_pista(Pista.revelar_casella_buida);
                        System.out.println("Pista utilitzada");
                        System.out.println("Consulta el kakuro per veure els canvis");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        partida.utilitzar_pista(Pista.comprovar_caselles_plenes);
                        System.out.println("Pista utilitzada");
                        System.out.println("Consulta el kakuro per veure els canvis");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case -1:
                    loop = false;
                    break;
            }
        }
    }

    private static void test_consultar_kakuro() {
        System.out.println("Introdueix el nom d'un arxiu on guardar el kakuro consultat");
        String arxiu = scanner.next();
        Kakuro kakuro = partida.consultar_kakuro();
        String string = ctrlStringKakuro.de_kakuro_a_string(kakuro);
        ctrlPersistencia.escriureArxiu(arxiu, string);
        System.out.println("kakuro consultat cap a " + arxiu);
    }
}
