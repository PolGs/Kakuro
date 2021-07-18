package Presentacio.KakuroViews.Create.ChooseCellColor;

import javax.swing.*;
import java.awt.*;

public class KakuroGridCreateChooseColor extends JPanel{//

    KakuroCellColorSelect[][] grid;
    JPanel kakuroSelectColor = new JPanel();

    public KakuroGridCreateChooseColor(int size) {
        grid = new KakuroCellColorSelect[size][size];
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        //------Add explicative label-.-----
        Label selectCellColorLabel = new Label("Select the color of each cell on the following Kakuro");
        selectCellColorLabel.setFont(new Font("Arial",Font.PLAIN,16));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(selectCellColorLabel,gbc);
        kakuroSelectColor.setLayout(new GridLayout(size, 10));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(kakuroSelectColor,gbc);


    }

    public JPanel getKakuroSelectColor(){return kakuroSelectColor;}
    public KakuroCellColorSelect[][] getGridV(){return grid;}
    public void createKakuroGridColorSelect(int size){
        grid = new KakuroCellColorSelect[size][size];
    }
}