package Domini.Drivers;

import Domini.CasellaNegra;

import java.util.Scanner;

public class CasellaNegraDriver {
    private static Scanner scanner = null;

    public static void test_constructor() {
        System.out.println("Introdueix el parametre int pistaHorizontal:");
        int pistaHorizontal = scanner.nextInt();
        System.out.println("Introdueix el parametre int pistaVertical:");
        int pistaVertical = scanner.nextInt();
        CasellaNegra casella = new CasellaNegra(pistaHorizontal, pistaVertical);
        System.out.println("pistaHorizontal consultada: " + casella.consultar_pistaHorizontal());
        System.out.println("pistaVertical consultada: " + casella.consultar_pistaVertical());
    }

    public static void test_consultar_pista_horizontal() {
        System.out.println("Introdueix el parametre int pistaHorizontal:");
        int pistaHorizontal = scanner.nextInt();
        CasellaNegra casella = new CasellaNegra(pistaHorizontal, 0);
        System.out.println("pistaHorizontal consultada: " + casella.consultar_pistaHorizontal());
    }

    public static void test_consultar_pista_vertical() {
        System.out.println("Introdueix el parametre int pistaVertical:");
        int pistaVertical = scanner.nextInt();
        CasellaNegra casella = new CasellaNegra(0, pistaVertical);
        System.out.println("pistaVertical consultada: " + casella.consultar_pistaVertical());
    }

    public static void main(String[] args) {
        while(true) {
            System.out.println("\nFuncions:");
            System.out.println("[0]: constructor(int pistaHorizontal, int pistaVertical)");
            System.out.println("[1]: consultar_pistaHorizontal()");
            System.out.println("[2]: consultar_pistaVertical()");
            System.out.println("Escriu el número associat a la funció que vols provar:");
            scanner = new Scanner(System.in);
            int num = scanner.nextInt();
            switch (num) {
                case 0:
                    test_constructor();
                    break;
                case 1:
                    test_consultar_pista_horizontal();
                    break;
                case 2:
                    test_consultar_pista_vertical();
                    break;
            }
        }
    }
}
