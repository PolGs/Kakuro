package Presentacio;

import javax.swing.*;
import java.awt.*;

public class RepositoriView {
    private JPanel RepositoriPanel;
    private JScrollPane scrollPanel;
    private JPanel panel;
    private JButton goBackButton;
    private JButton userButton;
    private JButton createNewKakuroButton;
    private JButton generateNewKakuroButton;

    public RepositoriView(){
        panel.setLayout(new GridBagLayout());
    }

    public void reset(){panel.removeAll();}
    public void addPanel(JPanel p){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy=panel.getComponentCount();
        panel.add(p,c);
    }
    public int getComponentIndex(JPanel p){return panel.getComponentZOrder(p);}
    public JPanel getPanel(){return RepositoriPanel;}
    public JButton getUserButton(){return userButton;}
    public JButton getCreateNewKakuroButton(){return createNewKakuroButton;}
    public JButton getGenerateNewKakuroButton(){return generateNewKakuroButton;}
    public JButton getGoBackButton(){return goBackButton;}


}
