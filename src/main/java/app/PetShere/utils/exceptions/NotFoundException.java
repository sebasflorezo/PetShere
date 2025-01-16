package app.PetShere.utils.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Not found Exception ";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ": " + detail);
    }
}
