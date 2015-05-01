package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;
import ca.ulaval.glo4003.ULavalWebTimeSheet.ValidationDonnee.ValidationDonneModificationEmploye;

@RunWith(MockitoJUnitRunner.class)
public class ServiceModifierEmployeTest
{
   
    @Mock
    private Departement departement;
    @Mock
    private ValidationDonneModificationEmploye validation;
    @InjectMocks
    private ServiceModifierEmploye serviceModifierEmploye = new ServiceModifierEmploye();

    @Test
    public void lorsqueModificationDEmplEstAppelerLesTroisMethodeSontAppele() throws Throwable {
	serviceModifierEmploye.mettreAJourEmploye("", "");
	verify(validation).validerDonnneeModifcationEmploye(any(String.class));
	verify(departement).modifierTauxHoraireRessourceHumaine(any(String.class), any(String.class));
    }
}
