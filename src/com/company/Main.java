package com.company;

 class Main {

    public static void main(String[] args) {
        //config.saveSetting("test","tset1234");
        //config.displayConfig();
        display.startup();
        homePage();
    }

    static void homePage() {

        String option = System.console().readLine();
        gameBoard board = new gameBoard();
        switch(option) {
            case "0":
                board.playerVsPlayer("pvp");
                break;
            case "1":
                board.playerVsPlayer("pvc");
                break;
            case "2":
                display.clear();
                display.startup();
                System.out.println("Author: Jack Buchanan\nProject: Data Structures & Algorithms\n\n");
                homePage();
                break;
            case "3":
                config.displayConfig();
                break;
            default:
                display.clear();
                display.startup();
                System.out.println("You stupid?...Try again\n\n");
                homePage();
        }
    }
}
