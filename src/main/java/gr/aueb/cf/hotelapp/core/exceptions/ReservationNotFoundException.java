package gr.aueb.cf.hotelapp.core.exceptions;

public class ReservationNotFoundException extends EntityGenericException{
    private static String DEFAULT_CODE = "H κράτηση δεν βρέθηκε";

    public ReservationNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
