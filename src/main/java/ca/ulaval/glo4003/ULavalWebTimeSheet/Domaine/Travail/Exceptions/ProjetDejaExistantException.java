package ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Travail.Exceptions;

@SuppressWarnings("serial")
public class ProjetDejaExistantException extends RuntimeException
{

    public ProjetDejaExistantException(String nomProjet) {
	super(String.format("Le projet '%s' existe déjà",nomProjet));
    }

}
