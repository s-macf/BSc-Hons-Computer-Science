import com.scotland_yard.AI.Detective_AI;
import com.scotland_yard.classes.Detective;
import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Map;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetectiveTests {

    private final Map map = new Map();
    @Test
    void blue_Detective_Colour_String_Should_Be_Blue(){
        // given
        Detective blue = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        String result = blue.toString();

        // then
        String expected = "Blue";
        assertEquals(expected, result);
    }

    @Test
    void detectives_Should_Start_With_10_Taxi_Tickets(){
        //given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        Integer result = blueDetective.getRemainingTickets(Ticket.TAXI);

        // then
        Integer expected = 10;
        assertEquals(expected, result);
    }

    @Test
    void detectives_Should_Start_With_8_Bus_Tickets(){
        //given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        Integer result = blueDetective.getRemainingTickets(Ticket.BUS);

        // then
        Integer expected = 8;
        assertEquals(expected, result);
    }

    @Test
    void detectives_Should_Start_With_4_Underground_Tickets(){
        //given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        Integer result = blueDetective.getRemainingTickets(Ticket.UNDERGROUND);

        // then
        Integer expected = 4;
        assertEquals(expected, result);
    }

    @Test
    void when_Taxi_Used_Taxi_Ticket_Should_Be_Removed(){
        // given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        blueDetective.removeTicket(Ticket.TAXI);

        // then
        int expectedRemainingTaxiTickets = 9;
        assertEquals(expectedRemainingTaxiTickets, blueDetective.getRemainingTickets(Ticket.TAXI));
    }

    @Test
    void when_Bus_Used_Bus_Ticket_Should_Be_Removed(){
        // given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        blueDetective.removeTicket(Ticket.BUS);

        // then
        int expectedRemainingTaxiTickets = 7;
        assertEquals(expectedRemainingTaxiTickets, blueDetective.getRemainingTickets(Ticket.BUS));
    }

    @Test
    void when_Underground_Used_Underground_Ticket_Should_Be_Removed(){
        // given
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(4));

        // when
        blueDetective.removeTicket(Ticket.UNDERGROUND);

        // then
        int expectedRemainingTaxiTickets = 3;
        assertEquals(expectedRemainingTaxiTickets, blueDetective.getRemainingTickets(Ticket.UNDERGROUND));
    }

    @Test
    void when_Stranded_On_Taxi_Available_Moves_Should_Be_Empty(){
        // given
        ArrayList<Detective> detectives = new ArrayList<>();
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(20));
        Mr_X mr_x = new Mr_X(Color.white, map.getNodeByNumber(89));
        detectives.add(blueDetective);

        // when (All taxi tickets have been used)
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);

        // then
        Pair<Location, Ticket> playedMove =  Detective_AI.getMove(blueDetective, blueDetective.getAvailableMoves(detectives), 5, mr_x);
        Pair<Location , Ticket> expectedMove = new Pair<>(null, null);
        assertEquals(expectedMove, playedMove);
    }
    @Test
    void when_Taxi_Used_Ticket_Should_Be_Removed(){
        // given
        ArrayList<Detective> detectives = new ArrayList<>();
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(20));
        detectives.add(blueDetective);

        // when
        blueDetective.removeTicket(Ticket.TAXI);

        // then
        Integer result = blueDetective.getRemainingTickets(Ticket.TAXI);
        Integer expected = 9;
        assertEquals(expected, result);
    }
    @Test
    void when_Stranded_On_Bus_Available_Moves_Should_Be_Empty(){
        // given
        ArrayList<Detective> detectives = new ArrayList<>();
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(86));
        detectives.add(blueDetective);

        // when (All taxi tickets have been used)
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);
        blueDetective.removeTicket(Ticket.TAXI);

        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);
        blueDetective.removeTicket(Ticket.BUS);

        // then
        Pair<Location, Ticket> playedMove =  Detective_AI.getMove(blueDetective, blueDetective.getAvailableMoves(detectives), 5, new Mr_X(Color.white, map.getNodeByNumber(4)));
        Pair<Location , Ticket> expectedMove = new Pair<>(null, null);
        assertEquals(expectedMove, playedMove);
    }
    @Test
    void when_Bus_Used_Ticket_Should_Be_Removed(){
        // given
        ArrayList<Detective> detectives = new ArrayList<>();
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(20));
        detectives.add(blueDetective);

        // when
        blueDetective.removeTicket(Ticket.BUS);

        // then
        Integer result = blueDetective.getRemainingTickets(Ticket.BUS);
        Integer expected = 7;
        assertEquals(expected, result);
    }

    @Test
    void when_Underground_Used_Ticket_Should_Be_Removed(){
        // given
        ArrayList<Detective> detectives = new ArrayList<>();
        Detective blueDetective = new Detective(Color.BLUE, map.getNodeByNumber(20));
        detectives.add(blueDetective);

        // when
        blueDetective.removeTicket(Ticket.UNDERGROUND);

        // then
        Integer result = blueDetective.getRemainingTickets(Ticket.UNDERGROUND);
        Integer expected = 3;
        assertEquals(expected, result);
    }

}
