import com.scotland_yard.classes.Location;
import com.scotland_yard.classes.Map;
import com.scotland_yard.classes.TransportUtilities.TransportType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    private static final Map map = new Map();
    @Test
    void location1ShouldBeTypeUnderground(){
        // given
        Location node1 = map.getNodeByNumber(1);

        // when
        TransportType result = node1.getTransportType();

        // then
        TransportType expected = TransportType.UNDERGROUND;
        assertEquals(expected, result);
    }

    @Test
    void location200ShouldBeTypeTaxi(){
        // given
        Location node1 = map.getNodeByNumber(200);

        // when
        TransportType result = node1.getTransportType();

        // then
        TransportType expected = TransportType.TAXI;
        assertEquals(expected, result);
    }

    @Test
    void location118ShouldBeTypeBus(){
        // given
        Location node1 = map.getNodeByNumber(118);

        // when
        TransportType result = node1.getTransportType();

        // then
        TransportType expected = TransportType.BUS;
        assertEquals(expected, result);
    }

}
