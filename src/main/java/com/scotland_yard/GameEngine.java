package com.scotland_yard;

import com.scotland_yard.AI.Detective_AI;
import com.scotland_yard.AI.Mr_X_AI;
import com.scotland_yard.classes.*;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.Utilities.GameLogger;
import com.scotland_yard.classes.Utilities.RandomGenerator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class GameEngine {

    private static final int MAX_TURN = 24;
    private static final Map map = new Map();
    public static final ArrayList<Integer> mrXRevealRounds = new ArrayList<>() {{
        add(3);
        add(8);
        add(13);
        add(18);
        add(24);
    }};
    public static CountDownLatch moveLatch = new CountDownLatch(1);
    public static CountDownLatch detectiveLatch;
    private static boolean userClosed;
    private final ArrayList<Location> randomStarts = new ArrayList<Location>(map.getRandomStarts());
    private final ArrayList<Detective> detectives = new ArrayList<>(5);
    private final boolean AIGame;
    private final GameLogger logger;
    private Mr_X mr_x;
    private boolean detectivesCanMove = true;
    private int roundCounter = 0;
    private boolean mr_X_captured = false;

    public GameEngine(boolean AIGame, GameLogger logger) {
        this.AIGame = AIGame;
        this.logger = logger;
        userClosed = false;
        setupDetectives();
        setUpMr_X();
        this.randomStarts.clear();
    }

    public static void setUserClosedGame() {
        userClosed = true;
    }

    public Pair<Boolean, Integer> start() {
        logStartingPositions();
        runGame();
        logger.logFinalRound(roundCounter);
        return new Pair<>(mr_X_captured, roundCounter);
    }

    private void runGame() {
        do {
            this.roundCounter++;
            if (!AIGame) {
                updateUIHistoryLog("\nRound " + roundCounter + "\n-------------------------------------------\n");
            }

            this.logger.logRound(roundCounter);
            moveMr_X();
            updateUIMR_XLocationLbl();
            checkCapture();
            if (detectivesCanMove && !mr_X_captured) {
                moveDetectives();
                if (!AIGame) {
                    waitForMoveButtonAction();
                }
                checkCapture();
            }
        } while (detectivesCanMove && roundCounter < MAX_TURN && !mr_X_captured && !userClosed);
    }

    private void logStartingPositions() {
        logger.logMr_X_startPosition(mr_x.getCurrentLocation());
        for (Detective detective : this.detectives) {
            logger.logStartPositions(detective);
        }
    }

    private void setupDetectives() {
        Color[] colours = Detective.getAvailableColours();
        StringBuilder string = new StringBuilder();
        string.append("Starting Locations:\n-------------------------------------------\n");
        for (int i = 0; i < colours.length; i++) {
            Location randomStartPos = this.randomStarts.get(RandomGenerator.getInt(this.randomStarts.size()));
            this.randomStarts.remove(randomStartPos);
            Detective newDetective = new Detective(colours[i], randomStartPos);
            detectives.add(newDetective);
            string.append(detectives.get(detectives.size() - 1)).append(": ").append(randomStartPos.getNumber()).append("\n");
            if (!AIGame){
                updateUIDetectiveTickets(newDetective);
            }
        }
        updateUIHistoryLog(string.toString());
    }

    private void setUpMr_X() {
        Location randomStartPos = this.randomStarts.get(RandomGenerator.getInt(this.randomStarts.size()));
        this.mr_x = new Mr_X(Mr_X.getColour(), randomStartPos);
    }

    private void moveMr_X() {
        HashMap<Location, ArrayList<TransportType>> availableMovesHashMap = mr_x.getAvailableMoves(this.detectives);

        Pair<Location, Ticket> playedMove = Mr_X_AI.getMove(this.mr_x, availableMovesHashMap);
        if (playedMove.getValue().equals(Ticket.CONCEALED) || playedMove.getValue().equals(Ticket.DOUBLE)) {
            mr_x.removeTicket(playedMove.getValue());
        }

        this.mr_x.setLocation(playedMove.getKey());
        logger.logMove(this.mr_x, playedMove.getKey(), playedMove.getValue());
        updateUIMr_XTicketLog(playedMove);

        if (!AIGame && mrXRevealRounds.contains(this.roundCounter) && playedMove.getKey() != null) {
            updateUIHistoryLog(mr_x.toString() + " moved to " + mr_x.getCurrentLocation().getNumber() + " by " + playedMove.getValue() + "\n");
        }
    }

    private void moveDetectives() {
        if (AIGame) {
            // AI Game
            detectiveAIMove();
        } else {
            // Player Game
            detectivePlayerMove();
        }
    }

    private void detectiveAIMove() {
        Pair<Location, Ticket> playedMove;
        int detectiveCantMoveCount = 0;
        if (roundCounter == 1){
            Detective_AI.getFirstMoves(detectives);
        }

        for (Detective currentDetective : detectives) {
            HashMap<Location, ArrayList<TransportType>> availableMovesHashMap = currentDetective.getAvailableMoves(this.detectives);
            if (availableMovesHashMap.keySet().size() == 0) {
                detectiveCantMoveCount++;
            }
            playedMove = Detective_AI.getMove(currentDetective, availableMovesHashMap, this.roundCounter, mr_x);
            detectiveMoveChecks(currentDetective, playedMove, detectiveCantMoveCount);
        }
    }

    private void detectivePlayerMove() {
        int detectiveCantMoveCount = 0;
        for (Detective currentDetective : detectives) {
            HashMap<Location, ArrayList<TransportType>> availableMovesHashMap = currentDetective.getAvailableMoves(this.detectives);
            Pair<Location, Ticket> playedMove = new Pair<>(null, null);
            if (availableMovesHashMap.keySet().size() == 0) {
                detectiveCantMoveCount++;
            }
            if (availableMovesHashMap.keySet().size() > 0) {
                if (!userClosed) {
                    detectiveLatch = new CountDownLatch(1);
                    updateUIPlayerMoves(currentDetective, availableMovesHashMap);

                    try {
                        detectiveLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (!userClosed) {
                        String temp = (String) Main.gameController.comboBox.getValue();
                        String[] test = temp.split(" ");
                        Location newLocation = map.getNodeByNumber(Integer.parseInt(test[0]));
                        Ticket usedTicket = TransportUtilities.transportTypeToTicket(TransportUtilities.getTransportType(test[1].toLowerCase()));
                        playedMove = new Pair<>(newLocation, usedTicket);
                    }
                }
            }
            detectiveMoveChecks(currentDetective, playedMove, detectiveCantMoveCount);
            updateUIHistoryLog(currentDetective + " moved to " + currentDetective.getCurrentLocation().getNumber() + " by " + playedMove.getValue() + "\n");
            updateUIDetectiveTickets(currentDetective);
        }
    }

    private void detectiveMoveChecks(Detective detective, Pair<Location, Ticket> playedMove, int detectiveCantMoveCount) {
        logger.logMove(detective, playedMove.getKey(), playedMove.getValue());

        if (playedMove.getKey() != null) {
            detective.removeTicket(playedMove.getValue());
            detective.setLocation(playedMove.getKey());
        }

        if (detectiveCantMoveCount == detectives.size()) {
            this.detectivesCanMove = false;
        }
    }

    private void checkCapture() {
        for (Detective detective : this.detectives) {
            if (detective.getCurrentLocation().equals(mr_x.getCurrentLocation())) {
                this.mr_X_captured = true;
                logger.logCapture(detective);
                break;
            }
        }
    }

    private void waitForMoveButtonAction() {
        try {
            moveLatch.await();                      // Wait until latch count reaches 0
            moveLatch = new CountDownLatch(1);      // Reset the latch for the next iteration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateUIMR_XLocationLbl() {
        if (!AIGame && mrXRevealRounds.contains(roundCounter)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Main.gameController.Mr_X_Locationlbl.setText("Mr. X Last Location: " + mr_x.getCurrentLocation().getNumber());
                }
            });
        }
    }

    private void updateUIHistoryLog(String string) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.historyLog.appendText(string);
            }
        });
    }

    private void updateUIDetectiveTickets(Detective detective){
        StringBuilder string = new StringBuilder();
        string.append(detective.getRemainingTickets(Ticket.TAXI)).append("\n");
        string.append(detective.getRemainingTickets(Ticket.BUS)).append("\n");
        string.append(detective.getRemainingTickets(Ticket.UNDERGROUND));
        switch (detective.toString()) {
            case ("Blue") -> updateBlueTicketsLabel(string.toString());
            case ("Black") -> updateBlackTicketsLabel(string.toString());
            case ("Red") -> updateRedTicketsLabel(string.toString());
            case ("Green") -> updateGreenTicketsLabel(string.toString());
            case ("Yellow") -> updateYellowTicketsLabel(string.toString());
        }
    }

    private void updateBlueTicketsLabel(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.blueTicketslbl.setText(string);
            }
        });
    }

    private void updateBlackTicketsLabel(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.blackTicketslbl.setText(string);
            }
        });
    }

    private void updateRedTicketsLabel(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.redTicketsLbl.setText(string);
            }
        });
    }

    private void updateGreenTicketsLabel(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.greenTicketsLbl.setText(string);
            }
        });
    }

    private void updateYellowTicketsLabel(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.yellowTicketsLbl.setText(string);
            }
        });
    }

    private void updateUIMr_XTicketLog(Pair<Location, Ticket> playedMove) {
        if (!AIGame) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Main.gameController.mrXTicketLog.setText(Main.gameController.mrXTicketLog.getText() + "\nRound " + roundCounter + ": " + playedMove.getValue());
                }
            });
        }
    }

    private void updateUIPlayerMoves(Detective detective, HashMap<Location, ArrayList<TransportType>> availableMovesHashMap) {
        ArrayList<String> movesList = new ArrayList<>();
        for (Location availableMove : availableMovesHashMap.keySet()) {
            for (TransportType transportType : availableMovesHashMap.get(availableMove)) {
                movesList.add(availableMove.getNumber() + " " + transportType);
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameController.detectiveLbl.setText(detective.toString());
                ObservableList<String> array = FXCollections.observableArrayList(movesList);
                Main.gameController.comboBox.setItems(array);
            }
        });
    }
}
