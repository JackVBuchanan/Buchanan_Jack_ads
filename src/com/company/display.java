package com.company;

public class display {

    static void displayHeader(){

        //text gen: http://patorjk.com/software/taag/#p=display&f=Big&t=Type%20Something%20
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

    static void handleWait(int time){
        try
        {
            Thread.sleep(time);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
