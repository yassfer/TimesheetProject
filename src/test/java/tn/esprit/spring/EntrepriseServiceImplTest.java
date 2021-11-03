package tn.esprit.spring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {

	@Mock
	@Autowired
	private EmployeRepository employeRepository;
	@Mock
	@Autowired
	DepartementRepository deptRepository;
	@Mock
	@Autowired
	EntrepriseRepository entrepRepository;
	@Autowired
	@InjectMocks
	private EntrepriseServiceImpl entrepriseService;
	private Entreprise entreprise;
	private Departement departement;
	@Before
	public void setUp() {
		entreprise = entrepRepository.save(new Entreprise("Moodme", "informatique"));
		departement = deptRepository.save(new Departement("RH"));
		departement.setEntreprise(entreprise);
		deptRepository.save(departement);
	}
	@After
	public void tearDown() {
		entrepRepository.deleteAll();
		deptRepository.deleteAll();
		
	}
	
	@Test
	public void testAjouterDepartement() {
		Departement dep = new Departement("Finance");
		int AddedepID = entrepriseService.ajouterDepartement(dep);
		Optional<Departement> departement = deptRepository.findById(AddedepID);
		if(departement.isPresent()) {
			String name= departement.get().getName();
			assertEquals("Finance", name);
		}
		
	}
	@Test
	public void testAffecterDepartementAEntreprise() {
		entrepriseService.affecterDepartementAEntreprise(departement.getId(), entreprise.getId());
		Optional<Departement> dep = deptRepository.findById(departement.getId());
		if(dep.isPresent()) {
			Entreprise emps= dep.get().getEntreprise();
			assertEquals(emps.getId(), entreprise.getId());
		}
		
	}
	@Test
	public void testgetAllDepartementsNamesByEntreprise(){
		List<String> names= entrepriseService.getAllDepartementsNamesByEntreprise(entreprise.getId());
		assertEquals(1, names.size());
	}
	/*@Test
	public void testDeleteDepartementById() {
		entrepriseService.deleteDepartementById(departement.getId());
		assertThat(deptRepository.findAll()).isEmpty();
	}*/
}