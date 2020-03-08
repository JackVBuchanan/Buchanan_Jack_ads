package com.company;

import java.util.Stack;

public class gameBoard {

    String[][] boardArray = new String[6][7];
    Stack<String> moveHistory = new Stack<String>();

    public void playerVsPlayer() {

        Player playerOne = new Player();
        Player playerTwo = new Player();
        playerOne.playerToken = "O";
        playerTwo.playerToken = "X";

        System.out.println("Please Enter player one's name");
        playerOne.name = System.console().readLine();

        System.out.println("Please Enter player two's name");
        playerTwo.name = System.console().readLine();

        newGame();

        boolean gameOver = false;
        int counter = 0;
        String token = "X";
        boardScan(token);

        do {
            counter++;
            display();
            System.out.println(playerOne.name + ", please take your turn");
            playerOne.playerTurn = System.console().readLine();
            handleTurn(playerOne.playerTurn, playerOne.playerToken);

            display();
            System.out.println(playerTwo.name + ", please take your turn");
            playerTwo.playerTurn = System.console().readLine();
            handleTurn(playerTwo.playerTurn, playerTwo.playerToken);

            if (counter > 42) {
                gameOver = true;
            }
        }
        while (!gameOver);
    }

    void retakeTurn(String token) {

        System.out.println("That was not a valid move.... try again");
        String column = System.console().readLine();
        handleTurn(column, token);
    }

    void handleTurn(String column, String token) {

        int y = 0;
        int x = 0;
        int lastRow = boardArray.length - 1;

        try {
            y = Integer.parseInt(column);
            if (boardArray[lastRow][y] == " ") {
                boardArray[lastRow][y] = token;
            } else {
                for (int i = 0; i < boardArray.length; i++) {
                    if (!boardArray[i][y].equals(" ")) {
                        x = i;
                        x--;
                        boardArray[x][y] = token;
                        String move = x + "," + y;
                        moveHistory.push(move);
                        boardScan(token);
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
            retakeTurn(token);
        }
    }

    void newGame() {

        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[0].length; y++) {
                boardArray[x][y] = " ";
            }
        }
    }

    void display() {

        Main options = new Main();
        options.clear();
        options.displayHeader();

        for (int x = 0; x < boardArray.length; x++) {
            StringBuilder row = new StringBuilder();
            for (int y = 0; y < boardArray[0].length; y++) {
                row.append("[" + boardArray[x][y] + "] ");
            }
            System.out.println(row);
        }
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < boardArray.length + 1; i++) {
            columns.append("[" + i + "] ");
        }

        System.out.println(columns + "\nPlease pick a column");
    }

    void boardScan(String token) {

        int winCount = 0;
        boolean limit = false;

        //Horizontal check
        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[0].length; y++) {
                if (boardArray[x][y] == token) {
                    winCount++;
                    if (winCount == 4) {
                        handleWin();
                    }
                } else {
                    winCount = 0;
                }
            }
        }

        //Vertical check
        for (int y = 0; y < boardArray[0].length; y++) {
            for (int x = 0; x < boardArray.length; x++) {
                if (boardArray[x][y] == token) {
                    winCount++;
                    if (winCount == 4) {
                        handleWin();
                    }
                } else {
                    winCount = 0;
                }
            }
        }

        //Uphill diagonal check
        for (int x = 0; x < boardArray.length; x++) {
            int i = x;
            int j = 0;
            limit = false;
            winCount = 0;
            do {
                try {
                    if (boardArray[i][j] == token) {
                        winCount++;
                        if (winCount == 4) {
                            handleWin();
                        }
                    } else {
                        winCount = 0;
                    }
                    i--;
                    j++;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    limit = true;
                }
            } while (!limit);

            if (x == boardArray.length - 1) {
                for (int y = 0; y < boardArray[0].length; y++) {
                    i = x;
                    j = y;
                    limit = false;
                    winCount = 0;
                    do {
                        try {
                            if (boardArray[i][j] == token) {
                                winCount++;
                                if (winCount == 4) {
                                    handleWin();
                                }
                            } else {
                                winCount = 0;
                            }
                            i--;
                            j++;
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            limit = true;
                        }
                    } while (!limit);
                }
            }
        }

        //Downhill diagonal check
        for (int x = boardArray.length-1; x > -1; x--) {
            int i = x;
            int j = 0;
            limit = false;
            winCount = 0;
            do {
                try {
                    if (boardArray[i][j] == token) {
                        winCount++;
                        if (winCount == 4) {
                            handleWin();
                        }
                    } else {
                        winCount = 0;
                    }
                    i++;
                    j++;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    limit = true;
                }
            } while (!limit);

            if (x == 0) {
                for (int y = 0; y < boardArray[0].length; y++) {
                    i = x;
                    j = y;
                    limit = false;
                    winCount = 0;
                    do {
                        try {
                            if (boardArray[i][j] == token) {
                                winCount++;
                                if (winCount == 4) {
                                    handleWin();
                                }
                            } else {
                                winCount = 0;
                            }
                            i++;
                            j++;
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            limit = true;
                        }
                    } while (!limit);
                }
            }
        }
    }

    void handleWin(){

        display();
        System.out.println("Winrar.... Same again?");
        String win = System.console().readLine();
        Main home = new Main();
        home.homePage();
    }
}
