package Presentacio;

import Domini.CtrlDomini;
import Presentacio.KakuroViews.Play.KakuroGridPlay;

import javax.swing.*;
import java.awt.*;

public class PlayKakuroView extends JPanel {
    CtrlDomini ctrlDomini = new CtrlDomini();
    private JLabel titleLabel = new JLabel("Play Kauro");
    private JButton userButton = new JButton();
    private KakuroGridPlay kakuroView = new KakuroGridPlay();
    private JButton checkButton = new JButton();
    private JButton solveButton = new JButton();
    private JButton hintButton = new JButton();
    private JButton resetButton = new JButton();
    private JButton saveButton = new JButton();
    private JButton backButton = new JButton();
    private JLabel timeLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JLabel rankLabel = new JLabel();
    private JPanel labelPanel = new JPanel();
    private Font titleFont = new Font("Arial", Font.PLAIN, 30);
    private Font subtitleFont = new Font("Arial", Font.PLAIN, 20);

    PlayKakuroView(){
        initializePanel();
    }

    private void initializePanel(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gdc = new GridBagConstraints();
        //set up back button
        backButton.setText("Back");
        gdc.gridy = 0;
        gdc.gridx = 0;
        add(backButton,gdc);
        //--set up title label
        titleLabel.setFont(titleFont);
        gdc.gridy = 0;
        gdc.gridx = 1;
        gdc.weightx = 0.5;
        gdc.weighty = 0.1;
        add(titleLabel,gdc);

        //--add user button
        userButton.setText("User");
        gdc.gridy = 0;
        gdc.gridx = 3;
        gdc.weightx = 0.5;
        gdc.weighty = 0.1;
        add(userButton,gdc);




        //add kakuro
        gdc.gridy = 1;
        gdc.gridx = 0;
        gdc.weightx = 0.5;
        gdc.weighty = 0.5;
        gdc.gridwidth = 3;
        gdc.gridheight = 3;
        gdc.fill =GridBagConstraints.BOTH;
        add(kakuroView,gdc);

        //add labels time rank score
        labelPanel.setLayout(new GridLayout(3,1));
        gdc.gridwidth = 1;
        gdc.gridheight = 1;
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(subtitleFont);
        timeLabel.setText("00:00");


        gdc.gridy = 1;
        gdc.gridx = 3 ;
        gdc.weightx = 0.9;
        gdc.weighty = 0.2;
        gdc.fill = 1;
        add(timeLabel,gdc);

        //add JBUttons check,hint
        hintButton.setText("Hint");
        hintButton.setFont(subtitleFont);
        gdc.gridy = 2;
        gdc.gridx = 3;
        gdc.weightx = 0.9;
        gdc.weighty = 0.2;
        add(hintButton,gdc);
        checkButton.setText("Check");
        checkButton.setFont(subtitleFont);
        gdc.gridy = 3;
        gdc.gridx = 3;
        gdc.weightx = 0.9;
        gdc.weighty = 0.2;
        gdc.fill =GridBagConstraints.BOTH;
        add(checkButton,gdc);

        //addJbuttons restart solve save
        resetButton.setText("Start over");
        resetButton.setFont(subtitleFont);
        gdc.gridy = 4;
        gdc.gridx = 0;
        gdc.weightx = 0.9;
        gdc.weighty = 0.2;
        add(resetButton,gdc);

        solveButton.setText("Solve");
        solveButton.setFont(subtitleFont);
        gdc.gridy = 4;
        gdc.gridx = 1;
        add(solveButton,gdc);

        saveButton.setText("Save");
        saveButton.setFont(subtitleFont);
        gdc.gridy = 4;
        gdc.gridx = 2;
        add(saveButton,gdc);





        //adding 3 botom buttons: reset solve save
        //adding reset




    }

    KakuroGridPlay getKakuroPanel(){return kakuroView;}
    JLabel getTimeLabel(){return timeLabel;}
    JLabel getScoreLabel(){return scoreLabel;}
    JLabel getRankLabel(){return rankLabel;}
    JButton getUserButton(){return userButton;}
    JButton getCheckButton(){return checkButton;}
    JButton getSolveButton(){return solveButton;}
    JButton getHintButton(){return hintButton;}
    JButton getResetButton(){return resetButton;}
    JButton getSaveButton(){return saveButton;}
    JButton getBackButton(){return backButton;}

}
