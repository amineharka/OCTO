package ma.octo.assignement.exceptions;

public class NotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
