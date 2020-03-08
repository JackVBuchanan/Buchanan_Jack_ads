package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class readConfig {

    public static void settings() {

        Main.clear();
        Main.displayHeader();

        String options = "[0] - Board options\n" +
                "[1] - Player options\n" +
                "[2] - Return to home\n\n" +
                "What Would you like to change?";

        System.out.println(options);
        setValues();
    }


    public static void setValues() {


        try (OutputStream output = new FileOutputStream("com/company/config.properties")) {

            Properties config = new Properties();

            String option = System.console().readLine();

            switch (option) {
                case "0":
                    System.out.println("Please select board height...");
                    String configValue = System.console().readLine();
                    config.setProperty("board.height", configValue);

                    System.out.println("Please select board width...");
                    configValue = System.console().readLine();
                    config.setProperty("board.width", configValue);

                    config.store(output, null);
                    settings();
                    break;
                case "1":
                    System.out.println("Please select player one's token...");
                    configValue = System.console().readLine();
                    config.setProperty("player.playerOneToken", configValue);

                    System.out.println("Please select player two's token...");
                    configValue = System.console().readLine();
                    config.setProperty("player.playerTwoToken", configValue);

                    config.store(output, null);
                    settings();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    System.out.println("You stupid?\n\n");
            }

        } catch (IOException io) {
            System.out.println("There's been a failure!");
            io.printStackTrace();
        }
    }
}

