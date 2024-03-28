package com.scotland_yard.AI;

import com.scotland_yard.GameEngine;
import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import com.scotland_yard.classes.Utilities.RandomGenerator;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Detective_AI {
    private static HashMap<Detective, Pair<Location, Location>> firstMoves;

    public static Pair<Location, Ticket> getMove(Detective detective, HashMap<Location, ArrayList<TransportType>> availableMovesHashMap, int currentRound, Mr_X mr_x) {
        if (GameEngine.mrXRevealRounds.contains(currentRound)) {
            if (detective.getCurrentLocation().getNeighbours().contains(mr_x.getCurrentLocation()) && availableMovesHashMap.containsKey(mr_x.getCurrentLocation())) {
                ArrayList<TransportType> transportOptions = detective.getCurrentLocation().getNeighbourTransportMethods(mr_x.getCurrentLocation());
                int randInt = RandomGenerator.getInt(transportOptions.size());
                Ticket usedTicket = TransportUtilities.transportTypeToTicket(transportOptions.get(randInt));
                return new Pair<>(mr_x.getCurrentLocation(), usedTicket);
            }
        }

        if (currentRound == 1) {
            return new Pair<>(firstMoves.get(detective).getKey(), TransportUtilities.transportTypeToTicket(detective.getCurrentLocation().getNeighbourTransportMethods(firstMoves.get(detective).getKey()).get(0)));
        } else if (currentRound == 2) {
            return new Pair<>(firstMoves.get(detective).getValue(), TransportUtilities.transportTypeToTicket(detective.getCurrentLocation().getNeighbourTransportMethods(firstMoves.get(detective).getValue()).get(0)));
        }

        return getRandomMove(detective, availableMovesHashMap);
    }

    public static Pair<Location, Ticket> getRandomMove(Detective detective, HashMap<Location, ArrayList<TransportType>> availableMovesHashMap) {
        ArrayList<Location> availableMoves = new ArrayList<>(availableMovesHashMap.keySet());
        if (availableMoves.size() == 0) {
            return new Pair<>(null, null);
        }

        int randInt = RandomGenerator.getInt(availableMoves.size());
        Location newLocation = availableMoves.get(randInt);
        ArrayList<TransportType> transportOptions = detective.getCurrentLocation().getNeighbourTransportMethods(newLocation);

        randInt = RandomGenerator.getInt(transportOptions.size());
        Ticket usedTicket = TransportUtilities.transportTypeToTicket(transportOptions.get(randInt));

        return new Pair<>(newLocation, usedTicket);
    }


    // Starting Moves Logic
    public static HashMap<Detective, Pair<Location, Location>> getFirstMoves(ArrayList<Detective> detectives) {
        HashMap<Detective, HashSet<Pair<Location, Location>>> allFirstMoves = findAllFirstMoves(detectives);
        HashMap<Detective, Pair<Location, Location>> bestMoves = findNextBestMoves(detectives, allFirstMoves);

        firstMoves = bestMoves;
        return bestMoves;
    }

    protected static HashMap<Detective, Pair<Location, Location>> findNextBestMoves(ArrayList<Detective> detectives, HashMap<Detective, HashSet<Pair<Location, Location>>> allFirstMoves) {
        Detective blueDetective = getDetective(detectives, Color.BLUE);
        Detective blackDetective = getDetective(detectives, Color.BLACK);
        Detective redDetective = getDetective(detectives, Color.RED);
        Detective greenDetective = getDetective(detectives, Color.GREEN);
        Detective yellowDetective = getDetective(detectives, Color.YELLOW);


        int bestScore = 0;
        HashMap<Detective, Pair<Location, Location>> bestFirstMoves = new HashMap<>();
        for (Pair<Location, Location> blueMove : allFirstMoves.get(blueDetective)) {
            for (Pair<Location, Location> blackMove : allFirstMoves.get(blackDetective)) {
                if (checkConflicts(blackMove, blueMove)) {
                    continue;
                }
                for (Pair<Location, Location> redMove : allFirstMoves.get(redDetective)) {
                    if (checkConflicts(redMove, blueMove, blackMove)) {
                        continue;
                    }
                    for (Pair<Location, Location> greenMove : allFirstMoves.get(greenDetective)) {
                        if (checkConflicts(greenMove, blueMove, blackMove, redMove)) {
                            continue;
                        }
                        for (Pair<Location, Location> yellowMove : allFirstMoves.get(yellowDetective)) {
                            if (checkConflicts(yellowMove, blueMove, blackMove, redMove, greenMove)) {
                                continue;
                            }
                            int score = getScore(blueMove.getValue(), blackMove.getValue(), redMove.getValue(), greenMove.getValue(), yellowMove.getValue());
                            if (bestScore < score) {
                                bestScore = score;

                                bestFirstMoves.put(blueDetective, blueMove);
                                bestFirstMoves.put(blackDetective, blackMove);
                                bestFirstMoves.put(redDetective, redMove);
                                bestFirstMoves.put(greenDetective, greenMove);
                                bestFirstMoves.put(yellowDetective, yellowMove);
                            }
                        }
                    }
                }
            }
        }

        return bestFirstMoves;
    }

    private static boolean checkConflicts(Pair<Location, Location> targetMove, Pair<Location, Location>... moves) {
        HashSet<Location> visited = new HashSet<>();
        Location firstMove = targetMove.getKey();
        Location secondMove = targetMove.getValue();
        for (Pair<Location, Location> move : moves) {
            visited.add(move.getValue());
        }

        if (visited.contains(firstMove)) {
            return true;
        }

        return visited.contains(secondMove);
    }

    protected static HashMap<Detective, HashSet<Pair<Location, Location>>> findAllFirstMoves(ArrayList<Detective> detectives) {
        HashMap<Detective, HashSet<Pair<Location, Location>>> potentialMoves = new HashMap<>();
        for (Detective detective : detectives) {
            HashSet<Pair<Location, Location>> moves = undergroundSearch(detective.getCurrentLocation());

            moves = nextBestSearch(detective.getCurrentLocation(), moves);

            potentialMoves.put(detective, moves);
        }

        return potentialMoves;
    }

    protected static HashSet<Pair<Location, Location>> undergroundSearch(Location startLocation) {
        HashSet<Location> firstMoves = startLocation.getNeighbours();
        HashSet<Pair<Location, Location>> result = new HashSet<>();
        for (Location firstMove : firstMoves) {
            HashSet<Location> secondMoves = firstMove.getNeighbours();
            for (Location secondMove : secondMoves) {
                ArrayList<TransportType> transportMethods = firstMove.getNeighbourTransportMethods(secondMove);
                if (secondMove.getTransportType().equals(TransportType.UNDERGROUND)) {
                    if (transportMethods.size() == 1 && transportMethods.get(0).equals(TransportType.UNDERGROUND)) {
                        continue;
                    }
                    result.add(new Pair<>(firstMove, secondMove));
                }
            }
        }

        return result;
    }

    protected static HashSet<Pair<Location, Location>> nextBestSearch(Location startLocation, HashSet<Pair<Location, Location>> moves) {
        HashSet<Integer> ferries = new HashSet<>() {{
            add(115);
            add(157);
            add(194);
            add(118);
        }};
        HashSet<Pair<Location, Location>> result = new HashSet<>();
        HashSet<Location> firstMoves = startLocation.getNeighbours();
        for (Location firstMove : firstMoves) {
            ArrayList<TransportType> firstTransportMethods = startLocation.getNeighbourTransportMethods(firstMove);
            if (firstTransportMethods.size() == 1 && firstTransportMethods.get(0).equals(TransportType.UNDERGROUND)) {
                continue;
            }
            HashSet<Location> secondMoves = firstMove.getNeighbours();
            for (Location secondMove : secondMoves) {
                for (Location visibleLocation : secondMove.getNeighbours()) {
                    ArrayList<TransportType> secondTransportMethods = firstMove.getNeighbourTransportMethods(secondMove);
                    if (secondTransportMethods.size() == 1 && secondTransportMethods.get(0).equals(TransportType.UNDERGROUND)) {
                        continue;
                    }
                    if (visibleLocation.getTransportType().equals(TransportType.UNDERGROUND) || ferries.contains(visibleLocation.getNumber())) {
                        moves.add(new Pair<>(firstMove, secondMove));
                    }
                }
            }
        }

        return moves;
    }

    protected static int getScore(Location... moves) {
        HashMap<Location, Integer> watchedUndergrounds = new HashMap<>();
        HashSet<Location> ferriesWatched = new HashSet<>();
        int currentLocationScore = 0;
        int unwatchedUndergroundCount = 0;
        int punishScore = 0;
        int undergroundWeight = 4;
        int taxiWeight = 0;
        int busWeight = 1;


        for (Location move : moves) {
            switch (move.getTransportType()) {
                case TAXI -> currentLocationScore += taxiWeight;
                case BUS -> currentLocationScore += busWeight;
                case UNDERGROUND -> currentLocationScore += undergroundWeight;
            }

            // Punish for being on location 1
            if (move.getNumber() == 1) {
                punishScore -= 5;
            }

            // Add undergrounds detectives are on
            if (!watchedUndergrounds.containsKey(move)) {
                if (move.getTransportType().equals(TransportType.UNDERGROUND)) {
                    watchedUndergrounds.put(move, 1);
                }
            } else {
                watchedUndergrounds.put(move, watchedUndergrounds.get(move) + 1);
            }

            // Add undergrounds being watched by detectives
            for (Location neighbour : move.getNeighbours()) {
                if (neighbour.getNumber() == 115 || neighbour.getNumber() == 118 || neighbour.getNumber() == 157 || neighbour.getNumber() == 194) {
                    if (ferriesWatched.contains(neighbour)) {
                        punishScore -= 1;
                    } else if (!ferriesWatched.contains(neighbour) && !move.getNeighbourTransportMethods(neighbour).get(0).equals(TransportType.FERRY)) {
                        ferriesWatched.add(neighbour);
                    }
                }
                if (neighbour.getTransportType().equals(TransportType.UNDERGROUND)) {
                    if (!watchedUndergrounds.containsKey(neighbour)) {
                        watchedUndergrounds.put(neighbour, 1);
                    } else {
                        watchedUndergrounds.put(neighbour, watchedUndergrounds.get(neighbour) + 1);
                    }
                }
            }
        }

        for (Location key : watchedUndergrounds.keySet()) {
            if (watchedUndergrounds.get(key) == 1) {
                unwatchedUndergroundCount++;
            }
        }

        return currentLocationScore + unwatchedUndergroundCount + ferriesWatched.size() + punishScore;
    }

    protected static Detective getDetective(ArrayList<Detective> detectives, Color colour) {
        Detective result = null;
        for (Detective detective : detectives) {
            if (detective.getPlayerColour().equals(colour)) {
                result = detective;
                break;
            }
        }
        return result;
    }
}