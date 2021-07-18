package Domini.Drivers;

import Domini.CtrlDomini;
import Domini.Usuari;

import java.util.ArrayList;
import java.util.Scanner;

public class CtrlUsuarisDriver {
    private static Scanner scanner = null;
    private static CtrlDomini ctrDomini;

    public static void testAfegirUsuari(){
        System.out.println("Introdueix nom d'usuari:");
        String username = scanner.next();
        System.out.println("Introdueix contrassenya:");
        String pass = scanner.next();
        System.out.println(ctrDomini.registrarUsuari(username,pass));
    }

    public static void testIniciaSessio(){
        System.out.println("Introdueix nom d'usuari:");
        String username = scanner.next();
        System.out.println("Introdueix contrassenya:");
        String pass = scanner.next();
        System.out.println(null != ctrDomini.iniciaSessio(username,pass));
    }

    private static void testSetPuntuacio() {
        System.out.println("Introdueix nom d'usuari:");
        String username = scanner.next();
        System.out.println("Introdueix contrassenya:");
        String pass = scanner.next();
        Usuari user = ctrDomini.iniciaSessio(username,pass);
        if(user == null) return;
        System.out.println("Introdueix punts:");
        int punts = scanner.nextInt();
        user.setPuntuacio(punts);
    }

    private static void testGetRanking(){
        ArrayList<ArrayList<String>> llista = ctrDomini.getRanking();
        for(ArrayList<String> line : llista){
            for(String us : line) {
                System.out.printf(us + " ");
            }
            System.out.println();
        }
    }

    private static void testResetRankingAll(){
        ctrDomini.resetRankingAll();
        testGetRanking();
    }

    public static void main(String[] args){
        scanner = new Scanner(System.in);
        ctrDomini = new CtrlDomini();
        int cas = 0;
        while(cas != -1){
            System.out.println("Selecciona una funcio:");
            System.out.println("1. AfegirUsuari");
            System.out.println("2. IniciaSessio");
            System.out.println("3. ListUsuaris");
            System.out.println("4. SetPuntuacio");
            System.out.println("5. GetRanking");
            System.out.println("6. ResetRanking");
            cas = scanner.nextInt();
            if(cas == 1) testAfegirUsuari();
            else if(cas == 2) testIniciaSessio();
            else if(cas == 3) ctrDomini.listUsuaris();
            else if(cas == 4) testSetPuntuacio();
            else if(cas == 5) testGetRanking();
            else if(cas == 6) testResetRankingAll();
        }
    }
}
