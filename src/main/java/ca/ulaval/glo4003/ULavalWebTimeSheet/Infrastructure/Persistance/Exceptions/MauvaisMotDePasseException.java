package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public class MauvaisMotDePasseException extends PersistanceException
{
    public MauvaisMotDePasseException(String message) {
	super(message);
    }
}
