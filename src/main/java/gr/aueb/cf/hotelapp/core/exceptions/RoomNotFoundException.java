package gr.aueb.cf.hotelapp.core.exceptions;

public class RoomNotFoundException extends EntityGenericException  {
    private static  String DEFAULT_CODE = "Το δωμάτιο δεν υπάρχει. ";

    public RoomNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
