package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public class UtilisateurDejaExistantException extends PersistanceException
{
    public UtilisateurDejaExistantException(String message) {
	super(message);
    }
}
