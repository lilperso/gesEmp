package ca.ulaval.glo4003.ULavalWebTimeSheet.Service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.ULavalWebTimeSheet.DTO.ListeAdresseCourrielEmployeDTO;
import ca.ulaval.glo4003.ULavalWebTimeSheet.Domaine.Utilisateurs.Agregat.Departement;

@RunWith(MockitoJUnitRunner.class)
public class ServiceAssignationProjetTest
{
    private static final String EMAIL_BIDON = "yes@test.com";
    
    @Mock 
    private Departement departement;
    
    @Mock 
    private FabriqueProjetDTO fabriqueProjetDTO;
    
    @InjectMocks
    private ServiceAssignationProjet serviceAssignationProjet = new ServiceAssignationProjet();
    
    @Test 
    public void obtenirListeEmailEmployeLorsqueGestionnaireContientUnEmailRetourneUnEmail(){
	mockGestionnaireRetourneUnEmail();
	
	ListeAdresseCourrielEmployeDTO listeDTO= serviceAssignationProjet.obtenirListeAdresseEmailEmployeDuGestionnaire();
	
	verify(departement).obtenirListeEmailEmploye();
	assertEquals(EMAIL_BIDON,listeDTO.getListeEmploye().get(0));
	assertEquals(1,listeDTO.getListeEmploye().size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void obtenirListeDesProjetsAppelGestionnaireAgregatEtFactoryDTO(){
	serviceAssignationProjet.obtenirListeDesProjets();
	verify(departement).obtenirListeDesProjet();
	verify(fabriqueProjetDTO).creerListeProjetDTO(any(List.class));
    }

    private void mockGestionnaireRetourneUnEmail() {
	List<String> liste = new ArrayList<String>();
	liste.add(EMAIL_BIDON);
	when(departement.obtenirListeEmailEmploye()).thenReturn(liste);
    }
    
}
