public enum TypeOfService {
    PASSENGER_TRANSPORT, CARGO_TRANSPORT, SATELLITE_SERVICE;

    public static TypeOfService decodeTypeOfService(String letter){
        switch(letter){
            case "P":
                return TypeOfService.PASSENGER_TRANSPORT;
            case "C":
                return TypeOfService.CARGO_TRANSPORT;
            case "O":
                return TypeOfService.SATELLITE_SERVICE;
            default:
                return null;
        }
    }
}
