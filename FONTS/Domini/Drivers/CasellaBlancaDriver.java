package Domini.Drivers;

import Domini.CasellaBlanca;

import java.util.Scanner;

public class CasellaBlancaDriver {
    private static Scanner scanner = null;

    public static void test_constructor() {
        System.out.println("Introdueix el parametre int valor_correcte:");
        int valor_correcte = scanner.nextInt();
        CasellaBlanca casella = new CasellaBlanca(valor_correcte);
        System.out.println("valor_correcte consultat: " + casella.consultar_valor_correcte());
    }

    public static void test_consultar_valor() {
        System.out.println("Introdueix el parametre int valor:");
        int valor = scanner.nextInt();
        CasellaBlanca casella = new CasellaBlanca(0);
        casella.escriure_valor(valor);
        System.out.println("valor consultat: " + casella.consultar_valor());
    }

    public static void test_escriure_valor() {
        System.out.println("Introdueix el parametre int valor:");
        int valor = scanner.nextInt();
        CasellaBlanca casella = new CasellaBlanca(0);
        casella.escriure_valor(valor);
        System.out.println("valor consultat: " + casella.consultar_valor());
    }

    public static void test_escriure_valor_correcte() {
        System.out.println("Introdueix el parametre int correcte:");
        int correcte = scanner.nextInt();
        CasellaBlanca casella = new CasellaBlanca(0);
        casella.escriure_valor_correcte(correcte);
        System.out.println("valor consultat: " + casella.consultar_valor_correcte());
    }

    public static void test_escriure_correcte_a_valor() {
        System.out.println("Introdueix el parametre int valor_correcte:");
        int valor_correcte = scanner.nextInt();
        CasellaBlanca casella = new CasellaBlanca(valor_correcte);
        casella.escriure_correcte_a_valor();
        System.out.println("valor consultat: " + casella.consultar_valor());
    }

    public static void main(String[] args) {
        while(true) {
            System.out.println("Funcions:");
            System.out.println("[0]: constructor(int correcte)");
            System.out.println("[1]: consultar_valor()");
            System.out.println("[2]: escriure_valor(int valor)");
            System.out.println("[3]: escriure_correcte_a_valor()");
            System.out.println("Escriu el número associat a la funció que vols provar:");
            scanner = new Scanner(System.in);
            int num = scanner.nextInt();
            switch (num) {
                case 0:
                    test_constructor();
                    break;
                case 1:
                    test_consultar_valor();
                    break;
                case 2:
                    test_escriure_valor();
                    break;
                case 3:
                    test_escriure_correcte_a_valor();
                    break;
            }
        }
    }
}
