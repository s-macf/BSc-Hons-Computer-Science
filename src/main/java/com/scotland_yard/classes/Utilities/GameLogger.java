package com.scotland_yard.classes.Utilities;

import com.opencsv.CSVWriter;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.abstract_classes.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameLogger {

    private static final String FILEPATH_ROOT = "./game_results/";
    private static final String AI_FILE_PREFIX = "random_simulation_";
    private static final String PLAYER_FILE_PREFIX = "human_played_game_";
    private static CSVWriter csvWriter;
    private final StringBuilder moves = new StringBuilder();
    private final StringBuilder startingPositions = new StringBuilder();
    private Boolean mr_X_Caught = false;
    private String capturer;
    private Integer mr_x_startingPosition;
    private Integer finalRound;
    private String locationCaught;

    public static void createNewCSVFile(boolean AIGame) {
        String filePath;
        if (AIGame) {
            filePath = FILEPATH_ROOT + AI_FILE_PREFIX + getNextFileNumber(AI_FILE_PREFIX) + ".csv";
        } else {
            filePath = FILEPATH_ROOT + PLAYER_FILE_PREFIX + getNextFileNumber(PLAYER_FILE_PREFIX) + ".csv";
        }
        File file = new File(filePath);
        try {
            FileWriter outfile = new FileWriter(file);
            csvWriter = new CSVWriter(outfile);
            String[] headers = {
                    "Mr.X Caught",
                    "Caught By",
                    "Round Count",
                    "Mr.X Starting Position",
                    "Location Caught",
                    "Detective Starting Positions",
                    "Moves Played"
            };
            csvWriter.writeNext(headers);
        } catch (IOException ignored) {
        }
    }

    public static void close() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getNextFileNumber(String prefix) {
        int currentHighest = 0;
        List<String> res = listFilesInDirectory(FILEPATH_ROOT);
        for (String filename : res) {
            if (filename.equals("DO_NOT_DELETE.txt") || !filename.contains(prefix)) {
                continue;
            }
            filename = filename.replace(prefix, "");
            filename = filename.replace(".csv", "");
            int fileNum = Integer.parseInt(filename);
            if (fileNum > currentHighest) {
                currentHighest = fileNum;
            }
        }
        return currentHighest + 1;
    }

    private static List<String> listFilesInDirectory(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public void writeDataToCSV() {
        String[] data = {
                mr_X_Caught.toString(),
                capturer,
                finalRound.toString(),
                mr_x_startingPosition.toString(),
                locationCaught,
                startingPositions.toString(),
                moves.toString()
        };
        csvWriter.writeNext(data);
    }

    public void logMove(Player player, Location location, Ticket ticket) {
        if (location != null) {
            this.moves.append(player).append(" moved to ").append(location.getNumber()).append(" by ").append(ticket).append("\n");
        } else {
            this.moves.append(player).append(" is stuck on position ").append(player.getCurrentLocation().getNumber()).append("\n");
        }

    }

    public void logFinalRound(int round) {
        this.finalRound = round;
    }

    public void logRound(int round) {
        this.moves.append("\nRound ").append(round).append("\n");
    }

    public void logCapture(Player player) {
        this.capturer = player.toString();
        this.mr_X_Caught = true;
        this.locationCaught = player.getCurrentLocation().getNumber().toString();
    }

    public void logStartPositions(Player player) {
        this.startingPositions.append(player).append(" ").append(player.getCurrentLocation().getNumber()).append("\n");
        ;
    }

    public void logMr_X_startPosition(Location location) {
        this.mr_x_startingPosition = location.getNumber();
    }
}