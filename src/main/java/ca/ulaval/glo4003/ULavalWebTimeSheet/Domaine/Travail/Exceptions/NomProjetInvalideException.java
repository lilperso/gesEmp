package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class NomProjetInvalideException extends RuntimeException
{
    public NomProjetInvalideException(String message) {
	super(message);
    }

}
