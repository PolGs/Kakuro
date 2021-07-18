package Presentacio.KakuroViews.Visualize;



import Domini.*;

import javax.swing.*;
import java.awt.*;

public class KakuroGridVisualizer extends JPanel {
    public KakuroGridVisualizer(){
        /*String kak="10,10\n" +
                "*,C13,C8,C45,*,*,C11,C8,C10,*\n" +
                "F15,?,?,?,*,F16,?,?,?,*\n" +
                "F17,?,?,?,C37,C35F13,?,?,?,*\n" +
                "*,*,F9,?,?,?,*,*,*,*\n" +
                "*,C21,C30F17,?,?,?,*,*,C19,C23\n" +
                "F22,?,?,?,?,?,C16,C18F11,?,?\n" +
                "F45,?,?,?,?,?,?,?,?,?\n" +
                "F45,?,?,?,?,?,?,?,?,?\n" +
                "F45,?,?,?,?,?,?,?,?,?\n" +
                "F39,?,?,?,?,?,?,?,?,*";
        //CtrlDomini ctrl = new CtrlDomini();
        //Kakuro k = ctrl.de_string_a_kakuro(kak);
        Casella[][] matCasellles = k.consultar_caselles();
        int size = matCasellles.length;
        setLayout(new GridLayout(size,size));


        for(int i=0; i<size; ++i){
            for(int j=0;j<size;++j){
                KakuroCellWhiteVisualizer cellW = new KakuroCellWhiteVisualizer(matCasellles[i][j]);
                KakuroCellBlackVisualizer cellB = new KakuroCellBlackVisualizer(matCasellles[i][j]);
                if(matCasellles[i][j] instanceof CasellaBlanca ) add(cellW);
                else add(cellB);
                cellW.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellB.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
*/
    }
    /*public void setKakuro(Kakuro k){
        removeAll();
        Casella[][] matCasellles = k.consultar_caselles();
        int size = matCasellles.length;
        setLayout(new GridLayout(size,size));


        for(int i=0; i<size; ++i){
            for(int j=0;j<size;++j){
                KakuroCellWhiteVisualizer cellW = new KakuroCellWhiteVisualizer(matCasellles[i][j]);
                KakuroCellBlackVisualizer cellB = new KakuroCellBlackVisualizer(matCasellles[i][j]);
                if(matCasellles[i][j] instanceof CasellaBlanca ) add(cellW);
                else add(cellB);
                cellW.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellB.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }




    }*/
}
