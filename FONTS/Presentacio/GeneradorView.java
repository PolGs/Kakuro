package Presentacio;

import Domini.*;
import Presentacio.KakuroViews.Visualize.KakuroGridVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneradorView {

    private JPanel GeneratorPanel;
    private JTextField rows;
    private JTextField numwcells;
    private JButton saveButton;
    private JButton playButton;
    private JButton generateButton;
    private JTextField columns;
    private JLabel info;
    private JButton goBackButton;
    private JButton userButton;
    private int value;
    private JPanel kakuroPrev;


    //creadora
    public GeneradorView(){
        kakuroPrev.setLayout(new GridLayout());
        kakuroPrev.setPreferredSize(new Dimension(400,400));
    }

    //metodos publicos
    public JPanel getPanel(){return GeneratorPanel;}
    public JPanel getKakuroPrev(){return kakuroPrev;}
    public void setKakuroGrid(KakuroGridVisualizer k){kakuroPrev.removeAll(); kakuroPrev.add(k);}
    public JButton getSaveButton(){return saveButton;}
    public JButton getPlayButton(){return playButton;}
    public JButton getGenerateButton(){return generateButton;}
    public JButton getGoBackButton(){return goBackButton;}
    public JButton getUserButton(){return userButton;}
    public void setInfo(String s){info.setText(s);}
    public int getRows(){if(checkInteger(rows.getText())) return value; return -1;}
    public int getColumns(){if(checkInteger(columns.getText())) return value; return -1;}
    public int getNCells(){if(checkInteger(numwcells.getText())) return value; return -1;}

    //metodos privados
    private boolean checkInteger(String s){
        try{
            value = Integer.parseInt(s);
            return true;
        }catch (NumberFormatException exception){
            return false;
        }
    }
    //private void createUIComponents(){kakuroGrid = new KakuroGridVisualizer();}

    /*
    public void iniciarPanel(JFrame frame){
        frame.setMinimumSize(new Dimension(450,250));
        kakuroPrev.setMaximumSize(new Dimension(400,400));
        kakuroGrid1.setMaximumSize(new Dimension(400,400));
        frame.setContentPane(GeneratorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void setCtrlPresentacio(CtrlPresentacio ctrlp){
        cp = ctrlp;
    }*/
}
