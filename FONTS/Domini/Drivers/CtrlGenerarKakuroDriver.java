package Domini.Drivers;

import Domini.*;

import java.util.Scanner;

public class CtrlGenerarKakuroDriver {

    public static void test_generar_kakuro(){
        CtrlDomini ctrlDomini = new CtrlDomini();
        CtrlStringKakuro ctrlStringKakuro = ctrlDomini.getCtrlStringKakuro();
        CtrlGenerarKakuro genKakuro = new CtrlGenerarKakuro();
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdueix el num de files del kakuro:");
            int files = scanner.nextInt();
            System.out.println("Introdueix el num de columnes del kakuro:");
            int columnes = scanner.nextInt();
            System.out.println("Introdueix el num de caselles blanques:");
            int numBlanques = scanner.nextInt();
            Kakuro kakuro = genKakuro.generar(files,columnes,numBlanques);
            if (kakuro != null) {
                System.out.println(ctrlStringKakuro.de_kakuro_a_string(kakuro));
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver per testejar la classe CtrlGenerarKakuro");
        test_generar_kakuro();
    }
}
