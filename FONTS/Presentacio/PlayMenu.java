package Presentacio;

import javax.swing.*;
import java.awt.*;

public class PlayMenu {
    private JButton fastPlayButton;
    private JButton selectButton;
    private JButton generateButton;
    private JButton continueGameButton;
    private JButton goBackButton;
    private JPanel playmenu;
    private JButton createKakuroButton;
    private JButton readKakuroFromFileButton;

    public PlayMenu(){}

    public JPanel getPanel(){return playmenu;}
    public JButton getGoBackButton(){return goBackButton;}
    public JButton getFastPlayButton(){return fastPlayButton;}
    public JButton getSelectButton(){return selectButton;}
    public JButton getGenerateButton(){return generateButton;}
    public JButton getContinueGameButton(){return continueGameButton;}
    public JButton getCreateKakuroButton(){return createKakuroButton;}
    public JButton getReadKakuroFromFileButton(){return readKakuroFromFileButton;}

}
