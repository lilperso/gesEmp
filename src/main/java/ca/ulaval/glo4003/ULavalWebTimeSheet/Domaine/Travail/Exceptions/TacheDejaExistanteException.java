package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class TacheDejaExistanteException extends RuntimeException
{
    public TacheDejaExistanteException(String message) {
	super(message);
    }
}
