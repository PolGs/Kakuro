package Presentacio.KakuroViews.Create.SelectNHints;


import Presentacio.KakuroViews.Create.ChooseCellColor.KakuroCellColorSelect;

import javax.swing.*;
import java.awt.*;

public class KakuroGridCreateSelectNHints extends JPanel{//

    KakuroCellSelectNHintsParent[][] grid;

    public KakuroGridCreateSelectNHints(int size) {

        setLayout(new GridLayout(size, size));
        grid = new KakuroCellSelectNHintsParent[size][size];


    }

    public KakuroCellSelectNHintsParent getCell(int i, int j){
        return grid[i][j];
    }

}