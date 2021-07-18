package Presentacio;

import Domini.Usuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingView {
    private JButton goBackButton;
    private JList<String> scoreList;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JButton userButton;
    private JPanel RankingPanel;
    private JTextField searchField;
    private JButton resetYourScoreButton;
    private JButton resetAllScoreButton;
    private JButton resetSelectedScoreButton;
    private JLabel info;
    private JButton showRankingButton;
    private JButton showRecordsButton;
    private JPanel contenedor;
    private ArrayList<ArrayList<String>> scores;
    private ArrayList<ArrayList<String>> records;
    private ArrayList<ArrayList<String>> aux;
    private boolean sor;

    public RankingView() {
        scoreList.setModel(model);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) scoreList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        contenedor.setMinimumSize(new Dimension(300,300));
    }

    public JPanel getPanel(){return RankingPanel;}
    public JButton getGoBackButton(){return goBackButton;}
    public JButton getUserButton(){return userButton;}
    public JButton getResetYourScoreButton(){return resetYourScoreButton;}
    public JButton getResetAllScoreButton(){return resetAllScoreButton;}
    public JButton getResetSelectedScoreButton(){return resetSelectedScoreButton;}
    public JButton getShowRankingButton(){return showRankingButton;}
    public JButton getShowRecordsButton(){return showRecordsButton;}
    public JList<String> getScoreList(){return scoreList;}
    public boolean getSOR(){return sor;}
    public JTextField getSearchField(){return searchField;}
    public String getSearchFilter(){return searchField.getText();}
    public void removeAllElements(){model.removeAllElements();}
    public void setInfo(String s){info.setText(s);}
    public void swapLists(){sor = !sor;}
    public void setScores(ArrayList<ArrayList<String>> s){
        if(!sor) scores = new ArrayList<>(s);
        else records = new ArrayList<>(s);
        aux = new ArrayList<>(s);
        bindData();

        if(sor){
            for(ArrayList<String> p: records){
                System.out.println(p.get(2)+" "+p.get(0)+" "+p.get(1));
            }
        }
    }

    private Comparator<ArrayList<String>> comparator = new Comparator<ArrayList<String>>(){
        public int compare(ArrayList<String> s1, ArrayList<String> s2){
            int i = Integer.parseInt(s2.get(1))-Integer.parseInt(s1.get(1));
            if(i==0) return s1.get(0).compareTo(s2.get(0));
            return i;
        }
    };

    public void searchFilter(String filter){
        ArrayList<ArrayList<String>> aux2 = new ArrayList<>();
        ArrayList<ArrayList<String>> aux3 = new ArrayList<>();
        //System.out.println("El filtro es: "+filter);
        for (ArrayList<String> s : ((!sor)? scores : records)) {
            //System.out.println("iterando sobre "+s.get(0));
            if (!s.get(0).startsWith(filter)) {
                //System.out.println(s.get(0)+" no empieza con "+filter);
                if (aux.contains(s)) {
                    //System.out.println("aux contiene "+s.get(0)+ " y lo eliminamos");
                    aux2.add(s);
                }
            } else {
                //System.out.println(s.get(0)+" empieza con "+filter);
                if (!aux.contains(s)) {
                    //System.out.println("aux no contiene "+s.get(0)+ " y lo añadimos");
                    aux3.add(s);
                }
            }
        }

        aux.removeAll(aux2);
        aux.addAll(aux3);
        bindData();
    }


    //metodos privados
    private void bindData(){
        aux.sort(comparator);
        model.removeAllElements();
        if(!sor) {
            for (ArrayList<String> s : aux) {
                model.addElement(s.get(0) + " - " + s.get(1));
            }
        }else{
            for (ArrayList<String> s : aux) {
                if(s.get(0)!=null) model.addElement(s.get(2)+": "+s.get(0)+" - "+s.get(1));
            }
        }

    }




}
