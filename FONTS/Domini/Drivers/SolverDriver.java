package Domini.Drivers;

import Domini.*;
import Persistencia.CtrlPersistencia;

import java.util.*;

public class SolverDriver {
    private static Solver solver;
    private static Scanner scanner;
    private static CtrlPersistencia pers;
    private static CtrlDomini ctrlDomini;
    private static CtrlKakuro ctrlKak;
    private static Kakuro kakuro;
    private static String path;

    public static void testSolver(){
        kakuro.printKakuro();
        System.out.println();
        int numsols = solver.soluciona(1,0,-1);
        ctrlKak = solver.getCtrlKakuro();
        kakuro = ctrlKak.getKakuro();
        kakuro.printKakuro();
        String sol = ctrlDomini.getCtrlStringKakuro().de_kakuro_a_string(kakuro);
        pers.escriureArxiu("sol.txt",sol);
        String solreal = pers.llegirArxiu("sol"+path);
        if(sol.equals(solreal))System.out.println("Result: OK");
        else if(numsols == 2) System.out.println("Result: WRONG, multiple solutions");
        else if(numsols == 0) System.out.println("Result: WRONG, invalid kakuro");
        else System.out.println("Result: WRONG solution");
    }

    public static void testValorsCaselles(){
        solver.valorsCaselles();
        Dictionary<Integer, HashSet<Integer>> cjtPos = solver.getCjtValorsPossibles();
        HashSet<Integer> test = cjtPos.get(solver.getNcolumnes() +2);
        System.out.println(test);
    }

    public static void testValorsFila(){
        System.out.println("Introdueix llargada fila:");
        int llarg = scanner.nextInt();
        System.out.println("Introdueix el que ha de sumar:");
        int suma = scanner.nextInt();
        Set<Integer> set = solver.calculaValorsPossibles(llarg,suma);
        System.out.println("Els valors possibles per cada casella son:");
        System.out.println(Arrays.toString(set.toArray()));
    }

    private static void setKakuro(){
        solver = new Solver();
        scanner = new Scanner(System.in);
        pers = new CtrlPersistencia();
        ctrlDomini = new CtrlDomini();
        ctrlKak = new CtrlKakuro();
        System.out.println("Escriu el nom de l'arxiu del kakuro:");
        path = scanner.next();
        String kak = pers.llegirArxiu(path);
        kakuro = ctrlDomini.getCtrlStringKakuro().de_string_a_kakuro(kak);
        ctrlKak.setKakuro(kakuro);
        solver.setCtrlKakuro(ctrlKak);
        solver.setCaselles(kakuro.consultar_caselles());
    }

    public static void main(String[] args){
        solver = new Solver();
        scanner = new Scanner(System.in);
        pers = new CtrlPersistencia();
        ctrlDomini = new CtrlDomini();
        ctrlKak = new CtrlKakuro();
        System.out.println("Escriu el nom de l'arxiu del kakuro:");
        path = scanner.next();
        String kak = pers.llegirArxiu(path);
        kakuro = ctrlDomini.getCtrlStringKakuro().de_string_a_kakuro(kak);
        ctrlKak.setKakuro(kakuro);
        solver.setCtrlKakuro(ctrlKak);
        solver.setCaselles(kakuro.consultar_caselles());
        int opcio;
        while(true) {
            System.out.println("Tria funcio a provar: ");
            System.out.println("1. Solver");
            System.out.println("2. Valors possibles fila");
            System.out.println("3. Valors caselles");
            System.out.println("4. Set kakuro");
            opcio = scanner.nextInt();
            switch (opcio) {
                case 1:
                    testSolver();
                    break;
                case 2:
                    testValorsFila();
                    break;
                case 3:
                    testValorsCaselles();
                    break;
                case 4:
                    setKakuro();
                    break;
                default:
                    return;
            }
        }
    }
}
