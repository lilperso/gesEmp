package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class NomTacheInvalideException extends RuntimeException
{
    public NomTacheInvalideException(String message) {
	super(message);
    }

}
