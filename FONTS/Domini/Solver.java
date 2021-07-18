package Domini;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;

public class Solver {
    private CtrlKakuro ctrlKakuro;
    private int nfiles;
    private int ncolumnes;
    private Casella[][] caselles;

    private Dictionary<Integer,HashSet<Integer>> cjtValorsPossibles = new Hashtable<>();
    private int numSol = 0;

    public int getNcolumnes(){
        return ncolumnes;
    }
    public void setCaselles(Casella[][] cas){
        caselles = cas;
    }
    public void setCtrlKakuro(CtrlKakuro ctrl){
        this.ctrlKakuro = ctrl;
    }
    public CtrlKakuro getCtrlKakuro(){
        return ctrlKakuro;
    }

    public int soluciona(int i, int j, int sumaH){
        nfiles = caselles.length;
        ncolumnes = caselles[0].length;
        numSol = 0;
        cjtValorsPossibles = new Hashtable<>();
        for (int k = 1; k < nfiles; ++k) {
            for (int l = 1; l < ncolumnes; ++l) {
                if (caselles[k][l] instanceof CasellaBlanca) {
                    ((CasellaBlanca) caselles[k][l]).escriure_valor(0);
                }
            }
        }
        for (int k = 1; k < nfiles; ++k) {
            for (int l = 1; l < ncolumnes; ++l) {
                if (caselles[k][l] instanceof CasellaBlanca) {
                    ((CasellaBlanca) caselles[k][l]).escriure_valor(0);
                }
            }
        }
        valorsCaselles();
        long start = System.currentTimeMillis();
        backtrack2(i,j,sumaH,new HashSet<>());
        System.out.println("Temps solver: " + (System.currentTimeMillis()-start)/1000. + " segons");
        return numSol;
    }
    public int validateKakuro(Kakuro kak){
        this.caselles = kak.consultar_caselles();
        return soluciona(1,0,-1);
    }

    private void saveCaselles(){
        for(int k = 1; k < nfiles; ++k){
            for(int l = 1; l < ncolumnes; ++l){
                if(caselles[k][l] instanceof CasellaBlanca){
                    ((CasellaBlanca) caselles[k][l]).escriure_valor_correcte(((CasellaBlanca) caselles[k][l]).consultar_valor());
                }
            }
        }
    }
    private int obteSumaVert(int i, int j){
        int aux = 0;
        while(caselles[i][j] instanceof CasellaBlanca){
            aux += ((CasellaBlanca) caselles[i][j]).consultar_valor_correcte();
            --i;
        }
        return ((CasellaNegra)caselles[i][j]).consultar_pistaVertical()-aux;
    }
    public Dictionary<Integer,HashSet<Integer>> getCjtValorsPossibles(){
        return cjtValorsPossibles;
    }
    private HashSet<Integer> setPosatsV(int i, int j){
        HashSet<Integer> posatsV = new HashSet<>();
        int aux = i-1;
        while(caselles[aux][j] instanceof CasellaBlanca){
            posatsV.add(((CasellaBlanca) caselles[aux][j]).consultar_valor_correcte());
            --aux;
        }
        aux = i;
        while((aux < nfiles) && caselles[aux][j] instanceof CasellaBlanca){
            posatsV.add(((CasellaBlanca) caselles[aux][j]).consultar_valor_correcte());
            ++aux;
            if(aux > nfiles-1)break;
        }
        posatsV.add(((CasellaBlanca) caselles[i][j]).consultar_valor_correcte());
        return posatsV;
    }
    private void valorsFila(int suma, int numCaselles, HashSet<Integer> jaPosats, HashSet<Integer> possiblesValors){
        if(numCaselles == 1){
            if(suma > 0 && suma < 10 && !jaPosats.contains(suma)){
                jaPosats.add(suma);
                possiblesValors.addAll(jaPosats);
                jaPosats.remove(suma);
            }
        }
        else{
            for(int p = 1; p < 10 && p < suma; ++p){
                if(!jaPosats.contains(p)) {
                    jaPosats.add(p);
                    valorsFila(suma - p, numCaselles - 1, jaPosats, possiblesValors);
                    jaPosats.remove(p);
                }
            }
        }
    }
    public HashSet<Integer> calculaValorsPossibles(int num, int suma){
        HashSet<Integer> jaPosats = new HashSet<>();
        HashSet<Integer> possiblesValors = new HashSet<>();
        valorsFila(suma,num,jaPosats,possiblesValors);
        return possiblesValors;
    }
    private int getLlargada(int i, int j){
        int num = 0;
        while(j < ncolumnes && caselles[i][j] instanceof CasellaBlanca){
            ++num;
            ++j;
        }
        return num;
    }
    private int getLlargadaV(int i, int j){
        int num = 0,aux = i;
        while(caselles[aux][j] instanceof CasellaBlanca){
            ++num;
            --aux;
        }
        ++i;
        while(i <= nfiles-1 && caselles[i][j] instanceof CasellaBlanca){
            ++num;
            ++i;
        }
        return num;
    }
    private int getPistaV(int i, int j){
        while(caselles[i][j] instanceof CasellaBlanca){
            --i;
        }
        return ((CasellaNegra)caselles[i][j]).consultar_pistaVertical();
    }
    public void valorsCaselles(){
        cjtValorsPossibles = new Hashtable<>();
        nfiles = caselles.length;
        ncolumnes = caselles[0].length;
        for(int i = 1; i <= nfiles-1; ++i){
            boolean enFila = false;
            HashSet<Integer> horitz = new HashSet<>();
            for(int j = 1; j <= ncolumnes-1; ++j){
                if(caselles[i][j] instanceof CasellaBlanca){
                    if(!enFila){
                        enFila = true;
                        horitz = calculaValorsPossibles(getLlargada(i,j),((CasellaNegra)caselles[i][j-1]).consultar_pistaHorizontal());
                        HashSet<Integer> vert = calculaValorsPossibles(getLlargadaV(i,j),getPistaV(i,j));
                        vert.retainAll(horitz);
                        cjtValorsPossibles.put(i*(ncolumnes-1)+j,vert);
                    } else{
                        HashSet<Integer> vert = calculaValorsPossibles(getLlargadaV(i,j),getPistaV(i,j));
                        vert.retainAll(horitz);
                        cjtValorsPossibles.put(i*(ncolumnes-1)+j,vert);
                    }
                }
                else {
                    enFila = false;
                    horitz = new HashSet<>();
                }
            }
        }
    }
    public void printCaselles(){
        Kakuro kak = new Kakuro(caselles);
        kak.printKakuro();
        System.out.println();
    }

    private void backtrack2(int i, int j, int sumaH, HashSet<Integer> posatsH){
        if(numSol > 1) return;
        HashSet<Integer> posatsV = new HashSet<>();
        if(caselles[i][j] instanceof CasellaBlanca)posatsV = setPosatsV(i,j);
        int sumaV = obteSumaVert(i,j);
        if(i == nfiles-1 && j == ncolumnes-1){
            if(caselles[i][j] instanceof CasellaNegra) {
                ++numSol;
                if(numSol == 1){
                    saveCaselles();
                }
            }
            else if(!posatsH.contains(sumaH) && !posatsV.contains(sumaV) && sumaH == sumaV){
                ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                ++numSol;
                if(numSol == 1){
                    saveCaselles();
                }
                ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
            }
        }
        else if(i == nfiles-1){
            if(caselles[i][j] instanceof CasellaNegra){
                if(caselles[i][j+1] instanceof CasellaBlanca)backtrack2(i,j+1,((CasellaNegra)caselles[i][j]).consultar_pistaHorizontal(),new HashSet<>());
                else backtrack2(i,j+1,-1,new HashSet<>());
            }
            else {
                if(caselles[i][j+1] instanceof CasellaNegra){
                    if(sumaH < 9 && sumaH > 0){
                        if(!posatsH.contains(sumaH) && sumaV == sumaH && !posatsV.contains(sumaV) && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaH)){
                            ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                            backtrack2(i,j+1,-1,null);
                            ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                        }
                    }
                }
                else if(!posatsV.contains(sumaV) && sumaV < sumaH && !posatsH.contains(sumaV) && sumaV > 0 && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaV)){
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaV);
                    backtrack2(i,j+1,sumaH-sumaV,new HashSet<>());
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                }
            }
        }
        else if(j == ncolumnes-1){
            if(caselles[i][j] instanceof CasellaNegra){
                backtrack2(i+1,0,-1,null);
            }
            else if(caselles[i+1][j] instanceof CasellaNegra){
                if(sumaH == sumaV && !posatsH.contains(sumaH) && !posatsV.contains(sumaV) && sumaH > 0 && sumaH < 10 && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaH)){
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                    backtrack2(i+1,0,-1,null);
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                }
            }
            else {
                if(sumaH > 0 && sumaH < 10 && !posatsH.contains(sumaH) && !posatsV.contains(sumaH) && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaH)){
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                    backtrack2(i+1,0,-1,null);
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                }
            }
        }
        else {
            if(caselles[i][j] instanceof CasellaNegra){
                if(caselles[i][j+1] instanceof CasellaBlanca){
                    backtrack2(i,j+1,((CasellaNegra)caselles[i][j]).consultar_pistaHorizontal(),new HashSet<>());
                } else backtrack2(i,j+1,-1,null);
            }
            else if(caselles[i][j+1] instanceof CasellaNegra){
                if(caselles[i+1][j] instanceof CasellaNegra){
                    if(sumaH == sumaV && sumaH > 0 && sumaH < 10 && !posatsH.contains(sumaH) && !posatsV.contains(sumaV) && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaH)){
                        ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                        backtrack2(i,j+1,-1,null);
                        ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                    }
                }
                else if(sumaH < 10 && sumaH > 0 && !posatsH.contains(sumaH) && !posatsV.contains(sumaH) && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaH)){
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaH);
                    backtrack2(i,j+1,-1,null);
                    ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                }
            }
            else {
                if(caselles[i+1][j] instanceof CasellaNegra){
                    if(sumaV > 0 && sumaV < 10 && !posatsV.contains(sumaV) && !posatsH.contains(sumaV) && sumaH > sumaV && cjtValorsPossibles.get(i*(ncolumnes-1)+j).contains(sumaV)){
                        ((CasellaBlanca)caselles[i][j]).escriure_valor(sumaV);
                        posatsH.add(sumaV);
                        backtrack2(i,j+1,sumaH-sumaV,posatsH);
                        posatsH.remove(sumaV);
                        ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                    }
                }
                else {
                    for(Integer p : cjtValorsPossibles.get(i*(ncolumnes-1)+j)){
                        if(numSol > 1) break;
                        if(sumaH > p && sumaV > p){
                            if(!posatsH.contains(p) && !posatsV.contains(p)){
                                ((CasellaBlanca)caselles[i][j]).escriure_valor(p);
                                posatsH.add(p);
                                backtrack2(i,j+1,sumaH-p,posatsH);
                                posatsH.remove(p);
                                ((CasellaBlanca)caselles[i][j]).escriure_valor(0);
                            }
                        }
                    }
                }
            }
        }
    }

}
