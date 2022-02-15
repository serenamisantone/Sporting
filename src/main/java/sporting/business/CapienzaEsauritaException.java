package sporting.business;

public class CapienzaEsauritaException extends BusinessException {
	public CapienzaEsauritaException() {}
	public CapienzaEsauritaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
	}
public CapienzaEsauritaException(String message, Throwable cause) {
		super(message,cause);
}
public CapienzaEsauritaException(String message) {
		super(message);
}
public CapienzaEsauritaException(Throwable cause) {
		super(cause);
}
}
