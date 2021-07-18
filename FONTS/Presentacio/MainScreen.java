package Presentacio;

import javax.swing.*;

public class MainScreen {
    private JButton PLAYButton;
    private JPanel mainPanel;
    private JButton RANKINGButton;
    private JButton REPOSITORYButton;
    private JButton USERButton;

    public MainScreen(){}
    public JPanel getPanel(){return mainPanel;}
    public JButton getRANKINGButton(){return RANKINGButton;}
    public JButton getPLAYButton(){return PLAYButton;}
    public JButton getMYKAKUROSButton(){return REPOSITORYButton;}
    public JButton getUSERButton(){return USERButton;}
}