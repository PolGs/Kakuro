package Domini;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CjtUsuaris {
    private Vector<Usuari> cjtUsuaris;

    private Usuari cjtContains(Usuari user){
        for(Usuari userAux : cjtUsuaris){
            if(user.getUsername().equals(userAux.getUsername())) return userAux;
        }
        return null;
    }

    private void loadUsuaris(String toLoad){
        String[] lines = toLoad.split("\n");
        for(String linia : lines){
            if(linia.length() > 0) {
                if (cjtUsuaris == null) cjtUsuaris = new Vector<>();
                cjtUsuaris.add(new Usuari(linia));
            }
        }
    }

    Comparator<Usuari> comparaPunts = (o1, o2) -> {
        if(o1.getPuntuacio() > o2.getPuntuacio()) return 1;
        else if(o1.getPuntuacio() < o2.getPuntuacio()) return-1;
        return 0;
    };

    public ArrayList<ArrayList<String>> consultarRanking(){
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        cjtUsuaris.sort(comparaPunts);
        for (Usuari us : cjtUsuaris) {
            if(us.getUsername() != null) {
                System.out.println(us.getUsername() + " " + (us.getPuntuacio() * -1));
                ArrayList<String> aux = new ArrayList<>();
                aux.add(us.getUsername());
                aux.add(String.valueOf((us.getPuntuacio() * -1)));
                result.add(aux);
            }
        }
        return result;
    }

    public void resetRankingAll(){
        for (Usuari usuari : cjtUsuaris) {
            usuari.setPuntuacio(0);
        }
    }

    public CjtUsuaris(String toLoad){
        loadUsuaris(toLoad);
    }

    public boolean registrarUsuari(String username, String password){
        Usuari user = new Usuari(username,password);
        if(cjtUsuaris != null) {
            Usuari userAux = cjtContains(user);
            if(userAux != null) return false;
        }
        else cjtUsuaris = new Vector<>();
        cjtUsuaris.add(user);
        return true;
    }

    public Usuari iniciaSessio(String username, String password){
        Usuari user = cjtContains(new Usuari(username, password));
        if(user != null && password.length() > 0){
            String saltbytes = user.getSalt().substring(1,user.getSalt().length()-1);
            String[] llista = saltbytes.split(", ");
            byte[] salt = new byte[16];
            for(int i = 0; i < 16; ++i) salt[i] = Byte.parseByte(llista[i],10);
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-256");
                md.update(salt);
                byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
                if(Arrays.toString(hashedPassword).equals(user.getPassword()))return user;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void listUsuaris(){
        if(cjtUsuaris != null) {
            for (Usuari user : cjtUsuaris) {
                System.out.println(user.getUsername() + " " + (user.getPuntuacio()*-1));
            }
        }
    }

    public String getUsers(){
        String toDeliver = "";
        for(Usuari us : cjtUsuaris) {
            toDeliver += us.getUsername() + ":" + us.getPassword() + ":" + us.getSalt() + ":" + us.getPuntuacio() + "\n";
        }
        return toDeliver;
    }

    public void addPunts(int punts, String user, String pass){
        for(Usuari us : cjtUsuaris){
            Usuari aux = iniciaSessio(user,pass);
            if(aux != null && aux.getUsername().equals(us.getUsername()) && aux.getPassword().equals(us.getPassword())){
                us.addPuntuacio(punts);
            }
        }
    }

    public void setPunts(int punts, String user, String pass){
        for(Usuari us : cjtUsuaris){
            if(user.equals(us.getUsername())){
                us.setPuntuacio(punts);
            }
        }
    }

    public boolean isAdmin(String username, String pass){
        for(Usuari user : cjtUsuaris){
            if(user.getUsername().equals("admin") && username.equals("admin")){
                if(iniciaSessio(username,pass) != null)return true;
            }
        }
        return false;
    }
}
