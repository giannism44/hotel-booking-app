package gr.aueb.cf.hotelapp.core.exceptions;

public class UsernameAlreadyExistsException extends EntityGenericException{
    private static String DEFAULT_CODE = "Το username υπάρχει ήδη";

    public UsernameAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
