# SudokuGame

Welcome to the Sudoku Solver project! This is a Java program that can solve a given Sudoku puzzle. The program uses a backtracking algorithm to find a valid solution for the given puzzle.

## Table of Contents

- [Demo](#demo)
- [Usage](#usage)
- [Algorithm](#algorithm)
- [Choose Level of Hardness](#choose-level-of-hardness)
- [Contributing](#contributing)

## Demo

Here's how you can use the Sudoku Solver:

1. Run the program.
2. Choose the level of hardness (easy, medium, hard).
3. The program generates an unsolved Sudoku puzzle.
4. Press Enter to see the solved Sudoku puzzle.

## Usage

1. Compile the `Sudoku.java` file: `javac Sudoku.java`
2. Run the compiled Java program: `java Sudoku`

## Algorithm

The Sudoku Solver uses a backtracking algorithm to find a valid solution for the given puzzle. It recursively tries different numbers in empty cells and checks if they satisfy the rules of Sudoku. If a number doesn't work, the algorithm backtracks and tries a different number.

## Choose Level of Hardness

The program allows you to choose the level of hardness for the generated Sudoku puzzle:

- Easy: 20 missing digits
- Medium: 30 missing digits
- Hard: 40 missing digits

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for any improvements or bug fixes.

