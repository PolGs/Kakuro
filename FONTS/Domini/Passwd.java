package Domini;

public class Passwd {
    public Passwd(String salt1, String passwd){
        salt = salt1; hash = passwd;
    }
    private String salt;
    private String hash;

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash(){
        return hash;
    }
}
