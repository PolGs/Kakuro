package Presentacio;


import Domini.*;
import Presentacio.KakuroViews.Create.ChooseCellColor.KakuroCellColorSelect;
import Presentacio.KakuroViews.Create.SelectNHints.KakuroCellSelectNHintsParent;
import Presentacio.KakuroViews.Create.SetHintValue.KakuroCellSetHintValues;
import Presentacio.KakuroViews.Play.KakuroCellBlackPlay;
import Presentacio.KakuroViews.Play.KakuroCellWhitePlay;
import Presentacio.KakuroViews.Visualize.KakuroCellBlackVisualizer;
import Presentacio.KakuroViews.Visualize.KakuroCellWhiteVisualizer;
import Presentacio.KakuroViews.Visualize.KakuroGridVisualizer;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.TimeUnit.SECONDS;


public class CtrlPresentacio {
    private MainScreen mp = new MainScreen();
    private UsuariView up = new UsuariView();
    private GeneradorView gp = new GeneradorView();
    private RankingView ra = new RankingView();
    private RepositoriView rp = new RepositoriView();
    private PlayMenu pm = new PlayMenu();
    private PlayKakuroView pk = new PlayKakuroView();
    private CreateKakuroView ck = new CreateKakuroView();
    private String actualUser=null;
    private String password=null;
    private JFrame frame;
    private JPanel padre;
    private CardLayout card;
    private CtrlDomini ctrlDomini;
    private boolean partidaencurso=false;
    private int csize=1;

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    //constructora: inicialitza el card layout que engloba les vistes
    public CtrlPresentacio(){
        /*gp.setCtrlPresentacio(this);
        up.setCtrlPresentacio(this);
        rp.setCtrlPresentacio(this);*/
        ctrlDomini = new CtrlDomini();
        frame = new JFrame();
        padre = new JPanel();
        card = new CardLayout();
        padre.setLayout(card);
        frame.setContentPane(padre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600,600));
        setListeners();
        setPlayListeners();
    }

    //inicialitza les vistes, que comença per la main screen
    public void start(){
        frame.setVisible(true);
        padre.add(mp.getPanel());
        frame.setSize(new Dimension(800,600));
    }

    //aplica tots el listeners de tots els botons de les vistes
    private void setListeners(){

        //torna enrere
        ActionListener goBackListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.previous(padre);
                padre.remove(padre.getComponentCount()-1);
                if(partidaencurso) ctrlDomini.getCtrlPartida().continuePartida();
            }
        };
        up.getGoBackButton().addActionListener(goBackListener);
        gp.getGoBackButton().addActionListener(goBackListener);
        ra.getGoBackButton().addActionListener(goBackListener);
        rp.getGoBackButton().addActionListener(goBackListener);
        pm.getGoBackButton().addActionListener(goBackListener);

        //mostra el panel d'usuari
        ActionListener userPanelListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                padre.add(up.getPanel());
                card.next(padre);
            }
        };
        mp.getUSERButton().addActionListener(userPanelListener);
        gp.getUserButton().addActionListener(userPanelListener);
        ra.getUserButton().addActionListener(userPanelListener);
        rp.getUserButton().addActionListener(userPanelListener);


        //MainScreen Listeners:

        //
        mp.getPLAYButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(pm.getPanel());
                card.next(padre);
            }
        });

        mp.getRANKINGButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(ra.getPanel());
                ra.removeAllElements();
                if(!ra.getSOR()) ra.setScores(ctrlDomini.getRanking());
                else ra.setScores(ctrlDomini.getRecords());
                card.next(padre);
            }
        });

        mp.getMYKAKUROSButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(rp.getPanel());
                card.next(padre);
                rp.reset();
                setRepositori();
                refreshFrame();
            }
        });

        //PlayMenu Listeners:
        pm.getGenerateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(gp.getPanel());
                card.next(padre);
            }
        });

        pm.getSelectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(rp.getPanel());
                card.next(padre);
                rp.reset();
                setRepositori();
                refreshFrame();
            }
        });

        pm.getFastPlayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlKakuro ctrlKakuro = ctrlDomini.getCtrlKakuro();
                ctrlKakuro.setKakuro(ctrlDomini.getCtrlGenerarKakuro().generar(10,10,30));
                pk = new PlayKakuroView();
                setPlayListeners();
                startKakuroPlay();
                updatePlayLabels();
                padre.add(pk);
                card.next(padre);
                partidaencurso = true;
            }
        });

        pm.getCreateKakuroButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ck = new CreateKakuroView();
                padre.add(ck);
                card.next(padre);
                setCreatorListeners();
            }
        });

        pm.getContinueGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actualUser==null) JOptionPane.showMessageDialog(null, "You need to login first");
                else if(!ctrlDomini.getCtrlPartida().hasPartida(actualUser)) JOptionPane.showMessageDialog(null,"No game found");
                else {
                    try {
                        ctrlDomini.getCtrlPartida().loadPartida(actualUser);
                        pk = new PlayKakuroView();
                        setPlayListeners();
                        startKakuroPlay();
                        updatePlayLabels();
                        padre.add(pk);
                        card.next(padre);
                        partidaencurso = true;
                    } catch (Exception exception) {
                        System.out.println("Esto nunca va a pasar :)");
                    }
                }
            }
        });

        pm.getReadKakuroFromFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //UsuariView Listeners:

        up.getLogInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = up.getUsername();
                String pass = up.getPassword();
                System.out.println(pass);
                if(actualUser==null && up.getUsername().length()!=0 && up.getPassword().length()!=0){
                    //actualUser = ctrlDomini.iniciaSessio(user, pass).getUsername();
                    //if(actualUser==null) up.setInfo("Login error, please try again");
                    if(ctrlDomini.iniciaSessio(user, pass)!=null){
                        actualUser = user;
                        password = pass;
                        //gracias por no dejarnos tener instancias de la capa de domini aquí
                        up.setInfo("Login successful");
                    }
                    else up.setInfo("Login error, please try again");
                }
                else if(actualUser!=null) up.setInfo("The user "+actualUser+" is actually logged. If it is not you, try to logout first");
                else up.setInfo("Login error, please try again");
            }
        });

        up.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actualUser!=null) up.setInfo("The user "+actualUser+" is actually logged. If it is not you, try to logout first");
                else{
                    String name = up.getUsername();
                    String pass = up.getPassword();
                    if("".equals(name)) up.setInfo("Please introduce a username");
                    else if("".equals(pass)) up.setInfo("Please introduce a valid password");
                    else if(!ctrlDomini.registrarUsuari(name,pass)) up.setInfo("The user "+name+" already exists");
                    else{
                        up.setInfo("Sign successful. Login to continue");
                    }
                }
            }
        });

        up.getLogOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actualUser!=null){
                    actualUser=null;
                    up.setInfo("Logout successful");
                }
            }
        });


        //GeneradorView Listeners:

        gp.getGenerateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=gp.getRows(), j=gp.getColumns(), n=gp.getNCells();
                if(i!=-1 && j!=-1 && n!=-1 && i<=200 && j<= 200 && n < i*j) {
                    CtrlGenerarKakuro cgk = ctrlDomini.getCtrlGenerarKakuro();
                    CtrlKakuro ck = ctrlDomini.getCtrlKakuro();
                    ck.setKakuro(cgk.generar(i, j, n));
                    KakuroGridVisualizer kgv = new KakuroGridVisualizer();
                    createKakuroView(kgv);
                    gp.setKakuroGrid(kgv);
                    gp.getKakuroPrev().setPreferredSize(new Dimension(300,300));
                    refreshFrame();
                    gp.setInfo("Kakuro generated correctly");

                }
                else{
                    JOptionPane.showMessageDialog(null,"All values must be integers");
                }
            }
        });

        gp.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actualUser==null) gp.setInfo("You need to login before saving a kakuro");
                else if(actualUser==null) gp.setInfo("You need to generate a kakuro first");
                else {
                    try {
                        //ctrlDomini.consultar_repositori().afegir(ctrlDomini.de_string_a_kakuro(k));
                        CtrlKakuro ctrlK = ctrlDomini.getCtrlKakuro();
                        //ctrlK.setKakuro(ctrlDomini.getCtrlStringKakuro().de_string_a_kakuro(k));
                        CtrlRepositori ctrlR = ctrlDomini.getCtrlRepositori();
                        ctrlR.addKakuro(ctrlK);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null,"You tried to save a kakuro that already exists");
                    }
                    gp.setInfo("Kakuro saved correctly");
                }
            }
        });

        gp.getPlayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ctrlDomini.getCtrlKakuro().getKakuro()==null) gp.setInfo("Generate a kakuro first");
                else{
                    pk = new PlayKakuroView();
                    setPlayListeners();
                    startKakuroPlay();
                    updatePlayLabels();
                    padre.add(pk);
                    card.next(padre);
                    partidaencurso = true;
                }
            }
        });


        //RankingView Listeners:

        ra.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                ra.searchFilter(ra.getSearchFilter());
            }
        });

        ra.getResetYourScoreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actualUser==null) JOptionPane.showMessageDialog(null,"You have to login first");
                else{
                    ctrlDomini.setPunts(0,actualUser,password);
                    ra.removeAllElements();
                    if(!ra.getSOR()) ra.setScores(ctrlDomini.getRanking());
                    else ra.setScores(ctrlDomini.getRecords());
                    ra.setInfo("Score reset correctly");
                }
            }
        });

        ra.getResetAllScoreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(actualUser);
                System.out.println(password);
                if (actualUser==null) JOptionPane.showMessageDialog(null,"You have to login first");
                else if(!ctrlDomini.isAdmin(actualUser, password)) JOptionPane.showMessageDialog(null,"You need to be admin to do that");
                else{
                    if(!ra.getSOR()){
                        ctrlDomini.resetRankingAll();
                        ra.removeAllElements();
                        ra.setScores(ctrlDomini.getRanking());
                    }
                    else{
                        ctrlDomini.resetRecords();
                        ra.removeAllElements();
                        ra.setScores(ctrlDomini.getRecords());
                    }
                    ra.setInfo("Score reset correctly");
                }
            }
        });


        ra.getResetSelectedScoreButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actualUser==null) JOptionPane.showMessageDialog(null,"You have to login first");
                else if(ra.getScoreList().isSelectionEmpty()) JOptionPane.showMessageDialog(null, "Select a score first");
                else if(!ctrlDomini.isAdmin(actualUser, password)) JOptionPane.showMessageDialog(null,"You need to be admin to do that");
                else{
                    String[] s = ra.getScoreList().getSelectedValue().split(" ");
                    ra.removeAllElements();
                    if(!ra.getSOR()){
                        ctrlDomini.setPunts(0,s[0],".");
                        ra.setScores(ctrlDomini.getRanking());
                    }
                    else {
                        ra.setScores(ctrlDomini.getRecords());
                        JOptionPane.showMessageDialog(null,"Only all records can be reseted");
                    }
                    ra.setInfo("Score reset correctly");
                }
            }
        });


        ra.getShowRankingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ra.getSOR()){
                    ra.swapLists();
                    ra.setScores(ctrlDomini.getRanking());
                }
                else JOptionPane.showMessageDialog(null,"The ranking list is already being showed");
            }
        });

        ra.getShowRecordsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!ra.getSOR()){
                    ArrayList<ArrayList<String>> records = new ArrayList<>(ctrlDomini.getRecords());
                    for(ArrayList<String> p: records){
                        System.out.println(p.get(2)+" "+p.get(0)+" "+p.get(1));
                    }


                    ra.swapLists();
                    ra.setScores(ctrlDomini.getRecords());
                }
                else JOptionPane.showMessageDialog(null,"The records list is already being showed");
            }
        });

        //RepositoriView Listeners

        rp.getGenerateNewKakuroButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.add(gp.getPanel());
                card.next(padre);
            }
        });

        rp.getCreateNewKakuroButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }

    public void setCreatorListeners(){
        ActionListener nextlistener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("NEXT");
                CtrlKakuro ctrlKakuro = ctrlDomini.getCtrlKakuro();
                if(ck.getScreenV() == 0) {//AddColorSelect
                    csize = Integer.valueOf(ck.getSizeTextField().getText());
                    ck.setSizeV(csize);
                    ck.createGridColorSelect(csize);
                    ctrlKakuro.createKakuro(csize);
                    for(int i=0; i<csize; ++i){
                        for(int j=0; j<csize; ++j){
                            KakuroCellColorSelect cell = new KakuroCellColorSelect();
                            cell.setI(i);
                            cell.setJ(j);
                            ctrlKakuro.setCellType(cell.getI(),cell.getJ(),true);
                            cell.setBackgroundColor(0);
                            ActionListener whitelistener = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    cell.changeBackgroundColor();
                                    if(cell.getColor() == 1) ctrlKakuro.setCellType(cell.getI(),cell.getJ(),false);
                                    else ctrlKakuro.setCellType(cell.getI(),cell.getJ(),true);
                                }
                            };
                            cell.addActionListener(whitelistener);
                            ck.getChooseColorGrid().getKakuroSelectColor().add(cell);
                            ck.getChooseColorGrid().getGridV()[i][j] = cell;
                        }
                    }
                    ck.getViewsCardPanel().add(ck.getChooseColorGrid());
                    System.out.println("added color select csize" + csize );
                }
                if(ck.getScreenV() == 1){//Add Hintselect
                    //set up choose hint select
                    ck.createGridSelectNHints(csize);
                    for (int i = 0; i < csize; ++i) {
                        for (int j = 0; j < csize; ++j) {
                            if(!ctrlKakuro.isCellBlack(i,j)){
                                KakuroCellWhiteVisualizer white = new KakuroCellWhiteVisualizer();
                                white.setPreferredSize(new Dimension(100,100));
                                white.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                                ck.getKakuroHintSelect().add(white);
                            }
                            else {
                                KakuroCellSelectNHintsParent cellNHintSelect = new KakuroCellSelectNHintsParent();
                                cellNHintSelect.setTipusPista(0);
                                cellNHintSelect.setPreferredSize(new Dimension(100,100));
                                cellNHintSelect.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                                int finalI = i;
                                int finalJ = j;
                                ActionListener blackListener = new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println("ChangeHint");
                                        cellNHintSelect.nextTipusPista();
                                        cellNHintSelect.repaint();
                                        if(cellNHintSelect.getTipusPista() == 0)ctrlKakuro.setCellHints(finalI,finalJ,-1,-1);
                                        else if(cellNHintSelect.getTipusPista() == 1)ctrlKakuro.setCellHints(finalI,finalJ,0,-1);
                                        else if(cellNHintSelect.getTipusPista() == 2)ctrlKakuro.setCellHints(finalI,finalJ,-1,0);
                                        else if(cellNHintSelect.getTipusPista() == 3)ctrlKakuro.setCellHints(finalI,finalJ,0,0);
                                    }
                                };
                                cellNHintSelect.addActionListener(blackListener);
                                ck.getKakuroHintSelect().add(cellNHintSelect);
                            }
                        }
                    }
                    ck.getViewsCardPanel().add(ck.getKakuroHintSelect());
                }

                if(ck.getScreenV() == 2){//Add HintValueselect
                    KakuroCellSetHintValues cellSetHintValues ;
                    ck.getKakuroHintValueSelect().createGrid(csize);
                    for (int i = 0; i < csize; ++i) {
                        for (int j = 0; j < csize; ++j) {
                            if(!ctrlKakuro.isCellBlack(i,j)){
                                cellSetHintValues = new KakuroCellSetHintValues(i,j,1,-1,-1);
                            }
                            else cellSetHintValues = new KakuroCellSetHintValues(i,j,0,ctrlKakuro.getCellHintHorizontal(i,j),ctrlKakuro.getCellHintVertical(i,j));
                            cellSetHintValues.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                            ck.getKakuroHintValueSelect().setLayout(new GridLayout(csize, csize));
                            ck.getKakuroHintValueSelect().setGrid(i,j,cellSetHintValues);
                            ck.getKakuroHintValueSelect().add(cellSetHintValues);

                        }
                    }
                    ck.viewsCardPanel.add(ck.getKakuroHintValueSelect());
                }
                if(ck.getScreenV() == 3){
                    ck.getnextButton().setText("Save and play");
                    for (int i = 0; i < csize; ++i) {
                        for (int j = 0; j < csize; ++j) {
                            if(ctrlKakuro.isCellBlack(i,j)) {
                                int pauxvert = -1;
                                int pauxhor = -1;
                                try{
                                    pauxhor = Integer.parseInt(ck.getKakuroHintValueSelect().getCell(i, j).getFieldPistaHoritzontal().getText());
                                }
                                catch (Exception exception){}
                                try{
                                    pauxvert = Integer.parseInt(ck.getKakuroHintValueSelect().getCell(i, j).getFieldPistaVertical().getText());
                                }
                                catch (Exception exception){}

                                System.out.println("Cell has " + pauxhor +" "+ pauxvert);
                                ctrlKakuro.setCellHints(i, j, pauxvert, pauxhor);
                            }
                        }
                    }
                    if(ctrlDomini.validateKakuro(ctrlKakuro)!=1){
                        JOptionPane.showMessageDialog(null, "El Kakuro no te solució");
                        while(padre.getComponentCount()>1){
                            card.previous(padre);
                            padre.remove(padre.getComponentCount()-1);
                        }
                    }
                    else {
                        try {
                            ctrlDomini.getCtrlRepositori().addKakuro(ctrlKakuro);
                        } catch (Exception exception) {}
                        pk = new PlayKakuroView();
                        setPlayListeners();
                        startKakuroPlay();
                        updatePlayLabels();
                        padre.add(pk);
                        card.next(padre);
                        partidaencurso = true;
                    }
                }


                ck.setScreen(ck.getScreenV()+1);
                ck.getViewsLayout().next(ck.getViewsCardPanel());
            }
        };


        ck.getnextButton().addActionListener(nextlistener);
    }

    public void setPlayListeners(){
        ActionListener goBackListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                while(padre.getComponentCount()>1){
                    card.previous(padre);
                    padre.remove(padre.getComponentCount()-1);
                }
                partidaencurso = false;
            }
        };

        pk.getBackButton().addActionListener(goBackListener);

        //mostra el panel d'usuari
        ActionListener userPanelListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                padre.add(up.getPanel());
                card.next(padre);
                ctrlDomini.getCtrlPartida().pausePartida();
            }
        };

        pk.getUserButton().addActionListener(userPanelListener);

        ActionListener hintListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    ctrlDomini.getCtrlPartida().usePista(Pista.revelar_casella_buida);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,exception.getMessage());
                }
                int size= ctrlDomini.getCtrlKakuro().getKakuro().consultarMida()[0];
                int randoma;
                int randomb;
                do{
                    randoma= ThreadLocalRandom.current().nextInt(0, size);
                    randomb = ThreadLocalRandom.current().nextInt(0, size);
                    while(ctrlDomini.getCtrlKakuro().isCellBlack(randoma,randomb)){
                        randoma= ThreadLocalRandom.current().nextInt(0, size);
                        randomb= ThreadLocalRandom.current().nextInt(0, size);
                    }
                }while(ctrlDomini.getCtrlKakuro().checkCell(randoma,randomb));

                ctrlDomini.getCtrlKakuro().solveCell(randoma,randomb);
                int value = ctrlDomini.getCtrlKakuro().getCellValue(randoma,randomb);
                ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(randoma,randomb)).setValue(value);
            }

        };
        pk.getHintButton().addActionListener(hintListener);

        //----Solve Button---
        ActionListener solveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){

                int size= ctrlDomini.getCtrlKakuro().getKakuro().consultarMida()[0];
                for(int i = 0; i<size; ++i){
                    for(int j=0; j<size; ++j){
                        if(! ctrlDomini.getCtrlKakuro().isCellBlack(i,j)) {
                            ctrlDomini.getCtrlKakuro().solveCell(i, j);
                            int value = ctrlDomini.getCtrlKakuro().getCellValue(i, j);
                            ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(i, j)).setValue(value);
                        }
                    }
                }
            }
        };
        pk.getSolveButton().addActionListener(solveListener);

        //----Start Over Button---
        ActionListener resetListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int size= ctrlDomini.getCtrlKakuro().getKakuro().consultarMida()[0];
                for(int i = 0; i<size; ++i){
                    for(int j=0; j<size; ++j){
                        if(! ctrlDomini.getCtrlKakuro().isCellBlack(i,j)) {
                            ctrlDomini.getCtrlKakuro().setCellValue(i,j,-1);
                            int value = ctrlDomini.getCtrlKakuro().getCellValue(i, j);
                            if(value == -1) ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(i, j)).setValue(value);
                            ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(i,j)).setBackground(Color.WHITE);
                        }
                    }
                }
            }
        };
        pk.getResetButton().addActionListener(resetListener);


        //----check Button---
        ActionListener checkListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    ctrlDomini.getCtrlPartida().usePista(Pista.comprovar_caselles_plenes);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,exception.getMessage());
                }
                int size= ctrlDomini.getCtrlKakuro().getKakuro().consultarMida()[0];
                for(int i = 0; i<size; ++i){
                    for(int j=0; j<size; ++j){
                        if(! ctrlDomini.getCtrlKakuro().isCellBlack(i,j)) {
                            if(ctrlDomini.getCtrlKakuro().checkCell(i,j)){
                                ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(i,j)).setBackground(Color.GREEN);
                            }
                            else ((KakuroCellWhitePlay) pk.getKakuroPanel().getCell(i,j)).setBackground(Color.RED);
                        }
                    }
                }
            }
        };
        pk.getCheckButton().addActionListener(checkListener);

        //----Save Button---
        ActionListener saveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ctrlDomini.getCtrlPartida().savePartida(actualUser);
            }
        };
        pk.getSaveButton().addActionListener(saveListener);
    }

    private void createKakuroView(KakuroGridVisualizer kgv){
        int size = ctrlDomini.getCtrlKakuro().getKakuro().consultarMida()[0];
        kgv.setLayout(new GridLayout(size,size));
        CtrlKakuro ctrlKakuro = ctrlDomini.getCtrlKakuro();
        for(int i=0; i<size; ++i){
            for(int j=0;j<size;++j){
                if(ctrlKakuro.isCellBlack(i,j)){
                    KakuroCellBlackVisualizer cellB = new KakuroCellBlackVisualizer(ctrlKakuro.getCellHintVertical(i,j), ctrlKakuro.getCellHintHorizontal(i,j));
                    cellB.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    kgv.add(cellB);
                }
                else{
                    KakuroCellWhiteVisualizer cellW = new KakuroCellWhiteVisualizer();
                    cellW.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    kgv.add(cellW);
                }
            }
        }
    }

    private void setRepositori(){
        CtrlRepositori ctrlRepositori = ctrlDomini.getCtrlRepositori();
        String[] info = ctrlRepositori.getKakuroInfoList();
        int cont = 0;
        CtrlKakuro[] kakuros = ctrlDomini.getCtrlRepositori().getKakuroList();
        for (CtrlKakuro k: kakuros){
            JPanel p = new JPanel();
            p.setLayout(new GridBagLayout());
            JPanel v = new JPanel();
            v.setLayout(new GridBagLayout());
            p.setMaximumSize(new Dimension(400,600));
            JLabel l = new JLabel();
            l.setFont(new Font(null,0,26));
            ctrlDomini.setCtrlKakuro(k);
            KakuroGridVisualizer kgv = new KakuroGridVisualizer();
            createKakuroView(kgv);
            l.setText(info[cont]);
            ++cont;
            JButton play = new JButton("Play");
            play.setFont(new Font(null,0,26));
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pk = new PlayKakuroView();
                    setPlayListeners();
                    startKakuroPlay();
                    updatePlayLabels();
                    padre.add(pk);
                    card.next(padre);
                }
            });
            JButton delete = new JButton("Delete");
            delete.setFont(new Font(null,0,26));
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(actualUser==null) JOptionPane.showMessageDialog(null,"You need to be logged as an admin");
                    else if(ctrlDomini.isAdmin(actualUser,password)) JOptionPane.showMessageDialog(null,"You need to be logged as an admin");
                    else {
                        ctrlDomini.getCtrlRepositori().removeAt(rp.getComponentIndex(p));
                        rp.getPanel().remove(p);
                        rp.reset();
                        setRepositori();
                        refreshFrame();
                    }
                }
            });
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth=1;
            c.weightx=1;
            c.weighty=1;
            c.gridx=0;
            c.gridy=0;
            p.add(kgv,c);
            kgv.setPreferredSize(new Dimension(400,400));
            c.gridx=0;
            c.gridy=1;
            p.add(l,c);
            c.gridx=1;
            c.gridy=1;
            v.add(play,c);
            c.gridx=2;
            c.gridy=1;
            v.add(delete,c);
            c.gridx=0;
            c.gridy=2;
            p.add(v,c);
            JLabel espacio = new JLabel();
            espacio.setText(" ");
            c.gridx=0;
            c.gridy=3;
            p.add(espacio,c);
            rp.addPanel(p);
        }

    }

    private void refreshFrame(){
        frame.setSize(new Dimension(frame.getHeight(),frame.getWidth()+1));
        frame.setSize(new Dimension(frame.getHeight(),frame.getWidth()-1));
    }

    private void startKakuroPlay(){
        //temporal kakuro for testing

        CtrlKakuro ctrlKakuro = ctrlDomini.getCtrlKakuro();
        //ctrlKakuro.setKakuro(ctrlDomini.getCtrlGenerarKakuro().generar_kakuro(10,10,40));
        ctrlDomini.getCtrlPartida().startPartida(ctrlKakuro);
        ///////
        int size = ctrlKakuro.getKakuro().consultarMida()[0];
        pk.getKakuroPanel().setGridSize(size);
        pk.getKakuroPanel().setLayout(new GridLayout(size,size));
        for(int i=0; i<size; ++i){
            for(int j=0;j<size;++j){
                if(ctrlKakuro.isCellBlack(i, j)){//creates black cell and adds it to the grid
                    KakuroCellBlackPlay blackCell = new KakuroCellBlackPlay(ctrlKakuro.getCellHintVertical(i,j),ctrlKakuro.getCellHintHorizontal(i,j),i,j);
                    blackCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    pk.getKakuroPanel().add(blackCell);
                    pk.getKakuroPanel().setCell(blackCell,i,j);
                }
                else{//creates white cel and add to the grid
                    KakuroCellWhitePlay whiteCell = new KakuroCellWhitePlay(ctrlKakuro.getCellValue(i,j),i,j);
                    whiteCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    int finalI = i;
                    int finalI1 = i;
                    int finalJ = j;
                    KeyListener whiteListener = new KeyListener() {
                        public void keyTyped(KeyEvent e) {
                            String lastKey="";
                            if(e.getKeyChar() == '1') lastKey = "1";
                            if(e.getKeyChar() == '2') lastKey = "2";
                            if(e.getKeyChar() == '3') lastKey = "3";
                            if(e.getKeyChar() == '4') lastKey = "4";
                            if(e.getKeyChar() == '5') lastKey = "5";
                            if(e.getKeyChar() == '6') lastKey = "6";
                            if(e.getKeyChar() == '7') lastKey = "7";
                            if(e.getKeyChar() == '8') lastKey = "8";
                            if(e.getKeyChar() == '9') lastKey = "9";
                            if(lastKey != "") {
                                whiteCell.setValue(Integer.valueOf(lastKey));
                                ctrlKakuro.setCellValue(finalI1, finalJ,Integer.valueOf(lastKey));
                            }

                        }
                        public void keyReleased(KeyEvent e){}
                        public void keyPressed(KeyEvent e){}
                    };
                    whiteCell.addKeyListener(whiteListener);
                    pk.getKakuroPanel().add(whiteCell);
                    pk.getKakuroPanel().setCell(whiteCell,i,j);
                }
            }
        }
        timerStart();
    }

    private void updatePlayLabels(){
        pk.getTimeLabel().setText("Time: "+String.valueOf((ctrlDomini.getCtrlPartida().getTemps()/1000)/60)+ ":"+
                String.valueOf((ctrlDomini.getCtrlPartida().getTemps()/10000)%6)+
                String.valueOf((ctrlDomini.getCtrlPartida().getTemps()/1000)%10));
        //pk.getScoreLabel().setText(String.valueOf(ctrlDomini.getCtrlPartida().getScore));
        //pk.getRankLabel().setText(String.valueOf(ctrlDomini.getCtrlPartida().getRank));

    }

    public void timerStart() {
        final Runnable timer = new Runnable() {
            public void run() { updatePlayLabels(); }
        };
        final ScheduledFuture<?> timeHandle =
                scheduler.scheduleAtFixedRate(timer, 1, 1, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() { timeHandle.cancel(true); }
        }, 60 * 60, SECONDS);
    }
}
