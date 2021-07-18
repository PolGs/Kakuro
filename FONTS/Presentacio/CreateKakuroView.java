package Presentacio;


import Presentacio.KakuroViews.Create.ChooseCellColor.KakuroGridCreateChooseColor;
import Presentacio.KakuroViews.Create.SelectNHints.KakuroGridCreateSelectNHints;
import Presentacio.KakuroViews.Create.SetHintValue.KakuroGridCreateSetHintValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateKakuroView extends JPanel{
    int size=2;
    JTextField sizeTextField = new JTextField();

    JPanel viewsCardPanel = new JPanel();
    CardLayout viewsLayout = new CardLayout();
    JButton nextButton = new JButton();
    int screen=0;
    JPanel selectSizePanel = new JPanel();
    KakuroGridCreateSelectNHints kakuroHintSelect = new KakuroGridCreateSelectNHints(1);
    KakuroGridCreateChooseColor kakuroColorSelect = new KakuroGridCreateChooseColor(1);
    KakuroGridCreateSetHintValues kakuroHintValueSelect = new KakuroGridCreateSetHintValues(1);

    public CreateKakuroView() {

        //Set Class Layout as GridBag
        GridBagLayout parentLayout = new GridBagLayout();
        GridBagConstraints gridContraints = new GridBagConstraints();
        setLayout(parentLayout);


        //-----Add title to parent Layout------
        Label crearKakuroLabel = new Label("Create Kakuro");
        crearKakuroLabel.setFont(new Font("Arial",Font.PLAIN,25));
        gridContraints.fill = GridBagConstraints.HORIZONTAL;
        gridContraints.weightx = 0.5;
        gridContraints.weighty = 0.05;
        gridContraints.gridx = 0;
        gridContraints.gridy = 0;
        add(crearKakuroLabel,gridContraints);
        //--------Title layout added(Create Kakuro)------



        //-------Add Panel with all CreateViews inside set as cards-----
        viewsCardPanel.setLayout(viewsLayout);
        viewsCardPanel.setBackground(Color.BLUE);
        gridContraints.fill = GridBagConstraints.BOTH;
        gridContraints.weightx = 1;
        gridContraints.weighty = 1;
        gridContraints.gridx = 0;
        gridContraints.gridy = 1;
        add(viewsCardPanel,gridContraints);
        //------Panel with create views added------



        //------Add nextButton This listener adds the needed jpanel------
        nextButton.setText("Next");
        gridContraints.fill = GridBagConstraints.BOTH;
        gridContraints.weightx = 1;
        gridContraints.weighty = 0.05;
        gridContraints.gridx = 0;
        gridContraints.gridy = 2;
        add(nextButton,gridContraints);
        //-------nextButtonAdded---------




        //------Now Adding MAinscreen JPanels to the Card Layout rest of panels are added when next button is clicked-------


        //First:Select size

        selectSizePanel.setPreferredSize(new Dimension(800,600));
        selectSizePanel.setLayout(new GridBagLayout());
        JLabel selectSizeLabel = new JLabel("Select Kakuro size:");//Label "Select Kakuro size:"
        selectSizeLabel.setFont(new Font("Arial",Font.PLAIN,20));
        gridContraints.gridy=0;
        gridContraints.gridx=0;
        gridContraints.fill = GridBagConstraints.HORIZONTAL;
        gridContraints.weightx = 1;
        gridContraints.weighty=1;
        selectSizePanel.add(selectSizeLabel,gridContraints);
        //Field to input Kakuro size
        sizeTextField.setText("10");
        gridContraints.gridy=0;
        gridContraints.gridx=1;
        gridContraints.fill = GridBagConstraints.HORIZONTAL;
        gridContraints.weightx = 1;
        gridContraints.weighty=1;
        selectSizePanel.add(sizeTextField,gridContraints);
        viewsCardPanel.add(selectSizePanel);



        //Second:Select Celll Color



    }
    public int getSizeV(){return size;}
    public int getScreenV(){return screen;}
    public JTextField getSizeTextField(){return sizeTextField;}
    public JButton getnextButton(){return nextButton;}
    public KakuroGridCreateChooseColor getChooseColorGrid(){return kakuroColorSelect;}
    public KakuroGridCreateSelectNHints getKakuroHintSelect() { return kakuroHintSelect;}
    public KakuroGridCreateSetHintValues getKakuroHintValueSelect() {
        return kakuroHintValueSelect;
    }
    public JPanel getViewsCardPanel(){return viewsCardPanel;}
    public CardLayout getViewsLayout() {return viewsLayout;}

    public void setScreen(int screen){this.screen = screen;}
    public  void setSizeV(int n){size = n;}
    public void createGridColorSelect(int size){ kakuroColorSelect = new KakuroGridCreateChooseColor(size);}
    public void createGridSelectNHints(int size){kakuroHintSelect = new KakuroGridCreateSelectNHints(size);}
    public void createGridSetHintValue(int size){kakuroHintValueSelect = new KakuroGridCreateSetHintValues(size);}

}

