package Presentacio.KakuroViews.Create.SetHintValue;

import Domini.Casella;
import Domini.CasellaNegra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class KakuroCellSetHintValues extends JPanel{//JPanel que representa una casella negrea. Conte textfields per introduir les pistes al creau un kakuro
    //S'utilitza per a escollir els valors de les pistes
    private int nPista;
    private int pistaVertical;
    private int pistaHoritzontal;
    private int tipusCasella;//0 black 1 white
    private int i;
    private int j;
    JTextField textPistaVertical = new JTextField();
    JTextField textPistaHoritzontal = new JTextField();
    JTextArea  textAreaVertical;
    JTextArea textAreaHoritzontal;

    public KakuroCellSetHintValues(int i, int j, int tipusCasella, int pistaHoritzontal, int pistaVertical){
        this.pistaHoritzontal = pistaHoritzontal;
        this.pistaVertical = pistaVertical;
        this.i = i;
        this.j = j;
        this.tipusCasella = tipusCasella;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100,100));
        if(tipusCasella == 0) {
            setBackground(Color.decode("#fcf8ec"));
            textPistaHoritzontal.setVisible(false);
            textPistaVertical.setVisible(false);
            if(pistaHoritzontal != -1) textPistaHoritzontal.setVisible(true);
            if(pistaVertical != -1) textPistaVertical.setVisible(true);
            //Get and set values pistaV i pistaH to two labels
            //SetLayout to gridbag to place labels where we want them
            setLayout(new GridLayout(2,2,10,10));
            //Add labels to Jpanelç
            JLabel separatorLabel = new JLabel();
            separatorLabel.setText("  ");
            add(separatorLabel);
            add(textPistaHoritzontal);
            add(textPistaVertical);
            calcularNPista();
        }
        else{
            setBackground(Color.WHITE);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.decode("#456268"));
            if(tipusCasella == 0) {
                if (nPista == 0) {
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                } else if (nPista == 1) {
                    if (pistaHoritzontal == -1) {
                        g.fillPolygon(new int[]{0, this.getSize().width, this.getSize().width}, new int[]{0, 0, this.getSize().height}, 3);
                    } else
                        g.fillPolygon(new int[]{0, 0, this.getSize().width}, new int[]{0, this.getSize().height, this.getSize().height}, 3);

                } else if (nPista == 2) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(3));
                    g2.draw(new Line2D.Float(0, 0, this.getSize().width, this.getSize().height));
                }
            }

            else{
                System.out.println("drawing white");
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, 20, this.getHeight());
            }
    }

    private void calcularNPista(){
            if(pistaVertical == -1 && pistaHoritzontal == -1) nPista = 0;
            else if(pistaHoritzontal != -1 && pistaVertical != -1) nPista = 2;
            else nPista = 1;
            }
    public JTextField getFieldPistaVertical(){return textPistaVertical;}
    public JTextField getFieldPistaHoritzontal(){return textPistaHoritzontal;}
    public void updateValues(){
        try{
            pistaVertical = Integer.parseInt(textPistaHoritzontal.getText());
            pistaHoritzontal = Integer.parseInt(textAreaHoritzontal.getText());
        }
        catch (NumberFormatException exception){}
    }

    public int getPistaVertical(){return pistaVertical;}
    public int getPistaHoritzontal(){return pistaHoritzontal;}


}
