package tomasulo.exceptions;

public class InvalidInstructionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidInstructionException() {
        super();
    }

    public InvalidInstructionException(String message) {
        super(message);
    }

}
