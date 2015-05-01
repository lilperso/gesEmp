package ca.ulaval.glo4003.ULavalWebTimeSheet.Utilitaires;

import org.joda.time.DateTime;

public class UtilitaireDate
{

    public UtilitaireDate() {
    }

    public String formaterDate(DateTime date) {
	return date.toString("yyyy-MM-dd");
    }

    public boolean estMemeDate(DateTime dateA, DateTime dateB) {
	return formaterDate(dateA).equals(formaterDate(dateB));
    }
}
