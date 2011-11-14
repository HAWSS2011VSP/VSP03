package cash_access;

public class OverdraftException extends Exception {
  public OverdraftException(String message) {
    super(message);
  }
}