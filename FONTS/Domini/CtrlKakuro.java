package Domini;

public class CtrlKakuro {
    private Kakuro kakuro;

    public void createKakuro(int size) {
        Casella[][] cells = new Casella[size][size];
        kakuro = new Kakuro(cells);
    }

    public void setKakuro(Kakuro kakuro) {
        this.kakuro = kakuro;
    }

    public Kakuro getKakuro() {
        return kakuro;
    }

    public void setCellType(int i, int j, boolean isBlack) {
        Casella[][] cells = kakuro.consultar_caselles();
        if (isBlack) {
            cells[i][j] = new CasellaNegra();
        } else {
            cells[i][j] = new CasellaBlanca();
        }
        kakuro.escriure_caselles(cells);
    }

    public boolean isCellBlack(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        return cells[i][j] instanceof CasellaNegra;
    }

    public void setCellValue(int i, int j, int value) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaBlanca white = (CasellaBlanca) cells[i][j];
        white.escriure_valor(value);
        kakuro.escriure_caselles(cells);
    }

    public int getCellValue(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaBlanca white = (CasellaBlanca) cells[i][j];
        return white.consultar_valor();
    }

    public void setCellCorrect(int i, int j, int correct) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaBlanca white = (CasellaBlanca) cells[i][j];
        white.escriure_valor_correcte(correct);
        kakuro.escriure_caselles(cells);
    }

    public void setCellHints(int i, int j, int vertical, int horizontal) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaNegra black = (CasellaNegra) cells[i][j];
        black.setpistaVertical(vertical);
        black.setpistaHorizontal(horizontal);
        kakuro.escriure_caselles(cells);
    }

    public int getCellHintVertical(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaNegra black = (CasellaNegra) cells[i][j];
        return black.consultar_pistaVertical();
    }

    public int getCellHintHorizontal(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaNegra black = (CasellaNegra) cells[i][j];
        return black.consultar_pistaHorizontal();
    }

    public void solveCell(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaBlanca white = (CasellaBlanca) cells[i][j];
        white.escriure_correcte_a_valor();
        kakuro.escriure_caselles(cells);
    }

    public boolean checkCell(int i, int j) {
        Casella[][] cells = kakuro.consultar_caselles();
        CasellaBlanca white = (CasellaBlanca) cells[i][j];
        return white.consultar_valor() == white.consultar_valor_correcte();
    }
}
