package com.scotland_yard;

import com.scotland_yard.classes.Utilities.GameLogger;
import javafx.application.Platform;
import javafx.util.Pair;

public class ScotlandYard {
    public static void startRound(int numOfRounds, boolean AIGame) {
        updateUIToGameScreen(AIGame);               // If players vs AI update UI to game screen
        GameLogger.createNewCSVFile(AIGame);        // Create new CSV file for current games
        resetHistoryLog();
        new Thread(() -> {                          // New thread to run the game separate from the UI
            int gameCounter = 0;
            int mr_X_winCount = 0;
            float roundsCaughtSum = 0;
            Pair<Boolean, Integer> result;          // For Command line output (Can be removed if command line not used)
            do {
                GameLogger logger = new GameLogger();                       // Initialise new GameLogger for current game
                GameEngine currentGame = new GameEngine(AIGame, logger);    // Initialise new game
                result = currentGame.start();                               // Run and store result from game
                if (!result.getKey()) {                                     // If not caught by detective increment Mr.X wins
                    mr_X_winCount++;
                }
                roundsCaughtSum += result.getValue();
                logger.writeDataToCSV();                            // write game results to CSV
                gameCounter++;
                System.out.println(gameCounter + "/" + numOfRounds + " Caught: " + result.getKey() + " Round: " + result.getValue());
            } while (gameCounter < numOfRounds);

            outputGameResults(mr_X_winCount, numOfRounds, roundsCaughtSum);
            updateUIToMainScreen();                                 // change the UI back to the main menu
        }).start();
    }

    private static void outputGameResults(int mr_X_winCount, int numOfRounds, float roundsCaughtSum) {
        // Output game results csv + cmd line
        GameLogger.close();
        System.out.println("Mr. X Won: " + mr_X_winCount + " | Lost: " + (numOfRounds - mr_X_winCount) + " | Avg Turn Caught: " + (roundsCaughtSum / numOfRounds));
    }

    private static void updateUIToGameScreen(boolean AIGame) {
        // Show game screen if its Players vs AI
        if (!AIGame) {
            Main.setGameScene();
        }
    }

    private static void resetHistoryLog() {
        Main.gameController.historyLog.clear();
    }

    private static void updateUIToMainScreen() {
        // Change UI back to main menu
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.mrXTicketLog.setText("Mr.X Ticket Log");
                Main.setMainScene();
            }
        });
    }
}