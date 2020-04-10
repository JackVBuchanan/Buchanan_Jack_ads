package com.company;

 class Main {

    public static void main(String[] args) {
        startup();
        homePage();
    }

    static void homePage() {

        String option = System.console().readLine();

        switch(option) {
            case "0":
                gameBoard board = new gameBoard();
                board.playerVsPlayer();
                break;
            case "1":
                clear();
                startup();
                System.out.println("No AI yet....\n\n");
                homePage();
                break;
            case "2":
                clear();
                startup();
                System.out.println("Author: Jack Buchanan\n" +
                        "Project: Data Structures & Algorithms\n\n");
                homePage();
                break;
            case "3":
                clear();
                startup();
                System.out.println("Options page coming soon...\n\n");
                homePage();
                break;
            default:
                clear();
                startup();
                System.out.println("You stupid?...Try again\n\n");
                homePage();

        }
    }

    static void displayHeader(){

        System.out.println(
            "   _____                            _     ______               \n" +
            "  / ____|                          | |   |  ____|              \n" +
            " | |     ___  _ __  _ __   ___  ___| |_  | |__ ___  _   _ _ __ \n" +
            " | |    / _ \\| '_ \\| '_ \\ / _ \\/ __| __| |  __/ _ \\| | | | '__|\n" +
            " | |___| (_) | | | | | | |  __/ (__| |_  | | | (_) | |_| | |   \n" +
            "  \\_____\\___/|_| |_|_| |_|\\___|\\___|\\__| |_|  \\___/ \\__,_|_|   \n" +
            "                                                               \n" +
            "                                                              ");
}

    static void startup() {

        //text gen: http://patorjk.com/software/taag/#p=display&f=Big&t=Type%20Something%20

        clear();
        displayHeader();
        String options = "[0] - Player vs Player\n" +
                         "[1] - Player vs Computer\n" +
                         "[2] - About\n" +
                         "[3] - Options\n";

        System.out.println(options);
    }

    static void clear(){
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }
}
