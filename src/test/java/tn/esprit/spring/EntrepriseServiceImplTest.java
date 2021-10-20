package tn.esprit.spring;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	@Autowired 
	IEntrepriseService iEntrepriseService; 


	
	@Test
	public void testAjouterDepartement() throws ParseException {
		Departement d = new Departement("test"); 
		int departementAdded = iEntrepriseService.ajouterDepartement(d);
		assertEquals(departementAdded,d.getId());
	}
	

	@Test
	public void testDeleteDepartementById() {
		iEntrepriseService.deleteDepartementById(7);
		List<Departement> listDepartements = iEntrepriseService.retrieveAllDepartements();
		assertEquals(9, listDepartements.size());
	}

	@Test
	public void testAffecterDepartementAEntreprise() {
		iEntrepriseService.affecterDepartementAEntreprise(3,1);
		Optional<Departement> dep = iEntrepriseService.findById(3);

		assertEquals(1, dep.get().getEntreprise().getId());

		
	}

	@Test
	public void testgetAllDepartementsNamesByEntreprise(){
		List<String> names= iEntrepriseService.getAllDepartementsNamesByEntreprise(1);
		assertEquals(3, names.size());
	}
}
