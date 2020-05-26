package tictactoe;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static String[][] gameBoard = {
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}
    };

    public static State currentState = State.X_TURN;
    public static boolean isRunning = true;

    public static void main(String[] args) {
        printGameBoard();
        while (isRunning) {
            gameRuns(currentState);
        }
    }

    enum State {
        X_TURN, O_TURN, HAS_WINNER, DRAW
    }

    public static void gameRuns(State state) {
        switch (state) {
            case X_TURN:
                getNextMove();
                printGameBoard();
                checkStateOfBoard();
                if (currentState != State.HAS_WINNER && currentState != State.DRAW) {
                    currentState = State.O_TURN;
                }
                break;
            case O_TURN:
                getNextMove();
                printGameBoard();
                checkStateOfBoard();
                if (currentState != State.HAS_WINNER && currentState != State.DRAW) {
                    currentState = State.X_TURN;
                }
                break;
            case HAS_WINNER:
                if (xWins) {
                    System.out.println("X wins");
                } else {
                    System.out.println("O wins");
                }
                isRunning = false;
                break;
            case DRAW:
                System.out.println("Draw");
                isRunning = false;
                break;
        }
    }

    public static void getNextMove() {
        boolean hasPlayedValidMove = false;

        while (!hasPlayedValidMove) {
            System.out.println("Enter the coordinates:");
            String[] moveInput = scanner.nextLine().trim().split(" ");
            if (moveInput[0].length() > 1 || moveInput[1].length() > 1) {
                printNeedsNumbers();
                continue;
            }

            int coord1, coord2;
            try {
                coord1 = Integer.parseInt(moveInput[0]);
                coord2 = Integer.parseInt(moveInput[1]);
            }
            catch (NumberFormatException nfe) {
                printNeedsNumbers();
                continue;
            }

            if (coord1 > 3 || coord2 > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            int coord1Index = 0;
            if (coord1 == 1) {
                coord1Index = 0;
            }
            if (coord1 == 2) {
                coord1Index = 1;
            }
            if (coord1 == 3) {
                coord1Index = 2;
            }

            int coord2Index = 0;
            if (coord2 == 1) {
                coord2Index = 2;
            }
            if (coord2 == 2) {
                coord2Index = 1;
            }
            if (coord2 == 3) {
                coord2Index = 0;
            }

            if (gameBoard[coord2Index][coord1Index].equals(" ") && currentState == State.X_TURN) {
                gameBoard[coord2Index][coord1Index] = "X";
                hasPlayedValidMove = true;
            } else if (gameBoard[coord2Index][coord1Index].equals(" ") && currentState == State.O_TURN) {
                gameBoard[coord2Index][coord1Index] = "O";
                hasPlayedValidMove = true;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    public static void checkStateOfBoard() {
        checkForLines();
        if (xWins) {
            currentState = State.HAS_WINNER;
        } else if (oWins) {
            currentState = State.HAS_WINNER;
        } else if (!checkForEmptySpaces()) {
            currentState = State.DRAW;
        }
    }

    public static boolean xWins = false;
    public static boolean oWins = false;

    public static void checkForLines() {
        // Horizontal Case
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            if (gameBoard[rowIndex][0].equals(gameBoard[rowIndex][1]) && gameBoard[rowIndex][0].equals(gameBoard[rowIndex][2])) {
                if (checkForXLine(rowIndex, 0)) {
                    xWins = true;
                }
                if (checkForOLine(rowIndex, 0)) {
                    oWins = true;
                }
            }
        }

        // Vertical Case
        for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
            if (gameBoard[0][columnIndex].equals(gameBoard[1][columnIndex]) && gameBoard[0][columnIndex].equals(gameBoard[2][columnIndex])) {
                if (checkForXLine(0, columnIndex)) {
                    xWins = true;
                }
                if (checkForOLine(0, columnIndex)) {
                    oWins = true;
                }
            }
        }

        // Main Diagonal Case
        if (gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[0][0].equals(gameBoard[2][2])) {
            if (checkForXLine(0, 0)) {
                xWins = true;
            }
            if (checkForOLine(0, 0)) {
                oWins = true;
            }
        }

        // Anti Diagonal Case
        if (gameBoard[2][0].equals(gameBoard[1][1]) && gameBoard[2][0].equals(gameBoard[0][2])) {
            if (checkForXLine(2, 0)) {
                xWins = true;
            }
            if (checkForOLine(2, 0)) {
                oWins = true;
            }
        }
    }

    public static boolean xLine = false;
    public static boolean oLine = false;

    public static boolean checkForXLine(int rowIndex, int columnIndex) {
        if (gameBoard[rowIndex][columnIndex].equals("X")) {
            xLine = true;
        }
        return xLine;
    }

    public static boolean checkForOLine(int rowIndex, int columnIndex) {
        if (gameBoard[rowIndex][columnIndex].equals("O")) {
            oLine = true;
        }
        return oLine;
    }

    public static boolean checkForEmptySpaces() {
        boolean hasEmptySpaces = false;
        int spaceCounter = 0;
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
                if (gameBoard[rowIndex][columnIndex].equals(" ")) {
                    spaceCounter += 1;
                }
            }
        }
        if (spaceCounter > 0) {
            hasEmptySpaces = true;
        }
        return hasEmptySpaces;
    }

    public static void printGameBoard() {
        System.out.println("---------");
        for (String[] arrays : gameBoard) {
            System.out.print("| ");
            for (int index = 0; index < 3; index++) {
                System.out.print(arrays[index] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }
    public static void printNeedsNumbers() {
        System.out.println("You should enter numbers!");
    }
}
