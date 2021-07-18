package Presentacio.KakuroViews.Visualize;

import Domini.Casella;
import Domini.CasellaNegra;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class KakuroCellBlackVisualizer extends JPanel{
    private int nPista;
    private int pistaVertical;
    private int pistaHoritzontal;

    public KakuroCellBlackVisualizer(int pistaVert, int pistaHor){
            this.pistaHoritzontal = pistaHor;
            this.pistaVertical = pistaVert;
            setBackground(Color.decode("#fcf8ec"));
            JLabel labelPistaVertical = new JLabel();
            JLabel labelPistaHoritzontal = new JLabel();

            //Get and set values pistaV i pistaH to two labels

             String stringAux = String.valueOf(pistaVert);
            if(pistaVert != -1) labelPistaVertical.setText(stringAux);
            stringAux = String.valueOf(pistaHor);
            if(pistaHor != -1) labelPistaHoritzontal.setText(stringAux);

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


    @Override
    protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.setColor(Color.decode("#456268"));
            if(nPista == 0){
                g.fillRect(0,0, this.getWidth(),this.getHeight());
            }
            else if(nPista == 1){
                if(pistaHoritzontal == -1){
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
            if(this.pistaVertical == -1 && pistaHoritzontal == -1) nPista = 0;
            else if(pistaHoritzontal != -1 && pistaVertical != -1) nPista = 2;
            else nPista = 1;
            }


}
