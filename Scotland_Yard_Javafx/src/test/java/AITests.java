import com.scotland_yard.AI.Detective_AI;
import com.scotland_yard.AI.Mr_X_MCTS;
import com.scotland_yard.classes.*;
import com.scotland_yard.classes.Utilities.GameState;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AITests extends Detective_AI {

    private static final Map map = new Map();

    @Test
    void MCTS_Full_Test(){
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(115));
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(5));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(162));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(100));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(10));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(133));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        Mr_X_MCTS mrXMcts = new Mr_X_MCTS(detectives, mr_x, 21);
        System.out.println(mrXMcts.startMCTS().getNumber());
    }
    @Test
    void MCTS_UCB1_Test(){
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(172));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(160));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(158));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(143));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(129));
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(134));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        Mr_X_MCTS mcts = new Mr_X_MCTS(detectives, mr_x, 1);
        mcts.root.number_of_visits = 5;
        System.out.println(Mr_X_MCTS.calc_UCB1(mcts.root, mcts.root.number_of_visits));
    }

    @Test
    void MCTS_new_tree() {
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(172));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(160));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(158));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(143));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(129));
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(134));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        Mr_X_MCTS test = new Mr_X_MCTS(detectives, mr_x, 1);
        int gameState = 1;
        for (Mr_X_MCTS.MCTS_Node childNode : test.root.children) {
            System.out.println("Gamestate: " + gameState);
            System.out.println(childNode.gameState.mr_x + " " + childNode.gameState.mr_x.getCurrentLocation().getNumber());
            System.out.println(childNode.gameState.blue + " " + childNode.gameState.blue.getCurrentLocation().getNumber());
            System.out.println(childNode.gameState.black + " " + childNode.gameState.black.getCurrentLocation().getNumber());
            System.out.println(childNode.gameState.red + " " + childNode.gameState.red.getCurrentLocation().getNumber());
            System.out.println(childNode.gameState.green + " " + childNode.gameState.green.getCurrentLocation().getNumber());
            System.out.println(childNode.gameState.yellow + " " + childNode.gameState.yellow.getCurrentLocation().getNumber());

            System.out.println();
            gameState++;
        }
    }

    @Test
    void MCTS_Rollout_Test() {
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(172));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(160));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(158));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(143));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(129));
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(134));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        GameState currentGameState = GameState.copyGameState(detectives, mr_x, 21);
        System.out.println(Mr_X_MCTS.rollout(currentGameState));
    }

    @Test
    void MCTS_Get_Detective_Children_Test() {
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(41));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(3));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(38));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(172));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(6));
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(17));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        GameState currentGameState = GameState.copyGameState(detectives, mr_x, 21);
        Mr_X_MCTS.MCTS_Node parentNode = new Mr_X_MCTS.MCTS_Node(currentGameState);
        Mr_X_MCTS.addDetectiveChildStates(parentNode);
    }

    @Test
    void MCTS_Get_Mr_X_Children_Test() {
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(41));
        Detective black = new Detective(Color.BLACK, map.getNodeByNumber(3));
        Detective red = new Detective(Color.RED, map.getNodeByNumber(38));
        Detective green = new Detective(Color.GREEN, map.getNodeByNumber(172));
        Detective yellow = new Detective(Color.YELLOW, map.getNodeByNumber(6));
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(17));

        ArrayList<Detective> detectives = new ArrayList<>();
        detectives.add(blue);
        detectives.add(black);
        detectives.add(red);
        detectives.add(green);
        detectives.add(yellow);

        GameState currentGameState = GameState.copyGameState(detectives, mr_x, 21);
        Mr_X_MCTS.MCTS_Node parentNode = new Mr_X_MCTS.MCTS_Node(currentGameState);
        Mr_X_MCTS.addMrXChildrenStates(parentNode);
    }

    @Test
    void weeTest() {
        Location loc = map.getNodeByNumber(153);

        HashSet<Location> res = loc.getNeighbours();

        for (Location x : res) {
            if (x.getTransportType().equals(TransportUtilities.TransportType.UNDERGROUND)) {
                System.out.println(x.getNumber());
            }
        }
    }

    @Test
    void fullTest() {
        ArrayList<Detective> detectives = new ArrayList<>() {{
            add(new Detective(Color.BLUE, map.getNodeByNumber(53)));
            add(new Detective(Color.BLACK, map.getNodeByNumber(13)));
            add(new Detective(Color.RED, map.getNodeByNumber(197)));
            add(new Detective(Color.GREEN, map.getNodeByNumber(138)));
            add(new Detective(Color.YELLOW, map.getNodeByNumber(132)));
        }};

        HashMap<Detective, Pair<Location, Location>> res = getFirstMoves(detectives);

        for (Detective detective : res.keySet()) {
            Pair<Location, Location> x = res.get(detective);
            System.out.println(detective + ": " + x.getKey().getNumber() + "," + x.getValue().getNumber());
        }
    }

    @Test
    void firstMoves() {
        ArrayList<Detective> detectives = new ArrayList<>() {{
            add(new Detective(Color.BLUE, map.getNodeByNumber(198)));
            add(new Detective(Color.BLACK, map.getNodeByNumber(197)));
            add(new Detective(Color.RED, map.getNodeByNumber(174)));
            add(new Detective(Color.GREEN, map.getNodeByNumber(141)));
            add(new Detective(Color.YELLOW, map.getNodeByNumber(138)));
        }};

        findAllFirstMoves(detectives);
    }

    @Test
    void undergroundSearchTest() {
        Location startLoc = map.getNodeByNumber(117);
        System.out.println("Underground Search for " + startLoc.getNumber() + ": " + undergroundSearch(startLoc));
    }

    @Test
    void nextBestSearchTest() {
        Location startLoc = map.getNodeByNumber(117);
//        System.out.println("Next Best Search for " + startLoc.getNumber() + ": " + nextBestSearch(startLoc));
    }
}