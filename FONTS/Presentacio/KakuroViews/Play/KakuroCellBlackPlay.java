package Presentacio.KakuroViews.Play;

import Domini.Casella;
import Domini.CasellaNegra;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class KakuroCellBlackPlay extends JPanel{
    private int nPista;
    private int pistaVertical;
    private int pistaHoritzotal;
    private int i;
    private int j;

    public KakuroCellBlackPlay(int pistaV, int pistaH, int i_in, int j_in){
       pistaVertical=pistaV;
       pistaHoritzotal=pistaH;
       i=i_in;
       j=j_in;

        if(true) {
            setBackground(Color.decode("#fcf8ec"));
            JLabel labelPistaVertical = new JLabel();
            JLabel labelPistaHoritzontal = new JLabel();

            //Get and set values pistaV i pistaH to two labels
            String stringAux = String.valueOf(pistaV);
            if(pistaV != -1) labelPistaVertical.setText(stringAux);
            stringAux = String.valueOf(pistaH);
            if(pistaH!= -1) labelPistaHoritzontal.setText(stringAux);

            //SetLayout to gridbag to place labels where we want them
            setLayout(new GridLayout(2,2,10,10));

            //Add labels to Jpanel
            JLabel separatorLabel = new JLabel();
            separatorLabel.setText("  ");
            add(separatorLabel);
            add(labelPistaHoritzontal);
            add(labelPistaVertical);

            calcularNPista();
        }
    }





    @Override
    protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.setColor(Color.decode("#456268"));
            if(nPista == 0){
                g.fillRect(0,0, this.getWidth(),this.getHeight());
            }
            else if(nPista == 1){
                if(pistaHoritzotal == -1){
                    g.fillPolygon(new int[] {0,this.getSize().width ,this.getSize().width }, new int[] {0,0,this.getSize().height}, 3);
                }
                else g.fillPolygon(new int[] {0,0 ,this.getSize().width }, new int[] {0,this.getSize().height,this.getSize().height}, 3);

            }
            else if(nPista == 2){
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g2.draw(new Line2D.Float(0, 0, this.getSize().width, this.getSize().height));
            }
    }

    private void calcularNPista(){
            if(pistaVertical == -1 && pistaHoritzotal == -1) nPista = 0;
            else if(pistaHoritzotal != -1 && pistaVertical != -1) nPista = 2;
            else nPista = 1;
            }


}
