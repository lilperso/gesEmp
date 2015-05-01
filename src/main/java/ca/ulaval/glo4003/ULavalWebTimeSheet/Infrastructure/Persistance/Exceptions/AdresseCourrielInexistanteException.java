package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public class AdresseCourrielInexistanteException extends PersistanceException
{
    public AdresseCourrielInexistanteException(String message) {
	super(message);
    }
}
