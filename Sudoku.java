
import java.util.*;

public class Sudoku {
    int[][] mat;
    static int N;
    int SRN;
    int K;

    Sudoku(int N, int K) {
        this.N = N;
        this.K = K;

        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];
    }

    // Sudoku Generator
    public void fillValues() {
        fillDiagonal();

        fillRemaining(0, SRN);

        removeKDigits();
    }

    void fillDiagonal() {

        for (int i = 0; i < N; i = i + SRN)

            fillBox(i, i);
    }

    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                if (mat[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                do {
                    num = randomGenerator(N);
                } while (!unUsedInBox(row, col, num));

                mat[row + i][col + j] = num;
            }
        }
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    boolean CheckIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % SRN, j - j % SRN, num));
    }

    boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j) {
        if (j >= N && i < N - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= N && j >= N)
            return true;

        if (i < SRN) {
            if (j < SRN)
                j = SRN;
        } else if (i < N - SRN) {
            if (j == (int) (i / SRN) * SRN)
                j = j + SRN;
        } else {
            if (j == N - SRN) {
                i = i + 1;
                j = 0;
                if (i >= N)
                    return true;
            }
        }

        for (int num = 1; num <= N; num++) {
            if (CheckIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    public void removeKDigits() {
        int count = K;
        while (count != 0) {
            int cellId = randomGenerator(N * N) - 1;

            int i = (cellId / N);
            int j = cellId % 9;
            if (j != 0)
                j = j - 1;

            if (mat[i][j] != 0) {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    public void printSudoku() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] == 0) {
                    System.out.print("- ");
                } else {
                    System.out.print(mat[i][j] + " ");
                }
                if ((j + 1) % SRN == 0) {
                    System.out.print("| ");
                }
            }
            if ((i + 1) % SRN == 0) {
                System.out.println();
                for (int x = 0; x < SRN; x++) {
                    System.out.print("--------");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean solveSudoku(int mat[][], int row,
            int col) {

        if (row == N - 1 && col == N)
            return true;

        // Check if column value becomes 9 ,we move to next row and column start from 0
        if (col == N) {
            row++;
            col = 0;
        }
        if (mat[row][col] != 0)
            return solveSudoku(mat, row, col + 1);

        for (int num = 1; num < 10; num++) {
            if (isSafe(mat, row, col, num)) {

                mat[row][col] = num;

                if (solveSudoku(mat, row, col + 1))
                    return true;
            }
            mat[row][col] = 0;
        }
        return false;
    }

    static boolean isSafe(int[][] mat, int row, int col,
            int num) {

        for (int x = 0; x <= 8; x++)
            if (mat[row][x] == num)
                return false;
        for (int x = 0; x <= 8; x++)
            if (mat[x][col] == num)
                return false;

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (mat[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }

    public static void main(String[] args) {
        int N = 9;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose level of Hardness: ");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        int n = 0;
        int K = 10;
        do {
            n = sc.nextInt();
            if (n == 1) {
                K = 20;
                break;
            } else if (n == 2) {
                K = 30;
                break;
            } else if (n == 3) {
                K = 40;
                break;
            }
            System.out.println("Enter a valid number");
        } while (n > 3 || n < 1);
        System.out.println("Unsolved sudoku");
        Sudoku sudoku = new Sudoku(N, K);
        sudoku.fillValues();
        sudoku.printSudoku();
        System.out.println("Press Enter for the Solution...");
        sc.nextLine();
        sc.nextLine();
        System.out.println("Solved Sudoku:");
        if (solveSudoku(sudoku.mat, 0, 0))
            sudoku.printSudoku();
        else
            System.out.println("No Solution exists");
    }
}
