package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class ProjetInexistantException extends RuntimeException
{
    public ProjetInexistantException(String nomDuProjet) {
	super(String.format("Le projet '%s' n'existe pas",nomDuProjet));
    }
}
