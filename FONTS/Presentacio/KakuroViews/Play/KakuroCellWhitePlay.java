package Presentacio.KakuroViews.Play;

import Domini.Casella;
import Domini.CasellaBlanca;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KakuroCellWhitePlay extends JButton{
    public int size = 100;
    private String lastKey = "";
    private int valor;
    private int i;
    private int j;

    public KakuroCellWhitePlay(int v, int i_in, int j_in) {

        valor = v;
        i = i_in;
        j=j_in;
        if (true) {
            //set preferred size : 100x100
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
            if (true) {//Pintar casellablanca
                setBackground(Color.WHITE);


                setText("");
                setFont(new Font("Arial", Font.PLAIN, 18));
                //fons del button blanc no border layout per que ompli tot el panel
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder());
                //setLayout(new BorderLayout());

                //acton listener de la casella per si es premuda

            }

            //Key listen studff


        }
    }

    public void setValue(int value){
        valor = value;
        if(value == -1) setText("");
        else setText(String.valueOf(value));
    }

}
