import com.scotland_yard.AI.Detective_AI;
import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Map;
import com.scotland_yard.classes.TransportUtilities;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Temp extends Detective_AI{

    private static final Map map = new Map();

    @Test
    void weeTest(){
        Location loc = map.getNodeByNumber(153);

        HashSet<Location> res = loc.getNeighbours();

        for (Location x : res){
            if (x.getTransportType().equals(TransportUtilities.TransportType.UNDERGROUND)){
                System.out.println(x.getNumber());
            }
        }
    }

    @Test
    void fullTest(){
        ArrayList<Detective> detectives = new ArrayList<>(){{
            add(new Detective(Color.BLUE, map.getNodeByNumber(53)));
            add(new Detective(Color.BLACK, map.getNodeByNumber(13)));
            add(new Detective(Color.RED, map.getNodeByNumber(197)));
            add(new Detective(Color.GREEN, map.getNodeByNumber(138)));
            add(new Detective(Color.YELLOW, map.getNodeByNumber(132)));
        }};

        HashMap<Detective, Pair<Location, Location>> res = getFirstMoves(detectives);

        for (Detective detective : res.keySet()){
            Pair<Location, Location> x = res.get(detective);
            System.out.println(detective + ": " + x.getKey().getNumber() + "," + x.getValue().getNumber());
        }
    }

    @Test
    void firstMoves(){
        ArrayList<Detective> detectives = new ArrayList<>(){{
            add(new Detective(Color.BLUE, map.getNodeByNumber(198)));
            add(new Detective(Color.BLACK, map.getNodeByNumber(197)));
            add(new Detective(Color.RED, map.getNodeByNumber(174)));
            add(new Detective(Color.GREEN, map.getNodeByNumber(141)));
            add(new Detective(Color.YELLOW, map.getNodeByNumber(138)));
        }};

        findAllFirstMoves(detectives);
    }

    @Test
    void undergroundSearchTest(){
        Location startLoc = map.getNodeByNumber(117);
        System.out.println("Underground Search for " + startLoc.getNumber() + ": " + undergroundSearch(startLoc));
    }

    @Test
    void nextBestSearchTest(){
        Location startLoc = map.getNodeByNumber(117);
//        System.out.println("Next Best Search for " + startLoc.getNumber() + ": " + nextBestSearch(startLoc));
    }
}