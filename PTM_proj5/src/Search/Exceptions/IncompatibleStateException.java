package Search.Exceptions;

public class IncompatibleStateException extends Exception {

	public static final String DefaultMessage = "Cannot compute solution of different configurations.";

	public IncompatibleStateException() {
		super(DefaultMessage);
	}

	public IncompatibleStateException(String message) {
		super(message);
	}
}
