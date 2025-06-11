package gr.aueb.cf.hotelapp.core.exceptions;

public class EmployeeNotFoundException extends EntityGenericException{
    private static String DEFAULT_CODE = "Ο υπάλληλος δεν βρέθηκε";

    public EmployeeNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

