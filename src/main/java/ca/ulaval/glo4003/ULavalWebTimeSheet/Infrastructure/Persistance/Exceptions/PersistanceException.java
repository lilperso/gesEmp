package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public abstract class PersistanceException extends RuntimeException
{
    private String message;

    public PersistanceException(String message) {
	this.message = message;
    }

    @Override
    public String getMessage() {
	return message;
    }

}
