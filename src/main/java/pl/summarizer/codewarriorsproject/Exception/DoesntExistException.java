package pl.summarizer.codewarriorsproject.Exception;

public class DoesntExistException extends RuntimeException {
    public DoesntExistException(String message) {
        super(message);
    }
}
