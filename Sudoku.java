
import java.util.*;

public class Sudoku {
    int[][] mat;
    static int N; // number of columns/rows.
    int SRN; // square root of N
    int K; // No. Of missing digits

    // Constructor
    Sudoku(int N, int K) {
        this.N = N;
        this.K = K;

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];
    }

    // Sudoku Generator
    public void fillValues() {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void fillDiagonal() {

        for (int i = 0; i < N; i = i + SRN)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
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

    // Fill a 3 x 3 matrix.
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

    // Random generator
    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % SRN, j - j % SRN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j) {
        // System.out.println(i+" "+j);
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

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits() {
        int count = K;
        while (count != 0) {
            int cellId = randomGenerator(N * N) - 1;

            // System.out.println(cellId);
            // extract coordinates i and j
            int i = (cellId / N);
            int j = cellId % 9;
            if (j != 0)
                j = j - 1;

            // System.out.println(i+" "+j);
            if (mat[i][j] != 0) {
                count--;
                mat[i][j] = 0;
            }
        }
    }

    // Print sudoku
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

    /*
     * Takes a partially filled-in grid and attempts
     * to assign values to all unassigned locations in
     * such a way to meet the requirements for
     * Sudoku solution (non-duplication across rows,
     * columns, and boxes)
     */
    static boolean solveSudoku(int grid[][], int row,
            int col) {

        /*
         * if we have reached the 8th
         * row and 9th column (0
         * indexed matrix) ,
         * we are returning true to avoid further
         * backtracking
         */
        if (row == N - 1 && col == N)
            return true;

        // Check if column value becomes 9 ,
        // we move to next row
        // and column start from 0
        if (col == N) {
            row++;
            col = 0;
        }

        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1);

        for (int num = 1; num < 10; num++) {

            // Check if it is safe to place
            // the num (1-9) in the
            // given row ,col ->we move to next column
            if (isSafe(grid, row, col, num)) {

                /*
                 * assigning the num in the current
                 * (row,col) position of the grid and
                 * assuming our assigned num in the position
                 * is correct
                 */
                grid[row][col] = num;

                // Checking for next
                // possibility with next column
                if (solveSudoku(grid, row, col + 1))
                    return true;
            }
            /*
             * removing the assigned num , since our
             * assumption was wrong , and we go for next
             * assumption with diff num value
             */
            grid[row][col] = 0;
        }
        return false;
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    static boolean isSafe(int[][] grid, int row, int col,
            int num) {

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (grid[row][x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (grid[x][col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i + startRow][j + startCol] == num)
                    return false;

        return true;
    }

    // Driver code
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
