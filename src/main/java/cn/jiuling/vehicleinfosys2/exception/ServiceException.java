package cn.jiuling.vehicleinfosys2.exception;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 8522795433330011415L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
