import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Map;
import com.scotland_yard.classes.TransportUtilities;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LocationNeighboursTests {
    private static final Map map = new Map();
    @Test
    void location_6_Should_Have_Neighbours_7_29() {

        // given
        Location nodeSix = map.getNodeByNumber(6);

        // when
        HashSet<Location> result = nodeSix.getNeighbours();

        //then
        HashSet<Location> expected = new HashSet<>();
        expected.add(map.getNodeByNumber(7));
        expected.add(map.getNodeByNumber(29));
        assertEquals(expected, result);
    }

    @Test
    void location_6_Should_Have_Available_Transport_Methods_Taxi() {
        // given
        Location nodeSix = map.getNodeByNumber(6);
        HashSet<Location> neighbours = nodeSix.getNeighbours();


        // when
        HashSet<TransportUtilities.TransportType> result = new HashSet<>();
        for (Location neighbour : neighbours) {
            ArrayList<TransportUtilities.TransportType> transportMethods = nodeSix.getNeighbourTransportMethods(neighbour);
            result.addAll(transportMethods);
        }

        // then
        HashSet<TransportUtilities.TransportType> expected = new HashSet<>();
        expected.add(TransportUtilities.TransportType.TAXI);
        assertEquals(expected, result);
    }

    @Test
    void location_135_Should_Have_Available_Transport_Methods_Taxi_Bus() {
        // given
        Location node135 = map.getNodeByNumber(135);
        HashSet<Location> neighbours = node135.getNeighbours();


        // when
        HashSet<TransportUtilities.TransportType> result = new HashSet<>();
        for (Location neighbour : neighbours) {
            ArrayList<TransportUtilities.TransportType> transportMethods = node135.getNeighbourTransportMethods(neighbour);
            result.addAll(transportMethods);
        }

        // then
        HashSet<TransportUtilities.TransportType> expected = new HashSet<>();
        expected.add(TransportUtilities.TransportType.TAXI);
        expected.add(TransportUtilities.TransportType.BUS);
        assertEquals(expected, result);
    }

    @Test
    void location_159_Should_Have_Available_Transport_Methods_Taxi_Bus_Underground() {
        // given
        Location node159 = map.getNodeByNumber(159);
        HashSet<Location> neighbours = node159.getNeighbours();


        // when
        HashSet<TransportUtilities.TransportType> result = new HashSet<>();
        for (Location neighbour : neighbours) {
            ArrayList<TransportUtilities.TransportType> transportMethods = node159.getNeighbourTransportMethods(neighbour);
            result.addAll(transportMethods);
        }

        // then
        HashSet<TransportUtilities.TransportType> expected = new HashSet<>();
        expected.add(TransportUtilities.TransportType.TAXI);
        expected.add(TransportUtilities.TransportType.BUS);
        expected.add(TransportUtilities.TransportType.UNDERGROUND);
        assertEquals(expected, result);
    }

    @Test
    void location_118_Should_Have_Available_Transport_Methods_Taxi_Bus_Ferry() {
        // given
        Location node118 = map.getNodeByNumber(118);
        HashSet<Location> neighbours = node118.getNeighbours();


        // when
        HashSet<TransportUtilities.TransportType> result = new HashSet<>();
        for (Location neighbour : neighbours) {
            ArrayList<TransportUtilities.TransportType> transportMethods = node118.getNeighbourTransportMethods(neighbour);
            result.addAll(transportMethods);
        }

        // then
        HashSet<TransportUtilities.TransportType> expected = new HashSet<>();
        expected.add(TransportUtilities.TransportType.TAXI);
        expected.add(TransportUtilities.TransportType.BUS);
        expected.add(TransportUtilities.TransportType.FERRY);
        assertEquals(expected, result);
    }

    @Test
    void location_1_Should_Have_Neighbours_8_9_48_58() {

        // given
        Location nodeOne = map.getNodeByNumber(1);

        // when
        HashSet<Location> result = nodeOne.getNeighbours();

        //then
        HashSet<Location> expected = new HashSet<>();
        expected.add(map.getNodeByNumber(8));
        expected.add(map.getNodeByNumber(9));
        expected.add(map.getNodeByNumber(46));
        expected.add(map.getNodeByNumber(58));
        assertEquals(expected, result);
    }

}
