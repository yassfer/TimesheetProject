package tn.esprit.spring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class EntrepriseServiceImplTest {
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImplTest.class);


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
		long startTime = System.nanoTime();
		int AddedepID = entrepriseService.ajouterDepartement(dep);
		long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
		l.log(Level.INFO, () -> "Execution time of AjouterDepartement : " +elapsedTimeInSecond+" seconds");
		Optional<Departement> departement = deptRepository.findById(AddedepID);
		if(departement.isPresent()) {
			String name= departement.get().getName();
			assertEquals("Finance", name);
		}
		
	}
	@Test
	public void testAffecterDepartementAEntreprise() {
		long startTime = System.nanoTime();
		entrepriseService.affecterDepartementAEntreprise(departement.getId(), entreprise.getId());
		long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
		l.log(Level.INFO, () -> "Execution time of AffecterDepartementAEntreprise : " +elapsedTimeInSecond+" seconds");
		Optional<Departement> dep = deptRepository.findById(departement.getId());
		if(dep.isPresent()) {
			Entreprise emps= dep.get().getEntreprise();
			assertEquals(emps.getId(), entreprise.getId());
		}
		
	}
	@Test
	public void testgetAllDepartementsNamesByEntreprise(){
		long startTime = System.nanoTime();
		List<String> names= entrepriseService.getAllDepartementsNamesByEntreprise(entreprise.getId());
		long stopTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (stopTime - startTime) / 1_000_000_000;
		l.log(Level.INFO, () -> "Execution time of getAllDepartementsNamesByEntreprise : " +elapsedTimeInSecond+" seconds");
		assertEquals(1, names.size());
	}
	/*
	@Test
	public void testDeleteDepartementById() {
		entrepriseService.deleteDepartementById(departement.getId());
		assertThat(deptRepository.findAll()).isEmpty();
	}*/
}