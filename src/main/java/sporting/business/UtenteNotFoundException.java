package sporting.business;

public class UtenteNotFoundException extends BusinessException {
	public UtenteNotFoundException() {}
	public UtenteNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
	}
public UtenteNotFoundException(String message, Throwable cause) {
		super(message,cause);
}
public UtenteNotFoundException(String message) {
		super(message);
}
public UtenteNotFoundException(Throwable cause) {
		super(cause);
}
}
