package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class TacheInexistanteException extends RuntimeException
{
    public TacheInexistanteException(String nomDeLaTache) {
	super(String.format("La tâche '%s' n'existe pas",nomDeLaTache));
    }
}