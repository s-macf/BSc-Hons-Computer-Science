import com.scotland_yard.classes.*;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MrXTests {

    private static final Map map = new Map();

    @Test
    void MrX_Should_Have_Available_Moves_20_10() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(115));
        mr_x.removeTicket(Ticket.CONCEALED);
        mr_x.removeTicket(Ticket.CONCEALED);
        mr_x.removeTicket(Ticket.CONCEALED);
        mr_x.removeTicket(Ticket.CONCEALED);

        ArrayList<Detective> detectives = new ArrayList<>(){{
            add(new Detective(Color.BLUE, map.getNodeByNumber(118)));
            add(new Detective(Color.BLACK, map.getNodeByNumber(127)));
            add(new Detective(Color.RED, map.getNodeByNumber(114)));
            add(new Detective(Color.GREEN, map.getNodeByNumber(102)));
            add(new Detective(Color.YELLOW, map.getNodeByNumber(126)));
        }};

        // when
        HashSet<Location> availableMoves = new HashSet<>(mr_x.getAvailableMoves(detectives).keySet());

        // then
        HashSet<Location> expectedLocations = new HashSet<>(){{
//            add(map.getNodeByNumber(118));
            add(map.getNodeByNumber(157));

//            add(map.getNodeByNumber(114));
//            add(map.getNodeByNumber(102));
//            add(map.getNodeByNumber(126));
//            add(map.getNodeByNumber(127));
        }};

        for (Location loc : availableMoves){
            System.out.println(loc.getNumber());
        }

        assertEquals(expectedLocations, availableMoves);

    }

    @Test
    void MrX_Should_Start_With_Num_Of_BlackTickets_Equal_To_Num_Of_Detectives() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(7));


        // when
        Integer result = mr_x.getRemainingTickets(Ticket.CONCEALED);

        // then
        int expected = 5;      // to be updated to get length of detectives array
        assertEquals(expected, result);
    }

    @Test
    void MrX_Should_Start_With_2_Double_Tickets() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(7));


        // when
        Integer result = mr_x.getRemainingTickets(Ticket.DOUBLE);

        // then
        int expected = 2;
        assertEquals(expected, result);
    }

    @Test
    void mr_X_current_location_should_be_node_6() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(6));

        // when
        Location mr_x_currentLocation = mr_x.getCurrentLocation();

        // then
        Location expected = map.getNodeByNumber(6);
        assertEquals(expected, mr_x_currentLocation);
    }

    @Test
    void when_Concealed_Ticket_Used_Concealed_Ticket_Should_Be_Removed() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(118));

        // when
        mr_x.removeTicket(Ticket.CONCEALED);

        // then
        Integer result = mr_x.getRemainingTickets(Ticket.CONCEALED);
        Integer expected = 4;
        assertEquals(expected, result);
    }

    @Test
    void when_Double_Ticket_Used_Double_Ticket_Should_Be_Removed() {
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(118));

        // when
        mr_x.removeTicket(Ticket.DOUBLE);

        // then
        Integer result = mr_x.getRemainingTickets(Ticket.DOUBLE);
        Integer expected = 1;
        assertEquals(expected, result);
    }

}