package com.scotland_yard.classes;

public class TransportUtilities {

    public static TransportType getTransportType(String type) {
        return switch (type) {
            case "taxi" -> TransportType.TAXI;
            case "bus" -> TransportType.BUS;
            case "underground" -> TransportType.UNDERGROUND;
            case "ferry" -> TransportType.FERRY;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static Ticket transportTypeToTicket(TransportType transport){
        return switch (transport){
            case TAXI -> Ticket.TAXI;
            case BUS -> Ticket.BUS;
            case UNDERGROUND -> Ticket.UNDERGROUND;
            case FERRY -> Ticket.CONCEALED;
        };
    }

    public enum TransportType {
        TAXI,
        BUS,
        FERRY,
        UNDERGROUND
    }

    public enum Ticket {
        TAXI,
        BUS,
        UNDERGROUND,
        DOUBLE,
        CONCEALED
    }
}
