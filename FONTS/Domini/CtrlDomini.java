package Domini;

import Persistencia.CtrlPersistencia;

import java.util.ArrayList;

public class CtrlDomini {

    private final CjtUsuaris cjtUsuaris;
    private final CjtRecords cjtRecords;
    private final CtrlPersistencia ctrlPersistencia;
    private CtrlKakuro ctrlKakuro;
    private final CtrlStringKakuro ctrlStringKakuro;
    private final CtrlPartida ctrlPartida;
    private final CtrlRepositori ctrlRepositori;
    private final CtrlGenerarKakuro ctrlGenerarKakuro;
    private final Solver solver;

    public CtrlDomini() {
        ctrlPersistencia = new CtrlPersistencia();
        cjtUsuaris = new CjtUsuaris(ctrlPersistencia.llegirArxiu("usuaris.txt"));
        cjtRecords = new CjtRecords(ctrlPersistencia.llegirArxiu("records.txt"));
        ctrlKakuro = new CtrlKakuro();
        ctrlStringKakuro = new CtrlStringKakuro();
        ctrlPartida = new CtrlPartida(ctrlStringKakuro, ctrlPersistencia);
        ctrlRepositori = new CtrlRepositori(ctrlStringKakuro, ctrlPersistencia);
        ctrlGenerarKakuro = new CtrlGenerarKakuro();
        solver = new Solver();
    }

    public CtrlKakuro getCtrlKakuro() {
        return ctrlKakuro;
    }

    public void setCtrlKakuro(CtrlKakuro ctrl){this.ctrlKakuro = ctrl;}

    public CtrlStringKakuro getCtrlStringKakuro() {
        return ctrlStringKakuro;
    }

    public CtrlPartida getCtrlPartida() {
        return ctrlPartida;
    }

    public CtrlRepositori getCtrlRepositori() {
        return ctrlRepositori;
    }

    public CtrlGenerarKakuro getCtrlGenerarKakuro() {
        return ctrlGenerarKakuro;
    }

    private void storeUsuaris(){
        String toStore = cjtUsuaris.getUsers();
        ctrlPersistencia.escriureArxiu("usuaris.txt",toStore);
    }

    private void storeRecords(){
        String toStore = cjtRecords.getRecordsStore();
        ctrlPersistencia.escriureArxiu("records.txt",toStore);
    }

    public boolean registrarUsuari(String username, String password){
        boolean aux = cjtUsuaris.registrarUsuari(username,password);
        storeUsuaris();
        return aux;
    }

    public void listUsuaris(){
        cjtUsuaris.listUsuaris();
    }

    public Usuari iniciaSessio(String username, String password){
        return cjtUsuaris.iniciaSessio(username,password);
    }

    public ArrayList<ArrayList<String>> getRanking(){
        return cjtUsuaris.consultarRanking();
    }

    public void resetRankingAll(){
        cjtUsuaris.resetRankingAll();
        storeUsuaris();
    }

    public void resetUsers(){
        ctrlPersistencia.escriureArxiu("usuaris.txt","");
    }

    public void addPunts(int punts, String user, String pass){
        cjtUsuaris.addPunts(punts, user, pass);
        storeUsuaris();
    }

    public void setPunts(int punts, String user, String pass){
        cjtUsuaris.setPunts(punts,user,pass);
        storeUsuaris();
    }

    public boolean checkRecord(String kakuroID, String username, int points){
        if(cjtRecords.checkRecord(kakuroID,username,points)){
            storeRecords();
            return true;
        }
        return false;
    }

    public ArrayList<ArrayList<String>> getRecords(){
        return cjtRecords.getRecords();
    }

    public boolean isAdmin(String username, String pass){
        return cjtUsuaris.isAdmin(username,pass);
    }

    public CtrlKakuro loadKakuroFromFile(String filename) {
        String kakuroString = ctrlPersistencia.llegirArxiu(filename);
        Kakuro kakuro = ctrlStringKakuro.de_string_a_kakuro(kakuroString);
        ctrlKakuro.setKakuro(kakuro);
        return ctrlKakuro;
    }

    public int validateKakuro(CtrlKakuro ctrlKakuro) {
        return solver.validateKakuro(ctrlKakuro.getKakuro());
    }

    public void resetRecords(){
        cjtRecords.resetRecords();
        storeRecords();
    }
}
