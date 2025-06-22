package gr.aueb.cf.hotelapp.core.exceptions;

import lombok.Getter;

/**
 * Γενική εξαίρεση για όλες τις οντότητες του συστήματος.
 * Περιέχει κωδικό σφάλματος ώστε να διευκολύνεται η διεπαφή χρήστη.
 */
@Getter
public class EntityGenericException extends Exception{
    private final String code;

    public EntityGenericException(String code, String message) {
        super(message);
        this.code = code;
    }
}