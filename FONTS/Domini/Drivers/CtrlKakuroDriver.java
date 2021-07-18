package Domini.Drivers;

import Domini.CtrlKakuro;

import java.util.Scanner;

public class CtrlKakuroDriver {

    private static Scanner scanner;
    private static CtrlKakuro ctrlKakuro;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        ctrlKakuro = new CtrlKakuro();
        boolean loop = true;
        while (loop) {
            System.out.println("Escriu el numero de la funcio a provar, -1 per sortir:");
            System.out.println("1. createKakuro(int size)");
            System.out.println("2. setCellType(int i, int j, boolean isBlack)");
            System.out.println("3. isCellBlack(int i, int j)");
            System.out.println("4. setCellValue(int i, int j, int value)");
            System.out.println("5. getCellValue(int i, int j)");
            System.out.println("6. setCellCorrect(int i, int j, int correct)");
            System.out.println("7. setCellHints(int i, int j, int vertical, int horizontal)");
            System.out.println("8. getCellHintVertical(int i, int j)");
            System.out.println("9. getCellHintHorizontal(int i, int j)");
            System.out.println("10. solveCell(int i, int j)");
            System.out.println("11. checkCell(int i, int j)");
            int num = scanner.nextInt();
            switch (num) {
                case 1: {
                    System.out.println("Escriu la mida del kakuro:");
                    int size = scanner.nextInt();
                    ctrlKakuro.createKakuro(size);
                    System.out.println("Kakuro creat correctament");
                    break;
                }
                case 2: {
                    testIsCellBlack();
                    break;
                }
                case 3: {
                    int[] rowColumn = readRowColumn();
                    boolean negra = ctrlKakuro.isCellBlack(rowColumn[0], rowColumn[1]);
                    System.out.println("La casella és " + (negra ? "negra" : "blanca"));
                    break;
                }
                case 4: {
                    int[] rowColumn = readRowColumn();
                    System.out.println("Escriu el nou valor:");
                    int value = scanner.nextInt();
                    ctrlKakuro.setCellValue(rowColumn[0], rowColumn[1], value);
                    System.out.println("Valor escrit correctament");
                    break;
                }
                case 5: {
                    int[] rowColumn = readRowColumn();
                    int value = ctrlKakuro.getCellValue(rowColumn[0], rowColumn[1]);
                    System.out.println("El valor és " + value);
                    break;
                }
                case 6: {
                    int[] rowColumn = readRowColumn();
                    System.out.println("Escriu el valor correcte:");
                    int correct = scanner.nextInt();
                    ctrlKakuro.setCellCorrect(rowColumn[0], rowColumn[1], correct);
                    System.out.println("Valor correcte escrit correctament");
                    break;
                }
                case 7: {
                    int[] rowColumn = readRowColumn();
                    System.out.println("Escriu la pista vertical:");
                    int vertical = scanner.nextInt();
                    System.out.println("Escriu la pista horitzontal:");
                    int horizontal = scanner.nextInt();
                    ctrlKakuro.setCellHints(rowColumn[0], rowColumn[1], vertical, horizontal);
                    System.out.println("Pistes escrites correctament");
                    break;
                }
                case 8: {
                    int[] rowColumn = readRowColumn();
                    int vertical = ctrlKakuro.getCellHintVertical(rowColumn[0], rowColumn[1]);
                    System.out.println("La pista vertical és " + vertical);
                    break;
                }
                case 9: {
                    int[] rowColumn = readRowColumn();
                    int horizontal = ctrlKakuro.getCellHintHorizontal(rowColumn[0], rowColumn[1]);
                    System.out.println("La pista horizontal és " + horizontal);
                    break;
                }
                case 10: {
                    int[] rowColumn = readRowColumn();
                    ctrlKakuro.solveCell(rowColumn[0], rowColumn[1]);
                    System.out.println("Casella solucionada");
                    break;
                }
                case 11: {
                    int[] rowColumn = readRowColumn();
                    boolean isCorrect = ctrlKakuro.checkCell(rowColumn[0], rowColumn[1]);
                    System.out.println("La casella és " + (isCorrect ? "correcta" : "incorrecta"));
                    break;
                }
                case -1: {
                    loop = false;
                    break;
                }
            }
        }
    }

    private static int[] readRowColumn() {
        int[] rowColumn = new int[2];
        System.out.println("Escriu la fila:");
        rowColumn[0] = scanner.nextInt();
        System.out.println("Escriu la columna:");
        rowColumn[1] = scanner.nextInt();
        return rowColumn;
    }

    private static void testIsCellBlack() {
        int[] rowColumn = readRowColumn();
        System.out.println("Vols que la casella sigui o blanca? (N/B)");
        String blackWhite = scanner.next();
        if (Character.toUpperCase(blackWhite.charAt(0)) == 'N') {
            ctrlKakuro.setCellType(rowColumn[0], rowColumn[1], true);
            System.out.println("Tipus de casella escrit correctament");
        } else if (Character.toUpperCase(blackWhite.charAt(0)) == 'B') {
            ctrlKakuro.setCellType(rowColumn[0], rowColumn[1], false);
            System.out.println("Tipus de casella escrit correctament");
        } else {
            System.out.println("Entrada incorrecta: Has d'escriure N o B");
        }
    }
}
