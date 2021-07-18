package Presentacio.KakuroViews.Create.SelectNHints;

import Domini.CasellaNegra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class KakuroCellSelectNHintsParent extends JButton {//Panel on al fer click sobre aquesta casella cambia el tipus de pista.
    //S'utilitza per seleccionar el nombre de pistes en una casella
    int tipusPista = 0;//0 no pista 1 pistavert 2 pistahor 3 dues pistes


    public KakuroCellSelectNHintsParent(){

    }


    public void setTipusPista(int tipusPista) {
        this.tipusPista = tipusPista;
    }
    public void nextTipusPista(){
        if(tipusPista==3) tipusPista=0;
        else ++tipusPista;
    }
    public int getTipusPista(){return tipusPista;}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#456268"));
        if(tipusPista == 0){//No hi ha pista
            g.fillRect(0,0, this.getWidth(),this.getHeight());
        }
        if(tipusPista == 1){
            g.fillPolygon(new int[] {0,this.getSize().width ,this.getSize().width }, new int[] {0,0,this.getSize().height}, 3);
        }
        if(tipusPista == 2){
            g.fillPolygon(new int[] {0,0 ,this.getSize().width }, new int[] {0,this.getSize().height,this.getSize().height}, 3);
        }
        if(tipusPista == 3){
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.draw(new Line2D.Float(0, 0, this.getSize().width, this.getSize().height));
        }
    }
}
