package tomasulo.exceptions;

public class InstructionBufferOverflowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InstructionBufferOverflowException() {
        super();
    }

    public InstructionBufferOverflowException(String message) {
        super(message);
    }

}

