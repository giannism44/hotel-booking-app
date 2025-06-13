package gr.aueb.cf.hotelapp.core.exceptions;

public class UserNotFoundException extends EntityGenericException{
    private static  String DEFAULT_CODE = "O χρήστης δεν βρέθηκε ";

    public UserNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
