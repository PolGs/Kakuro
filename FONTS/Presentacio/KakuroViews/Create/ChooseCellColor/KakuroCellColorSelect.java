package Presentacio.KakuroViews.Create.ChooseCellColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KakuroCellColorSelect extends JButton {
    //S'utilitza per seeleccionar el color d'una casella --> despre es pasa a selecionar nombre de pistes
    int color = 0;//0 black 1 white
    int i,j;

    public KakuroCellColorSelect(){
        setBackground(Color.black);
    }

    public void setBackgroundColor(int n){
        color = n;
        if(color == 0) setBackground(Color.BLACK);
        else setBackground(Color.WHITE);
    }
    public void changeBackgroundColor(){
        if(color == 0) color = 1;
        else color = 0;
        updateBackground();
    }

    private void updateBackground(){
        if(color == 0) setBackgroundColor(0);
        else setBackgroundColor(1);
    }
    public int getColor(){return color;}

    public void setI(int i){this.i = i;}
    public void setJ(int j){this.j = j;}
    public int getI(){return this.i;}
    public int getJ(){return this.j;}
}
