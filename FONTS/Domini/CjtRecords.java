package Domini;

import java.util.ArrayList;
import java.util.Vector;

public class CjtRecords {

    private Vector<Record> cjtRecords;

    public CjtRecords(String toLoad){
        loadRecords(toLoad);
    }

    public boolean checkRecord(String ID, String user, int points){
        boolean found = false;
        for(Record rec : cjtRecords){
            if(rec.getKakuroID().equals(ID)){
                found = true;
                if(rec.getPunts() <= points){
                    rec.setPunts(points);
                    rec.setUsername(user);
                    return true;
                }
            }
        }
        if(found) return false;
        cjtRecords.add(new Record(ID,user,points));
        return true;
    }

    private void loadRecords(String toLoad){
        String[] records = toLoad.split("\n");
        for(String linia : records){
            if(cjtRecords == null) cjtRecords = new Vector<>();
            cjtRecords.add(new Record(linia));
        }
    }

    public String getRecordsStore(){
        String toDeliver = "";
        for(Record rec : cjtRecords){
            toDeliver += rec.getKakuroID() + ":" + rec.getUsername() + ":" + rec.getPunts() + "\n";
        }
        return toDeliver;
    }

    public ArrayList<ArrayList<String>> getRecords(){
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for(Record rec : cjtRecords){
            ArrayList<String> aux = new ArrayList<>();
            aux.add(rec.getUsername());
            aux.add(String.valueOf(rec.getPunts()));
            aux.add(rec.getKakuroID());
            list.add(aux);
        }
        return list;
    }

    public void resetRecords(){
        cjtRecords = new Vector<>();
    }
}
