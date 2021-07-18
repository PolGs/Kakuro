package Domini.Drivers;

import Domini.*;
import Persistencia.CtrlPersistencia;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RepositoriDriver {

    private static Scanner scanner;
    private static CtrlStringKakuro ctrlStringKakuro;
    private static CtrlPersistencia ctrlPersistencia;
    private static Repositori repositori;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        CtrlDomini ctrlDomini = new CtrlDomini();
        ctrlStringKakuro = ctrlDomini.getCtrlStringKakuro();
        ctrlPersistencia = new CtrlPersistencia();
        repositori = new Repositori(new ArrayList<>(0));
        boolean loop = true;
        while (loop) {
            System.out.println("Escriu el numero de la funcio a provar, -1 per sortir:");
            System.out.println("1. afegir(Kakuro kakuro)");
            System.out.println("2. eliminar(Kakuro kakuro)");
            System.out.println("3. consultar()");
            System.out.println("4. buidar()");
            int num = scanner.nextInt();
            switch (num) {
                case 1:
                    test_afegir();
                    break;
                case 2:
                    test_eliminar();
                    break;
                case 3:
                    test_consultar();
                    break;
                case 4:
                    repositori.buidar();
                    System.out.println("Repositori buidat correctament");
                    break;
                case -1:
                    loop = false;
                    break;
            }
        }
    }

    private static void test_afegir() {
        System.out.println("Introdueix el nom d'un arxiu que contingui el kakuro");
        String arxiu = scanner.next();
        String string = ctrlPersistencia.llegirArxiu(arxiu);
        Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(string);
        try {
            repositori.afegir(kakuro);
            System.out.println("Kakuro afegit correctament");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void test_eliminar() {
        System.out.println("Introdueix el nom d'un arxiu que contingui el kakuro");
        String arxiu = scanner.next();
        String string = ctrlPersistencia.llegirArxiu(arxiu);
        Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(string);
        try {
            repositori.eliminar(kakuro);
            System.out.println("Kakuro eliminat correctament");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void test_consultar() {
        System.out.println("Introdueix el nom d'un arxiu on consultar el repositori");
        String arxiu = scanner.next();
        ArrayList<Kakuro> kakuros = repositori.consultar();
        StringBuilder builder = new StringBuilder();
        for (Kakuro kakuro : kakuros) {
            builder.append(ctrlStringKakuro.de_kakuro_a_string(kakuro)).append("\n\n");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 2);
        }
        ctrlPersistencia.escriureArxiu(arxiu, builder.toString());
        System.out.println("Repositori consultat cap a " + arxiu);
    }
}
