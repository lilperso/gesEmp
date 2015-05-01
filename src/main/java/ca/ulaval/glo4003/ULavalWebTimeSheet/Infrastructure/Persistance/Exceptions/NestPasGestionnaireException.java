package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Exceptions;

@SuppressWarnings("serial")
public class NestPasGestionnaireException extends PersistanceException
{
    public NestPasGestionnaireException(String message){
	super(message);
    }
}
