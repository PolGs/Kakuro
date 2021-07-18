package Domini.Drivers;

import Domini.*;

import java.util.Scanner;

public class UsuariDriver {

    public static void main(String[] args) {
        System.out.println("Introdueix el nom d'usuari:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        System.out.println("Introdueix la contrasenya:");
        String password = scanner.next();

        Usuari user = new Usuari(username, password);
        int num = -2;
        while(num != -1){
            System.out.println("Escriu el numero de la funcio a provar, -1 per sortir:");
            System.out.println("1. getUsername()");
            System.out.println("2. getPassword()");
            System.out.println("3. getSalt()");
            System.out.println("4. getPuntuacio()");
            System.out.println("5. setPuntuacio(int punts)");
            num = scanner.nextInt();
            switch (num){
                case 1:
                    System.out.println(user.getUsername());
                    break;
                case 2:
                    System.out.println(user.getPassword());
                    break;
                case 3:
                    System.out.println(user.getSalt());
                    break;
                case 4:
                    System.out.println(user.getPuntuacio());
                    break;
                case 5:
                    System.out.println("Introdueix nova puntuacio:");
                    int punts = scanner.nextInt();
                    user.setPuntuacio(punts);
                    System.out.println("Nova puntuacio: " + user.getPuntuacio());
                    break;
            }
        }
    }
}
