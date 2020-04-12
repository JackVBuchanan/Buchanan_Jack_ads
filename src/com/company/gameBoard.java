package com.company;

import java.util.Stack;
import java.util.Random;

public class gameBoard {

    String[][] boardArray = getBoardDimensions();
    Stack<String> moveHistory = new Stack<String>();
    Stack<String> backupMoveHistory = new Stack<String>();

    private String[][] getBoardDimensions(){

        String height = config.getConfigData("boardDimensionsHeight");
        String length = config.getConfigData("boardDimensionsLength");
        int x = Integer.parseInt(height);
        int y = Integer.parseInt(length);

        return new String[x][y];
    }

    public void playerVsPlayer(String playerOption) {

        Player playerOne = new Player();
        Player playerTwo = new Player();
        playerOne.playerToken = config.getConfigData("playerOneToken");
        playerTwo.playerToken = config.getConfigData("playerTwoToken");

        System.out.println("Please enter player one's name");
        playerOne.name = System.console().readLine();
        if (playerOption.equals("pvc")) {
            System.out.println("Please enter the computer's name");
        } else {
            System.out.println("Please enter player two's name");
        }
        playerTwo.name = System.console().readLine();

        newGame();

        handleTurn(playerOne, playerTwo, playerOption);
    }

   void handleTurn(Player playerOne, Player playerTwo, String playerOption){

       Random rand = new Random();
       boolean gameOver = false;
       int counter = 0;
       int lengthX = boardArray.length-1;

       do {
           counter++;
           display();
           System.out.println(playerOne.name + ", please take your turn");
           playerOne.playerTurn = System.console().readLine();
           handleMove(playerOne.playerTurn, playerOne.playerToken);

           if (playerOption.equals("pvc")) {
               display();
               int randomNum = rand.nextInt(lengthX);
               playerTwo.playerTurn = String.valueOf(randomNum);
               System.out.println(playerTwo.name + " is thinking...." + randomNum);
               display.handleWait(1000);
               handleMove(playerTwo.playerTurn, playerTwo.playerToken);
           } else {
               display();
               System.out.println(playerTwo.name + ", please take your turn");
               playerTwo.playerTurn = System.console().readLine();
               handleMove(playerTwo.playerTurn, playerTwo.playerToken);
           }
           if (counter > 42) {
               gameOver = true;
           }
       }while (!gameOver);
   }

    void retakeTurn(String token) {

        System.out.println("That was not a valid move.... try again");
        String column = System.console().readLine();
        handleMove(column, token);
    }

    void handleUndo(String token){

        int index = moveHistory.size()-1;

        if(index <= 0){
            retakeTurn(token);
        }

        if(backupMoveHistory.empty() || moveHistory.size() > backupMoveHistory.size()){
            backupMoveHistory.addAll(moveHistory);
        }

        String undoMove = moveHistory.get(index);
        String[] split = undoMove.split(",");

        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        if(split[2].equals("popEnd")){
            while(!moveHistory.get(index).contains("popStart")){
                moveHistory.remove(index);
                index--;
            }
            moveHistory.remove(index);
            for (int i = 0; i < moveHistory.size(); i++) {
                undoMove = moveHistory.get(i);
                split = undoMove.split(",");
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
                boardArray[x][y] = split[2];
            }
        }else{
            moveHistory.remove(index-1);
            boardArray[x][y] = " ";
        }
    }

    void handleRedo(String token){

        int index = moveHistory.size();

        String undoMove = backupMoveHistory.get(index);
        String[] split = undoMove.split(",");

        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        moveHistory.push(undoMove);
        boardArray[x][y] = token;
    }

    void handlePop(String[] column, String token){
        try{
            int y = Integer.parseInt(column[1]);
            //Vertical check
            String move = "0,0,popStart";
            moveHistory.push(move);
            for (int x = boardArray.length-1; x > 0; x--) {
                boardArray[x][y] = boardArray[x-1][y];
                move = x + "," + y + "," + token;
                moveHistory.push(move);
            }
            moveHistory.push("0,0,popEnd");
            boardArray[0][y] = " ";
            boardScan(token);
        }catch(ArrayIndexOutOfBoundsException ignored){

        }catch (NumberFormatException exception){
            retakeTurn(token);
        }
    }

    void handleMove(String column, String token) {

        if(column.equals("undo")){
            handleUndo(token);
            return;
        }else if(column.equals("redo")){
            handleRedo(token);
            return;
        }

        if(column.contains("PopOut")){
            String[] popSplit = column.split(" ");
            handlePop(popSplit, token);
            return;
        }

        int y = 0;
        int x = 0;
        int lastRow = boardArray.length - 1;

        try {
            y = Integer.parseInt(column);
            if (boardArray[lastRow][y] == " ") {
                boardArray[lastRow][y] = token;
                String move = lastRow + "," + y + "," + token;
                moveHistory.push(move);
                boardScan(token);
            } else {
                for (int i = 0; i < boardArray.length; i++) {
                    if (!boardArray[i][y].equals(" ")) {
                        x = i;
                        x--;
                        boardArray[x][y] = token;
                        String move = x + "," + y + "," + token;
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

        display.clear();
        display.displayHeader();

        for (int x = 0; x < boardArray.length; x++) {
            StringBuilder row = new StringBuilder();
            for (int y = 0; y < boardArray[0].length; y++) {
                if (y > 9) {
                    row.append("[ " + boardArray[x][y] + "] ");
                } else {
                    row.append("[" + boardArray[x][y] + "] ");
                }
            }
            System.out.println(row);
        }
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < boardArray[0].length; i++) {
            columns.append("[" + i + "] ");
        }

        System.out.println(columns);
        if (config.getConfigData("gameMode").equals("PopOut")) {
            StringBuilder popColumns = new StringBuilder();
            String bottomToken = config.getConfigData("boardDimensionsHeight");
            int num = Integer.parseInt(bottomToken);
            for (int i = 0; i < boardArray[0].length; i++) {
                if (boardArray[num - 1][i].equals(getPlayerTurn())) {
                    popColumns.append(" V  ");
                } else {
                    popColumns.append("    ");
                }
            }
            System.out.println(popColumns + "\n->To pop type: pop <number>");
        }
        System.out.println("\n->To undo last turn type: undo\n->To redo last undo type: redo\n");
    }

    String getPlayerTurn(){

        if (moveHistory.size() % 2 == 0){
            return config.getConfigData("playerOneToken");
        }else{
            return config.getConfigData("playerTwoToken");
        }
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

    void actionReplay() {
        newGame();
        int i = 0;

        while (i < moveHistory.size()) {

            moveHistory.get(i);
            String undoMove = moveHistory.get(i);
            String[] split = undoMove.split(",");

            boardArray[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = split[2];
            i++;
            display();
            display.handleWait(1000);
        }
        handleWin();
    }

    void handleWin(){

        display();
        System.out.println("\nWinrar!");


        System.out.println("\n0 - Home\n1 - Action Replay\n");
        String option = System.console().readLine();

        switch(option) {
            case "0":
                display.startup();
                Main.homePage();
                break;
            case "1":
                actionReplay();
                break;
        }
    }
}
