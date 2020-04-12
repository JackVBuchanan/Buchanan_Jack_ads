package com.company;

import java.io.*;
import java.util.Properties;

public class config {

    static void saveSetting(String key, String value){

        try {
            String basePath = System.getProperty("user.dir");
            basePath = basePath.split("connect4")[0];

            FileInputStream in = new FileInputStream(basePath + "connect4/config.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(basePath + "connect4/config.properties");
            props.setProperty(key, value);
            props.store(out, null);
            out.close();
        }catch(IOException e){
            System.out.println("Fail");
        }
    }

    static void displayConfig(){

        display.clear();
        display.displayHeader();
        String optionsDisplay = "--Settings--\n\n" +
                "[0] - Game Board size\n" +
                "[1] - Player tokens\n" +
                "[2] - Game mode\n" +
                "[3] - Home\n";
        System.out.println(optionsDisplay);

        String option = System.console().readLine();

        switch(option) {
            case "0":
                try {
                    System.out.println("Please enter the desired height of the board");
                    String height = System.console().readLine();
                    int tmp = Integer.parseInt(height);
                    saveSetting("boardDimensionsHeight", height);

                    System.out.println("Please enter the desired length of the board");
                    String length = System.console().readLine();
                    tmp = Integer.parseInt(length);
                    saveSetting("boardDimensionsLength", length);
                }
                catch(Exception e) {
                    System.out.print("Type a Number, Not String");
                }
                System.out.println("Saving settings...");
                display.handleWait(1000);
                displayConfig();
            case "1":
                System.out.println("Please enter the desired token of player one");
                String playerOneToken = System.console().readLine();
                saveSetting("playerOneToken", playerOneToken);

                System.out.println("Please enter the desired token of player two");
                String playerTwoToken = System.console().readLine();
                saveSetting("playerTwoToken", playerTwoToken);

                System.out.println("Saving settings...");
                display.handleWait(1000);
                displayConfig();
            case "2":
                System.out.println("Please pick a game mode:\n\n" + "[0] - Connect 4\n[1] - PopOut\n\nCurrent game mode: " + getConfigData("gameMode"));
                String gameMode = System.console().readLine();
                if(gameMode.equals("0")){
                    saveSetting("gameMode", "Connect4");
                }else{
                    saveSetting("gameMode", "PopOut");
                }
                System.out.println("Saving settings...");
                display.handleWait(1000);
                displayConfig();
                break;
            case "3":
                display.clear();
                display.startup();
                Main.homePage();
        }
    }

    public static String getConfigData(String key){
        String value = "";
        try {
            String basePath = System.getProperty("user.dir");
            basePath = basePath.split("connect4")[0];

            FileInputStream in = new FileInputStream(basePath + "connect4/config.properties");
            Properties props = new Properties();
            props.load(in);
            value = props.getProperty(key);
            in.close();
        } catch (IOException e) {
            System.out.println("Fail");
        }
        return value;
    }
}
