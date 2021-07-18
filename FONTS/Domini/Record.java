package Domini;

public class Record {
    private String kakuroID;
    private String username;
    private int punts;

    public void setPunts(int p){punts = p;}

    public void setUsername(String u){username = u;}

    public void setKakuroID(String ID){kakuroID = ID;}

    public String getKakuroID(){return kakuroID;}

    public String getUsername(){return username;}

    public int getPunts(){return punts;}

    public Record(String id, String u, int p){
        kakuroID = id; username = u; punts = p;
    }

    public Record(String loading){
        String[] linies = loading.split(":");
        if(linies.length == 3) {
            kakuroID = linies[0];
            username = linies[1];
            punts = Integer.parseInt(linies[2]);
        }
    }
}
