package Presentacio;

import Domini.CjtUsuaris;
import Domini.Usuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UsuariView {
    private JPanel TopPanel; //sobra
    private JPanel userPanel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton logInButton;
    private JButton signUpButton;
    private JLabel info;
    private JButton logOutButton;
    private JButton goBackButton;

    public UsuariView() {}

    public JPanel getPanel(){return TopPanel;}
    public JButton getLogInButton(){return logInButton;}
    public JButton getSignUpButton(){return signUpButton;}
    public JButton getLogOutButton(){return logOutButton;}
    public JButton getGoBackButton(){return goBackButton;}
    public String getUsername(){return usernameField.getText();}
    public String getPassword(){return new String(passwordField.getPassword());}
    public void setInfo(String s){info.setText(s);}

}
