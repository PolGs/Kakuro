package Presentacio.KakuroViews.Play;



import Domini.*;

import javax.swing.*;
import java.awt.*;

public class KakuroGridPlay extends JPanel {

    Object[][] grid;

    public KakuroGridPlay(){



    }

    public void setGridSize(int n){grid = new Object[n][n];}

    public void setCell(Object cell, int i, int j){grid[i][j] = cell;}

    public Object getCell(int i, int j){return grid[i][j];}


}
