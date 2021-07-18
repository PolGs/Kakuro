package Presentacio.KakuroViews.Create.SetHintValue;


import Domini.CasellaNegra;
import Presentacio.KakuroViews.Create.ChooseCellColor.KakuroCellColorSelect;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class KakuroGridCreateSetHintValues extends JPanel{//
    private KakuroCellSetHintValues[][] grid;
    public KakuroGridCreateSetHintValues(int size) {
        setLayout(new GridLayout(size, size));

    }
    public void setGrid(int i, int j, KakuroCellSetHintValues cell){
        grid[i][j] = cell;
    }
    public KakuroCellSetHintValues getCell(int i, int j){
        return grid[i][j];
    }
    public void createGrid(int size){
        grid = new KakuroCellSetHintValues[size][size];
    }
}