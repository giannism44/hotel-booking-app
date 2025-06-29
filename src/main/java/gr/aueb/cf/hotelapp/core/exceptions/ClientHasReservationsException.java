package gr.aueb.cf.hotelapp.core.exceptions;

public class ClientHasReservationsException extends EntityGenericException {
    private static final String DEFAULT_CODE = "Ο πελάτης έχει ενεργές κρατήσεις και δεν μπορεί να διαγραφεί.";

    public ClientHasReservationsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
