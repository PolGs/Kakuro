package Domini;

import java.util.*;

public class CtrlGenerarKakuro {

    private int nfiles;
    private int ncolumnes;
    private int nblanques;
    private int blanques_actuals;
    private boolean[][] mig_patro_negres;
    private int[][] sizeRow;
    private int[][] sizeCol;
    private int[][] hintRowIndex;
    private int[][] hintColIndex;
    private Casella[][] caselles;
    private Random random;
    private boolean[][] per_visitar;
    private ArrayList<Integer>[][] numberPossibilities;
    private ArrayList<Integer>[][] hintRowPossibilities;
    private ArrayList<Integer>[][] hintColPossibilities;
    private ArrayList<Integer[]> hintsPositionList;

    private int iter;
    private boolean generatedValid;

    // Indexes for cell count, map keys for sum and then list of possibilities lists
    private TreeMap<Integer, ArrayList<ArrayList<Integer>>>[] possibilitiesCellCountSum;

    public Kakuro generar(int nfiles, int ncolumnes, int nblanques) {

        try {
            this.nfiles = nfiles;
            this.ncolumnes = ncolumnes;
            this.nblanques = nblanques;
            mig_patro_negres = new boolean[nfiles - 1][(ncolumnes + 1) / 2];
            caselles = new Casella[nfiles][ncolumnes];
            random = new Random();
            iter = 0;

            generar_mig_patro();

            if (nblanques < blanques_actuals) {
                reduir_blanques();
            }
            if (nblanques > blanques_actuals) {
                augmentar_blanques();
            }

            generar_caselles_patro();
            calculateSizeRow();
            calculateSizeCol();
            calculateHintRow();
            calculateHintCol();
            precalculatePossibilitiesCellCountSum();
            runPossibilities();
            removeNumbers();
        } catch (Exception e) {
            return generar(nfiles, ncolumnes, nblanques);
        }

        return new Kakuro(caselles);
    }

    private void updateNumberPossFromHintPoss2(int i, int j, boolean row, ArrayList<Integer> hintPoss) {
        if (row) {
            ArrayList<ArrayList<Integer>> possibilities = new ArrayList<>(sizeRow[i][j]);
            ArrayList<Set<Integer>> result = new ArrayList<>(sizeRow[i][j]);
            for (int k = j + 1; k < j + sizeRow[i][j] + 1; k++) {
                possibilities.add(numberPossibilities[i][k]);
                result.add(new HashSet<>());
            }
            getNumberPossFromHintPossBacktrack(0, possibilities, new ArrayList<>(sizeRow[i][j]), 0, hintPoss, result);
            for (int k = j + 1; k < j + sizeRow[i][j] + 1; k++) {
                numberPossibilities[i][k] = new ArrayList<>(result.get(k - j - 1));
                if (numberPossibilities[i][k].size() == 1) {
                    int value = numberPossibilities[i][k].get(0);
                    ((CasellaBlanca) caselles[i][k]).escriure_valor(value);
                }
            }
        } else {
            ArrayList<ArrayList<Integer>> possibilities = new ArrayList<>(sizeCol[i][j]);
            ArrayList<Set<Integer>> result = new ArrayList<>(sizeCol[i][j]);
            for (int k = i + 1; k < i + sizeCol[i][j] + 1; k++) {
                possibilities.add(numberPossibilities[k][j]);
                result.add(new HashSet<>());
            }
            getNumberPossFromHintPossBacktrack(0, possibilities, new ArrayList<>(sizeCol[i][j]), 0, hintPoss, result);
            for (int k = i + 1; k < i + sizeCol[i][j] + 1; k++) {
                numberPossibilities[k][j] = new ArrayList<>(result.get(k - i - 1));
                if (numberPossibilities[k][j].size() == 1) {
                    int value = numberPossibilities[k][j].get(0);
                    ((CasellaBlanca) caselles[k][j]).escriure_valor(value);
                }
            }
        }
    }

    private void calculateSizeRow() {
        sizeRow = new int[nfiles][ncolumnes];
        for (int i = 0; i < nfiles; i++) {
            int currentSize = 0;
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaNegra) {
                    if (currentSize > 0) {
                        for (int k = j - currentSize - 1; k < j; k++) {
                            sizeRow[i][k] = currentSize;
                        }
                    }
                    currentSize = 0;
                } else {
                    currentSize++;
                }
            }
            if (currentSize > 0) {
                for (int k = ncolumnes - currentSize - 1; k < ncolumnes; k++) {
                    sizeRow[i][k] = currentSize;
                }
            }
        }
    }

    private void calculateSizeCol() {
        sizeCol = new int[nfiles][ncolumnes];
        for (int j = 0; j < ncolumnes; j++) {
            int currentSize = 0;
            for (int i = 0; i < nfiles; i++) {
                if (caselles[i][j] instanceof CasellaNegra) {
                    if (currentSize > 0) {
                        for (int k = i - currentSize - 1; k < i; k++) {
                            sizeCol[k][j] = currentSize;
                        }
                    }
                    currentSize = 0;
                } else {
                    currentSize++;
                }
            }
            for (int k = nfiles - currentSize - 1; k < nfiles; k++) {
                sizeCol[k][j] = currentSize;
            }
        }
    }

    private void calculateHintRow() {
        hintRowIndex = new int[nfiles][ncolumnes];
        for (int i = 0; i < nfiles; i++) {
            int currentIndex = -1;
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaNegra) {
                    if (sizeRow[i][j] != 0) {
                        currentIndex = j;
                    }
                    hintRowIndex[i][j] = currentIndex;
                } else {
                    hintRowIndex[i][j] = currentIndex;
                }
            }
        }
    }

    private void calculateHintCol() {
        hintColIndex = new int[nfiles][ncolumnes];
        for (int j = 0; j < ncolumnes; j++) {
            int currentIndex = -1;
            for (int i = 0; i < nfiles; i++) {
                if (caselles[i][j] instanceof CasellaNegra) {
                    if (sizeCol[i][j] != 0) {
                        currentIndex = i;
                    }
                    hintColIndex[i][j] = currentIndex;
                } else {
                    hintColIndex[i][j] = currentIndex;
                }
            }
        }
    }

    private void precalculatePossibilitiesCellCountSum() {
        possibilitiesCellCountSum = new TreeMap[10];
        for (int i = 1; i <= 9; i++) {
            possibilitiesCellCountSum[i] = new TreeMap<>();
            precalculatePossibilitiesCellCountSumBactrack(i, new ArrayList<>(), 0);
        }
    }

    private void precalculatePossibilitiesCellCountSumBactrack(int cellCount, ArrayList<Integer> current, int sum) {
        if (current.size() == cellCount) {
            if (!possibilitiesCellCountSum[cellCount].containsKey(sum)) {
                possibilitiesCellCountSum[cellCount].put(sum, new ArrayList<>());
            }
            possibilitiesCellCountSum[cellCount].get(sum).add(current);
            return;
        }
        int start = current.size() > 0 ? current.get(current.size() - 1) + 1 : 1;
        for (int i = start; i <= 9; i++) {
            ArrayList<Integer> updated = new ArrayList<>(current);
            updated.add(i);
            precalculatePossibilitiesCellCountSumBactrack(cellCount, updated, sum + i);
        }
    }

    private void removeNumbers() {
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaBlanca) {
                    CasellaBlanca cell = (CasellaBlanca) caselles[i][j];
                    cell.escriure_valor_correcte(cell.consultar_valor());
                    cell.escriure_valor(-1);
                }
            }
        }
    }

    private void runPossibilities() {
        numberPossibilities = new ArrayList[nfiles][ncolumnes];
        hintRowPossibilities = new ArrayList[nfiles][ncolumnes];
        hintColPossibilities = new ArrayList[nfiles][ncolumnes];
        ArrayList<Integer> allNine = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            allNine.add(i);
        }
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaBlanca) {
                    numberPossibilities[i][j] = new ArrayList<>(allNine);
                }
            }
        }
        hintsPositionList = new ArrayList<>();
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaNegra) {
                    if (sizeRow[i][j] != 0) {
                        hintRowPossibilities[i][j] = new ArrayList<>(possibilitiesCellCountSum[sizeRow[i][j]].keySet());
                        hintsPositionList.add(new Integer[] {i, j, 1});
                    }
                    if (sizeCol[i][j] != 0) {
                        hintColPossibilities[i][j] = new ArrayList<>(possibilitiesCellCountSum[sizeCol[i][j]].keySet());
                        hintsPositionList.add(new Integer[] {i, j, 0});
                    }
                }
            }
        }
        Collections.shuffle(hintsPositionList, random);
        generatedValid = false;
        setHintBacktrack(0);
    }

    private Casella[][] copyCells(Casella[][] from) {
        Casella[][] to = new Casella[nfiles][ncolumnes];
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (from[i][j] instanceof CasellaBlanca) {
                    CasellaBlanca white = new CasellaBlanca();
                    white.escriure_valor(((CasellaBlanca) from[i][j]).consultar_valor());
                    to[i][j] = white;
                } else {
                    CasellaNegra black = new CasellaNegra();
                    black.setpistaHorizontal(((CasellaNegra) from[i][j]).consultar_pistaHorizontal());
                    black.setpistaVertical(((CasellaNegra) from[i][j]).consultar_pistaVertical());
                    to[i][j] = black;
                }
            }
        }
        return to;
    }

    private ArrayList<Integer>[][] copyListMatrix(ArrayList<Integer>[][] from) {
        ArrayList<Integer>[][] to = new ArrayList[nfiles][ncolumnes];
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (from[i][j] == null) {
                    to[i][j] = null;
                } else {
                    to[i][j] = new ArrayList<>(from[i][j]);
                }
            }
        }
        return to;
    }

    private boolean oneNumberPossibilityEachCell() {
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (numberPossibilities[i][j] != null && numberPossibilities[i][j].size() != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean safeCheckOneSolution() {
        ArrayList<Integer>[][] backup = copyListMatrix(numberPossibilities);
        removeNumbers();
        numberPossibilities = new ArrayList[nfiles][ncolumnes];
        ArrayList<Integer> allNine = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            allNine.add(i);
        }
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (caselles[i][j] instanceof CasellaBlanca) {
                    numberPossibilities[i][j] = new ArrayList<>(allNine);
                }
            }
        }
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < nfiles; i++) {
                for (int j = 0; j < ncolumnes; j++) {
                    if (caselles[i][j] instanceof CasellaNegra) {
                        CasellaNegra black = (CasellaNegra) caselles[i][j];
                        if (black.consultar_pistaHorizontal() != -1) {
                            ArrayList<Integer> hintPoss = new ArrayList<>(1);
                            hintPoss.add(black.consultar_pistaHorizontal());
                            updateNumberPossFromHintPoss2(i, j, true, hintPoss);
                        }
                        if (black.consultar_pistaVertical() != -1) {
                            ArrayList<Integer> hintPoss = new ArrayList<>(1);
                            hintPoss.add(black.consultar_pistaVertical());
                            updateNumberPossFromHintPoss2(i, j, false, hintPoss);
                        }
                    }
                }
            }
        }
        boolean correct = oneNumberPossibilityEachCell();
        numberPossibilities = copyListMatrix(backup);
        return correct;
    }

    private void setHintBacktrack(int hintPositionIndex) {
        if (iter > 10000) return;
        iter++;
        System.out.println(iter);
        if (hintPositionIndex == hintsPositionList.size()) {
            return;
        }
        Integer[] hintPosition = hintsPositionList.get(hintPositionIndex);
        Casella[][] startCells = copyCells(caselles);
        ArrayList<Integer>[][] startNumberPossibilities = copyListMatrix(numberPossibilities);
        ArrayList<Integer>[][] startHintRowPossibilities = copyListMatrix(hintRowPossibilities);
        ArrayList<Integer>[][] startHintColPossibilities = copyListMatrix(hintColPossibilities);
        if (hintPosition[2] == 1) {
            ArrayList<Integer> hints = hintRowPossibilities[hintPosition[0]][hintPosition[1]];
            for (int i = 0; i < hints.size(); i++) {
                CasellaNegra cell = (CasellaNegra) caselles[hintPosition[0]][hintPosition[1]];
                int index = random.nextInt(2) == 0 ? i : hints.size() - i - 1;
                if (iter > 10000) return;
                cell.setpistaHorizontal(hints.get(index));
                ArrayList<Integer> hintsPoss = new ArrayList<>(1);
                hintsPoss.add(hints.get(index));
                if (updateNumberPossFromHintPoss(hintPosition[0], hintPosition[1], true, hintsPoss)) {
                    setHintBacktrack(hintPositionIndex + 1);
                    if (oneNumberPossibilityEachCell()) {
                        if (!generatedValid) {
                            generatedValid = safeCheckOneSolution();
                        }
                        if (generatedValid) {
                            return;
                        }
                    }
                } else {
                    caselles = copyCells(startCells);
                    numberPossibilities = copyListMatrix(startNumberPossibilities);
                    hintRowPossibilities = copyListMatrix(startHintRowPossibilities);
                    hintColPossibilities = copyListMatrix(startHintColPossibilities);
                }
                cell.setpistaHorizontal(-1);
            }
        } else {
            ArrayList<Integer> hints = hintColPossibilities[hintPosition[0]][hintPosition[1]];
            for (int i = 0; i < hints.size(); i++) {
                CasellaNegra cell = (CasellaNegra) caselles[hintPosition[0]][hintPosition[1]];
                int index = random.nextInt(2) == 0 ? i : hints.size() - i - 1;
                if (iter > 10000) return;
                cell.setpistaVertical(hints.get(index));
                ArrayList<Integer> hintsPoss = new ArrayList<>(1);
                hintsPoss.add(hints.get(index));
                if (updateNumberPossFromHintPoss(hintPosition[0], hintPosition[1], false, hintsPoss)) {
                    setHintBacktrack(hintPositionIndex + 1);
                    if (oneNumberPossibilityEachCell()) {
                        if (!generatedValid) {
                            generatedValid = safeCheckOneSolution();
                        }
                        if (generatedValid) {
                            return;
                        }
                    }
                } else {
                    caselles = copyCells(startCells);
                    numberPossibilities = copyListMatrix(startNumberPossibilities);
                    hintRowPossibilities = copyListMatrix(startHintRowPossibilities);
                    hintColPossibilities = copyListMatrix(startHintColPossibilities);
                }
                cell.setpistaVertical(-1);
            }
        }
    }

    private void getNumberPossFromHintPossBacktrack(int i, ArrayList<ArrayList<Integer>> numberPossibilities, ArrayList<Integer> used,
                                                         int sum, ArrayList<Integer> hintPossibilities, ArrayList<Set<Integer>> result) {
        if (i == numberPossibilities.size()) {
            if (hintPossibilities.contains(sum)) {
                for (int j = 0; j < used.size(); j++) {
                    result.get(j).add(used.get(j));
                }
            }
            return;
        }
        for (int possible : numberPossibilities.get(i)) {
            if (!used.contains(possible)) {
                ArrayList<Integer> copyUsed = new ArrayList<>(used);
                copyUsed.add(possible);
                getNumberPossFromHintPossBacktrack(i + 1, numberPossibilities, copyUsed, sum + possible, hintPossibilities, result);
            }
        }
    }

    private boolean updateNumberPossFromHintPoss(int i, int j, boolean row, ArrayList<Integer> hintPoss) {
        if (row) {
            ArrayList<ArrayList<Integer>> possibilities = new ArrayList<>(sizeRow[i][j]);
            ArrayList<Set<Integer>> result = new ArrayList<>(sizeRow[i][j]);
            for (int k = j + 1; k < j + sizeRow[i][j] + 1; k++) {
                possibilities.add(numberPossibilities[i][k]);
                result.add(new HashSet<>());
            }
            getNumberPossFromHintPossBacktrack(0, possibilities, new ArrayList<>(sizeRow[i][j]), 0, hintPoss, result);
            for (int k = j + 1; k < j + sizeRow[i][j] + 1; k++) {
                int oldSize = numberPossibilities[i][k].size();
                numberPossibilities[i][k] = new ArrayList<>(result.get(k - j - 1));
                if (numberPossibilities[i][k].size() != oldSize) {
                    if (numberPossibilities[i][k].size() == 0) {
                        return false;
                    } else if (numberPossibilities[i][k].size() == 1) {
                        int value = numberPossibilities[i][k].get(0);
                        ((CasellaBlanca) caselles[i][k]).escriure_valor(value);
                    }
                    if (!updateHintPossibilitiesFromNumberPossibilities(i, k)) {
                        return false;
                    }
                    ArrayList<Integer> newHintPoss = hintColPossibilities[hintColIndex[i][k]][k];
                    if (!updateNumberPossFromHintPoss(hintColIndex[i][k], k, false, newHintPoss)) {
                        return false;
                    }
                }
            }
        } else {
            ArrayList<ArrayList<Integer>> possibilities = new ArrayList<>(sizeCol[i][j]);
            ArrayList<Set<Integer>> result = new ArrayList<>(sizeCol[i][j]);
            for (int k = i + 1; k < i + sizeCol[i][j] + 1; k++) {
                possibilities.add(numberPossibilities[k][j]);
                result.add(new HashSet<>());
            }
            getNumberPossFromHintPossBacktrack(0, possibilities, new ArrayList<>(sizeCol[i][j]), 0, hintPoss, result);
            for (int k = i + 1; k < i + sizeCol[i][j] + 1; k++) {
                int oldSize = numberPossibilities[k][j].size();
                numberPossibilities[k][j] = new ArrayList<>(result.get(k - i - 1));
                if (numberPossibilities[k][j].size() != oldSize) {
                    if (numberPossibilities[k][j].size() == 0) {
                        return false;
                    } else if (numberPossibilities[k][j].size() == 1) {
                        int value = numberPossibilities[k][j].get(0);
                        ((CasellaBlanca) caselles[k][j]).escriure_valor(value);
                    }
                    if (!updateHintPossibilitiesFromNumberPossibilities(k, j)) {
                        return false;
                    }
                    ArrayList<Integer> newHintPoss = hintRowPossibilities[k][hintRowIndex[k][j]];
                    if (!updateNumberPossFromHintPoss(k, hintRowIndex[k][j], true, newHintPoss)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void getHintPossibilitiesFromNumberPossibilitiesBacktrack(int i, ArrayList<ArrayList<Integer>> numberPossibilities,
                                                                      ArrayList<Integer> used, int sum, ArrayList<Integer> result) {
        if (i == numberPossibilities.size()) {
            if (!result.contains(sum)) {
                result.add(sum);
            }
            return;
        }
        for (int possible : numberPossibilities.get(i)) {
            if (!used.contains(possible)) {
                ArrayList<Integer> copyUsed = new ArrayList<>(used);
                copyUsed.add(possible);
                getHintPossibilitiesFromNumberPossibilitiesBacktrack(i + 1, numberPossibilities, copyUsed, sum + possible, result);
            }
        }
    }

    private boolean updateHintPossibilitiesFromNumberPossibilities(int i, int j) {
        if (hintRowPossibilities[i][hintRowIndex[i][j]].size() != 1) {
            ArrayList<ArrayList<Integer>> numberPossibilitiesRow = new ArrayList<>(sizeRow[i][j]);
            for (int k = hintRowIndex[i][j] + 1; k < hintRowIndex[i][j] + sizeRow[i][j] + 1; k++) {
                numberPossibilitiesRow.add(numberPossibilities[i][k]);
            }
            ArrayList<Integer> result = new ArrayList<>();
            getHintPossibilitiesFromNumberPossibilitiesBacktrack(0, numberPossibilitiesRow, new ArrayList<>(sizeRow[i][j]), 0, result);
            hintRowPossibilities[i][hintRowIndex[i][j]] = result;
            if (hintRowPossibilities[i][hintRowIndex[i][j]].size() == 0) {
                return false;
            } else if (hintRowPossibilities[i][hintRowIndex[i][j]].size() == 1) {
                int value = hintRowPossibilities[i][hintRowIndex[i][j]].get(0);
                ((CasellaNegra) caselles[i][hintRowIndex[i][j]]).setpistaHorizontal(value);
            }
        }
        if (hintColPossibilities[hintColIndex[i][j]][j].size() != 1) {
            ArrayList<ArrayList<Integer>> numberPossibilitiesCol = new ArrayList<>(sizeCol[i][j]);
            for (int k = hintColIndex[i][j] + 1; k < hintColIndex[i][j] + sizeCol[i][j] + 1; k++) {
                numberPossibilitiesCol.add(numberPossibilities[k][j]);
            }
            ArrayList<Integer> result = new ArrayList<>();
            getHintPossibilitiesFromNumberPossibilitiesBacktrack(0, numberPossibilitiesCol, new ArrayList<>(sizeCol[i][j]), 0, result);
            hintColPossibilities[hintColIndex[i][j]][j] = result;
            if (hintColPossibilities[hintColIndex[i][j]][j].size() == 0) {
                return false;
            } else if (hintColPossibilities[hintColIndex[i][j]][j].size() == 1) {
                int value = hintColPossibilities[hintColIndex[i][j]][j].get(0);
                ((CasellaNegra) caselles[hintColIndex[i][j]][j]).setpistaVertical(value);
            }
        }
        return true;
    }

    private void generar_mig_patro() {
        for (int i = 0; i < nfiles - 1; i++) {
            for (int j = 0; j < ncolumnes / 2; j++) {
                mig_patro_negres[i][j] = false;
            }
        }
        blanques_actuals = (nfiles - 1) * (ncolumnes - 1);
        if (nfiles % 2 == 0 && ncolumnes % 2 == 0 && nblanques % 2 == 0) {
            mig_patro_negres[(nfiles - 1) / 2][(ncolumnes - 1) / 2] = true;
            blanques_actuals++;
        }
        // Evitar blocs de 9+ en fila
        for (int i = 0; i < nfiles - 1; i++) {
            int maxloops = 5;
            while (maxloops-- > 0 && fila_massa_llarga(i)) {
                int j = random.nextInt((ncolumnes - 1) / 2);
                ArrayList<Integer[]> foratList = omplir_forats_individuals(i, j, false);
                if (genera_illes()) {
                    reverteixForats(foratList);
                }
            }
        }
        // Evitar blocs de 9+ en columna
        for (int j = 0; j < (ncolumnes - 1) / 2; j++) {
            int maxloops = 5;
            while (maxloops-- > 0 && columna_massa_llarga(j)) {
                int i = random.nextInt(nfiles - 1);
                ArrayList<Integer[]> foratList = omplir_forats_individuals(i, j, false);
                if (genera_illes()) {
                    reverteixForats(foratList);
                }
            }
        }
        // Evitar blocs de 9+ en columna central
        if (ncolumnes % 2 == 0) {
            int maxloops = 5;
            while (maxloops-- > 0 && columna_massa_llarga((ncolumnes - 1) / 2)) {
                int i = random.nextInt((nfiles - 1) / 2 + 1);
                ArrayList<Integer[]> foratList = omplir_forats_individuals(i, (ncolumnes - 1) / 2, false);
                if (genera_illes()) {
                    reverteixForats(foratList);
                }
            }
        }
    }

    private void reverteixForats(ArrayList<Integer[]> foratList) {
        for (Integer[] forat : foratList) {
            mig_patro_negres[forat[0]][forat[1]] = false;
            blanques_actuals += 2;
        }
    }

    private ArrayList<Integer[]> omplir_forats_individuals(int i, int j, boolean obligat) {
        if (mig_patro_negres[i][j]) {
            return new ArrayList<>();
        }
        ArrayList<Integer[]> forats_individuals = negra_genera_forats_individuals(i, j);
        if (forats_individuals.size() == 0) {
            mig_patro_negres[i][j] = true;
            blanques_actuals -= 2;
            ArrayList<Integer[]> arrayList = new ArrayList<>(1);
            arrayList.add(new Integer[] {i, j});
            return arrayList;
        } else {
            // Decidir si omplir els dos o cap
            if (obligat || nblanques < blanques_actuals) {
                mig_patro_negres[i][j] = true;
                blanques_actuals -= 2;
                ArrayList<Integer[]> arrayList = new ArrayList<>(1);
                arrayList.add(new Integer[] {i, j});
                for (Integer[] forat : forats_individuals) {
                    ArrayList<Integer[]> foratList = omplir_forats_individuals(forat[0], forat[1], true);
                    arrayList.addAll(foratList);
                }
                return arrayList;
            }
            return new ArrayList<>();
        }
    }

    private boolean fila_massa_llarga(int fila) {
        int blanques = 0;
        for (int j = 0; j < ncolumnes - 1; j++) {
            if (j < ncolumnes / 2) {
                if (mig_patro_negres[fila][j]) {
                    blanques = 0;
                    continue;
                }
            } else {
                if (mig_patro_negres[fila][ncolumnes - j - 1]) {
                    blanques = 0;
                    continue;
                }
            }
            blanques++;
            if (blanques > 9) {
                return true;
            }
        }
        return false;
    }

    private boolean columna_massa_llarga(int columna) {
        int blanques = 0;
        for (int i = 0; i < nfiles - 1; i++) {
            if (mig_patro_negres[i][columna]) {
                blanques = 0;
                continue;
            }
            blanques++;
            if (blanques > 9) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer[]> negra_genera_forats_individuals(int i, int j) {
        // Forat vertical negre blanc actual
        ArrayList<Integer[]> forats_individuals = new ArrayList<>();
        boolean i_negre_abans = i >= 2 && mig_patro_negres[i - 2][j];
        if (i_negre_abans && !mig_patro_negres[i - 1][j]) {
            forats_individuals.add(new Integer[]{i - 1, j});
        }
        // Forat vertical actual blanc negre
        boolean i_negre_despres = i <= nfiles - 4 && mig_patro_negres[i + 2][j];
        if (i_negre_despres && !mig_patro_negres[i + 1][j]) {
            forats_individuals.add(new Integer[]{i + 1, j});
        }
        // Forat vertical lateral blanc actual
        if (i == 1 && !mig_patro_negres[i - 1][j]) {
            forats_individuals.add(new Integer[]{0, j});
        }
        // Forat vertical actual blanc fora
        if (i == nfiles - 3 && !mig_patro_negres[nfiles - 2][j]) {
            forats_individuals.add(new Integer[]{nfiles - 2, j});
        }
        // Forat horitzontal negre blanc actual
        boolean j_negre_abans = j >= 2 && mig_patro_negres[i][j - 2];
        if (j_negre_abans && !mig_patro_negres[i][j - 1]) {
            forats_individuals.add(new Integer[]{i, j - 1});
        }
        // Forat horitzontal actual blanc negre
        boolean j_negre_despres = j <= ncolumnes / 2 - 3 && mig_patro_negres[i][j + 2];
        if (j_negre_despres && !mig_patro_negres[i][j + 1]) {
            forats_individuals.add(new Integer[]{i, j + 1});
        }
        // Forat horitzontal lateral blanc actual
        if (j == 1 && !mig_patro_negres[i][j - 1]) {
            forats_individuals.add(new Integer[]{i, 0});
        }
        // Forat horitzontal actual blanc meitat negre
        boolean meitat_negre_despres = j == ncolumnes / 2 - 2 && mig_patro_negres[nfiles - i - 2][j];
        if (meitat_negre_despres && !mig_patro_negres[i][j + 1]) {
            if (i < (nfiles - 1) / 2) {
                forats_individuals.add(new Integer[]{i, j + 1});
            } else {
                forats_individuals.add(new Integer[]{nfiles - i - 2, j + 1});
            }
        }
        // Forat central
        boolean vertical_central = i == (nfiles - 1) / 2 && j == (ncolumnes - 1) / 2 - 1;
        boolean horitzontal_central = i == (nfiles - 1) / 2 - 1 && j == (ncolumnes - 1) / 2;
        boolean forat_central = vertical_central || horitzontal_central;
        if (forat_central && !mig_patro_negres[(nfiles - 1) / 2][(ncolumnes - 1) / 2]) {
            forats_individuals.add(new Integer[]{(nfiles - 1) / 2, (ncolumnes - 1) / 2});
        }
        return forats_individuals;
    }

    private boolean genera_illes() {
        per_visitar = new boolean[nfiles - 1][ncolumnes - 1];
        int primera_i = -1;
        int primera_j = -1;
        for (int i = 0; i < nfiles - 1; i++) {
            for (int j = 0; j < ncolumnes - 1; j++) {
                per_visitar[i][j] = !get_negra_patro(i, j);
                if (per_visitar[i][j] && primera_i == -1) {
                    primera_i = i;
                    primera_j = j;
                }
            }
        }
        visitar_veins_rec(primera_i, primera_j);
        for (int i = 0; i < nfiles - 1; i++) {
            for (int j = 0; j < ncolumnes - 1; j++) {
                if (per_visitar[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void visitar_veins_rec(int i, int j) {
        per_visitar[i][j] = false;
        if (i > 0 && per_visitar[i - 1][j]) {
            visitar_veins_rec(i - 1, j);
        }
        if (i < nfiles - 2 && per_visitar[i + 1][j]) {
            visitar_veins_rec(i + 1, j);
        }
        if (j > 0 && per_visitar[i][j - 1]) {
            visitar_veins_rec(i, j - 1);
        }
        if (j < ncolumnes - 2 && per_visitar[i][j + 1]) {
            visitar_veins_rec(i, j + 1);
        }
    }

    private boolean get_negra_patro(int i, int j) {
        // laterals
        if (i == 0 || j == 0) {
            return true;
        }
        // meitat esquerra
        if (j < ncolumnes / 2) {
            return mig_patro_negres[i - 1][j - 1];
        }
        if (ncolumnes % 2 == 0) {
            // meitat dreta
            if (j > ncolumnes / 2) {
                return mig_patro_negres[nfiles - i - 1][ncolumnes - j - 1];
            }
            // linia central superior
            if (i < nfiles / 2) {
                return mig_patro_negres[i - 1][j - 1];
            }
            // linia central mig i inferior
            return mig_patro_negres[nfiles - i - 1][ncolumnes - j - 1];
        } else {
            // meitat dreta
            if (j > ncolumnes / 2 + 1) {
                return mig_patro_negres[nfiles - i - 1][ncolumnes - j - 1];
            }
            // linia central superior
            if (i < nfiles / 2) {
                return mig_patro_negres[i - 1][j - 1];
            }
            // linia central mig i inferior
            return mig_patro_negres[nfiles - i - 1][ncolumnes - j - 1];
        }
    }

    private void generar_caselles_patro() {
        for (int i = 0; i < nfiles; i++) {
            for (int j = 0; j < ncolumnes; j++) {
                if (get_negra_patro(i, j)) {
                    caselles[i][j] = new CasellaNegra();
                } else {
                    caselles[i][j] = new CasellaBlanca();
                }
            }
        }
    }

    private boolean blanca_genera_forats_individuals(int i, int j) {
        boolean negra_i_neg = i >= 1 && mig_patro_negres[i - 1][j];
        if (negra_i_neg && negra_genera_forats_individuals(i - 1, j).size() > 0) {
            return true;
        }
        boolean negra_i_pos = i <= nfiles - 2 && mig_patro_negres[i + 1][j];
        if (negra_i_pos && negra_genera_forats_individuals(i + 1, j).size() > 0) {
            return true;
        }
        boolean negra_j_neg = j >= 1 && mig_patro_negres[i][j - 1];
        if (negra_j_neg && negra_genera_forats_individuals(i, j - 1).size() > 0) {
            return true;
        }
        boolean negra_j_pos = j <= ncolumnes / 2 - 1 && mig_patro_negres[i][j + 1];
        if (negra_j_pos && negra_genera_forats_individuals(i, j + 1).size() > 0) {
            return true;
        }
        return false;
    }

    private void augmentar_blanques() {
        ArrayList<Integer> random_i_s = new ArrayList<>(nfiles - 1);
        for (int i = 0; i < nfiles - 1; i++) {
            random_i_s.add(i);
        }
        ArrayList<Integer> random_j_s = new ArrayList<>((ncolumnes - 1) / 2);
        for (int j = 0; j < (ncolumnes - 1) / 2; j++) {
            random_j_s.add(j);
        }
        Collections.shuffle(random_i_s, random);
        Collections.shuffle(random_j_s, random);
        boolean break_flag = false;
        for (int i = 0; i < nfiles - 1; i++) {
            if (break_flag) {
                break;
            }
            for (int j = 0; j < (ncolumnes - 1) / 2; j++) {
                int random_i = random_i_s.get(i);
                int random_j = random_j_s.get(j);
                if (!mig_patro_negres[random_i][random_j]) {
                    continue;
                }
                mig_patro_negres[random_i][random_j] = false;
                blanques_actuals += 2;
                boolean invalida = blanca_genera_forats_individuals(random_i, random_j) || genera_illes();
                if (invalida || genera_illes() || fila_massa_llarga(random_i) || columna_massa_llarga(random_j)) {
                    mig_patro_negres[random_i][random_j] = true;
                    blanques_actuals -= 2;
                } else if (blanques_actuals >= nblanques) {
                    break_flag = true;
                    break;
                }
            }
        }
        if (blanques_actuals < nblanques && ncolumnes % 2 == 0) {
            Collections.shuffle(random_i_s, random);
            for (int i = 0; i < nfiles - 1; i++) {
                int random_i = random_i_s.get(i);
                if (!mig_patro_negres[random_i][(ncolumnes - 1) / 2]) {
                    continue;
                }
                mig_patro_negres[random_i][(ncolumnes - 1) / 2] = false;
                blanques_actuals += 2;
                boolean invalida = blanca_genera_forats_individuals(random_i, (ncolumnes - 1) / 2) || genera_illes();
                if (invalida || fila_massa_llarga(random_i) || columna_massa_llarga((ncolumnes - 1) / 2)) {
                    mig_patro_negres[random_i][(ncolumnes - 1) / 2] = true;
                    blanques_actuals -= 2;
                } else if (blanques_actuals >= nblanques) {
                    break_flag = true;
                    break;
                }
            }
        }
    }

    private void reduir_blanques() {
        ArrayList<Integer> random_i_s = new ArrayList<>(nfiles - 1);
        for (int i = 0; i < nfiles - 1; i++) {
            random_i_s.add(i);
        }
        ArrayList<Integer> random_j_s = new ArrayList<>((ncolumnes - 1) / 2);
        for (int j = 0; j < (ncolumnes - 1) / 2; j++) {
            random_j_s.add(j);
        }
        Collections.shuffle(random_i_s, random);
        Collections.shuffle(random_j_s, random);
        boolean break_flag = false;
        for (int i = 0; i < nfiles - 1; i++) {
            if (break_flag) {
                break;
            }
            for (int j = 0; j < (ncolumnes - 1) / 2; j++) {
                int random_i = random_i_s.get(i);
                int random_j = random_j_s.get(j);
                if (mig_patro_negres[random_i][random_j]) {
                    continue;
                }
                ArrayList<Integer[]> foratList = omplir_forats_individuals(random_i, random_j, false);
                if (genera_illes()) {
                    reverteixForats(foratList);
                } else if (blanques_actuals <= nblanques) {
                    break_flag = true;
                    break;
                }
            }
        }
        if (blanques_actuals > nblanques && ncolumnes % 2 == 0) {
            Collections.shuffle(random_i_s, random);
            for (int i = 0; i < nfiles - 1; i++) {
                int random_i = random_i_s.get(i);
                if (mig_patro_negres[random_i][(ncolumnes - 1) / 2]) {
                    continue;
                }
                ArrayList<Integer[]> foratList = omplir_forats_individuals(random_i, (ncolumnes - 1) / 2, false);
                if (genera_illes()) {
                    reverteixForats(foratList);
                } else if (blanques_actuals <= nblanques) {
                    break;
                }
            }
        }
    }

    private boolean repetitsFila(int i, int j, int num) {
        for (int k = j - 1; k >= 0 && caselles[i][k] instanceof CasellaBlanca; k--) {
            if (((CasellaBlanca) caselles[i][k]).consultar_valor_correcte() == num) {
                return true;
            }
        }
        return false;
    }

    private boolean repetitsColumna(int i, int j, int num) {
        for (int k = i - 1; k >= 0 && caselles[k][j] instanceof CasellaBlanca; k--) {
            if (((CasellaBlanca) caselles[k][j]).consultar_valor_correcte() == num) {
                return true;
            }
        }
        return false;
    }
}
