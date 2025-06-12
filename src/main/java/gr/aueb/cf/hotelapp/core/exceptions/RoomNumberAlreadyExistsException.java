package gr.aueb.cf.hotelapp.core.exceptions;

public class RoomNumberAlreadyExistsException extends EntityGenericException {
    private static  String DEFAULT_CODE = "Το δωμάτιο υπάρχει ήδη. ";

    public RoomNumberAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
