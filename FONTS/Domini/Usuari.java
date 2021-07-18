package Domini;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Usuari {
    private final String nom;
    private Passwd passwd;
    private int puntuacio;
    public Usuari(String nom1, String password) {
        nom = nom1;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            passwd = new Passwd(Arrays.toString(salt),Arrays.toString(hashedPassword));
            puntuacio = 0;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Usuari(String loadedUser){
        String[] llista = loadedUser.split(":");
        nom = llista[0];
        passwd = new Passwd(llista[2],llista[1]);
        puntuacio = Integer.parseInt(llista[3]);

    }

    public String getUsername(){return nom;}
    public String getPassword(){return passwd.getHash();}
    public String getSalt(){return passwd.getSalt();}
    public int getPuntuacio(){return puntuacio;}
    public void setPuntuacio(int punts) {puntuacio = punts*-1;}
    public void addPuntuacio(int punts) {puntuacio += punts*-1;}
}
