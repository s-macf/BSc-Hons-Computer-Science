package com.scotland_yard.AI;

import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities;
import com.scotland_yard.classes.Utilities.GameState;
import javafx.util.Pair;
import java.util.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Mr_X_MCTS {
    private static final int NUMBER_OF_ITERATIONS = 2500;
    private static final long TIME_LIMIT = 1000L;
    private static final int UCB1_CONSTANT = 2;
    public MCTS_Node root;

    public Mr_X_MCTS(ArrayList<Detective> detectives, Mr_X mr_x, int roundCount) {
        GameState gameState = new GameState(detectives, mr_x, roundCount);
        this.root = new MCTS_Node(gameState);
        addMrXChildrenStates(this.root);
    }

    // Random Simulation
    public static int rollout(GameState originalGameState) {
        GameState simGameState = new GameState(originalGameState);
        boolean mr_x_caught = false;
        int stuckCounter = 0;
        while (simGameState.round <= 24 && !mr_x_caught && stuckCounter < 5) {
            if (simGameState.round < 24) {
                simGameState.round++;
            }
            // play random Mr.X Move
            Pair<Location, TransportUtilities.Ticket> mrX_PlayedMove = Mr_X_AI.getRandomMove(simGameState.mr_x, simGameState.mr_x.getAvailableMoves(simGameState.detectives));
            if (mrX_PlayedMove.getValue().equals(TransportUtilities.Ticket.CONCEALED) || mrX_PlayedMove.getValue().equals(TransportUtilities.Ticket.DOUBLE)) {
                simGameState.mr_x.removeTicket(mrX_PlayedMove.getValue());
            }
            simGameState.mr_x.setLocation(mrX_PlayedMove.getKey());

            // check if captured
            for (Detective detective : simGameState.detectives) {
                if (detective.getCurrentLocation().equals(simGameState.mr_x.getCurrentLocation())) {
                    mr_x_caught = true;
                    break;
                }
            }

            // play random detective moves
            if (!mr_x_caught) {
                for (Detective detective : simGameState.detectives) {
                    Pair<Location, TransportUtilities.Ticket> detectivePlayedMove = Detective_AI.getRandomMove(detective, detective.getAvailableMoves(simGameState.detectives));
                    if (detectivePlayedMove.getKey() != null) {
                        detective.removeTicket(detectivePlayedMove.getValue());
                        detective.setLocation(detectivePlayedMove.getKey());
                    } else {
                        stuckCounter++;
                    }
                }

                // check if captured
                for (Detective detective : simGameState.detectives) {
                    if (detective.getCurrentLocation().equals(simGameState.mr_x.getCurrentLocation())) {
                        mr_x_caught = true;
                        break;
                    }
                }
            }
        }

        return (!mr_x_caught) ? 25 : simGameState.round;
//        return (!mr_x_caught) ? 1 : 0;
    }

    private static int getScore(int depth, int result) {
        if (depth % 2 == 0) {
            if (result == 25) {
                return 0;
            }
            return 24 - result;
//            return result*-1;
        }
        return result;
    }

    public static void addDetectiveChildStates(MCTS_Node parentNode) {
        GameState parentState = parentNode.gameState;
        GameState childState = new GameState(parentState);
        ArrayList<Location> blueMoves = new ArrayList<>(childState.blue.getAvailableMoves(childState.detectives).keySet());
        if (blueMoves.isEmpty()) {
            blueMoves = new ArrayList<>();
            blueMoves.add(childState.blue.getCurrentLocation());
        }

        int counter = 0;
        for (Location blueMove : blueMoves) {
            childState.blue.setLocation(blueMove);
            ArrayList<Location> blackMoves = new ArrayList<>(childState.black.getAvailableMoves(childState.detectives).keySet());
            if (blackMoves.isEmpty()) {
                blackMoves = new ArrayList<>();
                blackMoves.add(childState.black.getCurrentLocation());
            }
            for (Location blackMove : blackMoves) {
                childState.black.setLocation(blackMove);
                ArrayList<Location> redMoves = new ArrayList<>(childState.red.getAvailableMoves(childState.detectives).keySet());
                if (redMoves.isEmpty()) {
                    redMoves = new ArrayList<>();
                    redMoves.add(childState.red.getCurrentLocation());
                }
                if (blackMove.equals(blackMoves.get(blackMoves.size() - 1))) {
                    childState.black.setLocation(parentState.black.getCurrentLocation());
                }
                for (Location redMove : redMoves) {
                    childState.red.setLocation(redMove);
                    ArrayList<Location> greenMoves = new ArrayList<>(childState.green.getAvailableMoves(childState.detectives).keySet());
                    if (greenMoves.isEmpty()) {
                        greenMoves = new ArrayList<>();
                        greenMoves.add(childState.green.getCurrentLocation());
                    }
                    if (redMove.equals(redMoves.get(redMoves.size() - 1))) {
                        childState.red.setLocation(parentState.red.getCurrentLocation());
                    }
                    for (Location greenMove : greenMoves) {
                        childState.green.setLocation(greenMove);
                        ArrayList<Location> yellowMoves = new ArrayList<>(childState.yellow.getAvailableMoves(childState.detectives).keySet());
                        if (yellowMoves.isEmpty()) {
                            yellowMoves = new ArrayList<>();
                            yellowMoves.add(childState.yellow.getCurrentLocation());
                        }
                        if (greenMove.equals(greenMoves.get(greenMoves.size() - 1))) {
                            childState.green.setLocation(parentState.green.getCurrentLocation());
                        }
                        for (Location yellowMove : yellowMoves) {
                            childState.yellow.setLocation(yellowMove);
                            if (yellowMove.equals(yellowMoves.get(yellowMoves.size() - 1))) {
                                childState.yellow.setLocation(parentState.yellow.getCurrentLocation());
                            }
                            if (parentNode.children.size() == counter) {
                                // Add child node to tree
                                GameState childGameState = new GameState(parentState);
                                childGameState.blue.setLocation(blueMove);
                                childGameState.black.setLocation(blackMove);
                                childGameState.red.setLocation(redMove);
                                childGameState.green.setLocation(greenMove);
                                childGameState.yellow.setLocation(yellowMove);
                                MCTS_Node childNode = new MCTS_Node(childGameState);
                                parentNode.addChildNode(childNode);
                                checkTerminalState(childNode);
                                return;
                            }
//                            System.out.println(blueMove.getNumber() + " " + blackMove.getNumber() + " " + redMove.getNumber() + " " + greenMove.getNumber() + " " + yellowMove.getNumber());
                            counter++;
                        }
                    }
                }
            }
        }
    }

    private static void checkTerminalState(MCTS_Node node) {
        GameState gameState = node.gameState;
        for (Detective detective : gameState.detectives) {
            if (gameState.mr_x.getCurrentLocation().equals(detective.getCurrentLocation())) {
                node.terminalState = true;
                break;
            }
        }
    }

    public static double calc_UCB1(MCTS_Node node, int total_iterations) {
        return ((double) node.score / node.number_of_visits) + UCB1_CONSTANT * Math.sqrt(Math.log(total_iterations) / node.number_of_visits);
    }

    public static void addMrXChildrenStates(MCTS_Node parentNode) {
        GameState gameState = parentNode.gameState;
        ArrayList<Location> mr_x_moves = new ArrayList<>(gameState.mr_x.getAvailableMoves(gameState.detectives).keySet());
        for (Location newLocation : mr_x_moves) {
            boolean detectiveSpot = false;
            for (Detective detective : gameState.detectives) {
                if (detective.getCurrentLocation().equals(newLocation)) {
                    detectiveSpot = true;
                }
            }
            if (!detectiveSpot) {
                GameState childGameState = new GameState(gameState.detectives, new Mr_X(Color.WHITE, newLocation), gameState.round + 1);
                MCTS_Node newNode = new MCTS_Node(childGameState);
                parentNode.addChildNode(newNode);
                checkTerminalState(newNode);
            }
        }

        if (parentNode.children.isEmpty()) {
            for (Location newLocation : mr_x_moves) {
                GameState childGameState = new GameState(gameState.detectives, new Mr_X(Color.WHITE, newLocation), gameState.round + 1);
                MCTS_Node newNode = new MCTS_Node(childGameState);
                parentNode.addChildNode(newNode);
                checkTerminalState(newNode);
            }
        }
    }

    public Location startMCTS() {

        for (long stop=System.nanoTime()+ TimeUnit.MILLISECONDS.toNanos(1000);stop > System.nanoTime();){
            mcts_run(0, root);
        }

//        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
//            mcts_run(0, root);
//        }

        MCTS_Node best_result_node = this.root.children.get(0);
        double best_result = calc_UCB1(best_result_node, this.root.number_of_visits);
        for (MCTS_Node childNode : this.root.children) {
            double UCB1_Score = calc_UCB1(childNode, this.root.number_of_visits);
            if (UCB1_Score > best_result) {
                best_result = UCB1_Score;
                best_result_node = childNode;
            }
        }

        return best_result_node.gameState.mr_x.getCurrentLocation();
    }

    public int mcts_run(int depth, MCTS_Node currentNode) {

        if (currentNode.children.isEmpty() || currentNode.gameState.round == 24) {
            if (currentNode.number_of_visits == 0 || currentNode.gameState.round == 24 || currentNode.terminalState) {
                int result = rollout(currentNode.gameState);
                currentNode.score += getScore(depth, result);
                currentNode.number_of_visits++;
                return result;
            }
        }

        if (!currentNode.allChildrenAdded) {
            int oldSize = currentNode.children.size();
            addChildren(currentNode, depth);
            if (currentNode.children.size() == oldSize || depth % 2 == 0) {
                currentNode.allChildrenAdded = true;
            }
        }

        double UCB1_max = calc_UCB1(currentNode.children.get(0), this.root.number_of_visits);
        MCTS_Node UCB1_max_node = currentNode.children.get(0);
        for (MCTS_Node childNode : currentNode.children) {
            double UCB1_score = calc_UCB1(childNode, this.root.number_of_visits);
            if (Double.isNaN(UCB1_score)) {
                UCB1_max_node = childNode;
                break;
            }
            if (UCB1_score > UCB1_max) {
                UCB1_max = UCB1_score;
                UCB1_max_node = childNode;
            }
        }

        int result = mcts_run(depth + 1, UCB1_max_node);
        currentNode.score += getScore(depth, result);
        currentNode.number_of_visits++;
        return result;
    }

    private void addChildren(MCTS_Node parentNode, int depth) {
        int roundType = depth % 2;
        if (roundType == 0) {
            addMrXChildrenStates(parentNode);
        } else {
            addDetectiveChildStates(parentNode);
        }
    }

    public static class MCTS_Node {
        public GameState gameState;
        public ArrayList<MCTS_Node> children = new ArrayList<>();
        public int number_of_visits;
        public int score;
        public boolean allChildrenAdded = false;
        public boolean terminalState = false;

        public MCTS_Node(GameState gameState) {
            this.gameState = gameState;
            this.number_of_visits = 0;
            this.score = 0;
        }

        private void addChildNode(MCTS_Node childNode) {
            children.add(childNode);
        }
    }
}
