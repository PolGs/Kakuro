package Domini.Drivers;

import Domini.*;
import Persistencia.CtrlPersistencia;

import java.util.Scanner;

public class CtrlRepositoriDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CtrlDomini ctrlDomini = new CtrlDomini();
        CtrlPersistencia ctrlPersistencia = new CtrlPersistencia();
        CtrlStringKakuro ctrlStringKakuro = ctrlDomini.getCtrlStringKakuro();
        CtrlRepositori ctrlRepositori = ctrlDomini.getCtrlRepositori();
        boolean loop = true;
        while (loop) {
            System.out.println("Escriu el numero de la funcio a provar, -1 per sortir:");
            System.out.println("1. addKakuro(CtrlKakuro ctrlKakuro)");
            System.out.println("2. getKakuroInfoList()");
            System.out.println("3. removeAt(int index)");
            System.out.println("4. empty()");
            int num = scanner.nextInt();
            switch (num) {
                case 1: {
                    System.out.println("Introdueix el nom d'un arxiu que contingui el kakuro");
                    String arxiu = scanner.next();
                    String string = ctrlPersistencia.llegirArxiu(arxiu);
                    Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(string);
                    CtrlKakuro ctrlKakuro = new CtrlKakuro();
                    ctrlKakuro.setKakuro(kakuro);
                    try {
                        ctrlRepositori.addKakuro(ctrlKakuro);
                        System.out.println("Kakuro afegit correctament");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    String[] kakuroInfos = ctrlRepositori.getKakuroInfoList();
                    System.out.println(kakuroInfos.length + " results");
                    for (String kakuroInfo : kakuroInfos) {
                        System.out.println(kakuroInfo);
                    }
                    break;
                }
                case 3: {
                    System.out.println("Introdueix l'index del kakuro a eliminar");
                    int index = scanner.nextInt();
                    ctrlRepositori.removeAt(index);
                    System.out.println("Eliminar correctament");
                    break;
                }
                case 4: {
                    ctrlRepositori.empty();
                    System.out.println("Buidat correctament");
                    break;
                }
                case -1: {
                    loop = false;
                    break;
                }
            }
        }
    }
}
