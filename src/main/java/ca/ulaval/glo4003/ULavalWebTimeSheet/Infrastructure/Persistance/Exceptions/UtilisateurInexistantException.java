package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public class UtilisateurInexistantException extends PersistanceException
{
    public UtilisateurInexistantException(String message) {
	super(message);
    }

}
