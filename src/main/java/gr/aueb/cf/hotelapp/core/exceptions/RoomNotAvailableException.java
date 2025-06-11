package gr.aueb.cf.hotelapp.core.exceptions;

public class RoomNotAvailableException extends EntityGenericException{
    private static String DEFAULT_CODE = "Το δωμάτιο δεν είναι διαθέσιμο";

    public RoomNotAvailableException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
