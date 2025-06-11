package gr.aueb.cf.hotelapp.core.exceptions;

public class ClientNotFoundException extends EntityGenericException{
    private static String DEFAULT_CODE = "Ο πελάτης δεν βρέθηκε";

    public ClientNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
