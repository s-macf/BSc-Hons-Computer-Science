import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Map;
import com.scotland_yard.classes.Mr_X;
import com.scotland_yard.classes.TransportUtilities.Ticket;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MrXTests {

    private static final Map map = new Map();

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
    void mr_X_current_location_should_be_node_6(){
        // given
        Mr_X mr_x = new Mr_X(Color.WHITE, map.getNodeByNumber(6));

        // when
        Location mr_x_currentLocation = mr_x.getCurrentLocation();

        // then
        Location expected = map.getNodeByNumber(6);
        assertEquals(expected, mr_x_currentLocation);
    }

    @Test
    void when_Concealed_Ticket_Used_Concealed_Ticket_Should_Be_Removed(){
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
    void when_Double_Ticket_Used_Double_Ticket_Should_Be_Removed(){
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