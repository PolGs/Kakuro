package Domini.Drivers;

import Domini.CtrlDomini;

import java.util.ArrayList;
import java.util.Scanner;

public class CtrlDominiDriver {
    private static Scanner scanner = null;
    private static CtrlDomini ctrDomini;

    public static void checkRegistrarUsuari(){
        System.out.println("Introdueix nom d'usuari:");
        String username = scanner.next();
        System.out.println("Introdueix contrassenya:");
        String pass = scanner.next();
        System.out.println(ctrDomini.registrarUsuari(username,pass));
    }

    public static void checkIniciaSessio(){
        System.out.println("Introdueix nom d'usuari:");
        String username = scanner.next();
        System.out.println("Introdueix contrassenya:");
        String pass = scanner.next();
        System.out.println(ctrDomini.iniciaSessio(username,pass));
    }

    public static void checkListUsuaris(){
        ctrDomini.listUsuaris();
    }

    private static void checkGetRanking(){
        ArrayList<ArrayList<String>> llista = ctrDomini.getRanking();
        for(ArrayList<String> line : llista){
            for(String us : line) {
                System.out.printf(us + " ");
            }
            System.out.println();
        }
    }

    private static void checkResetRankingAll(){
        ctrDomini.resetRankingAll();
        checkGetRanking();
    }

    private static void checkResetUsuaris(){
        ctrDomini.resetUsers();
        checkListUsuaris();
    }

    private static void checkAddPunts(){
        ctrDomini.listUsuaris();
        System.out.println("Introdueix l'usuari a afegir el punts:");
        String user = scanner.next();
        System.out.println("Introdueix contrassenya per l'usuari:");
        String pass = scanner.next();
        System.out.println("Introdueix els punts a afegir:");
        int punts = scanner.nextInt();
        ctrDomini.addPunts(punts,user,pass);
        ctrDomini.listUsuaris();
    }

    private static void checkCheckRecord(){
        ArrayList<ArrayList<String>> llista = ctrDomini.getRecords();
        for(ArrayList<String> line : llista){
            for(String user : line) {
                System.out.print(user + " ");
            }
            System.out.println();
        }
        System.out.println("Introdueix l'usuari a afegir el punts:");
        String username = scanner.next();
        System.out.println("Introdueix l'ID del kakuro:");
        String ID = scanner.next();
        System.out.println("Introdueix els punts:");
        int points = scanner.nextInt();
        System.out.println(ctrDomini.checkRecord(ID,username,points));
    }

    private static void checkIsAdmin(){
        System.out.println("Introdueix l'usuari a comprovar:");
        String username = scanner.next();
        System.out.println("Introdueix password:");
        String pass = scanner.next();
        System.out.println(ctrDomini.isAdmin(username,pass));
    }

    public static void main(String[] args){
        ctrDomini = new CtrlDomini();
        System.out.println("Trieu la funcio a provar:");
        System.out.println("1. RegistrarUsuari");
        System.out.println("2. ListUsuaris");
        System.out.println("3. IniciaSessio");
        System.out.println("4. GetRanking");
        System.out.println("5. ResetRankingAll");
        System.out.println("6. ResetUsuaris");
        System.out.println("7. AddPunts");
        System.out.println("8. CheckRecord");
        System.out.println("9. IsAdmin");
        scanner = new Scanner(System.in);
        int cas = scanner.nextInt();
        while(cas != -1) {
            switch (cas) {
                case 1:
                    checkRegistrarUsuari();
                    break;
                case 2:
                    checkListUsuaris();
                    break;
                case 3:
                    checkIniciaSessio();
                    break;
                case 4:
                    checkGetRanking();
                    break;
                case 5:
                    checkResetRankingAll();
                    break;
                case 6:
                    checkResetUsuaris();
                    break;
                case 7:
                    checkAddPunts();
                    break;
                case 8:
                    checkCheckRecord();
                    break;
                case 9:
                    checkIsAdmin();
                    break;
            }
            System.out.println("Trieu la funcio a provar:");
            System.out.println("1. RegistrarUsuari");
            System.out.println("2. ListUsuaris");
            System.out.println("3. IniciaSessio");
            System.out.println("4. GetRanking");
            System.out.println("5. ResetRankingAll");
            System.out.println("6. ResetUsuaris");
            System.out.println("7. AddPunts");
            System.out.println("8. CheckRecord");
            System.out.println("9. IsAdmin");
            cas = scanner.nextInt();
        }
    }
}