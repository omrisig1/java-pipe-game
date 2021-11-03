package Search.Exceptions;

public class UnsolvableProblemException extends Exception {

	public static final String DefaultMessage = "Given problem cannot be solved.";

	public UnsolvableProblemException() {
		super(DefaultMessage);
	}

	public UnsolvableProblemException(String message) {
		super(message);
	}
}
